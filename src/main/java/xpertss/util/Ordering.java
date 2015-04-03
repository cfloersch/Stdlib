package xpertss.util;

import xpertss.function.Function;
import xpertss.lang.Objects;

import java.io.Serializable;
import java.util.Comparator;

/*
 * Source
 * http://hg.openjdk.java.net/jdk8/jdk8-gate/jdk/file/484e16c0a040/src/share/classes/java/util/Comparator.java
 * http://hg.openjdk.java.net/jdk8/jdk8-gate/jdk/file/484e16c0a040/src/share/classes/java/util/Comparators.java
 */


/**
 * Ordering provides a rich interface for creating Comparators.
 * <p/>
 * The common ways to get an instance of Ordering are:
 * <ul>
 * <li>Subclass it and implement {@link #compare(Object, Object)} instead of implementing
 *    {@link Comparator} directly</li>
 * <li>Pass a pre-existing {@link Comparator} instance to {@link #from(Comparator)}</li>
 * <li>Use the natural ordering, {@link #natural()}</li>
 * <li>Use the reverse natural ordering, {@link #natural()}</li>
 * </ul>
 * <p/>
 * Then you can use the chaining methods to get an altered version of that {@code
 * Ordering}, including:
 * <ul>
 * <li>{@link #reverse()}</li>
 * <li>{@link #thenComparing(Ordering)}</li>
 * <li>{@link #thenComparing(xpertss.function.Function)}</li>
 * <li>{@link #thenComparing(xpertss.function.Function, Ordering)}</li>
 * </ul>
 * <p/>
 * A couple of utility methods are provided as well to wrap orderings and handle {@code
 * null} arguments.
 * <p/>
 * Example that will use the reverse of the object's natural ordering and sorting
 * {@code null}s first: <pre>   {@code
 *
 *   Ordering<String> ordering = Ordering.nullsFirst(Ordering.<String>reverse())
 *
 * }</pre>
 * Example that will order an {@code int[]}: <pre>   {@code
 *
 *   Ordering<int[]> ordering = Ordering.from(Integers.comparator())
 *
 * }</pre>
 * Example that will order an {@code Integer[]} allowing one or both of the arrays to be
 * {@code null}:
 * <pre>   {@code
 *
 *   Ordering<Integer[]> ordering = Ordering.nullsFirst(Ordering.from(Objects.comparator()))
 *
 * }</pre>
 */
public abstract class Ordering<T> implements Comparator<T> {


   /**
    * Returns a comparator that compares {@link Comparable} objects in natural order.
    * <p/>
    * The returned comparator is serializable and throws {@link NullPointerException}
    * when comparing {@code null} objects.
    *
    * @param  <T> the {@link Comparable} type of element to be compared
    * @return a comparator that imposes the <i>natural ordering</i> on {@code
    *         Comparable} objects.
    * @see Comparable
    */
   @SuppressWarnings("unchecked")
   public static <T extends Comparable<? super T>> Ordering<T> natural()
   {
      return (Ordering<T>) NaturalOrdering.INSTANCE;
   }

   /**
    * Returns a comparator that imposes the reverse of the <em>natural ordering</em>.
    * <p/>
    * The returned comparator is serializable and throws {@link NullPointerException}
    * when comparing {@code null} objects.
    *
    * @param  <T> the {@link Comparable} type of element to be compared
    * @return a comparator that imposes the reverse of the <i>natural ordering</i> on
    *         {@code Comparable} objects.
    * @see Comparable
    */
   @SuppressWarnings("unchecked")
   public static <T extends Comparable<? super T>> Ordering<T> reversed()
   {
      return (Ordering<T>) ReversedOrdering.INSTANCE;
   }




