package xpertss.time;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertNotNull;

/**
 * User: cfloersch
 * Date: 12/16/12
 */
public class MonthTest {

   @Test
   public void testQuarter()
   {
      assertEquals(1, Month.January.quarter());
      assertEquals(1, Month.February.quarter());
      assertEquals(1, Month.March.quarter());

      assertEquals(2, Month.April.quarter());
      assertEquals(2, Month.May.quarter());
      assertEquals(2, Month.June.quarter());

      assertEquals(3, Month.July.quarter());
      assertEquals(3, Month.August.quarter());
      assertEquals(3, Month.September.quarter());

      assertEquals(4, Month.October.quarter());
      assertEquals(4, Month.November.quarter());
      assertEquals(4, Month.December.quarter());
   }


   @Test
   public void testFirstMonthOfQuarter()
   {
      assertSame(Month.January, Month.January.firstMonthOfQuarter());
      assertSame(Month.January, Month.February.firstMonthOfQuarter());
      assertSame(Month.January, Month.March.firstMonthOfQuarter());

      assertSame(Month.April, Month.April.firstMonthOfQuarter());
      assertSame(Month.April, Month.May.firstMonthOfQuarter());
      assertSame(Month.April, Month.June.firstMonthOfQuarter());

      assertSame(Month.July, Month.July.firstMonthOfQuarter());
      assertSame(Month.July, Month.August.firstMonthOfQuarter());
      assertSame(Month.July, Month.September.firstMonthOfQuarter());

      assertSame(Month.October, Month.October.firstMonthOfQuarter());
      assertSame(Month.October, Month.November.firstMonthOfQuarter());
      assertSame(Month.October, Month.December.firstMonthOfQuarter());
   }


   @Test
   public void testPlus()
   {
      assertSame(Month.July, Month.January.plus(-6));
      assertSame(Month.August, Month.January.plus(-5));
      assertSame(Month.September, Month.January.plus(-4));
      assertSame(Month.October, Month.January.plus(-3));
      assertSame(Month.November, Month.January.plus(-2));
      assertSame(Month.December, Month.January.plus(-1));
      assertSame(Month.January, Month.January.plus(0));
      assertSame(Month.February, Month.January.plus(1));
      assertSame(Month.March, Month.January.plus(2));
      assertSame(Month.April, Month.January.plus(3));
      assertSame(Month.May, Month.January.plus(4));
      assertSame(Month.June, Month.January.plus(5));
      assertSame(Month.July, Month.January.plus(6));

      assertSame(Month.January, Month.January.plus(-72));
      assertSame(Month.February, Month.February.plus(-60));
      assertSame(Month.March, Month.March.plus(-48));
      assertSame(Month.April, Month.April.plus(-36));
      assertSame(Month.May, Month.May.plus(-24));
      assertSame(Month.June, Month.June.plus(-12));
      assertSame(Month.July, Month.July.plus(12));
      assertSame(Month.August, Month.August.plus(24));
      assertSame(Month.September, Month.September.plus(36));
      assertSame(Month.October, Month.October.plus(48));
      assertSame(Month.November, Month.November.plus(60));
      assertSame(Month.December, Month.December.plus(72));
   }

   @Test
   public void testMinus()
   {
      assertSame(Month.July, Month.January.minus(-6));
      assertSame(Month.June, Month.January.minus(-5));
      assertSame(Month.May, Month.January.minus(-4));
      assertSame(Month.April, Month.January.minus(-3));
      assertSame(Month.March, Month.January.minus(-2));
      assertSame(Month.February, Month.January.minus(-1));
      assertSame(Month.January, Month.January.minus(0));
      assertSame(Month.December, Month.January.minus(1));
      assertSame(Month.November, Month.January.minus(2));
      assertSame(Month.October, Month.January.minus(3));
      assertSame(Month.September, Month.January.minus(4));
      assertSame(Month.August, Month.January.minus(5));
      assertSame(Month.July, Month.January.minus(6));

      assertSame(Month.January, Month.January.minus(-72));
      assertSame(Month.February, Month.February.minus(-60));
      assertSame(Month.March, Month.March.minus(-48));
      assertSame(Month.April, Month.April.minus(-36));
      assertSame(Month.May, Month.May.minus(-24));
      assertSame(Month.June, Month.June.minus(-12));
      assertSame(Month.July, Month.July.minus(12));
      assertSame(Month.August, Month.August.minus(24));
      assertSame(Month.September, Month.September.minus(36));
      assertSame(Month.October, Month.October.minus(48));
      assertSame(Month.November, Month.November.minus(60));
      assertSame(Month.December, Month.December.minus(72));
   }


