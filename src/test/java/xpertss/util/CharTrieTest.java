package xpertss.util;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class CharTrieTest {

   @Test
   public void testBinarySearch()
   {
      CharTrie<Object> test = new CharTrie<>();
      int idx = test.binarySearch(0,0,'c');
      assertEquals(-1, idx);
      assertEquals(0, -(idx + 1));
   }


   @Test
   public void testRoot()
   {
      CharTrie<String> test = new CharTrie<>();
      test.put("a", "a");
      assertTrue(test.isRoot());
      assertFalse(test.findChild('a').isRoot());
   }

   @Test
   public void testSimpleAdd()
   {
      CharTrie<String> test = new CharTrie<>();
      test.put("a", "a");
      test.put("c", "c");
      test.put("b", "b");
      assertEquals("a", test.find("a"));
      assertEquals("b", test.find("b"));
      assertEquals("c", test.find("c"));
      assertEquals("a", test.findChild('a').get());
      assertEquals("b", test.findChild('b').get());
      assertEquals("c", test.findChild('c').get());
      assertNull(test.get());
   }

   @Test
   public void testTwoLevels()
   {
      CharTrie<String> test = new CharTrie<>();
      test.put("ab", "ab");
      test.put("ac", "ac");
      test.put("ad", "ad");
      test.put("ae", "ae");
      assertEquals("ab", test.find("ab"));
      assertNull(test.findChild('b'));
      assertNull(test.find("bc"));
      CharTrie<String> a = test.findChild('a');
      assertNull(a.get());
      assertEquals("ab", a.findChild('b').get());
      assertEquals("ac", a.findChild('c').get());
      assertEquals("ad", a.findChild('d').get());
   }

   @Test
   public void testStringSearchShort()
   {
      CharTrie<String> test = new CharTrie<>();
      test.put("EST", "Eastern Standard");
      test.put("EDT", "Eastern Daylight");

      long start = System.nanoTime();
      String testStr = "EDT";
      for(int i = 0; i < testStr.length(); i++) {
         test = test.findChild(testStr.charAt(i));
         if(test == null) fail();
      }
      long end = System.nanoTime();
      System.out.println(TimeUnit.NANOSECONDS.toMicros(end - start) + " micros");
      assertEquals("Eastern Daylight", test.get());
   }

   @Test
   public void testStringSearchLong()
   {
      CharTrie<String> test = new CharTrie<>();
      test.put("Eastern Standard Time", "EST");
      test.put("Eastern Daylight Time", "EDT");

      long start = System.nanoTime();
      String testStr = "Eastern Standard Time";
      for(int i = 0; i < testStr.length(); i++) {
         test = test.findChild(testStr.charAt(i));
         if(test == null) fail();
      }
      long end = System.nanoTime();
      System.out.println(TimeUnit.NANOSECONDS.toMicros(end - start) + " micros");
      assertEquals("EST", test.get());
   }


   @Test
   public void testAlreadyBound()
   {
      CharTrie<String> test = new CharTrie<>();
      assertNull(test.put("Eastern Standard Time", "EST"));
      assertNotNull(test.put("Eastern Standard Time", "EDT"));
      long start = System.nanoTime();
      String value = test.find("Eastern Standard Time");
      long end = System.nanoTime();
      assertEquals("EDT", value);
      System.out.println(TimeUnit.NANOSECONDS.toMicros(end - start) + " micros");

   }

}