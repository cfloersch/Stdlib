package xpertss.lang;


import xpertss.function.Predicates;

import java.io.Serializable;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.lang.String.*;

/**
 * Static utility methods and argument checkers used to process Strings.
 *
 * User: cfloersch
 * Date: 12/9/12
 */
@SuppressWarnings("UnusedDeclaration")
public final class Strings {

   private Strings() { }

   /**
    * A string is empty if it is either null or made up of nothing but spaces.
    */
   public static boolean isEmpty(String str)
   {
      return (str == null || str.trim().length() == 0);
   }

   /**
    * Returns the string as {@code null} if it is {@code null} or empty. Otherwise, this
    * will return the string unmodified.
    */
   public static String nullIfEmpty(String str)
   {
      return isEmpty(str) ? null : str;
   }

   /**
    * Returns the specified default string if the input string is {@code null}.
    */
   public static String ifNull(String str, String def)
   {
      return (isEmpty(str)) ? def : str;
   }

   /**
    * Returns a zero length string if the specified string is {@code null}. Otherwise,
    * this will return the string unmodified.
    */
   public static String emptyIfNull(String str)
   {
      return str == null ? "" : str;
   }

   /**
    * Returns the specified default string if the input string is empty.
    */
   public static String ifEmpty(String str, String def)
   {
      return (isEmpty(str)) ? def : str;
   }

   /**
    * Returns a default string returned from the provided supplier if the input string
    * is empty.
    */
   public static String ifEmpty(String str, Supplier<String> supplier)
   {
      return (isEmpty(str)) ? supplier.get() : str;
   }





   /**
    * Returns a predicate that evaluates to {@code true} if the string being tested
    * {@code equalsIgnoreCase()} the given target or both are {@code null}.
    */
   public static Predicate<String> equalToIgnoreCase(String target)
   {
      return (target == null) ? Predicates.<String>isNull() : new IsEqualToIgnoreCasePredicate(target);
   }

   static class IsEqualToIgnoreCasePredicate implements Predicate<String>, Serializable {
      private final String target;

      private IsEqualToIgnoreCasePredicate(String target)
      {
         this.target = target;
      }

      @Override
      public boolean test(String t)
      {
         return target.equalsIgnoreCase(t);
      }

      @Override
      public int hashCode()
      {
         return target.hashCode();
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof IsEqualToIgnoreCasePredicate) {
            IsEqualToIgnoreCasePredicate that = (IsEqualToIgnoreCasePredicate) obj;
            return target.equals(that.target);
         }
         return false;
      }

      @Override
      public String toString()
      {
         return "IsEqualToIgnoreCase(" + target + ")";
      }

      private static final long serialVersionUID = 0;
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the string being tested
    * {@code isEmpty()}.
    */
   public static Predicate<String> empty()
   {
      return EmptyPredicate.INSTANCE;
   }

   private enum EmptyPredicate implements Predicate<String> {
      INSTANCE;

      @Override public boolean test(String input)
      {
         return isEmpty(input);
      }
   }