   @Test
   public void testJanThroughUn()
   {
      for(int i = 1; i <= 12; i++) {
         assertNotNull(Month.valueOf(i));
      }
   }

   @Test (expected = IllegalArgumentException.class)
   public void testNegativeIndex()
   {
      Month.valueOf(-1);
   }

   @Test (expected = IllegalArgumentException.class)
   public void testZeroIndex()
   {
      Month.valueOf(0);
   }

   @Test (expected = IllegalArgumentException.class)
   public void testExcessiveIndex()
   {
      Month.valueOf(13);
   }



   @Test
   public void testJanuraryLength()
   {
      assertEquals(31, Month.January.length(false));
      assertEquals(31, Month.January.length(true));
   }

   @Test
   public void testFebruaryLength()
   {
      assertEquals(28, Month.February.length(false));
      assertEquals(29, Month.February.length(true));
   }

   @Test
   public void testMarchLength()
   {
      assertEquals(31, Month.March.length(false));
      assertEquals(31, Month.March.length(true));
   }

   @Test
   public void testAprilLength()
   {
      assertEquals(30, Month.April.length(false));
      assertEquals(30, Month.April.length(true));
   }

   @Test
   public void testMayLength()
   {
      assertEquals(31, Month.May.length(false));
      assertEquals(31, Month.May.length(true));
   }

   @Test
   public void testJuneLength()
   {
      assertEquals(30, Month.June.length(false));
      assertEquals(30, Month.June.length(true));
   }

   @Test
   public void testJulyLength()
   {
      assertEquals(31, Month.July.length(false));
      assertEquals(31, Month.July.length(true));
   }

   @Test
   public void testAugustLength()
   {
      assertEquals(31, Month.August.length(false));
      assertEquals(31, Month.August.length(true));
   }

   @Test
   public void testSeptemberLength()
   {
      assertEquals(30, Month.September.length(false));
      assertEquals(30, Month.September.length(true));
   }

   @Test
   public void testOctoberLength()
   {
      assertEquals(31, Month.October.length(false));
      assertEquals(31, Month.October.length(true));
   }

   @Test
   public void testNovemberLength()
   {
      assertEquals(30, Month.November.length(false));
      assertEquals(30, Month.November.length(true));
   }

   @Test
   public void testDecemberLength()
   {
      assertEquals(31, Month.December.length(false));
      assertEquals(31, Month.December.length(true));
   }



   @Test
   public void testJanuraryFirstDay()
   {
      assertEquals(1, Month.January.firstDayOfYear(false));
      assertEquals(1, Month.January.firstDayOfYear(true));
   }

   @Test
   public void testFebruaryFirstDay()
   {
      assertEquals(32, Month.February.firstDayOfYear(false));
      assertEquals(32, Month.February.firstDayOfYear(true));
   }

   @Test
   public void testMarchFirstDay()
   {
      assertEquals(60, Month.March.firstDayOfYear(false));
      assertEquals(61, Month.March.firstDayOfYear(true));
   }

   @Test
   public void testAprilFirstDay()
   {
      assertEquals(91, Month.April.firstDayOfYear(false));
      assertEquals(92, Month.April.firstDayOfYear(true));
   }

   @Test
   public void testMayFirstDay()
   {
      assertEquals(121, Month.May.firstDayOfYear(false));
      assertEquals(122, Month.May.firstDayOfYear(true));
   }

   @Test
   public void testJuneFirstDay()
   {
      assertEquals(152, Month.June.firstDayOfYear(false));
      assertEquals(153, Month.June.firstDayOfYear(true));
   }

   @Test
   public void testJulyFirstDay()
   {
      assertEquals(182, Month.July.firstDayOfYear(false));
      assertEquals(183, Month.July.firstDayOfYear(true));
   }

   @Test
   public void testAugustFirstDay()
   {
      assertEquals(213, Month.August.firstDayOfYear(false));
      assertEquals(214, Month.August.firstDayOfYear(true));
   }

   @Test
   public void testSeptemberFirstDay()
   {
      assertEquals(244, Month.September.firstDayOfYear(false));
      assertEquals(245, Month.September.firstDayOfYear(true));
   }

   @Test
   public void testOctoberFirstDay()
   {
      assertEquals(274, Month.October.firstDayOfYear(false));
      assertEquals(275, Month.October.firstDayOfYear(true));
   }

   @Test
   public void testNovemberFirstDay()
   {
      assertEquals(305, Month.November.firstDayOfYear(false));
      assertEquals(306, Month.November.firstDayOfYear(true));
   }

   @Test
   public void testDecemberFirstDay()
   {
      assertEquals(335, Month.December.firstDayOfYear(false));
      assertEquals(336, Month.December.firstDayOfYear(true));
   }
}
