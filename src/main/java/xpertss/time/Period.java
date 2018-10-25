/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Dec 18, 2002
 * Time: 4:01:31 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package xpertss.time;


import xpertss.lang.Objects;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A Period is an object that encompasses a start date and an end date.
 */
public class Period implements java.io.Serializable, Comparable<Period> {
   /**
    * This period's max is less than the supplied period's min.
    */
   public static final int BEFORE    = -4;

   /**
    * This period's max is equal to the supplied period's min.
    */
   public static final int PRECEEDS = -3;

   /**
    * This period's min is less than the supplied period's min and this period's max
    * is contained within the supplied period.
    */
   public static final int LEADING   = -2;

   /**
    * This period completely contains the supplied period and is not equal to the
    * supplied period.
    */
   public static final int CONTAINS  =  -1;

   /**
    * This period's min and max are the same as the supplied period's min and max.
    */
   public static final int EQUALS    =  0;

   /**
    * This period is completely contained within the supplied period and is not equal
    * to the supplied period.
    */
   public static final int CONTAINED = 1;


   /**
    * This period's max is greater than the supplied period's max and this period's
    * min is contained within the supplied period.
    */
   public static final int TRAILING  =  2;

   /**
    * This period's min is equal to the supplied period's max.
    */
   public static final int FOLLOWS = 3;

   /**
    * This period's min is greater than the supplied period's max.
    */
   public static final int AFTER     =  4;



   private Date start;
   private Date end;



   public Period(Date start, Date end)
   {
      if(end.compareTo(Objects.notNull(start)) <= 0)
         throw new IllegalArgumentException();
      this.start = start;
      this.end = end;
   }




   /**
    * Returns the start bound for this period.
    */
   public Date getStart()
   {
      return start;
   }

   /**
    * Returns the maximum value for this period.
    */
   public Date getEnd()
   {
      return end;
   }



   /**
    * Check to see if the period contains the specified date. Returns <tt>true</tt> if
    * the argument date is contained in the period and <tt>false</tt> otherwise.
    * <p><pre>
    *    this.start &lt;= date &lt; this.end
    * </pre>
    */
   public boolean contains(Date date)
   {
      return start.compareTo(date) <= 0 && date.compareTo(end) < 0;
   }


   /**
    * Check to see if the supplied date falls within this period. Returns <tt>true</tt>
    * if the argument date falls within the period and <tt>false</tt> otherwise.
    * <p><pre>
    *    this.start &lt;= date &lt;= this.end
    * </pre>
    */
   public boolean within(Date date)
   {
      return start.compareTo(date) <= 0 && date.compareTo(end) <= 0;
   }


   /**
    * Check to see if the supplied date falls within this period exclusive. Returns
    * <tt>true</tt> if the argument date falls within the period and <tt>false</tt>
    * otherwise.
    * <p><pre>
    *    this.start &lt; date &lt; this.end
    * </pre>
    */
   public boolean between(Date date)
   {
      return start.compareTo(date) < 0 && date.compareTo(end) < 0;
   }








   /**
    * This method returns an integer constant defining this period's relationship to the
    * supplied period.
    *
    * @see #BEFORE
    * @see #PRECEEDS
    * @see #LEADING
    * @see #CONTAINS
    * @see #EQUALS
    * @see #CONTAINED
    * @see #TRAILING
    * @see #FOLLOWS
    * @see #AFTER
    */
   public int compareTo(Period range)
   {
      if(end.compareTo(range.start) < 0) {
         return BEFORE;
      } else if(start.compareTo(range.end) > 0) {
         return AFTER;

      } else if(end.compareTo(range.start) == 0) {
         return PRECEEDS;
      } else if(start.compareTo(range.end) == 0) {
         return FOLLOWS;

      } else if(start.compareTo(range.start) < 0) {
         if(end.compareTo(range.end) < 0) {
            return LEADING;
         } else {
            return CONTAINS;
         }
      } else if(start.compareTo(range.start) > 0) {
         if(end.compareTo(range.end) <= 0) {
            return CONTAINED;
         } else {
            return TRAILING;
         }
      } else {
         if(end.compareTo(range.end) == 0) {
            return EQUALS;
         } else if(end.compareTo(range.end) < 0) {
            return CONTAINED;
         } else {
            return CONTAINS;
         }
      }
   }











