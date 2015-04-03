package xpertss.lang;

import xpertss.util.Ordering;
import xpertss.function.Supplier;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A set of utility methods for operating on objects.
 */
public final class Objects {

   private Objects() { }

   /**
    * Shallow clone an object if it implements cloneable or is an array.
    * <p>
    * {@code null} inputs will return {@code null}.
    *
    * @param obj  the object to clone
    * @return the clone if the object implements {@link Cloneable} otherwise {@code null}
    */
   @SuppressWarnings("unchecked")
   public static <T> T clone(T obj)
   {
      if (obj instanceof Cloneable) {
         T result;
         if (obj.getClass().isArray()) {
            Class<?> componentType = obj.getClass().getComponentType();
            if (!componentType.isPrimitive()) {
               result = (T) ((Object[]) obj).clone();
            } else {
               int length = Array.getLength(obj);
               result = (T) Array.newInstance(componentType, length);
               //noinspection SuspiciousSystemArraycopy
               System.arraycopy(obj, 0, result, 0, length);
            }
         } else {
            try {
               final Method clone = obj.getClass().getMethod("clone");
               result = (T) clone.invoke(obj);
            } catch (final Exception e) {
               return null;
            }
         }
         return result;
      }

      return null;
   }



   /**
    * Returns {@code true} if the specified object exists within the specified
    * set, {@code false} otherwise.
    *
    * @param obj The object to find in the set
    * @param set The set in which the object should exist
    * @param <T> The type of the object and the set
    * @return <tt>true</tt> if found in the set, <tt>false</tt> otherwise
    */
   @SafeVarargs
   public static <T> boolean isOneOf(T obj, T ... set)
   {
      if(set != null) {
         for(T item : set) {
            if(obj == null) {
               if(item == null) return true;
            } else if(obj.equals(item)) return true;
         }
      }
      return false;
   }






   /**
    * Returns the specified object if it is not {@code null}, otherwise, it returns the
    * specified default.
    *
    * @param obj Object to test for {@code null}
    * @param def Object to return if obj is {@code null}
    * @return {@code obj} unless {@code null} then {@code def}
    */
   public static <T> T ifNull(T obj, T def)
   {
      return (obj == null) ? def : obj;
   }


   /**
    * Returns the specified object if it is not {@code null}, otherwise, it returns the
    * result of calling get on the given {@link Supplier}. The supplier's {@code get}
    * method will only be called if the specified object is {@code null}.
    *
    * @param obj The Object to test for {@code null}
    * @param supplier Supplier to supply a default value
    * @return {@code obj} unless {@code null} then {@link Supplier#get()}
    * @throws NullPointerException if {@code obj} is {@code null} and {@code supplier}
    *       is {@code null}
    */
   public static <T> T ifNull(T obj, Supplier<T> supplier)
   {
      return (obj != null) ? obj : supplier.get();
   }




   /**
    * Returns the first element in the supplied argument set that is not {@code null}
    * if a non-null entity exists. Otherwise, it returns {@code null}.
    *
    * @param args The input items to iterate
    * @return The first non-null entity in the argument list or {@code null} if no
    *          non-null item exist.
    */
   @SafeVarargs
   public static <T> T firstNonNull(T ... args)
   {
      if(args != null) {
         for(T arg : args) {
            if(arg != null) return arg;
         }
      }
      return null;
   }






   /**
    * Returns {@code true} if all the arguments are equal to each other and {@code false}
    * otherwise. Consequently, if the first arguments is {@code null}, {@code true} is
    * returned if all other objects are also {@code null}. Otherwise, equality is
    * determined by using the {@link Object#equals equals} method of the first argument
    * against all other elements in the input set.
    * <p/>
    * This method will return {@code false} if only a single object is specified.
    *
    * @param items Objects to check for equality
    * @return {@code true} if the arguments are equal to each other and {@code false}
    *    otherwise
    * @throws NullPointerException If the {@code items} is {@code null}
    * @see Object#equals(Object)
    */
   public static boolean equal(Object ... items)
   {
      if(Objects.notNull(items).length < 1) return false;
      Object first = items[0];
      for(int i = 1; i < items.length; i++) {
         if(first == null) {
            if(items[i] != null) return false;
         } else if(!first.equals(items[i])) {
            return false;
         }
      }
      return true;
   }



   /**
    * Returns the hash code of a non-{@code null} argument and 0 for a {@code null}
    * argument.
    *
    * @param o an object
    * @return the hash code of a non-{@code null} argument and 0 for a {@code null}
    *          argument
    * @see Object#hashCode
    */
   public static int hashCode(Object o)
   {
      return o != null ? o.hashCode() : 0;
   }

