package xpertss.function;

/**
 * Represents an operation on a single operand that produces a result of the
 * same type as its operand.  This is a specialization of {@code Function} for
 * the case where the operand and result are of the same type.
 *
 * @param <T> the type of the operand and result of the operator
 * @see Function
 */
public interface UnaryOperator<T> extends Function<T, T> {
}
