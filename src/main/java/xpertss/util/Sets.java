/**
 * Created By: cfloersch
 * Date: 6/8/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import xpertss.lang.Classes;
import xpertss.lang.Objects;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * General utility functions for Sets.
 */
@SuppressWarnings("UnusedDeclaration")
public final class Sets {

   private Sets() { }

   /**
    * Returns an empty set if the specified set was {@code null}.
    */
   public static <T> Set<T> emptyIfNull(Set<T> values)
   {
      return values != null ? values : Collections.<T>emptySet();
   }


   /**
    * Returns an immutable set containing the specified items in the same order they
    * were provided. Because a set can only contain a single item of equal value the
    * returned set will always contain the first instance of the given value in the
    * input array.
    */
   @SafeVarargs
   public static <T> Set<T> of(T ... values)
   {
      Set<T> result = newLinkedHashSet();
      Collections.addAll(result, values);
      return Collections.unmodifiableSet(result);
   }



   /**
    * Returns a set containing elements that result from applying a given function to
    * the elements of the source set.
    * <p>
    * The returned set will be an instance of the supplied set if the supplied set can
    * be duplicated using the {@link Sets#newSet(java.util.Set)} method. Otherwise, the
    * returned set will be a {@link java.util.LinkedHashSet}.
    *
    * @param src The source set to pull elements from
    * @param function The function to apply to each element
    * @return A set containing the results of applying function to each element
    * @throws NullPointerException If either the set or function are {@code null}
    * @see Sets#newSet
    */
   public static <F,T> Set<T> transform(Set<F> src, Function<? super F,T> function)
   {
      Set<T> result = newSet(src);
      for(F item : src) result.add(function.apply(item));
      return result;
   }


   /**
    * Creates and returns a new set containing the elements from the source set which
    * satisfy the predicate.
    * <p>
    * The returned set will be an instance of the supplied set if the supplied set can
    * be duplicated using the {@link Sets#newSet(java.util.Set)} method. Otherwise, the
    * returned set will be a {@link java.util.LinkedHashSet}.
    *
    * @param src The source set to pull elements from
    * @param predicate The predicate to apply to each element
    * @return A set containing the elements that satisfy the predicate
    * @throws NullPointerException If either the set or predicate are {@code null}
    * @see Sets#newSet
    */
   public static <T> Set<T> filter(Set<T> src, Predicate<T> predicate)
   {
      Set<T> result = newSet(src);
      for(T item : src) if(predicate.test(item)) result.add(item);
      return result;
   }





   /**
    * Adds the specified items to the given set if and only if they satisfy the
    * predicate.
    * <p>
    * If the specified predicate is {@code null} then all the items are added to
    * the set.
    *
    * @param src The set of elements to add items to using the predicate
    * @param predicate The predicate applied to the specified items
    * @throws NullPointerException If either the set or the items are {@code null}
    */
   @SafeVarargs
   public static <E> void add(Set<E> src, Predicate<E> predicate, E ... items)
   {
      for(E arg : Objects.notNull(items, "items")) {
         if(predicate == null || predicate.test(arg)) src.add(arg);
      }
   }

   /**
    * Removes from the given set all elements which do not satisfy a predicate.
    *
    * @param src The set of elements to modify using the predicate
    * @param predicate The predicate applied to the set's elements
    * @throws NullPointerException If either the set or predicate are {@code null}
    */
   public static <E> void retain(Set<E> src, Predicate<? super E> predicate)
   {
      for(Iterator<E> it = src.iterator(); it.hasNext(); ) {
         if(!predicate.test(it.next())) it.remove();
      }
   }

   /**
    * Removes from the given set all elements which satisfy a predicate.
    *
    * @param src The set of elements to modify using the predicate
    * @param predicate The predicate applied to the set's elements
    * @throws NullPointerException If either the set or predicate are {@code null}
    */
   public static <E> void remove(Set<E> src, Predicate<? super E> predicate)
   {
      for(Iterator<E> it = src.iterator(); it.hasNext(); ) {
         if(predicate.test(it.next())) it.remove();
      }
   }





   /**
    * Return the first item from the given set or null if the specified set is null
    * or empty.
    *
    * @param set The set to extract the first item from
    * @return Null if the set is null or empty, otherwise the first item in the set
    */
   public static <T> T first(Set<T> set)
   {
      if(set != null && set.size() > 0) {
         Object[] items = set.toArray();
         return Objects.cast(items[0]);
      }
      return null;
   }