   /**
    * Returns an ordering for a pre-existing {@link Comparator}. Note that if the
    * comparator is not pre-existing, and you don't require serialization, you can
    * subclass {@code Ordering} and implement its {@link #compare(Object, Object)}
    * method instead.
    *
    * @param comparator the comparator that defines the order
    */
   @SuppressWarnings("unchecked")
   public static <T> Ordering<T> from(Comparator<? super T> comparator)
   {
      if(comparator instanceof Ordering) return Objects.cast(comparator);
      return (Ordering<T>) new ComparatorOrdering<>(comparator);
   }






   /**
    * Returns a null-friendly ordering that considers {@code null} to be less than non-null.
    * When both are {@code null}, they are considered equal.  If both are non-null, their
    * natural order is used to determine the order.
    * <p/>
    * The returned ordering is serializable if the specified ordering is serializable.
    *
    * @param  <T> the type of the elements to be compared
    * @return a comparator that considers {@code null} to be less than non-null, and
    *         orders non-null by their natural ordering.
    */
   public static <T> Ordering<T> nullsFirst()
   {
      return new NullOrdering<>(true);
   }

   /**
    * Returns a null-friendly ordering that considers {@code null} to be less than non-null.
    * When both are {@code null}, they are considered equal.  If both are non-null, the
    * specified {@code Ordering} is used to determine the order. If the specified ordering
    * is {@code null}, then the returned ordering considers all non-null values to be equal.
    * <p/>
    * The returned ordering is serializable if the specified ordering is serializable.
    *
    * @param  <T> the type of the elements to be compared
    * @param  ordering an {@code Ordering} for comparing non-null values
    * @return a comparator that considers {@code null} to be less than non-null, and
    *         compares non-null objects with the supplied {@code Ordering}.
    */
   public static <T> Ordering<T> nullsFirst(Ordering<? super T> ordering)
   {
      return new NullOrdering<>(true, ordering);
   }


   /**
    * Returns a null-friendly ordering that considers {@code null} to be greater than
    * non-null. When both are {@code null}, they are considered equal.  If both are
    * non-null, their natural order is used to determine the order.
    * <p/>
    * The returned ordering is serializable if the specified ordering is serializable.
    *
    * @param  <T> the type of the elements to be compared
    * @return a comparator that considers {@code null} to be greater than non-null, and
    *         orders non-null by their natural ordering.
    */
   public static <T> Ordering<T> nullsLast()
   {
      return new NullOrdering<>(false);
   }

   /**
    * Returns a null-friendly ordering that considers {@code null} to be greater than
    * non-null. When both are {@code null}, they are considered equal.   If both are
    * non-null, the specified {@code Ordering} is used to determine the order. If the
    * specified ordering is {@code null},  then the returned ordering  considers all
    * non-null values to be equal.
    * <p/>
    * The returned ordering is serializable if the specified ordering is serializable.
    *
    * @param  <T> the type of the elements to be compared
    * @param  ordering an {@code Ordering} for comparing non-null values
    * @return an ordering that considers {@code null} to be greater than non-null, and
    *         orders non-null objects with the supplied {@code Ordering}.
    */
   public static <T> Ordering<T> nullsLast(Ordering<? super T> ordering)
   {
      return new NullOrdering<>(false, ordering);
   }

   /**
    * Accepts a function that extracts a sort key from a type {@code T}, and returns an
    * {@code Ordering<T>} that compares by that sort key using the specified {@link
    * Ordering}.
    * <p/>
    * The returned ordering is serializable if the specified function and ordering are
    * both serializable.
    * <p/>
    * For example, to obtain an {@code Ordering} that compares {@code Person} objects by
    * their last name ignoring case differences,
    *
    * <pre>{@code
    *     Ordering<Person> cmp = Ordering.comparing(
    *             new PersonGetLastNameFunction(),
    *             Ordering.from(String.CASE_INSENSITIVE_ORDER));
    * }</pre>
    *
    * @param  <T> the type of element to be compared
    * @param  <U> the type of the sort key
    * @param  keyExtractor the function used to extract the sort key
    * @param  keyComparator the {@code Ordering} used to compare the sort key
    * @return an ordering that compares by an extracted key using the specified
    *         {@code Ordering}
    * @throws NullPointerException if either argument is {@code null}
    */
   public static <T,U> Ordering<T> comparing(Function<? super T,? extends U> keyExtractor,
                                             Ordering<? super U> keyComparator)
   {
      return new ComposedOrdering<>(keyComparator, keyExtractor);
   }