   /**
    * Generates a hash code for a sequence of input values. The hash code is generated
    * as if all the input values were placed into an array, and that array were hashed
    * by calling {@link Arrays#hashCode(Object[])}.
    * <p/>
    * This method is useful for implementing {@link Object#hashCode()} on objects
    * containing multiple fields. For example, if an object that has three fields,
    * {@code x}, {@code y}, and {@code z}, one could write:
    *
    * <blockquote><pre>
    * &#064;Override public int hashCode() {
    *     return Objects.hash(x, y, z);
    * }
    * </pre></blockquote>
    *
    * <b>Warning: When a single object reference is supplied, the returned value does not
    * equal the hash code of that object reference.</b> This value can be computed by
    * calling {@link #hashCode(Object)}.
    *
    * @param values the values to be hashed
    * @return a hash value of the sequence of input values
    * @see Arrays#hashCode(Object[])
    */
   public static int hash(Object... values)
   {
      return Arrays.deepHashCode(values);
   }



   /**
    * Returns the result of calling {@code toString} for a non-{@code null} argument and
    * {@code null} for a {@code null} argument.
    *
    * @param o an object
    * @return the result of calling {@code toString} for a non-{@code null} argument and
    *          {@code null} for a {@code null} argument
    */
   public static String toString(Object o)
   {
      return (o == null) ? null : o.toString();
   }

   /**
    * Returns the result of calling {@code toString} on the first argument if the first
    * argument is not {@code null} and returns the second argument otherwise.
    *
    * @param o an object
    * @param nullDefault string to return if the first argument is {@code null}
    * @return the result of calling {@code toString} on the first argument if it is not
    *          {@code null} and the second argument otherwise.
    */
   public static String toString(Object o, String nullDefault)
   {
      return (o != null) ? o.toString() : nullDefault;
   }








   /**
    * Returns 0 if the arguments are identical and {@code  c.compare(a, b)}
    * otherwise. Consequently, if both arguments are {@code null} 0 is
    * returned.
    * <p>
    * Note that if one of the arguments is {@code null}, a {@link NullPointerException}
    * may or may not be thrown depending on what ordering policy, if any, the {@link
    * Comparator} chooses to have for {@code null} values.
    *
    * @param <T> the type of the objects being compared
    * @param a an object
    * @param b an object to be compared with {@code a}
    * @param c the {@code Comparator} to compare the first two arguments
    * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
    * @throws NullPointerException If the comparator is {@code null}
    * @see Comparable
    * @see Comparator
    */
   public static <T> int compare(T a, T b, Comparator<? super T> c)
   {
      return (a == b) ? 0 : c.compare(a, b);
   }


   /**
    * Performs an unchecked cast of an object to {@code T}. This is intended
    * for situations where a valid cast is guaranteed (by the programmer) to
    * occur but the compiler is not convinced. This is *not* intended to be a
    * workaround for cast which are questionable, as in these cases, suppressing
    * the compiler warning just defers the issue to being a runtime error.
    * <p>
    * This method takes ownership of @SuppressWarnings("unchecked") to prevent
    * littering them around the codebase and the more egregious method level
    * annotation which suppresses all such warnings from happening at all.
    *
    * @param o The object to cast
    * @param <T> the type to which to cast
    * @return the original object, cast to the supplied type
    */
   public static <T> T cast(Object o)
   {
      @SuppressWarnings("unchecked")
      T result = (T) o;
      return result;
   }








   /**
    * Utility method to determine the component type to use for an array..
    * <p/>
    * If the given object is an array type then it will extract the array's
    * component type, otherwise, it will return the object's class type.
    *
    * @throws NullPointerException If the given object is {@code null}
    */
   public static Class<?> getComponentType(Object o)
   {
      Class<?> cls = notNull(o).getClass();
      return (cls.isArray()) ? cls.getComponentType() : cls;
   }


   /**
    * Null safe method that wll return the object's class type or {@code null} if
    * the object is {@code null}.
    *
    * @param o The object to get the class type of
    * @return The class type of the object or {@code null}
    */
   public static Class<?> getClass(Object o)
   {
      return (o == null) ? null : o.getClass();
   }








   // Array helpers

   /**
    * Null safe method to determine if a given object instance is an array.
    *
    * @param o The object to inspect
    * @return {@code True} if the given object is not {@code null} and is an
    *       array
    */
   public static boolean isArray(Object o)
   {
      return (o != null && o.getClass().isArray());
   }


   /**
    * Simple lazy man's utility to convert a single object or a collection of
    * objects into an array.
    */
   @SafeVarargs
   public static <T> T[] toArray(T ... b)
   {
      return b;
   }


