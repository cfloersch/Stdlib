package xpertss.time;


import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


import static xpertss.lang.Objects.*;


/**
 * A Chronology is based on the Gregorian/Julian Calendar and allows conversions
 * between human readable dates and times and computer Date objects.
 * <p/>
 * This uses a Julian/Gregorian calendar system with Oct 15th, 1582 as the cut over
 * date. This means that all dates prior to the cut over date will be treated as
 * Julian calendar dates while all dates equal to or greater than the cut over date
 * will be treated as Gregorian calendar days. The only difference between the
 * Gregorian and the Julian calendar is the leap year rule. The Julian calendar
 * specifies leap years every four years, whereas the Gregorian calendar omits
 * century years which are not divisible by 400.
 * <p/>
 * Historically, in those countries which adopted the Gregorian calendar first,
 * October 4, 1582 (Julian) was thus followed by October 15, 1582 (Gregorian).
 * This chronology models that correctly.
 * <p/>
 * This chronology implements proleptic Gregorian and Julian calendars. That is,
 * dates are computed by extrapolating Julian rules indefinitely backward in time
 * and Gregorian rules indefinitely forward in time. Dates obtained using this
 * chronology are historically accurate only from March 1, 4 AD onward, when
 * modern Julian calendar rules were adopted. Before this date, leap year rules
 * were applied irregularly, and before 45 BC the Julian calendar did not even
 * exist.
 * <p/>
 * This chronology differs from the modern ISO chronology in that it treats
 * Sunday as the first day of the week and it supports both the modern era CE/AD
 * and the ancient era BCE/BC.
 */
public final class Chronology {

   private TimeZone tz;
   private Locale locale;

   private Chronology(TimeZone tz, Locale locale)
   {
      this.tz = (tz != null) ? tz : TimeZone.getDefault();
      this.locale = (locale != null) ? locale : Locale.getDefault();
   }


   /**
    * Create a date representing the given month and year. The day will default to the
    * first.
    *
    * @param year The value used to set the YEAR calendar field.
    * @param month The value used to set the MONTH calendar field. Month value is 1-based.
    *               e.g., 1 for January.
    * @return A date representation of those values in this chronology
    */
   public Date newDate(int year, int month)
   {
      return newDate(year, month, 1);
   }

   /**
    * Create a date representing the given month, day, and year.
    *
    * @param year The value used to set the YEAR calendar field.
    * @param month The value used to set the MONTH calendar field. Month value is 1-based.
    *               e.g., 1 for January.
    * @param day The value used to set the DAY_OF_MONTH calendar field.
    * @return A date representation of those values in this chronology
    */
   public Date newDate(int year, int month, int day)
   {
      return newDate(year, month, day, 0, 0, 0, 0);
   }

   /**
    * Create a date representing the given month, day, year, and hour of the day.
    *
    * @param year The value used to set the YEAR calendar field.
    * @param month The value used to set the MONTH calendar field. Month value is 1-based.
    *               e.g., 1 for January.
    * @param day The value used to set the DAY_OF_MONTH calendar field.
    * @param hour The value used to set the HOUR_OF_DAY calendar field.
    * @return A date representation of those values in this chronology
    */
   public Date newDate(int year, int month, int day, int hour)
   {
      return newDate(year, month, day, hour, 0);
   }

   /**
    * Create a date representing the given month, day, year, hour of the day, and minute.
    *
    * @param year The value used to set the YEAR calendar field.
    * @param month The value used to set the MONTH calendar field. Month value is 1-based.
    *               e.g., 1 for January.
    * @param day The value used to set the DAY_OF_MONTH calendar field.
    * @param hour The value used to set the HOUR_OF_DAY calendar field.
    * @param min The value used to set the MINUTE calendar field.
    * @return A date representation of those values in this chronology
    */
   public Date newDate(int year, int month, int day, int hour, int min)
   {
      return newDate(year, month, day, hour, min, 0, 0);
   }

