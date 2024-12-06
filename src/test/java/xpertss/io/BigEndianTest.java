package xpertss.io;

import org.junit.jupiter.api.Test;
import xpertss.lang.Bytes;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 2/26/2016
 */
public class BigEndianTest {


   @Test
   public void testSignedBoundary()
   {
      byte[] data = Bytes.toArray((byte)0x00, (byte)0x00, (byte)0x00, (byte)0xff);
      assertEquals((short)255, BigEndian.parseShort(data, 2));
      assertEquals((char)255, BigEndian.parseChar(data, 2));
      assertEquals(255, BigEndian.parseInt(data));
   }

   @Test
   public void testToBytesFromInt()
   {
      byte[] target = new byte[] { 0x00, 0x00, 0x00, 0x02 };
      int raw = 2;
      assertTrue(Arrays.equals(target, BigEndian.toBytes(raw)));
      assertEquals(2, raw);
   }

   @Test
   public void testShort()
   {
      short s = 0;
      do {
         byte[] data = BigEndian.toBytes(s);
         assertEquals(s, BigEndian.parseShort(data));
         s++;
      } while(s != 0);
   }


}