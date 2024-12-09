/**
 * Created By: cfloersch
 * Date: 1/18/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;


import org.junit.jupiter.api.Test;
import xpertss.function.Consumers;

import java.io.IOException;
import java.security.DigestException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static xpertss.lang.Throwables.causes;


public class ThrowablesTest {

   @Test
   public void testGetFirstOfType()
   {
      IOException io = new IOException();
      DigestException de = new DigestException(io);
      RuntimeException re = new RuntimeException(de);

      assertSame(io, Throwables.getFirstOfType(re, IOException.class));
      assertSame(de, Throwables.getFirstOfType(re, DigestException.class));
      assertNull(Throwables.getFirstOfType(re, Exception.class));
      assertSame(re, Throwables.getFirstOfType(re, RuntimeException.class));
      assertNull(Throwables.getFirstOfType(re, Throwable.class));
      assertNull(Throwables.getFirstOfType(re, IllegalArgumentException.class));
   }

   @Test
   public void testGetRootCause()
   {
      IOException io = new IOException();
      DigestException de = new DigestException(io);
      RuntimeException re = new RuntimeException(de);
      assertSame(io, Throwables.getRootCause(re));
      assertSame(io, Throwables.getRootCause(de));
      assertSame(io, Throwables.getRootCause(io));
   }

   @Test
   public void testPropagateExceptionSubClass()
   {
      assertThrows(RuntimeException.class, ()->{
         Throwables.propagate(new DigestException());
      });
   }

   @Test
   public void testPropagateRuntimeExceptionSubClass()
   {
      assertThrows(SecurityException.class, ()->{
         Throwables.propagate(new SecurityException());
      });
   }

   @Test
   public void testPropagateErrorSubClass()
   {
      assertThrows(InternalError.class, ()->{
         Throwables.propagate(new InternalError());
      });
   }

   @Test
   public void testPropagateNullThrowable()
   {
      assertThrows(NullPointerException.class, ()->{
         Throwables.propagate(null);
      });
   }


   @Test
   public void testIsChecked()
   {
      assertTrue(Throwables.isChecked(new Throwable()));
      assertFalse(Throwables.isChecked(new Error()));
      assertFalse(Throwables.isChecked(new InternalError()));
      assertFalse(Throwables.isChecked(new RuntimeException()));
      assertFalse(Throwables.isChecked(new SecurityException()));
      assertTrue(Throwables.isChecked(new Exception()));
      assertTrue(Throwables.isChecked(new IOException()));
      assertFalse(Throwables.isChecked((Throwable) null));
      assertFalse(Throwables.isChecked((Class<?>) null));
      assertFalse(Throwables.isChecked(String.class));
   }



   @Test
   public void testStreamStack() throws NoSuchElementException
   {
      RuntimeException re = new RuntimeException();
      Stream<StackTraceElement> stacks = Throwables.stacks(re);
      Optional<StackTraceElement> first = stacks.findFirst();
      StackTraceElement se = first.get();
      assertEquals("xpertss.lang.ThrowablesTest", se.getClassName());
   }


   @Test
   public void testStreamCauses()
   {
      Function<Throwable,RuntimeException> outter = throwable -> new RuntimeException("outter", throwable);
      Function<Throwable,RuntimeException> inner = throwable -> new IllegalArgumentException("outter", throwable);
      Wrapper third = new Wrapper(new Wrapper(new Source(), inner), outter);
      try {
         third.execute();
      } catch(Throwable t) {
         Stream<Throwable> stream = causes(t);
         List<Throwable> causes = stream.collect(Collectors.toList());
         assertEquals(3, causes.size());
         assertEquals(RuntimeException.class, causes.get(0).getClass());
         assertEquals(IllegalArgumentException.class, causes.get(1).getClass());
         assertEquals(SecurityException.class, causes.get(2).getClass());
      }
   }


   private static interface Layer {
      public void execute();
   }

   private static class Source implements Layer {

      public void execute() {
         throw new SecurityException("origin");
      }
   }

   private static class Wrapper implements Layer {
      private Function<Throwable,RuntimeException> factory;
      private Layer wrapped;
      private Wrapper(Layer wrapped, Function<Throwable,RuntimeException> factory)
      {
         this.factory = factory;
         this.wrapped = wrapped;
      }
      @Override
      public void execute() {
         try {
            wrapped.execute();
         } catch(Throwable t) {
            throw factory.apply(t);
         }
      }
   }

}
