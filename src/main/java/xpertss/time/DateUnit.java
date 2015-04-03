package xpertss.time;


import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A standard set of date periods units.
 */
public enum DateUnit {

   /*
    * Date Calculator (days/weeks betwen)
    * http://www.easysurf.cc/ndate2.htm
    * http://www.timeanddate.com/date/durationresult.html?m1=01&d1=01&y1=1&m2=8&d2=8&y2=2014
    *
    * Julian Date Computation
    * http://www.cs.utsa.edu/~cs1063/projects/Spring2011/Project1/jdn-explanation.html
    * http://en.wikipedia.org/wiki/Julian_day#Converting_Julian_or_Gregorian_calendar_date_to_Julian_Day_Number
    */


   /**
    * Unit that represents the concept of a millennium. It is equal to 1000 years.
    */
   Millennia {
      long between(Calendar start, Calendar end)
      {
         return Years.between(start, end) / 1000;
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.YEAR, amount * 1000); }
   },

   /**
    * Unit that represents the concept of a century. It is equal to 100 years.
    */
   Centuries {
      long between(Calendar start, Calendar end)
      {
         return Years.between(start, end) / 100;
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.YEAR, amount * 100); }
   },

   /**
    * Unit that represents the concept of a decade. It is equal to 10 years.
    */
   Decades {
      long between(Calendar start, Calendar end)
      {
         return Years.between(start, end) / 10;
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.YEAR, amount * 10); }
   },




   /**
    * Unit that represents the concept of a year. It is equal to 12 months.
    */
   Years {

      // Fri Dec 31, 1 BC transitions to Sat Jan 1, 1 AD
      // aka there is no year 0

      long between(Calendar start, Calendar end)
      {
         int years = 0;
         if(end.get(Calendar.ERA) != start.get(Calendar.ERA)) {
            // if start is BC years count down so we need to add them to our AD
            // since years are not zero based we need to -1 from result
            // Jan 1, 2 BC - Jan 1, 2 AD = 3 as opposed to 4 because there is no zero
            years = end.get(Calendar.YEAR) + start.get(Calendar.YEAR) - 1;
         } else if(start.get(Calendar.ERA) == GregorianCalendar.BC) {
            years = start.get(Calendar.YEAR) - end.get(Calendar.YEAR);
         } else {
            years = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
         }

         start.add(Calendar.YEAR, years);
         if(start.after(end)) {
             return years - 1;
         }
         return years;
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.YEAR, amount); }
   },

   /**
    * Unit that represents the concept of a month. The length of the month varies by
    * month-of-year and leap year.
    */
   Months {
      long between(Calendar start, Calendar end)
      {
         int years = 0;
         if(end.get(Calendar.ERA) != start.get(Calendar.ERA)) {
            // if start is BC years count down so we need to add them to our AD
            // since years are not zero based we need to -1 from result
            // Jan 1, 2 BC - Jan 1, 2 AD = 3 as opposed to 4 because there is no zero
            years = end.get(Calendar.YEAR) + start.get(Calendar.YEAR) - 1;
         } else if(start.get(Calendar.ERA) == GregorianCalendar.BC) {
            years = start.get(Calendar.YEAR) - end.get(Calendar.YEAR);
         } else {
            years = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
         }

         int months = 12 * years;

         start.add(Calendar.MONTH, months);
         if(start.after(end)) {
            while(start.after(end)) {
               start.add(Calendar.MONTH, -1);
               months--;
            }
         } else if(start.before(end)) {
            while(true) {
               start.add(Calendar.MONTH, 1);
               if(start.after(end)) {
                  break;
               } else {
                  months++;
               }
            }
         }

         return months;
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.MONTH, amount); }
   },


   /**
    * Unit that represents the concept of a week. It is equal to 7 days.
    */
   Weeks {
      long between(Calendar start, Calendar end)
      {
         return Days.between(start, end) / 7;
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.DATE, amount * 7); }
   },

   /**
    * Unit that represents the concept of a day. It is the standard day from midnight
    * to midnight. The estimated duration of a day is 24 Hours.
    */
   Days {
      long between(Calendar start, Calendar end)
      {
         int[] FIELDS = { Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND };

         long days = getJulianDay(end) - getJulianDay(start);

         for(int FIELD : FIELDS) {
            if(end.get(FIELD) < start.get(FIELD)) return days - 1;
         }
         return days;
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.DATE, amount); }
   },

   /**
    * Unit that represents the concept of an hour. It is equal to 60 minutes.
    */
   Hours {
      long between(Calendar start, Calendar end)
      {
         return (end.getTimeInMillis() - start.getTimeInMillis()) / (60 * 60 * 1000);
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.HOUR_OF_DAY, amount); }
   },

   /**
    * Unit that represents the concept of a minute. It is equal to 60 seconds.
    */
   Minutes {
      long between(Calendar start, Calendar end)
      {
         return (end.getTimeInMillis() - start.getTimeInMillis()) / (60 * 1000);
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.MINUTE, amount); }
   },

   /**
    * Unit that represents the concept of a second. It is equal to the second in the
    * SI system of units.
    */
   Seconds {
      long between(Calendar start, Calendar end)
      {
         return (end.getTimeInMillis() - start.getTimeInMillis()) / 1000;
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.SECOND, amount); }
   },

   /**
    * Unit that represents the concept of a millisecond. It is equal to 1/1000th of
    * a second.
    */
   Milliseconds {
      long between(Calendar start, Calendar end)
      {
         return end.getTimeInMillis() - start.getTimeInMillis();
      }
      void add(Calendar cal, int amount) { cal.add(Calendar.MILLISECOND, amount); }
   };




   long between(Calendar low, Calendar high) { return 0; }
   void add(Calendar cal, int amount) { }



   // The default value of gregorianCutover.
   static final long DEFAULT_GREGORIAN_CUTOVER = -12219292800000L;

   private static long getJulianDay(Calendar cal)
   {
      int month = cal.get(Calendar.MONTH) + 1;
      int a = (14 - month) / 12;
      int year = cal.get(Calendar.YEAR);
      if(cal.get(Calendar.ERA) == GregorianCalendar.BC) {
         year = 1 - year; // adjust bc years where 1 BC = 0, 2 BC = -1, etc
      }
      int y = year + 4800 - a;
      int m = month + (12 * a) - 3;
      if(cal.getTimeInMillis() < DEFAULT_GREGORIAN_CUTOVER) {
         return cal.get(Calendar.DAY_OF_MONTH) + (((153*m)+2)/5) + (365*y) + (y/4) - 32083;
      }
      return cal.get(Calendar.DAY_OF_MONTH) + (((153*m)+2)/5) + (365*y) + (y/4) - (y/100) + (y/400) - 32045;

   }

}
