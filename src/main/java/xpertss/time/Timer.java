/**
 * Created By: cfloersch
 * Date: 6/23/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.time;

import xpertss.lang.Objects;

import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Simple utility class which measures relative time. This timer has nanosecond
 * precision if the underlying system supports it. Time measurement begins when
 * an instance is created or when it is reset.
 */
@SuppressWarnings("UnusedDeclaration")
public final class Timer {

   private final TimeProvider provider;
   private long start;

   private Timer(TimeProvider provider)
   {
      this.provider = Objects.notNull(provider);
      this.start = provider.nanoTime();
   }


   /**
    * Create and return a timer instance. The measurement of time
    * begins when the object is created.
    */
   public static Timer create()
   {
      return new Timer(TimeProvider.get());
   }

   /**
    * Create and return a timer instance that uses the specified time provider to
    * obtain its underlying time values. The measurement of time begins when the
    * object is created.
    *
    * @throws NullPointerException if the provider is {@code null}
    */
   public static Timer create(TimeProvider provider)
   {
      return new Timer(provider);
   }




   /**
    * Returns the amount of time that has elapsed since the timer was created or
    * it was last reset. The result will be returned in the time unit specified.
    * <p>
    * This timer has nanosecond precision but its resolution is dependent on the
    * underlying operating system.
    */
   public long elapsedTime(TimeUnit unit)
   {
      return unit.convert(provider.nanoTime() - start, NANOSECONDS);
   }

   /**
    * Reset the base time by which relative time is measured.
    */
   public void reset()
   {
      start = provider.nanoTime();
   }


   private static final String[] units = { "ns", "µs", "ms", "s", "min", "hrs", "days" };

   /**
    * Returns the currently elapsed time in a human readable format in the specified
    * units.
    */
   public String toString(TimeUnit unit)
   {
      long time = elapsedTime(unit);
      return time + " " + units[unit.ordinal()];
   }

}
