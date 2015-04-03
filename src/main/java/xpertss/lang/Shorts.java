package xpertss.lang;

import xpertss.util.Ordering;

import java.util.Arrays;

/**
 * Static utility methods pertaining to shorts
 *
 * @author cfloersch
 * Date: 12/7/12
 */
@SuppressWarnings({ "UnnecessaryBoxing", "UnnecessaryUnboxing", "UnusedDeclaration" })
public final class Shorts {

   private Shorts() { }

   /**
    * Parse the given string into a short or return the specified default if there is an
    * error parsing the string.
    */
   public static short parse(String str, short def)
   {
      try { return Short.parseShort(str); } catch (Exception e) { return def; }
   }

   /**
    * Parse the given string into a short using the specified radix or return the
    * specified default if there is an error parsing the string.
    */
   public static short parse(String str, int radix, short def)
   {
      try { return Short.parseShort(str, radix); } catch (Exception e) { return def; }
   }





   /**
    * Simple utility method to take an arbitrary number of shorts and convert them into
    * an array using Java's varargs.
    */
   public static short[] toArray(short ... i)
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
   public static short[] clone(short[] input)
   {
      return (input == null) ? null : input.clone();
   }

   /**
    * Null safe method to sort an array using the elements natural ordering.
    *
    * @param input The input array to sort
    * @return The sorted array or {@code null} if the array was {@code null}
    */
   public static short[] sort(short[] input)
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
   public static boolean isEmpty(short[] array)
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
   public static int size(short[] array)
   {
      return (array == null) ? 0 : array.length;
   }



   /**
    * Returns an empty array if the specified data array is {@code null}, otherwise it
    * returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length array
    */
   public static short[] emptyIfNull(short[] data)
   {
      return (data == null) ? new short[0] : data;
   }

   /**
    * Returns an empty array if the specified data array is {@code null}, otherwise it
    * returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length array
    */
   public static Short[] emptyIfNull(Short[] data)
   {
      return (data == null) ? new Short[0] : data;
   }




   /**
    * Returns {@code true} if target is present as an element anywhere in the array.
    */
   public static boolean contains(short[] array, short target)
   {
      for (short value : emptyIfNull(array)) {
         if (value == target) return true;
      }
      return false;
   }

   /**
    * Returns the index of the first appearance of the target value in the array.
    */
   public static int indexOf(short[] array, short target)
   {
      for (int i = 0; array != null && i < array.length; i++) {
         if (array[i] == target) return i;
      }
      return -1;
   }

   /**
    * Returns the index of the last appearance of the target value in the array.
    */
   public static int lastIndexOf(short[] array, short target)
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
   public static short first(short[] array)
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
   public static short last(short[] array)
   {
      if(array.length < 1) throw new IllegalArgumentException();
      return array[array.length - 1];
   }





