/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

import xpertss.lang.Objects;

import java.io.Serializable;

/**
 * A mutable pair consisting of two {@link Object} elements.
 *
 * @param <L> The type of the left element
 * @param <R> The type of the right element
 */
public class MutablePair<L,R> implements Pair<L,R>, Serializable, Cloneable {

   private L left;
   private R right;

   /**
    * Bean compatible zero argument constructor that defaults the left and right
    * value to {@code null}.
    */
   public MutablePair()
   {
   }

   /**
    * Creates a new pair instance with the specified left and right values.
    *
    * @param left The possibly {@code null} left value
    * @param right The possibly {@code null} right value
    */
   public MutablePair(L left, R right)
   {
      this.left = left;
      this.right = right;
   }



   @Override
   public L getLeft()
   {
      return left;
   }

   @Override
   public R getRight()
   {
      return right;
   }


   /**
    * Sets the pair's left element value.
    *
    * @param left The possibly {@code null} new value of the left element
    */
   @Override
   public void setLeft(L left)
   {
      this.left = left;
   }

   /**
    * Sets the pair's right element value.
    *
    * @param right The possibly {@code null} new value of the right element
    */
   @Override
   public void setRight(R right)
   {
      this.right = right;
   }

   /**
    * Create a mutable pair of elements from the specified objects.
    * <p/>
    * This factory allows the pair to be created using inference to obtain the
    * generic types.
    *
    * @param left The possibly {@code null} left value
    * @param right The possibly {@code null} right value
    * @param <L> The type of the left element
    * @param <R> The type of the right element
    * @return a mutable pair formed from the two parameters.
    */
   public static <L,R> Pair<L,R> of(L left, R right)
   {
      return new MutablePair<>(left, right);
   }


   @Override
   public Pair<L,R> clone()
   {
      return new MutablePair<>(left, right);
   }


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
      StringBuilder buf = new StringBuilder();
      buf.append("MutablePair<").append(left).append(",").append(right).append(">");
      return buf.toString();
   }

}
