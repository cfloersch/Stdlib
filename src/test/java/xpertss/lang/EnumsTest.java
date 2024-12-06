/**
 * Created By: cfloersch
 * Date: 1/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

   @Test
   public void testOfNullArray()
   {
      assertThrows(NullPointerException.class, ()->{
         Enums.of((Thread.State)null);
      });
   }

}
