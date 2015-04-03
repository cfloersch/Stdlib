/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

import org.junit.Test;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

public class MutablePairTest {


   @Test
   public void testPair()
   {
      MutablePair<String,String> pair = new MutablePair<>();
      assertNull(pair.getLeft());
      assertNull(pair.getRight());
   }


   @Test
   public void testPairLeftMutation()
   {
      MutablePair<String,String> pair = new MutablePair<>();
      pair.setLeft("one");
      assertEquals("one", pair.getLeft());
   }

   @Test
   public void testPairRightMutation()
   {
      MutablePair<String,String> pair = new MutablePair<>();
      pair.setRight("Flo");
      assertEquals("Flo", pair.getRight());
   }


   @Test
   public void testPairOf()
   {
      Pair<String,String> pair = MutablePair.of("Chris", "Flo");
      assertEquals("Chris", pair.getLeft());
      assertEquals("Flo", pair.getRight());
   }


   @Test
   public void testPairClone()
   {
      Pair<String,String> pair = MutablePair.of("Chris", "Flo");
      Pair<String,String> copy = pair.clone();
      assertNotSame(pair, copy);
      assertSame(pair.getLeft(), copy.getLeft());
      assertSame(pair.getRight(), copy.getRight());
   }


   @Test
   public void testPairEquals()
   {
      Pair<String,String> pair = MutablePair.of("Chris", "Flo");
      Pair<String,String> copy = pair.clone();
      assertTrue(pair.equals(copy));
      assertTrue(copy.equals(pair));

      Pair<String,String> custom = new Pair<String, String>() {
         @Override public String getLeft() { return "Chris"; }
         @Override public String getRight() { return "Flo"; }
         @Override public void setLeft(String left) { }
         @Override public void setRight(String right) { }
         @Override public Pair<String, String> clone() { return null; }
      };

      assertTrue(pair.equals(custom));
   }

   @Test
   public void testPairHashCode()
   {
      Pair<String,String> pair = MutablePair.of("Chris", "Flo");
      Pair<String,String> copy = pair.clone();
      assertEquals(pair.hashCode(), copy.hashCode());
   }

   @Test
   public void testPairToString()
   {
      Pair<String,String> pair = MutablePair.of("Chris", "Flo");
      Pair<String,String> copy = pair.clone();
      assertEquals(pair.toString(), copy.toString());
   }

}