   /**
    * Accepts a function that extracts a {@link Comparable} sort key from a type {@code T},
    * and returns an {@code Ordering<T>} that compares by that sort key.
    * <p/>
    * The returned ordering is serializable if the specified function is also serializable.
    * <p/>
    * For example, to obtain an {@code Ordering} that compares {@code Person} objects by their
    * last name,
    *
    * <pre>{@code
    *     Ordering<Person> byLastName = Ordering.comparing(new PersonGetLastNameFunction());
    * }</pre>
    *
    * @param  <T> the type of element to be compared
    * @param  <U> the type of the {@code Comparable} sort key
    * @param  keyExtractor the function used to extract the {@link Comparable} sort key
    * @return an ordering that compares by an extracted key
    * @throws NullPointerException if the argument is {@code null}
    */
   @SuppressWarnings("unchecked")
   public static <T,U extends Comparable<? super U>> Ordering<T> comparing(Function<? super T,? extends U> keyExtractor)
   {
      return new ComposedOrdering<>((Ordering<U>)NaturalOrdering.INSTANCE, keyExtractor);
   }






   @Override
   public abstract int compare(T o1, T o2);






   /**
    * Returns an ordering that imposes the reverse ordering of this ordering.
    *
    * @return an ordering that imposes the reverse ordering of this ordering.
    */
   @SuppressWarnings("unchecked")
   public Ordering<T> reverse()
   {
      return new ReverseOrdering(this);
   }


   /**
    * Returns a lexicographic ordering with another ordering. If this {@code Ordering}
    * considers two elements equal, i.e. {@code compare(a, b) == 0}, {@code other} is
    * used to determine the order.
    * <p/>
    * The returned ordering is serializable if the specified ordering is also serializable.
    * <p/>
    * For example, to sort a collection of {@code String} based on the length and then
    * case-insensitive natural ordering, the ordering can be composed using following
    * code,
    *
    * <pre>{@code
    *     Ordering<String> cmp = Ordering.comparing(String::length)
    *             .thenComparing(Ordering.from(String.CASE_INSENSITIVE_ORDER));
    * }</pre>
    *
    * @param  other the other ordering to be used when this ordering compares two objects
    *               that are equal.
    * @return a lexicographic ordering composed of this and then the other ordering
    * @throws NullPointerException if the argument is {@code null}.
    */
   public Ordering<T> thenComparing(Ordering<? super T> other)
   {
      return new CompoundOrdering<>(this, other);
   }


   /**
    * Returns a lexicographic ordering with a function that extracts a key to be compared
    * with the given {@code Ordering}.
    * <p/>
    * This default implementation behaves as if
    *    {@code thenComparing(comparing(keyExtractor, cmp))}.
    *
    * @param  <U>  the type of the sort key
    * @param  keyExtractor the function used to extract the sort key
    * @param  keyComparator the {@code Ordering} used to compare the sort key
    * @return a lexicographic ordering composed of this ordering and then comparing on the
    *          key extracted by the keyExtractor function
    * @throws NullPointerException if either argument is {@code null}.
    * @see #comparing(Function, Ordering)
    * @see #thenComparing(Ordering)
    */
   public <U extends Comparable<? super U>> Ordering<T> thenComparing(Function<? super T,? extends U> keyExtractor,
                                                                      Ordering<? super U> keyComparator)
   {
      return thenComparing(comparing(keyExtractor, keyComparator));
   }

