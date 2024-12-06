package xpertss.lang;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * User: cfloersch
 * Date: 12/12/12
 * Time: 6:21 PM
 */
@SuppressWarnings("UnnecessaryBoxing")
public class NumbersTest {


   public static int mean(int x, int y) {
      // Efficient method for computing the arithmetic mean.
      // The alternative (x + y) / 2 fails for large values.
      // The alternative (x + y) >>> 1 fails for negative values.
      return (x & y) + ((x ^ y) >> 1);
   }

   public static long mean(long x, long y) {
      // Efficient method for computing the arithmetic mean.
      // The alternative (x + y) / 2 fails for large values.
      // The alternative (x + y) >>> 1 fails for negative values.
      return (x & y) + ((x ^ y) >> 1);
   }


   private static final class MeanAccumulator {

      private long count = 0;
      private double mean = 0.0;

      void add(double value) {
         //checkArgument(isFinite(value));
         ++count;
         // Art of Computer Programming vol. 2, Knuth, 4.2.2, (15)
         mean += (value - mean) / count;
      }

      double mean() {
         //checkArgument(count > 0, "Cannot take mean of 0 values");
         return mean;
      }
   }




   @Test
   public void testIntRounding()
   {
      assertEquals(2, (int) 2.2D);
      assertEquals(2, (int) 2.5D);
      assertEquals(2, (int) 2.7D);

      assertEquals(-2, (int) -2.2D);
      assertEquals(-2, (int) -2.5D);
      assertEquals(-2, (int) -2.7D);

   }

   @Test
   public void testMean()
   {
      double[] items = {2D, 3D, 4D};
      MeanAccumulator accumulator = new MeanAccumulator();
      for(double value : items) {
         accumulator.add(value);
      }
      double amount = 0;
      for(double value : items) {
         amount += value;
      }
      assertEquals(amount/items.length, accumulator.mean());
   }








   @Test
   public void testLessThanEdgeError()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.lt(10,10,"error");
      });
   }

   @Test
   public void testLessThanError()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.lt(10,15,"error");
      });
   }

   @Test
   public void testLessThanValid()
   {
      assertEquals(Integer.valueOf(5), Numbers.lt(10, 5, "error"));
   }


   @Test
   public void testLessThanEqualError()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.lte(10,15,"error");
      });
   }

   @Test
   public void testLessThanEqualEdgeError()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.lte(10,11,"error");
      });
   }

   @Test
   public void testLessThanEqualEdgeValid()
   {
      assertEquals(Integer.valueOf(10), Numbers.lte(10, 10, "error"));
   }

   @Test
   public void testLessThanEqualValid()
   {
      assertEquals(Integer.valueOf(5), Numbers.lte(10, 5, "error"));
   }



   @Test
   public void testGreaterThanEqualError()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.gte(10,5,"error");
      });
   }

   @Test
   public void testGreaterThanEqualEdgeError()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.gte(10,9,"error");
      });
   }

   @Test
   public void testGreaterThanEqualEdgeValid()
   {
      assertEquals(Integer.valueOf(10), Numbers.gte(10, 10, "error"));
   }

   @Test
   public void testGreaterThanEqualValid()
   {
      assertEquals(Integer.valueOf(15), Numbers.gte(10, 15, "error"));
   }



   @Test
   public void testGreaterThanEdgeError()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.gt(10,10,"error");
      });
   }

   @Test
   public void testGreaterThanError()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.gt(10,5,"error");
      });
   }

   @Test
   public void testGreaterThanValid()
   {
      assertEquals(Integer.valueOf(11), Numbers.gt(10, 11, "error"));
   }


   @Test
   public void testBetween()
   {
      assertEquals(Integer.valueOf(1), Numbers.between(0, 10, 1, ""));
      assertEquals(Integer.valueOf(9), Numbers.between(0, 10, 9, ""));
   }

   @Test
   public void testBetweenFailLowerBound()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.between(0, 10, 0, "");
      });
   }

   @Test
   public void testBetweenFailUpperBound()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.between(0, 10, 10, "");
      });
   }



   @Test
   public void testWithin()
   {
      assertEquals(Integer.valueOf(0), Numbers.within(0, 10, 0, ""));
      assertEquals(Integer.valueOf(1), Numbers.within(0, 10, 1, ""));
      assertEquals(Integer.valueOf(9), Numbers.within(0, 10, 9, ""));
      assertEquals(Integer.valueOf(10), Numbers.within(0, 10, 10, ""));
   }

   @Test
   public void testWithinFailLowerBound()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.within(0, 10, -1, "");
      });

   }

   @Test
   public void testWithinFailUpperBound()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.within(0, 10, 11, "");
      });
   }


   @Test
   public void testContains()
   {
      assertEquals(Integer.valueOf(0), Numbers.contains(0, 10, 0, ""));
      assertEquals(Integer.valueOf(1), Numbers.contains(0, 10, 1, ""));
      assertEquals(Integer.valueOf(9), Numbers.contains(0, 10, 9, ""));
   }

   @Test
   public void testContainsFailLowerBound()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.contains(0, 10, -1, "");
      });
   }

   @Test
   public void testContainsFailUpperBound()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Numbers.contains(0, 10, 10, "");
      });
   }






   @Test
   public void testEqual()
   {
      assertTrue(Numbers.equal(Integer.valueOf(2), Double.valueOf(2D)));
      assertTrue(Numbers.equal(Integer.valueOf(2), Short.valueOf((short)2)));
      assertTrue(Numbers.equal(Float.valueOf(2.00F), Short.valueOf((short)2)));
      assertTrue(Numbers.equal(BigDecimal.valueOf(2.00D), BigInteger.valueOf(2)));
      assertTrue(Numbers.equal(BigDecimal.valueOf(2.00D), Short.valueOf((short)2)));
      assertTrue(Numbers.equal(BigInteger.valueOf(2), Short.valueOf((short)2)));
      assertTrue(Numbers.equal(BigInteger.valueOf(2)));
      assertFalse(Numbers.equal());
   }

   @Test
   public void testEqualNullSet()
   {
      assertThrows(NullPointerException.class, ()->{
         Numbers.equal(null);
      });
   }


}