   /**
    * Returns a period that includes all the values where the supplied period
    * intersects this period.  Returns {@code null} if the supplied period does
    * not share common values.
    */
   public Period intersection(Period range)
   {
      switch(compareTo(range)) {
         case LEADING:
            return new Period(range.start, end);
         case EQUALS:
         case CONTAINED:
            return new Period(start, end);
         case CONTAINS:
            return new Period(range.start, range.end);
         case TRAILING:
            return new Period(start, range.end);
      }
      return null;
   }

   /**
    * Returns a period that includes all the values from the supplied period and
    * this period. For a union to be possible the two periods must be contiguous
    * which is to say they do not relate to one another as {@link #BEFORE} or
    * {@link #AFTER}. If the periods are not contiguous then {@code null} is
    * returned.
    */
   public Period union(Period range)
   {
      switch(compareTo(range)) {
         case BEFORE:
         case AFTER:
            return null;
      }
      return new Period(Dates.min(start, range.start), Dates.max(end, range.end));
   }

   /**
    * Returns a new period that contains all of the values from both periods.
    * That is to say the returned period will have a
    * {@code start = min(this.start, other.start)} and an
    * {@code end = max(this.end, other.end)}.
    */
   public Period sum(Period range)
   {
      return new Period(Dates.min(start, range.start), Dates.max(end, range.end));
   }

   /**
    * Returns a period which represents the gap between this period and a
    * specified period. This will return {@code null} for all relations other
    * than {@link #BEFORE} and {@link #AFTER}.
    */
   public Period gap(Period range)
   {
      switch(compareTo(range)) {
         case BEFORE:
         case AFTER:
            return new Period(Dates.min(end, range.end), Dates.max(start, range.start));
      }
      return null;
   }

   /**
    * Returns a list of period objects that contain all the values contained in
    * this period that are not shared with the supplied period.  Returns an empty
    * list if the supplied period equals this period or if the supplied period
    * completely contains this period.
    */
   public List<Period> difference(Period range)
   {
      switch(compareTo(range)) {
         case BEFORE:
         case PRECEEDS:
         case FOLLOWS:
         case AFTER:
            return Arrays.asList(clone());
         case LEADING:
            return Arrays.asList(new Period(start, range.start));
         case TRAILING:
            return Arrays.asList(new Period(range.end, end));
         case CONTAINS:
            List<Period> result = new ArrayList<Period>();
            if(start.compareTo(range.start) < 0) {
               result.add(new Period(start, range.start));
            }
            if(end.compareTo(range.end) > 0) {
               result.add(new Period(range.end, end));
            }
            return result;
         default:
            return Collections.emptyList();
      }
   }


   /**
    * Returns true if the start and end date of this Period are equal.
    */
   public boolean equals(Object o)
   {
      if(o instanceof Period) {
         Period r = (Period) o;
         return start.equals(r.start) && end.equals(r.end);
      }
      return false;
   }

   /**
    * Returns a hash code for this Period.
    */
   public int hashCode()
   {
      return Objects.hash(start, end);
   }


   /**
    * Creates a shallow clone of this Period.
    */
   public Period clone()
   {
      return new Period(start, end);
   }


   /**
    * Converts this Period to a String using the system's default DateFormat.
    */
   public String toString()
   {
      return toString(null);
   }


   /**
    * Converts this Period to a String using the specified DateFormat.
    * <p>
    * TODO Rework this to use DateFormatter
    */
   public String toString(DateFormat format)
   {
      if(format == null) format = DateFormat.getDateTimeInstance();
      return format.format(start) + " - " + format.format(end);
   }

}
