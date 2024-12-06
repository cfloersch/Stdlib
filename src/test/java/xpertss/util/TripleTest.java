/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripleTest {


   @Test
   public void testValues()
   {
      Triple<String,String,String> nullLeft = Triple.of(null, "H", "Singer");
      assertNull(nullLeft.getLeft());
      assertEquals("H", nullLeft.getMiddle());
      assertEquals("Singer", nullLeft.getRight());

      Triple<String,String,String> nullMiddle = Triple.of("Chris", null, "Singer");
      assertEquals("Chris", nullMiddle.getLeft());
      assertNull(nullMiddle.getMiddle());
      assertEquals("Singer", nullMiddle.getRight());

      Triple<String,String,String> nullRight = Triple.of("Chris", "H", null);
      assertEquals("Chris", nullRight.getLeft());
      assertEquals("H", nullRight.getMiddle());
      assertNull(nullRight.getRight());
   }

   @Test
   public void testWith()
   {
      Triple<String,String,String> one = Triple.of("Chris", "H", "Singer");
      Triple<String,String,String> two = one.withLeft("Angela").withMiddle("B");
      assertEquals(one.getRight(), two.getRight());
      assertEquals("Angela", two.getLeft());
      assertEquals("Chris", one.getLeft());
      assertEquals("B", two.getMiddle());
      assertEquals("H", one.getMiddle());
   }


   @Test
   public void testTripleEquals()
   {
      Triple<String,String,String> one = Triple.of("Chris", "H", "Singer");
      Triple<String,String,String> two = Triple.of("Chris", "H", "Singer");
      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
      Triple<String,String,String> three = two.withRight("Saul");
      assertFalse(two.equals(three));

   }

   @Test
   public void testTripleHashCode()
   {
      Triple<String,String,String> one = Triple.of("Chris", "H", "Singer");
      Triple<String,String,String> two = Triple.of("Chris", "H", "Singer");
      assertTrue(one.hashCode() == two.hashCode());
      Triple<String,String,String> three = two.withRight("Saul");
      assertFalse(two.hashCode() == three.hashCode());
   }

   @Test
   public void testClone()
   {
      Triple<String,String,String> orig = Triple.of("Chris", "H", "Singer");
      Triple<String,String,String> copy = orig.clone();
      assertEquals(orig, copy);
      assertNotSame(orig, copy);
      assertSame(orig.getLeft(), copy.getLeft());
      assertSame(orig.getMiddle(), copy.getMiddle());
      assertSame(orig.getRight(), copy.getRight());
   }

}
