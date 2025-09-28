/**
 * Property of XpertSoftware. All rights reserved.
 * User: floersh
 * Date: Dec 11, 2005 - 8:44:08 PM
 */
package xpertss.lang;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.List;

/**
 * A Range is a contiguous serious of numeric values with an inclusive upper and
 * lower bound.
 * <p>
 * An empty range is defined as one which has the same value for its minimum as it
 * does for its maximum. All ranges are guaranteed to have a minimum value less than
 * or equal to their maximum value.
 * <p/>
 * A range includes {@link #contains(Number)}, {@link #within(Number)}, and {@link
 * #between(Number)} methods that allow for various inclusivity tests.
 * <p/>
 * A range also includes {@link #gap(Range)}, {@link #sum(Range)}, {@link #union(Range)},
 * {@link #difference(Range)}, and {@link #intersection(Range)} methods that allow you
 * to create new ranges from a given starting set.
 * <p/>
 * Finally, the {@link #compareTo(Range)} method will return various values indicating
 * in what way the given range compares to this range. For example it might fall {@link
 * #BEFORE} or {@link #AFTER} or it may {@link #PRECEEDS} or {@link #TRAILING}.
 * <p/>
 * Due to the type safety inherent in generic classes the equals method will return
 * {@code true} if, and only if, the two ranges are of the same numeric TYPE and the
 * {@link #getLower()}s and {@link #getUpper()}s match. An Integer based range will
 * not equate to a Long based range even if their values are technically equal.
 */
public class Range<T extends Number & Comparable<T>> implements Comparable<Range<T>>, Serializable {

   /**
    * This range's max is less than the supplied range's min.
    */
   public static final int BEFORE    = -4;

   /**
    * This ranges max is equal to the supplied range's min.
    */
   public static final int PRECEEDS = -3;

   /**
    * This range's min is less than the supplied range's min and this range's max
    * is contained within the supplied range.
    */
   public static final int LEADING   = -2;

   /**
    * This range completely contains the supplied range and is not equal to the
    * supplied range.
    */
   public static final int CONTAINS  =  -1;

   /**
    * This range's min and max are the same as the supplied range's min and max.
    */
   public static final int EQUALS    =  0;

   /**
    * This range is completely contained within the supplied range and is not equal
    * to the supplied range.
    */
   public static final int CONTAINED = 1;


   /**
    * This range's max is greater than the supplied range's max and this range's
    * min is contained within the supplied range.
    */
   public static final int TRAILING  =  2;

   /**
    * This range's min is equal to the supplied range's max.
    */
   public static final int FOLLOWS = 3;

   /**
    * This range's min is greater than the supplied range's max.
    */
   public static final int AFTER     =  4;



   private final T lower;
   private final T upper;


   /**
    * Constructs a Range from the given lower and upper bounds.
    *
    * @throws NullPointerException if either argument is {@code null}
    * @throws IllegalArgumentException if lower is not less than or equal
    *    to upper
    */
   public Range(T lower, T upper)
   {
      if(upper.compareTo(lower) <= 0) throw new IllegalArgumentException();
      this.lower = lower;
      this.upper = upper;
   }




   /**
    * Returns the lower bound for this Range.
    */
   public T getLower()
   {
      return lower;
   }

   /**
    * Returns the maximum value for this Range.
    */
   public T getUpper()
   {
      return upper;
   }



   /**
    * Check to see if the Range contains the supplied number. Returns <tt>true</tt>
    * if the argument number falls within the Range and <tt>false</tt> otherwise.
    * <p><pre>
    *    this.min &lt;= number &lt; this.max
    * </pre>
    */
   public boolean contains(T number)
   {
      return lower.compareTo(number) <= 0 && number.compareTo(upper) < 0;
   }


   /**
    * Check to see if the supplied number falls within this Range inclusive. Returns
    * <tt>true</tt> if the argument number falls within the Range and <tt>false</tt>
    * otherwise.
    * <p><pre>
    *    this.min &lt;= number &lt;= this.max
    * </pre>
    */
   public boolean within(T number)
   {
      return lower.compareTo(number) <= 0 && number.compareTo(upper) <= 0;
   }


   /**
    * Check to see if the supplied number falls within this Range exclusive. Returns
    * <tt>true</tt> if the argument number falls within the Range and <tt>false</tt>
    * otherwise.
    * <p><pre>
    *    this.min &lt; number &lt; this.max
    * </pre>
    */
   public boolean between(T number)
   {
      return lower.compareTo(number) < 0 && number.compareTo(upper) < 0;
   }