   /**
    * Returns a lexicographic ordering with a function that extracts a {@code Comparable}
    * sort key.
    * <p/>
    * This default implementation behaves as if
    *    {@code thenComparing(comparing(keyExtractor))}.
    *
    * @param  <U>  the type of the {@link Comparable} sort key
    * @param  keyExtractor the function used to extract the {@link Comparable} sort key
    * @return a lexicographic ordering composed of this and then the {@link Comparable}
    *          sort key.
    * @throws NullPointerException if the argument is {@code null}.
    * @see #comparing(Function)
    * @see #thenComparing(Ordering)
    */
   public <U extends Comparable<? super U>> Ordering<T> thenComparing(Function<? super T,? extends U> keyExtractor)
   {
      return thenComparing(comparing(keyExtractor));
   }



   static class NaturalOrdering extends Ordering<Comparable<Object>> implements Serializable {

      private static final long serialVersionUID = 0;
      private static final Ordering<Comparable<Object>> INSTANCE = new NaturalOrdering();

      @Override public int compare(Comparable<Object> c1, Comparable<Object> c2)
      {
         return Integer.signum(c1.compareTo(c2));
      }
      @Override public Ordering<Comparable<Object>> reverse()
      {
         return ReversedOrdering.INSTANCE;
      }
      @Override public String toString()
      {
         return "Ordering.natural()";
      }
      private Object readResolve()
      {
         return INSTANCE;
      }

   }

   static class ReversedOrdering extends Ordering<Comparable<Object>> implements Serializable {

      private static final long serialVersionUID = 0;
      private static final Ordering<Comparable<Object>> INSTANCE = new ReversedOrdering();

      @Override public int compare(Comparable<Object> c1, Comparable<Object> c2)
      {
         return Integer.signum(c2.compareTo(c1));
      }

      @Override public Ordering<Comparable<Object>> reverse()
      {
         return NaturalOrdering.INSTANCE;
      }
      @Override public String toString()
      {
         return "Ordering.reverse()";
      }
      private Object readResolve()
      {
         return INSTANCE;
      }

   }

   static class NullOrdering<T> extends Ordering<T> implements Serializable {

      private static final long serialVersionUID = 0;
      private final boolean nullFirst;
      // if null, non-null Ts are considered equal
      private final Ordering<T> real;

      @SuppressWarnings({ "unchecked", "RedundantCast" })
      NullOrdering(boolean nullFirst)
      {
         this.nullFirst = nullFirst;
         this.real = (Ordering<T>) Ordering.natural();
      }

      @SuppressWarnings("unchecked")
      NullOrdering(boolean nullFirst, Ordering<?> real)
      {
         this.nullFirst = nullFirst;
         this.real = (Ordering<T>) real;
      }

      @Override
      public int compare(T a, T b)
      {
         if (a == null) {
            return (b == null) ? 0 : (nullFirst ? -1 : 1);
         } else if (b == null) {
            return nullFirst ? 1: -1;
         } else {
            return (real == null) ? 0 : real.compare(a, b);
         }
      }
      @Override
      public Ordering<T> thenComparing(Ordering<? super T> other)
      {
         Objects.notNull(other);
         return new NullOrdering<>(nullFirst, real == null ? other : real.thenComparing(other));
      }
      @Override
      public Ordering<T> reverse()
      {
         return new NullOrdering<>(!nullFirst, real == null ? null : real.reverse());
      }
      @Override public boolean equals(Object object)
      {
         if (object == this) return true;
         if (object instanceof NullOrdering) {
            NullOrdering<?> that = (NullOrdering<?>) object;
            return Objects.equal(this.real, that.real)
                  && Objects.equal(this.nullFirst, that.nullFirst);
         }
         return false;
      }
      @Override public int hashCode()
      {
         return Objects.hash(real, nullFirst) ^ -921210296; // meaningless
      }
      @Override public String toString()
      {
         if(real == null) return (nullFirst) ? "nullsFirst()" : "nullsLast()";
         return (nullFirst) ? real + ".nullsFirst()" : real + ".nullsLast()";
      }
   }

   static class ComposedOrdering<U,T> extends Ordering<T> implements Serializable {