   /**
    * Returns a predicate that evaluates to {@code true} if the string being tested
    * {@code startsWith()} the given prefix.
    */
   public static Predicate<String> startsWith(final String prefix)
   {
      return input -> input.startsWith(prefix);
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the string being tested
    * {@code endsWith()} the given suffix.
    */
   public static Predicate<String> endsWith(final String suffix)
   {
      return input -> input.endsWith(suffix);
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the string being tested
    * {@code contains()} the given string.
    */
   public static Predicate<String> contains(final String s)
   {
      return input -> input.contains(s);
   }






   /**
    * Null safe comparison routine that compares a set of strings for equality. Strings
    * are equal if they are both {@code null} or if they are both not {@code null} and
    * the equals method returns {@code true}.
    */
   public static boolean equal(String ... set)
   {
      if(set.length < 1) return false;
      if(set.length > 1) {
         String first = set[0];
         for(int i = 1; i < set.length; i++) {
            if(first == null) {
               if(set[i] != null) return false;
            } else {
               if(!first.equals(set[i])) return false;
            }
         }
      }
      return true;
   }

   /**
    * Null safe comparison routine that compares two strings for equality. Two strings
    * are equal if they are both {@code null} or if they are both not {@code null} and
    * the equalIgnoreCase method returns {@code true}.
    */
   public static boolean equalIgnoreCase(String ... set)
   {
      if(set.length < 1) return false;
      if(set.length > 1) {
         String first = set[0];
         for(int i = 1; i < set.length; i++) {
            if(first == null) {
               if(set[i] != null) return false;
            } else {
               if(!first.equalsIgnoreCase(set[i])) return false;
            }
         }
      }
      return true;
   }



   /**
    * Null safe routine to upper case the supplied string. This will return the string
    * in all upper caps using the default Locale unless its {@code null} in which case
    * it will return {@code null}.
    *
    * @param str A possibly {@code null} string to make upper case
    * @return a upper case variant of the supplied string or {@code null}
    */
   public static String toUpper(String str)
   {
      return (str == null) ? null : str.toUpperCase();
   }

   /**
    * Null safe routine to lower case the supplied string. This will return the string in
    * all lower caps using the default Locale unless its {@code null} in which case it
    * will return {@code null}.
    *
    * @param str A possibly {@code null} string to make lower case
    * @return a lower case variant of the supplied string or {@code null}
    */
   public static String toLower(String str)
   {
      return (str == null) ? null : str.toLowerCase();
   }

   /**
    * This method will return the string with the first letter capitalized. It does not
    * discern words from paragraphs. It simply makes the first character in the string
    * upper case if it can.  In the case where the first character is not a character
    * which can be upper cased then it will simply return the String unchanged.
    *
    * @param str The string to upper case the first character
    * @return The string with the first character upper cased.
    */
   public static String firstCharToUpper(String str)
   {
      if(str == null || str.length() < 1) return str;
      return str.substring(0,1).toUpperCase() + str.substring(1);
   }



   /**
    * This method will return the string with the first letter lower cased. It does not
    * discern words from paragraphs. It simply makes the first character in the string
    * lower case if it can.  In the case where the first character is not a character
    * which can be lower cased then it will simply return the String unchanged.
    *
    * @param str The string to lower case the first character
    * @return The string with the first character lower cased.
    */
   public static String firstCharToLower(String str)
   {
      if(str == null || str.length() < 1) return str;
      return str.substring(0,1).toLowerCase() + str.substring(1);
   }


   /**
    * Null safe trim method that will return {@code null} if the supplied argument is
    * {@code null}, otherwise, it will return the trimmed string.
    */
   public static String trim(String str)
   {
      return (str == null) ? null : str.trim();
   }


   /**
    * Null safe length method that will return 0 if the supplied argument is {@code null},
    * otherwise, it will return the length of the supplied string.
    */
   public static int length(String str)
   {
      return (str == null) ? 0 : str.length();
   }




   /**
    * Null safe contains method that will return {@code true} only if both the input string
    * and the input expression are not {@code null} and the expression is found in the
    * string. This method is case sensitive.
    *
    * @param str The search string
    * @param exp The search expression
    * @return {@code true} if both string and expression are not {@code null} and expression
    *       is contained within the search string.
    */
   public static boolean contains(String str, String exp)
   {
      return (str != null && exp != null) && str.contains(exp);
   }




   /**
    * Truncate the given string to be no larger than <code>length</code>.
    *
    * @param s The string to truncate
    * @param length The length at which to truncate it
    * @return The truncated string or the original if no truncation was necessary
    * @throws NullPointerException If the specified string is {@code null}
    */
   public static String truncate(String s, int length)
   {
      return  (s.length() > length) ? s.substring(0,length) : s;
   }



   /**
    * Removes all the new line and carriage returns found at the end of the given string.
    * Unlike {@link #trim} it does not remove other whitespace characters nor does it
    * affect the beginning of the line.
    * <p>
    * If the specified string is {@code null} then this will return {@code null}.
    */
   public static String chomp(String str)
   {
      if (str == null) return null;
      for(int i = str.length() - 1; i >= 0; i--) {
         char c = str.charAt(i);
         if(c != '\n' && c != '\r') return str.substring(0, i + 1);
      }
      return str;
   }


   /**
    * Counts the number of times expression is found within a specified string.
    * <p>
    * If the specified string or expression are {@code null}, this will return 0.
    */
   public static int count(String str, String exp)
   {
      if(str == null || exp == null) return 0;
      int idx = 0, count = 0;
      while(idx < str.length() && idx >= 0) {
         idx = str.indexOf(exp, idx);
         if(idx != -1) {
            count++;
            idx += exp.length();
         }
      }
      return count;
   }






   /**
    * Removes single or double quotes from the given string if its quoted.
    * <p>
    * <pre>
    *   for input string = "mystr1" output will be = mystr1
    *   for input string = 'mystr2' output will be = mystr2
    *   for input string = "mystr3 output will be = "mystr3
    *   for input string = 'mystr4 output will be = 'mystr4
    *   for input string = mystr5" output will be = mystr5"
    *   for input string = mystr6' output will be = mystr6'
    *   for input string = 'mystr7" output will be = 'mystr7"
    *   for input string = mystr8 output will be = mystr8
    *   for input string = null output will be null
    * </pre>
    *
    * @param s value to be unquoted.
    * @return value unquoted, {@code null} if input is {@code null}.
    *
    */
   public static String unquote(String s)
   {

      if (s != null && ((s.startsWith("\"") && s.endsWith("\""))
            || (s.startsWith("'") && s.endsWith("'")))) {
         s = s.substring(1, s.length() - 1);
      }
      return s;
   }




   /**
    * This static method will take an array of Strings and join them together using
    * the supplied separator. The parts are joined in the order they are passed in.
    *
    * @param sep - The separator character.
    * @param parts - One or more String parts to join together
    * @return  A String object representing the elements joined together
    */
   public static String join(String sep, String... parts)
   {
      return join(sep, parts, 0, parts.length);
   }

   /**
    * This static method will take an array of Strings and joins them together using
    * the supplied separator. The parts are joined in the order they are passed in.
    * Null parts will not be concatenated.
    *
    * @param sep - The separator character.
    * @param parts An array of String parts to join together
    * @param offset The offset of the array to begin the joining
    * @param length The number of array elements to join
    * @return  A String object representing the elements joined together
    * @throws NullPointerException if either sep or parts are {@code null}
    * @throws IndexOutOfBoundsException if the offset or length are invalid
    */
   public static String join(String sep, String[] parts, int offset, int length)
   {
      if(sep == null) throw new NullPointerException("sep");
      if(parts == null) throw new NullPointerException("parts");
      StringBuilder buf = new StringBuilder();
      for(int i = offset; i < offset + length; i++) {
         if(parts[i] != null) {
            if(buf.length() > 0) buf.append(sep);
            buf.append(parts[i]);
         }
      }
      return buf.toString();
   }

   /**
    * This static method will take an Iterable of Strings and joins them together using
    * the supplied separator. The parts are joined in the order they are passed in. Null
    * parts will not be concatenated.
    *
    * @param sep - The separator character.
    * @param parts An iterable of String parts to join together
    * @return  A String object representing the elements joined together
    * @throws NullPointerException if either sep or parts are {@code null}
    */
   public static String join(String sep, Iterable<String> parts)
   {
      if(sep == null) throw new NullPointerException("sep");
      if(parts == null) throw new NullPointerException("parts");
      StringBuilder buf = new StringBuilder();
      for(Object part : parts) {
         if(part != null) {
            if(buf.length() > 0) buf.append(sep);
            buf.append(part);
         }
      }
      return buf.toString();
   }






   /**
    * Returns a string, of length at least <tt>minLength</tt>, consisting of <tt>str</tt>
    * appended with as many copies of <tt>padChar</tt> as are necessary to reach that length.
    * For example,
    * <ul>
    *    <li>padEnd("4.", 5, '0') returns "4.000"</li>
    *    <li>padEnd("2010", 3, '!') returns "2010"</li>
    * </ul>
    * @param str The string which should appear at the beginning of the result
    * @param minLength The minimum length the resulting string must have. Can be zero or
    *                   negative, in which case the input string is always returned.
    * @param padChar The character to append to the end of the result until the minimum
    *                 length is reached
    * @return The padded string
    * @throws NullPointerException If the specified string is {@code null}
    */
   public static String padEnd(String str, int minLength, char padChar)
   {
      int len = minLength - str.length();
      if(len > 0) {
         StringBuilder buf = new StringBuilder(str);
         for(int i = 0; i < len; i++) buf.append(padChar);
         return buf.toString();
      }
      return str;
   }

   /**
    * Returns a string, of length at least <tt>minLength</tt>, consisting of <tt>str</tt>
    * prepended with as many copies of <tt>padChar</tt> as are necessary to reach that length.
    * For example,
    * <ul>
    *    <li>padStart("7", 3, '0') returns "007"</li>
    *    <li>padStart("2010", 3, '0') returns "2010"</li>
    * </ul>
    * @param str The string which should appear at the end of the result
    * @param minLength The minimum length the resulting string must have. Can be zero or
    *                   negative, in which case the input string is always returned.
    * @param padChar The character to insert at the beginning of the result until the minimum
    *                 length is reached
    * @return The padded string
    * @throws NullPointerException If the specified string is {@code null}
    */
   public static String padStart(String str, int minLength, char padChar)
   {
      int len = minLength - str.length();
      if(len > 0) {
         StringBuilder buf = new StringBuilder();
         for(int i = 0; i < len; i++) buf.append(padChar);
         buf.append(str);
         return buf.toString();
      }
      return str;
   }







   /**
    * Returns an empty array if the specified data array is {@code null}, otherwise it
    * returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length array
    */
   public static String[] emptyIfNull(String[] data)
   {
      return (data == null) ? new String[0] : data;
   }










   // Argument checking


   /**
    * Argument checking utility that will throw an {@link IllegalArgumentException} with
    * a default message if the specified argument is {@code null} or zero length.
    */
   public static String notEmpty(String arg)
   {
      if(isEmpty(arg)) throw new IllegalArgumentException("string argument is empty");
      return arg;
   }

   /**
    * Argument checking utility that will throw an {@link IllegalArgumentException} with
    * a default message that identifies the argument name if the specified argument is
    * {@code null} or zero length.
    */
   public static String notEmpty(String arg, String argName)
   {
      if(isEmpty(arg)) throw new IllegalArgumentException(format("%s is empty", argName));
      return arg;
   }




   /**
    * Ensures that {@code index} specifies a valid <i>position</i> in an array, list or string of
    * size {@code size}. A position index may range from zero to {@code size}, inclusive.
    *
    * @param index a user-supplied index identifying a position in an array, list or string
    * @param size the size of that array, list or string
    * @return the value of {@code index}
    * @throws IndexOutOfBoundsException if {@code index} is negative or is greater than {@code size}
    * @throws IllegalArgumentException if {@code size} is negative
    */
   public static int checkPositionIndex(int index, int size)
   {
      return checkPositionIndex(index, size, "index");
   }

   /**
    * Ensures that {@code index} specifies a valid <i>position</i> in an array, list or string of
    * size {@code size}. A position index may range from zero to {@code size}, inclusive.
    *
    * @param index a user-supplied index identifying a position in an array, list or string
    * @param size the size of that array, list or string
    * @param desc the text to use to describe this index in an error message
    * @return the value of {@code index}
    * @throws IndexOutOfBoundsException if {@code index} is negative or is greater than {@code size}
    * @throws IllegalArgumentException if {@code size} is negative
    */
   public static int checkPositionIndex(int index, int size, String desc)
   {
      // Carefully optimized for execution by hotspot (explanatory comment above)
      if (index < 0) {
         throw new IndexOutOfBoundsException(format("%s (%s) must not be negative", desc, index));
      } else if (size < 0) {
         throw new IllegalArgumentException("negative size: " + size);
      } else if(index > size) {
         throw new IndexOutOfBoundsException(format("%s (%s) must not be greater than size (%s)", desc, index, size));
      }
      return index;
   }

}
