/**
 * Created By: cfloersch
 * Date: 6/10/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.jupiter.api.Test;
import xpertss.io.Charsets;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BytesTest {


   @Test
   public void testNullSrcThrowsNullPointerException()
   {
      assertThrows(NullPointerException.class, ()->{
         Bytes.regionMatches(null, 0, new byte[2], 0, 2);
      });
   }

   @Test
   public void testNullOtherThrowsNullPointerException()
   {
      assertThrows(NullPointerException.class, ()->{
         Bytes.regionMatches(new byte[2], 0, null, 0, 2);
      });
   }


   @Test
   public void testNegativeSrcOffsetThrowsIllegalArgumentException()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Bytes.regionMatches(new byte[2], -1, new byte[2], 0, 2);
      });
   }

   @Test
   public void testNegativeOtherOffsetThrowsIllegalArgumentException()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Bytes.regionMatches(new byte[2], 0, new byte[2], -1, 2);
      });
   }

   @Test
   public void testNegativeLengthThrowsIllegalArgumentException()
   {
      assertThrows(IllegalArgumentException.class, ()->{
         Bytes.regionMatches(new byte[2], 0, new byte[2], 0, -1);
      });
   }



   @Test
   public void testRegionMatchesSrcArrayIndexOutOfBounds()
   {
      assertThrows(ArrayIndexOutOfBoundsException.class, ()->{
         Bytes.regionMatches(new byte[2], 2, new byte[4], 0, 2);
      });

   }

   @Test
   public void testRegionMatchesOtherArrayIndexOutOfBounds()
   {
      assertThrows(ArrayIndexOutOfBoundsException.class, ()->{
         Bytes.regionMatches(new byte[4], 2, new byte[2], 2, 2);
      });
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


   @Test
   public void testFromHexOddDigitCount()
   {
      byte[] value = new byte[] { 0x01, (byte) 0xff };
      assertThrows(NumberFormatException.class, ()->{
         Arrays.equals(value, Bytes.fromHexString("1ff"));
      });
   }

   @Test
   public void testFromHexInvalidCharacter()
   {
      byte[] value = new byte[] { 0x01, (byte) 0xff };
      assertThrows(NumberFormatException.class, ()->{
         Arrays.equals(value, Bytes.fromHexString("01gf"));
      });
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