      private final Ordering<? super U> keyComparator;
      private final Function<? super T, ? extends U> keyExtractor;

      ComposedOrdering(Ordering<? super U> keyComparator, Function<? super T, ? extends U> keyExtractor)
      {
         this.keyComparator = Objects.notNull(keyComparator, "keyComparator");
         this.keyExtractor = Objects.notNull(keyExtractor, "keyExtractor");

      }

      @Override
      public int compare(T left, T right)
      {
         return keyComparator.compare(keyExtractor.apply(left), keyExtractor.apply(right));
      }

      @Override public boolean equals(Object object)
      {
         if (object == this) return true;
         if (object instanceof ComposedOrdering) {
            ComposedOrdering<?, ?> that = Objects.cast(object);
            return Objects.equal(this.keyComparator, that.keyComparator)
                  && Objects.equal(this.keyExtractor, that.keyExtractor);
         }
         return false;
      }

      @Override public int hashCode()
      {
         return Objects.hash(keyComparator, keyExtractor);
      }

      @Override public String toString()
      {
         return keyComparator + ".thenComparing(" + keyExtractor + ")";
      }


   }

   static class ReverseOrdering<T> extends Ordering<T> implements Serializable {
      final Ordering<T> forwardOrder;

      ReverseOrdering(Ordering<T> forwardOrder)
      {
         this.forwardOrder = Objects.notNull(forwardOrder);
      }

      @Override public int compare(T left, T right)
      {
         return forwardOrder.compare(right, left);
      }

      @SuppressWarnings("unchecked") // how to explain?
      @Override public Ordering<T> reverse()
      {
         return forwardOrder;
      }
      @Override public int hashCode()
      {
         return -forwardOrder.hashCode();
      }
      @Override public boolean equals(Object object)
      {
         if (object == this) return true;
         if (object instanceof ReverseOrdering) {
            ReverseOrdering<?> that = (ReverseOrdering<?>) object;
            return this.forwardOrder.equals(that.forwardOrder);
         }
         return false;
      }
      @Override public String toString()
      {
         return forwardOrder + ".reverse()";
      }

      private static final long serialVersionUID = 0;
   }

   static class CompoundOrdering<T> extends Ordering<T> implements Serializable {
      final Ordering<T> first;
      final Ordering<? super T> second;

      CompoundOrdering(Ordering<T> first, Ordering<? super T> second)
      {
         this.first = Objects.notNull(first);
         this.second = Objects.notNull(second);
      }


      @Override public int compare(T left, T right)
      {
         int result = first.compare(left, right);
         return (result == 0) ? second.compare(left, right) : result;
      }
      @Override public boolean equals(Object object)
      {
         if (object == this) return true;
         if (object instanceof CompoundOrdering) {
            CompoundOrdering<?> that = (CompoundOrdering<?>) object;
            return Objects.equal(this.first, that.first)
                  && Objects.equal(this.second, that.second);
         }
         return false;
      }
      @Override public int hashCode()
      {
         return Objects.hash(first, second);
      }
      @Override public String toString()
      {
         return first + ".thenComparing(" + second + ")";
      }

      private static final long serialVersionUID = 0;
   }

   static class ComparatorOrdering<T> extends Ordering<T> implements Serializable {
      final Comparator<T> comparator;

      ComparatorOrdering(Comparator<T> comparator)
      {
         this.comparator = Objects.notNull(comparator);
      }

      @Override public int compare(T a, T b)
      {
         return Integer.signum(comparator.compare(a, b));
      }


      @Override public boolean equals(Object object)
      {
         if (object == this) return true;
         if (object instanceof ComparatorOrdering) {
            ComparatorOrdering<?> that = (ComparatorOrdering<?>) object;
            return this.comparator.equals(that.comparator);
         }
         return false;
      }

      @Override public int hashCode()
      {
         return comparator.hashCode();
      }

      @Override public String toString()
      {
         return comparator.toString();
      }

      private static final long serialVersionUID = 0;
   }

}

