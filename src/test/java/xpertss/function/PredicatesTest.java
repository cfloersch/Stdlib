/**
 * Created By: cfloersch
 * Date: 1/6/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PredicatesTest {

   @Test
   public void testAlwaysTrue()
   {
      assertTrue(Predicates.alwaysTrue().apply(null));
      assertTrue(Predicates.alwaysTrue().apply(""));
      assertTrue(Predicates.alwaysTrue().apply(0));
      assertTrue(Predicates.alwaysTrue().equals(Predicates.alwaysTrue()));
   }

   @Test
   public void testAlwaysFalse()
   {
      assertFalse(Predicates.alwaysFalse().apply(null));
      assertFalse(Predicates.alwaysFalse().apply(""));
      assertFalse(Predicates.alwaysFalse().apply(0));
      assertTrue(Predicates.alwaysFalse().equals(Predicates.alwaysFalse()));
   }

   @Test
   public void testIsNull()
   {
      assertTrue(Predicates.isNull().apply(null));
      assertFalse(Predicates.isNull().apply(""));
      assertFalse(Predicates.isNull().apply(0));
      assertTrue(Predicates.isNull().equals(Predicates.isNull()));
   }

   @Test
   public void testNotNull()
   {
      assertFalse(Predicates.notNull().apply(null));
      assertTrue(Predicates.notNull().apply(""));
      assertTrue(Predicates.notNull().apply(0));
      assertTrue(Predicates.notNull().equals(Predicates.notNull()));
   }

   @Test
   public void testNot()
   {
      assertFalse(Predicates.not(Predicates.alwaysTrue()).apply(null));
      assertTrue(Predicates.not(Predicates.alwaysFalse()).apply(null));
      assertTrue(Predicates.not(Predicates.alwaysTrue()).equals(Predicates.not(Predicates.alwaysTrue())));
   }

   @Test(expected = NullPointerException.class)
   public void testNotWithNull()
   {
      Predicates.not(null);
   }

}
