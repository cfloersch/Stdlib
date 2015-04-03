/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

/**
 * A pair consisting of two elements referred to as 'left' and 'right'.
 * <p/>
 * Implementations may be mutable or immutable. However, there is no restriction
 * on the type of the stored objects that may be stored. If mutable objects are
 * stored in the pair, then the pair itself effectively becomes mutable.
 *
 * @param <L> The type of the left element
 * @param <R> The type of the right element
 */
public interface Pair<L,R> {

   /**
    * Gets the left element from this pair.
    * <p/>
    * When treated as a key-value pair, this is the key.
    *
    * @return the left element, may be {@code null}
    */
   public L getLeft();

   /**
    * Gets the right element from this pair.
    * <p/>
    * When treated as a key-value pair, this is the value.
    *
    * @return the right element, may be {@code null}
    */
   public R getRight();



   /**
    * Sets the pair's left element value.
    *
    * @param left The possibly {@code null} new value of the left element
    * @throws UnsupportedOperationException if the implementation does not
    *    support mutation of the left element's value
    */
   public void setLeft(L left);

   /**
    * Sets the pair's right element value.
    *
    * @param right The possibly {@code null} new value of the right element
    * @throws UnsupportedOperationException if the implementation does not
    *    support mutation of the right element's value
    */
   public void setRight(R right);


   /**
    * Compares this pair to another based entirely on the two elements.
    * <p/>
    * {@link ImmutablePair}'s may be equated to {@link MutablePair}'s if their
    * element values are equal.
    *
    * @param o The object to compare this pair against.
    * @return {@code true} if the elements of the two objects are equal, {@code
    *          false} otherwise.
    *
    */
   @Override
   public boolean equals(Object o);


   /**
    * Creates a shallow copy of this pair implementation.
    */
   public Pair<L,R> clone();

}
