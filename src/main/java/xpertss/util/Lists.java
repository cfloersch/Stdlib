/**
 * Created By: cfloersch
 * Date: 6/7/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import xpertss.lang.Classes;
import xpertss.lang.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static java.lang.String.format;

/**
 * General utility methods for Lists.
 */
@SuppressWarnings("UnusedDeclaration")
public final class Lists {

   private Lists() { }

   /**
    * Returns an empty list if the specified list is {@code null}. Otherwise, it
    * returns the specified list.
    */
   public static <T> List<T> emptyIfNull(List<T> values)
   {
      return values != null ? values : Collections.<T>emptyList();
   }

   /**
    * Returns an immutable list containing the specified items in the same order
    * they were provided.
    */
   @SafeVarargs
   public static <T> List<T> of(T ... items)
   {
      List<T> result = new ArrayList<>(items.length);
      Collections.addAll(result, items);
      return Collections.unmodifiableList(result);
   }


   /**
    * Tests a given list and throws a {@link IllegalArgumentException} if it is {@code null}
    * or empty.
    *
    * @param list the list to test
    * @param name the name of the property used in the exception message
    */
   public static void notEmpty(List list, String name)
   {
      if(list == null || list.isEmpty()) throw new IllegalArgumentException(format("%s cannot be empty"));
   }






   /**
    * Creates and returns a new list containing elements that result from applying a given
    * function to the elements of the source list.
    * <p>
    * The returned list will be an instance of the supplied list if the supplied list can
    * be duplicated using the {@link Lists#newList(java.util.List)} method. Otherwise, the
    * returned list will be an {@link java.util.ArrayList}.
    *
    * @param src The source list to pull elements from
    * @param function The function to apply to each element
    * @return A list containing the results of applying function to each element
    * @throws NullPointerException If either the list or function are {@code null}
    * @see Lists#newList
    */
   public static <F,T> List<T> transform(List<F> src, Function<? super F,T> function)
   {
      List<T> result = newList(src);
      for(F item : src) result.add(function.apply(item));
      return result;
   }


   /**
    * Creates and returns a new list containing the elements from the source list which
    * satisfy the predicate.
    * <p>
    * The returned list will be an instance of the supplied list if the supplied list can
    * be duplicated using the {@link Lists#newList(java.util.List)} method. Otherwise, the
    * returned list will be an {@link java.util.ArrayList}.
    *
    * @param src The source list to pull elements from
    * @param predicate The predicate to apply to each element
    * @return A list containing the elements that satisfy the predicate
    * @throws NullPointerException If either the list or predicate are {@code null}
    * @see Lists#newList
    */
   public static <T> List<T> filter(List<T> src, Predicate<T> predicate)
   {
      List<T> result = newList(src);
      for(T item : src) if(predicate.test(item)) result.add(item);
      return result;
   }






   /**
    * Adds the specified items to the given list if and only if they satisfy the
    * predicate.
    * <p>
    * If the specified predicate is {@code null} then all the items are added to
    * the list.
    *
    * @param list The list of elements to add items to using the predicate
    * @param predicate The predicate applied to the specified items
    * @throws NullPointerException If the list or the items are {@code null}
    */
   @SafeVarargs
   public static <E> void add(List<E> list, Predicate<E> predicate, E ... items)
   {
      for(E arg : Objects.notNull(items, "items")) {
         if(predicate == null || predicate.test(arg)) list.add(arg);
      }
   }

   /**
    * Removes from the given list all elements which do not satisfy a predicate.
    *
    * @param src The set of elements to modify using the predicate
    * @param predicate The predicate applied to the set's elements
    * @throws NullPointerException If either the list or predicate are {@code null}
    */
   public static <E> void retain(List<E> src, Predicate<? super E> predicate)
   {
      for(Iterator<E> it = src.iterator(); it.hasNext(); ) {
         if(!predicate.test(it.next())) it.remove();
      }
   }

   /**
    * Removes from the given list all elements which satisfy a predicate.
    *
    * @param src The list of elements to modify using the predicate
    * @param predicate The predicate applied to the list's elements
    * @throws NullPointerException If either the list or predicate are {@code null}
    */
   public static <E> void remove(List<E> src, Predicate<? super E> predicate)
   {
      for(Iterator<E> it = src.iterator(); it.hasNext(); ) {
         if(predicate.test(it.next())) it.remove();
      }
   }





   /**
    * Replaces each element of the source list with the result of applying the
    * operator to that element.
    *
    * @param src The list of elements to replace using the operator
    * @param operator The operator applied to the list's elements
    * @throws NullPointerException If either the list or operator are {@code null}
    * @throws UnsupportedOperationException if the list does not support the set
    *          operation
    */
   public static <E> void replace(List<E> src, UnaryOperator<E> operator)
   {
      for(int i = 0; i < src.size(); i++) {
         src.set(i, operator.apply(src.get(i)));
      }
   }






   // Null safe operations


   /**
    * Null safe method for obtaining the size of a list. This will return zero if
    * the specified list is {@code null}.
    *
    * @param list The list to evaluate
    * @return 0 for an empty or {@code null} list, otherwise the size.
    */
   public static int size(List list)
   {
      return (list == null) ? 0 : list.size();
   }


   /**
    * Null safe method for determining if a given list is empty. This will return true
    * if a {@code null} list is specified. Otherwise, it returns the result of calling
    * {@code isEmpty()}.
    *
    * @param list The list to evaluate
    * @return {@code True} if the list is empty or {@code null}, {@code false}
    *          otherwise.
    */
   public static boolean isEmpty(List list)
   {
      return (list == null) || list.isEmpty();
   }











