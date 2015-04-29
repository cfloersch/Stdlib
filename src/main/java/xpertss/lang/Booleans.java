package xpertss.lang;


import xpertss.util.Ordering;

import static java.lang.String.format;
import static xpertss.lang.BooleanStyle.*;

/**
 * Static utility methods pertaining to booleans
 *
 * @author cfloersch
 * Date: 12/13/12
 */
public final class Booleans {

   private static final BooleanStyle[] styles = {
                           TrueFalseIgnoreCase, TrueFalseAnyChar,
                           YesNoIgnoreCase, YesNoAnyChar,
                           OnOffIgnoreCase, ZeroOne };

   private Booleans() { }



   /**
    * Returns {@code true} if the specified string is not {@code null}, and it equals "on",
    * "true", "yes", "y", or "t" ignoring case. It returns {@code false} otherwise.
    *
    * @see xpertss.lang.BooleanStyle
    */
   public static boolean parse(CharSequence str)
   {
      return BooleanStyle.parse(str, styles);
   }




   // Array helpers

   /**
    * Simple lazy man's utility to convert a single boolean or a collection of booleans
    * into a boolean array.
    */
   public static boolean[] toArray(boolean ... b)
   {
      return b;
   }


   /**
    * Null safe method to clone a given array. If the array is {@code null} then {@code null}
    * will be returned. Otherwise, a clone of the array is returned.
    *
    * @param input The input array to clone
    * @return The cloned array or {@code null} if the array was {@code null}
    */
   public static boolean[] clone(boolean[]  input)
   {
      return (input == null) ? null : input.clone();
   }


   /**
    * Checks if an array is empty or {@code null}.
    *
    * @param array The array to test
    * @return {@code true} if the array is empty or {@code null}
    */
   public static boolean isEmpty(boolean[] array)
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
   public static int size(boolean[] array)
   {
      return (array == null) ? 0 : array.length;
   }






   /**
    * Returns an empty boolean array if the specified data array is {@code null},
    * otherwise it returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length array
    */
   public static boolean[] emptyIfNull(boolean[] data)
   {
      return (data == null) ? new boolean[0] : data;
   }

   /**
    * Returns an empty boolean array if the specified data array is {@code null},
    * otherwise it returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length array
    */
   public static Boolean[] emptyIfNull(Boolean[] data)
   {
      return (data == null) ? new Boolean[0] : data;
   }





   /**
    * Returns {@code true} if target is present as an element anywhere in array.
    *
    * @return {@code true} if array[i] == target for some value of {@code i}
    */
   public static boolean contains(boolean[] array, boolean target)
   {
      for (boolean value : emptyIfNull(array)) {
         if (value == target) return true;
      }
      return false;
   }

   /**
    * Returns the index of the first appearance of the target value in the array.
    */
   public static int indexOf(boolean[] array, boolean target)
   {
      for (int i = 0; array != null && i < array.length; i++) {
         if (array[i] == target) return i;
      }
      return -1;
   }

