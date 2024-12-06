/**
 * Created By: cfloersch
 * Date: 1/18/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoublesTest {

   @Test
   public void testMedian()
   {
      assertEquals(1, Doubles.median(1), .00001);
      assertEquals(2.5, Doubles.median(1,2,3,4), .00001);
      assertEquals(3, Doubles.median(1,2,4,6), .00001);
      assertEquals(3, Doubles.median(1,2,3,4,5), .00001);
      assertEquals(3, Doubles.median(1,3,2,5,4), .00001);
   }

   @Test
   public void testSafeCastMax()
   {
      long max = Doubles.MAX_LONG_AS_DOUBLE;
      double last = (double) max;
      for(int i = 1; i < 10; i++) {
         double minus = (double) (max - i);
         assertTrue(minus != last);
         last = minus;
      }
      double same = (double) max;
      assertTrue(same == max);

      double plus = (double) (max + 1);
      assertTrue(plus == max);
   }

   @Test
   public void testSafeCastMin()
   {
      long min = Doubles.MIN_LONG_AS_DOUBLE;
      double last = (double) min;
      for(int i = 1; i < 10; i++) {
         double plus = (double) (min + i);
         assertTrue(plus != last);
         last = plus;
      }
      double same = (double) min;
      assertTrue(same == min);

      double minus = (double) (min - 1);
      assertTrue(minus == min);
   }


   @Test
   public void testParse()
   {
      assertTrue(10D == Doubles.parse("10", -1D));
      assertTrue(10D == Doubles.parse(new StringBuilder("10"), -1D));
   }

}
