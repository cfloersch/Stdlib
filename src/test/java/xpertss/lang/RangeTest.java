package xpertss.lang;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * User: cfloersch
 * Date: 12/8/12
 * Time: 8:40 PM
 */
public class RangeTest {


   @Test (expected = NullPointerException.class)
   public void testConstructionNullLower()
   {
      Range<BigDecimal> bdr = new Range<BigDecimal>(null, BigDecimal.ONE);
   }

   @Test (expected = NullPointerException.class)
   public void testConstructionNullUpper()
   {
      Range<BigDecimal> bdr = new Range<BigDecimal>(BigDecimal.ONE, null);
   }

   @Test (expected = IllegalArgumentException.class)
   public void testConstructionUpperEqualsLower()
   {
      Range<BigDecimal> bdr = new Range<BigDecimal>(BigDecimal.ONE, BigDecimal.ONE);
   }

   @Test (expected = IllegalArgumentException.class)
   public void testConstructionUpperLessThanLower()
   {
      Range<BigDecimal> bdr = new Range<BigDecimal>(BigDecimal.TEN, BigDecimal.ONE);
   }


   @Test
   public void testBigDecimal()
   {
      Range<BigDecimal> bdr = new Range<BigDecimal>(BigDecimal.ONE, BigDecimal.TEN);
      assertEquals(BigDecimal.ONE, bdr.getLower());
      assertEquals(BigDecimal.TEN, bdr.getUpper());
      assertFalse(bdr.contains(BigDecimal.ZERO));
      assertTrue(bdr.contains(BigDecimal.ONE));
      assertTrue(bdr.contains(BigDecimal.valueOf(5)));
      assertFalse(bdr.contains(BigDecimal.TEN));
      assertFalse(bdr.contains(BigDecimal.valueOf(11)));
   }

   @Test
   public void testBigInteger()
   {
      Range<BigInteger> bir = new Range<BigInteger>(BigInteger.ONE, BigInteger.TEN);
      assertEquals(BigInteger.ONE, bir.getLower());
      assertEquals(BigInteger.TEN, bir.getUpper());
      assertFalse(bir.contains(BigInteger.valueOf(0)));
      assertTrue(bir.contains(BigInteger.valueOf(1)));
      assertTrue(bir.contains(BigInteger.valueOf(5)));
      assertFalse(bir.contains(BigInteger.valueOf(10)));
      assertFalse(bir.contains(BigInteger.valueOf(11)));
   }

   @Test
   public void testDouble()
   {
      Range<Double> bir = new Range<Double>(Double.valueOf(1), Double.valueOf(10));
      assertEquals(Double.valueOf(1), bir.getLower());
      assertEquals(Double.valueOf(10), bir.getUpper());
      assertFalse(bir.contains(Double.valueOf(0)));
      assertTrue(bir.contains(Double.valueOf(1)));
      assertTrue(bir.contains(Double.valueOf(5)));
      assertFalse(bir.contains(Double.valueOf(10)));
      assertFalse(bir.contains(Double.valueOf(11)));
   }

   @Test
   public void testFloat()
   {
      Range<Float> bir = new Range<Float>(Float.valueOf(1), Float.valueOf(10));
      assertEquals(Float.valueOf(1), bir.getLower());
      assertEquals(Float.valueOf(10), bir.getUpper());
      assertFalse(bir.contains(Float.valueOf(0)));
      assertTrue(bir.contains(Float.valueOf(1)));
      assertTrue(bir.contains(Float.valueOf(5)));
      assertFalse(bir.contains(Float.valueOf(10)));
      assertFalse(bir.contains(Float.valueOf(11)));
   }

   @Test
   public void testLong()
   {
      Range<Long> bir = new Range<Long>(Long.valueOf(1), Long.valueOf(10));
      assertEquals(Long.valueOf(1), bir.getLower());
      assertEquals(Long.valueOf(10), bir.getUpper());
      assertFalse(bir.contains(Long.valueOf(0)));
      assertTrue(bir.contains(Long.valueOf(1)));
      assertTrue(bir.contains(Long.valueOf(5)));
      assertFalse(bir.contains(Long.valueOf(10)));
      assertFalse(bir.contains(Long.valueOf(11)));
   }

