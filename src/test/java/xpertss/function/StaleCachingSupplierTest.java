package xpertss.function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import xpertss.threads.Threads;
import xpertss.time.TimeProvider;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StaleCachingSupplierTest {

   private Supplier<Integer> testSupplier;
   private TimeProvider mocktime;

   private StaleCachingSupplier<Integer> objectUnderTest;

   @Before
   public void setUp() throws Exception {
      testSupplier = mock(Supplier.class);
      when(testSupplier.get()).thenReturn(33).thenReturn(44);

      mocktime = mock(TimeProvider.class);
      when(mocktime.nanoTime()).thenAnswer(new Answer<Long>() {
         private long[] mains = {0, 500, 1000, 1001, 2001, 3000};
         private int main = 0;
         private long[] loads = {0, 2001};
         private int load = 0;

         public Long answer(InvocationOnMock invocationOnMock) throws Throwable
         {
            if (Threads.isMainThread()) {
               return MILLISECONDS.toNanos(mains[main++ % mains.length]);
            }
            return MILLISECONDS.toNanos(loads[load++ % loads.length]);
         }
      });

      objectUnderTest = new StaleCachingSupplier<Integer>(mocktime, testSupplier, 1, 1, SECONDS);
   }

   @Test
   public void testInitialLoad() {
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
   }

   @Test
   public void testOneSecondCache() {
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      verify(testSupplier).get();
   }

   @Test
   public void testAsyncStaleCache() {
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      assertEquals(Integer.valueOf(44), objectUnderTest.get());
   }


   @Test
   public void testReturnStaleOnError() {
      when(testSupplier.get()).thenReturn(33).thenReturn(null);
      when(mocktime.nanoTime()).thenAnswer(new Answer<Long>() {
         private long[] mains = {0, 1006, 2006, 3000};
         private int main = 0;

         private long[] loads = {5, 2008};
         private int load = 0;

         public Long answer(InvocationOnMock invocationOnMock)
               throws Throwable
         {
            if (Threads.isMainThread()) {
               return MILLISECONDS.toNanos(mains[main++ % mains.length]);
            }
            return MILLISECONDS.toNanos(loads[load++ % loads.length]);
         }
      });
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      assertEquals(Integer.valueOf(33), objectUnderTest.get());
      assertNull(objectUnderTest.get());
      assertNull(objectUnderTest.get());
      verify(testSupplier, times(3)).get();
   }

   // Test null on initialization error
   @Test
   public void testReturnNullOnError() {
      when(testSupplier.get()).thenReturn(null);
      when(mocktime.nanoTime()).thenAnswer(new Answer<Long>() {
         private long[] mains = { 0, 1006, 2006, 3000 };
         private int main = 0;

         private long[] loads = { 5, 2008 };
         private int load = 0;

         public Long answer(InvocationOnMock invocationOnMock)
               throws Throwable
         {
            if(Threads.isMainThread()) {
               return MILLISECONDS.toNanos(mains[main++ % mains.length]);
            }
            return MILLISECONDS.toNanos(loads[load++ % loads.length]);
         }
      });
      assertNull(objectUnderTest.get());
      assertNull(objectUnderTest.get());
      assertNull(objectUnderTest.get());
      verify(testSupplier, times(3)).get();
   }



   @Test
   public void testMultipleConncurrentAccess() throws Exception
   {
      Supplier<String> test = mock(Supplier.class);
      when(test.get()).thenAnswer(new Answer<String>() {
         @Override
         public String answer(InvocationOnMock invocation) throws Throwable {
            Threads.sleep(30, TimeUnit.MILLISECONDS);
            return "Hello";
         }});
      final StaleCachingSupplier<String> cache = new StaleCachingSupplier<String>(test, 30, 30, TimeUnit.SECONDS);

      final Observer observer = mock(Observer.class);


      final CyclicBarrier barrier = new CyclicBarrier(3);
      final CountDownLatch latch = new CountDownLatch(3);
      Thread[] threads = new Thread[3];
      for(int i = 0; i < 3; i++) {
         threads[i] = new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                  barrier.await();
                  observer.update(null, cache.get());
               } catch(Exception e) {

               } finally {
                  latch.countDown();
               }
            }
         });
         threads[i].start();
      }

      latch.await(1, TimeUnit.SECONDS);

      verify(test, times(1)).get();                                           // No stampede
      verify(observer, times(3)).update(eq((Observable)null), eq("Hello"));   // all three threads return
   }



}
