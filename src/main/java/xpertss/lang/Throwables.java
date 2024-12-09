/**
 * Created By: cfloersch
 * Date: 1/18/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

/**
 * Static utility methods pertaining to instances of {@link Throwable}.
 */
public final class Throwables {

   private Throwables() { }

   /**
    * Propagates {@code throwable} as-is if it is an instance of {@link RuntimeException}
    * or {@link Error}, or else as a last resort, wraps it in a {@code RuntimeException}
    * then propagate.
    * <p>
    * Example usage:
    * <pre>
    *   T doSomething() {
    *     try {
    *       return someMethodThatCouldThrowAnything();
    *     } catch (IKnowWhatToDoWithThisException e) {
    *       return handle(e);
    *     } catch (Throwable t) {
    *       Throwables.propagate(t);
    *     }
    *   }
    * </pre>
    *
    * @param throwable the Throwable to propagate
    * @throws RuntimeException If the given throwable must be wrapped to propagate
    */
   public static void propagate(Throwable throwable)
   {
      if(throwable instanceof Error) {
         throw (Error) throwable;
      } else if(throwable instanceof RuntimeException) {
         throw (RuntimeException) throwable;
      } else if(throwable == null) {
         throw new NullPointerException();
      }
      throw new RuntimeException(throwable);
   }


   /**
    * Returns the innermost cause of {@code throwable}. The first throwable in a chain
    * provides context from when the error or exception was initially detected. Example
    * usage:
    * <pre>
    *   assertEquals("Unable to assign a customer id",
    *       Throwables.getRootCause(e).getMessage());
    * </pre>
    */
   public static Throwable getRootCause(Throwable t)
   {
      while(t.getCause() != null) t = t.getCause();
      return t;
   }


   /**
    * Iterates through the causal chain of the given {@link Throwable} and returns the
    * first instance of the requested type. If the requested type is not found this
    * will return {@code null}.
    * <p>
    * This implementation will return the element only if it is an exact match of the
    * request type. It does not return instances of the requested type.
    *
    * @param t The source throwable
    * @param type The exception/error type to return
    * @return The first exception/error of the given type or {@code null}.
    */
   public static <T> T getFirstOfType(Throwable t, Class<T> type)
   {
      while(t != null && t.getClass() !=  type) t = t.getCause();
      return type.cast(t);
   }



   /**
    * Returns an abbreviated version of the stack trace where only the first
    * five stack frames per exception are output without their package name
    * prefixes.
    * <pre>
    *    IOException - sample IO fault
    *        ErrorsTest.testShortStackTrace(line:17)
    *        NativeMethodAccessorImpl.invoke0(Native Method)
    *        NativeMethodAccessorImpl.invoke(line:57)
    *        DelegatingMethodAccessorImpl.invoke(line:43)
    *        Method.invoke(line:601)
    * </pre>
    * This can be useful for those who want to log stack trace information
    * without filling up their disk or in cases where compactness are critical
    * like network logging facilities.
    * <p>
    * In cases where the throwable contains a root cause this will only output
    * the stack frames of the root cause itself, not all of its wrappers.
    */
   public static String toShortString(Throwable t)
   {
      while(t.getCause() != null) t = t.getCause();
      StringBuilder buf = new StringBuilder();
      buf.append(stripPackage(t.getClass().getName()));
      if(!Strings.isEmpty(t.getMessage()))
         buf.append(" - ").append(t.getMessage());
      StackTraceElement[] ste = t.getStackTrace();
      for(int i = 0; i < Math.min(ste.length, 5); i++) {
         buf.append(System.getProperty("line.separator"));
         buf.append("   ").append(stripPackage(ste[i].getClassName()));
         buf.append(".").append(ste[i].getMethodName());
         if(ste[i].isNativeMethod()) {
            buf.append("(Native Method)");
         } else if(ste[0].getLineNumber() >= 0) {
            buf.append("(line:").append(ste[i].getLineNumber()).append(")");
         } else
            buf.append("(Unknown Source)");
      }
      return buf.toString();
   }

   /**
    * Converts the given {@link Throwable} into a string containing the full stack
    * trace as if {@link Throwable#printStackTrace()} had been called.
    */
   public static String toString(Throwable t)
   {
      StringWriter sw = new StringWriter();
      t.printStackTrace(new PrintWriter(sw, true));
      return sw.toString();
   }


   /**
    * Converts the given Throwable into a {@link Stream} of Throwables representing
    * the causation tree of the given error.
    * <p/>
    * This can make operating on the causation tree more fluent within the modern
    * confines of Java functional programming.
    *
    * @param t The throwable to stream
    */
   public static Stream<Throwable> causes(Throwable t)
   {
      if (t == null) return Stream.empty();
      return stream(new Spliterators.AbstractSpliterator<Throwable>(Long.MAX_VALUE, Spliterator.ORDERED) {
         private Throwable error = t;
         @Override
         public boolean tryAdvance(Consumer<? super Throwable> action)
         {
            if(error == null) return false;
            action.accept(error);
            error = error.getCause();
            return true;
         }
      }, false);
   }

   /**
    * Converts the specified Throwable's stack trace elements into a {@link Stream}
    * allowing the caller to iterate the stacks using Java's functional programing
    * model.
    *
    * @param t The source Throwable to generate stacks from
    */
   public static Stream<StackTraceElement> stacks(Throwable t)
   {
      return Arrays.stream(t.getStackTrace());
   }



   /**
    * Returns {@code true} if the given throwable is not {@code null} and represents
    * a checked exception type.
    *
    * @param t The throwable to evaluate
    * @return {@code true} if the throwable represents a checked exception type
    */
   public static boolean isChecked(Throwable t)
   {
      return (t != null) && isChecked(t.getClass());
   }

   /**
    * Returns {@code true} if the given exception type is not {@code null} and represents
    * a checked exception type.
    *
    * @param exceptionType The class type to evaluate
    * @return {@code true} if the throwable represents a checked exception type
    */
   public static boolean isChecked(Class<?> exceptionType)
   {
      return (exceptionType != null) &&
         Throwable.class.isAssignableFrom(exceptionType) &&
         !(Error.class.isAssignableFrom(exceptionType) ||
            RuntimeException.class.isAssignableFrom(exceptionType));
   }



   private static String stripPackage(String name)
   {
      return name.substring(name.lastIndexOf('.') + 1);
   }



}