   /**
    * Return the last item from the given set or null if the specified set is null
    * or empty.
    *
    * @param set The set to extract the last item from
    * @return Null if the set is null or empty, otherwise the last item in the set
    */
   public static <T> T last(Set<T> set)
   {
      if(set != null && set.size() > 0) {
         Object[] items = set.toArray();
         return Objects.cast(items[items.length - 1]);
      }
      return null;
   }






   // Null safe operations


   /**
    * Null safe method for obtaining the size of a set. This will return zero if
    * the specified set is null or the size of the set as returned by the size()
    * method.
    *
    * @param set The set to evaluate
    * @return 0 for an empty or null set, otherwise the size.
    */
   public static int size(Set set)
   {
      return (set == null) ? 0 : set.size();
   }


   /**
    * Null safe method for determining if a given set is empty. This will return
    * true if a null set is specified. Otherwise, it returns the result of calling
    * isEmpty().
    *
    * @param set The set to evaluate
    * @return True if the set is empty or null, false otherwise.
    */
   public static boolean isEmpty(Set set)
   {
      return (set == null) || set.isEmpty();
   }






   /**
    * This method finds the intersection of the two specified sets. That means it returns
    * a new Set containing all objects if and only if those objects are found in both sets.
    *
    * @param set1 - The first set to compare
    * @param set2 - The second set to compare
    * @return Set - Returns a set of objects that were found in both input sets
    * @throws NullPointerException If either of the source sets are {@code null}
    */
   public static <T> Set<T> intersection(Set<T> set1, Set<T> set2)
   {
      Set<T> result = newHashSet();
      for(T o : set1) if(set2.contains(o)) result.add(o);
      return result;
   }

   /**
    * This method subtracts set2 from set1. That means that if the object is found in set2 it
    * wont be found in the results. This method does not alter the original Sets that where
    * passed in.
    *
    * @param set1 - Base set to subtract from
    * @param set2 - Set of items to subtract from set1
    * @throws NullPointerException If either of the source sets are {@code null}
    */
   public static <T> Set<T> subtract(Set<T> set1, Set<T> set2)
   {
      Set<T> result = newHashSet();
      result.addAll(set1);
      result.removeAll(set2);
      return result;
   }

   /**
    * This method creates and returns a new set that represents the union of set1 and set2.
    * A union is a set of all objects contained in both sets with no duplicates.
    *
    * @param set1 - First set to add
    * @param set2 - Second set to add
    * @return set which represents the union of the two sets
    * @throws NullPointerException If either of the source sets are {@code null}
    */
   public static <T> Set<T> union(Set<T> set1, Set<T> set2)
   {
      Set<T> result = newHashSet();
      result.addAll(set1);
      result.addAll(set2);
      return result;
   }




   /**
    * Create a new empty set of the same type as the supplied set if possible or
    * a {@link java.util.LinkedHashSet} if creating an instance of the given set
    * is impossible.
    * <p>
    * This method uses reflection to create a new instance of the given set type.
    * If the given set type is an instance of {@link SortedSet} and its comparator
    * method returns a non-null result, then this implementation will look for a
    * constructor with a single {@link Comparator} argument. Otherwise, it will
    * look for a zero argument constructor.
    * <p>
    * Set implementations that do not expose constructors with the appropriate
    * argument sets can not be duplicated. As such many of the wrapper and view
    * implementations found throughout the core jdk and some third party
    * implementations will fail to be replicated.
    *
    * @param set The set instance to derive an implementation from
    * @return An empty set of the supplied set's type if possible
    * @throws NullPointerException If the supplied set is {@code null}
    */
   @SuppressWarnings("unchecked")
   public static <T> Set<T> newSet(Set<?> set)
   {
      Comparator comp = (set instanceof SortedSet) ? ((SortedSet)set).comparator() : null;
      Set<T> result;
      if(comp != null) {
         Constructor<?> cons = Classes.getConstructor(set.getClass(), Comparator.class);
         result = (Set<T>) Classes.newInstance(cons, comp);
      } else {
         result = Classes.newInstance(set.getClass());
      }
      return (result != null) ? result : Sets.<T>newLinkedHashSet();
   }




   // Creator methods


   /**
    * Returns a new empty Hash Set.
    */
   public static <E> HashSet<E> newHashSet()
   {
      return new HashSet<>();
   }

