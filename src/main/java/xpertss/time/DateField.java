package xpertss.time;

import java.util.Calendar;

/**
 * A standard set of fields.
 * <p>
 * The difference between DateField and DateUnit is the later is intended to
 * be used in relation to periods or durations while the former is used to
 * indicate date/time fields for truncation, comparison, and rounding.
 */
public enum DateField {

   /**
    * The year field of a date indicating year of era
    */
   Year(Calendar.YEAR),

   /**
    * The month field of a date indicating month of year
    */
   Month(Calendar.MONTH),

   /**
    * The day field of a date indicating day of month
    */
   Day(Calendar.DAY_OF_MONTH),


   /**
    * The hour field of a time indicating hour of day using a 24 hour clock
    */
   Hour(Calendar.HOUR_OF_DAY),


   /**
    * The minute field of a time indicating the minute within an hour
    */
   Minute(Calendar.MINUTE),

   /**
    * The second field of a time indicating the second within a minute
    */
   Second(Calendar.SECOND),

   /**
    * The millisecond field of a time indicating the millisecond within a second
    */
   Millisecond(Calendar.MILLISECOND);


   private int value;

   private DateField(int value)
   {
      this.value = value;
   }

   int get()
   {
      return value;
   }


}
