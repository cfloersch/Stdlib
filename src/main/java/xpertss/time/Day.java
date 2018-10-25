package xpertss.time;

import xpertss.lang.Numbers;


/**
 * A day of the week, such as 'Tuesday'.
 * <p>
 * Day is an enum representing the 7 days of the week - Monday, Tuesday, Wednesday,
 * Thursday, Friday, Saturday and Sunday.
 * <p>
 * In addition to the textual enum name, each day of the week has an int value. The
 * int value follows the Gregorian standard, from 1 (Sunday) to 7 (Saturday). It is
 * recommended that applications use the enum rather than the int value to ensure
 * code clarity.
 */
public enum Day {

   /**
    * The singleton instance for Sunday. This has the numeric value of 1.
    */
   Sunday,

   /**
    * The singleton instance for Monday. This has the numeric value of 2.
    */
   Monday,

   /**
    * The singleton instance for Tuesday. This has the numeric value of 3.
    */
   Tuesday,

   /**
    * The singleton instance for Wednesday. This has the numeric value of 4.
    */
   Wednesday,

   /**
    * The singleton instance for Thursday. This has the numeric value of 5.
    */
   Thursday,

   /**
    * The singleton instance for Friday. This has the numeric value of 6.
    */
   Friday,

   /**
    * The singleton instance for Saturday. This has the numeric value of 7.
    */
   Saturday;



   /**
    * Returns the day of the week that is the specified number of days before this one.
    * <p>
    * The calculation rolls around the start of the week from Sunday to Saturday. The
    * specified period may be negative.
    *
    * @param days The days to subtract, positive or negative
    * @return the resulting day, not {@code null}
    */
   public Day minus(int days)
   {
      Day[] daysOfWeek = values();
      int ordinal = (ordinal() - days);
      while(ordinal < 0) ordinal += daysOfWeek.length;
      return daysOfWeek[ordinal % daysOfWeek.length];
   }

   /**
    * Returns the day of the week that is the specified number of days after this one.
    * <p>
    * The calculation rolls around the end of the week from Saturday to Sunday. The
    * specified period may be negative.
    *
    * @param days The days to add, positive or negative
    * @return the resulting day, not {@code null}
    */
   public Day plus(int days)
   {
      Day[] daysOfWeek = values();
      int ordinal = (ordinal() + days);
      while(ordinal < 0) ordinal += daysOfWeek.length;
      return daysOfWeek[ordinal % daysOfWeek.length];
   }






   /**
    * Obtains an instance of Day from an int value.
    * <p>
    * Day is an enum representing the 7 days of the week. This factory allows the enum
    * to be obtained from the int value. The int value follows the Gregorian standard,
    * from 1 (Sunday) to 7 (Saturday).
    */
   public static Day valueOf(int dayOfWeek)
   {
      Day[] days = values();
      return days[Numbers.within(1, days.length, dayOfWeek, "Invalid day of the week: " + dayOfWeek) - 1];
   }



}
