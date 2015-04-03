package xpertss.time;

/**
 * {@link TimeProvider} implementation that utilizes calls to
 * {@link System#currentTimeMillis()} and {@link System#nanoTime()}
 */
public final class SystemTimeProvider extends TimeProvider {

   @Override
   public long milliTime()
   {
      return System.currentTimeMillis();
   }

   @Override
   public long nanoTime()
   {
      return System.nanoTime();
   }
}