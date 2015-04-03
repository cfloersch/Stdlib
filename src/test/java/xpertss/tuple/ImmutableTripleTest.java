/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

public class ImmutableTripleTest {

   @Test
   public void testTriple()
   {
      ImmutableTriple<String,String,String> cache = new ImmutableTriple<>();
      assertNull(cache.getLeft());
      assertNull(cache.getMiddle());
      assertNull(cache.getRight());
   }

   @Test(expected = UnsupportedOperationException.class)
   public void testTripleLeftImmutable()
   {
      ImmutableTriple<String,String,String> cache = new ImmutableTriple<>();
      cache.setLeft("Chris");
   }

   @Test(expected = UnsupportedOperationException.class)
   public void testTripleMiddleImmutable()
   {
      ImmutableTriple<String,String,String> cache = new ImmutableTriple<>();
      cache.setMiddle("Flo");
   }

   @Test(expected = UnsupportedOperationException.class)
   public void testTripleRightImmutable()
   {
      ImmutableTriple<String,String,String> cache = new ImmutableTriple<>();
      cache.setRight("Flo");
   }

   @Test
   public void testTripleOf()
   {
      Triple<String,String,String> triple = ImmutableTriple.of("Chris", "E", "Flo");
      assertEquals("Chris", triple.getLeft());
      assertEquals("E", triple.getMiddle());
      assertEquals("Flo", triple.getRight());
   }


   @Test
   public void testTripleClone()
   {
      Triple<String,String,String> triple = ImmutableTriple.of("Chris", "E", "Flo");
      Triple<String,String,String> copy = triple.clone();
      assertNotSame(triple, copy);
      assertSame(triple.getLeft(), copy.getLeft());
      assertSame(triple.getMiddle(), copy.getMiddle());
      assertSame(triple.getRight(), copy.getRight());
   }

   @Test
   public void testTripleEquals()
   {
      Triple<String,String,String> triple = ImmutableTriple.of("Chris", "E", "Flo");
      Triple<String,String,String> copy = triple.clone();
      assertTrue(triple.equals(copy));
      assertTrue(copy.equals(triple));

      Triple<String,String,String> custom = new Triple<String, String, String>() {
         @Override public String getLeft() { return "Chris"; }
         @Override public String getMiddle() { return "E"; }
         @Override public String getRight() { return "Flo"; }
         @Override public void setLeft(String left) { }
         @Override public void setMiddle(String middle) { }
         @Override public void setRight(String right) { }
         @Override public Triple<String, String, String> clone() { return null; }
      };

      assertTrue(triple.equals(custom));
   }


   @Test
   public void testTripleHashcode()
   {
      Triple<String,String,String> triple = ImmutableTriple.of("Chris", "E", "Flo");
      Triple<String,String,String> copy = triple.clone();
      assertEquals(triple.hashCode(), copy.hashCode());
   }

   @Test
   public void testTripleToString()
   {
      Triple<String,String,String> triple = ImmutableTriple.of("Chris", "E", "Flo");
      Triple<String,String,String> copy = triple.clone();
      assertEquals(triple.toString(), copy.toString());
   }


}
