/**
 * Created By: cfloersch
 * Date: 1/18/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.Test;

import java.io.IOException;
import java.security.DigestException;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;

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

   @Test(expected = RuntimeException.class)
   public void testPropagateExceptionSubClass()
   {
      Throwables.propagate(new DigestException());
   }

   @Test(expected = SecurityException.class)
   public void testPropagateRuntimeExceptionSubClass()
   {
      Throwables.propagate(new SecurityException());
   }

   @Test(expected = InternalError.class)
   public void testPropagateErrorSubClass()
   {
      Throwables.propagate(new InternalError());
   }

   @Test(expected = NullPointerException.class)
   public void testPropagateNullThrowable()
   {
      Throwables.propagate(null);
   }

}