   @Test
   public void testInteger()
   {
      Range<Integer> bir = new Range<Integer>(Integer.valueOf(1), Integer.valueOf(10));
      assertEquals(Integer.valueOf(1), bir.getLower());
      assertEquals(Integer.valueOf(10), bir.getUpper());
      assertFalse(bir.contains(Integer.valueOf(0)));
      assertTrue(bir.contains(Integer.valueOf(1)));
      assertTrue(bir.contains(Integer.valueOf(5)));
      assertFalse(bir.contains(Integer.valueOf(10)));
      assertFalse(bir.contains(Integer.valueOf(11)));
   }

   @Test
   public void testShort()
   {
      Range<Short> bir = new Range<Short>(Short.valueOf("1"), Short.valueOf("10"));
      assertEquals(Short.valueOf("1"), bir.getLower());
      assertEquals(Short.valueOf("10"), bir.getUpper());
      assertFalse(bir.contains(Short.valueOf("0")));
      assertTrue(bir.contains(Short.valueOf("1")));
      assertTrue(bir.contains(Short.valueOf("5")));
      assertFalse(bir.contains(Short.valueOf("10")));
      assertFalse(bir.contains(Short.valueOf("11")));
   }

   @Test
   public void testByte()
   {
      Range<Byte> bir = new Range<Byte>(Byte.valueOf("1"), Byte.valueOf("10"));
      assertEquals(Byte.valueOf("1"), bir.getLower());
      assertEquals(Byte.valueOf("10"), bir.getUpper());
      assertFalse(bir.contains(Byte.valueOf("0")));
      assertTrue(bir.contains(Byte.valueOf("1")));
      assertTrue(bir.contains(Byte.valueOf("5")));
      assertFalse(bir.contains(Byte.valueOf("10")));
      assertFalse(bir.contains(Byte.valueOf("11")));
   }



   @Test
   public void testWithin()
   {
      Range<Byte> bir = new Range<Byte>(Byte.valueOf("1"), Byte.valueOf("10"));
      assertEquals(Byte.valueOf("1"), bir.getLower());
      assertEquals(Byte.valueOf("10"), bir.getUpper());
      assertFalse(bir.within(Byte.valueOf("0")));
      assertTrue(bir.within(Byte.valueOf("1")));
      assertTrue(bir.within(Byte.valueOf("5")));
      assertTrue(bir.within(Byte.valueOf("10")));
      assertFalse(bir.within(Byte.valueOf("11")));
   }

   @Test
   public void testBetween()
   {
      Range<Byte> bir = new Range<Byte>(Byte.valueOf("1"), Byte.valueOf("10"));
      assertEquals(Byte.valueOf("1"), bir.getLower());
      assertEquals(Byte.valueOf("10"), bir.getUpper());
      assertFalse(bir.between(Byte.valueOf("0")));
      assertFalse(bir.between(Byte.valueOf("1")));
      assertTrue(bir.between(Byte.valueOf("5")));
      assertFalse(bir.between(Byte.valueOf("10")));
      assertFalse(bir.between(Byte.valueOf("11")));
   }