   /**
    * Returns a new Hash Set pre-populated with the specified items.
    */
   @SafeVarargs
   public static <E> HashSet<E> newHashSet(E ... items)
   {
      HashSet<E> result = new HashSet<>();
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new Hash Set pre-populated with the specified items.
    */
   public static <E> HashSet<E> newHashSet(Iterable<? extends E> items)
   {
      HashSet<E> result = new HashSet<>();
      Iterables.addAll(result, items);
      return result;
   }




   /**
    * Returns a new empty Copy on Write Array Set.
    */
   public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet()
   {
      return new CopyOnWriteArraySet<>();
   }

   /**
    * Returns a new Copy on Write Array Set pre-populated with the specified items..
    */
   @SafeVarargs
   public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(E ... items)
   {
      CopyOnWriteArraySet<E> result = new CopyOnWriteArraySet<>();
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new Copy on Write Array Set pre-populated with the specified items..
    */
   public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(Iterable<? extends E> items)
   {
      CopyOnWriteArraySet<E> result = new CopyOnWriteArraySet<>();
      Iterables.addAll(result, items);
      return result;
   }




   /**
    * Returns a new empty Concurrent Skip List Set.
    */
   public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet()
   {
      return new ConcurrentSkipListSet<>();
   }

   /**
    * Returns a new empty Concurrent Skip List Set and pre-populated with the
    * specified items.
    */
   @SafeVarargs
   public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(E ... items)
   {
      ConcurrentSkipListSet<E> result = new ConcurrentSkipListSet<>();
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new empty Concurrent Skip List Set and pre-populated with the
    * specified items.
    */
   public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(Iterable<? extends E> items)
   {
      ConcurrentSkipListSet<E> result = new ConcurrentSkipListSet<>();
      Iterables.addAll(result, items);
      return result;
   }




   /**
    * Returns a new empty Concurrent Skip List Set with the given comparator.
    */
   public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(Comparator<? super E> comparator)
   {
      return new ConcurrentSkipListSet<>(comparator);
   }

   /**
    * Returns a new Concurrent Skip List Set with the given comparator and
    * pre-populated with the specified items..
    */
   @SafeVarargs
   public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(Comparator<? super E> comparator, E ... items)
   {
      ConcurrentSkipListSet<E> result = new ConcurrentSkipListSet<>(comparator);
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new Concurrent Skip List Set with the given comparator and
    * pre-populated with the specified items..
    */
   public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(Comparator<? super E> comparator, Iterable<? extends E> items)
   {
      ConcurrentSkipListSet<E> result = new ConcurrentSkipListSet<>(comparator);
      Iterables.addAll(result, items);
      return result;
   }



   /**
    * Returns a new empty Tree Set.
    */
   public static <E> TreeSet<E> newTreeSet()
   {
      return new TreeSet<>();
   }

   /**
    * Returns a new empty Tree Set and pre-populated with the specified items..
    */
   @SafeVarargs
   public static <E> TreeSet<E> newTreeSet(E ... items)
   {
      TreeSet<E> result = new TreeSet<>();
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new empty Tree Set and pre-populated with the specified items..
    */
   public static <E> TreeSet<E> newTreeSet(Iterable<? extends E> items)
   {
      TreeSet<E> result = new TreeSet<>();
      Iterables.addAll(result, items);
      return result;
   }



   /**
    * Returns a new empty Tree Set with the given comparator.
    */
   public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator)
   {
      return new TreeSet<>(comparator);
   }

   /**
    * Returns a new Tree Set with the given comparator and pre-populated with the
    * specified items..
    */
   @SafeVarargs
   public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator, E ... items)
   {
      TreeSet<E> result = new TreeSet<>(comparator);
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new Tree Set with the given comparator and pre-populated with the
    * specified items..
    */
   public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator, Iterable<? extends E> items)
   {
      TreeSet<E> result = new TreeSet<>(comparator);
      Iterables.addAll(result, items);
      return result;
   }



   /**
    * Returns a new empty Linked Hash Set.
    */
   public static <E> LinkedHashSet<E> newLinkedHashSet()
   {
      return new LinkedHashSet<>();
   }

   /**
    * Returns a new Linked Hash Set pre-populated with the specified items.
    */
   @SafeVarargs
   public static <E> LinkedHashSet<E> newLinkedHashSet(E ... items)
   {
      LinkedHashSet<E> result = new LinkedHashSet<>();
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new Linked Hash Set pre-populated with the specified items.
    */
   public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> items)
   {
      LinkedHashSet<E> result = new LinkedHashSet<>();
      Iterables.addAll(result, items);
      return result;
   }



}
