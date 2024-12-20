package xpertss.time;


import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * User: cfloersch
 * Date: 12/8/12
 */
public class DatesTest {


   @Test
   public void testParse()
   {
      Date date = Dates.parse(TimeZone.getDefault(), "yyyy-MM-dd", "2012-01-01", null);
      Date local = Dates.parseLocal("yyyy-MM-dd", "2012-01-01", null);
      assertEquals(local, date);
   }

   @Test
   public void testParsePacific()
   {
      Date date = Dates.parse(TimeZone.getTimeZone("US/Pacific"), "yyyy-MM-dd", "2012-01-01", null);
      Date local = Dates.parseLocal("yyyy-MM-dd", "2012-01-01", null);
      assertTrue(local.before(date));
   }

   @Test
   public void testParseNullTimeZone()
   {
      Date date = Dates.parse(null, "yyyy-MM-dd", "2012-01-01", null);
      Date local = Dates.parseLocal("yyyy-MM-dd", "2012-01-01", null);
      assertEquals(local, date);
   }


   @Test
   public void testParseLocale()
   {
      Chronology cal = Chronology.create();
      Date date = Dates.parseLocal("yyyy-MM-dd", "2012-01-01", null);
      assertEquals(cal.newDate(2012,1,1), date);
   }

   @Test
   public void testParseLocaleInvalidPattern()
   {
      assertNull(Dates.parseLocal("yeas it is that weired", "2012-01-01", null));
   }

   @Test
   public void testParseLocaleInvalidDateStr()
   {
      assertNull(Dates.parseLocal("yyyy-MM-dd", "cookies", null));
   }

   @Test
   public void testParseLocaleEmptyPattern()
   {
      assertNull(Dates.parseLocal("", "2012-01-01", null));
   }

   @Test
   public void testParseLocaleEmptyDateStr()
   {
      assertNull(Dates.parseLocal("yyyy-MM-dd", "", null));
   }

   @Test
   public void testParseLocaleNullPattern()
   {
      assertNull(Dates.parseLocal(null, "2012-01-01", null));
   }

   @Test
   public void testParseLocaleNulldateStr()
   {
      assertNull(Dates.parseLocal("yyyy-MM-dd", null, null));
   }



   @Test
   public void testLocalUTC()
   {
      Date utc = Dates.parseUTC("yyyy-MM-dd", "2012-01-01", null);
      Date local = Dates.parseLocal("yyyy-MM-dd", "2012-01-01", null);
      assertTrue(utc.before(local));
   }


   @Test
   public void testParseUTC()
   {
      Chronology cal = Chronology.create(TimeZone.getTimeZone("UTC"));
      Date date = Dates.parseUTC("yyyy-MM-dd", "2012-01-01", null);
      assertEquals(cal.newDate(2012,1,1), date);
   }

   @Test
   public void testParseUTCInvalidPattern()
   {
      assertNull(Dates.parseUTC("yeas it is that weired", "2012-01-01", null));
   }

   @Test
   public void testParseUTCInvalidDateStr()
   {
      assertNull(Dates.parseUTC("yyyy-MM-dd", "cookies", null));
   }

   @Test
   public void testParseUTCEmptyPattern()
   {
      assertNull(Dates.parseUTC("", "2012-01-01", null));
   }

   @Test
   public void testParseUTCEmptyDateStr()
   {
      assertNull(Dates.parseUTC("yyyy-MM-dd", "", null));
   }

   @Test
   public void testParseUTCNullPattern()
   {
      assertNull(Dates.parseUTC(null, "2012-01-01", null));
   }

   @Test
   public void testParseUTCNulldateStr()
   {
      assertNull(Dates.parseUTC("yyyy-MM-dd", null, null));
   }




