/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import xpertss.lang.Objects;

import java.io.Serializable;

/**
 * A triple consisting of three elements referred to as 'left', 'middle', and 'right'.
 * <p>
 * This implementation is immutable and thread-safe
 *
 * @param <L> The type of the left element
 * @param <M> The type of the middle element
 * @param <R> The type of the right element
 */
public class Triple<L,M,R> implements Serializable, Cloneable {

   private final L left;
   private final M middle;
   private final R right;

   private Triple(L left, M middle, R right)
   {
      this.left = left;
      this.middle = middle;
      this.right = right;
   }


   /**
    * Gets the left element from this triple.
    *
    * @return the left element, may be {@code null}
    */
   public L getLeft() { return left; }


   /**
    * Gets the middle element from this triple.
    *
    * @return the middle element, may be {@code null}
    */
   public M getMiddle() { return middle; }


   /**
    * Gets the right element from this triple.
    *
    * @return the right element, may be {@code null}
    */
   public R getRight() { return right; }



   /**
    * Creates and returns a new Triple with the specified left value and
    * the middle and right value's from this Triple.
    *
    * @param left The possibly {@code null} value of the left element
    */
   public Triple<L,M,R> withLeft(L left)
   {
      return new Triple<>(left, middle, right);
   }

   /**
    * Creates and returns a new Triple with the specified middle value and
    * the left and right value's from this Triple.
    *
    * @param middle The possibly {@code null} value of the middle element
    */
   public Triple<L,M,R> withMiddle(M middle)
   {
      return new Triple<>(left, middle, right);
   }

   /**
    * Creates and returns a new Triple with the specified right value and
    * the left and middle value's from this Triple.
    *
    * @param right The possibly {@code null} value of the right element
    */
   public Triple<L,M,R> withRight(R right)
   {
      return new Triple<>(left, middle, right);
   }



   /**
    * Compares this triple to another based entirely on the three elements.
    *
    * @param o The object to compare this triple against.
    * @return {@code true} if the elements of the two objects are equal, {@code
    *          false} otherwise.
    *
    */
   @Override
   public boolean equals(Object o)
   {
      if(o instanceof Triple) {
         Triple<?,?,?> other = (Triple<?,?,?>) o;
         return Objects.equal(left, other.getLeft()) &&
                  Objects.equal(middle, other.getMiddle()) &&
                  Objects.equal(right, other.getRight());
      }
      return false;
   }


   /**
    * Create a shallow copy of this triple implementation
    */
   public Triple<L,M,R> clone()
   {
      return new Triple<>(left, middle, right);
   }



   @Override
   public int hashCode()
   {
      return Objects.hash(left, middle, right);
   }

   @Override
   public String toString()
   {
      return String.format("Triple<%s,%s,%s>", left, middle, right);
   }





   /**
    * Create a Triple from the specified objects.
    * <p>
    * This factory allows the triple to be created using inference to obtain the
    * generic types.
    *
    * @param left The possibly {@code null} left value
    * @param middle The possibly {@code null} middle value
    * @param right The possibly {@code null} right value
    * @param <L> The type of the left element
    * @param <M> The type of the middle element
    * @param <R> The type of the right element
    * @return a mutable triple formed from the three parameters.
    */
   public static <L,M,R> Triple<L,M,R> of(L left, M middle, R right)
   {
      return new Triple<>(left, middle, right);
   }

}
