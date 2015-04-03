/*
* Created by IntelliJ IDEA.
* User: cfloersch
* Date: 10/24/12 1:04 PM
* Copyright 2013 XpertSoftware
*/
package xpertss.function;

import xpertss.lang.Numbers;
import xpertss.lang.Objects;
import xpertss.threads.Threads;
import xpertss.time.SystemTimeProvider;
import xpertss.time.TimeProvider;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;


/**
 * A StaleCachingSupplier caches an item supplied by an underlying supplier for a given amount
 * of time represented by max age. It will asynchronously update the cached item upon the first
 * access after the cached item becomes stale assuming that access occurs before the item has
 * exceeded its maximum stale period.
 * <p/>
 * Accessing the cached item will block if the cached item has not been loaded initially or
 * if the access time is greater than max stale period.
 * <p/>
 * This supplier ensures that only a single thread is used to update or load the underlying
 * cached item at a time avoiding the stampede effect.
 * <p/>
 * If the underlying supplier returns {@code null} it will be assumed that the load failed.
 * The previously loaded item will continue to be returned until a successful load occurs or
 * max stale is reached. Subsequent calls to get will result in additional attempts to load
 * the cached item. If no cached item is available to be returned because the item has passed
 * max stale or its initial load failed then {@code null} will be returned.
 */
public final class StaleCachingSupplier<T> implements Supplier<T> {


   /**
    * Compose a function that will create a stale caching supplier for a given input.
    *
    * @param provider The function that creates the actual underlying supplier delegate
    * @param maxAge The maxAge to use for the newly created caching supplier
    * @param maxStale The maxStale to use for the newly created caching supplier
    * @param unit The unit maxAge and maxStale are measured in
    * @return A function that will create a caching suppliers for each input
    */
   public static <K,V> Function<K,Supplier<V>> compose(Function<K,Supplier<V>> provider, long maxAge, long maxStale, TimeUnit unit)
   {
      final Function<K,Supplier<V>> source = Objects.notNull(provider);
      final long fMaxAge = Numbers.gt(0L, maxAge, "maxAge");
      final long fMaxStale = Numbers.gte(0L, maxStale, "maxStale");
      final TimeUnit fUnit = Objects.notNull(unit);
      return new Function<K, Supplier<V>>() {
         @Override
         public Supplier<V> apply(K input)
         {
            return new StaleCachingSupplier<>(source.apply(input), fMaxAge, fMaxStale, fUnit);
         }
      };
   }




   private final TimeProvider timer;

   private final long maxAge;
   private final long maxStale;
   private final Sync loader;

   private volatile Pair<T> pair;

   /**
    * @param timer The {@link TimeProvider} to use to supply time info
    * @param supplier The {@link Supplier} that will supply this cache with an updated
    *                 item when it needs to be loaded.
    * @param maxAge The maximum age before the cached item is considered stale.
    * @param maxStale The maximum amount of time before a stale item is no longer to
    *                 be served.
    * @param unit The time unit that maxAge and maxStale are measured in
    */
   StaleCachingSupplier(TimeProvider timer, Supplier<T> supplier, long maxAge, long maxStale, TimeUnit unit)
   {
      this.timer = Objects.notNull(timer, "timer");
      this.loader = new Sync(Objects.notNull(supplier, "supplier"));
      this.maxAge = Objects.notNull(unit, "unit").toNanos(Numbers.gt(0L, maxAge, "invalid max age"));
      this.maxStale = unit.toNanos(Numbers.gte(0L, maxStale, "invalid max stale")) + unit.toNanos(maxAge);
   }

   /**
    * Create a caching supplier that uses the specified supplier to obtain its cached
    * values.
    * <p/>
    * The item will be cached in memory for at least max age. At the end of the object's
    * lifetime it will be reloaded asynchronously and a stale copy returned if the max
    * stale time has not been exceeded. Otherwise, it will be reloaded and cached in a
    * blocking fashion.
    * <p/>
    * The max age and max stale are relative units. In absolute terms, max stale is
    * added to the end of the computed max age time.
    *
    * @param supplier The {@link Supplier} that will supply this cache with an updated
    *                 item when it needs to be loaded.
    * @param maxAge The maximum age before the cached item is considered stale.
    * @param maxStale The maximum amount of time before a stale item is no longer to
    *                 be served.
    * @param unit The time unit that maxAge and maxStale are measured in
    * @throws NullPointerException If specified supplier or unit are {@code null}
    */
   public StaleCachingSupplier(Supplier<T> supplier, long maxAge, long maxStale, TimeUnit unit)
   {
      this(new SystemTimeProvider(), supplier, maxAge, maxStale, unit);
   }


