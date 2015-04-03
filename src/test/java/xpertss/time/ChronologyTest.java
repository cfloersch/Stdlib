package xpertss.time;

import org.junit.Test;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * User: cfloersch
 * Date: 12/17/12
 * Time: 5:24 PM
 */
public class ChronologyTest {

   private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS G");

   @Test
   public void testCreate()
   {
      assertEquals("2013-01-01 00:00:00.000 AD", format.format(Chronology.create().newDate(2013, 1, 1)));
      assertEquals("2013-01-02 00:00:00.000 AD", format.format(Chronology.create().newDate(2013, 1, 2)));
      assertEquals("2013-01-12 00:00:00.000 AD", format.format(Chronology.create().newDate(2013, 1, 12)));
      assertEquals("2013-01-31 00:00:00.000 AD", format.format(Chronology.create().newDate(2013, 1, 31)));
      assertEquals("2013-01-01 00:00:00.000 AD", format.format(Chronology.create().newDate(2013, 1)));
   }

   @Test
   public void testIsLeapYear()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isLeapYear(cal.newDate(1600, 1, 1)));
      assertFalse(cal.isLeapYear(cal.newDate(1700, 1, 1)));
      assertFalse(cal.isLeapYear(cal.newDate(1800, 1, 1)));
      assertFalse(cal.isLeapYear(cal.newDate(1900, 1, 1)));
      assertTrue(cal.isLeapYear(cal.newDate(2000, 1, 1)));
      assertFalse(cal.isLeapYear(cal.newDate(2001, 1, 1)));
      assertFalse(cal.isLeapYear(cal.newDate(2100, 1, 1)));
   }

   @Test(expected = NullPointerException.class)
   public void testIsLeapYearNullDate()
   {
      Chronology cal = Chronology.create();
      cal.isLeapYear(null);
   }

   @Test
   public void testIsModernEra()
      throws Exception
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isModernEra(format.parse("2000-01-01 00:00:00.000 AD")));
      assertFalse(cal.isModernEra(format.parse("2000-01-01 00:00:00.000 BC")));
   }

   @Test(expected = NullPointerException.class)
   public void testIsModernEraNullDate()
   {
      Chronology cal = Chronology.create();
      cal.isModernEra(null);
   }


   @Test
   public void testIsFirstDayOfWeek()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isFirstDayOfWeek(cal.newDate(2012, 12, 2)));
      assertTrue(cal.isFirstDayOfWeek(cal.newDate(2012, 12, 9)));
      assertTrue(cal.isFirstDayOfWeek(cal.newDate(2012, 12, 16)));
      assertTrue(cal.isFirstDayOfWeek(cal.newDate(2012, 12, 23)));
      assertTrue(cal.isFirstDayOfWeek(cal.newDate(2012, 12, 30)));
      assertFalse(cal.isFirstDayOfWeek(cal.newDate(2012, 12, 31)));
      assertFalse(cal.isFirstDayOfWeek(cal.newDate(2012, 12, 1)));
   }

   @Test(expected = NullPointerException.class)
   public void testIsFirstDayOfWeekNullDate()
   {
      Chronology cal = Chronology.create();
      cal.isFirstDayOfWeek(null);
   }


   @Test
   public void testIsLastDayOfWeek()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isLastDayOfWeek(cal.newDate(2012, 12, 1)));
      assertTrue(cal.isLastDayOfWeek(cal.newDate(2012, 12, 8)));
      assertTrue(cal.isLastDayOfWeek(cal.newDate(2012, 12, 15)));
      assertTrue(cal.isLastDayOfWeek(cal.newDate(2012, 12, 22)));
      assertTrue(cal.isLastDayOfWeek(cal.newDate(2012, 12, 29)));
      assertFalse(cal.isLastDayOfWeek(cal.newDate(2012, 12, 30)));
      assertFalse(cal.isLastDayOfWeek(cal.newDate(2012, 12, 2)));
      assertFalse(cal.isLastDayOfWeek(cal.newDate(2012, 12, 27)));
   }

   @Test(expected = NullPointerException.class)
   public void testIsLastDayOfWeekNullDate()
   {
      Chronology cal = Chronology.create();
      cal.isLastDayOfWeek(null);
   }

   @Test
   public void testIsLastDayOfMonth()
   {
      Chronology cal = Chronology.create();
      assertFalse(cal.isLastDayOfMonth(cal.newDate(2012, 12, 30)));
      assertFalse(cal.isLastDayOfMonth(cal.newDate(2012, 2, 28)));
      assertFalse(cal.isLastDayOfMonth(cal.newDate(2012, 3, 1)));

      assertTrue(cal.isLastDayOfMonth(cal.newDate(2011, 2, 28)));

      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 1, 31)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 2, 29)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 3, 31)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 4, 30)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 5, 31)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 6, 30)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 7, 31)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 8, 31)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 9, 30)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 10, 31)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 11, 30)));
      assertTrue(cal.isLastDayOfMonth(cal.newDate(2012, 12, 31)));
   }

   @Test(expected = NullPointerException.class)
   public void testIsLastDayOfMonthNullDate()
   {
      Chronology cal = Chronology.create();
      cal.isLastDayOfMonth(null);
   }

   @Test
   public void testGetDayOfWeek()
   {
      Chronology cal = Chronology.create();
      assertEquals(Day.Sunday, cal.getDayOfWeek(cal.newDate(2012, 12, 16)));
      assertEquals(Day.Monday, cal.getDayOfWeek(cal.newDate(2012, 12, 17)));
      assertEquals(Day.Tuesday, cal.getDayOfWeek(cal.newDate(2012, 12, 18)));
      assertEquals(Day.Wednesday, cal.getDayOfWeek(cal.newDate(2012, 12, 19)));
      assertEquals(Day.Thursday, cal.getDayOfWeek(cal.newDate(2012, 12, 20)));
      assertEquals(Day.Friday, cal.getDayOfWeek(cal.newDate(2012, 12, 21)));
      assertEquals(Day.Saturday, cal.getDayOfWeek(cal.newDate(2012, 12, 22)));
      assertEquals(Day.Sunday, cal.getDayOfWeek(cal.newDate(2012, 12, 23)));
   }

   @Test(expected = NullPointerException.class)
   public void testGetDayOfWeekNullDate()
   {
      Chronology cal = Chronology.create();
      cal.getDayOfWeek(null);
   }

   @Test
   public void testGetDayOfYear()
   {
      Chronology cal = Chronology.create();
      assertEquals(1, cal.getDayOfYear(cal.newDate(2012, 1, 1)));
      assertEquals(61, cal.getDayOfYear(cal.newDate(2012, 3, 1)));
      assertEquals(366, cal.getDayOfYear(cal.newDate(2012, 12, 31)));
   }

   @Test(expected = NullPointerException.class)
   public void testGetDayOfYearNullDate()
   {
      Chronology cal = Chronology.create();
      cal.getDayOfYear(null);
   }

   @Test
   public void testGetWeekOfMonth()
   {
      Chronology cal = Chronology.create();
      assertEquals(6, cal.getWeekOfMonth(cal.newDate(2012, 12, 31)));
      assertEquals(2, cal.getWeekOfMonth(cal.newDate(2012, 12, 8)));
      assertEquals(1, cal.getWeekOfMonth(cal.newDate(2012, 12, 1)));
   }


   @Test(expected = NullPointerException.class)
   public void testGetWeekOfMonthNullDate()
   {
      Chronology cal = Chronology.create();
      cal.getWeekOfMonth(null);
   }


   @Test
   public void testGetWeekOfYear()
   {
      Chronology cal = Chronology.create();
      assertEquals(1, cal.getWeekOfYear(cal.newDate(2012, 1, 1)));
      assertEquals(2, cal.getWeekOfYear(cal.newDate(2012, 1, 8)));
      assertEquals(5, cal.getWeekOfYear(cal.newDate(2012, 2, 1)));
      assertEquals(6, cal.getWeekOfYear(cal.newDate(2012, 2, 5)));
   }

   @Test(expected = NullPointerException.class)
   public void testGetWeekOfYearNullDate()
   {
      Chronology cal = Chronology.create();
      cal.getWeekOfYear(null);
   }


   @Test
   public void testGetMonthOfYear()
   {
      Chronology cal = Chronology.create();
      assertEquals(Month.January, cal.getMonthOfYear(cal.newDate(2012, 1, 1)));
      assertEquals(Month.January, cal.getMonthOfYear(cal.newDate(2012, 1, 8)));
      assertEquals(Month.February, cal.getMonthOfYear(cal.newDate(2012, 2, 1)));
      assertEquals(Month.February, cal.getMonthOfYear(cal.newDate(2012, 2, 5)));
      assertEquals(Month.December, cal.getMonthOfYear(cal.newDate(2012, 12, 5)));
   }

   @Test(expected = NullPointerException.class)
   public void testGetMonthOfYearNullDate()
   {
      Chronology cal = Chronology.create();
      cal.getMonthOfYear(null);
   }


   @Test
   public void testAddYears()
   {
      Chronology cal = Chronology.create();
      Date twentyEighth = cal.add(cal.newDate(2012, 2, 27, 0, 0, 0, 0), 1, DateUnit.Years);
      assertEquals("2013-02-27 00:00:00.000 AD", format.format(twentyEighth));
      Date twentyNinth = cal.add(twentyEighth, 1, DateUnit.Years);
      assertEquals("2014-02-27 00:00:00.000 AD", format.format(twentyNinth));
      Date first = cal.add(twentyNinth, 1, DateUnit.Years);
      assertEquals("2015-02-27 00:00:00.000 AD", format.format(first));
      Date back = cal.add(first, -1, DateUnit.Years);
      assertEquals("2014-02-27 00:00:00.000 AD", format.format(back));
   }

   @Test
   public void testAddMonths()
   {
      Chronology cal = Chronology.create();
      Date twentyEighth = cal.add(cal.newDate(2012, 2, 29, 0, 0, 0, 0), 1, DateUnit.Months);
      assertEquals("2012-03-29 00:00:00.000 AD", format.format(twentyEighth));
      Date twentyNinth = cal.add(twentyEighth, 1, DateUnit.Months);
      assertEquals("2012-04-29 00:00:00.000 AD", format.format(twentyNinth));
      Date first = cal.add(twentyNinth, 1, DateUnit.Months);
      assertEquals("2012-05-29 00:00:00.000 AD", format.format(first));
      Date back = cal.add(first, -1, DateUnit.Months);
      assertEquals("2012-04-29 00:00:00.000 AD", format.format(back));

      Date endOfMonth = cal.add(cal.newDate(2012, 3, 31, 0, 0, 0, 0), 1, DateUnit.Months);
      assertEquals("2012-04-30 00:00:00.000 AD", format.format(endOfMonth));
   }


   @Test
   public void testAddWeeks()
   {
      Chronology cal = Chronology.create();
      Date base = cal.newDate(2014, 8, 2);

      Date one = cal.add(base, 1, DateUnit.Weeks);
      assertEquals("2014-08-09 00:00:00.000 AD", format.format(one));
      Date two = cal.add(one, 1, DateUnit.Weeks);
      assertEquals("2014-08-16 00:00:00.000 AD", format.format(two));
      Date three = cal.add(two, 1, DateUnit.Weeks);
      assertEquals("2014-08-23 00:00:00.000 AD", format.format(three));
      Date four = cal.add(three, 2, DateUnit.Weeks);
      assertEquals("2014-09-06 00:00:00.000 AD", format.format(four));
   }

   @Test
   public void testAddDays()
   {
      Chronology cal = Chronology.create();
      Date twentyEighth = cal.add(cal.newDate(2012, 2, 27, 0, 0, 0, 0), 1, DateUnit.Days);
      assertEquals("2012-02-28 00:00:00.000 AD", format.format(twentyEighth));
      Date twentyNinth = cal.add(twentyEighth, 1, DateUnit.Days);
      assertEquals("2012-02-29 00:00:00.000 AD", format.format(twentyNinth));
      Date first = cal.add(twentyNinth, 1, DateUnit.Days);
      assertEquals("2012-03-01 00:00:00.000 AD", format.format(first));
   }

   @Test
   public void testAddHours()
   {
      Chronology cal = Chronology.create();
      Date twentyfirst = cal.add(cal.newDate(2012, 2, 28, 20, 0, 0, 0), 1, DateUnit.Hours);
      assertEquals("2012-02-28 21:00:00.000 AD", format.format(twentyfirst));
      Date twentySecond = cal.add(twentyfirst, 1, DateUnit.Hours);
      assertEquals("2012-02-28 22:00:00.000 AD", format.format(twentySecond));
      Date twentyThird = cal.add(twentySecond, 1, DateUnit.Hours);
      assertEquals("2012-02-28 23:00:00.000 AD", format.format(twentyThird));
      Date nextday = cal.add(twentyThird, 1, DateUnit.Hours);
      assertEquals("2012-02-29 00:00:00.000 AD", format.format(nextday));
   }

   @Test
   public void testAddMinutes()
   {
      Chronology cal = Chronology.create();
      Date thirty = cal.add(cal.newDate(2012, 2, 28, 23, 15, 0, 0), 15, DateUnit.Minutes);
      assertEquals("2012-02-28 23:30:00.000 AD", format.format(thirty));
      Date fortyfive = cal.add(thirty, 15, DateUnit.Minutes);
      assertEquals("2012-02-28 23:45:00.000 AD", format.format(fortyfive));
      Date nextday = cal.add(fortyfive, 15, DateUnit.Minutes);
      assertEquals("2012-02-29 00:00:00.000 AD", format.format(nextday));
   }

   @Test
   public void testAddSeconds()
   {
      Chronology cal = Chronology.create();
      Date thirty = cal.add(cal.newDate(2012, 2, 28, 23, 58, 0, 0), 65, DateUnit.Seconds);
      assertEquals("2012-02-28 23:59:05.000 AD", format.format(thirty));
      Date fortyfive = cal.add(thirty, 45, DateUnit.Seconds);
      assertEquals("2012-02-28 23:59:50.000 AD", format.format(fortyfive));
      Date nextday = cal.add(fortyfive, 15, DateUnit.Seconds);
      assertEquals("2012-02-29 00:00:05.000 AD", format.format(nextday));
   }

   @Test
   public void testAddMillis()
   {
      Chronology cal = Chronology.create();
      Date thirty = cal.add(cal.newDate(2012, 2, 28, 23, 59, 50, 0), 1500, DateUnit.Milliseconds);
      assertEquals("2012-02-28 23:59:51.500 AD", format.format(thirty));
      Date fortyfive = cal.add(thirty, 500, DateUnit.Milliseconds);
      assertEquals("2012-02-28 23:59:52.000 AD", format.format(fortyfive));
      Date nextday = cal.add(fortyfive, 8100, DateUnit.Milliseconds);
      assertEquals("2012-02-29 00:00:00.100 AD", format.format(nextday));
   }

   @Test(expected = NullPointerException.class)
   public void testAddNullDate()
   {
      Chronology cal = Chronology.create();
      cal.add(null, 1500, DateUnit.Milliseconds);
   }

   @Test(expected = NullPointerException.class)
   public void testAddNullUnit()
   {
      Chronology cal = Chronology.create();
      cal.add(cal.newDate(2012, 2, 28, 23, 59, 50, 0), 1500, null);
   }














   /*
    * Its conclusive. Including WeekOfMonth in truncate results in us rolling back the date
    * a week when that's not what we want. This is especially true when the input date is in
    * the first week of the month.
    */

   @Test
   public void testTruncateYear()
      throws Exception
   {
      Chronology cal = Chronology.create();
      Date trimDate = cal.truncate(cal.newDate(2012, 10, 22, 12, 31, 44, 432), DateField.Year);
      assertEquals("2012-01-01 00:00:00.000 AD", format.format(trimDate));
   }


   @Test
   public void testTruncateMonth()
      throws Exception
   {
      Chronology cal = Chronology.create();
      Date trimDate = cal.truncate(cal.newDate(2012, 10, 22, 12, 31, 44, 432), DateField.Month);
      assertEquals("2012-10-01 00:00:00.000 AD", format.format(trimDate));
   }

   @Test
   public void testTruncateDay()
      throws Exception
   {
      Chronology cal = Chronology.create();
      Date trimDate = cal.truncate(cal.newDate(2012, 10, 22, 12, 31, 44, 432), DateField.Day);
      assertEquals("2012-10-22 00:00:00.000 AD", format.format(trimDate));
   }

   @Test
   public void testTruncateHour()
      throws Exception
   {
      Chronology cal = Chronology.create();
      Date trimDate = cal.truncate(cal.newDate(2012, 10, 22, 12, 31, 44, 432), DateField.Hour);
      assertEquals("2012-10-22 12:00:00.000 AD", format.format(trimDate));
   }

   @Test
   public void testTruncateMinute()
      throws Exception
   {
      Chronology cal = Chronology.create();
      Date trimDate = cal.truncate(cal.newDate(2012, 10, 22, 12, 31, 44, 432), DateField.Minute);
      assertEquals("2012-10-22 12:31:00.000 AD", format.format(trimDate));
   }

   @Test
   public void testTruncateSecond()
      throws Exception
   {
      Chronology cal = Chronology.create();
      Date trimDate = cal.truncate(cal.newDate(2012, 10, 22, 12, 31, 44, 432), DateField.Second);
      assertEquals("2012-10-22 12:31:44.000 AD", format.format(trimDate));
   }

   @Test(expected = NullPointerException.class)
   public void testTruncateNullDate()
      throws Exception
   {
      Chronology cal = Chronology.create();
      cal.truncate(null, DateField.Second);
   }

   @Test(expected = NullPointerException.class)
   public void testTruncateNullField()
      throws Exception
   {
      Chronology cal = Chronology.create();
      cal.truncate(cal.newDate(2012, 10, 22, 12, 31, 44, 432), null);
   }






   @Test
   public void testIsSameYear()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 30, 500), DateField.Year));
      assertFalse(cal.isSame(cal.newDate(2011, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 30, 500), DateField.Year));
   }

   @Test
   public void testIsSameMonth()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 29, 12, 15, 30, 500), DateField.Month));
      assertFalse(cal.isSame(cal.newDate(2012, 3, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 30, 500), DateField.Month));
   }


   @Test
   public void testIsSameDay()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 30, 500), DateField.Day));
      assertTrue(cal.isSame(cal.newDate(2012, 2, 29, 12, 15, 30, 500), cal.newDate(2012, 2, 29, 12, 15, 30, 500), DateField.Day));

      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 27, 12, 15, 30, 500), DateField.Day));
      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 29, 12, 15, 30, 500), DateField.Day));
   }

   @Test
   public void testIsSameHour()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 0, 30, 500), DateField.Hour));
      assertTrue(cal.isSame(cal.newDate(2012, 2, 29, 12, 15, 30, 500), cal.newDate(2012, 2, 29, 12, 59, 30, 500), DateField.Hour));

      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 11, 59, 30, 500), DateField.Hour));
      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 13, 0, 30, 500), DateField.Hour));
   }

   @Test
   public void testIsSameMinute()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 0, 500), DateField.Minute));
      assertTrue(cal.isSame(cal.newDate(2012, 2, 29, 12, 15, 30, 500), cal.newDate(2012, 2, 29, 12, 15, 59, 500), DateField.Minute));

      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 14, 30, 500), DateField.Minute));
      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 16, 30, 500), DateField.Minute));
   }

   @Test
   public void testIsSameSecond()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 30, 0), DateField.Second));
      assertTrue(cal.isSame(cal.newDate(2012, 2, 29, 12, 15, 30, 500), cal.newDate(2012, 2, 29, 12, 15, 30, 999), DateField.Second));

      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 29, 999), DateField.Second));
      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 31, 0), DateField.Second));
   }

   @Test
   public void testIsSameMilliSecond()
   {
      Chronology cal = Chronology.create();
      assertTrue(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 0), cal.newDate(2012, 2, 28, 12, 15, 30, 0), DateField.Millisecond));
      assertTrue(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 30, 500), DateField.Millisecond));
      assertTrue(cal.isSame(cal.newDate(2012, 2, 29, 12, 15, 30, 999), cal.newDate(2012, 2, 29, 12, 15, 30, 999), DateField.Millisecond));

      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 0), cal.newDate(2012, 2, 28, 12, 15, 30, 999), DateField.Millisecond));
      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 500), cal.newDate(2012, 2, 28, 12, 15, 30, 501), DateField.Millisecond));
      assertFalse(cal.isSame(cal.newDate(2012, 2, 28, 12, 15, 30, 999), cal.newDate(2012, 2, 28, 12, 15, 30, 0), DateField.Millisecond));
   }

   @Test(expected = NullPointerException.class)
   public void testIsSameNullDateOne()
   {
      Chronology cal = Chronology.create();
      cal.isSame(null, cal.newDate(2012, 2), DateField.Year);
   }

   @Test(expected = NullPointerException.class)
   public void testIsSameNullDateTwo()
   {
      Chronology cal = Chronology.create();
      cal.isSame(cal.newDate(2012, 2), null, DateField.Year);
   }

   @Test(expected = NullPointerException.class)
   public void testIsSameNullDateField()
   {
      Chronology cal = Chronology.create();
      cal.isSame(cal.newDate(2012, 2), cal.newDate(2012, 3), null);
   }












   @Test(expected = NullPointerException.class)
   public void testRoundDateNull()
   {
      Chronology cal = Chronology.create();
      cal.round(null, DateField.Year, RoundingMode.HALF_DOWN);
   }

   @Test(expected = NullPointerException.class)
   public void testRoundUnitNull()
   {
      Chronology cal = Chronology.create();
      cal.round(new Date(), null, RoundingMode.HALF_DOWN);
   }

   @Test(expected = NullPointerException.class)
   public void testRoundModeNull()
   {
      Chronology cal = Chronology.create();
      cal.round(new Date(), DateField.Year, null);
   }


   @Test(expected = ArithmeticException.class)
   public void testRoundUnnecessaryErrorMillis()
      throws Exception
   {
      Chronology cal = Chronology.create();

      Date testDate = cal.newDate(1973, 1, 1, 0, 0, 0, 1);
      cal.round(testDate, DateField.Year, RoundingMode.UNNECESSARY);
   }

   @Test(expected = ArithmeticException.class)
   public void testRoundUnnecessaryErrorDays()
      throws Exception
   {
      Chronology cal = Chronology.create();

      Date testDate = cal.newDate(1973, 1, 22, 0, 0, 0, 0);
      cal.round(testDate, DateField.Year, RoundingMode.UNNECESSARY);
   }







   @Test
   public void testRoundUp()
   {
      Chronology cal = Chronology.create();
      Date testDate = cal.newDate(1973, 1, 22, 12, 30, 30, 500);

      // Millisecond
      Date millis = cal.round(testDate, DateField.Millisecond, RoundingMode.UP);
      assertEquals(testDate, millis);

      // Seconds
      Date seconds = cal.round(testDate, DateField.Second, RoundingMode.UP);
      assertEquals(cal.newDate(1973, 1, 22, 12, 30, 31, 0), seconds);

      // Minutes
      Date minutes = cal.round(testDate, DateField.Minute, RoundingMode.UP);
      assertEquals(cal.newDate(1973, 1, 22, 12, 31, 0, 0), minutes);

      // Hours
      Date hours = cal.round(testDate, DateField.Hour, RoundingMode.UP);
      assertEquals(cal.newDate(1973, 1, 22, 13, 0, 0, 0), hours);

      // Days
      Date days = cal.round(testDate, DateField.Day, RoundingMode.UP);
      assertEquals(cal.newDate(1973, 1, 23, 0, 0, 0, 0), days);

      // Months
      Date months = cal.round(testDate, DateField.Month, RoundingMode.UP);
      assertEquals(cal.newDate(1973, 2, 1, 0, 0, 0, 0), months);

      // Years
      Date years = cal.round(testDate, DateField.Year, RoundingMode.UP);
      assertEquals(cal.newDate(1974, 1, 1, 0, 0, 0, 0), years);

   }

   @Test
   public void testRoundDown()
   {
      Chronology cal = Chronology.create();
      Date testDate = cal.newDate(1973, 1, 22, 12, 30, 30, 500);

      // Millisecond
      Date millis = cal.round(testDate, DateField.Millisecond, RoundingMode.DOWN);
      assertEquals(testDate, millis);

      // Seconds
      Date seconds = cal.round(testDate, DateField.Second, RoundingMode.DOWN);
      assertEquals(cal.newDate(1973, 1, 22, 12, 30, 30, 0), seconds);

      // Minutes
      Date minutes = cal.round(testDate, DateField.Minute, RoundingMode.DOWN);
      assertEquals(cal.newDate(1973, 1, 22, 12, 30, 0, 0), minutes);

      // Hours
      Date hours = cal.round(testDate, DateField.Hour, RoundingMode.DOWN);
      assertEquals(cal.newDate(1973, 1, 22, 12, 0, 0, 0), hours);

      // Days
      Date days = cal.round(testDate, DateField.Day, RoundingMode.DOWN);
      assertEquals(cal.newDate(1973, 1, 22, 0, 0, 0, 0), days);

      // Months
      Date months = cal.round(testDate, DateField.Month, RoundingMode.DOWN);
      assertEquals(cal.newDate(1973, 1, 1, 0, 0, 0, 0), months);

      // Years
      Date years = cal.round(testDate, DateField.Year, RoundingMode.DOWN);
      assertEquals(cal.newDate(1973, 1, 1, 0, 0, 0, 0), years);

   }


   @Test
   public void testRoundCeiling()
   {
      Chronology cal = Chronology.create();
      Date testDate = cal.newDate(1973, 1, 15, 12, 30, 30, 500);

      // Millisecond
      Date millis = cal.round(testDate, DateField.Millisecond, RoundingMode.CEILING);
      assertEquals(testDate, millis);

      // Seconds
      Date seconds = cal.round(testDate, DateField.Second, RoundingMode.CEILING);
      assertEquals(cal.newDate(1973, 1, 15, 12, 30, 31, 0), seconds);

      // Minutes
      Date minutes = cal.round(testDate, DateField.Minute, RoundingMode.CEILING);
      assertEquals(cal.newDate(1973, 1, 15, 12, 31, 0, 0), minutes);

      // Hours
      Date hours = cal.round(testDate, DateField.Hour, RoundingMode.CEILING);
      assertEquals(cal.newDate(1973, 1, 15, 13, 0, 0, 0), hours);

      // Days
      Date days = cal.round(testDate, DateField.Day, RoundingMode.CEILING);
      assertEquals(cal.newDate(1973, 1, 16, 0, 0, 0, 0), days);

      // Months
      Date months = cal.round(testDate, DateField.Month, RoundingMode.CEILING);
      assertEquals(cal.newDate(1973, 2, 1, 0, 0, 0, 0), months);

      // Years
      Date years = cal.round(testDate, DateField.Year, RoundingMode.CEILING);
      assertEquals(cal.newDate(1974, 1, 1, 0, 0, 0, 0), years);

   }


   @Test
   public void testRoundFloor()
   {
      Chronology cal = Chronology.create();
      Date testDate = cal.newDate(1973, 1, 15, 12, 30, 30, 500);

      // Millisecond
      Date millis = cal.round(testDate, DateField.Millisecond, RoundingMode.FLOOR);
      assertEquals(testDate, millis);

      // Seconds
      Date seconds = cal.round(testDate, DateField.Second, RoundingMode.FLOOR);
      assertEquals(cal.newDate(1973, 1, 15, 12, 30, 30, 0), seconds);

      // Minutes
      Date minutes = cal.round(testDate, DateField.Minute, RoundingMode.FLOOR);
      assertEquals(cal.newDate(1973, 1, 15, 12, 30, 0, 0), minutes);

      // Hours
      Date hours = cal.round(testDate, DateField.Hour, RoundingMode.FLOOR);
      assertEquals(cal.newDate(1973, 1, 15, 12, 0, 0, 0), hours);

      // Days
      Date days = cal.round(testDate, DateField.Day, RoundingMode.FLOOR);
      assertEquals(cal.newDate(1973, 1, 15, 0, 0, 0, 0), days);

      // Months
      Date months = cal.round(testDate, DateField.Month, RoundingMode.FLOOR);
      assertEquals(cal.newDate(1973, 1, 1, 0, 0, 0, 0), months);

      // Years
      Date years = cal.round(testDate, DateField.Year, RoundingMode.FLOOR);
      assertEquals(cal.newDate(1973, 1, 1, 0, 0, 0, 0), years);

   }


   @Test
   public void testRoundHalfUp()
   {
      Chronology cal = Chronology.create();
      Date testUp = cal.newDate(1973, 6, 15, 12, 30, 30, 500);

      // Millisecond
      Date millis = cal.round(testUp, DateField.Millisecond, RoundingMode.HALF_UP);
      assertEquals(testUp, millis);

      // Seconds
      Date seconds = cal.round(testUp, DateField.Second, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 6, 15, 12, 30, 31, 0), seconds);

      // Minutes
      Date minutes = cal.round(testUp, DateField.Minute, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 6, 15, 12, 31, 0, 0), minutes);

      // Hours
      Date hours = cal.round(testUp, DateField.Hour, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 6, 15, 13, 0, 0, 0), hours);

      // Days
      Date days = cal.round(testUp, DateField.Day, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 6, 16, 0, 0, 0, 0), days);

      // Months
      Date months = cal.round(testUp, DateField.Month, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 7, 1, 0, 0, 0, 0), months);

      // Years
      Date years = cal.round(testUp, DateField.Year, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1974, 1, 1, 0, 0, 0, 0), years);


      Date testDown = cal.newDate(1973, 5, 14, 11, 29, 29, 499);

      // Millisecond
      millis = cal.round(testDown, DateField.Millisecond, RoundingMode.HALF_UP);
      assertEquals(testDown, millis);

      // Seconds
      seconds = cal.round(testDown, DateField.Second, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 5, 14, 11, 29, 29, 0), seconds);

      // Minutes
      minutes = cal.round(testDown, DateField.Minute, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 5, 14, 11, 29, 0, 0), minutes);

      // Hours
      hours = cal.round(testDown, DateField.Hour, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 5, 14, 11, 0, 0, 0), hours);

      // Days
      days = cal.round(testDown, DateField.Day, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 5, 14, 0, 0, 0, 0), days);

      // Months
      months = cal.round(testDown, DateField.Month, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 5, 1, 0, 0, 0, 0), months);

      // Years
      years = cal.round(testDown, DateField.Year, RoundingMode.HALF_UP);
      assertEquals(cal.newDate(1973, 1, 1, 0, 0, 0, 0), years);
   }


   @Test
   public void testRoundHalfDown()
   {
      Chronology cal = Chronology.create();
      Date testUp = cal.newDate(1973, 6, 15, 12, 30, 30, 501);

      // Millisecond
      Date millis = cal.round(testUp, DateField.Millisecond, RoundingMode.HALF_DOWN);
      assertEquals(testUp, millis);

      // Seconds
      Date seconds = cal.round(testUp, DateField.Second, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 15, 12, 30, 31, 0), seconds);

      // Minutes
      Date minutes = cal.round(testUp, DateField.Minute, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 15, 12, 31, 0, 0), minutes);

      // Hours
      Date hours = cal.round(testUp, DateField.Hour, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 15, 13, 0, 0, 0), hours);

      // Days
      Date days = cal.round(testUp, DateField.Day, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 16, 0, 0, 0, 0), days);

      // Months
      Date months = cal.round(testUp, DateField.Month, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 7, 1, 0, 0, 0, 0), months);

      // Years
      Date years = cal.round(testUp, DateField.Year, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1974, 1, 1, 0, 0, 0, 0), years);


      Date testDown = cal.newDate(1973, 6, 15, 12, 30, 30, 500);

      // Millisecond
      millis = cal.round(testDown, DateField.Millisecond, RoundingMode.HALF_DOWN);
      assertEquals(testDown, millis);

      // Seconds
      seconds = cal.round(testDown, DateField.Second, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 15, 12, 30, 30, 0), seconds);

      // Minutes
      minutes = cal.round(testDown, DateField.Minute, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 15, 12, 30, 0, 0), minutes);

      // Hours
      hours = cal.round(testDown, DateField.Hour, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 15, 12, 0, 0, 0), hours);

      // Days
      days = cal.round(testDown, DateField.Day, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 15, 0, 0, 0, 0), days);

      // Months
      months = cal.round(testDown, DateField.Month, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 6, 1, 0, 0, 0, 0), months);

      // Years
      years = cal.round(testDown, DateField.Year, RoundingMode.HALF_DOWN);
      assertEquals(cal.newDate(1973, 1, 1, 0, 0, 0, 0), years);
   }


   @Test
   public void testRoundHalfEvenUp()
   {
      Chronology cal = Chronology.create();
      Date testUp = cal.newDate(1973, 5, 15, 11, 29, 29, 500);

      // Millisecond
      Date millis = cal.round(testUp, DateField.Millisecond, RoundingMode.HALF_EVEN);
      assertEquals(testUp, millis);

      // Seconds
      Date seconds = cal.round(testUp, DateField.Second, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 5, 15, 11, 29, 30, 0), seconds);

      // Minutes
      Date minutes = cal.round(testUp, DateField.Minute, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 5, 15, 11, 30, 0, 0), minutes);

      // Hours
      Date hours = cal.round(testUp, DateField.Hour, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 5, 15, 12, 0, 0, 0), hours);

      // Days
      Date days = cal.round(testUp, DateField.Day, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 5, 16, 0, 0, 0, 0), days);

      // Months
      Date months = cal.round(testUp, DateField.Month, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 6, 1, 0, 0, 0, 0), months);

      // Years
      Date years = cal.round(testUp, DateField.Year, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1974, 1, 1, 0, 0, 0, 0), years);

   }

   @Test
   public void testRoundHalfEvenDown()
   {
      Chronology cal = Chronology.create();
      Date testUp = cal.newDate(1973, 6, 14, 12, 30, 30, 500);

      // Millisecond
      Date millis = cal.round(testUp, DateField.Millisecond, RoundingMode.HALF_EVEN);
      assertEquals(testUp, millis);

      // Seconds
      Date seconds = cal.round(testUp, DateField.Second, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 6, 14, 12, 30, 30, 0), seconds);

      // Minutes
      Date minutes = cal.round(testUp, DateField.Minute, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 6, 14, 12, 30, 0, 0), minutes);

      // Hours
      Date hours = cal.round(testUp, DateField.Hour, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 6, 14, 12, 0, 0, 0), hours);

      // Days
      Date days = cal.round(testUp, DateField.Day, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 6, 14, 0, 0, 0, 0), days);

      // Months
      Date months = cal.round(testUp, DateField.Month, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1973, 6, 1, 0, 0, 0, 0), months);

      // Years
      Date years = cal.round(testUp, DateField.Year, RoundingMode.HALF_EVEN);
      assertEquals(cal.newDate(1974, 1, 1, 0, 0, 0, 0), years);

   }







   @Test
   public void testBetweenMillis()
   {
      Chronology cal = Chronology.create();
      Date base = cal.newDate(1973, 6, 14, 12, 30, 30, 500);

      // 500 millis smaller
      assertEquals(-500, cal.between(base, cal.newDate(1973, 6, 14, 12, 30, 30, 0), DateUnit.Milliseconds));

      // 500 millis larger
      assertEquals(500, cal.between(base, cal.newDate(1973, 6, 14, 12, 30, 31, 0), DateUnit.Milliseconds));

      // 3 hours larger
      assertEquals(10800000, cal.between(base, cal.newDate(1973, 6, 14, 15, 30, 30, 500), DateUnit.Milliseconds));

      // 3 days larger
      assertEquals(259200000, cal.between(base, cal.newDate(1973, 6, 17, 12, 30, 30, 500), DateUnit.Milliseconds));
   }






   @Test
   public void testBetweenHours()
   {
      Chronology cal = Chronology.create();
      Date base = cal.newDate(2014, 8, 8);    // midnight Aug 8th, 2014
      assertEquals(1, cal.between(base, cal.newDate(2014, 8, 8, 1), DateUnit.Hours));
      assertEquals(12, cal.between(base, cal.newDate(2014, 8, 8, 12), DateUnit.Hours));
      assertEquals(24, cal.between(base, cal.newDate(2014, 8, 9), DateUnit.Hours));
   }

   @Test
   public void testBetweenHoursDayLightSavingsStart()
   {
      Chronology cal = Chronology.create();
      Date before = cal.newDate(2013, 3, 10);   // midnight March 10, 2013
      Date after = cal.newDate(2013, 3, 10, 4);   // 4 AM March 10, 2013

      assertEquals(3, cal.between(before, after, DateUnit.Hours));
      assertEquals(-3, cal.between(after, before, DateUnit.Hours));
   }

   @Test
   public void testBetweenHoursDayLightSavingsEnd()
   {
      Chronology cal = Chronology.create();
      Date before = cal.newDate(2013, 11, 3);   // midnight March 10, 2013
      Date after = cal.newDate(2013, 11, 3, 4);   // 4 AM March 10, 2013

      assertEquals(5, cal.between(before, after, DateUnit.Hours));
      assertEquals(-5, cal.between(after, before, DateUnit.Hours));
   }

   @Test
   public void testBetweenHoursLeapYear()
   {
      Chronology cal = Chronology.create();
      Date before = cal.newDate(2012, 2, 28);   // midnight Feb 28th, 2012
      Date after = cal.newDate(2012, 3, 1);   // midnight Mar 1st, 2012

      assertEquals(48, cal.between(before, after, DateUnit.Hours));
      assertEquals(-48, cal.between(after, before, DateUnit.Hours));
   }






   @Test
   public void testBetweenDays()
   {
      Chronology cal = Chronology.create();

      assertEquals(1, cal.between(cal.newDate(2013, 1, 1), cal.newDate(2013, 1, 2), DateUnit.Days));
      assertEquals(2, cal.between(cal.newDate(2013, 1, 1), cal.newDate(2013, 1, 3), DateUnit.Days));

      assertEquals(31, cal.between(cal.newDate(2013, 1, 1), cal.newDate(2013, 2, 1), DateUnit.Days)); // jan = 31 days
      assertEquals(28, cal.between(cal.newDate(2013, 2, 1), cal.newDate(2013, 3, 1), DateUnit.Days)); // feb = 28 days - non-leap year
      assertEquals(31, cal.between(cal.newDate(2013, 3, 1), cal.newDate(2013, 4, 1), DateUnit.Days)); // mar = 31 days
      assertEquals(30, cal.between(cal.newDate(2013, 4, 1), cal.newDate(2013, 5, 1), DateUnit.Days)); // apr = 30 days
      assertEquals(31, cal.between(cal.newDate(2013, 5, 1), cal.newDate(2013, 6, 1), DateUnit.Days)); // may = 31 days
      assertEquals(30, cal.between(cal.newDate(2013, 6, 1), cal.newDate(2013, 7, 1), DateUnit.Days)); // jun = 30 days

      assertEquals(31, cal.between(cal.newDate(2013, 7, 1), cal.newDate(2013, 8, 1), DateUnit.Days)); // jul = 31 days
      assertEquals(31, cal.between(cal.newDate(2013, 8, 1), cal.newDate(2013, 9, 1), DateUnit.Days)); // aug = 31 days
      assertEquals(30, cal.between(cal.newDate(2013, 9, 1), cal.newDate(2013, 10, 1), DateUnit.Days)); // sept = 30 days
      assertEquals(31, cal.between(cal.newDate(2013, 10, 1), cal.newDate(2013, 11, 1), DateUnit.Days)); // oct = 31 days
      assertEquals(30, cal.between(cal.newDate(2013, 11, 1), cal.newDate(2013, 12, 1), DateUnit.Days)); // nov = 30 days
      assertEquals(31, cal.between(cal.newDate(2013, 12, 1), cal.newDate(2014, 1, 1), DateUnit.Days)); // dec = 31 days
   }

   @Test
   public void testBetweenDaysWithTime()
   {
      Chronology cal = Chronology.create();
      Date base = cal.newDate(2013, 1, 2, 0, 0, 0, 0);    // midnight

      assertEquals(0, cal.between(base, cal.newDate(2013, 1, 2, 2, 0, 0, 0), DateUnit.Days)); // 2 AM
      assertEquals(0, cal.between(base, cal.newDate(2013, 1, 2, 23, 0, 0, 0), DateUnit.Days)); // 11 PM
      assertEquals(0, cal.between(base, cal.newDate(2013, 1, 2, 23, 59, 59, 999), DateUnit.Days)); // 1 milli before midnight

      assertEquals(0, cal.between(cal.newDate(2013, 1, 1, 23, 59, 59, 999), base, DateUnit.Days)); // 1 milli before
      assertEquals(0, cal.between(cal.newDate(2013, 1, 1, 0, 0, 0, 1), base, DateUnit.Days)); // 1 milli shy of full day
   }


   @Test
   public void testBetweenDaysLeapYear()
   {
      Chronology cal = Chronology.create();

      // 2 days that span leap day
      Date before = cal.newDate(2012, 2, 28);   // midnight Feb 28th, 2012
      Date after = cal.newDate(2012, 3, 1);   // midnight Mar 1st, 2012
      assertEquals(2, cal.between(before, after, DateUnit.Days));
      assertEquals(-2, cal.between(after, before, DateUnit.Days));

      // full year
      before = cal.newDate(2012, 1, 1);   // midnight Jan 1st, 2012
      after = cal.newDate(2013, 1, 1);   // midnight Jan 1st, 2013
      assertEquals(366, cal.between(before, after, DateUnit.Days));
      assertEquals(-366, cal.between(after, before, DateUnit.Days));

      // 2 years that span leap year
      before = cal.newDate(2011, 1, 1);   // midnight Jan 1st, 2011
      after = cal.newDate(2013, 1, 1);   // midnight Jan 1st, 2013
      assertEquals(731, cal.between(before, after, DateUnit.Days));
      assertEquals(-731, cal.between(after, before, DateUnit.Days));
   }

   @Test
   public void testBetweenDaysSpanEra()
   {
      Chronology cal = Chronology.create();

      Date bc = cal.add(cal.newDate(1, 1, 1), -1, DateUnit.Days);
      Date ad = cal.newDate(1, 1, 1);

      assertEquals(1, cal.between(bc, ad, DateUnit.Days));
      assertEquals(-1, cal.between(ad, bc, DateUnit.Days));
   }


   @Test
   public void testBetweenDaysGregorianEra()
   {
      Chronology cal = Chronology.create();

      Date start = cal.newDate(1582, 10, 15);
      Date augEighth = cal.newDate(2014, 8, 8);

      assertEquals(157717, cal.between(start, augEighth, DateUnit.Days));
   }

   @Test
   public void testBetweenDaysSinceLastJulian()
   {
      Chronology cal = Chronology.create();

      Date start = cal.newDate(1582, 10, 4);
      Date augEighth = cal.newDate(2014, 8, 8);

      long begin = System.nanoTime();
      assertEquals(157718, cal.between(start, augEighth, DateUnit.Days));
      long end = System.nanoTime();
      System.out.println(TimeUnit.NANOSECONDS.toMicros(end - begin) + " micros");
   }

   @Test
   public void testBetweenDaysCommonEra()
   {
      Chronology cal = Chronology.create();

      Date start = cal.newDate(1, 1, 1);
      Date augEighth = cal.newDate(2014, 8, 8);

      long begin = System.nanoTime();
      assertEquals(735454, cal.between(start, augEighth, DateUnit.Days));
      long end = System.nanoTime();
      System.out.println(TimeUnit.NANOSECONDS.toMicros(end - begin) + " micros");
   }

   @Test
   public void testBetweenDaysBeforeCommonEra()
   {
      Chronology cal = Chronology.create();

      Date start = cal.add(cal.add(cal.newDate(1, 1, 1), -3, DateUnit.Years), 55, DateUnit.Days);   // Feb 25th, 3 BC
      Date end = cal.add(cal.newDate(1, 1, 1), -1, DateUnit.Years);                                 // Jan 1, 1 BC

      assertEquals(675, cal.between(start, end, DateUnit.Days));
   }



   @Test
   public void testBetweenMonths()
   {
      Chronology cal = Chronology.create();

      assertEquals(0, cal.between(cal.newDate(2013, 1, 1), cal.newDate(2013, 1, 31), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2013, 1, 1), cal.newDate(2013, 2, 1), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2012, 12, 1), cal.newDate(2013, 1, 1), DateUnit.Months));

      assertEquals(12, cal.between(cal.newDate(2012, 1, 1), cal.newDate(2013, 1, 1), DateUnit.Months));
      assertEquals(24, cal.between(cal.newDate(2011, 1, 1), cal.newDate(2013, 1, 1), DateUnit.Months));


      long begin = System.nanoTime();
      assertEquals(35, cal.between(cal.newDate(2010, 2, 1), cal.newDate(2013, 1, 1), DateUnit.Months));
      long end = System.nanoTime();
      System.out.println(TimeUnit.NANOSECONDS.toMicros(end - begin) + " micros");
   }

   @Test
   public void testBetweenMonthsWithTime()
   {
      Chronology cal = Chronology.create();

      assertEquals(0, cal.between(cal.newDate(2013, 1, 1, 12, 30, 30, 500), cal.newDate(2013, 2, 1, 12, 30, 30, 499), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2013, 1, 1, 12, 30, 30, 500), cal.newDate(2013, 2, 1, 12, 30, 30, 500), DateUnit.Months));

      assertEquals(0, cal.between(cal.newDate(2012, 12, 1, 12, 30, 30, 500), cal.newDate(2013, 1, 1, 12, 30, 30, 499), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2012, 12, 1, 12, 30, 30, 500), cal.newDate(2013, 1, 1, 12, 30, 30, 500), DateUnit.Months));
   }


   @Test
   public void testBetweenMonthsWithLeapYearDay()
   {
      Chronology cal = Chronology.create();

      assertEquals(1, cal.between(cal.newDate(2012, 1, 28), cal.newDate(2012, 2, 28), DateUnit.Months));
      assertEquals(0, cal.between(cal.newDate(2012, 1, 29), cal.newDate(2012, 2, 28), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2012, 1, 29), cal.newDate(2012, 2, 29), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2012, 1, 29), cal.newDate(2012, 3, 1), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2012, 1, 30), cal.newDate(2012, 3, 1), DateUnit.Months));

      assertEquals(1, cal.between(cal.newDate(2012, 4, 30), cal.newDate(2012, 6, 1), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2012, 5, 1), cal.newDate(2012, 6, 1), DateUnit.Months));
      assertEquals(0, cal.between(cal.newDate(2012, 5, 2), cal.newDate(2012, 6, 1), DateUnit.Months));

      // TODO Should these be a month???
      assertEquals(1, cal.between(cal.newDate(2013, 1, 29), cal.newDate(2013, 2, 28), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2013, 1, 30), cal.newDate(2013, 2, 28), DateUnit.Months));
      assertEquals(1, cal.between(cal.newDate(2013, 1, 31), cal.newDate(2013, 2, 28), DateUnit.Months));

   }







   @Test
   public void testBetweenYears()
   {
      Chronology cal = Chronology.create();
      Date base = cal.newDate(1973, 6, 14, 12, 30, 30, 500);

      // 0 years
      assertEquals(0, cal.between(base, cal.newDate(1973, 6, 14, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(0, cal.between(base, cal.newDate(1973, 9, 14, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(0, cal.between(base, cal.newDate(1974, 1, 13, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(0, cal.between(base, cal.newDate(1974, 6, 13, 12, 30, 30, 500), DateUnit.Years));

      // 1 year
      assertEquals(1, cal.between(base, cal.newDate(1974, 6, 14, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(1, cal.between(base, cal.newDate(1974, 9, 14, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(1, cal.between(base, cal.newDate(1975, 1, 13, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(1, cal.between(base, cal.newDate(1975, 6, 13, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(1, cal.between(base, cal.newDate(1975, 6, 14, 12, 30, 30, 499), DateUnit.Years));

      // 2 years
      assertEquals(2, cal.between(base, cal.newDate(1975, 6, 14, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(2, cal.between(base, cal.newDate(1975, 9, 14, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(2, cal.between(base, cal.newDate(1976, 1, 13, 12, 30, 30, 500), DateUnit.Years));


      // Leap Year at end
      // 1976 is a leap year
      assertEquals(2, cal.between(base, cal.newDate(1976, 6, 14, 12, 30, 30, 499), DateUnit.Years));
      assertEquals(3, cal.between(base, cal.newDate(1976, 6, 14, 12, 30, 30, 500), DateUnit.Years));

      // Leap year at beginning
      // 1972 is a leap year
      assertEquals(-1, cal.between(base, cal.newDate(1972, 6, 14, 12, 30, 30, 500), DateUnit.Years));
      assertEquals(-1, cal.between(base, cal.newDate(1971, 6, 14, 12, 30, 30, 501), DateUnit.Years));
      assertEquals(-2, cal.between(base, cal.newDate(1971, 6, 14, 12, 30, 30, 500), DateUnit.Years));

   }

   @Test
   public void testBetweenYearsWithEras()
   {
      Chronology cal = Chronology.create();
      Date threeHundredBC = cal.add(cal.newDate(1, 1, 1, 0, 0, 0, 0), -300, DateUnit.Years);
      Date oneHundredBC = cal.add(cal.newDate(1, 1, 1, 0, 0, 0, 0), -100, DateUnit.Years);
      Date oneHundredAD = cal.newDate(100, 1, 1, 0, 0, 0, 0);
      Date threeHundredAD = cal.newDate(300, 1, 1, 0, 0, 0, 0);

      // 300 BC - 100 BC = 200 years
      assertEquals(200, cal.between(threeHundredBC, oneHundredBC, DateUnit.Years));
      // 100 AD - 300 AD = 200 years
      assertEquals(200, cal.between(oneHundredAD, threeHundredAD, DateUnit.Years));
      // 300 BC - 300 AD = 599 years (no year zero)
      assertEquals(599, cal.between(threeHundredBC, threeHundredAD, DateUnit.Years));
      // 300 AD - 300 BC = -599 years (no year zero)
      assertEquals(-599, cal.between(threeHundredAD, threeHundredBC, DateUnit.Years));

   }


}
