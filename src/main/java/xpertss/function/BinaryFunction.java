package xpertss.function;

/**
 * Represents a function that accepts two arguments and produces a result.
 * This is the two-arity specialization of {@link Function}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 *
 * @see Function
 */
public interface BinaryFunction<T, U, R> {

   /**
    * Applies this function to the given arguments.
    *
    * @param first the first function argument
    * @param second the second function argument
    * @return the function result
    */
   R apply(T first, U second);


}
