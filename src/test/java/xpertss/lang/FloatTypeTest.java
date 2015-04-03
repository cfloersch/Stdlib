package xpertss.lang;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static xpertss.lang.FloatType.*;

public class FloatTypeTest {

   private BigDecimal maxPlusOne = BigDecimal.valueOf(Doubles.MAX_LONG_AS_DOUBLE).add(BigDecimal.ONE);
   private BigDecimal minMinusOne = BigDecimal.valueOf(Doubles.MIN_LONG_AS_DOUBLE).subtract(BigDecimal.ONE);


   @Test
   public void testDoubleOverflow()
   {
      BigDecimal dec = BigDecimal.valueOf(Doubles.MAX_LONG_AS_DOUBLE);
      assertEquals(dec, BigDecimal.valueOf(dec.doubleValue()));
      BigDecimal plus = dec.add(BigDecimal.ONE);
      assertEquals(dec, BigDecimal.valueOf(plus.doubleValue()));
   }

   @Test
   public void testDoubleUnderflow()
   {
      BigDecimal dec = BigDecimal.valueOf(Doubles.MIN_LONG_AS_DOUBLE);
      assertEquals(dec, BigDecimal.valueOf(dec.doubleValue()));
      BigDecimal minus = dec.subtract(BigDecimal.ONE);
      assertEquals(dec, BigDecimal.valueOf(minus.doubleValue()));
   }



   @Test
   public void testDoubleSuccess()
   {
      Decimal64.checkCast(BigDecimal.ZERO);
      Decimal64.checkCast(BigDecimal.ZERO.negate());
      Decimal64.checkCast(BigDecimal.ONE);
      Decimal64.checkCast(BigDecimal.ONE.negate());
      Decimal64.checkCast(BigDecimal.TEN);
      Decimal64.checkCast(BigDecimal.TEN.negate());
      Decimal64.checkCast(BigDecimal.valueOf(2.2D));

      Decimal32.checkCast(BigDecimal.ZERO);
      Decimal32.checkCast(BigDecimal.ZERO.negate());
      Decimal32.checkCast(BigDecimal.ONE);
      Decimal32.checkCast(BigDecimal.ONE.negate());
      Decimal32.checkCast(BigDecimal.TEN);
      Decimal32.checkCast(BigDecimal.TEN.negate());
      Decimal32.checkCast(BigDecimal.valueOf(2.2D));
   }



   @Test(expected = ArithmeticException.class)
   public void testDoubleOverflowAtMaxPlusOne()
   {
      Decimal64.checkCast(maxPlusOne);
   }

   @Test
   public void testDoubleOverflowAtMaxPlusTwo()
   {
      Decimal64.checkCast(maxPlusOne.add(BigDecimal.ONE));
   }

   @Test(expected = ArithmeticException.class)
   public void testDoubleOverflowAtMaxPlusThree()
   {
      BigDecimal maxPlusTwo = maxPlusOne.add(BigDecimal.ONE);
      Decimal64.checkCast(maxPlusTwo.add(BigDecimal.ONE));
   }



   @Test(expected = ArithmeticException.class)
   public void testDoubleOverflowAtMinMinusOne()
   {
      Decimal64.checkCast(minMinusOne);
   }

   @Test
   public void testDoubleOverflowAtMinMinusTwo()
   {
      Decimal64.checkCast(minMinusOne.subtract(BigDecimal.ONE));
   }

   @Test(expected = ArithmeticException.class)
   public void testDoubleOverflowAtMinMinusThree()
   {
      BigDecimal minMinusTwo = minMinusOne.subtract(BigDecimal.ONE);
      Decimal64.checkCast(minMinusTwo.subtract(BigDecimal.ONE));
   }


   @Test(expected = ArithmeticException.class)
   public void testMaxDouble()
   {
      Decimal32.checkCast(BigDecimal.valueOf(Double.MAX_VALUE));
   }

   @Test(expected = ArithmeticException.class)
   public void testMinDouble()
   {
      Decimal32.checkCast(BigDecimal.valueOf(Double.MIN_VALUE));
   }

   @Test(expected = ArithmeticException.class)
   public void testMinNormalDouble()
   {
      Decimal32.checkCast(BigDecimal.valueOf(Double.MIN_NORMAL));
   }



   @Test
   public void testMaxDoubleSuccess()
   {
      Decimal64.checkCast(BigDecimal.valueOf(Double.MAX_VALUE));
   }

   @Test
   public void testMinDoubleSuccess()
   {
      Decimal64.checkCast(BigDecimal.valueOf(Double.MIN_VALUE));
   }

   @Test
   public void testMinNormalDoubleSuccess()
   {
      Decimal64.checkCast(BigDecimal.valueOf(Double.MIN_NORMAL));
   }


}