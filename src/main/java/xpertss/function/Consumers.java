package xpertss.function;

import xpertss.lang.Objects;

/**
 * Utility methods useful when operating with {@link Consumer}s.
 *
 * @see Consumer
 */
public final class Consumers {

   private Consumers() { }

   private static final Consumer<Object> NOOP = new Consumer<Object>() { @Override public void apply(Object o) { } };

   /**
    * Simple no-op consumer.
    */
   public static <T> Consumer<T> noop()
   {
      return Objects.cast(NOOP);
   }

   /**
    * Returns a null-safe consumer that output's its input to the system's standard output
    * stream.
    */
   public static <T> Consumer<T> stdOut()
   {
      return Objects.cast(StdIO.StdOut);
   }

   /**
    * Returns a null-safe consumer that output's its input to the system's standard error
    * stream.
    */
   public static <T> Consumer<T> stdErr()
   {
      return Objects.cast(StdIO.StdErr);
   }

   private enum StdIO implements Consumer<Object> {
      StdOut {
         @Override public void apply(Object s)
         {
            if(s != null) System.out.println(s);
         }
      },
      StdErr {
         @Override public void apply(Object s)
         {
            if(s != null) System.err.println(s);
         }
      }
   }


   /**
    * Returns a composed {@link Consumer} that performs, in sequence, each of the specified
    * operations.  If any of the operations throw an exception, the remaining operations are
    * not executed and the exception is relayed to the caller of the composed operation.
    * <p/>
    * If any of the consumers are {@code null} they will be skipped. An empty consumer set
    * will result in a NO-OP.
    *
    * @param consumers The operation(s) to perform in the order they are to be performed
    * @return a composed {@link Consumer} that performs in sequence the supplied operations
    */
   @SuppressWarnings("unchecked")
   public static <T> Consumer<T> compose(Consumer<? super T> ... consumers)
   {
      final Consumer<? super T>[] composed = Objects.clone(consumers);
      return new Consumer<T>() {
         @Override public void apply(T t)
         {
            for(int i = 0; composed != null && i < composed.length; i++) {
               if(composed[i] != null) composed[i].apply(t);
            }
         }
      };
   }




}
