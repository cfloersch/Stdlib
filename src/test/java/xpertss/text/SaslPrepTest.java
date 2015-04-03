package xpertss.text;

import org.junit.Test;
import xpertss.io.Charsets;
import xpertss.lang.SyntaxException;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Some additional test vectors
 * http://www.filewatcher.com/p/libidn_1.25.orig.tar.gz.3390531/libidn-1.25/tests/tst_stringprep.c.html
 *
 */
public class SaslPrepTest {

   @Test
   public void testSimple()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      String input = "cfloersch";
      assertEquals("cfloersch", prep.prepare(input, false));
   }


   @Test
   public void testMapping()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      String input = "I\u00ADX";
      assertEquals("IX", prep.prepare(input, false));
   }

   @Test
   public void testMappingTwo()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      String input = "x\u00ADy";
      assertEquals("xy", prep.prepare(input, false));
   }

   @Test
   public void testMappingThree()
   {
      byte[] data = { (byte)0xE2, (byte)0x85, (byte)0xA3 };
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      assertEquals("IV", prep.prepare(new String(data, Charsets.UTF_8), false));
   }


   @Test
   public void testNoTransform()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      String input = "user";
      assertEquals("user", prep.prepare(input, false));
   }

   @Test
   public void testCasePreserve()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      String input = "USER";
      assertEquals("USER", prep.prepare(input, false));
   }

   @Test
   public void testNfkc()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      String input = "\u00AA";
      assertEquals("a", prep.prepare(input, false));
   }


   @Test
   public void testNfkcMatches()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      String one = "I\u00ADX";
      String two = "\u2168";
      assertEquals(prep.prepare(one, false), prep.prepare(two, false));
   }


   @Test(expected = SyntaxException.class)
   public void testProhibitedCharacter()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      prep.prepare("\u0007", false);
   }

   @Test(expected = SyntaxException.class)
   public void testBidirectionalFail()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      prep.prepare("\u0627\u0031", false);
   }


   @Test
   public void testSpeedOnLongString()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      long start = System.nanoTime();
      prep.prepare("This is a long password example but realistic in todays world", false);
      long end = System.nanoTime();
      System.out.println(TimeUnit.NANOSECONDS.toMicros(end - start) + " microseconds");
   }

   @Test
   public void testSpeedOnShortString()
   {
      StringPrep prep = StringPrep.getInstance(Profile.SASLPrep);
      long start = System.nanoTime();
      prep.prepare("cfloersch", false);
      long end = System.nanoTime();
      System.out.println(TimeUnit.NANOSECONDS.toMicros(end - start) + " microseconds");
   }

}