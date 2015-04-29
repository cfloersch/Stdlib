package xpertss.lang;

import xpertss.util.Ordering;

import java.util.Arrays;

import static java.lang.String.format;

/**
 * Static utility methods pertaining to bytes.
 *
 * @author cfloersch
 * Date: 12/8/12
 */
@SuppressWarnings({ "UnnecessaryBoxing", "UnnecessaryUnboxing", "UnusedDeclaration" })
public final class Bytes {

   private Bytes() { }

   /**
    * Parse the given string into a byte or return the specified default if there
    * is an error parsing the string.
    */
   public static byte parse(CharSequence str, byte def)
   {
      try { return Byte.parseByte(CharSequences.toString(str)); } catch (Exception e) { return def; }
   }

   /**
    * Parse the given string into a byte using the specified radix or return the
    * specified default if there is an error parsing the string.
    */
   public static byte parse(CharSequence str, int radix, byte def)
   {
      try { return Byte.parseByte(CharSequences.toString(str), radix); } catch (Exception e) { return def; }
   }





   // Array helpers


   /**
    * Simple lazy man's utility to convert a single byte or a collection of bytes into a
    * byte array.
    */
   public static byte[] toArray(byte ... b)
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
   public static byte[] clone(byte[] input)
   {
      return (input == null) ? null : input.clone();
   }

   /**
    * Null safe method to sort an array using the elements natural ordering.
    *
    * @param input The input array to sort
    * @return The sorted array or {@code null} if the array was {@code null}
    */
   public static byte[] sort(byte[] input)
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
   public static boolean isEmpty(byte[] array)
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
   public static int size(byte[] array)
   {
      return (array == null) ? 0 : array.length;
   }


   /**
    * Returns an empty byte array if the specified data array is {@code null}, otherwise
    * it returns the data array.

    * @param data The data array
    * @return the data array if not null or a zero length byte array
    */
   public static byte[] emptyIfNull(byte[] data)
   {
      return (data == null) ? new byte[0] : data;
   }

   /**
    * Returns an empty byte array if the specified data array is {@code null}, otherwise
    * it returns the data array.

    * @param data The data array
    * @return the data array if not null or a zero length byte array
    */
   public static Byte[] emptyIfNull(Byte[] data)
   {
      return (data == null) ? new Byte[0] : data;
   }




   /**
    * Returns {@code true} if target is present as an element anywhere in array.
    */
   public static boolean contains(byte[] array, byte target)
   {
      for (byte value : emptyIfNull(array)) {
         if (value == target) return true;
      }
      return false;
   }

   /**
    * Returns the index of the first appearance of the target value in the array.
    */
   public static int indexOf(byte[] array, byte target)
   {
      for (int i = 0; array != null && i < array.length; i++) {
         if (array[i] == target) return i;
      }
      return -1;
   }