   @Test
   public void testObjectMethods()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x01);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x01);
      assertTrue(bo.equals(bt));
      assertTrue(bt.equals(bo));
      assertFalse(bt.equals(null));
      assertEquals(bo.hashCode(), bt.hashCode());
   }

   @Test (expected = NullPointerException.class)
   public void testContainsNull()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x01);
      bo.contains(null);
   }



   @Test
   public void testCompareToBefore()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x05), (byte)0x09);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x04);
      assertEquals(Range.BEFORE, bt.compareTo(bo));
   }

   @Test
   public void testCompareToPreceeds()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x05), (byte)0x09);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x05);
      assertEquals(Range.PRECEEDS, bt.compareTo(bo));
   }

   @Test
   public void testCompareToLeading()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x05), (byte)0x09);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x06);
      assertEquals(Range.LEADING, bt.compareTo(bo));
   }

   @Test
   public void testCompareToContains()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x05), (byte)0x09);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x10);
      assertEquals(Range.CONTAINS, bt.compareTo(bo));
   }

   @Test
   public void testCompareToEquals()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x10);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x10);
      assertEquals(Range.EQUALS, bt.compareTo(bo));
      assertEquals(Range.EQUALS, bo.compareTo(bt));
   }

   @Test
   public void testCompareToContainedSameLower()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x09);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x10);
      assertEquals(Range.CONTAINED, bo.compareTo(bt));
   }

   @Test
   public void testCompareToContainedSameUpper()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x01), (byte)0x10);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x10);
      assertEquals(Range.CONTAINED, bo.compareTo(bt));
   }

   @Test
   public void testCompareToTrailing()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x05), (byte)0x09);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x06);
      assertEquals(Range.TRAILING, bo.compareTo(bt));
   }

   @Test
   public void testCompareToFollows()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x05), (byte)0x09);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x05);
      assertEquals(Range.FOLLOWS, bo.compareTo(bt));
   }

   @Test
   public void testCompareToAfter()
   {
      Range<Byte> bo = new Range<Byte>(Byte.valueOf((byte)0x05), (byte)0x09);
      Range<Byte> bt = new Range<Byte>(Byte.valueOf((byte)0x00), (byte)0x04);
      assertEquals(Range.AFTER, bo.compareTo(bt));
   }

   @Test (expected = NullPointerException.class)
   public void testCompareToNull()
   {
      Range<Integer> io = new Range<Integer>(0,10);
      io.compareTo(null);
   }



   @Test
   public void testIntersectionLeadingTrailing()
   {
      Range<Integer> leading = new Range<Integer>(0, 10);
      Range<Integer> trailing = new Range<Integer>(5, 15);
      Range<Integer> i1 = leading.intersection(trailing);
      Range<Integer> i2 = trailing.intersection(leading);
      assertEquals(Integer.valueOf(5), i1.getLower());
      assertEquals(Integer.valueOf(10), i1.getUpper());
      assertEquals(i1,i2);
   }

   @Test
   public void testIntersectionEquals()
   {
      Range<Integer> one = new Range<Integer>(0, 10);
      Range<Integer> two = new Range<Integer>(0, 10);
      Range<Integer> i1 = one.intersection(two);
      Range<Integer> i2 = two.intersection(one);
      assertEquals(Integer.valueOf(0), i1.getLower());
      assertEquals(Integer.valueOf(10), i1.getUpper());
      assertEquals(i1,i2);
   }

   @Test
   public void testIntersectionContainsContained()
   {
      Range<Integer> contains = new Range<Integer>(0, 10);
      Range<Integer> contained = new Range<Integer>(1, 9);
      Range<Integer> i1 = contains.intersection(contained);
      Range<Integer> i2 = contained.intersection(contains);
      assertEquals(Integer.valueOf(1), i1.getLower());
      assertEquals(Integer.valueOf(9), i1.getUpper());
      assertEquals(i1,i2);
   }

   @Test
   public void testIntersectionBeforeAfter()
   {
      Range<Integer> before = new Range<Integer>(0, 5);
      Range<Integer> after = new Range<Integer>(10, 15);
      assertNull(before.intersection(after));
      assertNull(after.intersection(before));
   }

   @Test
   public void testIntersectionPreceedsFollows()
   {
      Range<Integer> preceeds = new Range<Integer>(0, 5);
      Range<Integer> follows = new Range<Integer>(5, 10);
      assertNull(preceeds.intersection(follows));
      assertNull(follows.intersection(preceeds));
   }






   @Test
   public void testUnionPreceedsFollows()
   {
      Range<Integer> preceeds = new Range<Integer>(0, 5);
      Range<Integer> follows = new Range<Integer>(5, 10);
      Range<Integer> u1 = preceeds.union(follows);
      Range<Integer> u2 = follows.union(preceeds);
      assertEquals(Integer.valueOf(0),u1.getLower());
      assertEquals(Integer.valueOf(10),u1.getUpper());
      assertEquals(u1, u2);
   }

   @Test
   public void testUnionBeforeAfter()
   {
      Range<Integer> before = new Range<Integer>(0, 5);
      Range<Integer> after = new Range<Integer>(10, 15);
      assertNull(before.union(after));
      assertNull(after.union(before));
   }

   @Test
   public void testUnionEquals()
   {
      Range<Integer> one = new Range<Integer>(10, 15);
      Range<Integer> two = new Range<Integer>(10, 15);
      Range<Integer> u1 = one.union(two);
      Range<Integer> u2 = two.union(one);
      assertEquals(Integer.valueOf(10),u1.getLower());
      assertEquals(Integer.valueOf(15),u1.getUpper());
      assertEquals(u1, u2);
   }

   @Test
   public void testUnionContainsContained()
   {
      Range<Integer> contains = new Range<Integer>(0, 15);
      Range<Integer> contained = new Range<Integer>(1, 14);
      Range<Integer> u1 = contains.union(contained);
      Range<Integer> u2 = contained.union(contains);
      assertEquals(Integer.valueOf(0),u1.getLower());
      assertEquals(Integer.valueOf(15),u1.getUpper());
      assertEquals(u1, u2);
   }

   @Test
   public void testUnionLeadingTrailing()
   {
      Range<Integer> leading = new Range<Integer>(0, 10);
      Range<Integer> trailing = new Range<Integer>(5, 15);
      Range<Integer> u1 = leading.union(trailing);
      Range<Integer> u2 = trailing.union(leading);
      assertEquals(Integer.valueOf(0),u1.getLower());
      assertEquals(Integer.valueOf(15),u1.getUpper());
      assertEquals(u1, u2);
   }






   @Test
   public void testSumPreceedsFollows()
   {
      Range<Integer> preceeds = new Range<Integer>(0, 5);
      Range<Integer> follows = new Range<Integer>(5, 10);
      Range<Integer> u1 = preceeds.sum(follows);
      Range<Integer> u2 = follows.sum(preceeds);
      assertEquals(Integer.valueOf(0),u1.getLower());
      assertEquals(Integer.valueOf(10),u1.getUpper());
      assertEquals(u1, u2);
   }

   @Test
   public void testSumBeforeAfter()
   {
      Range<Integer> before = new Range<Integer>(0, 5);
      Range<Integer> after = new Range<Integer>(10, 15);
      Range<Integer> u1 = before.sum(after);
      Range<Integer> u2 = after.sum(before);
      assertEquals(Integer.valueOf(0),u1.getLower());
      assertEquals(Integer.valueOf(15),u1.getUpper());
      assertEquals(u1, u2);
   }

   @Test
   public void testSumEquals()
   {
      Range<Integer> one = new Range<Integer>(10, 15);
      Range<Integer> two = new Range<Integer>(10, 15);
      Range<Integer> u1 = one.sum(two);
      Range<Integer> u2 = two.sum(one);
      assertEquals(Integer.valueOf(10),u1.getLower());
      assertEquals(Integer.valueOf(15),u1.getUpper());
      assertEquals(u1, u2);
   }

   @Test
   public void testSumContainsContained()
   {
      Range<Integer> contains = new Range<Integer>(0, 15);
      Range<Integer> contained = new Range<Integer>(1, 14);
      Range<Integer> u1 = contains.sum(contained);
      Range<Integer> u2 = contained.sum(contains);
      assertEquals(Integer.valueOf(0),u1.getLower());
      assertEquals(Integer.valueOf(15),u1.getUpper());
      assertEquals(u1, u2);
   }

   @Test
   public void testSumLeadingTrailing()
   {
      Range<Integer> leading = new Range<Integer>(0, 10);
      Range<Integer> trailing = new Range<Integer>(5, 15);
      Range<Integer> u1 = leading.sum(trailing);
      Range<Integer> u2 = trailing.sum(leading);
      assertEquals(Integer.valueOf(0),u1.getLower());
      assertEquals(Integer.valueOf(15),u1.getUpper());
      assertEquals(u1, u2);
   }





   @Test
   public void testGapPreceedsFollows()
   {
      Range<Integer> preceeds = new Range<Integer>(0, 5);
      Range<Integer> follows = new Range<Integer>(5, 10);
      assertNull(preceeds.gap(follows));
      assertNull(follows.gap(preceeds));
   }

   @Test
   public void testGapBeforeAfter()
   {
      Range<Integer> before = new Range<Integer>(0, 5);
      Range<Integer> after = new Range<Integer>(10, 15);
      Range<Integer> u1 = before.gap(after);
      Range<Integer> u2 = after.gap(before);
      assertEquals(Integer.valueOf(5),u1.getLower());
      assertEquals(Integer.valueOf(10), u1.getUpper());
      assertEquals(u1, u2);
   }

   @Test
   public void testGapEquals()
   {
      Range<Integer> one = new Range<Integer>(10, 15);
      Range<Integer> two = new Range<Integer>(10, 15);
      assertNull(one.gap(two));
      assertNull(two.gap(one));
   }

   @Test
   public void testGapContainsContained()
   {
      Range<Integer> contains = new Range<Integer>(0, 15);
      Range<Integer> contained = new Range<Integer>(1, 14);
      assertNull(contains.gap(contained));
      assertNull(contained.gap(contains));
   }

   @Test
   public void testGapLeadingTrailing()
   {
      Range<Integer> leading = new Range<Integer>(0, 10);
      Range<Integer> trailing = new Range<Integer>(5, 15);
      assertNull(leading.gap(trailing));
      assertNull(trailing.gap(leading));
   }





   @Test
   public void testDifferencePreceedsFollows()
   {
      Range<Integer> preceeds = new Range<Integer>(0, 5);
      Range<Integer> follows = new Range<Integer>(5, 10);
      List<Range<Integer>> u1 = preceeds.difference(follows);
      List<Range<Integer>> u2 = follows.difference(preceeds);
      assertEquals(1, u1.size());
      assertEquals(preceeds, u1.get(0));
      assertEquals(1, u2.size());
      assertEquals(follows, u2.get(0));
   }

   @Test
   public void testDifferenceBeforeAfter()
   {
      Range<Integer> before = new Range<Integer>(0, 5);
      Range<Integer> after = new Range<Integer>(10, 15);
      List<Range<Integer>> u1 = before.difference(after);
      List<Range<Integer>> u2 = after.difference(before);
      assertEquals(1, u1.size());
      assertEquals(before, u1.get(0));
      assertEquals(1, u2.size());
      assertEquals(after, u2.get(0));
   }

   @Test
   public void testDifferenceEquals()
   {
      Range<Integer> one = new Range<Integer>(10, 15);
      Range<Integer> two = new Range<Integer>(10, 15);
      assertEquals(0, one.difference(two).size());
      assertEquals(0, two.difference(one).size());

   }

   @Test
   public void testDifferenceContainsContained()
   {
      Range<Integer> contains = new Range<Integer>(0, 15);
      Range<Integer> contained = new Range<Integer>(1, 14);
      List<Range<Integer>> u1 = contains.difference(contained);
      List<Range<Integer>> u2 = contained.difference(contains);
      assertEquals(2, u1.size());
      assertEquals(0, u2.size());
      assertTrue(u1.contains(new Range<Integer>(0, 1)));
      assertTrue(u1.contains(new Range<Integer>(14,15)));
   }

   @Test
   public void testDifferenceLeadingTrailing()
   {
      Range<Integer> leading = new Range<Integer>(0, 10);
      Range<Integer> trailing = new Range<Integer>(5, 15);
      List<Range<Integer>> u1 = leading.difference(trailing);
      List<Range<Integer>> u2 = trailing.difference(leading);
      assertEquals(1, u1.size());
      assertTrue(u1.contains(new Range<Integer>(0, 5)));
      assertEquals(1, u2.size());
      assertTrue(u2.contains(new Range<Integer>(10, 15)));
   }


   @Test
   public void testClone()
   {
      Range<Integer> range = new Range<Integer>(0, 10);
      Range<Integer> clone = range.clone();
      assertEquals(range, clone);
      assertFalse(range == clone);
   }


   @Test
   public void testToString()
   {
      Range<Integer> ir = new Range<Integer>(0, 1000);
      assertEquals("0 - 1,000", ir.toString());
      Range<Double> dr = new Range<Double>(0.1D, 1000.1D);
      assertEquals("0.1 - 1,000.1", dr.toString());
   }

   @Test
   public void testToStringNullFormat()
   {
      Range<Integer> ir = new Range<Integer>(0, 1000);
      assertEquals("0 - 1,000", ir.toString(null));
   }

}