   /**
    * Returns an instance of the cached item, or {@code null} if the item could not be
    * loaded. This call may block if the currently cached item has never been loaded
    * or if it has exceeded its max stale time.
    */
   public T get()
   {
      long current = timer.nanoTime();
      checkStale(pair, current);
      if(isExpired(pair, current)) {
         loader.await();
         if(isExpired(pair, current)) return null;
      }
      return get(pair);
   }


   // internal helper method to return references from a potentially null pair
   private T get(Pair<T> copy)
   {
      return (copy != null) ? copy.reference : null;
   }

   // internal helper method to return stale status from a potential null pair
   private void checkStale(Pair<T> copy, long current)
   {
      if(copy == null) {
         // never loaded yet so use default seq of zero
         loader.reload(0);
      }  else if(current - copy.stamp > maxAge) {
         loader.reload(copy.seq);
      }
   }

   // internal helper method to return expired status from a potential null pair
   private boolean isExpired(Pair<T> copy, long current)
   {
      return (copy == null || current - copy.stamp > maxStale);
   }


   private final class Sync extends AbstractQueuedSynchronizer implements Runnable {

      /* State value representing that the loader is running */
      private static final int RUNNING   = -1;

      private ThreadFactory factory;
      private Supplier<T> supplier;
      private int sequence = 0;

      private Sync(Supplier<T> supplier)
      {
         this.supplier = supplier;
         this.factory = Threads.newThreadFactory("cache-loader", true);
      }


      public void await()
      {
         // blocks for as long as a thread is loading the data
         // if called after transition to READY then this wont block at all
         acquireShared(1);
      }

      public void reload(int seq)
      {
         // We don't want o block callers to this method but we only want one to be
         // capable of starting the thread per sequence number.
         if(compareAndSetState(seq, RUNNING)) {
            // Each sequence (including the initial sequence of zero) will only ever
            // be valid once assuming the cache loads successfully each time. if the
            // cache fails to load then the previous cached item remains and the
            // previous sequence remains as well.
            factory.newThread(this).start();
         }
      }

      public void run()
      {
         try {
            // Load the object from the underlying supplier
            T result = supplier.get();
            if(result != null) {
               // we assume null means it failed to load
               pair = Pair.of(result, ++sequence, timer.nanoTime());
            }
         } finally {
            // Release any threads that are blocked awaiting the load to complete
            // Return the state to the current sequence which represents the most recently
            // loaded item or the previously loaded item or zero if no item has ever been
            // loaded
            releaseShared(sequence);
         }
      }

      /**
       * Implements AQS base acquire to succeed if ran or cancelled
       */
      protected int tryAcquireShared(int arg)
      {
         // We want to block all callers for as long as the loader is running
         // When the loader is complete the state will transition to some number
         // representing the currently cached item's sequence number.
         return (getState() != RUNNING) ? 1 : -1;
      }

      /**
       * Return state to READY
       */
      protected boolean tryReleaseShared(int arg)
      {
         // Only our loader ever calls this and the current state should always be -1
         // Any state other than -1 represents NOT_RUNNING or READY.. In this case the
         // arg is an incrementing int. Every time we successfully load a cache item
         // the sequence is advanced by one and the state is set to that sequence. The
         // sequence can eventually overflow the bounds of an Integer but will advance
         // through all the negative values before landing on -1. If the sequence ever
         // reaches negative one then this system will break down. That seems unlikely
         // because The cache would have to load 4,294,967,296 times before it would
         // fail.
         return compareAndSetState(RUNNING, arg);
      }

   }

   private static class Pair<T> {
      final T reference;
      final long stamp;
      final int seq;

      private Pair(T reference, int seq, long stamp) {
         this.reference = reference;
         this.stamp = stamp;
         this.seq = seq;
      }

      static <T> Pair<T> of(T reference, int seq, long stamp) {
         return new Pair<>(reference, seq, stamp);
      }

   }

}
