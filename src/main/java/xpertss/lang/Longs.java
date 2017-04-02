package xpertss.lang;

import xpertss.util.Ordering;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

import static java.lang.String.format;
import static xpertss.lang.Numbers.gte;
import static xpertss.lang.Objects.notNull;

/**
 * Static utility methods pertaining to longs
 *
 * @author cfloersch
 * Date: 12/11/12
 */
@SuppressWarnings({ "UnnecessaryBoxing", "UnnecessaryUnboxing", "UnusedDeclaration" })
public final class Longs {

   private Longs() { }

   /**
    * Parse the given string into a long or return the specified default if there is an
    * error parsing the string.
    */
   public static long parse(CharSequence str, long def)
   {
      try { return Long.parseLong(CharSequences.toString(str)); } catch (Exception e) { return def; }
   }

   /**
    * Parse the given string into a long using the specified radix or return the
    * specified default if there is an error parsing the string.
    */
   public static long parse(CharSequence str, int radix, long def)
   {
      try { return Long.parseLong(CharSequences.toString(str), radix); } catch (Exception e) { return def; }
   }






   /**
    * Simple utility method to take an arbitrary number of longs and convert them into an
    * array using Java's varargs.
    */
   public static long[] toArray(long ... i)
   {
      return i;
   }


   /**
    * Null safe method to clone a given array. If the array is {@code null} then {@code null}
    * will be returned. Otherwise, a clone of the array is returned.
    *
    * @param input The input array to clone
    * @return The cloned array or {@code null} if the array was {@code null}
    */
   public static long[] clone(long[] input)
   {
      return (input == null) ? null : input.clone();
   }

   /**
    * Null safe method to sort an array using the elements natural ordering.
    *
    * @param input The input array to sort
    * @return The sorted array or {@code null} if the array was {@code null}
    */
   public static long[] sort(long[] input)
   {
      if(input != null) Arrays.sort(input);
      return input;
   }

   /**
    * Checks if an array is {@code null} or has a length of zero.
    *
    * @param array The array to test
    * @return {@code true} if the array is empty or {@code null}
    */
   public static boolean isEmpty(long[] array)
   {
      return array == null || array.length == 0;
   }

   /**
    * Null safe method to query an array's length. This will return zero if the given
    * array is {@code null}.
    *
    * @param array The array to query
    * @return The length of the array or zero if it is {@code null}
    */
   public static int size(long[] array)
   {
      return (array == null) ? 0 : array.length;
   }




   /**
    * Returns an empty long array if the specified data array is {@code null},
    * otherwise it returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length long array
    */
   public static long[] emptyIfNull(long[] data)
   {
      return (data == null) ? new long[0] : data;
   }

   /**
    * Returns an empty long array if the specified data array is {@code null},
    * otherwise it returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length long array
    */
   public static Long[] emptyIfNull(Long[] data)
   {
      return (data == null) ? new Long[0] : data;
   }




   /**
    * Returns {@code true} if target is present as an element anywhere in array.
    */
   public static boolean contains(long[] array, long target)
   {
      for (long value : emptyIfNull(array)) {
         if (value == target) return true;
      }
      return false;
   }

   /**
    * Returns the index of the first appearance of the target value in the array.
    */
   public static int indexOf(long[] array, long target)
   {
      for (int i = 0; array != null && i < array.length; i++) {
         if (array[i] == target) return i;
      }
      return -1;
   }

