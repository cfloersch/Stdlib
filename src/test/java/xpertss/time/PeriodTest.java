package xpertss.time;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: cfloersch
 * Date: 12/8/12
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class PeriodTest {


   @Test
   public void testConstruction()
   {
      Chronology cal = Chronology.create();
      Date start = cal.newDate(10, 22, 2012);
      Date end = cal.newDate(10, 23, 2012);
      Period range = new Period(start, end);
      assertSame(start, range.getStart());
      assertSame(end, range.getEnd());
   }

   @Test(expected = IllegalArgumentException.class)
   public void testInvalidDateRangeEndBeforeStart()
   {
      Chronology cal = Chronology.create();
      Period range = new Period(cal.newDate(10, 22, 2012), cal.newDate(10, 22, 1973));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testInvalidDateRangeEndEqualStart()
   {
      Chronology cal = Chronology.create();
      Period range = new Period(cal.newDate(10, 22, 2012), cal.newDate(10, 22, 2012));
   }


   @Test(expected = NullPointerException.class)
   public void testStartDateNull()
   {
      Period range = new Period(null, null);
   }

   @Test(expected = NullPointerException.class)
   public void testEndDateNull()
   {
      Period range = new Period(null, null);
   }


   @Test
   public void testEquals()
   {
      Chronology cal = Chronology.create();
      Period r1 = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      Period r2 = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));

      Period r3 = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 23, 2012));
      Period r4 = new Period(cal.newDate(10, 23, 1973), cal.newDate(10, 22, 2012));

      assertTrue(r1.equals(r2));
      assertTrue(r2.equals(r1));

      assertFalse(r1.equals(r3));
      assertFalse(r3.equals(r1));

      assertFalse(r4.equals(r1));
      assertFalse(r1.equals(r4));

      assertFalse(r1.equals(null));

      assertEquals(Period.EQUALS, r1.compareTo(r2));
      assertEquals(Period.EQUALS, r2.compareTo(r1));

   }

   @Test
   public void testContainsDate()
   {
      Chronology cal = Chronology.create();
      Period range = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertFalse(range.contains(cal.newDate(10, 21, 1973)));
      assertTrue(range.contains(cal.newDate(10, 22, 1973)));
      assertTrue(range.contains(cal.newDate(10, 22, 1980)));
      assertFalse(range.contains(cal.newDate(10, 22, 2012)));
      assertFalse(range.contains(cal.newDate(10, 23, 2012)));
   }

   @Test
   public void testWithinDate()
   {
      Chronology cal = Chronology.create();
      Period range = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertFalse(range.within(cal.newDate(10, 21, 1973)));
      assertTrue(range.within(cal.newDate(10, 22, 1973)));
      assertTrue(range.within(cal.newDate(10, 22, 1980)));
      assertTrue(range.within(cal.newDate(10, 22, 2012)));
      assertFalse(range.within(cal.newDate(10, 23, 2012)));
   }

   @Test
   public void testBetweenDate()
   {
      Chronology cal = Chronology.create();
      Period range = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertFalse(range.between(cal.newDate(10, 21, 1973)));
      assertFalse(range.between(cal.newDate(10, 22, 1973)));
      assertTrue(range.between(cal.newDate(10, 22, 1980)));
      assertFalse(range.between(cal.newDate(10, 22, 2012)));
      assertFalse(range.between(cal.newDate(10, 23, 2012)));
   }



   // compare to tests

   @Test
   public void testCompareTo()
   {
      Chronology cal = Chronology.create();


      Period range1 = new Period(cal.newDate(10, 22, 1922), cal.newDate(10, 22, 1945));
      Period range2 = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2002));
      Period range3 = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      Period range4 = new Period(cal.newDate(10, 22, 2002), cal.newDate(10, 22, 2022));

      assertEquals(Period.BEFORE, range1.compareTo(range2));
      assertEquals(Period.LEADING, range2.compareTo(range3));
      assertEquals(Period.PRECEEDS, range2.compareTo(range4));


      assertEquals(Period.AFTER, range3.compareTo(range1));
      assertEquals(Period.TRAILING, range3.compareTo(range2));
      assertEquals(Period.FOLLOWS, range4.compareTo(range2));


   }

   @Test
   public void testCompareToContains()
   {
      Chronology cal = Chronology.create();


      Period range1 = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));

      Period range2 = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2002));
      Period range3 = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));

      Period range4 = new Period(cal.newDate(10, 21, 1946), cal.newDate(10, 23, 2012));

      assertEquals(Period.CONTAINS, range1.compareTo(range2));
      assertEquals(Period.CONTAINS, range1.compareTo(range3));
      assertEquals(Period.CONTAINS, range4.compareTo(range1));

      assertEquals(Period.CONTAINED, range1.compareTo(range4));
      assertEquals(Period.CONTAINED, range2.compareTo(range1));
      assertEquals(Period.CONTAINED, range3.compareTo(range1));
   }




   // intersection tests


   @Test
   public void testIntersectionBeforeAfterFollowingAndPreceding()
   {
      Chronology cal = Chronology.create();

      Period before = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 1972));
      Period after = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertNull(before.intersection(after));
      assertNull(after.intersection(before));

      Period preceding = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 1972));
      Period following = new Period(cal.newDate(10, 22, 1972), cal.newDate(10, 22, 2012));
      assertNull(preceding.intersection(following));
      assertNull(following.intersection(preceding));

   }


   @Test
   public void testIntersectionLeadingTrailing()
   {
      Chronology cal = Chronology.create();

      Period leading = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2002));
      Period trailing = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));

      Period one = leading.intersection(trailing);
      assertEquals(cal.newDate(10, 22, 1973), one.getStart());
      assertEquals(cal.newDate(10, 22, 2002), one.getEnd());

      Period two = trailing.intersection(leading);
      assertEquals(cal.newDate(10, 22, 1973), two.getStart());
      assertEquals(cal.newDate(10, 22, 2002), two.getEnd());

   }

   @Test
   public void testIntersectionContainedContains()
   {
      Chronology cal = Chronology.create();

      Period contains = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));
      Period contained = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));

      Period one = contains.intersection(contained);
      assertEquals(contained, one);

      Period two = contained.intersection(contains);
      assertEquals(contained, two);
   }

   @Test
   public void testIntersectionEquals()
   {
      Chronology cal = Chronology.create();

      Period rangeOne = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      Period rangeTwo = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));

      Period one = rangeOne.intersection(rangeTwo);
      assertEquals(rangeOne, one);
      assertEquals(rangeTwo, one);

      Period two = rangeTwo.intersection(rangeOne);
      assertEquals(rangeOne, two);
      assertEquals(rangeTwo, two);
   }


   // Union Tests

   @Test
   public void testUnion()
   {
      Chronology cal = Chronology.create();

      Period before = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 1972));
      Period after = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertNull(before.union(after));
      assertNull(after.union(before));


      Period result = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));

      Period leading = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2002));
      Period trailing = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertEquals(result, leading.union(trailing));
      assertEquals(result, trailing.union(leading));

      Period preceding = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2002));
      Period following = new Period(cal.newDate(10, 22, 2002), cal.newDate(10, 22, 2012));
      assertEquals(result, preceding.union(following));
      assertEquals(result, following.union(preceding));

      Period contains = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));
      Period contained = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertEquals(result, contains.union(contained));
      assertEquals(result, contained.union(contains));

      Period equalsOne = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));
      Period equalsTwo = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));
      assertEquals(result, equalsOne.union(equalsTwo));
      assertEquals(result, equalsTwo.union(equalsOne));
   }
















   // Gap tests

   @Test
   public void testGap()
   {
      Chronology cal = Chronology.create();

      Period result = new Period(cal.newDate(10, 22, 1972), cal.newDate(10, 22, 1973));

      Period before = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 1972));
      Period after = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertEquals(result, before.gap(after));
      assertEquals(result, after.gap(before));


      Period leading = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2002));
      Period trailing = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertNull(leading.gap(trailing));
      assertNull(trailing.gap(leading));

      Period preceding = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2002));
      Period following = new Period(cal.newDate(10, 22, 2002), cal.newDate(10, 22, 2012));
      assertNull(preceding.gap(following));
      assertNull(following.gap(preceding));

      Period contains = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));
      Period contained = new Period(cal.newDate(10, 22, 1973), cal.newDate(10, 22, 2012));
      assertNull(contains.gap(contained));
      assertNull(contained.gap(contains));

      Period equalsOne = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));
      Period equalsTwo = new Period(cal.newDate(10, 22, 1946), cal.newDate(10, 22, 2012));
      assertNull(equalsOne.gap(equalsTwo));
      assertNull(equalsTwo.gap(equalsOne));
   }






   @Test
   public void testClone()
   {
      Chronology cal = Chronology.create();

      Period range = new Period(cal.newDate(10,22,1946), cal.newDate(10,22,2012));
      Period clone = range.clone();

      assertEquals(range, clone);
      assertFalse(range == clone);
   }

}
