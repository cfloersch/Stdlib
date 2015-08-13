package xpertss.time;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 8/13/2015.
 */
public class DurationTest {

   @Test
   public void testNullString()
   {
      assertEquals(0, Duration.parse(null, TimeUnit.SECONDS));
   }

   @Test
   public void testZeroUnspecified()
   {
      assertEquals(0, Duration.parse("0", TimeUnit.SECONDS));
   }

   @Test
   public void testZeroSeconds()
   {
      assertEquals(0, Duration.parse("0s", TimeUnit.SECONDS));
   }

   @Test
   public void testOneSeconds()
   {
      assertEquals(1, Duration.parse("1s", TimeUnit.SECONDS));
   }

   @Test
   public void testOneMinute()
   {
      assertEquals(60, Duration.parse("1m", TimeUnit.SECONDS));
   }

   @Test
   public void testOneHour()
   {
      assertEquals(3600, Duration.parse("1h", TimeUnit.SECONDS));
   }

   @Test
   public void testOneDay()
   {
      assertEquals(3600 * 24, Duration.parse("1d", TimeUnit.SECONDS));
   }


}