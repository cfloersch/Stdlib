package xpertss.threads;

import java.util.concurrent.Executor;

/**
 * Executes the runnable in the current thread.
 */
public final class DirectExecutor implements Executor {

   @Override
   public void execute(Runnable command)
   {
      if(command != null) command.run();
   }

}