   /**
    * Create a date representing the given month, day, year, and time.
    *
    * @param year The value used to set the YEAR calendar field.
    * @param month The value used to set the MONTH calendar field. Month value is 1-based.
    *               e.g., 1 for January.
    * @param day The value used to set the DAY_OF_MONTH calendar field.
    * @param hour The value used to set the HOUR_OF_DAY calendar field.
    * @param min The value used to set the MINUTE calendar field.
    * @param sec The value used to set the SECOND calendar field.
    * @return A date representation of those values in this chronology
    */
   public Date newDate(int year, int month, int day, int hour, int min, int sec)
   {
      return newDate(year, month, day, hour, min, sec, 0);
   }

   /**
    * Create a date representing the given month, day, year, and complete time.
    *
    * @param year The value used to set the YEAR calendar field.
    * @param month The value used to set the MONTH calendar field. Month value is 1-based.
    *               e.g., 1 for January.
    * @param day The value used to set the DAY_OF_MONTH calendar field.
    * @param hour The value used to set the HOUR_OF_DAY calendar field.
    * @param min The value used to set the MINUTE calendar field.
    * @param sec The value used to set the SECOND calendar field.
    * @param millis The value used to set the MILLISECOND calendar field.
    * @return A date representation of those values in this chronology
    */
   public Date newDate(int year, int month, int day, int hour, int min, int sec, int millis)
   {
      Calendar cal = Calendar.getInstance(tz, locale);
      cal.set(year, month - 1, day, hour, min, sec);
      cal.set(Calendar.MILLISECOND, millis);
      return cal.getTime();
   }






   /**
    * Returns {@code true} if the given date represents a leap year.
    * <p>
    * Leap years are used by most calendar systems to synchronize them with the
    * orbital rotation of the earth around the sun.
    */
   public boolean isLeapYear(Date date)
   {
      Calendar cal = createCal(date);
      return cal.getActualMaximum(Calendar.DAY_OF_YEAR) == cal.getMaximum(Calendar.DAY_OF_YEAR);
   }

   /**
    * Returns {@code true} if the given date belongs to the modern era. In the case
    * of the Georgian calendar this means the date belongs to Common Era (CE).
    */
   public boolean isModernEra(Date date)
   {
      Calendar cal = createCal(date);
      return cal.get(Calendar.ERA) == cal.getActualMaximum(Calendar.ERA);
   }




   /**
    * Determine if the supplied date is the first of the week.
    * <p>
    * Most of Europe and anyone who conforms to the ISO8601 international standard
    * views Monday as the first day of the week. Most Middle Eastern nations view
    * Saturday as the first day of the week. And North America views Sunday as the
    * first day of the week.
    */
   public boolean isFirstDayOfWeek(Date date)
   {
      Calendar cal = createCal(date);
      return (cal.getActualMinimum(Calendar.DAY_OF_WEEK) == cal.get(Calendar.DAY_OF_WEEK));
   }

   /**
    * Determine if the supplied date is the last day of the week.
    * <p>
    * Because different nations around the planet view which day is the first day
    * of the week differently then it is equally true that the last day is also
    * different.
    */
   public boolean isLastDayOfWeek(Date date)
   {
      Calendar cal = createCal(date);
      return (cal.getActualMaximum(Calendar.DAY_OF_WEEK) == cal.get(Calendar.DAY_OF_WEEK));
   }


