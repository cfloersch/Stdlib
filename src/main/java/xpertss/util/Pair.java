/**
 * Copyright 2013 XpertSoftware
 * Date: 2/11/14
 */
package xpertss.util;

import xpertss.lang.Objects;

import java.io.Serializable;

/**
 * A pair consisting of two elements referred to as 'left' and 'right'.
 * <p>
 * This implementation is immutable and thread-safe.
 *
 * @param <L> The type of the left element
 * @param <R> The type of the right element
 */
public class Pair<L,R>  implements Serializable, Cloneable {


   private final L left;
   private final R right;

   private Pair(L left, R right)
   {
      this.left = left;
      this.right = right;
   }

   /**
    * Gets the left element from this pair.
    * <p>
    * When treated as a key-value pair, this is the key.
    *
    * @return the left element, may be {@code null}
    */
   public L getLeft() { return left; }

   /**
    * Gets the right element from this pair.
    * <p>
    * When treated as a key-value pair, this is the value.
    *
    * @return the right element, may be {@code null}
    */
   public R getRight() { return right; }



   /**
    * Creates and returns a new Pair with the specified value for left and
    * this object's right value.
    *
    * @param left The possibly {@code null} value of the left element
    */
   public Pair<L,R> withLeft(L left)
   {
      return new Pair<>(left, right);
   }

   /**
    * Creates and returns a new Pair with the specified value for right and
    * this object's left value.
    *
    * @param right The possibly {@code null} value of the right element
    */
   public Pair<L,R> withRight(R right)
   {
      return new Pair<>(left, right);
   }





   /**
    * Creates a shallow copy of this pair implementation.
    */
   @Override
   public Pair<L,R> clone()
   {
      return new Pair<>(left, right);
   }


   /**
    * Compares this pair to another based entirely on the two elements.
    *
    * @param o The object to compare this pair against.
    * @return {@code true} if the elements of the two objects are equal, {@code
    *          false} otherwise.
    *
    */
   @Override
   public boolean equals(Object o)
   {
      if(o instanceof Pair) {
         Pair<?,?> other = (Pair<?,?>) o;
         return Objects.equal(left, other.getLeft()) &&
                  Objects.equal(right, other.getRight());
      }
      return false;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(left, right);
   }

   @Override
   public String toString()
   {
      return String.format("Pair<%s,%s>", left, right);
   }



   /**
    * Create a Pair of elements from the specified objects.
    * <p>
    * This factory allows the pair to be created using inference to obtain the
    * generic types.
    *
    * @param left The possibly {@code null} left value
    * @param right The possibly {@code null} right value
    * @param <L> The type of the left element
    * @param <R> The type of the right element
    * @return a pair formed from the two parameters.
    */
   public static <L,R> Pair<L,R> of(L left, R right)
   {
      return new Pair<>(left, right);
   }

}
