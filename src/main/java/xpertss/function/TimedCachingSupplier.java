/**
 * Created By: cfloersch
 * Date: 1/28/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;

import xpertss.lang.Numbers;
import xpertss.time.SystemTimeProvider;
import xpertss.time.TimeProvider;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

import static xpertss.lang.Objects.notNull;

/**
 * A TimedCachingSupplier caches an item supplied by an underlying supplier for a given
 * amount of time. It will synchronously update the cached item upon the first access
 * after the cached item becomes expired.
 * <p>
 * Accessing the cached item will block if the cached item has not been loaded initially
 * or if the access time is greater than its expiration period.
 * <p>
 * This supplier ensures that only a single thread is used to update or load the underlying
 * cached item at a time avoiding the stampede effect.
 * <p>
 * If the underlying supplier returns {@code null} it will be assumed that the load failed
 * and  {@code null} will be returned to the caller. Subsequent threads will again attempt
 * to reload the cache returning {@code null} only if their respective attempt to load or
 * refresh the cache fails.
 */
public final class TimedCachingSupplier<T> implements Supplier<T> {

   /**
    * Compose a function that will create a timed caching supplier for a given input.
    *
    * @param provider The function that creates the actual underlying supplier delegate
    * @param maxAge The maxAge to use for the newly created expiring supplier
    * @param unit The unit maxAge is measured in
    * @return A function that will create an expiring suppliers for each input
    */
   public static <K,V> Function<K,Supplier<V>> compose(Function<K,Supplier<V>> provider, long maxAge, TimeUnit unit)
   {
      final Function<K,Supplier<V>> source = notNull(provider);
      final long fMaxAge = Numbers.gt(0L, maxAge, "maxAge");
      final TimeUnit fUnit = notNull(unit);
      return input -> new TimedCachingSupplier<>(source.apply(input), fMaxAge, fUnit);
   }


   private final ReentrantLock lock = new ReentrantLock();

   private final TimeProvider timer;
   private final Supplier<T> delegate;
   private final Function<T, Long> expiry;

   private volatile Pair<T> pair;


   /**
    * Constructs a new TimedCachingSupplier that will use the specified delegate to
    * load its cache items. Those items will be cached for the specified duration.
    *
    * @param delegate The delegate that will supply cache items
    * @param duration The duration the item should be cached before refresh
    * @param unit The unit the duration is measured in.
    */
   public TimedCachingSupplier(Supplier<T> delegate, long duration, TimeUnit unit)
   {
      this(new SystemTimeProvider(), delegate, duration, unit);
   }

   /**
    * Constructs a new TimedCachingSupplier that will use the specified delegate to
    * load its cache items. Those items will be cached for a duration defined by the
    * supplied Function.
    * <p/>
    * The Function should evaluate the item to cache and return a value representing
    * an expires_in measured nanoseconds.
    *
    * @param delegate The delegate that will supply cache items
    * @param expiry A function that given a cache item, returns the expires_in in
    *                nanoseconds
    */
   public TimedCachingSupplier(Supplier<T> delegate, Function<T, Long> expiry)
   {
      this(new SystemTimeProvider(), delegate, expiry);
   }


   TimedCachingSupplier(TimeProvider timer, Supplier<T> delegate, long duration, TimeUnit unit)
   {
      this.timer = notNull(timer, "timer");
      this.delegate = notNull(delegate, "delegate");
      this.expiry = new DurationExpiry(duration, notNull(unit, "unit"));
   }

   TimedCachingSupplier(TimeProvider timer, Supplier<T> delegate, Function<T, Long> expiry)
   {
      this.timer = notNull(timer, "timer");
      this.delegate = notNull(delegate, "delegate");
      this.expiry = notNull(expiry, "expiry");
   }


   @Override
   public T get()
   {
      Pair<T> copy = pair;
      if(isExpired(copy)) {
         lock.lock();
         try {
            copy = pair;
            if(isExpired(copy)) {
               T value = delegate.get();
               if(value != null) {
                  copy = pair = new Pair<>(value, timer.nanoTime() + expiry.apply(value));
               } else {
                  return null;
               }
            }
         } finally {
            lock.unlock();
         }
      }
      return copy.get();
   }

   private boolean isExpired(Pair<T> copy)
   {
      return (copy == null || timer.nanoTime() > copy.getExpiryNanos());
   }


   private static class Pair<T> {

      final T reference;
      final long expiryNanos;


      Pair(T reference, long expiryNanos)
      {
         this.reference = reference;
         this.expiryNanos = expiryNanos;
      }

      public long getExpiryNanos()
      {
         return expiryNanos;
      }

      public T get()
      {
         return reference;
      }

   }

   private class DurationExpiry implements Function<T, Long> {

      private final long duration;

      private DurationExpiry(long duration, TimeUnit unit)
      {
         this.duration = unit.toNanos(Numbers.gte(0L, duration, "duration must not be negative"));
      }

      @Override
      public Long apply(T t)
      {
         return duration;
      }

   }
}
