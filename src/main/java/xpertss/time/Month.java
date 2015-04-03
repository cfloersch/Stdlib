package xpertss.time;

import xpertss.lang.Numbers;

/**
 * A month-of-year, such as 'July'.
 * <p/>
 * Month is an enum representing the 12 months of the year - January, February,
 * March, April, May, June, July, August, September, October, November and
 * December.
 * <p/>
 * In addition to the textual enum name, each month-of-year has an int value. The
 * int value follows normal usage and the ISO-8601 standard, from 1 (January) to
 * 12 (December). It is recommended that applications use the enum rather than
 * the int value to ensure code clarity.
 */
public enum Month {

   /**
    * The singleton instance for the month of January with 31 days. This
    * has the numeric value of 1.
    */
   January(31),

   /**
    * The singleton instance for the month of February with 28 days,
    * or 29 in a leap year. This has the numeric value of 2.
    */
   February(-1),

   /**
    * The singleton instance for the month of March with 31 days. This
    * has the numeric value of 3.
    */
   March(31),

   /**
    * The singleton instance for the month of April with 30 days. This
    * has the numeric value of 4.
    */
   April(30),

   /**
    * The singleton instance for the month of May with 31 days. This
    * has the numeric value of 5.
    */
   May(31),

   /**
    * The singleton instance for the month of June with 30 days. This
    * has the numeric value of 6.
    */
   June(30),

   /**
    * The singleton instance for the month of July with 31 days. This
    * has the numeric value of 7.
    */
   July(31),

   /**
    * The singleton instance for the month of August with 31 days. This
    * has the numeric value of 8.
    */
   August(31),

   /**
    * The singleton instance for the month of September with 30 days. This
    * has the numeric value of 9.
    */
   September(30),

   /**
    * The singleton instance for the month of October with 31 days. This
    * has the numeric value of 10.
    */
   October(31),

   /**
    * The singleton instance for the month of November with 30 days. This
    * has the numeric value of 11.
    */
   November(30),

   /**
    * The singleton instance for the month of December with 31 days. This
    * has the numeric value of 12.
    */
   December(31);



   private int days;

   private Month(int days)
   {
      this.days = days;
   }



   /**
    * Gets the length of this month in days.
    * <p>>
    * This takes a flag to determine whether to return the length for a leap year or not.
    * <p>
    * February has 28 days in a standard year and 29 days in a leap year. April, June,
    * September and November have 30 days. All other months have 31 days.
    */
   public int length(boolean leapYear)
   {
      if(days != -1) return days;
      return (leapYear) ? 29 : 28;
   }

   /**
    * Gets the day-of-year for the first day of this month.
    * <p/>
    * This returns the sum of day numbers for all previous months taking into
    * account that february can occasionally have 29 days rather than 28.
    */
   public int firstDayOfYear(boolean leapYear)
   {
      int total = 0;
      for(Month month : Month.values()) {
         if(month == this) return total + 1;
         total += month.length(leapYear);
      }
      throw new Error();
   }

   /**
    * Gets the month corresponding to the first month of this quarter.
    * <p>
    * The year can be divided into four quarters. This method returns the first month of
    * the quarter for the base month. January, February and March return January. April,
    * May and June return April. July, August and September return July.  October,
    * November and December return October.
    *
    * @return the first month of the quarter corresponding to this month, not {@code null}
    */
   public Month firstMonthOfQuarter()
   {
      return valueOf(((ordinal() / 3) * 3) + 1);
   }


   /**
    * Returns the numeric quarter this month falls within. January, February, and March all
    * fall within the first quarter.
    *
    * @return The numeric quarter corresponding to this month.
    */
   public int quarter()
   {
      return (ordinal() + 3) / 3;
   }


   /**
    * Returns the month-of-year that is the specified number of months before this one.
    * <p>
    * The calculation rolls around the start of the year from January to December. The
    * specified period may be negative.
    *
    * @param months The months to subtract, positive or negative
    * @return the resulting month-of-year, not null
    */
   public Month minus(int months)
   {
      Month[] monthOfYear = values();
      int ordinal = (ordinal() - months);
      while(ordinal < 0) ordinal += monthOfYear.length;
      return monthOfYear[ordinal % monthOfYear.length];
   }

   /**
    * Returns the month-of-year that is the specified number of months after this one.
    * <p>
    * The calculation rolls around the end of the year from December to January. The
    * specified period may be negative.
    *
    * @param months The months to add, positive or negative
    * @return the resulting month-of-year, not null
    */
   public Month plus(int months)
   {
      Month[] monthOfYear = values();
      int ordinal = (ordinal() + months);
      while(ordinal < 0) ordinal += monthOfYear.length;
      return monthOfYear[ordinal % monthOfYear.length];
   }








   /**
    * Obtains an instance of Month from an int value.
    * <p/>
    * Month is an enum representing the 12 months of the year. This factory allows
    * the enum to be obtained from the int value. The int value follows the ISO-8601
    * standard, from 1 (January) to 12 (December).
    */
   public static Month valueOf(int monthOfYear)
   {
      Month[] months = values();
      return months[Numbers.within(1, months.length, monthOfYear, "Invalid month of the year: " + monthOfYear) - 1];
   }

}