   /**
    * Null safe method to clone a given array. If the array is {@code null} then
    * {@code null} will be returned. Otherwise, a clone of the array is returned.
    *
    * @param input The input array to clone
    * @return The cloned array or {@code null} if the array was {@code null}
    */
   public static <T> T[] clone(T[] input)
   {
      return (input == null) ? null : input.clone();
   }


   /**
    * Null safe method to sort an array using the elements natural ordering.
    *
    * @param input The input array to sort
    * @return The sorted array or {@code null} if the array was {@code null}
    */
   public static <T extends Comparable<? super T>> T[] sort(T[] input)
   {
      if(input != null) Arrays.sort(input);
      return input;
   }

   /**
    * Null safe method to sort an array according to the order induced by the
    * specified comparator. If the comparator is {@code null} the element's
    * natural ordering will be used.
    * <p/>
    * This modifies the order of the input array.
    *
    * @param input The input array to sort
    * @param comparator  The comparator used to determine the order of the array.
    * @return The sorted array or {@code null} if the array was {@code null}
    * @throws ClassCastException If the given input items are not {@link Comparable}
    */
   public static <T> T[] sort(T[] input, Comparator<? super T> comparator)
   {
      if(input != null) Arrays.sort(input, comparator);
      return input;
   }



   /**
    * Checks if an array is empty or {@code null}.
    *
    * @param array The array to test
    * @return {@code true} if the array is empty or {@code null}
    */
   public static <T> boolean isEmpty(T[] array)
   {
      return array == null || array.length == 0;
   }

   /**
    * Null safe method to query an array's length. This will return zero if the given
    * array is {@code null}.
    *
    * @param array The array to query
    * @return The length of the array or zero if it is {@code null}
    */
   public static <T> int size(T[] array)
   {
      return (array == null) ? 0 : array.length;
   }





   /**
    * Returns the first element of the array or {@code null} if the array is {@code null}
    * or does not have at least one element.
    *
    * @param array The source array to pull from
    * @return The first element in the array or {@code null} if it doesn't exist.
    */
   public static <T> T first(T[] array)
   {
      return (array != null && array.length > 0) ? array[0] : null;
   }

   /**
    * Returns the last element in the array or {@code null} if the array is {@code null}
    * or does not have at least one element.
    *
    * @param array The source array to pull from
    * @return The last element in the array or {@code null} if it doesn't exist.
    */
   public static <T> T last(T[] array)
   {
      return (array != null && array.length > 0) ? array[array.length - 1] : null;
   }




   /**
    * Returns an empty array of the specified type if the specified data array is
    * {@code null}, otherwise it returns the data array.

    * @param data The data array
    * @param type The array type
    * @param <T> the type of the array's elements
    * @return the data array if not {@code null} or a zero length array of the specified
    *          type
    */
   public static <T> T[] emptyIfNull(T[] data, Class<? extends T> type)
   {
      //noinspection unchecked
      return (data == null) ? (T[]) Array.newInstance(type, 0) : data;
   }




   /**
    * Returns {@code true} if target is present as an element anywhere in array.
    *
    * @return {@code true} if array[i] == target for some value of {@code i}
    */
   public static <T> boolean contains(T[] array, T target)
   {
      if(array != null) {
         for (T value : array) {
            if(equal(value, target)) return true;
         }
      }
      return false;
   }

   /**
    * Returns the index of the first appearance of the target value in the array.
    */
   public static <T> int indexOf(T[] array, T target)
   {
      for (int i = 0; array != null && i < array.length; i++) {
         if(equal(array[i], target)) return i;
      }
      return -1;
   }

   /**
    * Returns the index of the last appearance of the target value in the array.
    */
   public static <T> int lastIndexOf(T[] array, T target)
   {
      if(array != null) {
         for (int i = array.length - 1; i >= 0; i--) {
            if(equal(array[i], target)) return i;
         }
      }
      return -1;
   }



   /**
    * Return a new array which contains the subset of the specified src array
    * identified by the given offset and length.
    *
    * @param src The source array to derive the subset from
    * @param offset The offset into the source array to begin the subset
    * @param length The number of elements from the offset to include
    * @return An array of the specified length containing a subset of elements
    *          from the source array
    * @throws NullPointerException If the src array is {@code null}
    * @throws IndexOutOfBoundsException  if the offset or length are invalid
    */
   public static <T> T[] subset(T[] src, int offset, int length)
   {
      Class<?> componentType = notNull(src, "src").getClass().getComponentType();
      if(length < 0) throw new ArrayIndexOutOfBoundsException("negative array size");
      //noinspection unchecked
      T[] result = (T[]) Array.newInstance(componentType, length);
      System.arraycopy(src, offset, result, 0, length);
      return result;
   }







