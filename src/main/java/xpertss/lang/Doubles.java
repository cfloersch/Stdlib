package xpertss.lang;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.String.format;

/**
 * Static utility methods pertaining to doubles
 *
 * @author cfloersch
 * Date: 12/11/12
 */
@SuppressWarnings({ "UnnecessaryBoxing", "UnnecessaryUnboxing", "UnusedDeclaration" })
public final class Doubles {



   public static final Long MAX_LONG_AS_DOUBLE = 9007199254740992L;
   public static final Long MIN_LONG_AS_DOUBLE = -9007199254740992L;


   private Doubles() { }

   /**
    * Parse the given string into a double or return the specified default if there
    * is an error parsing the string.
    */
   public static double parse(CharSequence str, double def)
   {
      try { return Double.parseDouble(CharSequences.toString(str)); } catch (Exception e) { return def; }
   }







   /**
    * Simple utility method to take an arbitrary number of doubles and convert them
    * into an array using Java's varargs.
    */
   public static double[] toArray(double ... i)
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
   public static double[] clone(double[] input)
   {
      return (input == null) ? null : input.clone();
   }

   /**
    * Null safe method to sort an array using the elements natural ordering.
    *
    * @param input The input array to sort
    * @return The sorted array or {@code null} if the array was {@code null}
    */
   public static double[] sort(double[] input)
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
   public static boolean isEmpty(double[] array)
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
   public static int size(double[] array)
   {
      return (array == null) ? 0 : array.length;
   }




   /**
    *
    *
    * Returns an empty double array if the specified data array is {@code null},
    * otherwise it returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length double array
    */
   public static double[] emptyIfNull(double[] data)
   {
      return (data == null) ? new double[0] : data;
   }

   /**
    * Returns an empty double array if the specified data array is {@code null},
    * otherwise it returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length double array
    */
   public static Double[] emptyIfNull(Double[] data)
   {
      return (data == null) ? new Double[0] : data;
   }




   /**
    * Returns {@code true} if target is present as an element anywhere in array.
    */
   public static boolean contains(double[] array, double target)
   {
      for (double value : emptyIfNull(array)) {
         if (value == target) return true;
      }
      return false;
   }

   /**
    * Returns the index of the first appearance of the target value in the array.
    */
   public static int indexOf(double[] array, double target)
   {
      for (int i = 0; array != null && i < array.length; i++) {
         if (array[i] == target) return i;
      }
      return -1;
   }

