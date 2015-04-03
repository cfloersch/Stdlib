package xpertss.lang;

import xpertss.util.Ordering;

import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Static utility methods pertaining to integers
 *
 * @author cfloersch
 * Date: 12/7/12
 */
@SuppressWarnings({ "UnnecessaryBoxing", "UnnecessaryUnboxing", "UnusedDeclaration" })
public final class Integers {

   private Integers() { }

   /**
    * Parse the given string into an int or return the specified default if there is an
    * error parsing the string.
    */
   public static int parse(String str, int def)
   {
      try { return Integer.parseInt(str); } catch (Exception e) { return def; }
   }

   /**
    * Parse the given string into an int using the specified radix or return the specified
    * default if there is an error parsing the string.
    */
   public static int parse(String str, int radix, int def)
   {
      try { return Integer.parseInt(str, radix); } catch (Exception e) { return def; }
   }







   /**
    * Simple utility method to take an arbitrary number of ints and convert them
    * into an array using Java's varargs.
    */
   public static int[] toArray(int ... i)
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
   public static int[] clone(int[] input)
   {
      return (input == null) ? null : input.clone();
   }


   /**
    * Null safe method to sort an array using the elements natural ordering.
    *
    * @param input The input array to sort
    * @return The sorted array or {@code null} if the array was {@code null}
    */
   public static int[] sort(int[] input)
   {
      if(input != null) Arrays.sort(input);
      return input;
   }


   /**
    * Checks if an array is empty or {@code null}.
    *
    * @param array The array to test
    * @return {@code true} if the array is empty or {@code null}
    */
   public static boolean isEmpty(int[] array)
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
   public static int size(int[] array)
   {
      return (array == null) ? 0 : array.length;
   }




   /**
    * Returns an empty array if the specified data array is {@code null}, otherwise it
    * returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length array
    */
   public static int[] emptyIfNull(int[] data)
   {
      return (data == null) ? new int[0] : data;
   }

   /**
    * Returns an empty array if the specified data array is {@code null}, otherwise it
    * returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length array
    */
   public static Integer[] emptyIfNull(Integer[] data)
   {
      return (data == null) ? new Integer[0] : data;
   }




   /**
    * Returns {@code true} if target is present as an element anywhere in array.
    */
   public static boolean contains(int[] array, int target)
   {
      for (int value : emptyIfNull(array)) {
         if (value == target) return true;
      }
      return false;
   }

   /**
    * Returns the index of the first appearance of the target value in the array.
    */
   public static int indexOf(int[] array, int target)
   {
      for (int i = 0; array != null && i < array.length; i++) {
         if (array[i] == target) return i;
      }
      return -1;
   }

   /**
    * Returns the index of the last appearance of the target value in the array.
    */
   public static int lastIndexOf(int[] array, int target)
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
   public static int first(int[] array)
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
   public static int last(int[] array)
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
    * @return An array of the specified length containing a subset of elements
    *          from the source array
    * @throws IllegalArgumentException if the src array is null or zero length,
    *          the offset is less than zero, or the length is less than 1.
    * @throws ArrayIndexOutOfBoundsException if the offset or length are invalid
    */
   public static int[] subset(int[] src, int offset, int length)
   {
      int[] result = new int[Numbers.gt(0, length, "length must be greater than 0")];
      System.arraycopy(notEmpty(src, "src"), Numbers.gte(0, offset, "invalid offset"), result, 0, length);
      return result;
   }







   /**
    * Creates and returns a new array which concatenates the specified items to the
    * end of the given array elements.
    *
    * @param array The prefix elements
    * @param items The elements to append to the end
    * @return A new array with all the items
    * @throws NullPointerException If array is {@code null}
    */
   public static int[] append(int[] array, int ... items)
   {
      return insert(size(array), array, items);
   }

   /**
    * Creates and returns a new array which concatenates the specified items to the
    * beginning of the given array elements.
    *
    * @param array The suffix elements
    * @param items The elements to prepend to the beginning
    * @return A new array with all the items
    * @throws NullPointerException If array is {@code null}
    */
   public static int[] prepend(int[] array, int ... items)
   {
      return insert(0, array, items);
   }


