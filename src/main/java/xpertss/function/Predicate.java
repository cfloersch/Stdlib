/**
 * Created By: cfloersch
 * Date: 1/6/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 * <p/>
 * This is a functional interface whose functional method is apply(Object).
 *
 * @param <T> the type of the input to the predicate
 */
public interface Predicate<T> {

   /**
    * Returns the result of applying this predicate to {@code input}. This method is <i>generally
    * expected</i>, but not absolutely required, to have the following properties:
    * <ul>
    * <li>Its execution does not cause any observable side effects.
    * <li>The computation is <i>consistent with equals</i>; that is, {@link Object#equals(Object)}
    * {@code (a, b)} implies that {@code predicate.apply(a) == predicate.apply(b))}.
    * </ul>
    *
    * @throws NullPointerException if input is @{code null} and this predicate does not accept
    *    {@code null} arguments
    */
   boolean apply(T input);

   /**
    * Indicates whether another object is equal to this predicate.
    * <p/>
    * Most implementations will have no reason to override the behavior of {@link Object#equals}.
    * However, an implementation may also choose to return {@code true} whenever {@code object} is
    * a {@link Predicate} that it considers <i>interchangeable</i> with this one. "Interchangeable"
    * <i>typically</i> means that {@code this.apply(t) == that.apply(t)} for all {@code t} of type
    * {@code T}). Note that a {@code false} result from this method does not imply that the
    * predicates are known <i>not</i> to be interchangeable.
    */
   @Override
   boolean equals(Object object);


}
