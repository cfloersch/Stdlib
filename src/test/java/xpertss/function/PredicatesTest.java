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
      assertTrue(Predicates.alwaysTrue().test(null));
      assertTrue(Predicates.alwaysTrue().test(""));
      assertTrue(Predicates.alwaysTrue().test(0));
      assertTrue(Predicates.alwaysTrue().equals(Predicates.alwaysTrue()));
   }

   @Test
   public void testAlwaysFalse()
   {
      assertFalse(Predicates.alwaysFalse().test(null));
      assertFalse(Predicates.alwaysFalse().test(""));
      assertFalse(Predicates.alwaysFalse().test(0));
      assertTrue(Predicates.alwaysFalse().equals(Predicates.alwaysFalse()));
   }

   @Test
   public void testIsNull()
   {
      assertTrue(Predicates.isNull().test(null));
      assertFalse(Predicates.isNull().test(""));
      assertFalse(Predicates.isNull().test(0));
      assertTrue(Predicates.isNull().equals(Predicates.isNull()));
   }

   @Test
   public void testNotNull()
   {
      assertFalse(Predicates.notNull().test(null));
      assertTrue(Predicates.notNull().test(""));
      assertTrue(Predicates.notNull().test(0));
      assertTrue(Predicates.notNull().equals(Predicates.notNull()));
   }

   @Test
   public void testNot()
   {
      assertFalse(Predicates.not(Predicates.alwaysTrue()).test(null));
      assertTrue(Predicates.not(Predicates.alwaysFalse()).test(null));
      assertTrue(Predicates.not(Predicates.alwaysTrue()).equals(Predicates.not(Predicates.alwaysTrue())));
   }

   @Test(expected = NullPointerException.class)
   public void testNotWithNull()
   {
      Predicates.not(null);
   }

}