   /**
    * Determine if the supplied date is the last day of the month.
    */
   public boolean isLastDayOfMonth(Date date)
   {
      Calendar cal = createCal(date);
      return (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH));
   }






   /**
    * Returns the day of the week the specified date represents.
    * <p>
    * Returns day object vs int to eliminate the confusion associated with
    * whether Sunday is 1 or 0 or if its monday that starts the week.
    */
   public Day getDayOfWeek(Date date)
   {
      Calendar cal = createCal(date);
      return Day.valueOf(cal.get(Calendar.DAY_OF_WEEK));
   }

   /**
    * Returns the day of the year the month the specified date represents.
    * <p>
    * This is always 1 based meaning the first day of the year will be reported
    * as 1 and the last day of the year will be 365 or 366 depending on whether
    * it is a leap year or not
    */
   public int getDayOfYear(Date date)
   {
      Calendar cal = createCal(date);
      return cal.get(Calendar.DAY_OF_YEAR);
   }

   /**
    * Returns the week of the month the specified date represents.
    * <p>
    * This is always 1 based meaning the first week of the month will be reported
    * as 1. Months may have between 4 and 6 weeks in them depending on how the
    * days are distributed and what day is determine to be the first of the week
    * for teh given locale.
    */
   public int getWeekOfMonth(Date date)
   {
      Calendar cal = createCal(date);
      return cal.get(Calendar.WEEK_OF_MONTH);
   }

   /**
    * Returns the week of the year the specified date represents.
    * <p>
    * This is always 1 based meaning the first week of the year will be reported
    * as 1 and the last week of the year will be 53. It is important to note that
    * the last day of the year is not always in the last week of the year.
    */
   public int getWeekOfYear(Date date)
   {
      Calendar cal = createCal(date);
      return cal.get(Calendar.WEEK_OF_YEAR);
   }

   /**
    * Returns the month of the year the specified date represents.
    * <p>
    * Returns month object vs int to eliminate the confusion associated with
    * whether January is 1 or 0.
    */
   public Month getMonthOfYear(Date date)
   {
      Calendar cal = createCal(date);
      return Month.valueOf(cal.get(Calendar.MONTH) + 1);
   }








   /**
    * Add the specified amount of time/days/etc to the given date. If a negative amount
    * is provided then the date is rolled backward in time rather than forward in time.
    *
    * @param date The base date to modify
    * @param amount The amount to modify it
    * @param unit The date unit in which the amount is applied
    * @return A newly created Date object representing the adjusted date time.
    * @throws NullPointerException If either {@code date} or {@code unit} are {@code
    *    null}
    */
   public Date add(Date date, int amount, DateUnit unit)
   {
      Calendar cal = createCal(notNull(date, "date"));
      notNull(unit, "unit").add(cal, amount);
      return cal.getTime();
   }




   /**
    * Return the number of years, months, weeks, days, hours, minutes, seconds, or milli
    * seconds between the period's start and end bounds. The returned value is relative
    * to the unit specified.
    *
    * @param unit The unit to return the period difference in
    * @return The units between the periods start and end
    * @throws NullPointerException If the specified period or unit are {@code null}
    */
   public long between(Period period, DateUnit unit)
   {
      return between(period.getStart(), period.getEnd(), unit);
   }


   /**
    * Return the number of years, months, weeks, days, hours, minutes, seconds, or milli
    * seconds between the period's start and end bounds. The returned value is relative
    * to the unit specified.
    *
    * @param start The beginning date of the period
    * @param end The ending date of the period
    * @param unit The unit to return the period difference in
    * @return The units between the periods start and end
    * @throws NullPointerException If the specified period or unit are {@code null}
    */
   public long between(Date start, Date end, DateUnit unit)
   {
      Calendar co = createCal(notNull(start, "one"));
      Calendar ct = createCal(notNull(end, "two"));

      if(end.before(start)) {
          return -(unit.between(ct, co));
      }
      return unit.between(co, ct);
   }







   private static final int[] FIELDS = {Calendar.ERA, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH,
         Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND };

   /**
    * Returns {@code true} if the two supplied dates represent the same point in time.
    * Equality of the two dates is measured only down to the specified unit. For
    * example:
    * <p><pre>
    *   Dates.isSame(d1, d2, DateField.Day)
    * </pre><p>
    * Measures the two dates for equality only down to whether they are the same day.
    * Hours, minutes, seconds, and milliseconds are ignored.
    *
    *
    * @param one The first date to compare
    * @param two The second date to compare
    * @param field The date field to which the comparison is executed
    * @return {@code true} if the two dates are the same relative to the specified date
    *    field
    * @throws NullPointerException If either {@code date} or the {@code field} are {@code
    *    null}
    */
   public boolean isSame(Date one, Date two, DateField field)
   {
      notNull(field, "field");
      Calendar cal1 = createCal(notNull(one, "one"));
      Calendar cal2 = createCal(notNull(two, "two"));
      for(int f : FIELDS) {
         if(cal1.get(f) != cal2.get(f)) return false;
         if(field.get() == f) break;
      }
      return true;
   }




   /**
    * Round this date, leaving the field specified as the most significant field.
    * <p>
    * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if this was
    * passed with HOUR, it would return 28 Mar 2002 14:00:00.000. If this was passed
    * with MONTH, it would return 1 April 2002 00:00:00.000.
    *
    * @param date The date to round
    * @param field The date field  to round to
    * @return The rounded date
    * @throws NullPointerException If {@code date}, {@code unit}, or {@code mode} are
    *    {@code null}
    */
   public Date round(Date date, DateField field, RoundingMode mode)
   {
      notNull(mode, "mode");
      if(notNull(field, "field") == DateField.Millisecond) return date;
      Calendar cal = createCal(notNull(date, "date"));
      for(int i = FIELDS.length - 1; i > 0; i--) {
         if(field.get() == FIELDS[i]) break;
         int val = cal.get(FIELDS[i]);
         int min = cal.getActualMinimum(FIELDS[i]);
         int max = cal.getActualMaximum(FIELDS[i]);
         int half = (FIELDS[i] == Calendar.MONTH) ? (max - min) / 2 : (max - min + 1) / 2;
         switch(mode) {
            case UNNECESSARY:
               if(val != min) throw new ArithmeticException();
               break;
            case UP:
            case CEILING:
               cal.add(FIELDS[i-1], 1);
               break;
            case DOWN:
            case FLOOR:
               break;
            case HALF_UP:
               if(val >= half) cal.add(FIELDS[i-1], 1);
               break;
            case HALF_DOWN:
               if(val > half) cal.add(FIELDS[i-1], 1);
               break;
            case HALF_EVEN:
               if(val > half || (val == half && cal.get(FIELDS[i-1]) % 2 == 1)) {
                  cal.add(FIELDS[i-1], 1);
               }
               break;
         }
         cal.set(FIELDS[i], min);
      }
      return cal.getTime();
   }



   /**
    * Truncate this date, leaving the field specified as the most significant field.
    * <p>
    * For example, if you had the datetime of 28 Mar 2002 13:45:01.231, if you specified
    * HOUR, it would return 28 Mar 2002 13:00:00.000. If this was passed with MONTH, it
    * would return 1 Mar 2002 00:00:00.000.
    *
    * @param date The date to truncate
    * @param field The field to truncate to
    * @return The truncated date
    * @throws NullPointerException If {@code date} or {@code field} are {@code null}
    */
   public Date truncate(Date date, DateField field)
   {
      notNull(field, "field");
      Calendar cal = createCal(notNull(date, "date"));
      for(int i = FIELDS.length - 1; i >= 0; i--) {
         if(field.get() == FIELDS[i]) break;
         cal.set(FIELDS[i], cal.getActualMinimum(FIELDS[i]));
      }
      return cal.getTime();
   }







   private Calendar createCal(Date date)
   {
      Calendar cal = GregorianCalendar.getInstance(tz, locale);
      cal.setTime(date);
      return cal;
   }






   /**
    * Create a Chronology that uses the default system timezone nad locale.
    */
   public static Chronology create()
   {
      return create(TimeZone.getDefault(), Locale.getDefault());
   }

   /**
    * Create a Chronology that uses the default system locale and the specified
    * time zone.
    */
   public static Chronology create(TimeZone tz)
   {
      return create(tz, Locale.getDefault());
   }

   /**
    * Create a Chronology that uses the default system timezone and the specified
    * locale.
    */
   public static Chronology create(Locale locale)
   {
      return create(TimeZone.getDefault(), locale);
   }

   /**
    * Create a Chronology that uses the specified timezone nad locale.
    */
   public static Chronology create(TimeZone tz, Locale locale)
   {
      return new Chronology(tz, locale);
   }


}
