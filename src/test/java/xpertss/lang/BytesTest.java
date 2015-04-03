/**
 * Created By: cfloersch
 * Date: 6/10/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class BytesTest {

   @Test
   public void testToArray()
   {
      byte[] data = Bytes.toArray((byte)0x00, (byte)0x01, (byte)0x02);
      assertEquals((byte)0, data[0]);
   }

}
