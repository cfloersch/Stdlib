package xpertss.threads;


import xpertss.lang.Objects;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

/**
 * Creates a new thread for each execution. If a thread factory is specified it will
 * be used to create new threads.
 */
public final class NewThreadExecutor implements Executor {

   private final ThreadFactory factory;

   /**
    * Create a new thread executor with a default thread factory.
    */
   public NewThreadExecutor()
   {
      this(null);
   }

   /**
    * Create a new thread executor with the given thread factory.
    */
   public NewThreadExecutor(ThreadFactory factory)
   {
      this.factory = Objects.ifNull(factory, ThreadFactorySupplier.INSTANCE);
   }



   @Override
   public void execute(Runnable command)
   {
      if(command != null) factory.newThread(command).start();
   }

   private enum ThreadFactorySupplier implements Supplier<ThreadFactory> {
      INSTANCE;


      @Override
      public ThreadFactory get()
      {
         return Threads.newThreadFactory("NewThreadExecutor");
      }
   }

}
