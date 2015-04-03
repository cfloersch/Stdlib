/**
 * Created By: cfloersch
 * Date: 1/30/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Simple enumeration of java reference types.
 */
public enum ReferenceType {

   /**
    * A normal java reference which will prevent the referenced item from
    * being garbage collected.
    */
   Strong {
      public <T> Reference<T> create(ReferenceQueue<? super T> queue, final T referent)
      {
         return new StrongReference<>(referent);
      }
   },

   /**
    * Java reference type which allows the referenced items to be garbage
    * collected in a globally least recently used way due to memory demand.
    */
   Soft {
      public <T> Reference<T> create(ReferenceQueue<? super T> queue, T referent)
      {
         return new SoftReference<>(referent, queue);
      }
   },

   /**
    * Java reference type which does not prevent it's referents from being
    * garbage collected.
    */
   Weak {
      public <T> Reference<T> create(ReferenceQueue<? super T> queue, T referent)
      {
         return new WeakReference<>(referent, queue);
      }
   },

   /**
    * Java reference type which marks a referent as ready for finalization
    * and collection.
    */
   Phantom {
      public <T> Reference<T> create(ReferenceQueue<? super T> queue, T referent)
      {
         return new PhantomReference<>(referent, queue);
      }
   };


   public abstract <T> Reference<T> create(ReferenceQueue<? super T> queue, T referent);



   private static class StrongReference<T> extends WeakReference<T> {

      private T referent;

      public StrongReference(T referent)
      {
         super(referent);
         this.referent = referent;
      }

      public void clear()
      {
         referent = null;
      }

      public boolean enqueue()
      {
         return false;
      }

      public T get()
      {
         return referent;
      }

      public boolean isEnqueued()
      {
         return false;
      }

   }
}
