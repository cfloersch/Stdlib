/**
 * Created By: cfloersch
 * Date: 6/18/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import java.math.BigDecimal;

/**
 * An enumeration of basic floating point numeric types.
 */
public enum FloatType {

   /**
    * A 32 bit floating point value {@link Float} with 23 bits
    * of precision and a 8 bit exponent.
    */
   Decimal32(23, 8, 7) {

      @Override
      public BigDecimal checkCast(BigDecimal value)
      {
         float val = value.floatValue();
         if(val != Float.POSITIVE_INFINITY && val != Float.NEGATIVE_INFINITY) {
            if(new BigDecimal(Float.toString(val)).compareTo(value) == 0) {
               return value;
            }
         }
         throw new ArithmeticException("loss of precision");
      }

   },

   /**
    * A 64 bit floating point value {@link Double} with 52 bits
    * of precision and a 11 bit exponent.
    */
   Decimal64(52, 11, 16) {

      @Override
      public BigDecimal checkCast(BigDecimal value)
      {
         double val = value.doubleValue();
         if(val != Double.POSITIVE_INFINITY && val != Double.NEGATIVE_INFINITY) {
            if(new BigDecimal(Double.toString(val)).compareTo(value) == 0) {
               return value;
            }
         }
         throw new ArithmeticException("loss of precision");
      }

   };


   private final int digits;
   private final int precision;
   private final int exponent;

   private FloatType(int precision, int exponent, int digits)
   {
      this.digits = digits;
      this.exponent = exponent;
      this.precision = precision;
   }


   /**
    * Returns the number of decimal digits that are used in standard
    * output formats for floating point numbers of this type.
    */
   public int getDecimalDigits() { return digits; }

   /**
    * Get the maximum precision floating point numbers of this type
    * are capable of representing.
    */
   public int getPrecision() { return precision; }

   /**
    * Get the number of exponent bits, floating point numbers of this
    * type utilize.
    */
   public int getExponentSize() { return exponent; }


   /**
    * Validate if the given BigDecimal can be cast to a floating point
    * value of this type.
    *
    * @throws ArithmeticException if the cast would result in the loss
    *    of precision.
    */
   public abstract BigDecimal checkCast(BigDecimal value);

}
