/**
 * Created By: cfloersch
 * Date: 7/28/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import java.util.Arrays;

import static xpertss.lang.Numbers.gte;
import static xpertss.lang.Objects.notNull;

/**
 * Static utility methods pertaining to chars.
 */
public final class Characters {

   private Characters() { }






   // Array helpers


   /**
    * Simple lazy man's utility to convert a single byte or a collection of bytes into a
    * byte array.
    */
   public static char[] toArray(char ... b)
   {
      return b;   // this is safe to do as long as we are primitives. Can't do it to objects.
   }

   /**
    * Null safe method to clone a given array. If the array is {@code null} then {@code null}
    * will be returned. Otherwise, a clone of the array is returned.
    *
    * @param input The input array to clone
    * @return The cloned array or {@code null} if the array was {@code null}
    */
   public static char[] clone(char[] input)
   {
      return (input == null) ? null : input.clone();
   }

   /**
    * Null safe method to sort an array using the elements natural ordering.
    *
    * @param input The input array to sort
    * @return The sorted array or {@code null} if the array was {@code null}
    */
   public static char[] sort(char[] input)
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
   public static boolean isEmpty(char[] array)
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
   public static int size(char[] array)
   {
      return (array == null) ? 0 : array.length;
   }


   /**
    * Returns an empty byte array if the specified data array is {@code null}, otherwise
    * it returns the data array.

    * @param data The data array
    * @return the data array if not null or a zero length byte array
    */
   public static char[] emptyIfNull(char[] data)
   {
      return (data == null) ? new char[0] : data;
   }

   /**
    * Returns an empty byte array if the specified data array is {@code null}, otherwise
    * it returns the data array.

    * @param data The data array
    * @return the data array if not null or a zero length byte array
    */
   public static Character[] emptyIfNull(Character[] data)
   {
      return (data == null) ? new Character[0] : data;
   }




   /**
    * Returns {@code true} if target is present as an element anywhere in array.
    */
   public static boolean contains(char[] array, char target)
   {
      for (char value : emptyIfNull(array)) {
         if (value == target) return true;
      }
      return false;
   }

   /**
    * Returns the index of the first appearance of the target value in the array.
    */
   public static int indexOf(char[] array, char target)
   {
      for (int i = 0; array != null && i < array.length; i++) {
         if (array[i] == target) return i;
      }
      return -1;
   }

   /**
    * Returns the index of the last appearance of the target value in the array.
    */
   public static int lastIndexOf(char[] array, char target)
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
   public static char first(char[] array)
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
   public static char last(char[] array)
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
   public static char[] subset(char[] src, int offset, int length)
   {
      char[] result = new char[Numbers.gt(0, length, "length must be greater than 0")];
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
   public static char[] append(char[] array, char ... items)
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
   public static char[] prepend(char[] array, char ... items)
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
   public static char[] insert(int idx, char[] array, char ... items)
   {
      char[] result = new char[Objects.notNull(array).length + size(items)];
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
   public static boolean regionMatches(char[] src, int sOffset, char[] other, int oOffset, int len)
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
   public static boolean startsWith(char[] src, char[] other, int oOffset, int len)
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
   public static boolean startsWith(char[] src, char[] other)
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
   public static boolean endsWith(char[] src, char[] other, int oOffset, int len)
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
   public static boolean endsWith(char[] src, char[] other)
   {
      return regionMatches(src, src.length - other.length, other, 0, other.length);
   }













   /**
    * Returns {@code true} if the character is A-Z or a-z in ascii, {@code false}
    * otherwise.
    */
   public static boolean isAsciiLetter(char c)
   {
      return (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z');
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
   public static char[] notEmpty(char[] array)
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
   public static char[] notEmpty(char[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      if(array.length < 1) throw new IllegalArgumentException(argName);
      return array;
   }

}
