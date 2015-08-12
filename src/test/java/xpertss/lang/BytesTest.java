/**
 * Created By: cfloersch
 * Date: 6/10/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BytesTest {

   @Test
   public void testFromHexLower()
   {
      byte[] value = new byte[] { 0x01, (byte) 0xff };
      assertTrue(Arrays.equals(value, Bytes.fromHexString("01ff")));
   }

   @Test
   public void testFromHexUpper()
   {
      byte[] value = new byte[] { 0x01, (byte) 0xff };
      assertTrue(Arrays.equals(value, Bytes.fromHexString("01FF")));
   }

   @Test
   public void testFromHexMixed()
   {
      byte[] value = new byte[] { 0x01, (byte) 0xff };
      assertTrue(Arrays.equals(value, Bytes.fromHexString("01fF")));
   }


   @Test(expected = NumberFormatException.class)
   public void testFromHexOddDigitCount()
   {
      byte[] value = new byte[] { 0x01, (byte) 0xff };
      assertTrue(Arrays.equals(value, Bytes.fromHexString("1ff")));
   }

   @Test(expected = NumberFormatException.class)
   public void testFromHexInvalidCharacter()
   {
      byte[] value = new byte[] { 0x01, (byte) 0xff };
      assertTrue(Arrays.equals(value, Bytes.fromHexString("01gf")));
   }





   @Test
   public void testToArray()
   {
      byte[] data = Bytes.toArray((byte)0x00, (byte)0x01, (byte)0x02);
      assertEquals((byte) 0, data[0]);
   }

   @Test
   public void testParse()
   {
      assertEquals((byte)10, Bytes.parse("10", (byte) -1));
      assertEquals((byte)10, Bytes.parse(new StringBuilder("10"), (byte) -1));
   }

}
