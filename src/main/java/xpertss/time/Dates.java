package xpertss.time;

import xpertss.lang.CharSequences;
import xpertss.lang.Objects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.function.Predicate;

import static java.lang.String.format;

/**
 * Utility methods and constants for managing and manipulating dates.
 * <p>
 * Note: A 64bit signed number can represent  +/- 292 million years if interpreted
 * as a number of milliseconds or +/- 292,000 years if interpreted as number of
 * microseconds.
 */
public final class Dates {


   private Dates() { }

   // TODO All of these parse methods currently use SimpleDateFormat. Change
   // it to use my DateFormatter and create a @see note to DateFormatter

   /**
    * Parse the given string date using the specified date pattern. If any
    * error occurs during the parse return the default date, otherwise,
    * return the parsed date.
    * <p>
    * This operation parses the date using the specified timezone or the
    * system default if tz is null.
    *
    * @param tz The timezone to use during the parse
    * @param pattern Date Format pattern to use to parse the date.
    * @param str The encoded date
    * @param def A default date to return if the parse fails
    * @return The parsed date or the default if the parse failed
    */
   public static Date parse(TimeZone tz, String pattern, CharSequence str, Date def)
   {
      try {
         SimpleDateFormat format = new SimpleDateFormat(pattern);
         format.setTimeZone(Objects.ifNull(tz, TimeZone.getDefault()));
         return format.parse(CharSequences.toString(str));
      } catch (Exception e) { return def; }
   }

   /**
    * Parse the given string date using the specified date pattern. If any
    * error occurs during the parse return the default date, otherwise,
    * return the parsed date.
    * <p>
    * This operation parses the date using the default timezone.
    *
    * @param pattern Date Format pattern to use to parse the date.
    * @param str The encoded date
    * @param def A default date to return if the parse fails
    * @return The parsed date or the default if the parse failed
    */
   public static Date parseLocal(String pattern, CharSequence str, Date def)
   {
      return parse(TimeZone.getDefault(), pattern, str, def);
   }

   /**
    * Parse the given string date using the specified date pattern. If any
    * error occurs during the parse return the default date, otherwise,
    * return the parsed date.
    * <p>
    * This operation parses the date using the default timezone.
    *
    * @param pattern Date Format pattern to use to parse the date.
    * @param str The encoded date
    * @param def A default date to return if the parse fails
    * @return The parsed date or the default if the parse failed
    */
   public static Date parseUTC(String pattern, CharSequence str, Date def)
   {
      return parse(TimeZone.getTimeZone("UTC"), pattern, str, def);
   }




   /**
    * Return the maximum date value from the set of input dates. Returns {@code null} if
    * the supplied set contains no elements.
    *
    * @param set Set of dates to find the maximum within
    * @return The maximum date or {@code null} if the set was empty
    * @throws NullPointerException If the set or an element in the set is {@code null}
    */
   @SafeVarargs
   public static Date max(Date ... set)
   {
      if(Objects.notNull(set).length < 1) return null;
      Date max = set[0];
      for(int i = 1; i < set.length; i++) {
         if(set[i].compareTo(max) > 0) max = set[i];
      }
      return max;
   }

   /**
    * Return the minimum date value from the set of input dates. Returns {@code null} if
    * the supplied set contains no elements.
    *
    * @param set Set of dates to find the minimum within
    * @return The minimum date or {@code null} if the set was empty
    * @throws NullPointerException If the set or an element in the set is {@code null}
    */
   public static Date min(Date ... set)
   {
      if(Objects.notNull(set).length < 1) return null;
      Date min = set[0];
      for(int i = 1; i < set.length; i++) {
         if(set[i].compareTo(min) < 0) min = set[i];
      }
      return min;
   }



