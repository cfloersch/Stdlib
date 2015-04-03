package xpertss.lang;



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
    * @throws NullPointerException If the speified string is {@code null}
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
    * This static method will take an array of Strings and concatenate them together using
    * a space as a separator. The parts are concatenated in the order they are passed in.
    * Null parts will not be concatenated.
    *
    * @param parts - One or more String parts to concatenate together
    * @return  A String object representing the elements concatenated together
    */
   public static String concat(String ... parts)
   {
      return concat(" ", parts, 0, parts.length);
   }

   /**
    * This static method will take an array of Strings and concatenate them together using
    * the supplied separator as a separator. The parts are concatenated in the order they
    * are passed in. Null parts will not be concatenated.
    *
    * @param sep - The string representation of the separator characters.
    * @param parts - One or more String parts to concatenate together
    * @return  A String object representing the elements concatenated together
    */
   public static String concat(String sep, String ... parts)
   {
      return concat(sep, parts, 0, parts.length);
   }

   /**
    * This static method will take an array of Strings and concatenate them together using
    * the supplied separator as a separator. The parts are concatenated in the order they
    * are passed in. Null parts will not be concatenated.
    *
    * @param sep The string representation of the separator characters.
    * @param parts An array of String parts to concatenate together
    * @param offset The offset of the array to begin the concatenation
    * @param length The number of array elements to concatenate
    * @return  A String object representing the elements concatenated together
    */
   public static String concat(String sep, String[] parts, int offset, int length)
   {
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
    * the given message if the specified argument is {@code null} or zero length.
    */
   public static String notEmpty(String arg, String msg)
   {
      if(isEmpty(arg)) throw new IllegalArgumentException(msg);
      return arg;
   }




}
