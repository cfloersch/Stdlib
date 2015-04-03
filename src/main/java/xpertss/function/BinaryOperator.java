package xpertss.function;

/**
 * Represents an operation upon two operands of the same type, producing a result of
 * the same type as the operands.  This is a specialization of {@link BinaryFunction}
 * for the case where the operands and the result are all of the same type.

 * @param <T> the type of the operands and result of the operator
 * @see BinaryFunction
 * @see UnaryOperator
 */
public interface BinaryOperator<T> extends BinaryFunction<T,T,T> {
}
