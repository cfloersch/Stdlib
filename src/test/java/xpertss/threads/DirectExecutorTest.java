/**
 * Created By: cfloersch
 * Date: 4/8/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.threads;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertSame;


public class DirectExecutorTest {

   private Executor objectUdnerTest;

   @BeforeEach
   public void setUp()
   {
      objectUdnerTest = new DirectExecutor();
   }

   @Test
   public void testDirectlyRun()
   {
      TestRunnable task = new TestRunnable();
      objectUdnerTest.execute(task);
      assertSame(Thread.currentThread(), task.getThread());
   }

   @Test
   public void testNullRunnable()
   {
      objectUdnerTest.execute(null);
   }


   public class TestRunnable implements Runnable {
      private Thread thread;
      @Override public void run()
      {
         thread = Thread.currentThread();
      }
      public Thread getThread() { return thread; }

   }


}