   /**
    * This method finds the intersection of the two specified lists. That means it
    * returns a new List containing all objects if and only if those objects are
    * found in both lists.
    * <p>
    * This method will return a new list and will not alter either of the input lists.
    *
    * @param list1 - The first list to compare
    * @param list2 - The second list to compare
    * @return Returns a list of objects that was found in both input Lists
    * @throws NullPointerException if either list1 or list2 are {@code null}
    */
   public static <T> List<T> intersection(List<T> list1, List<T> list2)
   {
      List<T> result = new ArrayList<>();
      for(T o : list2) if(list1.contains(o)) result.add(o);
      return result;
   }

   /**
    * This method subtracts list2 from list1. That means that if the object is found
    * in list2 it wont be found in the results. It returns a new list ensuring the
    * argument lists are not modified.
    * <p>
    * This method will return a new list and will not alter either of the input lists.
    *
    * @param list1 - Base list to subtract from
    * @param list2 - List of items to subtract from list1
    * @return New list with the subtraction values
    * @throws NullPointerException if either list1 or list2 are {@code null}
    */
   public static <T> List<T> subtract(List<T> list1, List<T> list2)
   {
      List<T> result = new ArrayList<>(list1);
      result.removeAll(list2);
      return result;
   }

   /**
    * This method determines the union of the two specified lists. That means that it
    * will return a new List containing all the objects from both lists yet no
    * duplicates.
    * <p>
    * This method will return a new list and will not alter either of the input lists.
    *
    * @param list1 - First list to add
    * @param list2 - Second list to add
    * @return New list with the union values
    * @throws NullPointerException if either list1 or list2 are {@code null}
    */
   public static <T> List<T> union(List<T> list1, List<T> list2)
   {
      List<T> list = new ArrayList<>();
      for(T obj : list1) if(!list.contains(obj)) list.add(obj);
      for(T obj : list2) if(!list.contains(obj)) list.add(obj);
      return list;
   }

   /**
    * This method constructs a new List which is the sum of both lists. This means that
    * the result will produce a List which contains all of the entries from both lists.
    * Some may be duplicated.
    * <p>
    * This method will return a new list and will not alter either of the input lists.
    *
    * @param list1 - First list to add
    * @param list2 - Second list to add
    * @return New list with all the values of both supplied lists
    */
   public static <T> List<T> sum(List<T> list1, List<T> list2)
   {
      List<T> result = new ArrayList<>(list1);
      result.addAll(list2);
      return result;
   }








   /**
    * Create a new empty list of the same type as the supplied list if possible or
    * a {@link java.util.ArrayList} if creating an instance of the given list is
    * impossible.
    * <p>
    * This method uses reflection to create a new instance of the given list type.
    * To be successful the list implementation in use must implement a public zero
    * argument constructor. As such jdk standard wrapper and view implementations
    * and some third party implementations will fail to duplicate.
    *
    * @param list The list instance to derive an implementation from
    * @return An empty list of the supplied list's type if possible
    * @throws NullPointerException If the supplied list is {@code null}
    */
   @SuppressWarnings("unchecked")
   public static <T> List<T> newList(List<?> list)
   {
      List<T> result = Classes.newInstance(list.getClass());
      return (result != null) ? result : Lists.<T>newArrayList();
   }



   /**
    * Returns a new empty array list.
    */
   public static <E> ArrayList<E> newArrayList()
   {
      return new ArrayList<>();
   }

   /**
    * Returns a new array list pre-populated with the specified items in the order
    * they are provided.
    */
   @SafeVarargs
   public static <E> ArrayList<E> newArrayList(E ... items)
   {
      ArrayList<E> result = new ArrayList<>();
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new array list pre-populated with the specified items in the order
    * they are provided.
    */
   public static <E> ArrayList<E> newArrayList(Iterable<? extends E> items)
   {
      ArrayList<E> result = new ArrayList<>();
      Iterables.addAll(result, items);
      return result;
   }



   /**
    * Returns a new empty copy on write array list.
    */
   public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList()
   {
      return new CopyOnWriteArrayList<>();
   }

   /**
    * Returns a new copy on write array list pre-populated with the specified items in
    * the order they are provided.
    */
   @SafeVarargs
   public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(E ... items)
   {
      CopyOnWriteArrayList<E> result = new CopyOnWriteArrayList<>();
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new copy on write array list pre-populated with the specified items in
    * the order they are provided.
    */
   public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> items)
   {
      CopyOnWriteArrayList<E> result = new CopyOnWriteArrayList<>();
      Iterables.addAll(result, items);
      return result;
   }




   /**
    * Returns a new empty linked list.
    */
   public static <E> LinkedList<E> newLinkedList()
   {
      return new LinkedList<>();
   }

   /**
    * Returns a new linked list pre-populated with the specified items in the order
    * they are provided.
    */
   @SafeVarargs
   public static <E> LinkedList<E> newLinkedList(E ... items)
   {
      LinkedList<E> result = new LinkedList<>();
      Collections.addAll(result, items);
      return result;
   }

   /**
    * Returns a new linked list pre-populated with the specified items in the order
    * they are provided.
    */
   public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> items)
   {
      LinkedList<E> result = new LinkedList<>();
      Iterables.addAll(result, items);
      return result;
   }


}