   /**
    * Returns {@code true} if there are one or more dates specified and they are equal to
    * one another.
    * <p>
    * If all dates in the set are {@code null} this will return {@code true}. This will
    * return {@code false} if the set is empty.
    *
    * @param set A set of dates to compare for equality
    * @return True if one or more dates equal one another
    * @throws NullPointerException If the supplied set is {@code null}
    */
   public static boolean equal(Date ... set)
   {
      if(Objects.notNull(set).length < 1) return false;
      if(set.length > 1) {
         Date first = set[0];
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
    * Returns a predicate that evaluates to {@code true} if the date being tested
    * falls {@code before()} the given date.
    */
   public static Predicate<Date> before(Date d)
   {
      final Date date = d;
      return input -> input.before(date);
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the date being tested
    * falls {@code after()} the given date.
    */
   public static Predicate<Date> after(Date d)
   {
      final Date date = d;
      return input -> input.after(date);
   }



   // Argument checkers


   /**
    * Argument checking utility that will return the specified <tt>arg</tt> if it is
    * greater than the specified minimum, otherwise it will throw an
    * {@link IllegalArgumentException} identifying the specified argument.
    */
   public static Date gt(Date minimum, Date arg, String argName)
   {
      if(minimum.compareTo(arg) >= 0)
         throw new IllegalArgumentException(format("%s(%s) not greater than %s",
                                             argName, arg, minimum));
      return arg;
   }



   /**
    * Argument checking utility that will return the specified <tt>arg</tt> if it is
    * greater than or equal to the specified minimum, otherwise it will throw an
    * {@link IllegalArgumentException} identifying the specified argument.
    */
   public static Date gte(Date minimum, Date arg, String argName)
   {
      if(minimum.compareTo(arg) > 0)
         throw new IllegalArgumentException(format("%s(%s) not greater than equal to %s",
                                             argName, arg, minimum));
      return arg;
   }


   /**
    * Argument checking utility that will return the specified <tt>arg</tt> if it is
    * less than the specified maximum, otherwise it will throw an
    * {@link IllegalArgumentException} identifying the specified argument.
    */
   public static Date lt(Date maximum, Date arg, String argName)
   {
      if(maximum.compareTo(arg) <= 0)
         throw new IllegalArgumentException(format("%s(%s) not less than %s",
                                             argName, arg, maximum));
      return arg;
   }


   /**
    * Argument checking utility that will return the specified <tt>arg</tt> if it is
    * less than or equal to the specified maximum, otherwise it will throw an
    * {@link IllegalArgumentException} identifying the specified argument.
    */
   public static Date lte(Date maximum, Date arg, String argName)
   {
      if(maximum.compareTo(arg) < 0)
         throw new IllegalArgumentException(format("%s(%s) not less than equal to %s",
                                             argName, arg, maximum));
      return arg;
   }



   /**
    * Argument checking utility that will return the specified <tt>arg</tt> if it is
    * between the given maximum and minimum, otherwise it will throw an
    * {@link IllegalArgumentException} identifying the specified argument.
    * <p>
    * An argument is said to be between when minimum &lt; arg &lt; maximum
    */
   public static Date between(Date minimum, Date maximum, Date arg, String argName)
   {
      if(maximum.compareTo(arg) <= 0 || minimum.compareTo(arg) >= 0)
         throw new IllegalArgumentException(format("%s(%s) not between %s and %s",
                                             argName, arg, minimum, maximum));
      return arg;
   }

   /**
    * Argument checking utility that will return the specified <tt>arg</tt> if it is
    * within the given range, otherwise it will throw an {@link IllegalArgumentException}
    * identifying the specified argument.
    * <p>
    * An argument is said to be within when minimum &lt;= arg &lt;= maximum
    */
   public static Date within(Date minimum, Date maximum, Date arg, String argName)
   {
      if(maximum.compareTo(arg) < 0 || minimum.compareTo(arg) > 0)
         throw new IllegalArgumentException(format("%s(%s) not within %s and %s",
                                             argName, arg, minimum, maximum));
      return arg;
   }

   /**
    * Argument checking utility that will return the specified <tt>arg</tt> if it is contained
    * within the given range, otherwise it will throw an {@link IllegalArgumentException}
    * identifying the specified argument.
    * <p>
    * An argument is said to be contained when minimum &lt;= arg &lt; maximum
    */
   public static Date contains(Date minimum, Date maximum, Date arg, String argName)
   {
      if(maximum.compareTo(arg) <= 0 || minimum.compareTo(arg) > 0)
         throw new IllegalArgumentException(format("%s(%s) not contained by %s and %s",
                                             argName, arg, minimum, maximum));
      return arg;
   }


}