   /**
    * Creates and returns a new array which concatenates the specified items to the
    * end of the given array elements.
    *
    * @param array The prefix elements
    * @param items The elements to append to the end
    * @return A new array with all the items
    * @throws NullPointerException If array is {@code null}
    */
   @SafeVarargs
   public static <T> T[] append(T[] array, T ... items)
   {
      return insert(size(array), array, items);
   }

   /**
    * Creates and returns a new array which concatenates the specified items to the
    * beginning of the given array elements.
    *
    * @param array The suffix elements
    * @param items The elements to prepend to the beginning
    * @return A new array with all the items
    * @throws NullPointerException If array is {@code null}
    */
   @SafeVarargs
   public static <T> T[] prepend(T[] array, T ... items)
   {
      return insert(0, array, items);
   }


   /**
    * Creates and returns a new array which inserts the specified items into the
    * given array elements at the specified index.
    *
    * @param idx The index position into the array to insert the given items
    * @param array The base array of items to insert into
    * @param items The elements to insert into the array
    * @return A new array with all the items
    * @throws NullPointerException If array is {@code null}
    */
   @SafeVarargs
   public static <T> T[] insert(int idx, T[] array, T ... items)
   {
      Class<?> componentType = getComponentType(notNull(array));
      //noinspection unchecked
      T[] result = (T[]) Array.newInstance(componentType, size(array) + size(items));
      System.arraycopy(array, 0, result, 0, idx);
      if(items != null) System.arraycopy(items, 0, result, idx, size(items));
      System.arraycopy(array, idx, result, idx + size(items), size(array) - idx);
      return result;
   }




   /**
    * Returns an ordering that orders two arrays lexicographically. That is, it
    * orders them, using {@link Comparable#compareTo(Object)}, the first pair of
    * values that follow any common prefix, or when one array is a prefix of the
    * other, treats the shorter array as the lesser.
    * <p/>
    * For example, {@code [] < [1] < [1, 1] < [1, 2] < [2]}.
    * <p/>
    * This ordering does not support {@code null} elements.
    * <p/>
    * The returned ordering is inconsistent with {@link Object#equals(Object)} (since
    * arrays support only identity equality), but it is consistent with
    * {@link java.util.Arrays#equals(Object[], Object[])}.
    *
    * @see <a href="http://en.wikipedia.org/wiki/Lexicographical_order">
    *     Lexicographical order article at Wikipedia</a>
    */
   public static <T extends Comparable<? super T>> Ordering<T[]> ordering()
   {
      return Objects.cast(LexicographicalOrdering.INSTANCE);
   }

   private static class LexicographicalOrdering extends Ordering<Comparable[]> {
      private static final Ordering<Comparable[]> INSTANCE = new LexicographicalOrdering();

      @Override public int compare(Comparable[] left, Comparable[] right)
      {
         int minLength = Math.min(left.length, right.length);
         for (int i = 0; i < minLength; i++) {
            @SuppressWarnings("unchecked") int result = left[i].compareTo(right[i]);
            if (result != 0) return result;
         }
         return Integer.signum(left.length - right.length);
      }
   }

   /**
    * Returns an ordering that orders two arrays according to the rules of the given
    * {@link Comparator}.
    * <p/>
    * It orders two arrays using {@link Comparator#compare(Object,Object)} on each
    * element of the array. If the two arrays share a common prefix then the array
    * with fewer elements is considered to be less than one with more elements.
    *
    * @param comparator The comparator to apply to the array elements
    * @throws NullPointerException If the comparator is {@code null}
    */
   public static <T> Ordering<T[]> ordering(Comparator<T> comparator)
   {
      final Ordering<T> ordering = Ordering.from(comparator);
      return new Ordering<T[]>() {
         @Override public int compare(T[] left, T[] right)
         {
            int minLength = Math.min(left.length, right.length);
            for (int i = 0; i < minLength; i++) {
               int result = ordering.compare(left[i], right[i]);
               if (result != 0) return result;
            }
            return Integer.signum(left.length - right.length);
         }
      };
   }




   /**
    * Checks whether two objects are the same type taking into account multi-dimensional
    * arrays.
    *
    * @param one the first object, must not be {@code null}
    * @param two the second object, must not be {@code null}
    * @return {@code true} if the two objects represent the same type
    * @throws NullPointerException If either argument is {@code null}
    */
   public static boolean isSameType(Object one, Object two)
   {
      String oneName = Objects.notNull(one, "one").getClass().getName();
      String twoName = Objects.notNull(two, "two").getClass().getName();
      return oneName.equals(twoName);
   }










