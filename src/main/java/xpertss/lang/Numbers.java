package xpertss.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.MessageFormat;

import static java.lang.Double.MIN_EXPONENT;
import static java.lang.Double.MAX_EXPONENT;
import static java.lang.Double.doubleToRawLongBits;
import static java.lang.Math.abs;
import static java.lang.Math.copySign;
import static java.lang.Math.getExponent;
import static java.lang.Math.rint;

/**
 * Static utility methods to operate on numerics.
 *
 * @author cfloersch
 * Date: 12/7/12
 */
@SuppressWarnings("UnusedDeclaration")
public final class Numbers {

   private Numbers() { }


   /**
    * Returns the value from the set representing the maximum value.
    * <p>
    * Returns <tt>null</tt> if the supplied set is zero size.
    *
    * @param set Set of input numbers to compare
    * @return The maximum value from the set
    * @throws NullPointerException If the supplied set or any of its elements are
    *       {@code null}
    */
   @SafeVarargs
   public static <T extends Number & Comparable<T>> T max(T ... set)
   {
      if(Objects.notNull(set, "set can not be null").length < 1) return null;
      T max = Objects.first(set);
      for(int i = 1; i < set.length; i++) {
         if(set[i].compareTo(max) > 0) max = set[i];
      }
      return max;
   }

   /**
    * Returns the value from the set representing the minimum value.
    * <p>
    * Returns {@code null} if the supplied set is zero size.
    *
    * @param set Set of input numbers to compare
    * @return The minimum value from the set
    * @throws NullPointerException If the supplied set or any of its elements are
    *       {@code null}
    */
   @SafeVarargs
   public static <T extends Number & Comparable<T>> T min(T ... set)
   {
      if(Objects.notNull(set, "set can not be null").length < 1) return null;
      T min = Objects.first(set);
      for(int i = 1; i < set.length; i++) {
         if(set[i].compareTo(min) < 0) min = set[i];
      }
      return min;
   }




   /**
    * Returns {@code true} if the specified number is an instance of {@link Float},
    * {@link Double}, or {@link BigDecimal}. Otherwise, it returns {@code false}.
    */
   public static boolean isDecimal(Number n)
   {
      return Classes.isInstanceOf(n, Float.class, Double.class, BigDecimal.class);
   }



   /**
    * Returns {@code true} if the specified number represents a negative value.
    *
    * @throws NullPointerException if n is {@code null}
    */
   public static boolean isNegative(Number n)
   {
      if(n instanceof BigDecimal) {
         return ((BigDecimal)n).signum() < 0;
      } else if(n instanceof BigInteger) {
         return ((BigInteger)n).signum() < 0;
      } else if(isDecimal(n)) {
         return n.doubleValue() < 0;
      }
      return n.longValue() < 0;
   }


   /**
    * Null safe method to convert any {@link Number} subclass into a {@link BigDecimal} in
    * a manner which minimizes loss of precession or overflow. If the supplied number is
    * {@code null} then this will return {@code null}. For number instances which are not
    * core jdk Number classes the big decimal is created using the number's doubleValue.
    */
   public static BigDecimal toBigDecimal(Number n)
   {
      if(Classes.isInstanceOf(n, Long.class, Integer.class, Short.class, Byte.class)) {
         return BigDecimal.valueOf(n.longValue());
      } else if(n instanceof BigDecimal) {
         return (BigDecimal) n;
      } else if(n instanceof BigInteger) {
         return new BigDecimal((BigInteger)n);
      }
      return (n != null) ? BigDecimal.valueOf(n.doubleValue()) : null;
   }



   /**
    * Checks a variable set of input numbers for equality. It returns {@code true} if the set
    * contains only a single element or all elements in the set are equal to one another. The
    * specified numbers are converted to big decimals and then evaluated for equality using
    * the {@link BigDecimal#compareTo(BigDecimal)} method. This will return {@code false} if
    * the input set is empty or any one element in the set is not equal the others.
    *
    * @throws NullPointerException if the specified set is {@code null}
    */
   public static boolean equal(Number ... set)
   {
      if(Objects.notNull(set).length < 1) return false;
      BigDecimal first = toBigDecimal(set[0]);
      for(int i = 1; i < set.length; i++) {
         BigDecimal next = toBigDecimal(set[i]);
         if(first == null) {
            if(next != null) return false;
         } else {
            if(next == null || first.compareTo(next) != 0) return false;
         }
      }
      return true;
   }






