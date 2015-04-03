package xpertss.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class ComparisonTest {



   @Test
   public void testSimpleBooleanComparison()
   {
      assertEquals(1, Comparison.start().compare(true, false).result());
      assertEquals(-1, Comparison.start().compare(false, true).result());
      assertEquals(0, Comparison.start().compare(false, false).result());
      assertEquals(0, Comparison.start().compare(true, true).result());

      assertEquals(1, Comparison.start().compare(Boolean.TRUE, Boolean.FALSE).result());
      assertEquals(-1, Comparison.start().compare(Boolean.FALSE, Boolean.TRUE).result());
      assertEquals(0, Comparison.start().compare(Boolean.FALSE, Boolean.FALSE).result());
      assertEquals(0, Comparison.start().compare(Boolean.TRUE, Boolean.TRUE).result());
   }

   @Test(expected = NullPointerException.class)
   public void testSimpleNullLeftBooleanComparison()
   {
      Comparison.start().compare(null, false).result();
   }

   @Test
   public void testNullLeftBooleanComparison()
   {
      Ordering<Boolean> ordering = Ordering.natural();
      assertEquals(-1, Comparison.start().compare(null, false, Ordering.nullsFirst(ordering)).result());
      assertEquals(1, Comparison.start().compare(null, false,  Ordering.nullsLast(ordering)).result());
   }

   @Test(expected = NullPointerException.class)
   public void testSimpleNullRightBooleanComparison()
   {
      Comparison.start().compare(false, null).result();
   }

   @Test
   public void testNullRightBooleanComparison()
   {
      Ordering<Boolean> ordering = Ordering.natural();
      assertEquals(1, Comparison.start().compare(false, null, Ordering.nullsFirst(ordering)).result());
      assertEquals(-1, Comparison.start().compare(false, null, Ordering.nullsLast(ordering)).result());
   }

   @Test
   public void testSimpleBooleanComparisonReverse()
   {
      Ordering<Boolean> ordering = Ordering.reversed();
      assertEquals(-1, Comparison.start().compare(true, false, ordering).result());
      assertEquals(1, Comparison.start().compare(false, true, ordering).result());
      assertEquals(0, Comparison.start().compare(false, false, ordering).result());
      assertEquals(0, Comparison.start().compare(true, true, ordering).result());

      assertEquals(-1, Comparison.start().compare(Boolean.TRUE, Boolean.FALSE, ordering).result());
      assertEquals(1, Comparison.start().compare(Boolean.FALSE, Boolean.TRUE, ordering).result());
      assertEquals(0, Comparison.start().compare(Boolean.FALSE, Boolean.FALSE, ordering).result());
      assertEquals(0, Comparison.start().compare(Boolean.TRUE, Boolean.TRUE, ordering).result());
   }

   @Test
   public void testSimpleByteComparison()
   {
      assertEquals(1, Comparison.start().compare((byte) 1, (byte) 0).result());
      assertEquals(-1, Comparison.start().compare((byte) -1, (byte) 0).result());
      assertEquals(0, Comparison.start().compare((byte)0, (byte)0).result());
      assertEquals(0, Comparison.start().compare((byte) 1, (byte) 1).result());
      
      assertEquals(1, Comparison.start().compare(Byte.valueOf((byte) 1), Byte.valueOf((byte) 0)).result());
      assertEquals(-1, Comparison.start().compare(Byte.valueOf((byte) -1), Byte.valueOf((byte) 0)).result());
      assertEquals(0, Comparison.start().compare(Byte.valueOf((byte) 0), Byte.valueOf((byte) 0)).result());
      assertEquals(0, Comparison.start().compare(Byte.valueOf((byte) 1), Byte.valueOf((byte) 1)).result());
   }


   @Test
   public void testSimpleShortComparison()
   {
      assertEquals(1, Comparison.start().compare((short) 1, (short) 0).result());
      assertEquals(-1, Comparison.start().compare((short) -1, (short) 0).result());
      assertEquals(0, Comparison.start().compare((short)0, (short)0).result());
      assertEquals(0, Comparison.start().compare((short) 1, (short) 1).result());

      assertEquals(1,  Comparison.start().compare(Short.valueOf((short) 1),  Short.valueOf((short) 0)).result());
      assertEquals(-1, Comparison.start().compare(Short.valueOf((short) -1), Short.valueOf((short) 0)).result());
      assertEquals(0,  Comparison.start().compare(Short.valueOf((short) 0),  Short.valueOf((short) 0)).result());
      assertEquals(0,  Comparison.start().compare(Short.valueOf((short) 1),  Short.valueOf((short) 1)).result());
   }


   @Test
   public void testSimpleIntegerComparison()
   {
      assertEquals(1, Comparison.start().compare(1, 0).result());
      assertEquals(-1, Comparison.start().compare(-1, 0).result());
      assertEquals(0, Comparison.start().compare(0, 0).result());
      assertEquals(0, Comparison.start().compare(1, 1).result());

      assertEquals(1,  Comparison.start().compare(Integer.valueOf(1),  Integer.valueOf(0)).result());
      assertEquals(-1, Comparison.start().compare(Integer.valueOf(-1), Integer.valueOf(0)).result());
      assertEquals(0,  Comparison.start().compare(Integer.valueOf(0),  Integer.valueOf(0)).result());
      assertEquals(0,  Comparison.start().compare(Integer.valueOf(1),  Integer.valueOf(1)).result());
   }

   @Test
   public void testSimpleLongComparison()
   {
      assertEquals(1, Comparison.start().compare(1L, 0L).result());
      assertEquals(-1, Comparison.start().compare(-1L, 0L).result());
      assertEquals(0, Comparison.start().compare(0L, 0L).result());
      assertEquals(0, Comparison.start().compare(1L, 1L).result());

      assertEquals(1,  Comparison.start().compare(Long.valueOf(1),  Long.valueOf(0)).result());
      assertEquals(-1, Comparison.start().compare(Long.valueOf(-1), Long.valueOf(0)).result());
      assertEquals(0,  Comparison.start().compare(Long.valueOf(0),  Long.valueOf(0)).result());
      assertEquals(0,  Comparison.start().compare(Long.valueOf(1),  Long.valueOf(1)).result());
   }

   @Test
   public void testSimpleFloatComparison()
   {
      assertEquals(1, Comparison.start().compare(1F, 0F).result());
      assertEquals(-1, Comparison.start().compare(-1F, 0F).result());
      assertEquals(0, Comparison.start().compare(0F, 0F).result());
      assertEquals(0, Comparison.start().compare(1F, 1F).result());

      assertEquals(1,  Comparison.start().compare(Float.valueOf(1),  Float.valueOf(0)).result());
      assertEquals(-1, Comparison.start().compare(Float.valueOf(-1), Float.valueOf(0)).result());
      assertEquals(0,  Comparison.start().compare(Float.valueOf(0),  Float.valueOf(0)).result());
      assertEquals(0,  Comparison.start().compare(Float.valueOf(1),  Float.valueOf(1)).result());
   }

   @Test
   public void testSimpleDoubleComparison()
   {
      assertEquals(1, Comparison.start().compare(1D, 0D).result());
      assertEquals(-1, Comparison.start().compare(-1D, 0D).result());
      assertEquals(0, Comparison.start().compare(0D, 0D).result());
      assertEquals(0, Comparison.start().compare(1D, 1D).result());

      assertEquals(1,  Comparison.start().compare(Double.valueOf(1),  Double.valueOf(0)).result());
      assertEquals(-1, Comparison.start().compare(Double.valueOf(-1), Double.valueOf(0)).result());
      assertEquals(0,  Comparison.start().compare(Double.valueOf(0),  Double.valueOf(0)).result());
      assertEquals(0,  Comparison.start().compare(Double.valueOf(1),  Double.valueOf(1)).result());
   }

   @Test
   public void testSimpleStringComparison()
   {
      assertEquals(1,  Comparison.start().compare("B", "A").result());
      assertEquals(-1, Comparison.start().compare("A", "B").result());
      assertEquals(0,  Comparison.start().compare("A", "A").result());
      assertEquals(0,  Comparison.start().compare("B", "B").result());

      assertEquals(1,  Comparison.start().compare("AB", "A").result());
   }


   @Test
   public void testCompoundComparison()
   {

      assertEquals(1, Comparison.start().compare("B", "A").result());
      assertEquals(1, Comparison.start().compare("A", "A").compare("B", "A").result());
      assertEquals(1, Comparison.start().compare("A", "A").compare("B", "B").compare(true, false).result());
      assertEquals(1, Comparison.start().compare("A", "A").compare("B", "B").compare(true, true).compare(1, 0).result());
      assertEquals(0, Comparison.start().compare("A", "A").compare("B", "B").compare(true, true).compare(0, 0).result());
   }

   @Test
   public void testSuperComparison()
   {

      assertEquals(1,  Comparison.start().compareSuper(0).compare("B", "A").result());
      assertEquals(1,  Comparison.start().compareSuper(1).compare("A", "B").result());
      assertEquals(-1, Comparison.start().compareSuper(-1).compare("B", "A").result());
   }






}
