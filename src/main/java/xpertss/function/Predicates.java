/**
 * Created By: cfloersch
 * Date: 1/6/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;

import xpertss.lang.Classes;
import xpertss.lang.Objects;
import xpertss.util.Sets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Static utility methods pertaining to {@code Predicate} instances.
 * <p>
 * All methods returns serializable predicates as long as they're given
 * serializable parameters.
 *
 * @see Predicate
 */
@SuppressWarnings("UnusedDeclaration")
public final class Predicates {

   private Predicates() { }


   /**
    * Returns a predicate that always evaluates to {@code true}.
    */
   public static <T> Predicate<T> alwaysTrue()
   {
      return ObjectPredicate.AlwaysTrue.withNarrowedType();
   }

   /**
    * Returns a predicate that always evaluates to {@code false}.
    */
   public static <T> Predicate<T> alwaysFalse()
   {
      return ObjectPredicate.AlwaysFalse.withNarrowedType();
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the object reference
    * being tested is {@code null}.
    */
   public static <T> Predicate<T> isNull()
   {
      return ObjectPredicate.IsNull.withNarrowedType();
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the object reference
    * being tested is not {@code null}.
    */
   public static <T> Predicate<T> notNull()
   {
      return ObjectPredicate.NotNull.withNarrowedType();
   }

   enum ObjectPredicate implements Predicate<Object> {
      AlwaysTrue {
         @Override public boolean test(Object o)
         {
            return true;
         }
      },
      AlwaysFalse {
         @Override public boolean test(Object o)
         {
            return false;
         }
      },
      IsNull {
         @Override public boolean test(Object o)
         {
            return o == null;
         }
      },
      NotNull {
         @Override public boolean test(Object o)
         {
            return o != null;
         }
      };

      @SuppressWarnings("unchecked") // these Object predicates work for any T
      <T> Predicate<T> withNarrowedType()
      {
         return (Predicate<T>) this;
      }
   }



   /**
    * Returns a predicate that evaluates to {@code true} if the given predicate
    * evaluates to {@code false}.
    *
    * @throws NullPointerException if predicate is {@code null}
    */
   public static <T> Predicate<T> not(Predicate<T> predicate)
   {
      return new NotPredicate<>(predicate);
   }

   static class NotPredicate<T> implements Predicate<T>, Serializable {
      final Predicate<T> predicate;

      NotPredicate(Predicate<T> predicate)
      {
         this.predicate = Objects.notNull(predicate);
      }

      @Override
      public boolean test(T t)
      {
         return !predicate.test(t);
      }

      @Override public int hashCode()
      {
         return ~predicate.hashCode();
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof NotPredicate) {
            NotPredicate<?> that = (NotPredicate<?>) obj;
            return predicate.equals(that.predicate);
         }
         return false;
      }

      @Override
      public String toString()
      {
         return "Not(" + predicate.toString() + ")";
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns a predicate that evaluates to {@code true} if each of its components
    * evaluates to {@code true}. The components are evaluated in order, and
    * evaluation will be "short-circuited" as soon as a {@code false} predicate is
    * found.
    * <p>
    * It defensively copies the array passed in, so future changes to it won't alter
    * the behavior of this predicate. If {@code components} is empty, the returned
    * predicate will always evaluate to {@code true}.
    *
    * @throws NullPointerException if components is {@code null} or any of its items
    *    are {@code null}
    */
   @SafeVarargs
   public static <T> Predicate<T> and(Predicate<? super T> ... components)
   {
      return new AndPredicate<>(defensiveCopy(components));
   }

   static class AndPredicate<T> implements Predicate<T>, Serializable {
      private final List<? extends Predicate<? super T>> components;

      private AndPredicate(List<? extends Predicate<? super T>> components)
      {
         this.components = components;
      }

      @Override
      public boolean test(T t)
      {
         for (Predicate<? super T> predicate : components) {
            if (!predicate.test(t)) {
               return false;
            }
         }
         return true;
      }

      @Override
      public int hashCode()
      {
         // 0x12472c2c is a random number to help avoid collisions with OrPredicate
         return components.hashCode() + 0x12472c2c;
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof AndPredicate) {
            AndPredicate<?> that = (AndPredicate<?>) obj;
            return components.equals(that.components);
         }
         return false;
      }

      @Override
      public String toString()
      {
         StringBuilder buf = new StringBuilder("And(");
         for(int i = 0; i < components.size(); i++) {
            Predicate<? super T> comp = components.get(i);
            if(comp != null) {
               if(i > 0) buf.append(", ");
               buf.append(comp);
            }
         }
         return buf.append(")").toString();
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns a predicate that evaluates to {@code true} if any one of its components
    * evaluates to {@code true}. The components are evaluated in order, and evaluation
    * will be "short-circuited" as soon as a true predicate is found. It defensively
    * copies the array passed in, so future changes to it won't alter the behavior of
    * this predicate. If {@code components} is empty, the returned predicate will always
    * evaluate to {@code false}.
    *
    * @throws NullPointerException if components is {@code null} or any of its items
    *    are {@code null}
    */
   @SafeVarargs
   public static <T> Predicate<T> or(Predicate<? super T> ... components)
   {
      return new OrPredicate<>(defensiveCopy(components));
   }

   static class OrPredicate<T> implements Predicate<T>, Serializable {
      private final List<? extends Predicate<? super T>> components;

      private OrPredicate(List<? extends Predicate<? super T>> components)
      {
         this.components = components;
      }

      @Override
      public boolean test(T t)
      {
         for (Predicate<? super T> predicate : components) {
            if (predicate.test(t)) {
               return true;
            }
         }
         return false;
      }

      @Override
      public int hashCode()
      {
         // 0x053c91cf is a random number to help avoid collisions with AndPredicate
         return components.hashCode() + 0x053c91cf;
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof OrPredicate) {
            OrPredicate<?> that = (OrPredicate<?>) obj;
            return components.equals(that.components);
         }
         return false;
      }

      @Override
      public String toString()
      {
         StringBuilder buf = new StringBuilder("Or(");
         for(int i = 0; i < components.size(); i++) {
            Predicate<? super T> comp = components.get(i);
            if(comp != null) {
               if(i > 0) buf.append(", ");
               buf.append(comp);
            }
         }
         return buf.append(")").toString();
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns a predicate that evaluates to {@code true} if the object being tested
    * {@code equals()} the given target or both are {@code null}.
    */
   public static <T> Predicate<T> equalTo(T target)
   {
      return (target == null) ? Predicates.<T>isNull() : new IsEqualToPredicate<>(target);
   }

   static class IsEqualToPredicate<T> implements Predicate<T>, Serializable {
      private final T target;

      private IsEqualToPredicate(T target)
      {
         this.target = target;
      }

      @Override
      public boolean test(T t)
      {
         return target.equals(t);
      }

      @Override
      public int hashCode()
      {
         return target.hashCode();
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof IsEqualToPredicate) {
            IsEqualToPredicate<?> that = (IsEqualToPredicate<?>) obj;
            return target.equals(that.target);
         }
         return false;
      }

      @Override
      public String toString()
      {
         return "IsEqualTo(" + target + ")";
      }

      private static final long serialVersionUID = 0;
   }


   /**
    * Returns a predicate that evaluates to {@code true} if the object being tested
    * equals the given target using reference-equality in place of object-equality
    * or both are {@code null}.
    */
   public static <T> Predicate<T> identity(T target)
   {
      return (target == null) ? Predicates.<T>isNull() : new IdentityPredicate<>(target);
   }

   static class IdentityPredicate<T> implements Predicate<T>, Serializable {
      private final T target;

      private IdentityPredicate(T target)
      {
         this.target = target;
      }

      @Override
      public boolean test(T t)
      {
         return target == t;
      }

      @Override
      public int hashCode()
      {
         return System.identityHashCode(target);
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof IdentityPredicate) {
            IdentityPredicate<?> that = (IdentityPredicate<?>) obj;
            return target == that.target;
         }
         return false;
      }

      @Override
      public String toString()
      {
         return "Identity(" + target + ")";
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns a predicate that evaluates to {@code true} if the object being tested is
    * an instance of the given class. If the object being tested is {@code null} this
    * predicate evaluates to {@code false}.
    * <p>
    * <b>Warning:</b> contrary to the typical assumptions about predicates (as documented
    * at {@link Predicate#test}), the returned predicate may not be <i>consistent with
    * equals</i>. For example, {@code instanceOf(ArrayList.class)} will yield different
    * results for the two equal instances {@code Lists.newArrayList(1)} and
    * {@code Arrays.asList(1)}.
    *
    * @throws NullPointerException if clazz is {@code null}
    */
   public static Predicate<Object> instanceOf(Class<?> clazz)
   {
      return new InstanceOfPredicate(clazz);
   }

   static class InstanceOfPredicate implements Predicate<Object>, Serializable {
      private final Class<?> clazz;

      private InstanceOfPredicate(Class<?> clazz)
      {
         this.clazz = Objects.notNull(clazz);
      }

      @Override
      public boolean test(Object o)
      {
         return clazz.isInstance(o);
      }

      @Override public int hashCode()
      {
         return clazz.hashCode();
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof InstanceOfPredicate) {
            InstanceOfPredicate that = (InstanceOfPredicate) obj;
            return clazz == that.clazz;
         }
         return false;
      }

      @Override
      public String toString()
      {
         return "IsInstanceOf(" + clazz.getName() + ")";
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns a predicate that evaluates to {@code true} if the class being tested is
    * assignable from the given class.  The returned predicate does not allow {@code null}
    * inputs.
    *
    * @throws NullPointerException if clazz is {@code null}
    */
   public static Predicate<Class<?>> assignableFrom(Class<?> clazz)
   {
      return new AssignableFromPredicate(clazz);
   }

   static class AssignableFromPredicate implements Predicate<Class<?>>, Serializable {
      private final Class<?> clazz;

      private AssignableFromPredicate(Class<?> clazz)
      {
         this.clazz = Objects.notNull(clazz);
      }

      @Override
      public boolean test(Class<?> input)
      {
         return clazz.isAssignableFrom(input);
      }

      @Override
      public int hashCode()
      {
         return clazz.hashCode();
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof AssignableFromPredicate) {
            AssignableFromPredicate that = (AssignableFromPredicate) obj;
            return clazz == that.clazz;
         }
         return false;
      }

      @Override
      public String toString()
      {
         return "IsAssignableFrom(" + clazz.getName() + ")";
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns a predicate that evaluates to {@code true} if the object reference being tested
    * is a member of the given collection. It does a defensive copy of the collection passed
    * in, so future changes to the collection will not alter the behavior of the predicate.
    *
    * @param target the collection that may contain the predicate input
    * @throws NullPointerException if target is {@code null}
    */
   public static <T> Predicate<T> in(Collection<? extends T> target)
   {
      Object[] data = Objects.notNull(target).toArray();
      if(Classes.isAssignableFrom(Objects.getComponentType(data), Comparable.class)) {
         return new InPredicate<>(Sets.newTreeSet(data));
      }
      return new InPredicate<>(Sets.newLinkedHashSet(data));
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the object reference being tested
    * is a member of the given array. It does a defensive copy of the array passed in, so future
    * changes to the array will not alter the behavior of the predicate.
    *
    * @param target the array that may contain the predicate input
    * @throws NullPointerException if target is {@code null}
    */
   @SafeVarargs
   public static <T> Predicate<T> in(T ... target)
   {
      if(target == null) throw new NullPointerException();
      if(Classes.isAssignableFrom(Objects.getComponentType(target), Comparable.class)) {
         return new InPredicate<>(Sets.newTreeSet(target));
      }
      return new InPredicate<>(Sets.newLinkedHashSet(target));
   }

   private static class InPredicate<T> implements Predicate<T>, Serializable {
      private final Set<?> target;

      private InPredicate(Set<?> target)
      {
         this.target = Objects.notNull(target);
      }

      @Override
      public boolean test(T t)
      {
         try {
            return target.contains(t);
         } catch (NullPointerException e) {
            return false;
         } catch (ClassCastException e) {
            return false;
         }
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof InPredicate) {
            InPredicate<?> that = (InPredicate<?>) obj;
            return target.equals(that.target);
         }
         return false;
      }

      @Override
      public int hashCode()
      {
         return target.hashCode();
      }

      @Override
      public String toString()
      {
         return "In(" + target + ")";
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns the composition of a function and a predicate. For every {@code x}, the generated
    * predicate returns {@code predicate(function(x))}.
    *
    * @return the composition of the provided function and predicate
    * @throws NullPointerException if predicate or function are {@code null}
    */
   public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function)
   {
      return new CompositionPredicate<>(predicate, function);
   }

   private static class CompositionPredicate<A, B> implements Predicate<A>, Serializable {

      final Predicate<B> p;
      final Function<A, ? extends B> f;

      private CompositionPredicate(Predicate<B> p, Function<A, ? extends B> f)
      {
         this.p = Objects.notNull(p);
         this.f = Objects.notNull(f);
      }

      @Override
      public boolean test(A a)
      {
         return p.test(f.apply(a));
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof CompositionPredicate) {
            CompositionPredicate<?, ?> that = (CompositionPredicate<?, ?>) obj;
            return f.equals(that.f) && p.equals(that.p);
         }
         return false;
      }

      @Override
      public int hashCode()
      {
         return f.hashCode() ^ p.hashCode();
      }

      @Override
      public String toString()
      {
         return p.toString() + "(" + f.toString() + ")";
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns a predicate that evaluates to {@code true} if the {@code CharSequence} being
    * tested contains any match for the given regular expression pattern. The test used is
    * equivalent to {@code Pattern.compile(pattern).matcher(arg).find()}
    *
    * @throws java.util.regex.PatternSyntaxException if the pattern is invalid
    * @throws NullPointerException if pattern is {@code null}
    */
   public static Predicate<CharSequence> contains(String pattern)
   {
      return new ContainsPatternPredicate(pattern);
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the {@code CharSequence} being
    * tested contains any match for the given regular expression pattern. The test used is
    * equivalent to {@code pattern.matcher(arg).find()}
    *
    * @throws NullPointerException if pattern is {@code null}
    */
   public static Predicate<CharSequence> contains(Pattern pattern)
   {
      return new ContainsPatternPredicate(pattern);
   }

   private static class ContainsPatternPredicate implements Predicate<CharSequence>, Serializable {
      final Pattern pattern;

      ContainsPatternPredicate(Pattern pattern) {
         this.pattern = Objects.notNull(pattern);
      }

      ContainsPatternPredicate(String patternStr)
      {
         this(Pattern.compile(Objects.notNull(patternStr)));
      }

      @Override
      public boolean test(CharSequence t)
      {
         return pattern.matcher(t).find();
      }

      @Override
      public int hashCode()
      {
         return Objects.hash(pattern.pattern(), pattern.flags());
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof ContainsPatternPredicate) {
            ContainsPatternPredicate that = (ContainsPatternPredicate) obj;
            // Pattern uses Object (identity) equality, so we have to reach
            // inside to compare individual fields.
            return Objects.equal(pattern.pattern(), that.pattern.pattern())
                  && Objects.equal(pattern.flags(), that.pattern.flags());
         }
         return false;
      }

      @Override
      public String toString()
      {
         return "ContainsPattern" + " {" + "pattern" + "=" + pattern + ", " + "flags" + "=" + Integer.toHexString(pattern.flags()) + "}";
      }

      private static final long serialVersionUID = 0;
   }




   /**
    * Returns a predicate that evaluates to {@code true} if the {@code CharSequence} being
    * tested matches the given regular expression pattern. The test used is equivalent to
    * {@code Pattern.compile(pattern).matcher(arg).matches()}
    *
    * @throws java.util.regex.PatternSyntaxException if the pattern is invalid
    * @throws NullPointerException if pattern is {@code null}
    */
   public static Predicate<CharSequence> matches(String pattern)
   {
      return new MatchesPatternPredicate(pattern);
   }

   /**
    * Returns a predicate that evaluates to {@code true} if the {@code CharSequence} being
    * tested matches the given regular expression pattern. The test used is equivalent to
    * {@code pattern.matcher(arg).matches()}
    *
    * @throws NullPointerException if pattern is {@code null}
    */
   public static Predicate<CharSequence> matches(Pattern pattern)
   {
      return new MatchesPatternPredicate(pattern);
   }

   private static class MatchesPatternPredicate implements Predicate<CharSequence>, Serializable {
      final Pattern pattern;

      MatchesPatternPredicate(Pattern pattern) {
         this.pattern = Objects.notNull(pattern);
      }

      MatchesPatternPredicate(String patternStr)
      {
         this(Pattern.compile(Objects.notNull(patternStr)));
      }

      @Override
      public boolean test(CharSequence t)
      {
         return pattern.matcher(t).matches();
      }

      @Override
      public int hashCode()
      {
         return Objects.hash(pattern.pattern(), pattern.flags());
      }

      @Override
      public boolean equals(Object obj)
      {
         if (obj instanceof MatchesPatternPredicate) {
            MatchesPatternPredicate that = (MatchesPatternPredicate) obj;
            // Pattern uses Object (identity) equality, so we have to reach
            // inside to compare individual fields.
            return Objects.equal(pattern.pattern(), that.pattern.pattern())
                    && Objects.equal(pattern.flags(), that.pattern.flags());
         }
         return false;
      }

      @Override
      public String toString()
      {
         return "MatchesPattern" + " {" + "pattern" + "=" + pattern + ", " + "flags" + "=" + Integer.toHexString(pattern.flags()) + "}";
      }

      private static final long serialVersionUID = 0;
   }



   /**
    * Returns a predicate implementation that evaluates to {@code true} the first time
    * an object is passed into the predicate.  It evaluates to {@code false} on all
    * subsequent times it sees the object.
    * <p>
    * An object in this case is identified by it's {@link Object#equals(Object)} method.
    * <p>
    * {@code null} is a permitted object, but like all other objects it will evaluate to
    * {@code false} every time it is seen after the first time.
    */
   public static  <T> Predicate<T> unique()
   {
      return new UniquePredicate<>();
   }

   private static class UniquePredicate<T> implements Predicate<T>, Serializable {
      private final Set<T> seen = new HashSet<>();

      private UniquePredicate() { }

      @Override
      public boolean test(T t)
      {
         return seen.add(t);
      }

      @Override
      public String toString()
      {
         return "Unique";
      }

      private static final long serialVersionUID = 0;
   }






   private static final Class[] lockers = { SyncSafePredicate.class, LockSafePredicate.class };

   /**
    * Returns a predicate which guarantees that the delegate's {@link Predicate#test(Object)}
    * method will be called by only a single thread at a time, making it thread-safe.  This
    * implementation will synchronizes on the delegate before calling it.
    * <p>
    * Using traditional synchronization is suitable where very little contention exists. As
    * lock contention goes up it scales poorly.
    *
    * @throws NullPointerException if the specified {@code delegate} is {@code null}
    * @see #lock(Predicate)
    */
   public static <T> Predicate<T> synchronize(Predicate<T> delegate)
   {
      if(Classes.isInstanceOf(delegate, lockers)) return delegate;
      return new SyncSafePredicate<>(delegate);
   }

   private static class SyncSafePredicate<T> implements Predicate<T>, Serializable {
      final Predicate<T> delegate;

      SyncSafePredicate(Predicate<T> delegate)
      {
         this.delegate = Objects.notNull(delegate);
      }
      public boolean test(T item)
      {
         synchronized (delegate) {
            return delegate.test(item);
         }
      }
   }


   /**
    * Returns a predicate which guarantees that the delegate's {@link Predicate#test(Object)}
    * method will be called by only a single thread at a time, making it thread-safe.  This
    * implementation will acquire a lock {@link java.util.concurrent.locks.ReentrantLock}
    * before calling the delegate.
    * <p>
    * This implementation is very similar to the synchronized variant at low contention levels
    * even though it is less consistent.  However, at higher contention levels it performs
    * slightly better.
    *
    * @throws NullPointerException if the specified {@code delegate} is {@code null}
    * @see #synchronize(Predicate)
    */
   public static <T> Predicate<T> lock(Predicate<T> delegate)
   {
      if(Classes.isInstanceOf(delegate, lockers)) return delegate;
      return new LockSafePredicate<>(delegate);
   }

   private static class LockSafePredicate<T> implements Predicate<T>, Serializable {
      final ReentrantLock lock = new ReentrantLock();
      final Predicate<T> delegate;

      LockSafePredicate(Predicate<T> delegate)
      {
         this.delegate = Objects.notNull(delegate);
      }
      public boolean test(T item)
      {
         try {
            lock.lock();
            return delegate.test(item);
         } finally {
            lock.unlock();
         }
      }
   }








   @SafeVarargs
   private static <T> List<T> defensiveCopy(T ... array)
   {
      ArrayList<T> list = new ArrayList<>();
      for (T element : array) list.add(Objects.notNull(element));
      return list;
   }

   private static <T> List<T> defensiveCopy(Iterable<T> iterable)
   {
      ArrayList<T> list = new ArrayList<>();
      for (T element : iterable) list.add(Objects.notNull(element));
      return list;
   }
}