   @Test
   public void testMaxByYear()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1);
      Date two = cal.newDate(2011,1,1);
      Date three = cal.newDate(2013,1,1);
      assertSame(three, Dates.max(one, two, three));
   }

   @Test
   public void testMaxBySecond()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,2);
      Date two = cal.newDate(2012,1,1,0,0,0);
      Date three = cal.newDate(2012,1,1,0,0,1);
      assertSame(one, Dates.max(one, two, three));
   }

   @Test
   public void testMaxSetIsNull()
   {
      assertThrows(NullPointerException.class, ()->{
         Dates.max(null);
      });
   }

   @Test
   public void testMaxElementIsNull()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,2);
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertThrows(NullPointerException.class, ()->{
         Dates.max(one, two, null);
      });
   }

   @Test
   public void testMaxEmptySet()
   {
      assertNull(Dates.max(new Date[0]));
   }


   @Test
   public void testMinByYear()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1);
      Date two = cal.newDate(2011,1,1);
      Date three = cal.newDate(2013,1,1);
      assertSame(two, Dates.min(one, two, three));
   }

   @Test
   public void testMinBySecond()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,2);
      Date two = cal.newDate(2012,1,1,0,0,1);
      Date three = cal.newDate(2012,1,1,0,0,0);
      assertSame(three, Dates.min(one, two, three));
   }

   @Test
   public void testMinSetIsNull()
   {
      assertThrows(NullPointerException.class, ()->{
         Dates.min(null);
      });
   }

   @Test
   public void testMinElementIsNull()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,2);
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertThrows(NullPointerException.class, ()->{
         Dates.min(one, two, null);
      });
   }

   @Test
   public void testMinEmptySet()
   {
      assertNull(Dates.min(new Date[0]));
   }



   @Test
   public void testEqual()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,2);
      Date two = cal.newDate(2012,1,1,0,0,0);
      Date three = cal.newDate(2012,1,1,0,0,0);
      Date four = cal.newDate(2012,1,1,0,0,0);
      assertFalse(Dates.equal(one,two));
      assertFalse(Dates.equal(one,two,three,four));
      assertFalse(Dates.equal(two,three,four,one));
      assertFalse(Dates.equal(two,three,one,four));
      assertTrue(Dates.equal(two,three));
      assertTrue(Dates.equal(two,three,four));
      assertTrue(Dates.equal(four,three,two));
      assertFalse(Dates.equal(two,three,null,four));
      assertFalse(Dates.equal(null,two,three,four));
      assertFalse(Dates.equal(new Date[0]));
      assertTrue(Dates.equal(null,null,null));
   }

   @Test
   public void testEqualNullSet()
   {
      assertThrows(NullPointerException.class, ()->{
         Dates.equal(null);
      });
   }


   @Test
   public void testGreaterThanTrue()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,2);
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertSame(one, Dates.gt(two,one, ""));
   }

   @Test
   public void testGreaterThanFalse()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,0);
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.gt(two, one, "");
      });
   }

   @Test
   public void testGreaterThanNullArg()
   {
      Chronology cal = Chronology.create();
      assertThrows(NullPointerException.class, ()->{
         Dates.gt(cal.newDate(2012,1,1,0,0,0), null, "");
      });
   }

   @Test
   public void testGreaterThanNullMin()
   {
      Chronology cal = Chronology.create();
      assertThrows(NullPointerException.class, ()->{
         Dates.gt(null, cal.newDate(2012,1,1,0,0,0), "");
      });
   }


   @Test
   public void testGreaterThanEqualTrue()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,0);
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertSame(one, Dates.gte(two,one, ""));
   }

   @Test
   public void testGreaterThanEqualFalse()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,0);
      Date two = cal.newDate(2012,1,1,0,0,1);
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.gte(two, one, "");
      });
   }

   @Test
   public void testGreaterThanEqualNullArg()
   {
      Chronology cal = Chronology.create();
      assertThrows(NullPointerException.class, ()->{
         Dates.gte(cal.newDate(2012,1,1,0,0,0), null, "");
      });
   }

   @Test
   public void testGreaterThanEqualNullMin()
   {
      Chronology cal = Chronology.create();
      assertThrows(NullPointerException.class, ()->{
         Dates.gte(null, cal.newDate(2012,1,1,0,0,0), "");
      });
   }


   @Test
   public void testLessThanTrue()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,0);
      Date two = cal.newDate(2012,1,1,0,0,1);
      assertSame(one, Dates.lt(two,one, ""));
   }

   @Test
   public void testLessThanFalse()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,0);
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.lt(two, one, "");
      });
   }

   @Test
   public void testLessThanNullArg()
   {
      Chronology cal = Chronology.create();
      assertThrows(NullPointerException.class, ()->{
         Dates.lt(cal.newDate(2012,1,1,0,0,0), null, "");
      });
   }

   @Test
   public void testLessThanNullMax()
   {
      Chronology cal = Chronology.create();
      assertThrows(NullPointerException.class, ()->{
         Dates.lt(null, cal.newDate(2012,1,1,0,0,0), "");
      });
   }

   @Test
   public void testLessThanEqualTrue()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,0);
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertSame(one, Dates.lte(two,one, ""));
   }

   @Test
   public void testLessThanEqualFalse()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,1);
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.lte(two, one, "");
      });
   }

   @Test
   public void testLessThanEqualNullArg()
   {
      Chronology cal = Chronology.create();
      Date two = cal.newDate(2012,1,1,0,0,0);
      assertThrows(NullPointerException.class, ()->{
         Dates.lte(two, null, "");
      });
   }

   @Test
   public void testLessThanEqualNullMax()
   {
      Chronology cal = Chronology.create();
      Date one = cal.newDate(2012,1,1,0,0,1);
      assertThrows(NullPointerException.class, ()->{
         Dates.lte(null, one, "");
      });
   }




   @Test
   public void testBetween()
   {
      Chronology cal = Chronology.create();
      Date lower = cal.newDate(2012,1,1);
      Date upper = cal.newDate(2013,1,1);
      assertNotNull(Dates.between(lower, upper, cal.newDate(2012,1,2), ""));
      assertNotNull(Dates.between(lower, upper, cal.newDate(2012,6,1), ""));
      assertNotNull(Dates.between(lower, upper, cal.newDate(2012,12,31), ""));
   }

   @Test
   public void testBetweenFailLowerBound()
   {
      Chronology cal = Chronology.create();
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.between(cal.newDate(2012,1,1), cal.newDate(2013,1,1), cal.newDate(2012,1,1), "");
      });
   }

   @Test
   public void testBetweenFailUpperBound()
   {
      Chronology cal = Chronology.create();
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.between(cal.newDate(2012,1,1), cal.newDate(2013,1,1), cal.newDate(2013,1,1), "");
      });
   }



   @Test
   public void testWithin()
   {
      Chronology cal = Chronology.create();
      Date lower = cal.newDate(2012,1,1);
      Date upper = cal.newDate(2013,1,1);
      assertNotNull(Dates.within(lower, upper, cal.newDate(2012,1,1), ""));
      assertNotNull(Dates.within(lower, upper, cal.newDate(2012,6,1), ""));
      assertNotNull(Dates.within(lower, upper, cal.newDate(2013,1,1), ""));
   }

   @Test
   public void testWithinFailLowerBound()
   {
      Chronology cal = Chronology.create();
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.within(cal.newDate(2012,1,1), cal.newDate(2013,1,1), cal.newDate(2011,12,31), "");
      });
   }

   @Test
   public void testWithinFailUpperBound()
   {
      Chronology cal = Chronology.create();
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.within(cal.newDate(2012,1,1), cal.newDate(2013,1,1), cal.newDate(2013,1,2), "");
      });
   }



   @Test
   public void testContains()
   {
      Chronology cal = Chronology.create();
      Date lower = cal.newDate(2012,1,1);
      Date upper = cal.newDate(2013,1,1);
      assertNotNull(Dates.contains(lower, upper, cal.newDate(2012,1,1), ""));
      assertNotNull(Dates.contains(lower, upper, cal.newDate(2012,6,1), ""));
      assertNotNull(Dates.contains(lower, upper, cal.newDate(2012,12,31), ""));
   }

   @Test
   public void testContainsFailLowerBound()
   {
      Chronology cal = Chronology.create();
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.contains(cal.newDate(2012,1,1), cal.newDate(2013,1,1), cal.newDate(2011,12,31), "");
      });
   }

   @Test
   public void testContainsFailUpperBound()
   {
      Chronology cal = Chronology.create();
      assertThrows(IllegalArgumentException.class, ()->{
         Dates.contains(cal.newDate(2012,1,1), cal.newDate(2013,1,1), cal.newDate(2013,1,1), "");
      });
   }
}
