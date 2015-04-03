/**
 * Created By: cfloersch
 * Date: 1/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.io;

import org.junit.Test;
import xpertss.util.Lists;

import java.io.ByteArrayInputStream;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

public class SerializationTest {

   @Test
   public void testClone()
   {
      List<Integer> testObj = Lists.of(1,2,3,4);
      List<Integer> cloneObj = Serialization.clone(testObj);
      assertTrue(testObj.equals(cloneObj));
      assertFalse(testObj == cloneObj);

      for(int i = 0; i < testObj.size(); i++) {
         Integer orig = testObj.get(i);
         Integer clone = cloneObj.get(i);
         assertTrue(orig.equals(clone));
         assertFalse(orig == clone);
      }
   }

   @Test
   public void testSerialize()
   {
      String hello = "Hello";
      byte[] data = Serialization.serialize(hello);
      String copy = Serialization.deserialize(new ByteArrayInputStream(data));
      assertNotSame(hello, copy);
      assertEquals(hello, copy);
   }

}
