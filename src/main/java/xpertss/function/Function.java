package xpertss.function;

/**
 * Determines an output value based on an input value.
 *
 * User: cfloersch
 * Date: 12/8/12
 */
public interface Function<F,T> {

   /**
    * Returns the result of applying this function to input. This method is generally
    * expected, but not absolutely required, to have the following properties:
    * <ul>
    *    <li>Its execution does not cause any observable side effects.</li>
    *    <li>The computation is consistent with equals; that is, Objects.equal(a, b)
    *        implies that Objects.equal(function.apply(a), function.apply(b)).</li>
    * </ul>
    * @param input an input to the function
    * @return the result of teh function
    * @throws NullPointerException If input is null and this function does not accept
    *          null arguments
    */
   public T apply(F input);


}
