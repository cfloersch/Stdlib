package xpertss.util;

import java.util.Comparator;

/**
 * A utility for performing a "lazy" chained comparison statement, which
 * performs comparisons only until it finds a nonzero result. For example:
 * <pre>   {@code
 *
 *   public int compareTo(Foo that) {
 *     return Comparison.start()
 *         .compareSuper(super.compareTo(that))
 *         .compare(this.aString, that.aString)
 *         .compare(this.anInt, that.anInt)
 *         .compare(this.anIntArray, that.anIntArray, Integers.natural())
 *         .compare(this.anEnum, that.anEnum, Ordering.nullsLast())
 *         .compare(this.aList, that.aList, Iterables.ordering())
 *         .result();
 *   }}</pre>
 * <p/>
 * The value of this expression will have the same sign as the <i>first
 * nonzero</i> comparison result in the chain, or will be zero if every
 * comparison result was zero.
 * <p/>
 * Once any comparison returns a nonzero value, remaining comparisons are
 * "short-circuited".
 * <p/>
 * Currently the only way to handle arrays is to pass in a comparator which
 * can be easily done for primitive arrays due to Integers.comparator() and
 * equivalent methods in Bytes, Booleans, Floats, etc.. However, that impl
 * will not handle multi-dimensional arrays.
 */
@SuppressWarnings("SuspiciousNameCombination")
public abstract class Comparison {

   private Comparison() {}

   /**
    * Begins a new chained comparison statement. See example in the class
    * documentation.
    */
   public static Comparison start()
   {
      return ACTIVE;
   }

   private static final Comparison ACTIVE = new Comparison() {
      @Override public Comparison compareSuper(int result)
      {
         return classify(result);
      }
      @SuppressWarnings("unchecked")
      @Override public <T extends Comparable<T>> Comparison compare(T left, T right)
      {
         return classify(left.compareTo(right));
      }
      @Override public <T> Comparison compare(T left, T right, Comparator<T> comparator)
      {
         return classify(comparator.compare(left, right));
      }
      @Override public Comparison compare(boolean left, boolean right)
      {
         return classify(Boolean.compare(left, right));
      }
      @Override public Comparison compare(byte left, byte right)
      {
         return classify(Byte.compare(left, right));
      }
      @Override public Comparison compare(short left, short right)
      {
         return classify(Short.compare(left, right));
      }
      @Override public Comparison compare(int left, int right)
      {
         return classify(Integer.compare(left, right));
      }
      @Override public Comparison compare(long left, long right)
      {
         return classify(Long.compare(left, right));
      }
      @Override public Comparison compare(float left, float right)
      {
         return classify(Float.compare(left, right));
      }
      @Override public Comparison compare(double left, double right)
      {
         return classify(Double.compare(left, right));
      }
      Comparison classify(int result)
      {
         return (result < 0) ? LESS : (result > 0) ? GREATER : ACTIVE;
      }
      @Override public int result()
      {
         return 0;
      }
   };

   private static final Comparison LESS = new InactiveComparisonChain(-1);

   private static final Comparison GREATER = new InactiveComparisonChain(1);

   private static final class InactiveComparisonChain extends Comparison {
      final int result;

      InactiveComparisonChain(int result)
      {
         this.result = result;
      }
      @Override public Comparison compareSuper(int result)
      {
         return this;
      }
      @Override public <T extends Comparable<T>> Comparison compare(T left, T right)
      {
         return this;
      }
      @Override public <T> Comparison compare(T left, T right, Comparator<T> comparator)
      {
         return this;
      }
      @Override public Comparison compare(boolean left, boolean right)
      {
         return this;
      }
      @Override public Comparison compare(byte left, byte right)
      {
         return this;
      }
      @Override public Comparison compare(short left, short right)
      {
         return this;
      }
      @Override public Comparison compare(int left, int right)
      {
         return this;
      }
      @Override public Comparison compare(long left, long right)
      {
         return this;
      }
      @Override public Comparison compare(float left, float right)
      {
         return this;
      }
      @Override public Comparison compare(double left, double right)
      {
         return this;
      }
      @Override public int result()
      {
         return result;
      }
   }





   /**
    * Evaluates the result of calling {@code super.compareTo}, <i>if</i> the result
    * of this comparison has not already been determined.
    */
   public abstract Comparison compareSuper(int result);

   /**
    * Compares two comparable objects as specified by {@link Comparable#compareTo},
    * <i>if</i> the result of this comparison has not already been determined.
    */
   public abstract <T extends Comparable<T>> Comparison compare(T left, T right);

   /**
    * Compares two objects using a ordering, <i>if</i> the result of this comparison
    * has not already been determined.
    */
   public abstract <T> Comparison compare(T left, T right, Comparator<T> comparator);

   /**
    * Compares two {@code boolean} values as specified by {@link Boolean#compare}, <i>if</i>
    * the result of this comparison has not already been determined.
    */
   public abstract Comparison compare(boolean left, boolean right);

   /**
    * Compares two {@code byte} values as specified by {@link Byte#compare}, <i>if</i>
    * the result of this comparison has not already been determined.
    */
   public abstract Comparison compare(byte left, byte right);

   /**
    * Compares two {@code short} values as specified by {@link Short#compare}, <i>if</i>
    * the result of this comparison has not already been determined.
    */
   public abstract Comparison compare(short left, short right);

   /**
    * Compares two {@code int} values as specified by {@link Integer#compare}, <i>if</i>
    * the result of this comparison has not already been determined.
    */
   public abstract Comparison compare(int left, int right);

   /**
    * Compares two {@code long} values as specified by {@link Long#compare}, <i>if</i>
    * the result of this comparison has not already been determined.
    */
   public abstract Comparison compare(long left, long right);

   /**
    * Compares two {@code float} values as specified by {@link Float#compare}, <i>if</i>
    * the result of this comparison has not already been determined.
    */
   public abstract Comparison compare(float left, float right);

   /**
    * Compares two {@code double} values as specified by {@link Double#compare}, <i>if</i>
    * the result of this comparison has not already been determined.
    */
   public abstract Comparison compare(double left, double right);



   /**
    * Ends this comparison chain and returns its result: a value having the
    * same sign as the first nonzero comparison result in the chain, or zero if
    * every result was zero.
    */
   public abstract int result();

}
