/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

public class PairTest {

   @Test
   public void testValues()
   {
      Pair<String,String> nullLeft = Pair.of(null, "Singer");
      assertNull(nullLeft.getLeft());
      assertEquals("Singer", nullLeft.getRight());

      Pair<String,String> nullRight = Pair.of("Chris", null);
      assertEquals("Chris", nullRight.getLeft());
      assertNull(nullRight.getRight());
   }

   @Test
   public void testWith()
   {
      Pair<String,String> one = Pair.of("Chris", "Singer");
      Pair<String,String> two = one.withLeft("Angela");
      assertEquals(one.getRight(), two.getRight());
      assertEquals("Angela", two.getLeft());
      assertEquals("Chris", one.getLeft());
   }


   @Test
   public void testTripleEquals()
   {
      Pair<String,String> one = Pair.of("Chris", "Singer");
      Pair<String,String> two = Pair.of("Chris", "Singer");
      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
      Pair<String,String> three = two.withRight("Saul");
      assertFalse(two.equals(three));

   }

   @Test
   public void testTripleHashCode()
   {
      Pair<String,String> one = Pair.of("Chris", "Singer");
      Pair<String,String> two = Pair.of("Chris", "Singer");
      assertTrue(one.hashCode() == two.hashCode());
      Pair<String,String> three = two.withRight("Saul");
      assertFalse(two.hashCode() == three.hashCode());
   }

   @Test
   public void testClone()
   {
      Pair<String,String> orig = Pair.of("Chris", "Singer");
      Pair<String,String> copy = orig.clone();
      assertEquals(orig, copy);
      assertNotSame(orig, copy);
      assertSame(orig.getLeft(), copy.getLeft());
      assertSame(orig.getRight(), copy.getRight());
   }

}
