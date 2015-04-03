/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

/**
 * A triple consisting of three elements referred to as 'left', 'middle', and 'right'.
 * <p/>
 * Implementations may be mutable or immutable. However, there is no restriction on the
 * type of the stored objects that may be stored. If mutable objects are stored in the
 * triple, then the triple itself effectively becomes mutable.
 *
 * @param <L> The type of the left element
 * @param <M> The type of the middle element
 * @param <R> The type of the right element
 */
public interface Triple<L,M,R> {


   /**
    * Gets the left element from this triple.
    *
    * @return the left element, may be {@code null}
    */
   public L getLeft();


   /**
    * Gets the middle element from this triple.
    *
    * @return the middle element, may be {@code null}
    */
   public M getMiddle();


   /**
    * Gets the right element from this triple.
    *
    * @return the right element, may be {@code null}
    */
   public R getRight();



   /**
    * Sets the triple's left element value.
    *
    * @param left The possibly {@code null} new value of the left element
    * @throws UnsupportedOperationException if the implementation does not
    *    support mutation of the left element's value
    */
   public void setLeft(L left);

   /**
    * Sets the triple's middle element value.
    *
    * @param middle The possibly {@code null} new value of the middle element
    * @throws UnsupportedOperationException if the implementation does not
    *    support mutation of the middle element's value
    */
   public void setMiddle(M middle);

   /**
    * Sets the triple's right element value.
    *
    * @param right The possibly {@code null} new value of the right element
    * @throws UnsupportedOperationException if the implementation does not
    *    support mutation of the right element's value
    */
   public void setRight(R right);


   /**
    * Compares this triple to another based entirely on the three elements.
    * <p/>
    * {@link ImmutableTriple}'s may be equated to {@link MutableTriple}'s if
    * their element values are equal.
    *
    * @param o The object to compare this triple against.
    * @return {@code true} if the elements of the two objects are equal, {@code
    *          false} otherwise.
    *
    */
   @Override
   public boolean equals(Object o);


   /**
    * Create a shallow copy of this triple implementation
    */
   public Triple<L,M,R> clone();

}
