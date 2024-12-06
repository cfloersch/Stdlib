package xpertss.time;


import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
public class DurationTest {

   @Test
   public void testBasicProof()
   {
      assertEquals(Duration.parse("1d", SECONDS), Duration.parse("24h", SECONDS));
      assertEquals(Duration.parse("1440m", SECONDS), Duration.parse("24h", SECONDS));
      assertEquals(Duration.parse("1440m", SECONDS), Duration.parse("86400s", SECONDS));
   }

   @Test
   public void testDaysToHours()
   {
      assertEquals(24L, Duration.parse("1d", HOURS));

   }

   @Test
   public void testHoursToMinutes()
   {
      assertEquals(60L, Duration.parse("1h", MINUTES));

   }

   @Test
   public void testMinutesToSeconds()
   {
      assertEquals(60L, Duration.parse("1m", SECONDS));

   }


   @Test
   public void testZeroUnspecified()
   {
      assertEquals(0, Duration.parse("0", SECONDS));
   }

   @Test
   public void testZeroSeconds()
   {
      assertEquals(0, Duration.parse("0s", SECONDS));
   }

   @Test
   public void testOneSeconds()
   {
      assertEquals(1, Duration.parse("1s", SECONDS));
   }

   @Test
   public void testOneMinute()
   {
      assertEquals(60, Duration.parse("1m", SECONDS));
   }

   @Test
   public void testOneHour()
   {
      assertEquals(3600, Duration.parse("1h", SECONDS));
   }

   @Test
   public void testOneDay()
   {
      assertEquals(3600 * 24, Duration.parse("1d", SECONDS));
   }



   @Test
   public void testNullString()
   {
      assertThrows(NullPointerException.class, ()->{
         Duration.parse(null, SECONDS);
      });
   }

   @Test
   public void testNullUnit()
   {
      assertThrows(NullPointerException.class, ()->{
         Duration.parse("10s", null);
      });
   }

   @Test
   public void testInvalidUnitChars()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Duration.parse("100dd", SECONDS);
      });
   }

   @Test
   public void testInvalidDigits()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Duration.parse("3d3s", SECONDS);
      });
   }

   @Test
   public void testNegativeDuration()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Duration.parse("-3d", SECONDS);
      });
   }

   @Test
   public void testInvalidTimeUnit()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Duration.parse("3M", SECONDS);
      });
   }


}
