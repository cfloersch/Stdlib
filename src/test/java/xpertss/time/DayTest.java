package xpertss.time;

import org.junit.Test;
import xpertss.time.Day;

import java.util.Calendar;

import static junit.framework.Assert.assertSame;

/**
 * Created with IntelliJ IDEA.
 * User: cfloersch
 * Date: 12/14/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class DayTest {


   @Test
   public void testPlus()
   {
      assertSame(Day.Wednesday, Day.Sunday.plus(-4));
      assertSame(Day.Thursday, Day.Sunday.plus(-3));
      assertSame(Day.Friday, Day.Sunday.plus(-2));
      assertSame(Day.Saturday, Day.Sunday.plus(-1));
      assertSame(Day.Sunday, Day.Sunday.plus(0));
      assertSame(Day.Monday, Day.Sunday.plus(1));
      assertSame(Day.Tuesday, Day.Sunday.plus(2));
      assertSame(Day.Wednesday, Day.Sunday.plus(3));
      assertSame(Day.Thursday, Day.Sunday.plus(4));


      assertSame(Day.Sunday, Day.Sunday.plus(-7));
      assertSame(Day.Monday, Day.Monday.plus(7));
      assertSame(Day.Tuesday, Day.Tuesday.plus(14));
      assertSame(Day.Wednesday, Day.Wednesday.plus(21));
      assertSame(Day.Thursday, Day.Thursday.plus(28));
      assertSame(Day.Friday, Day.Friday.plus(35));
      assertSame(Day.Saturday, Day.Saturday.plus(-35));
   }

   @Test
   public void testMinus()
   {
      assertSame(Day.Thursday, Day.Sunday.minus(-4));
      assertSame(Day.Wednesday, Day.Sunday.minus(-3));
      assertSame(Day.Tuesday, Day.Sunday.minus(-2));
      assertSame(Day.Monday, Day.Sunday.minus(-1));
      assertSame(Day.Sunday, Day.Sunday.minus(0));
      assertSame(Day.Saturday, Day.Sunday.minus(1));
      assertSame(Day.Friday, Day.Sunday.minus(2));
      assertSame(Day.Thursday, Day.Sunday.minus(3));
      assertSame(Day.Wednesday, Day.Sunday.minus(4));

      assertSame(Day.Sunday, Day.Sunday.minus(-7));
      assertSame(Day.Monday, Day.Monday.minus(7));
      assertSame(Day.Tuesday, Day.Tuesday.minus(14));
      assertSame(Day.Wednesday, Day.Wednesday.minus(21));
      assertSame(Day.Thursday, Day.Thursday.minus(28));
      assertSame(Day.Friday, Day.Friday.minus(35));
      assertSame(Day.Saturday, Day.Saturday.minus(-35));
   }

   @Test
   public void testValueOf()
   {
      assertSame(Day.Sunday, Day.valueOf(Calendar.SUNDAY));
      assertSame(Day.Monday, Day.valueOf(Calendar.MONDAY));
      assertSame(Day.Tuesday, Day.valueOf(Calendar.TUESDAY));
      assertSame(Day.Wednesday, Day.valueOf(Calendar.WEDNESDAY));
      assertSame(Day.Thursday, Day.valueOf(Calendar.THURSDAY));
      assertSame(Day.Friday, Day.valueOf(Calendar.FRIDAY));
      assertSame(Day.Saturday, Day.valueOf(Calendar.SATURDAY));
   }

   @Test (expected = IllegalArgumentException.class)
   public void testValueOfFailLower()
   {
      Day.valueOf(0);
   }

   @Test (expected = IllegalArgumentException.class)
   public void testValueOfFailUpper()
   {
      Day.valueOf(Day.values().length + 1);
   }

}
