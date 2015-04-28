/**
 * Created By: cfloersch
 * Date: 1/13/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.Test;

import java.math.RoundingMode;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class LongsTest {

   @Test
   public void testSafeMultiply()
   {
      Longs.safeMultiply(Long.MAX_VALUE, 1);
      Longs.safeMultiply(Long.MIN_VALUE, 1);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeMultiplyGreater()
   {
      Longs.safeMultiply(Long.MAX_VALUE, 2);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeMultiplyLess()
   {
      Longs.safeMultiply(Long.MIN_VALUE, 2);
   }




   @Test
   public void testSafeSubtract()
   {
      Longs.safeSubtract(Long.MIN_VALUE, 0);
      Longs.safeSubtract(Long.MAX_VALUE, 0);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeSubtractGreater()
   {
      Longs.safeSubtract(Long.MAX_VALUE, -1);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeSubtractLess()
   {
      Longs.safeSubtract(Long.MIN_VALUE, 1);
   }



   @Test
   public void testSafeAdd()
   {
      Longs.safeAdd(Long.MIN_VALUE, 0);
      Longs.safeAdd(Long.MAX_VALUE, 0);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeAddGreater()
   {
      Longs.safeAdd(Long.MAX_VALUE, 1);
   }

   @Test(expected = ArithmeticException.class)
   public void testSafeAddLess()
   {
      Longs.safeAdd(Long.MIN_VALUE, -1);
   }




   @Test
   public void testCheckedNegate()
   {
      assertEquals(Long.MIN_VALUE + 1, Longs.safeNegate(Long.MAX_VALUE));
   }

   @Test(expected = ArithmeticException.class)
   public void testCheckedNegateOverflow()
   {
      Longs.safeNegate(Long.MIN_VALUE);
   }



   @Test
   public void testRound()
   {
      assertEquals(1, Longs.round(1.2D, RoundingMode.DOWN));
      assertEquals(1, Longs.round(1.5D, RoundingMode.DOWN));
      assertEquals(1, Longs.round(1.8D, RoundingMode.DOWN));

      assertEquals(1, Longs.round(1.2D, RoundingMode.HALF_DOWN));
      assertEquals(1, Longs.round(1.5D, RoundingMode.HALF_DOWN));
      assertEquals(2, Longs.round(1.8D, RoundingMode.HALF_DOWN));

      assertEquals(1, Longs.round(1.2D, RoundingMode.HALF_UP));
      assertEquals(2, Longs.round(1.5D, RoundingMode.HALF_UP));
      assertEquals(2, Longs.round(1.8D, RoundingMode.HALF_UP));

      assertEquals(2, Longs.round(1.2D, RoundingMode.UP));
      assertEquals(2, Longs.round(1.5D, RoundingMode.UP));
      assertEquals(2, Longs.round(1.8D, RoundingMode.UP));

      assertEquals(1, Longs.round(1.2D, RoundingMode.HALF_EVEN));
      assertEquals(2, Longs.round(1.5D, RoundingMode.HALF_EVEN));
      assertEquals(2, Longs.round(1.8D, RoundingMode.HALF_EVEN));
      assertEquals(2, Longs.round(2.5D, RoundingMode.HALF_EVEN));

      assertEquals(-1, Longs.round(-1.2D, RoundingMode.CEILING));
      assertEquals(-1, Longs.round(-1.5D, RoundingMode.CEILING));
      assertEquals(-1, Longs.round(-1.8D, RoundingMode.CEILING));

      assertEquals(-2, Longs.round(-1.2D, RoundingMode.FLOOR));
      assertEquals(-2, Longs.round(-1.5D, RoundingMode.FLOOR));
      assertEquals(-2, Longs.round(-1.8D, RoundingMode.FLOOR));

   }

   @Test(expected = ArithmeticException.class)
   public void testRoundUnnecessary()
   {
      Longs.round(2.2D, RoundingMode.UNNECESSARY);
   }

   @Test(expected = ArithmeticException.class)
   public void testRoundPositiveInfinity()
   {
      Longs.round(Double.POSITIVE_INFINITY, RoundingMode.HALF_DOWN);
   }

   @Test(expected = ArithmeticException.class)
   public void testRoundNegativeInfinity()
   {
      Longs.round(Double.NEGATIVE_INFINITY, RoundingMode.HALF_UP);
   }

   @Test(expected = ArithmeticException.class)
   public void testRoundNaN()
   {
      Longs.round(Double.NaN, RoundingMode.HALF_UP);
   }



   @Test
   public void testIsPowerOfTwo()
   {
      for(int i = 0; i < 30; i++) {
         assertTrue(Longs.isPowerOfTwo(1<<i));
      }
      assertFalse(Longs.isPowerOfTwo(Long.MIN_VALUE));
      assertFalse(Longs.isPowerOfTwo(3));
      assertFalse(Longs.isPowerOfTwo(9));
   }


   @Test
   public void testScale()
   {
      Longs.safeAdd(Longs.safeAdd(Integer.MAX_VALUE, Integer.MAX_VALUE), Integer.MAX_VALUE);
   }


   @Test
   public void testMedian()
   {
      assertEquals(1, Longs.median(1));
      assertEquals(2, Longs.median(1,2,3,4));
      assertEquals(3, Longs.median(1,2,4,6));
      assertEquals(3, Longs.median(1,2,3,4,5));
      assertEquals(3, Longs.median(1,3,2,5,4));
   }


   @Test
   public void testParse()
   {
      assertEquals(10L, Longs.parse("10", -1L));
      assertEquals(10L, Longs.parse(new StringBuilder("10"), -1L));
   }

}
