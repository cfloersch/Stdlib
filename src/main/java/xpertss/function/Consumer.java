package xpertss.function;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result. Unlike most other functional interfaces, Consumer is expected to
 * operate via side-effects.
 */
public interface Consumer<T> {

   /**
    * Performs this operation on the given argument.
    *
    * @param t The input argument
    */
   public void apply(T t);

}