   /**
    * Return a new array which contains the subset of the specified src array identified
    * by the given offset and length.
    *
    * @param src The source array to derive the subset from
    * @param offset The offset into the source array to begin the subset
    * @param length The number of elements from the offset to include
    * @return An array of the specified length containing a subset of elements
    *          from the source array
    * @throws IllegalArgumentException if the src array is {@code null} or zero length,
    *          the offset is less than zero, or the length is less than 1.
    * @throws ArrayIndexOutOfBoundsException if the offset or length are invalid
    */
   public static short[] subset(short[] src, int offset, int length)
   {
      short[] result = new short[Numbers.gt(0, length, "length must be greater than 0")];
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
   public static short[] append(short[] array, short ... items)
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
   public static short[] prepend(short[] array, short ... items)
   {
      return insert(0, array, items);
   }


   /**
    * Creates and returns a new array which inserts the specified items into the given
    * array elements at the specified index.
    *
    * @param idx The index position into the array to insert the given items
    * @param array The base array of items to insert into
    * @param items The elements to insert into the array
    * @return A new array with all the items
    * @throws IndexOutOfBoundsException If the given index is invalid
    * @throws NullPointerException If array is {@code null}
    */
   public static short[] insert(int idx, short[] array, short ... items)
   {
      short[] result = new short[Objects.notNull(array).length + size(items)];
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
   public static Short[] toObject(short ... items)
   {
      Short[] data = new Short[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = Short.valueOf(items[i]);
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array.
    *
    * @throws NullPointerException If the array or any of its elements are {@code null}
    */
   public static short[] toPrimitive(Short ... items)
   {
      short[] data = new short[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = items[i].shortValue();
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array. This will use the
    * specified default value for all {@code null} items.
    *
    * @throws NullPointerException If the array is {@code null}
    */
   public static short[] toPrimitive(Short[] items, short def)
   {
      short[] data = new short[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = (items[i] != null) ? items[i].shortValue() : def;
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
   public static short max(short ... items)
   {
      short max = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         max = (max > items[i]) ? max : items[i];
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
   public static short min(short ... items)
   {
      short min = notEmpty(items)[0];
      for(int i = 1; i < items.length; i++) {
         min = (min < items[i]) ? min : items[i];
      }
      return min;
   }








   /**
    * Negates the input throwing an exception if it can't negate it.
    *
    * @param value  the value to negate
    * @return the negated value
    * @throws ArithmeticException if the value is {@link Short#MIN_VALUE}
    */
   public static short safeNegate(short value)
   {
      if (value == Short.MIN_VALUE) throw new ArithmeticException();
      return (short) -value;
   }

   /**
    * Returns the absolute value of the given {@code short} value. If the argument
    * is not negative, the argument is returned. If the argument is negative, the
    * negation of the argument is returned.
    * <p/>
    * If the value equals {@link Short#MIN_VALUE} an exception will be thrown to
    * indicate the action is impossible to perform without overflow.
    *
    * @param value the argument whose absolute value is to be determined
    * @return the absolute value of the argument.
    * @throws ArithmeticException if the value is {@link Short#MIN_VALUE}
    */
   public static short safeAbs(short value)
   {
      return (value < 0) ? safeNegate(value) : value;
   }

   /**
    * Returns the {@code short} value that is equal to {@code value}, if possible.
    *
    * @param value any value in the range of the {@code short} type
    * @return the {@code short} value that equals {@code value}
    * @throws ArithmeticException if {@code value} is greater than {@link
    *     Short#MAX_VALUE} or less than {@link Short#MIN_VALUE}
    */
   public static short safeCast(long value)
   {
      short result = (short) value;
      if(result != value) throw new ArithmeticException();
      return result;
   }

   /**
    * Returns the {@code short} nearest in value to {@code value}.
    *
    * @param value any {@code long} value
    * @return the same value cast to {@code short} if it is in the range of the
    *     {@code short} type, {@link Short#MAX_VALUE} if it is too large,
    *     or {@link Short#MIN_VALUE} if it is too small
    */
   public static short saturatedCast(long value)
   {
      if (value > Short.MAX_VALUE) return Short.MAX_VALUE;
      if (value < Short.MIN_VALUE) return Short.MIN_VALUE;
      return (short) value;
   }









   /**
    * Returns the difference between the range's upper value and its lower value as
    * an int.
    *
    * @param range The range to calculate a difference on
    * @return The difference between the upper and lower bound of the given range
    * @throws NullPointerException If the specified range is {@code null}
    */
   public static int between(Range<Short> range)
   {
      Objects.notNull(range);
      return range.getUpper() - range.getLower();
   }





   /**
    * Returns an ordering that orders two {@code short} arrays using their natural
    * order. When one array is a prefix of the other, this treats the shorter array
    * as the lesser. For example,
    * <p/>
    * {@code [] < [1] < [1, 2] < [2]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(short[], short[])}.
    */
   public static Ordering<short[]> natural()
   {
      return LexicographicalOrdering.NATURAL;
   }

   /**
    * Returns an ordering that orders two {@code short} arrays using the reverse of their
    * natural order. When one array is a prefix of the other, treats the shorter array as
    * the lesser. For example,
    * <p/>
    * {@code [] < [2] < [1], [1, 2]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(short[], short[])}.
    */
   public static Ordering<short[]> reversed()
   {
      return LexicographicalOrdering.REVERSED;
   }

   @SuppressWarnings("SuspiciousNameCombination")
   private static abstract class LexicographicalOrdering extends Ordering<short[]> {
      private static final Ordering<short[]> NATURAL = new LexicographicalOrdering() {
         @Override int compare(short left, short right)
         {
            return Short.compare(left, right);
         }
      };
      private static final Ordering<short[]> REVERSED = new LexicographicalOrdering() {
         @Override int compare(short left, short right)
         {
            return Short.compare(left, right);
         }
      };
      @Override public int compare(short[] left, short[] right)
      {
         int minLength = Math.min(left.length, right.length);
         for (int i = 0; i < minLength; i++) {
            int result = compare(left[i], right[i]);
            if (result != 0) return result;
         }
         return Integer.signum(left.length - right.length);
      }
      abstract int compare(short left, short right);
   }








   // Argument checking

   /**
    * Argument checking utility that will throw a {@link NullPointerException} if the given
    * array is {@code null} or an {@link IllegalArgumentException} if it's length is zero.
    *
    * @param array  the array to check for nullity and length
    * @return {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code array} is {@code null}
    * @throws IllegalArgumentException if {@code array}'s length is zero
    */
   public static short[] notEmpty(short[] array)
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
   public static short[] notEmpty(short[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      if(array.length < 1) throw new IllegalArgumentException(argName);
      return array;
   }


}
