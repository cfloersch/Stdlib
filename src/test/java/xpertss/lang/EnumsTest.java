/**
 * Created By: cfloersch
 * Date: 1/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.Test;

import java.util.EnumSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class EnumsTest {


   @Test
   public void testOfSimple()
   {
      EnumSet<Thread.State> set = Enums.of(Thread.State.NEW);
      assertTrue(set.contains(Thread.State.NEW));
      assertFalse(set.contains(Thread.State.BLOCKED));
      assertEquals(1, set.size());
   }

   @Test
   public void testOfEmptyArray()
   {
      EnumSet<Thread.State> set = Enums.of(new Thread.State[0]);
      assertFalse(set.contains(Thread.State.NEW));
      assertFalse(set.contains(Thread.State.BLOCKED));
      assertEquals(0, set.size());
   }

   @Test(expected = NullPointerException.class)
   public void testOfNullArray()
   {
      Enums.of((Thread.State)null);
   }

}
