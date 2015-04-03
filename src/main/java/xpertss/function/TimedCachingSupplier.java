/**
 * Created By: cfloersch
 * Date: 1/28/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;

import xpertss.lang.Numbers;
import xpertss.lang.Objects;
import xpertss.time.SystemTimeProvider;
import xpertss.time.TimeProvider;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A TimedCachingSupplier caches an item supplied by an underlying supplier for a given
 * amount of time represented by duration. It will synchronously update the cached item
 * upon the first access after the cached item becomes expired.
 * <p/>
 * Accessing the cached item will block if the cached item has not been loaded initially
 * or if the access time is greater than its expiration period.
 * <p/>
 * This supplier ensures that only a single thread is used to update or load the underlying
 * cached item at a time avoiding the stampede effect.
 * <p/>
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
      final Function<K,Supplier<V>> source = Objects.notNull(provider);
      final long fMaxAge = Numbers.gt(0L, maxAge, "maxAge");
      final TimeUnit fUnit = Objects.notNull(unit);
      return new Function<K, Supplier<V>>() {
         @Override
         public Supplier<V> apply(K input)
         {
            return new TimedCachingSupplier<>(source.apply(input), fMaxAge, fUnit);
         }
      };
   }


   private final ReentrantLock lock = new ReentrantLock();

   private final Supplier<T> delegate;
   private final long durationNanos;
   private final TimeProvider timer;

   private volatile Pair<T> pair;


   /**
    * Constructs a new ExpiringSupplier that will use the specified delegate to
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


   TimedCachingSupplier(TimeProvider timer, Supplier<T> delegate, long duration, TimeUnit unit)
   {
      this.timer = Objects.notNull(timer, "timer");
      this.delegate = Objects.notNull(delegate, "delegate");
      this.durationNanos = unit.toNanos(Numbers.gte(0L, duration, "duration must not be negative"));
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
                  copy = pair = new Pair<>(value, timer.nanoTime());
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
      return (copy == null || timer.nanoTime() - copy.getTimestamp() > durationNanos);
   }


   private static class Pair<T> {

      final T reference;
      final long timestamp;


      Pair(T reference, long timestamp)
      {
         this.reference = reference;
         this.timestamp = timestamp;
      }

      public long getTimestamp()
      {
         return timestamp;
      }

      public T get()
      {
         return reference;
      }

   }

}
