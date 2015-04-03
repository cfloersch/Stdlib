package xpertss.text;

import org.junit.Test;

import xpertss.io.Charsets;
import xpertss.lang.SyntaxException;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class NamePrepTest {

   @Test
   public void testSimple()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      String input = "cfloersch";
      assertEquals(input, prep.prepare(input, false));
   }

   @Test
   public void testCaseFolding()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("cfloersch", prep.prepare("cFloersch", false));
   }

   @Test
   public void testCaseFoldingTwo()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("cfloersch", prep.prepare("CFLOERSCH", false));
   }

   @Test
   public void testInternationalMixedText()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("安室奈美恵-with-super-monkeys", prep.prepare("安室奈美恵-with-SUPER-MONKEYS", false));
   }

   @Test
   public void testKorean()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("미술", prep.prepare("미술", false));
   }


   @Test
   public void testEgyptian()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("ليهمابتكلموشعربي؟", prep.prepare("ليهمابتكلموشعربي؟", false));
   }


   @Test
   public void testChinese()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("他们为什么不说中文", prep.prepare("他们为什么不说中文", false));
   }


   @Test
   public void testHebrew()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("למההםפשוטלאמדבריםעברית", prep.prepare("למההםפשוטלאמדבריםעברית", false));
   }






   @Test
   public void testRussian()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("почемужеонинеговорятпорусски", prep.prepare("почемужеонинеговорятпорусски", false));
   }

   @Test
   public void testVietnamese()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("tạisaohọkhôngthểchỉnóitiếngviệt", prep.prepare("TạisaohọkhôngthểchỉnóitiếngViệt", false));
   }

   @Test
   public void testJapanese()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("ひとつ屋根の下2", prep.prepare("ひとつ屋根の下2", false));
   }


   @Test
   public void testCzech()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("pročprostěnemluvíčesky", prep.prepare("Pročprostěnemluvíčesky", false));
   }


   @Test
   public void testHindi()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("यहलोगहिन्दीक्योंनहींबोलसकतेहैं", prep.prepare("यहलोगहिन्दीक्योंनहींबोलसकतेहैं", false));
   }

   @Test
   public void testLao()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("ພາສາລາວ", prep.prepare("ພາສາລາວ", false));
   }

   @Test
   public void testMaltese()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("bonġusaħħa", prep.prepare("bonġusaħħa", false));
   }

   @Test
   public void testGreek()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      assertEquals("ελληνικά", prep.prepare("ελληνικά", false));
   }





   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsOne()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xC2, (byte) 0x85 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsTwo()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xE1, (byte) 0xA0, (byte) 0x8E };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsThree()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF0, (byte) 0x9D, (byte)0x85, (byte) 0xB5 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsFour()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xEF, (byte) 0x84, (byte) 0xA3 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsFive()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF3, (byte) 0xB1, (byte) 0x88, (byte) 0xB4 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsSix()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF4, (byte) 0x8F, (byte) 0x88, (byte) 0xB4 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsSeven()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF2, (byte) 0x8F, (byte) 0xBF, (byte) 0xBE };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsEight()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF4, (byte) 0x8F, (byte) 0xBF, (byte) 0xBF };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsNine()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xED, (byte) 0xBD, (byte) 0x82 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsTen()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xE2, (byte) 0xBF, (byte) 0xB5 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }



   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsEleven()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xE2, (byte) 0x80, (byte) 0x8E };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsTwelve()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xE2, (byte) 0x80, (byte) 0xAA };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsThirteen()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF3, (byte) 0xA0, (byte) 0x80, (byte) 0x81 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsFourteen()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF3, (byte) 0xA0, (byte) 0x81, (byte) 0x82 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailProhibitedCharsFifteen()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF4, (byte) 0x8F, (byte) 0xBF, (byte) 0xBF };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }





   @Test(expected = SyntaxException.class)
   public void testFailBidiOne()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { 'f', 'o', 'o', (byte)0xD6, (byte) 0xBE, 'b', 'a', 'r' };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailBidiTwo()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { 'f', 'o', 'o', (byte)0xEF, (byte) 0xB5, (byte) 0x90, 'b', 'a', 'r' };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }

   @Test(expected = SyntaxException.class)
   public void testFailBidiThree()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xD8, (byte) 0xA7, (byte) 0x31 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }



   @Test(expected = SyntaxException.class)
   public void testFailUnassigned()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      byte[] input = { (byte)0xF3, (byte) 0xA0, (byte) 0x80, (byte) 0x82 };
      prep.prepare(new String(input, Charsets.UTF_8), false);
   }



   private byte[][] vectors = {
      // Map To Nothing
      { 'f', 'o', 'o', (byte)0xC2, (byte)0xAD, (byte)0xCD,  (byte)0x8F, (byte)0xE1, (byte)0xA0, (byte)0x86,
         (byte)0xE1, (byte)0xA0, (byte)0x8B, 'b', 'a', 'r', (byte)0xE2, (byte)0x80, (byte)0x8B, (byte)0xE2,
         (byte)0x81, (byte)0xA0, 'b', 'a', 'z', (byte)0xEF, (byte)0xB8, (byte)0x80, (byte)0xEF, (byte)0xB8,
         (byte)0x88, (byte)0xEF, (byte)0xB8, (byte)0x8F, (byte)0xEF, (byte)0xBB, (byte)0xBF },
      {'f', 'o', 'o', 'b', 'a', 'r', 'b', 'a', 'z'},

      // Case Folding German Sharp s
      { (byte)0xC3, (byte)0x9F },
      {'s','s'},


      // Case folding U+0130 (turkish capital I with dot)
      {(byte) 0xC4, (byte)0xB0},
      {'i', (byte)0xCC, (byte)0x87},

      // Case folding multibyte U+0143 U+037A
      {(byte) 0xC5, (byte) 0x83, (byte) 0xCD, (byte) 0xBA},
      {(byte) 0xC5, (byte) 0x84, ' ', (byte) 0xCE, (byte) 0xB9},


      // Normalization of U+006a U+030c U+00A0 U+00AA
      {(byte)0x6A, (byte)0xCC, (byte)0x8C, (byte)0xC2, (byte)0xA0, (byte)0xC2, (byte)0xAA},
      {(byte)0xC7, (byte)0xB0, ' ', 'a'},


      // Case folding U+1FB7 and normalization
      {(byte)0xE1, (byte)0xBE, (byte)0xB7},
      {(byte)0xE1, (byte)0xBE, (byte)0xB6, (byte)0xCE, (byte)0xB9},


      // Self-reverting case folding U+01F0 and normalization
      /*
      {(byte) 0xC7, (byte) 0xF0},
      {(byte) 0xC7, (byte) 0xB0},
      */

      // Self-reverting case folding U+0390 and normalization
      {(byte) 0xCE, (byte) 0x90},
      {(byte) 0xCE, (byte) 0x90},

      // Self-reverting case folding U+03B0 and normalization
      {(byte) 0xCE, (byte) 0xB0},
      {(byte) 0xCE, (byte) 0xB0},

      //Self-reverting case folding U+1E96 and normalization
      {(byte) 0xE1, (byte) 0xBA, (byte) 0x96},
      {(byte) 0xE1, (byte) 0xBA, (byte) 0x96},

      // Self-reverting case folding U+1F56 and normalization
      {(byte) 0xE1, (byte) 0xBD, (byte) 0x96},
      {(byte) 0xE1, (byte) 0xBD, (byte) 0x96},


      // ASCII space character U+0020
      {(byte) 0x20},
      {(byte) 0x20},

      // Non-ASCII 8bit space character U+00A0
      {(byte) 0xC2, (byte) 0xA0},
      {(byte) 0x20},


      // Non-ASCII multibyte space character U+2000
      {(byte) 0xE2, (byte) 0x80, (byte) 0x80},
      {(byte) 0x20},

      // Zero Width Space U+200b
      {(byte) 0xE2, (byte) 0x80, (byte) 0x8b},
      {},

      // Non-ASCII multibyte space character U+3000
      {(byte) 0xE3, (byte) 0x80, (byte) 0x80},
      {(byte) 0x20},

      // ASCII control characters U+0010 U+007F
      {(byte) 0x10, (byte) 0x7F},
      {(byte) 0x10, (byte) 0x7F},

      // Zero Width No-Break Space U+FEFF
      {(byte) 0xEF, (byte) 0xBB, (byte)0xBF},
      {},

      // Display property character U+0341
      {(byte) 0xCD, (byte) 0x81},
      {(byte) 0xCC, (byte) 0x81},


      // Bidi: RandALCat character U+0627 U+0031 U+0628
      {(byte) 0xD8, (byte) 0xA7, (byte) 0x31, (byte) 0xD8, (byte) 0xA8},
      {(byte) 0xD8, (byte) 0xA7, (byte) 0x31, (byte) 0xD8, (byte) 0xA8},

   };


   @Test
   public void testVectors()
   {
      StringPrep prep = StringPrep.getInstance(Profile.NamePrep);
      for(int i = 0; i < vectors.length; i+=2) {
         String input = new String(vectors[i], Charsets.UTF_8);
         String result= new String(vectors[i+1], Charsets.UTF_8);
         long start = System.nanoTime();
         assertEquals(result, prep.prepare(input, false));
         long end = System.nanoTime();
         System.out.println(TimeUnit.NANOSECONDS.toMicros(end - start) + " microseconds");
      }
   }

}