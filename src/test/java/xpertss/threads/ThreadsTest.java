/**
 * Created By: cfloersch
 * Date: 1/27/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.threads;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xpertss.util.Sets;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ThreadsTest {

   private Executor executor;
   private ReentrantLock lock;

   @BeforeEach
   public void setUp()
   {
      executor = new NewThreadExecutor();
      lock = new ReentrantLock();
   }


   @Test
   public void testAwaitCondition()
   {
      final Condition cond = lock.newCondition();
      try {
         lock.lock();

         executor.execute(new Runnable() {
            @Override public void run()
            {
               try {
                  lock.lock();
                  cond.signal();
               } finally {
                  lock.unlock();
               }
            }
         });

         assertTrue(Threads.await(cond));
         assertFalse(Thread.interrupted());
      } finally {
         lock.unlock();
      }

   }

   @Test
   public void testAwaitConditionNullArgument()
   {
      assertThrows(NullPointerException.class, ()->{
         Threads.await(null);
      });
   }

   @Test
   public void testAwaitConditionInterrupted()
   {
      final Condition cond = lock.newCondition();
      final Thread runner = Thread.currentThread();

      lock.lock();
      executor.execute(new Runnable() {
         @Override
         public void run()
         {
            runner.interrupt();
         }
      });
      assertFalse(Threads.await(cond));
      assertTrue(Thread.interrupted());
      lock.unlock();
   }

   @Test
   public void testAwaitConditionNoLock()
   {
      final Condition cond = lock.newCondition();
      assertThrows(IllegalMonitorStateException.class, ()->{
         Threads.await(cond);
      });
   }



   @Test
   public void testAwaitTimeout()
   {
      final Condition cond = lock.newCondition();
      try {
         lock.lock();
         assertFalse(Threads.await(cond, 10, TimeUnit.MILLISECONDS));
      } finally {
         lock.unlock();
      }
   }

   @Test
   public void testAwaitZeroTimeout()
   {
      final Condition cond = lock.newCondition();
      try {
         lock.lock();
         assertFalse(Threads.await(cond, 0, TimeUnit.MILLISECONDS));
      } finally {
         lock.unlock();
      }
   }

   @Test
   public void testAwaitConditionNegativeTimeout()
   {
      final Condition cond = lock.newCondition();
      try {
         lock.lock();
         assertFalse(Threads.await(cond, -1, TimeUnit.MILLISECONDS));
      } finally {
         lock.unlock();
      }
   }






   @Test
   public void testSleep()
   {
      assertTrue(Threads.sleep(2, TimeUnit.MILLISECONDS));
      assertFalse(Thread.interrupted());
   }

   @Test
   public void testSleepInterrupted()
   {
      final Thread runner = Thread.currentThread();
      executor.execute(new Runnable() {
         @Override public void run()
         {
            runner.interrupt();
         }
      });
      assertFalse(Threads.sleep(2, TimeUnit.MILLISECONDS));
      assertTrue(Thread.interrupted());
   }



   @Test
   public void testJoin()
   {
      Thread t = new Thread(new Runnable() {
         @Override public void run()
         {
            Threads.sleep(2, TimeUnit.MILLISECONDS);
         }
      });
      t.start();
      assertTrue(Threads.join(t));
      assertFalse(Thread.interrupted());
   }

   @Test
   public void testJoinInterrupted()
   {
      final Thread runner = Thread.currentThread();
      Thread t = new Thread(new Runnable() {
         @Override public void run()
         {
            runner.interrupt();
            Threads.sleep(2, TimeUnit.MILLISECONDS);
         }
      });
      t.start();
      assertFalse(Threads.join(t));
      assertTrue(Thread.interrupted());
   }






   @Test
   public void testIsTerminated()
   {
      Thread t = new Thread(new Runnable() {
         @Override public void run() { }
      });
      assertFalse(Threads.isTerminated(t));
      assertFalse(Threads.isTerminated(Thread.currentThread()));
      t.start();
      Threads.join(t, 10, TimeUnit.MILLISECONDS);
      assertTrue(Threads.isTerminated(t));
   }



   @Test
   public void testReport()
   {
      Throwable t = new Throwable("test");
      Thread.UncaughtExceptionHandler handler = mock(Thread.UncaughtExceptionHandler.class);
      Thread.currentThread().setUncaughtExceptionHandler(handler);
      Threads.report(t);
      verify(handler, times(1)).uncaughtException(eq(Thread.currentThread()), eq(t));
   }




   @Test
   public void testCurrentGroup()
   {
      ThreadGroup group = Threads.currentGroup();
      assertSame(Thread.currentThread().getThreadGroup(), group);
      assertEquals("main", group.getName());
   }



   @Test
   public void testGetThreads()
   {
      final CountDownLatch latch = new CountDownLatch(1);
      ThreadGroup group = new ThreadGroup("test-get-threads");
      Thread[] threads = new Thread[2];
      for(int i = 0; i < threads.length; i++) {
         threads[i] = new Thread(group, new Runnable() {
            @Override public void run()
            {
               try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
            }
         }, String.format("test-thread-%d", i));
         threads[i].start();
      }
      Set<Thread> gThreads = Threads.getThreads(group);
      assertEquals(2, gThreads.size());
      assertSame(threads[0], Sets.first(gThreads));
      assertSame(threads[1], Sets.last(gThreads));
      latch.countDown();
   }

   @Test
   public void testGetThreadsPredicated()
   {
      final CountDownLatch latch = new CountDownLatch(1);
      ThreadGroup group = new ThreadGroup("test-get-threads-predicate");
      Thread[] threads = new Thread[2];
      for(int i = 0; i < threads.length; i++) {
         threads[i] = new Thread(group, new Runnable() {
            @Override public void run()
            {
               try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
            }
         }, String.format("test-thread-%d", i));
         threads[i].start();
      }
      Set<Thread> gThreads = Threads.getThreads(group, input -> input.getName().contains("-1"));
      assertEquals(1, gThreads.size());
      assertSame(threads[1], Sets.first(gThreads));
      latch.countDown();
   }

   @Test
   public void testGetThreadsNullPredicate()
   {
      final CountDownLatch latch = new CountDownLatch(1);
      ThreadGroup group = new ThreadGroup("test-get-threads-null-predicate");
      Thread[] threads = new Thread[2];
      for(int i = 0; i < threads.length; i++) {
         threads[i] = new Thread(group, new Runnable() {
            @Override public void run()
            {
               try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
            }
         }, String.format("test-thread-%d", i));
         threads[i].start();
      }
      Set<Thread> gThreads = Threads.getThreads(group, null);
      assertEquals(2, gThreads.size());
      assertSame(threads[0], Sets.first(gThreads));
      assertSame(threads[1], Sets.last(gThreads));
      latch.countDown();
   }

   @Test
   public void testGetThreadsNullGroup()
   {
      assertThrows(NullPointerException.class, ()->{
         Threads.getThreads((ThreadGroup)null);
      });
   }


   @Test
   public void testFindThreadByName()
   {
      final CountDownLatch latch = new CountDownLatch(1);
      ThreadGroup A1 = new ThreadGroup("A1");
      Thread threadA1 = new Thread(A1, new Runnable() {
         @Override public void run()
         {
            try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
         }
      }, "A1-1");
      ThreadGroup A2 = new ThreadGroup(A1, "A2");
      Thread threadA2 = new Thread(A2, new Runnable() {
         @Override public void run()
         {
            try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
         }
      }, "A2-1");
      ThreadGroup B1 = new ThreadGroup("B1");
      Thread threadB1 = new Thread(B1, new Runnable() {
         @Override public void run()
         {
            try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
         }
      }, "B1-1");
      threadA1.start();
      threadA2.start();
      threadB1.start();

      assertNull(Threads.findThread("A1-1", false));
      assertNull(Threads.findThread("A2-1", false));
      assertSame(threadA2, Threads.findThread("A2-1", true));
      assertSame(threadA2, Threads.findThread(A2, "A2-1", false));
      assertSame(threadB1, Threads.findThread("B1-1", true));

      latch.countDown();
      Threads.sleep(10, TimeUnit.MILLISECONDS);
      A1.destroy();
      B1.destroy();
   }

   @Test
   public void testFindThreadById()
   {
      final CountDownLatch latch = new CountDownLatch(1);
      ThreadGroup A1 = new ThreadGroup("A1");
      Thread threadA1 = new Thread(A1, new Runnable() {
         @Override public void run()
         {
            try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
         }
      }, "A1-1");
      ThreadGroup A2 = new ThreadGroup(A1, "A2");
      Thread threadA2 = new Thread(A2, new Runnable() {
         @Override public void run()
         {
            try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
         }
      }, "A2-1");
      ThreadGroup B1 = new ThreadGroup("B1");
      Thread threadB1 = new Thread(B1, new Runnable() {
         @Override public void run()
         {
            try { latch.await(); } catch(InterruptedException e) { /* Ignore */ }
         }
      }, "B1-1");
      threadA1.start();
      threadA2.start();
      threadB1.start();

      assertNull(Threads.findThread(threadA1.getId(), false));
      assertNull(Threads.findThread(threadA2.getId(), false));
      assertSame(threadA2, Threads.findThread(threadA2.getId(), true));
      assertSame(threadA2, Threads.findThread(A2, threadA2.getId(), false));
      assertSame(threadB1, Threads.findThread(threadB1.getId(), true));

      latch.countDown();
      Threads.sleep(10, TimeUnit.MILLISECONDS);
      A1.destroy();
      B1.destroy();
   }


   @Test
   public void testRootGroup()
   {
      ThreadGroup root = Threads.rootGroup();
      assertEquals("system", root.getName());
   }


   @Test
   public void testFindGroup()
   {
      ThreadGroup A1 = new ThreadGroup("A1");
      ThreadGroup A21 = new ThreadGroup(A1, "A21");
      ThreadGroup A22 = new ThreadGroup(A1, "A22");
      ThreadGroup A31 = new ThreadGroup(A22, "A31");
      ThreadGroup B1 = new ThreadGroup("B1");
      ThreadGroup B2 = new ThreadGroup(B1, "B2");
      assertSame(A1, Threads.findGroup("A1"));
      assertSame(A21, Threads.findGroup("A21"));
      assertSame(A22, Threads.findGroup("A22"));
      assertSame(A31, Threads.findGroup("A31"));
      assertSame(B1, Threads.findGroup("B1"));
      assertSame(B2, Threads.findGroup("B2"));
      assertSame(A1, Threads.findGroup(A1, "A1"));
      assertNull(Threads.findGroup(B1, "A1"));
      assertNull(Threads.findGroup(A21, "A1"));

      assertSame(Threads.currentGroup(), Threads.findGroup("main"));
      assertSame(Threads.currentGroup(), Threads.findGroup(Threads.rootGroup(), "main"));
   }



   @Test
   public void testAscending()
   {
      Thread[] threads = new Thread[3];
      threads[0] = new Thread("zero");
      threads[0].setPriority(Thread.NORM_PRIORITY);
      threads[1] = new Thread("one");
      threads[1].setPriority(Thread.MAX_PRIORITY);
      threads[2] = new Thread("two");
      threads[2].setPriority(Thread.MIN_PRIORITY);
      Arrays.sort(threads, Threads.ascending());
      assertEquals("two", threads[0].getName());
      assertEquals("zero", threads[1].getName());
      assertEquals("one", threads[2].getName());
   }

   @Test
   public void testDescending()
   {
      Thread[] threads = new Thread[3];
      threads[0] = new Thread("zero");
      threads[0].setPriority(Thread.NORM_PRIORITY);
      threads[1] = new Thread("one");
      threads[1].setPriority(Thread.MAX_PRIORITY);
      threads[2] = new Thread("two");
      threads[2].setPriority(Thread.MIN_PRIORITY);
      Arrays.sort(threads, Threads.decending());
      assertEquals("one", threads[0].getName());
      assertEquals("zero", threads[1].getName());
      assertEquals("two", threads[2].getName());
   }




   @Test
   public void testNewThreadFactory()
   {
      ThreadFactory factoryOne = Threads.newThreadFactory("test");
      Thread threadOne = factoryOne.newThread(Threads.NO_OP);
      assertEquals("test-0", threadOne.getName());

      ThreadFactory factoryTwo = Threads.newThreadFactory("test");
      Thread threadTwo = factoryTwo.newThread(Threads.NO_OP);
      assertEquals("test-1", threadTwo.getName());

      assertSame(threadOne.getThreadGroup(), threadTwo.getThreadGroup());
   }


   @Test
   public void testNewThreadFactoryPrefix()
   {
      ThreadFactory testFactory = Threads.newThreadFactory("hello");
      Thread testThread = testFactory.newThread(Threads.NO_OP);

      assertFalse(testThread.isDaemon());
      assertTrue(testThread.getName().startsWith("hello"));
      assertEquals(Thread.NORM_PRIORITY, testThread.getPriority());
      assertEquals(Thread.State.NEW, testThread.getState());
   }

   @Test
   public void testNewThreadFactoryPrefixDaemon()
   {
      ThreadFactory testFactory = Threads.newThreadFactory("hello", true);
      Thread testThread = testFactory.newThread(Threads.NO_OP);
      assertTrue(testThread.isDaemon());
      assertTrue(testThread.getName().startsWith("hello"));
      assertEquals(Thread.NORM_PRIORITY, testThread.getPriority());
      assertEquals(Thread.State.NEW, testThread.getState());
   }

   @Test
   public void testNewThreadFactoryPrefixPriority()
   {
      ThreadFactory testFactory = Threads.newThreadFactory("hello", Thread.MAX_PRIORITY);
      Thread testThread = testFactory.newThread(Threads.NO_OP);
      assertFalse(testThread.isDaemon());
      assertTrue(testThread.getName().startsWith("hello"));
      assertEquals(Thread.MAX_PRIORITY, testThread.getPriority());
      assertEquals(Thread.State.NEW, testThread.getState());
   }

   @Test
   public void testNewThreadFactoryPrefixPriorityDaemon()
   {
      ThreadFactory testFactory = Threads.newThreadFactory("hello", Thread.MAX_PRIORITY, true);
      Thread testThread = testFactory.newThread(Threads.NO_OP);
      assertTrue(testThread.isDaemon());
      assertTrue(testThread.getName().startsWith("hello"));
      assertEquals(Thread.MAX_PRIORITY, testThread.getPriority());
      assertEquals(Thread.State.NEW, testThread.getState());
   }






}