   /**
    * Returns the index of the last appearance of the target value in the array.
    */
   public static int lastIndexOf(double[] array, double target)
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
   public static double first(double[] array)
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
   public static double last(double[] array)
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
   public static double[] subset(double[] src, int offset, int length)
   {
      double[] result = new double[Numbers.gt(0, length, "length must be greater than 0")];
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
   public static double[] append(double[] array, double ... items)
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
   public static double[] prepend(double[] array, double ... items)
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
   public static double[] insert(int idx, double[] array, double ... items)
   {
      double[] result = new double[Objects.notNull(array).length + size(items)];
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
   public static Double[] toObject(double ... items)
   {
      Double[] data = new Double[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = Double.valueOf(items[i]);
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array.
    *
    * @throws NullPointerException If the array or any of its elements are {@code null}
    */
   public static double[] toPrimitive(Double ... items)
   {
      double[] data = new double[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = items[i].doubleValue();
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array. This will use the
    * specified default value for all {@code null} items.
    *
    * @throws NullPointerException If the array is {@code null}
    */
   public static double[] toPrimitive(Double[] items, double def)
   {
      double[] data = new double[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = (items[i] != null) ? items[i].doubleValue() : def;
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
   public static double max(double ... items)
   {
      double max = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         max = (Double.compare(max, items[i]) > 0) ? max : items[i];
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
   public static double min(double ... items)
   {
      double min = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         min = (Double.compare(min, items[i]) < 0) ? min : items[i];
      }
      return min;
   }



   /**
    * Find the "best guess" middle value among the given set of numbers. If there
    * is an even number of values, then the two middle values are summed together
    * and divided by two.
    * <pre>
    *    Doubles.median(1)            = 1
    *    Doubles.median(1,2,3,4)      = 2.5
    *    Doubles.median(1,2,4,6)      = 3
    *    Doubles.median(1,2,3,4,5)    = 3
    *    Doubles.median(1,3,2,5,4)    = 3
    * </pre>
    *
    * @param set set of items to obtain the median from
    * @return The median value from the set
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static double median(double ... set)
   {
      double[] sorted = sort(notEmpty(set).clone());
      int mid = (sorted.length - 1) / 2;
      return (sorted.length % 2 != 0) ? sorted[mid] :
            (sorted[mid] + sorted[mid + 1]) / 2;
   }

   /**
    * Find the most frequently occurring item(s) returning an empty array if all
    * items are unique.
    * <p>
    * If there are multiple most frequent items because they all have the same
    * count within the set, the returned array will include them all sorted from
    * smallest to largest.
    * <pre>
    *    Doubles.mode(1,2,3,4)          = [ ]
    *    Doubles.mode(4,3,2,1)          = [ ]
    *    Doubles.mode(2,2,1,1,3,3,4)    = [1,2,3]
    *    Doubles.mode(1,3,2,3,2,4)      = [2,3]
    *    Doubles.mode(1,3,3,3,2,2,4)    = [3]
    * </pre>
    *
    * @param items set of items to obtain the most populous item from
    * @return The item that occurred the most frequently
    * @throws NullPointerException If the supplied set is {@code null}
    * @throws IllegalArgumentException If the set has a length of zero
    */
   public static double[] mode(double ... items)
   {
      double[] sorted = sort(notEmpty(items).clone());
      double[] result = new double[sorted.length/2];
      int maxCount = 0, count = 1, pos = 0;
      double last = sorted[0];
      for(int i = 1; i < sorted.length; i++) {
         double next = sorted[i];
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
    * @throws ArithmeticException If any of the items are not finite
    */
   public static double mean(double ... items)
   {
      Numbers.MeanAccumulator accumulator = new Numbers.MeanAccumulator();
      for(double item : notEmpty(items)) {
         if(!isFinite(item)) throw new ArithmeticException();
         accumulator.add(item);
      }
      return accumulator.mean();
   }














   /**
    * Returns {@code true} if {@code x} is exactly equal to {@code 2^k} for some finite
    * integer {@code k}.
    */
   public static boolean isPowerOfTwo(double x)
   {
      return x > 0.0 && isFinite(x) &&
               Longs.isPowerOfTwo(Numbers.getSignificand(x));
   }





   /**
    * Doubles can only accurately represent integer numbers with its 53 bit mantissa.
    * This method will cast a {@code long} to a {@code double} only if its range falls
    * within the range representable by a double without losing precision.
    * <p>
    * The acceptable range of a long is -9007199254740992L &lt;= x &lt;= 9007199254740992L
    *
    *
    * @param value any value in the range of the {@code double} type
    * @return the {@code double} value that equals {@code value}
    * @throws ArithmeticException if {@code value} is greater than 9007199254740992L
    *    or less than -9007199254740992L
    */
   public static double safeCast(long value)
   {
      if(MIN_LONG_AS_DOUBLE <= value && value <= MAX_LONG_AS_DOUBLE) return (double) value;
      throw new ArithmeticException();
   }




   /**
    * Returns {@code true} if the specified value represents a real number. This is
    * equivalent to, but not necessarily implemented as,
    * {@code !(Double.isInfinite(value) || Double.isNaN(value))}.
    */
   public static boolean isFinite(double value)
   {
      return NEGATIVE_INFINITY < value & value < POSITIVE_INFINITY;
   }


   /**
    * Returns the difference between the range's upper value and its lower value as a
    * BigDecimal.
    * <p>
    * Unfortunately, it is possible the difference between lower and upper of a range
    * measured in doubles could overflow a signed double's capacity to represent it.
    *
    * @param range The range to calculate a difference on
    * @return The difference between the upper and lower bound of the given range
    * @throws NullPointerException If the specified range is {@code null}
    */
   public static BigDecimal between(Range<Double> range)
   {
      Objects.notNull(range);
      return BigDecimal.valueOf(range.getUpper()).subtract(BigDecimal.valueOf(range.getLower()));
   }






   /**
    * Returns an ordering that orders two {@code double} arrays using their natural
    * order. When one array is a prefix of the other, this treats the shorter array
    * as the lesser. For example,
    * <p>
    * {@code [] < [1] < [1, 2] < [2]}.
    * <p>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(double[], double[])}.
    */
   public static Comparator<double[]> natural()
   {
      return LexicographicalOrdering.NATURAL;
   }

   /**
    * Returns an ordering that orders two {@code double} arrays using the reverse of their
    * natural order. When one array is a prefix of the other, treats the shorter array as
    * the lesser. For example,
    * <p>
    * {@code [] < [2] < [1], [1, 2]}.
    * <p>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(double[], double[])}.
    */
   public static Comparator<double[]> reversed()
   {
      return LexicographicalOrdering.REVERSED;
   }

   private static abstract class LexicographicalOrdering implements Comparator<double[]> {
      private static final Comparator<double[]> NATURAL = new LexicographicalOrdering() {
         @Override int compare(double left, double right)
         {
            return Double.compare(left, right);
         }
      };
      private static final Comparator<double[]> REVERSED = new LexicographicalOrdering() {
         @Override int compare(double left, double right)
         {
            return Double.compare(right, left);
         }
      };
      @Override public int compare(double[] left, double[] right)
      {
         int minLength = Math.min(left.length, right.length);
         for (int i = 0; i < minLength; i++) {
            int result = compare(left[i], right[i]);
            if (result != 0) return result;
         }
         return Integer.signum(left.length - right.length);
      }
      abstract int compare(double left, double right);
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
   public static double[] notEmpty(double[] array)
   {
      if(array == null) throw new NullPointerException();
      if(array.length < 1) throw new IllegalArgumentException();
      return array;
   }

   /**
    * Argument checking utility that will throw a {@link NullPointerException} if the given
    * array is {@code null} or an {@link IllegalArgumentException} if it's length is zero.
    * <p>
    * The resulting exception will contain the given {@code argName} as the message if
    * thrown.
    *
    * @param array  the array to check for nullity and length
    * @param argName The argument name being inspected
    * @return {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code array} is {@code null}
    * @throws IllegalArgumentException if {@code array}'s length is zero
    */
   public static double[] notEmpty(double[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      if(array.length < 1) throw new IllegalArgumentException(format("%s is empty", argName));
      return array;
   }

}
