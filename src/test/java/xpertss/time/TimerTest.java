/**
 * Created By: cfloersch
 * Date: 1/30/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TimerTest {

   private TimeProvider provider;

   @BeforeEach
   public void setUp()
   {
      provider = mock(TimeProvider.class);
      doAnswer(new Answer() {
         long[] vals = {0L, 120L, 120000L, 120000000L, 120000000000L, 1000000000L * 60L * 120L , 1000000000L * 60L * 60L * 120L, 1000000000L * 60L * 60L * 24L * 120L };
         int count = 0;
         @Override public Object answer(InvocationOnMock invocation) throws Throwable
         {
            return vals[count++];
         }
      }).when(provider).nanoTime();
   }

   @Test
   public void testTimerToString()
   {

      Timer t = Timer.create(provider);
      assertEquals("120 ns", t.toString(TimeUnit.NANOSECONDS));
      assertEquals("120 µs", t.toString(TimeUnit.MICROSECONDS));
      assertEquals("120 ms", t.toString(TimeUnit.MILLISECONDS));
      assertEquals("120 s", t.toString(TimeUnit.SECONDS));
      assertEquals("120 min", t.toString(TimeUnit.MINUTES));
      assertEquals("120 hrs", t.toString(TimeUnit.HOURS));
      assertEquals("120 days", t.toString(TimeUnit.DAYS));
   }

}