   /**
    * This method returns an integer constant defining this range's relationship to the
    * supplied range.
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
   public int compareTo(Range<T> range)
   {

      if(upper.compareTo(range.lower) < 0) {
         return BEFORE;
      } else if(lower.compareTo(range.upper) > 0) {
         return AFTER;
      } else if(upper.compareTo(range.lower) == 0) {
         return PRECEEDS;
      } else if(lower.compareTo(range.upper) == 0) {
         return FOLLOWS;
      } else if(lower.compareTo(range.lower) < 0) {
         if(upper.compareTo(range.upper) < 0) {
            return LEADING;
         } else {
            return CONTAINS;
         }
      } else if(lower.compareTo(range.lower) > 0) {
         if(upper.compareTo(range.upper) <= 0) {
            return CONTAINED;
         } else {
            return TRAILING;
         }
      } else {
         if(upper.compareTo(range.upper) == 0) {
            return EQUALS;
         } else if(upper.compareTo(range.upper) < 0) {
            return CONTAINED;
         } else {
            return CONTAINS;
         }
      }
   }











   /**
    * Returns a range that includes all the values where the supplied range intersects
    * this range.  Returns <tt>null</tt> if the supplied Range does not share common
    * values.
    */
   public Range<T> intersection(Range<T> range)
   {
      switch(compareTo(range)) {
         case LEADING:
            return new Range<>(range.lower, upper);
         case EQUALS:
         case CONTAINED:
            return new Range<>(lower, upper);
         case CONTAINS:
            return new Range<>(range.lower, range.upper);
         case TRAILING:
            return new Range<>(lower, range.upper);
      }
      return null;
   }

   /**
    * Returns a range that includes all the values from the supplied range and this
    * range. For a union to be possible the two ranges must be contiguous which is
    * to say they do not relate to one another as BEFORE or AFTER. If the ranges
    * are not contiguous then <tt>null</tt> is returned.
    */
   public Range<T> union(Range<T> range)
   {
      switch(compareTo(range)) {
         case BEFORE:
         case AFTER:
            return null;
      }
      return new Range<>(Numbers.min(lower, range.lower), Numbers.max(upper, range.upper));
   }

   /**
    * Returns a new Range that contains all the values from both Ranges.  That is
    * to say the returned range will have a lower = min(this.lower, other.lower) and
    * an upper = max(this.upper, other.upper).
    */
   public Range<T> sum(Range<T> range)
   {
      return new Range<>(Numbers.min(lower, range.lower), Numbers.max(upper, range.upper));
   }

   /**
    * Returns a range which represents the gap between this range and a specified range.
    * This will return <tt>null</tt> for all relations other than BEFORE and AFTER.
    */
   public Range<T> gap(Range<T> range)
   {
      switch(compareTo(range)) {
         case BEFORE:
         case AFTER:
            return new Range<>(Numbers.min(upper, range.upper), Numbers.max(lower, range.lower));
      }
      return null;
   }

   /**
    * Returns a list of range objects that contain all the values contained in this range
    * that are not shared with the supplied range.  Returns an empty list if the supplied
    * range equals this range or if the supplied range completely contains this range.
    */
   public List<Range<T>> difference(Range<T> range)
   {
      switch(compareTo(range)) {
         case BEFORE:
         case PRECEEDS:
         case FOLLOWS:
         case AFTER:
            return Collections.singletonList(clone());
         case LEADING:
            return Collections.singletonList(new Range<>(lower, range.lower));
         case TRAILING:
            return Collections.singletonList(new Range<>(range.upper, upper));
         case CONTAINS:
            List<Range<T>> result = new ArrayList<>();
            if(lower.compareTo(range.lower) < 0) {
               result.add(new Range<>(lower, range.lower));
            }
            if(upper.compareTo(range.upper) > 0) {
               result.add(new Range<>(range.upper, upper));
            }
            return result;
         default:
            return Collections.emptyList();
      }
   }






   @Override
   public boolean equals(Object o)
   {
      if(o instanceof Range) {
         Range r = (Range) o;
         return lower.equals(r.lower) && upper.equals(r.upper);
      }
      return false;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(lower, upper);
   }


   @Override
   public Range<T> clone()
   {
      return new Range<>(lower, upper);
   }


   /**
    * Converts this Range to a String format using the system's default NumberFormat.
    */
   @Override
   public String toString()
   {
      return toString(null);
   }


   /**
    * Converts this Range to a String format using the specified NumberFormat.
    */
   public String toString(NumberFormat format)
   {
      if(format == null) format = NumberFormat.getNumberInstance();
      return format.format(lower) + " - " + format.format(upper);
   }


}
