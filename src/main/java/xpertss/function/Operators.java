package xpertss.function;

import xpertss.lang.Objects;

import java.util.Comparator;

/**
 * Utility methods useful when operating with {@link BinaryOperator}s
 * and {@link UnaryOperator}s.
 *
 * @see UnaryOperator
 * @see BinaryOperator
 */
public final class Operators {

   private Operators() { }

   /**
    * Returns a {@link BinaryOperator} which returns the lesser of two elements
    * according to the specified {@code Comparator}.
    *
    * @param <T> the type of the input arguments of the comparator
    * @param comparator a {@code Comparator} for comparing the two values
    * @return a {@code BinaryOperator} which returns the lesser of its operands,
    *         according to the supplied {@code Comparator}
    * @throws NullPointerException if the argument is {@code null}
    */
   public static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator)
   {
      final Comparator<? super T> comp = Objects.notNull(comparator);
      return new BinaryOperator<T>() {
         @Override
         public T apply(T left, T right) {
            return (comp.compare(left, right) <= 0) ? left : right;
         }
      };
   }

   /**
    * Returns a {@link BinaryOperator} which returns the greater of two elements
    * according to the specified {@code Comparator}.
    *
    * @param <T> the type of the input arguments of the comparator
    * @param comparator a {@code Comparator} for comparing the two values
    * @return a {@code BinaryOperator} which returns the greater of its operands,
    *         according to the supplied {@code Comparator}
    * @throws NullPointerException if the argument is {@code null}
    */
   public static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator)
   {
      final Comparator<? super T> comp = Objects.notNull(comparator);
      return new BinaryOperator<T>() {
         @Override
         public T apply(T left, T right) {
            return (comp.compare(left, right) >= 0) ? left : right;
         }
      };
   }


   /**
    * Returns a unary operator that always returns its input argument.
    *
    * @param <T> the type of the input and output of the operator
    * @return a unary operator that always returns its input argument
    */
   public static <T> UnaryOperator<T> identity()
   {
      return Objects.cast(IdentityOperator.INSTANCE);
   }

   private enum IdentityOperator implements UnaryOperator<Object> {
      INSTANCE;
      @Override public Object apply(Object input)
      {
         return input;
      }
   }



   // TODO Maybe create locked and synchronized methods.
}
