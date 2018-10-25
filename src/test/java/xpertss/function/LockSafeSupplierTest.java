package xpertss.function;

import org.junit.Test;
import xpertss.threads.PhaseBarrier;
import xpertss.threads.NewThreadExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class LockSafeSupplierTest {



   @Test
   public void testCpuCount() throws InterruptedException
   {
      System.out.println("CPU Count: " + Runtime.getRuntime().availableProcessors());
      testSynchronizedSupplier();
      testLockedSupplier();
   }

   public void testSynchronizedSupplier() throws InterruptedException
   {
      System.out.println("Synchronized Test");
      Supplier<String> supplier = Suppliers.synchronize(Suppliers.of("Hello"));
      benchmark3(1, supplier);
      benchmark3(2, supplier);
      benchmark3(3, supplier);
      benchmark3(4, supplier);
      benchmark3(5, supplier);
      benchmark3(10, supplier);
      benchmark3(15, supplier);
      benchmark3(25, supplier);
      benchmark3(50, supplier);
      benchmark3(100, supplier);
   }

   public void testLockedSupplier() throws InterruptedException
   {
      System.out.println("Locked Test");
      Supplier<String> supplier = Suppliers.lock(Suppliers.of("Hello"));
      benchmark3(1, supplier);
      benchmark3(2, supplier);
      benchmark3(3, supplier);
      benchmark3(4, supplier);
      benchmark3(5, supplier);
      benchmark3(10, supplier);
      benchmark3(15, supplier);
      benchmark3(25, supplier);
      benchmark3(50, supplier);
      benchmark3(100, supplier);
   }





   private void benchmark(int count, Supplier<String> supplier) throws InterruptedException
   {
      ThreadSafeTest warmup = new ThreadSafeTest(new CyclicBarrier(1), new CountDownLatch(1), supplier);
      for(int i = 0; i < 100; i++) warmup.run();


      NewThreadExecutor executor = new NewThreadExecutor();
      CyclicBarrier barrier = new CyclicBarrier(count);
      CountDownLatch latch = new CountDownLatch(count);
      ThreadSafeTest[] tests = new ThreadSafeTest[count];
      for(int i = 0; i < count; i++) {
         tests[i] = new ThreadSafeTest(barrier, latch, supplier);
         executor.execute(tests[i]);
      }
      latch.await();
      long maxTime = 0;
      for(int i = 0; i < count; i++) {
         maxTime = Math.max(maxTime, tests[i].getTime(TimeUnit.MICROSECONDS));
      }
      System.out.println(Integer.toString(count) + " threads: " + Long.toString(maxTime) + "µs");
   }




   private static class ThreadSafeTest implements Runnable {

      final CyclicBarrier barrier;
      final CountDownLatch latch;
      final Supplier<String> supplier;
      long nanoTime;
      private ThreadSafeTest(CyclicBarrier barrier, CountDownLatch latch, Supplier<String> supplier)
      {
         this.supplier = supplier;
         this.barrier = barrier;
         this.latch = latch;
      }


      @Override
      public void run()
      {
         try {
            barrier.await();
            long start = System.nanoTime();
            for(int i = 0; i < 10000; i++) {
               supplier.get();
            }
            nanoTime = System.nanoTime() - start;
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            latch.countDown();
         }
      }

      public long getTime(TimeUnit unit)
      {
         return unit.convert(nanoTime, TimeUnit.NANOSECONDS);
      }

   }








   private void benchmark2(int count, Supplier<String> supplier) throws InterruptedException
   {
      MyPhasedTest warmup = new MyPhasedTest(new PhaseBarrier(1), supplier);
      for(int i = 0; i < 100; i++) warmup.run();


      NewThreadExecutor executor = new NewThreadExecutor();
      Phaser phaser = new Phaser(count + 1);
      PhasedTest[] tests = new PhasedTest[count];
      for(int i = 0; i < count; i++) {
         tests[i] = new PhasedTest(phaser, supplier);
         executor.execute(tests[i]);
      }
      phaser.arriveAndAwaitAdvance();  // Starts waiting threads (Barrier release)
      phaser.arriveAndAwaitAdvance();  // Awaits termination of threads (Latch Release)
      long maxTime = 0;
      for(int i = 0; i < count; i++) {
         maxTime = Math.max(maxTime, tests[i].getTime(TimeUnit.MICROSECONDS));
      }
      System.out.println(Integer.toString(count) + " threads: " + Long.toString(maxTime) + "µs");
   }





   private static class PhasedTest implements Runnable {

      final Phaser barrier;
      final Supplier<String> supplier;
      long nanoTime;
      private PhasedTest(Phaser barrier, Supplier<String> supplier)
      {
         this.supplier = supplier;
         this.barrier = barrier;
      }


      @Override
      public void run()
      {
         try {
            barrier.arriveAndAwaitAdvance(); // awaits phase 0 to terminate
            long start = System.nanoTime();
            for(int i = 0; i < 10000; i++) {
               supplier.get();
            }
            nanoTime = System.nanoTime() - start;
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            barrier.arriveAndDeregister(); // pushes us one closer to phase 2
         }
      }

      public long getTime(TimeUnit unit)
      {
         return unit.convert(nanoTime, TimeUnit.NANOSECONDS);
      }

   }














   private void benchmark3(int count, Supplier<String> supplier) throws InterruptedException
   {
      MyPhasedTest warmup = new MyPhasedTest(new PhaseBarrier(1), supplier);
      for(int i = 0; i < 100; i++) warmup.run();


      NewThreadExecutor executor = new NewThreadExecutor();
      PhaseBarrier phaser = new PhaseBarrier(count);
      MyPhasedTest[] tests = new MyPhasedTest[count];
      for(int i = 0; i < count; i++) {
         tests[i] = new MyPhasedTest(phaser, supplier);
         executor.execute(tests[i]);
      }
      int ret;
      if((ret = phaser.awaitAdvance(1)) >= 0)  // await release of phase 1
         throw new Error("Failed to wait until termination: " + ret);
      long maxTime = 0;
      for(int i = 0; i < count; i++) {
         maxTime = Math.max(maxTime, tests[i].getTime(TimeUnit.MICROSECONDS));
      }
      System.out.println(Integer.toString(count) + " threads: " + Long.toString(maxTime) + "µs");
   }





   private static class MyPhasedTest implements Runnable {

      final PhaseBarrier barrier;
      final Supplier<String> supplier;
      long nanoTime;
      private MyPhasedTest(PhaseBarrier barrier, Supplier<String> supplier)
      {
         this.supplier = supplier;
         this.barrier = barrier;
      }


      @Override
      public void run()
      {
         try {
            barrier.arriveAndAwaitAdvance(); // awaits phase 0 to terminate
            long start = System.nanoTime();
            for(int i = 0; i < 10000; i++) {
               supplier.get();
            }
            nanoTime = System.nanoTime() - start;
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            barrier.arriveAndDeregister(); // pushes us one closer to phase 2
         }
      }

      public long getTime(TimeUnit unit)
      {
         return unit.convert(nanoTime, TimeUnit.NANOSECONDS);
      }

   }


}
