/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

import xpertss.lang.Objects;

import java.io.Serializable;

/**
 * An immutable pair consisting of two {@link Object} elements.
 * <p/>
 * Although the implementation is immutable, there is no restriction on the
 * objects that may be stored. If mutable objects are stored in the pair,
 * then the pair itself effectively becomes mutable. The class is also not
 * final, so a subclass could add undesirable behaviour.
 *
 * @param <L> The type of the left element
 * @param <R> The type of the right element
 */
public class ImmutablePair<L,R> implements Pair<L,R>, Serializable, Cloneable {

   private final L left;
   private final R right;

   /**
    * Bean compatible zero argument constructor that defaults the left and right
    * value to {@code null}.
    */
   public ImmutablePair()
   {
      this(null, null);
   }

   /**
    * Creates a new pair instance with the specified left and right values.
    *
    * @param left The possibly {@code null} left value
    * @param right The possibly {@code null} right value
    */
   public ImmutablePair(L left, R right)
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
    * Always throws {@link UnsupportedOperationException} as the elements of this
    * pair are immutable.
    */
   @Override
   public void setLeft(L left)
   {
      throw new UnsupportedOperationException();
   }

   /**
    * Always throws {@link UnsupportedOperationException} as the elements of this
    * pair are immutable.
    */
   @Override
   public void setRight(R right)
   {
      throw new UnsupportedOperationException();
   }



   /**
    * Create an immutable pair of elements from the specified objects.
    * <p/>
    * This factory allows the pair to be created using inference to obtain the
    * generic types.
    *
    * @param left The possibly {@code null} left value
    * @param right The possibly {@code null} right value
    * @param <L> The type of the left element
    * @param <R> The type of the right element
    * @return a immutable pair formed from the two parameters.
    */
   public static <L,R> Pair<L,R> of(L left, R right)
   {
      return new ImmutablePair<>(left, right);
   }


   @Override
   public Pair<L,R> clone()
   {
      return new ImmutablePair<>(left, right);
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
      buf.append("ImmutablePair<").append(left).append(",").append(right).append(">");
      return buf.toString();
   }

}