   /**
    * Creates and returns a new array which inserts the specified items into the
    * given array elements at the specified index.
    *
    * @param idx The index position into the array to insert the given items
    * @param array The base array of items to insert into
    * @param items The elements to insert into the array
    * @return A new array with all the items
    * @throws IndexOutOfBoundsException If the given index is invalid
    * @throws NullPointerException If array is {@code null}
    */
   public static int[] insert(int idx, int[] array, int ... items)
   {
      int[] result = new int[Objects.notNull(array).length + size(items)];
      System.arraycopy(array, 0, result, 0, idx);
      if(items != null) System.arraycopy(items, 0, result, idx, size(items));
      System.arraycopy(array, idx, result, idx + size(items), size(array) - idx);
      return result;
   }







   /**
    * Converts the given primitive array into an object array.
    *
    * @throws NullPointerException If the array is {@code null}
    */
   public static Integer[] toObject(int ... items)
   {
      Integer[] data = new Integer[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = Integer.valueOf(items[i]);
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array.
    *
    * @throws NullPointerException If the array or any of its elements are {@code null}
    */
   public static int[] toPrimitive(Integer ... items)
   {
      int[] data = new int[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = items[i].intValue();
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array. This will use the
    * specified default value for all {@code null} items.
    *
    * @throws NullPointerException If the array is {@code null}
    */
   public static int[] toPrimitive(Integer[] items, int def)
   {
      int[] data = new int[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = (items[i] != null) ? items[i].intValue() : def;
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
   public static int max(int ... items)
   {
      int max = notEmpty(items)[0];
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
   public static int min(int ... items)
   {
      int min = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         min = Math.min(min, items[i]);
      }
      return min;
   }

   /**
    * Find the "best guess" middle value among the given set of numbers. If there is
    * an even number of values, then the two middle values are summed together
    * and divided by two and rounded down.
    * <pre>
    *    Integers.median(1)            = 1
    *    Integers.median(1,2,3,4)      = 2
    *    Integers.median(1,2,4,6)      = 3
    *    Integers.median(1,2,3,4,5)    = 3
    *    Integers.median(1,3,2,5,4)    = 3
    * </pre>
    *
    * @param set set of items to obtain the median from
    * @return The median value from the set
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static int median(int ... set)
   {
      int[] sorted = sort(notEmpty(set).clone());
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
    *    Integers.mode(1,2,3,4)          = [ ]
    *    Integers.mode(4,3,2,1)          = [ ]
    *    Integers.mode(1,1,2,2,3,3,4)    = [1,2,3]
    *    Integers.mode(1,3,2,3,2,4)      = [2,3]
    *    Integers.mode(1,3,3,3,2,2,4)    = [3]
    * </pre>
    *
    * @param items set of items to obtain the most populous item from
    * @return The item that occurred the most frequently
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static int[] mode(int ... items)
   {
      int[] sorted = sort(notEmpty(items).clone());
      int[] result = new int[sorted.length/2];
      int pos = 0, last = sorted[0];
      int maxCount = 0, count = 1;
      for(int i = 1; i < sorted.length; i++) {
         int next = sorted[i];
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
    *
    * @param items The set of numbers to compute the mean from
    * @return the mean
    * @throws NullPointerException If the set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static int mean(int ... items)
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
    * @throws ArithmeticException If the result overflows the bounds of an integer
    */
   public static int safeAdd(int val1, int val2)
   {
      int sum = val1 + val2;
      // If there is a sign change, but the two values have the same sign...
      if ((val1 ^ sum) < 0 && (val1 ^ val2) >= 0) {
         throw new ArithmeticException();
      }
      return sum;
   }

   /**
    * Subtracts two values throwing an exception if overflow occurs.
    *
    * @param val1  the first value, to be taken away from
    * @param val2  the second value, the amount to take away
    * @return the new total
    * @throws ArithmeticException If the result overflows the bounds of an integer
    */
   public static int safeSubtract(int val1, int val2)
   {
      int diff = val1 - val2;
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
    * @throws ArithmeticException If the result overflows the bounds of an integer
    */
   public static int safeMultiply(int val1, int val2)
   {
      long total = (long) val1 * (long) val2;
      if (total < Integer.MIN_VALUE || total > Integer.MAX_VALUE) {
         throw new ArithmeticException();
      }
      return (int) total;
   }

   /**
    * Multiply two values to return an int throwing an exception if overflow occurs.
    *
    * @param val1  the first value
    * @param val2  the second value
    * @return the new total
    * @throws ArithmeticException If the result overflows the bounds of an integer
    */
   public static int safeMultiply(long val1, long val2)
   {
      return safeCast(Longs.safeMultiply(val1, val2));
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
   public static int safeSum(int ... items)
   {
      int result = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         result = safeAdd(result, items[i]);
      }
      return result;
   }



   /**
    * Returns the {@code b} to the {@code k}th power, provided it does not overflow.
    * <p/>
    * {@link Math#pow} may be faster, but does not check for overflow.
    *
    * @throws IllegalArgumentException if {@code k} is negative
    * @throws ArithmeticException if the result overflows the bounds of an integer
    */
   public static int safePow(int b, int k)
   {
      Numbers.gte(0, k, "k may not be negative");
      switch (b) {
         case 0:
            return (k == 0) ? 1 : 0;
         case 1:
            return 1;
         case (-1):
            return ((k & 1) == 0) ? 1 : -1;
         case 2:
            checkNoOverflow(k < Integer.SIZE - 1);
            return 1 << k;
         case (-2):
            checkNoOverflow(k < Integer.SIZE);
            return ((k & 1) == 0) ? 1 << k : -1 << k;
         default:
            // continue below to handle the general case
      }
      int accum = 1;
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
                  checkNoOverflow(-FLOOR_SQRT_MAX_INT <= b & b <= FLOOR_SQRT_MAX_INT);
                  b *= b;
               }
         }
      }
   }

   static final int FLOOR_SQRT_MAX_INT = 46340;




   /**
    * Negates the input throwing an exception if it can't negate it.
    *
    * @param value  the value to negate
    * @return the negated value
    * @throws ArithmeticException if the value is {@link Integer#MIN_VALUE}
    */
   public static int safeNegate(int value)
   {
      if (value == Integer.MIN_VALUE) throw new ArithmeticException();
      return -value;
   }

   /**
    * Returns the absolute value of the given {@code int} value. If the argument
    * is not negative, the argument is returned. If the argument is negative, the
    * negation of the argument is returned.
    * <p/>
    * If the value equals {@link Integer#MIN_VALUE} an exception will be thrown to
    * indicate the action is impossible to perform without overflow.
    *
    * @param value the argument whose absolute value is to be determined
    * @return the absolute value of the argument.
    * @throws ArithmeticException if the value is {@link Integer#MIN_VALUE}
    */
   public static int safeAbs(int value)
   {
      if (value == Integer.MIN_VALUE) throw new ArithmeticException();
      return Math.abs(value);
   }


   /**
    * Returns the {@code int} value that is equal to {@code value}, if possible.
    *
    * @param value any value in the range of the {@code int} type
    * @return the {@code int} value that equals {@code value}
    * @throws ArithmeticException if {@code value} is greater than {@link
    *     Integer#MAX_VALUE} or less than {@link Integer#MIN_VALUE}
    */
   public static int safeCast(long value)
   {
      if(Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE) return (int) value;
      throw new ArithmeticException();
   }

   /**
    * Returns the {@code int} nearest in value to {@code value}.
    *
    * @param value any {@code long} value
    * @return the same value cast to {@code int} if it is in the range of the
    *     {@code int} type, {@link Integer#MAX_VALUE} if it is too large,
    *     or {@link Integer#MIN_VALUE} if it is too small
    */
   public static int saturatedCast(long value)
   {
      if (value > Integer.MAX_VALUE) return Integer.MAX_VALUE;
      if (value < Integer.MIN_VALUE) return Integer.MIN_VALUE;
      return (int) value;
   }








   private static final double MIN_INT_AS_DOUBLE = -0x1p31;
   private static final double MAX_INT_AS_DOUBLE = 0x1p31 - 1.0;

   /**
    * Returns the {@code int} value that is equal to {@code x} rounded with the specified rounding
    * mode, if possible.
    *
    * @throws ArithmeticException if
    *         <ul>
    *         <li>{@code x} is infinite or NaN
    *         <li>{@code x}, after being rounded to a mathematical integer using the specified
    *         rounding mode, is either less than {@code Integer.MIN_VALUE} or greater than {@code
    *         Integer.MAX_VALUE}
    *         <li>{@code x} is not a mathematical integer and {@code mode} is
    *         {@link RoundingMode#UNNECESSARY}
    *         </ul>
    */
   public static int round(double x, RoundingMode mode)
   {
      double z = Numbers.roundIntermediate(x, mode);
      if(!(z > MIN_INT_AS_DOUBLE - 1.0 & z < MAX_INT_AS_DOUBLE + 1.0))
            throw new ArithmeticException();
      return (int) z;
   }


   /**
    * Returns {@code true} if {@code x} represents a power of two.
    * <p/>
    * This differs from {@code Integer.bitCount(x) == 1}, because
    * {@code Integer.bitCount(Integer.MIN_VALUE) == 1}, but
    * {@link Integer#MIN_VALUE} is not a power of two.
    */
   public static boolean isPowerOfTwo(int x)
   {
      return x > 0 & (x & (x - 1)) == 0;
   }



   /**
    * The following method will return a random number between <code>min</code>
    * and <code>max</code> minus one. For example if you pass
    * in the numbers 5 & 10 you will consistently get random numbers
    * of 5,6,7,8, and 9 but will never get 10.
    * <p>
    * @param min    The minimum number the random number should be
    * @param max    One more than the maximum number the random should be.
    */
   public static int random(int min, int max)
   {
      if(min >= max) throw new IllegalArgumentException("Max value must be greater than min value");
      return (int) ((Math.random() * (max-min)) + min);
   }


   /**
    * Returns the difference between the range's upper value and its lower value as
    * a long.
    *
    * @param range The range to calculate a difference on
    * @return The difference between the upper and lower bound of the given range
    * @throws NullPointerException If the specified range is {@code null}
    */
   public static long between(Range<Integer> range)
   {
      Objects.notNull(range);
      return range.getUpper().longValue() - range.getLower().longValue();
   }







   /**
    * Returns an ordering that orders two {@code int} arrays using their natural
    * order. When one array is a prefix of the other, this treats the shorter array
    * as the lesser. For example,
    * <p/>
    * {@code [] < [1] < [1, 2] < [2]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(int[], int[])}.
    */
   public static Ordering<int[]> natural()
   {
      return LexicographicalOrdering.NATURAL;
   }

   /**
    * Returns an ordering that orders two {@code int} arrays using the reverse of their
    * natural order. When one array is a prefix of the other, treats the shorter array as
    * the lesser. For example,
    * <p/>
    * {@code [] < [2] < [1], [1, 2]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(int[], int[])}.
    */
   public static Ordering<int[]> reversed()
   {
      return LexicographicalOrdering.REVERSED;
   }

   @SuppressWarnings("SuspiciousNameCombination")
   private static abstract class LexicographicalOrdering extends Ordering<int[]> {
      private static final Ordering<int[]> NATURAL = new LexicographicalOrdering() {
         @Override int compare(int left, int right)
         {
            return Integer.compare(left, right);
         }
      };
      private static final Ordering<int[]> REVERSED = new LexicographicalOrdering() {
         @Override int compare(int left, int right)
         {
            return Integer.compare(right, left);
         }
      };
      @Override public int compare(int[] left, int[] right)
      {
         int minLength = Math.min(left.length, right.length);
         for (int i = 0; i < minLength; i++) {
            int result = compare(left[i], right[i]);
            if (result != 0) return result;
         }
         return Integer.signum(left.length - right.length);
      }
      abstract int compare(int left, int right);
   }










   // Argument checker

   /**
    * Argument checking utility that will throw a {@link NullPointerException} if the given
    * array is {@code null} or an {@link IllegalArgumentException} if it's length is zero.
    *
    * @param array  the array to check for nullity and length
    * @return {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code array} is {@code null}
    * @throws IllegalArgumentException if {@code array}'s length is zero
    */
   public static int[] notEmpty(int[] array)
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
   public static int[] notEmpty(int[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      if(array.length < 1) throw new IllegalArgumentException(argName);
      return array;
   }












   // Private methods
   private static void checkNoOverflow(boolean condition)
   {
      if(!condition) throw new ArithmeticException();
   }
}