   /**
    * Returns the index of the last appearance of the target value in the array.
    */
   public static int lastIndexOf(boolean[] array, boolean target)
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
   public static boolean first(boolean[] array)
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
   public static boolean last(boolean[] array)
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
   public static boolean[] subset(boolean[] src, int offset, int length)
   {
      boolean[] result = new boolean[Numbers.gt(0, length, "length must be greater than 0")];
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
   public static boolean[] append(boolean[] array, boolean ... items)
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
   public static boolean[] prepend(boolean[] array, boolean ... items)
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
   public static boolean[] insert(int idx, boolean[] array, boolean ... items)
   {
      boolean[] result = new boolean[Objects.notNull(array).length + size(items)];
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
   public static Boolean[] toObject(boolean ... items)
   {
      Boolean[] data = new Boolean[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = (items[i]) ? Boolean.TRUE : Boolean.FALSE;
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array.
    *
    * @throws NullPointerException If the array or any of its elements are {@code null}
    */
   public static boolean[] toPrimitive(Boolean ... items)
   {
      boolean[] data = new boolean[items.length];
      for(int i = 0; i < data.length; i++) {
         //noinspection UnnecessaryUnboxing
         data[i] = items[i].booleanValue();
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array. This will use the
    * specified default value for all {@code null} items.
    *
    * @throws NullPointerException If the array is {@code null}
    */
   public static boolean[] toPrimitive(Boolean[] items, boolean def)
   {
      boolean[] data = new boolean[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = (items[i] != null) ? items[i] : def;
      }
      return data;
   }




   /**
    * Returns {@code true} if the specified boolean is not {@code null} and its
    * value is {@code true}.
    */
   public static boolean isTrue(Boolean b)
   {
      return b != null && b;
   }

   /**
    * Returns {@code true} if the specified boolean is not {@code null} and its
    * value is {@code false}.
    */
   public static boolean isFalse(Boolean b)
   {
      return b != null && !b;
   }

   /**
    * Returns {@code true} if the specified boolean is {@code null} or its value is
    * {@code false}.
    */
   public static boolean isNotTrue(Boolean b)
   {
      return b == null || !b;
   }

   /**
    * Returns {@code true} if the specified boolean is {@code null} or its value is
    * {@code true}.
    */
   public static boolean isNotFalse(Boolean b)
   {
      return b == null || b;
   }




   /**
    * Negates the specified boolean.
    * <p/>
    * If {@code null} is passed in, {@code null} will be returned.
    */
   public static Boolean negate(Boolean bool)
   {
      if (bool == null) return null;
      return bool ? Boolean.FALSE : Boolean.TRUE;
   }




   /**
    * Performs an <b>and</b> on a set of booleans.
    *
    * @throws IllegalArgumentException if the set is empty
    */
   public static boolean and(boolean ... set)
   {
      for (boolean element : notEmpty(set)) {
         if (!element) return false;
      }
      return true;
   }

   /**
    * Performs an <b>or</b> on a set of booleans.
    *
    * @throws IllegalArgumentException if the set is empty
    */
   public static boolean or(boolean ... set)
   {
      for (boolean element : notEmpty(set)) {
         if (element) return true;
      }
      return false;
   }

   /**
    * Performs an <b>xor</b> on a set of booleans.
    *
    * @throws IllegalArgumentException if the set is empty
    */
   public static boolean xor(boolean ... set)
   {
      // Loops through array, comparing each item
      int trueCount = 0;
      for (boolean element : notEmpty(set)) {
         // If item is true, and trueCount is < 1, increments count
         // Else, xor fails
         if (element) {
            if (trueCount < 1) {
               trueCount++;
            } else {
               return false;
            }
         }
      }

      // Returns true if there was exactly 1 true item
      return trueCount == 1;
   }

















   /**
    * Returns an ordering that orders two {@code boolean} arrays using their natural
    * order. When one array is a prefix of the other, this treats the shorter array
    * as the lesser. For example,
    * <p/>
    * {@code [] < [false] < [false, true] < [true]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with {@link
    * java.util.Arrays#equals(boolean[], boolean[])}.
    */
   public static Ordering<boolean[]> natural()
   {
      return LexicographicalOrdering.NATURAL;
   }

   /**
    /**
    * Returns an ordering that orders two {@code byte} arrays using the reverse of their
    * natural order. When one array is a prefix of the other, treats the shorter array as
    * the lesser. For example,
    * <p/>
    * {@code [] < [true] < [false], [false, true]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with {@link
    * java.util.Arrays#equals(boolean[], boolean[])}.
    */
   public static Ordering<boolean[]> reversed()
   {
      return LexicographicalOrdering.REVERSED;
   }

   @SuppressWarnings("SuspiciousNameCombination")
   private static abstract class LexicographicalOrdering extends Ordering<boolean[]> {
      private static final Ordering<boolean[]> NATURAL = new LexicographicalOrdering() {
         @Override int compare(boolean left, boolean right)
         {
            return Boolean.compare(left, right);
         }
      };
      private static final Ordering<boolean[]> REVERSED = new LexicographicalOrdering() {
         @Override int compare(boolean left, boolean right)
         {
            return Boolean.compare(right, left);
         }
      };
      @Override public int compare(boolean[] left, boolean[] right)
      {
         int minLength = Math.min(left.length, right.length);
         for (int i = 0; i < minLength; i++) {
            int result = compare(left[i], right[i]);
            if (result != 0) return result;
         }
         return Integer.signum(left.length - right.length);
      }
      abstract int compare(boolean left, boolean right);
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
   public static boolean[] notEmpty(boolean[] array)
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
   public static boolean[] notEmpty(boolean[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      if(array.length < 1) throw new IllegalArgumentException(format("%s is empty", argName));
      return array;
   }




}