   /**
    * Returns the index of the last appearance of the target value in the array.
    */
   public static int lastIndexOf(byte[] array, byte target)
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
   public static byte first(byte[] array)
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
   public static byte last(byte[] array)
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
   public static byte[] subset(byte[] src, int offset, int length)
   {
      byte[] result = new byte[Numbers.gt(0, length, "length must be greater than 0")];
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
   public static byte[] append(byte[] array, byte ... items)
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
   public static byte[] prepend(byte[] array, byte ... items)
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
   public static byte[] insert(int idx, byte[] array, byte ... items)
   {
      byte[] result = new byte[Objects.notNull(array).length + size(items)];
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
   public static Byte[] toObject(byte ... items)
   {
      Byte[] data = new Byte[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = Byte.valueOf(items[i]);
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array.
    *
    * @throws NullPointerException If the array or any of its elements are {@code null}
    */
   public static byte[] toPrimitive(Byte ... items)
   {
      byte[] data = new byte[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = items[i].byteValue();
      }
      return data;
   }

   /**
    * Converts the given object array into a primitive array. This will use the
    * specified default value for all {@code null} items.
    *
    * @throws NullPointerException If the array is {@code null}
    */
   public static byte[] toPrimitive(Byte[] items, byte def)
   {
      byte[] data = new byte[items.length];
      for(int i = 0; i < data.length; i++) {
         data[i] = (items[i] != null) ? items[i].byteValue() : def;
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
   public static byte max(byte ... items)
   {
      byte max = notEmpty(items)[0];
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
   public static byte min(byte ... items)
   {
      byte min = notEmpty(items)[0];
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
    * @throws ArithmeticException if the value is {@link Byte#MIN_VALUE}
    */
   public static byte safeNegate(byte value)
   {
      if (value == Byte.MIN_VALUE) throw new ArithmeticException();
      return (byte) -value;
   }

   /**
    * Returns the absolute value of the given {@code byte} value. If the argument
    * is not negative, the argument is returned. If the argument is negative, the
    * negation of the argument is returned.
    * <p/>
    * If the value equals {@link Byte#MIN_VALUE} an exception will be thrown to
    * indicate the action is impossible to perform without overflow.
    *
    * @param value the argument whose absolute value is to be determined
    * @return the absolute value of the argument.
    * @throws ArithmeticException if the value is {@link Byte#MIN_VALUE}
    */
   public static byte safeAbs(byte value)
   {
      return (value < 0) ? safeNegate(value) : value;
   }

   /**
    * Returns the {@code byte} value that is equal to {@code value}, if
    * possible.
    *
    * @param value any value in the range of the {@code byte} type
    * @return the {@code byte} value that equals {@code value}
    * @throws ArithmeticException if {@code value} is greater than {@link
    *     Byte#MAX_VALUE} or less than {@link Byte#MIN_VALUE}
    */
   public static byte safeCast(long value)
   {
      byte result = (byte) value;
      if(result != value) throw new ArithmeticException();
      return result;
   }

   /**
    * Returns the {@code byte} nearest in value to {@code value}.
    *
    * @param value any {@code long} value
    * @return the same value cast to {@code byte} if it is in the range of the
    *     {@code byte} type, {@link Byte#MAX_VALUE} if it is too large,
    *     or {@link Byte#MIN_VALUE} if it is too small
    */
   public static byte saturatedCast(long value)
   {
      if (value > Byte.MAX_VALUE) return Byte.MAX_VALUE;
      if (value < Byte.MIN_VALUE) return Byte.MIN_VALUE;
      return (byte) value;
   }






   /**
    * This will return true if the supplied byte is an ascii character, false otherwise.
    */
   public static boolean isAscii(byte b)
   {
      return b < 127 && (b >= 32 || b == '\r' || b == '\n' || b == '\t');
   }


   /**
    * Returns the difference between the range's upper value and its lower value
    * as an int.
    *
    * @param range The range to calculate a difference on
    * @return The difference between the upper and lower bound of the given range
    * @throws NullPointerException If the specified range is {@code null}
    */
   public static int between(Range<Byte> range)
   {
      Objects.notNull(range);
      return range.getUpper() - range.getLower();
   }






   /**
    * Returns an ordering that orders two {@code byte} arrays using their natural
    * order. When one array is a prefix of the other, this treats the shorter array
    * as the lesser. For example,
    * <p/>
    * {@code [] < [1] < [1, 2] < [2]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(byte[], byte[])}.
    */
   public static Ordering<byte[]> natural()
   {
      return LexicographicalOrdering.NATURAL;
   }

   /**
    * Returns an ordering that orders two {@code byte} arrays using the reverse of their
    * natural order. When one array is a prefix of the other, treats the shorter array as
    * the lesser. For example,
    * <p/>
    * {@code [] < [2] < [1], [1, 2]}.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(byte[], byte[])}.
    */
   public static Ordering<byte[]> reversed()
   {
      return LexicographicalOrdering.REVERSED;
   }

   @SuppressWarnings("SuspiciousNameCombination")
   private static abstract class LexicographicalOrdering extends Ordering<byte[]> {
      private static final Ordering<byte[]> NATURAL = new LexicographicalOrdering() {
         @Override int compare(byte left, byte right)
         {
            return Byte.compare(left, right);
         }
      };
      private static final Ordering<byte[]> REVERSED = new LexicographicalOrdering() {
         @Override int compare(byte left, byte right)
         {
            return Byte.compare(right, left);
         }
      };
      @Override public int compare(byte[] left, byte[] right)
      {
         int minLength = Math.min(left.length, right.length);
         for (int i = 0; i < minLength; i++) {
            int result = compare(left[i], right[i]);
            if (result != 0) return result;
         }
         return Integer.signum(left.length - right.length);
      }
      abstract int compare(byte left, byte right);
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
   public static byte[] notEmpty(byte[] array)
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
   public static byte[] notEmpty(byte[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      if(array.length < 1) throw new IllegalArgumentException(format("%s is empty", argName));
      return array;
   }






   // Hex functions


   private static final char[] hexDigits = {
      '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
   };



   /**
    * Returns a string of hexadecimal digits from a byte array. Each
    * byte is converted to 2 hex symbols and a space.
    * <p><pre>
    *    0f 23 3a 3f ff
    * </pre>
    */
   public static String toFingerPrint(byte[] ba, int offset, int length)
   {
      StringBuilder builder = new StringBuilder(length * 3);
      int k;
      for (int i = offset; i < offset + length; i++) {
         if(builder.length() > 0) builder.append(" ");
         k = ba[i];
         builder.append(hexDigits[(k >>> 4) & 0x0F]);
         builder.append(hexDigits[ k        & 0x0F]);
      }
      return builder.toString();
   }

   /**
    * Returns a string of hexadecimal digits from a byte array. Each
    * byte is converted to 2 hex symbols and a space.
    * <p><pre>
    *    0f 23 3a 3f ff
    * </pre>
    */
   public static String toFingerPrint(byte[] ba)
   {
      return toFingerPrint(ba, 0, ba.length);
   }







   /**
    * Returns a string of hexadecimal digits from a byte array. Each
    * byte is converted to 2 hex symbols.
    * <p><pre>
    *    0f233a3fff
    * </pre>
    */
   public static String toHexString(byte[] ba, int offset, int length)
   {
      StringBuilder builder = new StringBuilder(length * 3);
      int k;
      for (int i = offset; i < offset + length; i++) {
         k = ba[i];
         builder.append(hexDigits[(k >>> 4) & 0x0F]);
         builder.append(hexDigits[ k        & 0x0F]);
      }
      return builder.toString();
   }


   /**
    * Returns a string of hexadecimal digits from a byte array. Each
    * byte is converted to 2 hex symbols.
    * <p><pre>
    *    0f233a3fff
    * </pre>
    */
   public static String toHexString(byte[] ba)
   {
      return toHexString(ba, 0, ba.length);
   }








   /**
    * Returns a byte array from a string of hexadecimal digits. This assumes
    * that the String does not contain any spaces such as a fingerprint.
    */
   public static byte[] fromHexString(String hex)
   {
      byte[] bytes = new byte[hex.length() / 2];
      String buf = hex.toLowerCase();
      for (int i = 0; i < buf.length(); i += 2) {
         char    left  = buf.charAt(i);
         char    right = buf.charAt(i+1);
         int     index = i / 2;
         if (left < 'a') {
            bytes[index] = (byte)((left - '0') << 4);
         } else {
            bytes[index] = (byte)((left - 'a' + 10) << 4);
         }
         if (right < 'a') {
            bytes[index] += (byte)(right - '0');
         } else {
            bytes[index] += (byte)(right - 'a' + 10);
         }
      }
      return bytes;
   }


}
