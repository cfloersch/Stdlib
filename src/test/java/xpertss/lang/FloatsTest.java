/**
 * Created By: cfloersch
 * Date: 1/13/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;



import org.junit.Test;

import static org.junit.Assert.*;


public class FloatsTest {


   @Test
   public void testMax()
   {
      assertTrue(2.5F == Floats.max(2.0F, .25F, .75F, 1.5F, 2.25F, 1.0F, 2.5F, 0F));
      assertTrue(0.0F == Floats.max(-0.0F, 0.0F));
      assertTrue(2.0F == Floats.max(2.0F, 1.0F, 0.0F, -1.0F, -2.0F));
      assertTrue(Float.POSITIVE_INFINITY == Floats.max(2.0F, 1.0F, 0.0F, Float.POSITIVE_INFINITY));
      assertTrue(Float.MAX_VALUE == Floats.max(2.0F, 1.0F, 0.0F, Float.MAX_VALUE));
      assertTrue(1.0F == Floats.max(1.0F, 0.0F, Float.MIN_VALUE));
      assertTrue(Float.MIN_VALUE == Floats.max(0.0F, Float.MIN_VALUE));
   }

   @Test
   public void testMin()
   {
      assertTrue(.25F == Floats.min(2.0F, .25F, .75F, 1.5F, 2.25F, 1.0F, 2.5F));
      assertTrue(-0.0F == Floats.min(-0.0F, 0.0F));
      assertTrue(-2.0F == Floats.min(2.0F, 1.0F, 0.0F, -1.0F, -2.0F));
      assertTrue(Float.NEGATIVE_INFINITY == Floats.min(Float.NEGATIVE_INFINITY, 0.0F, Float.POSITIVE_INFINITY));
      assertTrue(0.0F == Floats.min(2.0F, 1.0F, 0.0F, Float.MAX_VALUE));
      assertTrue(0.0F == Floats.min(1.0F, 0.0F, Float.MIN_VALUE));
      assertTrue(-Float.MAX_VALUE == Floats.min(1.0F, 0.0F, -Float.MAX_VALUE));
      assertTrue(-Float.MIN_VALUE == Floats.min(1.0F, 0.0F, -Float.MIN_VALUE));
   }


   @Test
   public void testMedian()
   {
      assertEquals(1, Floats.median(1), .00001);
      assertEquals(2.5, Floats.median(1, 2, 3, 4), .00001);
      assertEquals(3, Floats.median(1, 2, 4, 6), .00001);
      assertEquals(3, Floats.median(1, 2, 3, 4, 5), .00001);
      assertEquals(3, Floats.median(1, 3, 2, 5, 4), .00001);
   }


   @Test
   public void testIsFinite()
   {
      assertFalse(Floats.isFinite(Float.POSITIVE_INFINITY));
      assertFalse(Floats.isFinite(Float.NEGATIVE_INFINITY));
      assertFalse(Floats.isFinite(Float.NaN));

      assertTrue(Floats.isFinite(Float.MAX_VALUE));
      assertTrue(Floats.isFinite(Float.MIN_VALUE));
      assertTrue(Floats.isFinite(Float.MIN_NORMAL));
      assertTrue(Floats.isFinite(0.0F));
      assertTrue(Floats.isFinite(-0.0F));
   }

   @Test
   public void testSafeCast()
   {
      assertEquals(Float.MIN_VALUE, Floats.safeCast(Float.MIN_VALUE), .00001);
      assertEquals(-Float.MIN_VALUE, Floats.safeCast(-Float.MIN_VALUE), .00001);
      assertEquals(0.0F, Floats.safeCast(0.0D), .00001);
      assertEquals(-0.0F, Floats.safeCast(-0.0D), .00001);
      assertEquals(Float.MAX_VALUE, Floats.safeCast(Float.MAX_VALUE), .00001);
      assertEquals(-Float.MAX_VALUE, Floats.safeCast(-Float.MAX_VALUE), .00001);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeCastOverflowPositive()
   {
      Floats.safeCast(Float.MAX_VALUE * 2D);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeCastOverflowNegative()
   {
      Floats.safeCast(-Float.MAX_VALUE * 2D);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeCastUnderflowPositive()
   {
      Floats.safeCast(Float.MIN_VALUE / 2D);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeCastUnderflowNegative()
   {
      Floats.safeCast(-Float.MIN_VALUE / 2D);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeCastNegativeNaN()
   {
      Floats.safeCast(-Float.NaN);
   }


   @Test
   public void testSaturatedCast()
   {
      assertEquals(Float.MIN_VALUE * 2F, Floats.saturatedCast(Float.MIN_VALUE * 2D), .00001);
      assertEquals(-Float.MIN_VALUE * 2F, Floats.saturatedCast(-Float.MIN_VALUE * 2D), .00001);
      assertEquals(Float.MAX_VALUE / 2F, Floats.saturatedCast(Float.MAX_VALUE / 2D), .00001);
      assertEquals(-Float.MAX_VALUE / 2F, Floats.saturatedCast(-Float.MAX_VALUE / 2D), .00001);

      assertEquals(Float.MIN_VALUE, Floats.saturatedCast(Float.MIN_VALUE / 2D), .00001);
      assertEquals(-Float.MIN_VALUE, Floats.saturatedCast(-Float.MIN_VALUE / 2D), .00001);
      assertEquals(Float.MAX_VALUE, Floats.saturatedCast(Float.MAX_VALUE * 2D), .00001);
      assertEquals(-Float.MAX_VALUE, Floats.saturatedCast(-Float.MAX_VALUE * 2D), .00001);

      assertEquals(0.0F, Floats.saturatedCast(0.0D), .00001);
      assertEquals(-0.0F, Floats.saturatedCast(-0.0D), .00001);
      assertEquals(Float.NaN, Floats.saturatedCast(Double.NaN), .00001);

      assertEquals(Float.MIN_VALUE, Floats.saturatedCast(Double.MIN_VALUE), .00001);
      assertEquals(-Float.MIN_VALUE, Floats.saturatedCast(-Double.MIN_VALUE), .00001);
      assertEquals(Float.MAX_VALUE, Floats.saturatedCast(Double.MAX_VALUE), .00001);
      assertEquals(-Float.MAX_VALUE, Floats.saturatedCast(-Double.MAX_VALUE), .00001);
   }



   @Test
   public void testSafeCastMax()
   {
      long max = Floats.MAX_INT_AS_FLOAT;
      float last = (float) max;
      for(int i = 1; i < 10; i++) {
         float minus = (float) (max - i);
         assertTrue(minus != last);
         last = minus;
      }
      float same = (float) max;
      assertTrue(same == max);

      float plus = (float) (max + 1);
      assertTrue(plus == max);
   }

   @Test
   public void testSafeCastMin()
   {
      long min = Floats.MIN_INT_AS_FLOAT;
      float last = (float) min;
      for(int i = 1; i < 10; i++) {
         float plus = (float) (min + i);
         assertTrue(plus != last);
         last = plus;
      }
      float same = (float) min;
      assertTrue(same == min);

      float minus = (float) (min - 1);
      assertTrue(minus == min);
   }


}
