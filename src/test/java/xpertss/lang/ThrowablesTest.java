/**
 * Created By: cfloersch
 * Date: 1/18/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.DigestException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

}
