/**
 * Created By: cfloersch
 * Date: 1/28/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;

import xpertss.lang.Objects;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.locks.ReentrantLock;



/**
 * A SoftCachingSupplier caches an item supplied by an underlying supplier in a soft
 * reference.
 * <p/>
 * Accessing the cached item will block if the cached item has not been loaded initially
 * or if the soft reference has been cleared by the vm due to low memory conditions.
 * <p/>
 * This supplier ensures that only a single thread is used to load the underlying cached
 * item at a time avoiding the stampede effect.
 * <p/>
 * If the underlying supplier returns {@code null} it will be assumed that the load failed
 * and  {@code null} will be returned to the caller. Subsequent threads will again attempt
 * to load the cache returning {@code null} only if their respective attempt to load also
 * fail.
 */
public final class SoftCachingSupplier<T> implements Supplier<T> {


   /**
    * Compose a function that will create a soft caching supplier for a given input.
    *
    * @param provider The function that creates the actual underlying supplier delegate
    * @return A function that will create a soft caching suppliers for each input
    */
   public static <K,V> Function<K,Supplier<V>> compose(Function<K,Supplier<V>> provider)
   {
      final Function<K,Supplier<V>> source = Objects.notNull(provider);
      return new Function<K, Supplier<V>>() {
         @Override
         public Supplier<V> apply(K input)
         {
            return new SoftCachingSupplier<>(source.apply(input));
         }
      };
   }


   private final ReentrantLock lock = new ReentrantLock();
   private final Supplier<T> delegate;

   volatile Reference<T> ref = new SoftReference<>((T)null);

   /**
    * Construct an instance of Memoizing supplier that will load its cache item from
    * the given delegate.
    *
    * @throws NullPointerException if delegate is {@code null}
    */
   public SoftCachingSupplier(Supplier<T> delegate)
   {
      this.delegate = Objects.notNull(delegate);
   }


   @Override
   public T get()
   {

      T result = null;
      while((result = ref.get()) == null) {
         lock.lock();
         try {
            if((result = ref.get()) == null) {
               T value = delegate.get();
               if(value != null) {
                  ref = new SoftReference<>(value);
               } else {
                  return null;
               }
            }
         } finally {
            lock.unlock();
         }
      }
      return result;
   }
}
