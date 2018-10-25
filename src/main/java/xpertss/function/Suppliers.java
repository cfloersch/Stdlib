/**
 * Created By: cfloersch
 * Date: 6/7/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;

import xpertss.lang.Classes;
import xpertss.lang.Objects;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility methods to create and chain supplier implementations.
 *
 * @see Supplier
 */
@SuppressWarnings("UnusedDeclaration")
public final class Suppliers {

   private Suppliers() { }

   /**
    * Returns a new supplier which is the composition of the provided function and
    * supplier.  In other words, the new supplier's value  will be computed by
    * retrieving the value from supplier, and then applying function to that value.
    * Note that the resulting supplier will not call supplier delegate or invoke
    * function until it's {@link Supplier#get} is called.
    *
    * @throws NullPointerException if function or supplier are {@code null}
    */
   public static <F,T> Supplier<T> compose(Function<? super F,T> function, Supplier<F> supplier)
   {
      return new SupplierComposition<>(function, supplier);
   }

   private static class SupplierComposition<F, T> implements Supplier<T>, Serializable {
      final Function<? super F, T> function;
      final Supplier<F> supplier;

      SupplierComposition(Function<? super F, T> function, Supplier<F> supplier)
      {
         this.function = Objects.notNull(function, "function");
         this.supplier = Objects.notNull(supplier, "supplier");
      }

      public T get()
      {
         return function.apply(supplier.get());
      }
   }





   /**
    * Returns a supplier that always supplies the specified instance. If the instance is
    * {@code null} then the returned supplier will always return {@code null}.
    */
   public static <T> Supplier<T> of(T instance)
   {
      return new InstanceSupplier<>(instance);
   }

   private static class InstanceSupplier<T> implements Supplier<T>, Serializable {
      final T instance;

      InstanceSupplier(T instance)
      {
         this.instance = instance;
      }
      public T get()
      {
         return instance;
      }
   }







   private static final Class[] lockers = { SyncSafeSupplier.class, LockSafeSupplier.class };


   /**
    * Returns a supplier which guarantees that the delegate's {@link Supplier#get()} method
    * will be called by only a single thread at a time, making it thread-safe. This
    * implementation will synchronizes on the delegate before calling it.
    * <p>
    * Using traditional synchronization is suitable where very little contention exists. As
    * lock contention goes up it scales poorly.
    *
    * @throws NullPointerException if the specified {@code delegate} is {@code null}
    * @see #lock(Supplier)
    */
   public static <T> Supplier<T> synchronize(Supplier<T> delegate)
   {
      if(Classes.isInstanceOf(delegate, lockers)) return delegate;
      return new SyncSafeSupplier<>(delegate);
   }

   private static class SyncSafeSupplier<T> implements Supplier<T>, Serializable {
      final Supplier<T> delegate;

      SyncSafeSupplier(Supplier<T> delegate)
      {
         this.delegate = Objects.notNull(delegate);
      }
      public T get()
      {
         synchronized (delegate) {
            return delegate.get();
         }
      }
   }


   /**
    * Returns a supplier which guarantees that the delegate's {@link Supplier#get()}
    * method will be called by only a single thread at a time, making it thread-safe.
    * This implementation will acquire a lock {@link ReentrantLock} before calling the
    * delegate.
    * <p>
    * This implementation is very similar to the synchronized variant at low contention
    * levels even though it is less consistent.  However, at higher contention levels it
    * performs slightly better.
    *
    * @throws NullPointerException if the specified {@code delegate} is {@code null}
    * @see #synchronize(Supplier)
    */
   public static <T> Supplier<T> lock(Supplier<T> delegate)
   {
      if(Classes.isInstanceOf(delegate, lockers)) return delegate;
      return new LockSafeSupplier<>(delegate);
   }

   private static class LockSafeSupplier<T> implements Supplier<T>, Serializable {
      final ReentrantLock lock = new ReentrantLock();
      final Supplier<T> delegate;

      LockSafeSupplier(Supplier<T> delegate)
      {
         this.delegate = Objects.notNull(delegate);
      }
      public T get()
      {
         try {
            lock.lock();
            return delegate.get();
         } finally {
            lock.unlock();
         }
      }
   }




















   private static final Class[] memoizers = { MemoizingSupplier.class, SoftCachingSupplier.class,
                                                TimedCachingSupplier.class, StaleCachingSupplier.class };

   /**
    * Returns a supplier which caches the instance retrieved during the first call to
    * {@code get()} and returns that value on subsequent calls.
    * <p>
    * The returned supplier is thread-safe. It utilizes a double check lock mechanism
    * to maintain as much efficiency as possible.
    * <p>
    * This implementation stores the supplied value in a hard reference ensuring once
    * it is loaded the first time it will always be available on subsequent calls.
    * <p>
    * If the specified delegate is the product of a previous call to memoize or is an
    * instance of any of the predefined caching suppliers, it is returned directly.
    * <p>
    * If the underlying supplier returns {@code null} it will be assumed that the load
    * failed and  {@code null} will be returned to the caller. Subsequent threads will
    * again attempt to load the cache returning {@code null} only if their respective
    * attempt to load also fail.
    *
    * @param delegate The supplier delegate that will provide instances to be cached
    * @throws NullPointerException if the specified delegate is {@code null}
    * @see SoftCachingSupplier
    * @see TimedCachingSupplier
    * @see StaleCachingSupplier
    */
   public static <T> Supplier<T> memoize(Supplier<T> delegate)
   {
      if(Classes.isInstanceOf(delegate, memoizers)) return delegate;
      return new MemoizingSupplier<>(delegate);
   }

   static class MemoizingSupplier<T> implements Supplier<T> {

      private final ReentrantLock lock = new ReentrantLock();

      private final Supplier<T> delegate;
      private volatile T item;

      private MemoizingSupplier(Supplier<T> delegate)
      {
         this.delegate = Objects.notNull(delegate);
      }

      @Override
      public T get()
      {
         if(item == null) {
            lock.lock();
            try {
               if(item == null) item = delegate.get();
            } finally {
               lock.unlock();
            }
         }
         return item;
      }

   }


}
