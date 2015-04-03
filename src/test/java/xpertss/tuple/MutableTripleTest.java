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

public class MutableTripleTest {


   @Test
   public void testTriple()
   {
      MutableTriple<String,String,String> triple = new MutableTriple<>();
      assertNull(triple.getLeft());
      assertNull(triple.getRight());
   }


   @Test
   public void testTripleLeftMutation()
   {
      MutableTriple<String,String,String> triple = new MutableTriple<>();
      triple.setLeft("one");
      assertEquals("one", triple.getLeft());
   }

   @Test
   public void testTripleMiddleMutation()
   {
      MutableTriple<String,String,String> triple = new MutableTriple<>();
      triple.setMiddle("one");
      assertEquals("one", triple.getMiddle());
   }

   @Test
   public void testTripleRightMutation()
   {
      MutableTriple<String,String,String> triple = new MutableTriple<>();
      triple.setRight("Flo");
      assertEquals("Flo", triple.getRight());
   }


   @Test
   public void testTripleOf()
   {
      Triple<String,String,String> triple = MutableTriple.of("Chris", "E", "Flo");
      assertEquals("Chris", triple.getLeft());
      assertEquals("E", triple.getMiddle());
      assertEquals("Flo", triple.getRight());
   }


   @Test
   public void testTripleClone()
   {
      Triple<String,String,String> triple = MutableTriple.of("Chris", "E", "Flo");
      Triple<String,String,String> copy = triple.clone();
      assertNotSame(triple, copy);
      assertSame(triple.getLeft(), copy.getLeft());
      assertSame(triple.getMiddle(), copy.getMiddle());
      assertSame(triple.getRight(), copy.getRight());
   }


   @Test
   public void testTripleEquals()
   {
      Triple<String,String,String> triple = MutableTriple.of("Chris", "E", "Flo");
      Triple<String,String,String> copy = triple.clone();
      assertTrue(triple.equals(copy));
      assertTrue(copy.equals(triple));

      Triple<String,String,String> custom = new Triple<String, String, String>() {
         @Override public String getLeft() { return "Chris"; }
         @Override public String getMiddle() { return "E"; }
         @Override public String getRight() { return "Flo"; }
         @Override public void setLeft(String left) { }
         @Override public void setMiddle(String left) { }
         @Override public void setRight(String right) { }
         @Override public Triple<String,String,String> clone() { return null; }
      };

      assertTrue(triple.equals(custom));
   }

   @Test
   public void testTripleHashCode()
   {
      Triple<String,String,String> triple = MutableTriple.of("Chris", "E", "Flo");
      Triple<String,String,String> copy = triple.clone();
      assertEquals(triple.hashCode(), copy.hashCode());
   }

   @Test
   public void testTripleToString()
   {
      Triple<String,String,String> triple = MutableTriple.of("Chris", "E", "Flo");
      Triple<String,String,String> copy = triple.clone();
      assertEquals(triple.toString(), copy.toString());
   }

}