   // Argument checkers


   /**
    * Argument checking utility that will return the specified {@code arg} if it is greater than
    * the specified minimum, otherwise it will throw an {@link IllegalArgumentException} with
    * the specified message.
    * <p>
    * This will attempt to process the specified message as a {@link MessageFormat} pattern
    * supplying the {@code arg} as the sole input parameter at index zero.
    */
   public static <T extends Number & Comparable<T>> T gt(T minimum, T arg, String msg)
   {
      if(minimum.compareTo(arg) >= 0)
         throw new IllegalArgumentException(MessageFormat.format(msg, arg));
      return arg;
   }



   /**
    * Argument checking utility that will return the specified {@code arg} if it is greater than or
    * equal to the specified minimum, otherwise it will throw an {@link IllegalArgumentException}
    * with the specified message.
    * <p>
    * This will attempt to process the specified message as a {@link MessageFormat} pattern
    * supplying the {@code arg} as the sole input parameter at index zero.
    */
   public static <T extends Number & Comparable<T>> T gte(T minimum, T arg, String msg)
   {
      if(minimum.compareTo(arg) > 0)
         throw new IllegalArgumentException(MessageFormat.format(msg, arg));
      return arg;
   }


   /**
    * Argument checking utility that will return the specified {@code arg} if it is less than the
    * specified maximum, otherwise it will throw an {@link IllegalArgumentException} with the
    * specified message.
    * <p>
    * This will attempt to process the specified message as a {@link MessageFormat} pattern
    * supplying the {@code arg} as the sole input parameter at index zero.
    */
   public static <T extends Number & Comparable<T>> T lt(T maximum, T arg, String msg)
   {
      if(maximum.compareTo(arg) <= 0)
         throw new IllegalArgumentException(MessageFormat.format(msg, arg));
      return arg;
   }


   /**
    * Argument checking utility that will return the specified {@code arg} if it is less than or
    * equal to the specified maximum, otherwise it will throw an {@link IllegalArgumentException}
    * with the specified message.
    * <p>
    * This will attempt to process the specified message as a {@link MessageFormat} pattern
    * supplying the {@code arg} as the sole input parameter at index zero.
    */
   public static <T extends Number & Comparable<T>> T lte(T maximum, T arg, String msg)
   {
      if(maximum.compareTo(arg) < 0)
         throw new IllegalArgumentException(MessageFormat.format(msg, arg));
      return arg;
   }

   /**
    * Argument checking utility that will return the specified {@code arg} if it is between the
    * given maximum and minimum, otherwise it will throw an {@link IllegalArgumentException} with
    * the specified message.
    * <p>
    * An argument is said to be between when {@code minimum < arg < maximum}
    * <p>
    * This will attempt to process the specified message as a {@link MessageFormat} pattern
    * supplying the {@code arg} as the sole input parameter at index zero.
    */
   public static <T extends Number & Comparable<T>> T between(T minimum, T maximum, T arg, String msg)
   {
      if(maximum.compareTo(arg) <= 0 || minimum.compareTo(arg) >= 0)
         throw new IllegalArgumentException(MessageFormat.format(msg, arg));
      return arg;
   }

   /**
    * Argument checking utility that will return the specified {@code arg} if it is within the
    * given range, otherwise it will throw an {@link IllegalArgumentException} with the specified
    * message.
    * <p>
    * An argument is said to be within when {@code minimum <= arg <= maximum}
    * <p>
    * This will attempt to process the specified message as a {@link MessageFormat} pattern
    * supplying the {@code arg} as the sole input parameter at index zero.
    */
   public static <T extends Number & Comparable<T>> T within(T minimum, T maximum, T arg, String msg)
   {
      if(maximum.compareTo(arg) < 0 || minimum.compareTo(arg) > 0)
         throw new IllegalArgumentException(MessageFormat.format(msg, arg));
      return arg;
   }

