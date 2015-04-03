/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

public class PairTest {


   @Test
   public void testPairEquals()
   {
      Pair<String,String> mutable = MutablePair.of("Chris", "Singer");
      Pair<String,String> immutable = ImmutablePair.of("Chris", "Singer");
      assertTrue(mutable.equals(immutable));
      assertTrue(immutable.equals(mutable));
   }

   @Test
   public void testPairNotEquals()
   {
      Pair<String,String> mutable = MutablePair.of("Chris", "Singer");
      Pair<String,String> immutable = ImmutablePair.of("Chris", "Sall");
      assertFalse(mutable.equals(immutable));
      assertFalse(immutable.equals(mutable));
   }

   @Test
   public void testPairHashCode()
   {
      Pair<String,String> mutable = MutablePair.of("Chris", "Singer");
      Pair<String,String> immutable = ImmutablePair.of("Chris", "Singer");
      assertEquals(mutable.hashCode(), immutable.hashCode());
   }

   @Test
   public void testPairHashCodeDifferent()
   {
      Pair<String,String> mutable = MutablePair.of("Chris", "Singer");
      Pair<String,String> immutable = ImmutablePair.of("Chris", "Sall");
      assertTrue(mutable.hashCode() != immutable.hashCode());
   }

}