   // Argument checking

   /**
    * Checks that the specified object reference is not {@code null}. This method is
    * designed primarily for doing parameter validation in methods and constructors,
    * as demonstrated below:
    *
    * <blockquote><pre>
    * public Foo(Bar bar) {
    *     this.bar = Objects.notNull(bar);
    * }
    * </pre></blockquote>
    * <p/>
    * This variant does not provide a means to identify the offending argument if more
    * than one possibility exists.
    *
    * @param obj the object reference to check for nullity
    * @param <T> the type of the reference
    * @return the {@code obj} if not {@code null}
    * @throws NullPointerException if {@code obj} is {@code null}
    */
   public static <T> T notNull(T obj)
   {
      if (obj == null) throw new NullPointerException();
      return obj;
   }


   /**
    * Checks that the specified object reference is not {@code null} and throws a
    * {@link NullPointerException} identifying the offending argument if it is.
    * This method is designed primarily for doing parameter validation in methods
    * and constructors with multiple parameters, as demonstrated below:
    *
    * <blockquote><pre>
    * public Foo(Bar bar, Baz baz) {
    *     this.bar = Objects.notNull(bar, "bar");
    *     this.baz = Objects.notNull(baz, "baz");
    * }
    * </pre></blockquote>
    *
    * @param obj     the object reference to check for nullity
    * @param argName the argument name to be used in the event that a {@code
    *                NullPointerException} is thrown
    * @param <T> the type of the reference
    * @return the {@code obj} if not {@code null}
    * @throws NullPointerException if {@code obj} is {@code null}
    */
   public static <T> T notNull(T obj, String argName)
   {
      if (obj == null) throw new NullPointerException(argName);
      return obj;
   }



   /**
    * Argument checking utility that will throw a {@link NullPointerException} if the given
    * array is {@code null} or an {@link IllegalArgumentException} if it has a zero length.
    * It does not actually evaluate each array element to ensure they are not {@code null}.
    * <p/>
    * This variant does not identify the  offending argument.
    *
    * @param array   the array to check for nullity and length
    * @param <T> the type of the reference
    * @return the {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code array} is {@code null}
    * @throws IllegalArgumentException if the {@code array}'s length is zero
    */
   public static <T> T[] notEmpty(T[] array)
   {
      if(array == null) throw new NullPointerException();
      if(array.length < 1) throw new IllegalArgumentException();
      return array;
   }

   /**
    * Argument checking utility that will throw a {@link NullPointerException} if the given
    * array is {@code null} or an {@link IllegalArgumentException} if it has a zero length.
    * It does not actually evaluate each array element to ensure they are not {@code null}.
    * <p/>
    * This variant will include the given argument name in the exception to identify the
    * offending argument.
    *
    * @param array   the array to check for nullity and length
    * @param argName the argument name to be used in the event that an exception is
    *                thrown
    * @param <T> the type of the reference
    * @return the {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code array} is {@code null}
    * @throws IllegalArgumentException if the {@code array}'s length is zero
    */
   public static <T> T[] notEmpty(T[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      if(array.length < 1) throw new IllegalArgumentException(argName);
      return array;
   }


   /**
    * Argument checking utility that will throw a {@link NullPointerException} if
    * the given array is {@code null} or any of it's elements are {@code null}.
    * Empty arrays will be accepted.
    *
    * @param array The array to inspect
    * @param <T> the type of the reference
    * @return the {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code obj} is {@code null}
    */
   public static <T> T[] noneNull(T[] array)
   {
      if(array == null) throw new NullPointerException();
      for (T anArray : array) if (anArray == null) throw new NullPointerException();
      return array;
   }

   /**
    * Argument checking utility that will throw a {@link NullPointerException} if
    * the given array is {@code null} or any of it's elements are {@code null}.
    * Empty arrays will be accepted.
    * <p/>
    * This variant will include the given argument name in the exception to identify
    * the offending argument.
    *
    * @param array The array to inspect
    * @param argName The argument name to be used in the even an exception is thrown
    * @param <T> the type of the reference
    * @return the {@code array} if not {@code null} or zero length
    * @throws NullPointerException if {@code obj} is {@code null}
    */
   public static <T> T[] noneNull(T[] array, String argName)
   {
      if(array == null) throw new NullPointerException(argName);
      for(int i = 0; i < array.length; i++) {
         if(array[i] == null) {
            throw new NullPointerException(argName + "[" + Integer.toString(i) + "]");
         }
      }
      return array;
   }



}
