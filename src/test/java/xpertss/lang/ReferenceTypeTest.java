/**
 * Created By: cfloersch
 * Date: 1/30/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReferenceTypeTest {

   private ReferenceQueue<String> queue;

   @BeforeEach
   public void setUp()
   {
      queue = new ReferenceQueue<>();
   }

   @Test
   public void testWeakReference()
   {
      String hello = "hello";
      Reference ref = ReferenceType.Weak.create(queue, hello);
      assertSame(WeakReference.class, ref.getClass());
      assertFalse(ref.isEnqueued());
      assertNull(queue.poll());
      assertTrue(ref.enqueue());
      assertSame(ref, queue.poll());
   }

   @Test
   public void testSoftReference()
   {
      String hello = "hello";
      Reference ref = ReferenceType.Soft.create(queue, hello);
      assertSame(SoftReference.class, ref.getClass());
      assertFalse(ref.isEnqueued());
      assertNull(queue.poll());
      assertTrue(ref.enqueue());
      assertSame(ref, queue.poll());
   }

   @Test
   public void testPhantomReference()
   {
      String hello = "hello";
      Reference ref = ReferenceType.Phantom.create(queue, hello);
      assertSame(PhantomReference.class, ref.getClass());
      assertFalse(ref.isEnqueued());
      assertNull(queue.poll());
      assertTrue(ref.enqueue());
      assertSame(ref, queue.poll());
   }

   @Test
   public void testStrongReference()
   {
      String hello = "hello";
      Reference ref = ReferenceType.Strong.create(queue, hello);
      assertFalse(ref.isEnqueued());
      assertNull(queue.poll());
      assertFalse(ref.enqueue());
   }

}
