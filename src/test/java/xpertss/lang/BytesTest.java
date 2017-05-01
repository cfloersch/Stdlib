/**
 * Created By: cfloersch
 * Date: 6/10/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.Test;
import xpertss.io.Charsets;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BytesTest {


   @Test(expected = NullPointerException.class)
   public void testNullSrcThrowsNullPointerException()
   {
      Bytes.regionMatches(null, 0, new byte[2], 0, 2);
   }

   @Test(expected = NullPointerException.class)
   public void testNullOtherThrowsNullPointerException()
   {
      Bytes.regionMatches(new byte[2], 0, null, 0, 2);
   }


   @Test(expected = IllegalArgumentException.class)
   public void testNegativeSrcOffsetThrowsIllegalArgumentException()
   {
      Bytes.regionMatches(new byte[2], -1, new byte[2], 0, 2);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testNegativeOtherOffsetThrowsIllegalArgumentException()
   {
      Bytes.regionMatches(new byte[2], 0, new byte[2], -1, 2);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testNegativeLengthThrowsIllegalArgumentException()
   {
      Bytes.regionMatches(new byte[2], 0, new byte[2], 0, -1);
   }



   @Test(expected = ArrayIndexOutOfBoundsException.class)
   public void testRegionMatchesSrcArrayIndexOutOfBounds()
   {
      Bytes.regionMatches(new byte[2], 2, new byte[4], 0, 2);
   }

   @Test(expected = ArrayIndexOutOfBoundsException.class)
   public void testRegionMatchesOtherArrayIndexOutOfBounds()
   {
      Bytes.regionMatches(new byte[4], 2, new byte[2], 2, 2);
   }

   @Test
   public void testRegionMatches()
   {
      byte[] src = "This is an example text".getBytes(Charsets.US_ASCII);
      assertTrue(Bytes.regionMatches(src, 0, "This".getBytes(Charsets.US_ASCII), 0, 4));
      assertTrue(Bytes.regionMatches(src, 19, "text".getBytes(Charsets.US_ASCII), 0, 4));
      assertTrue(Bytes.regionMatches(src, 5, "is an".getBytes(Charsets.US_ASCII), 0, 5));
      assertTrue(Bytes.regionMatches(src, 5, "is an".getBytes(Charsets.US_ASCII), 0, 2));
      assertTrue(Bytes.regionMatches(src, 8, "is an".getBytes(Charsets.US_ASCII), 3, 2));

      assertFalse(Bytes.regionMatches(src, 5, "is an".getBytes(Charsets.US_ASCII), 3, 2));
   }




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