   /**
    * Argument checking utility that will return the specified {@code arg} if the specified range
    * contains that argument, otherwise it will throw an {@link IllegalArgumentException} with the
    * specified message.
    * <p>
    * An argument is said to be contained when {@code minimum <= arg < maximum}
    * <p>
    * This will attempt to process the specified message as a {@link MessageFormat} pattern
    * supplying the {@code arg} as the sole input parameter at index zero.
    */
   public static <T extends Number & Comparable<T>> T contains(T minimum, T maximum, T arg, String msg)
   {
      if(maximum.compareTo(arg) <= 0 || minimum.compareTo(arg) > 0)
         throw new IllegalArgumentException(MessageFormat.format(msg, arg));
      return arg;
   }











   // package utilities

   /*
    * This method returns a value y such that rounding y DOWN (towards zero) gives the same result
    * as rounding x according to the specified mode.
    */
   static double roundIntermediate(double x, RoundingMode mode)
   {
      if (!isFinite(x)) throw new ArithmeticException();
      switch (mode) {
         case UNNECESSARY:
            if(!isMathematicalInteger(x)) throw new ArithmeticException();
            return x;

         case FLOOR:
            if (x >= 0.0 || isMathematicalInteger(x)) {
               return x;
            } else {
               return x - 1.0;
            }

         case CEILING:
            if (x <= 0.0 || isMathematicalInteger(x)) {
               return x;
            } else {
               return x + 1.0;
            }

         case DOWN:
            return x;

         case UP:
            if (isMathematicalInteger(x)) {
               return x;
            } else {
               return x + copySign(1.0, x);
            }

         case HALF_EVEN:
            return rint(x);

         case HALF_UP: {
            double z = rint(x);
            if (abs(x - z) == 0.5) {
               return x + copySign(0.5, x);
            } else {
               return z;
            }
         }

         case HALF_DOWN: {
            double z = rint(x);
            if (abs(x - z) == 0.5) {
               return x;
            } else {
               return z;
            }
         }

         default:
            throw new AssertionError();
      }
   }

   static boolean isFinite(double d)
   {
      return getExponent(d) <= MAX_EXPONENT;
   }


   // The mask for the significand, according to the {@link
   // Double#doubleToRawLongBits(double)} spec.
   static final long SIGNIFICAND_MASK = 0x000fffffffffffffL;

   /**
    * The implicit 1 bit that is omitted in significands of normal doubles.
    */
   static final long IMPLICIT_BIT = SIGNIFICAND_MASK + 1;


   static long getSignificand(double d)
   {
      if(!isFinite(d)) throw new IllegalArgumentException();
      int exponent = getExponent(d);
      long bits = doubleToRawLongBits(d);
      bits &= SIGNIFICAND_MASK;
      return (exponent == MIN_EXPONENT - 1)
            ? bits << 1
            : bits | IMPLICIT_BIT;
   }


   static final int SIGNIFICAND_BITS = 52;


   /**
    * Returns {@code true} if {@code x} represents a mathematical integer.
    * <p/>
    * This is equivalent to, but not necessarily implemented as, the expression {@code
    * !Double.isNaN(x) && !Double.isInfinite(x) && x == Math.rint(x)}.
    */
   static boolean isMathematicalInteger(double x)
   {
      return isFinite(x)
            && (x == 0.0 ||
            SIGNIFICAND_BITS - Long.numberOfTrailingZeros(getSignificand(x)) <= getExponent(x));
   }


   static final class MeanAccumulator {

      private long count = 0;
      private double mean = 0.0;

      void add(double value) {
         ++count;
         // Art of Computer Programming vol. 2, Knuth, 4.2.2, (15)
         mean += (value - mean) / count;
      }

      double mean() {
         //checkArgument(count > 0, "Cannot take mean of 0 values");
         return mean;
      }
   }

}
