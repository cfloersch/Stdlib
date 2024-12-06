package xpertss.io;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 2/26/2016
 */
public class LittleEndianTest {

   @Test
   public void testBoundary()
   {
      byte[] data = new byte[] { (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
      assertEquals(255, LittleEndian.parseInt(data));
      assertEquals((char)255, LittleEndian.parseChar(data));
      assertEquals((short)255, LittleEndian.parseShort(data));
   }

   @Test
   public void testShort()
   {
      short s = 0;
      do {
         byte[] data = LittleEndian.toBytes(s);
         assertEquals(s, LittleEndian.parseShort(data));
         s++;
      } while(s != 0);
   }


}