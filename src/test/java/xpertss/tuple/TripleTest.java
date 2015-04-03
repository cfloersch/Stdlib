/**
 * Created By: cfloersch
 * Date: 2/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.tuple;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class TripleTest {


   @Test
   public void testTripleEquals()
   {
      Triple<String,String,String> mutable = MutableTriple.of("Chris", "H", "Singer");
      Triple<String,String,String> immutable = ImmutableTriple.of("Chris", "H", "Singer");
      assertTrue(mutable.equals(immutable));
      assertTrue(immutable.equals(mutable));
   }

   @Test
   public void testTripleHashCode()
   {
      Triple<String,String,String> mutable = MutableTriple.of("Chris", "H", "Singer");
      Triple<String,String,String> immutable = ImmutableTriple.of("Chris", "H", "Singer");
      assertEquals(mutable.hashCode(), immutable.hashCode());
   }


}