   /**
    * Returns the index of the last appearance of the target value in the array.
    */
   public static int lastIndexOf(long[] array, long target)
   {
      if(array != null) {
         for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == target) return i;
         }
      }
      return -1;
   }




   /**
    * Returns the first element of the array.
    *
    * @param array The source array to pull from
    * @return The first element in the array.
    * @throws NullPointerException if the array is {@code null}
    * @throws IllegalArgumentException if the array is zero length
    */
   public static long first(long[] array)
   {
      if(array.length < 1) throw new IllegalArgumentException();
      return array[0];
   }

   /**
    * Returns the last element in the array.
    *
    * @param array The source array to pull from
    * @return The last element in the array.
    * @throws NullPointerException if the array is {@code null}
    * @throws IllegalArgumentException if the array is zero length
    */
   public static long last(long[] array)
   {
      if(array.length < 1) throw new IllegalArgumentException();
      return array[array.length - 1];
   }



   /**
    * Return a new array which contains the subset of the specified src array
    * identified by the given offset and length.
    *
    * @param src The source array to derive the subset from
    * @param offset The offset into the source array to begin the subset
    * @param length The number of elements from the offset to include
    * @return An array of the specified length containing a subset of elements from the
    *          source array
    * @throws IllegalArgumentException if the src array is {@code null} or zero length,
    *          the offset is less than zero, or the length is less than 1.
    * @throws ArrayIndexOutOfBoundsException if the offset or length are invalid
    */
   public static long[] subset(long[] src, int offset, int length)
   {
      long[] result = new long[Numbers.gt(0, length, "length must be greater than 0")];
      System.arraycopy(notEmpty(src, "src"), Numbers.gte(0, offset, "invalid offset"), result, 0, length);
      return result;
   }







   /**
    * Creates and returns a new array which concatenates the specified items to the end of
    * the given array elements.
    *
    * @param array The prefix elements
    * @param items The elements to append to the end
    * @return A new array with all the items
    * @throws NullPointerException If array is {@code null}
    */
   public static long[] append(long[] array, long ... items)
   {
      return insert(size(array), array, items);
   }

   /**
    * Creates and returns a new array which concatenates the specified items to the beginning
    * of the given array elements.
    *
    * @param array The suffix elements
    * @param items The elements to prepend to the beginning
    * @return A new array with all the items
    * @throws NullPointerException If array is {@code null}
    */
   public static long[] prepend(long[] array, long ... items)
   {
      return insert(0, array, items);
   }


   /**
    * Creates and returns a new array which inserts the specified items into the given array
    * elements at the specified index.
    *
    * @param idx The index position into the array to insert the given items
    * @param array The base array of items to insert into
    * @param items The elements to insert into the array
    * @return A new array with all the items
    * @throws IndexOutOfBoundsException If the given index is invalid
    * @throws NullPointerException If array is {@code null}
    */
   public static long[] insert(int idx, long[] array, long ... items)
   {
      long[] result = new long[Objects.notNull(array).length + size(items)];
      System.arraycopy(array, 0, result, 0, idx);
      if(items != null) System.arraycopy(items, 0, result, idx, size(items));
      System.arraycopy(array, idx, result, idx + size(items), size(array) - idx);
      return result;
   }







   /**
    * Tests if two array regions are equal.
    *
    * @param src The source array to check within
    * @param sOffset The starting point within the source array to check
    * @param other The sequence to compare to
    * @param oOffset The index into the sequence to begin the comparison
    * @param len The number of elements to compare
    * @return {@code true} if the regions match, {@code false} otherwise
    */
   public static boolean regionMatches(long[] src, int sOffset, long[] other, int oOffset, int len)
   {
      if(notNull(src, "src").length < gte(0, sOffset, "sOffset") + gte(0, len, "len"))
         throw new ArrayIndexOutOfBoundsException("src array");
      if(notNull(other, "other").length < gte(0, oOffset, "oOffset") + len)
         throw new ArrayIndexOutOfBoundsException("other array");
      for(int i = 0; i < len; i++) {
         if(src[i + sOffset] != other[i + oOffset]) return false;
      }
      return true;
   }


   /**
    * Checks whether the src array starts with the specified array segment.
    *
    * @param src The src array to check - beginning at index 0
    * @param other The array to compare with
    * @param oOffset The offset into the compare array to begin the comparison
    * @param len The number of elements in the compare array to match
    * @return {@code true} if the regions match, {@code false} otherwise
    */
   public static boolean startsWith(long[] src, long[] other, int oOffset, int len)
   {
      return regionMatches(src, 0, other, oOffset, len);
   }

   /**
    * Checks whether the src array starts with the specified array.
    *
    * @param src The src array to check - beginning at index 0
    * @param other The array to compare with
    * @return {@code true} if the regions match, {@code false} otherwise
    */
   public static boolean startsWith(long[] src, long[] other)
   {
      return regionMatches(src, 0, other, 0, other.length);
   }


   /**
    * Checks whether the src array ends with the specified array segment.
    *
    * @param src The src array to check - beginning at {@code src.length - len}
    * @param other The array to compare with
    * @param oOffset The offset into the compare array to begin the comparison
    * @param len The number of elements in the compare array to match
    * @return {@code true} if the regions match, {@code false} otherwise
    */
   public static boolean endsWith(long[] src, long[] other, int oOffset, int len)
   {
      return regionMatches(src, src.length - len, other, oOffset, len);
   }

   /**
    * Checks whether the src array ends with the specified array.
    *
    * @param src The src array to check - beginning at {@code src.length - other.length}
    * @param other The array to compare with
    * @return {@code true} if the regions match, {@code false} otherwise
    */
   public static boolean endsWith(long[] src, long[] other)
   {
      return regionMatches(src, src.length - other.length, other, 0, other.length);
   }







   /**
    * Converts the given primitive array into an object array.
    *
    * @throws NullPointerException If the array is {@code null}
    */
   public static Long[] toObject(long ... items)
   {
      Long[] data = new Long[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = Long.valueOf(items[i]);
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array.
    *
    * @throws NullPointerException If the array or any of its elements are {@code null}
    */
   public static long[] toPrimitive(Long ... items)
   {
      long[] data = new long[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = items[i].longValue();
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array. This will use the specified
    * default value for all {@code null} items.
    *
    * @throws NullPointerException If the array is {@code null}
    */
   public static long[] toPrimitive(Long[] items, long def)
   {
      long[] data = new long[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = (items[i] != null) ? items[i].longValue() : def;
      }
      return data;
   }






   /**
    * Returns the value from the input set representing the maximum value.
    *
    * @param items Set of input numbers to compare
    * @return The maximum value from the set
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static long max(long ... items)
   {
      long max = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         max = Math.max(max, items[i]);
      }
      return max;
   }

   /**
    * Returns the value from the input set representing the minimum value.
    *
    * @param items Set of input numbers to compare
    * @return The minimum value from the set
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static long min(long ... items)
   {
      long min = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         min = Math.min(min, items[i]);
      }
      return min;
   }

   /**
    * Find the "best guess" middle value among the given set of numbers. If there
    * is an even number of values, then the two middle values are summed together
    * and divided by two and rounded down. If the two middle values are very large
    * then there is a possibility that overflow could occur when computing the
    * median.
    * <pre>
    *    Longs.median(1)            = 1
    *    Longs.median(1,2,3,4)      = 2
    *    Longs.median(1,2,4,6)      = 3
    *    Longs.median(1,2,3,4,5)    = 3
    *    Longs.median(1,3,2,5,4)    = 3
    * </pre>
    *
    * @param set set of items to obtain the median from
    * @return The median value from the set
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static long median(long ... set)
   {
      long[] sorted = sort(notEmpty(set).clone());
      int mid = (sorted.length - 1) / 2;
      return (sorted.length % 2 != 0) ? sorted[mid] :
            round((sorted[mid] + sorted[mid + 1]) / 2D, RoundingMode.DOWN);
   }

   /**
    * Find the most frequently occurring item(s) returning an empty array if all
    * items are unique.
    * <p/>
    * If there are multiple most frequent items because they all have the same
    * count within the set, the returned array will include them all sorted from
    * smallest to largest.
    * <pre>
    *    Longs.mode(1,2,3,4)          = [ ]
    *    Longs.mode(4,3,2,1)          = [ ]
    *    Longs.mode(1,1,2,2,3,3,4)    = [1,2,3]
    *    Longs.mode(1,3,2,3,2,4)      = [2,3]
    *    Longs.mode(1,3,3,3,2,2,4)    = [3]
    * </pre>
    *
    * @param items set of items to obtain the most populous item from
    * @return The item that occurred the most frequently
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static long[] mode(long ... items)
   {
      long[] sorted = sort(notEmpty(items).clone());
      long[] result = new long[sorted.length/2];
      int maxCount = 0, count = 1, pos = 0;
      long last = sorted[0];
      for(int i = 1; i < sorted.length; i++) {
         long next = sorted[i];
         if(last < next) {
            if(count > 1) {
               if(count > maxCount) {
                  maxCount = count;
                  result[0] = last;
                  pos = 1;
               } else if(count == maxCount) {
                  result[pos++] = last;
               }
            }
            count = 1;
         } else {
            count++;
         }
         last = next;
      }
      return Arrays.copyOf(result, (maxCount > 1) ? pos : 0);
   }

   /**
    * This computes the mean of the supplied set of numbers.
    * <p/>
    * The values will be converted to doubles, which causes a possible loss of
    * precision for longs of magnitude over 2^53 (slightly over 9e15).
    *
    * @param items The set of numbers to compute the mean from
    * @return the mean
    * @throws NullPointerException If the set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static long mean(long ... items)
   {
      Numbers.MeanAccumulator accumulator = new Numbers.MeanAccumulator();
      for(double item : notEmpty(items)) accumulator.add(item);
      return round(accumulator.mean(), RoundingMode.DOWN);
   }














   /**
    * Add two values throwing an exception if overflow occurs.
    *
    * @param val1  the first value
    * @param val2  the second value
    * @return the new total
    * @throws ArithmeticException if the value is too big or too small
    */
   public static long safeAdd(long val1, long val2)
   {
      long sum = val1 + val2;
      // If there is a sign change, but the two values have the same sign...
      if ((val1 ^ sum) < 0 && (val1 ^ val2) >= 0) {
         throw new ArithmeticException("The calculation caused an overflow: " + val1 + " + " + val2);
      }
      return sum;
   }

   /**
    * Subtracts two values throwing an exception if overflow occurs.
    *
    * @param val1  the first value, to be taken away from
    * @param val2  the second value, the amount to take away
    * @return the new total
    * @throws ArithmeticException if the value is too big or too small
    */
   public static long safeSubtract(long val1, long val2)
   {
      long diff = val1 - val2;
      // If there is a sign change, but the two values have different signs...
      if ((val1 ^ diff) < 0 && (val1 ^ val2) < 0) {
         throw new ArithmeticException();
      }
      return diff;
   }


   /**
    * Multiply two values throwing an exception if overflow occurs.
    *
    * @param val1  the first value
    * @param val2  the second value
    * @return the new total
    * @throws ArithmeticException if the value is too big or too small
    */
   public static long safeMultiply(long val1, long val2)
   {
      if (val2 == 1) return val1;
      if (val2 == 0 || val1 == 0) return 0;
      long total = val1 * val2;
      if (total / val2 != val1) throw new ArithmeticException();
      return total;
   }


   /**
    * Returns the sum of the input set throwing an exception if overflow occurs.
    *
    * @param items Set of input numbers to sum
    * @return The sum
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the supplied set is empty
    * @throws ArithmeticException If the result overflows the bounds of an integer
    */
   public static long safeSum(long ... items)
   {
      long result = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         result = safeAdd(result, items[i]);
      }
      return result;
   }


   /**
    * Returns the {@code b} to the {@code k}th power, provided it does not overflow.
    *
    * @throws IllegalArgumentException if {@code k} is negative
    * @throws ArithmeticException if {@code b} to the {@code k}th power overflows in signed
    *         {@code long} arithmetic
    */
   public static long safePow(long b, int k)
   {
      Numbers.gte(0, k, "k must not be negative");
      if (b >= -2 & b <= 2) {
         switch ((int) b) {
            case 0:
               return (k == 0) ? 1 : 0;
            case 1:
               return 1;
            case (-1):
               return ((k & 1) == 0) ? 1 : -1;
            case 2:
               checkNoOverflow(k < Long.SIZE - 1);
               return 1L << k;
            case (-2):
               checkNoOverflow(k < Long.SIZE);
               return ((k & 1) == 0) ? (1L << k) : (-1L << k);
            default:
               throw new AssertionError();
         }
      }
      long accum = 1;
      while (true) {
         switch (k) {
            case 0:
               return accum;
            case 1:
               return safeMultiply(accum, b);
            default:
               if ((k & 1) != 0) {
                  accum = safeMultiply(accum, b);
               }
               k >>= 1;
               if (k > 0) {
                  checkNoOverflow(b <= FLOOR_SQRT_MAX_LONG);
                  b *= b;
               }
         }
      }
   }

   static final long FLOOR_SQRT_MAX_LONG = 3037000499L;


   /**
    * Negates the input throwing an exception if it can't negate it without
    * overflowing the bounds of the {@code long}.
    *
    * @param value  the value to negate
    * @return the negated value
    * @throws ArithmeticException if the value is {@link Long#MIN_VALUE}
    */
   public static long safeNegate(long value)
   {
      if (value == Long.MIN_VALUE) throw new ArithmeticException();
      return -value;
   }

   /**
    * Returns the absolute value of the given {@code long} value. If the argument
    * is not negative, the argument is returned. If the argument is negative, the
    * negation of the argument is returned.
    * <p/>
    * If the value equals {@link Long#MIN_VALUE} an exception will be thrown to
    * indicate the action is impossible to perform without overflow.
    *
    * @param value the argument whose absolute value is to be determined
    * @return the absolute value of the argument.
    * @throws ArithmeticException if the value is {@link Long#MIN_VALUE}
    */
   public static long safeAbs(long value)
   {
      if (value == Long.MIN_VALUE) throw new ArithmeticException();
      return Math.abs(value);
   }







   /**
    * Converts the argument to a long by an unsigned conversion. In an unsigned
    * conversion to a long, the high-order 32 bits of the long are zero and the
    * low-order 32 bits are equal to the bits of the integer argument.
    * Consequently, zero and positive int values are mapped to a numerically
    * equal long value and negative int values are mapped to a long value equal
    * to the input plus 2^32.
    *
    * @param x the value to convert to a long
    * @return the argument converted to long by an unsigned conversion
    */
   public static long fromUnsignedInt(int x)
   {
      return ((long) x) & 0xffffffffL;
   }





   private static final double MIN_LONG_AS_DOUBLE = -0x1p63;
   /*
    * We cannot store Long.MAX_VALUE as a double without losing precision.  Instead, we store
    * Long.MAX_VALUE + 1 == -Long.MIN_VALUE, and then offset all comparisons by 1.
    */
   private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 0x1p63;

   /**
    * Returns the {@code long} value that is equal to {@code x} rounded with the specified rounding
    * mode, if possible.
    *
    * @throws ArithmeticException if
    *         <ul>
    *         <li>{@code x} is infinite or NaN
    *         <li>{@code x}, after being rounded to a mathematical integer using the specified
    *         rounding mode, is either less than {@code Long.MIN_VALUE} or greater than {@code
    *         Long.MAX_VALUE}
    *         <li>{@code x} is not a mathematical integer and {@code mode} is
    *         {@link RoundingMode#UNNECESSARY}
    *         </ul>
    */
   public static long round(double x, RoundingMode mode)
   {
      double z = Numbers.roundIntermediate(x, mode);
      if(!(MIN_LONG_AS_DOUBLE - z < 1.0 & z < MAX_LONG_AS_DOUBLE_PLUS_ONE))
         throw new ArithmeticException();
      return (long) z;
   }




   /**
    * Returns {@code true} if {@code x} represents a power of two.
    * <p/>
    * This differs from {@code Long.bitCount(x) == 1}, because
    * {@code Long.bitCount(Long.MIN_VALUE) == 1}, but
    * {@link Long#MIN_VALUE} is not a power of two.
    */
   public static boolean isPowerOfTwo(long x)
   {
      return x > 0 & (x & (x - 1)) == 0;
   }










   /**
    * The following method will return a random number between {@code min} and {@code max}
    * minus one. For example if you pass in the numbers 5 & 10 you will consistently get
    * random numbers of 5,6,7,8, and 9 but will never get 10.
    *
    * @param min    The minimum number the random number should be
    * @param max    One more than the maximum number the random should be.
    */
   public static long random(long min, long max)
   {
      if(min >= max) throw new IllegalArgumentException("Max value must be greater than min value");
      return (long) ((Math.random() * (max-min)) + min);
   }



   /**
    * Returns the difference between the range's upper value and its lower value as a
    * BigInteger.
    * <p>
    * Unfortunately, it is possible the difference between lower and upper of a range
    * measured in longs could overflow a signed long's capacity to represent it.
    *
    * @param range The range to calculate a difference on
    * @return The difference between the upper and lower bound of the given range
    * @throws NullPointerException If the specified range is {@code null}
    */
   public static BigInteger between(Range<Long> range)
   {
      Objects.notNull(range);
      return BigInteger.valueOf(range.getUpper()).subtract(BigInteger.valueOf(range.getLower()));
   }




   /**
    * Returns an ordering that orders two {@code long} arrays using their natural
    * order. When one array is a prefix of the other, this treats the shorter array
    * as the lesser. For example,
    * <p/>
    * {@code [] < [1] < [1, 2] < [2]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(long[], long[])}.
    */
   public static Ordering<long[]> natural()
   {
      return LexicographicalOrdering.NATURAL;
   }

   /**
    * Returns an ordering that orders two {@code long} arrays using the reverse of their
    * natural order. When one array is a prefix of the other, treats the shorter array as
    * the lesser. For example,
    * <p/>
    * {@code [] < [2] < [1], [1, 2]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(long[], long[])}.
    */
   public static Ordering<long[]> reversed()
   {
      return LexicographicalOrdering.REVERSED;
   }

   @SuppressWarnings("SuspiciousNameCombination")
   private static abstract class LexicographicalOrdering extends Ordering<long[]> {
      private static final Ordering<long[]> NATURAL = new LexicographicalOrdering() {
         @Override int compare(long left, long right)
         {
            return Long.compare(left, right);
         }
      };
      private static final Ordering<long[]> REVERSED = new LexicographicalOrdering() {
         @Override int compare(long left, long right)
         {
            return Long.compare(right, left);
         }
      };
      @Override public int compare(long[] left, long[] right)
      {
         int minLength = Math.min(left.length, right.length);
         for (int i = 0; i < minLength; i++) {
            int result = compare(left[i], right[i]);
            if (result != 0) return result;
         }
         return Integer.signum(left.length - right.length);
      }
      abstract int compare(long left, long right);
   }







   // Argument Checking

   /**
    * Argument checking utility that will throw a {@link NullPointerException} if the given
    * array is {@code null} or an {@link IllegalArgumentException} if it's length is zero.
    *
    * @param array  the array to check for nullity and length
    * @return {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code array} is {@code null}
    * @throws IllegalArgumentException if {@code array}'s length is zero
    */
   public static long[] notEmpty(long[] array)
   {
      if(array == null) throw new NullPointerException();
      if(array.length < 1) throw new IllegalArgumentException();
      return array;
   }

   /**
    * Argument checking utility that will throw a {@link NullPointerException} if the given
    * array is {@code null} or an {@link IllegalArgumentException} if it's length is zero.
    * <p/>
    * The resulting exception will contain the given {@code argName} as the message if
    * thrown.
    *
    * @param array  the array to check for nullity and length
    * @param argName The argument name being inspected
    * @return {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code array} is {@code null}
    * @throws IllegalArgumentException if {@code array}'s length is zero
    */
   public static long[] notEmpty(long[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      if(array.length < 1) throw new IllegalArgumentException(format("%s is empty", argName));
      return array;
   }




   // Private methods

   private static void checkNoOverflow(boolean condition)
   {
      if(!condition) throw new ArithmeticException();
   }

}
