/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

import xpertss.lang.Objects;

import java.io.Serializable;

/**
 * An immutable triple consisting of three {@link Object} elements.
 *
 * @param <L> The type of the left element
 * @param <M> The type of the middle element
 * @param <R> The type of the right element
 */
public class ImmutableTriple<L,M,R> implements Triple<L,M,R>, Serializable, Cloneable {

   private final L left;
   private final M middle;
   private final R right;

   /**
    * Bean compatible zero argument constructor that defaults the left, middle,
    * and right value to {@code null}.
    */
   public ImmutableTriple()
   {
      this(null, null, null);
   }

   /**
    * Creates a new triple instance with the specified left, middle, and right
    * values.
    *
    * @param left The possibly {@code null} left value
    * @param middle The possibly {@code null} middle value
    * @param right The possibly {@code null} right value
    */
   public ImmutableTriple(L left, M middle, R right)
   {
      this.left = left;
      this.middle = middle;
      this.right = right;
   }



   @Override
   public L getLeft()
   {
      return left;
   }

   @Override
   public M getMiddle()
   {
      return middle;
   }

   @Override
   public R getRight()
   {
      return right;
   }

   /**
    * Always throws {@link UnsupportedOperationException} as the elements of this
    * triple are immutable.
    */
   @Override
   public void setLeft(L left)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * Always throws {@link UnsupportedOperationException} as the elements of this
    * triple are immutable.
    */
   @Override
   public void setMiddle(M middle)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * Always throws {@link UnsupportedOperationException} as the elements of this
    * triple are immutable.
    */
   @Override
   public void setRight(R right)
   {
      throw new UnsupportedOperationException();
   }





   /**
    * Create a mutable triple from the specified objects.
    * <p/>
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
      return new ImmutableTriple<>(left, middle, right);
   }


   @Override
   public Triple<L,M,R> clone()
   {
      return new ImmutableTriple<>(left, middle, right);
   }


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

   @Override
   public int hashCode()
   {
      return Objects.hash(left, middle, right);
   }

   @Override
   public String toString()
   {
      StringBuilder buf = new StringBuilder();
      buf.append("ImmutableTriple<").append(left).append(",").append(middle).append(",").append(right).append(">");
      return buf.toString();
   }


}

