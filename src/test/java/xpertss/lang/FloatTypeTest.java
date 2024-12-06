package xpertss.lang;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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



   @Test
   public void testDoubleOverflowAtMaxPlusOne()
   {
      assertThrows(ArithmeticException.class, ()->{
         Decimal64.checkCast(maxPlusOne);
      });
   }

   @Test
   public void testDoubleOverflowAtMaxPlusTwo()
   {
      Decimal64.checkCast(maxPlusOne.add(BigDecimal.ONE));
   }

   @Test
   public void testDoubleOverflowAtMaxPlusThree()
   {
      BigDecimal maxPlusTwo = maxPlusOne.add(BigDecimal.ONE);
      assertThrows(ArithmeticException.class, ()->{
         Decimal64.checkCast(maxPlusTwo.add(BigDecimal.ONE));
      });
   }



   @Test
   public void testDoubleOverflowAtMinMinusOne()
   {
      assertThrows(ArithmeticException.class, ()->{
         Decimal64.checkCast(minMinusOne);
      });
   }

   @Test
   public void testDoubleOverflowAtMinMinusTwo()
   {
      Decimal64.checkCast(minMinusOne.subtract(BigDecimal.ONE));
   }

   @Test
   public void testDoubleOverflowAtMinMinusThree()
   {
      BigDecimal minMinusTwo = minMinusOne.subtract(BigDecimal.ONE);
      assertThrows(ArithmeticException.class, ()->{
         Decimal64.checkCast(minMinusTwo.subtract(BigDecimal.ONE));
      });
   }


   @Test
   public void testMaxDouble()
   {
      assertThrows(ArithmeticException.class, ()->{
         Decimal32.checkCast(BigDecimal.valueOf(Double.MAX_VALUE));
      });
   }

   @Test
   public void testMinDouble()
   {
      assertThrows(ArithmeticException.class, ()->{
         Decimal32.checkCast(BigDecimal.valueOf(Double.MIN_VALUE));
      });
   }

   @Test
   public void testMinNormalDouble()
   {
      assertThrows(ArithmeticException.class, ()->{
         Decimal32.checkCast(BigDecimal.valueOf(Double.MIN_NORMAL));
      });
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