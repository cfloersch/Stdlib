/**
 * Created By: cfloersch
 * Date: 6/9/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertNull;

public class CharSequencesTest {


   @Test
   public void testLength()
   {
      assertEquals(0, CharSequences.length(null));
      assertEquals(0, CharSequences.length(""));
      assertEquals(3, CharSequences.length("abc"));
   }

   @Test
   public void testTrim()
   {
      assertNull(CharSequences.trim(null));
      assertEquals("", CharSequences.trim(""));
      assertEquals("", CharSequences.trim(" "));
      assertEquals("", CharSequences.trim("  "));
      assertEquals("abc", CharSequences.trim("abc"));
      assertEquals("abc", CharSequences.trim(" abc"));
      assertEquals("abc", CharSequences.trim("abc "));
      assertEquals("abc", CharSequences.trim(" abc "));
      assertEquals("abc", CharSequences.trim("\tabc\n"));
      assertEquals("abc", CharSequences.trim("\tabc\r\n"));
      assertEquals("abc", CharSequences.trim("\tabc\r"));
      assertEquals("abc", CharSequences.trim("\t abc\r"));
      assertEquals("abc", CharSequences.trim("\tabc \r"));
      assertEquals("abc", CharSequences.trim("\tabc\r "));
      assertEquals("abc", CharSequences.trim(" \tabc\r"));
      assertEquals("abc", CharSequences.trim("\t abc\t "));
   }


   @Test
   public void testContains()
   {
      assertFalse(CharSequences.contains(null,null));
      assertFalse(CharSequences.contains(null,"hello"));
      assertFalse(CharSequences.contains("hello",null));
      assertFalse(CharSequences.contains("hello","hi"));
      assertTrue(CharSequences.contains("hello", "hell"));
   }


   @Test
   public void testEqual()
   {
      assertTrue(CharSequences.equal(null, null));
      assertFalse(CharSequences.equal(null, "abc"));
      assertFalse(CharSequences.equal("abc", null));
      assertTrue(CharSequences.equal("abc", "abc"));
      assertFalse(CharSequences.equal("abc", "ABC"));
      assertTrue(CharSequences.equal("abc", new StringBuilder("abc")));
      assertTrue(CharSequences.equal("abc", new StringBuffer("abc")));
      assertTrue(CharSequences.equal(new StringBuilder("abc"), "abc"));
      assertTrue(CharSequences.equal(new StringBuffer("abc"), "abc"));
      assertFalse(CharSequences.equal("abc", new StringBuilder("ABC")));
      assertFalse(CharSequences.equal("abc", new StringBuffer("ABC")));
      assertFalse(CharSequences.equal(new StringBuilder("ABC"), "abc"));
      assertFalse(CharSequences.equal(new StringBuffer("ABC"), "abc"));
   }

   @Test
   public void testEqualIgnoreCase()
   {
      assertTrue(CharSequences.equalIgnoreCase(null, null));
      assertFalse(CharSequences.equalIgnoreCase(null, "abc"));
      assertFalse(CharSequences.equalIgnoreCase("abc", null));
      assertTrue(CharSequences.equalIgnoreCase("abc", "abc"));
      assertTrue(CharSequences.equalIgnoreCase("abc", "ABC"));
      assertTrue(CharSequences.equalIgnoreCase("abc", new StringBuilder("abc")));
      assertTrue(CharSequences.equalIgnoreCase("abc", new StringBuffer("abc")));
      assertTrue(CharSequences.equalIgnoreCase(new StringBuilder("abc"), "abc"));
      assertTrue(CharSequences.equalIgnoreCase(new StringBuffer("abc"), "abc"));
      assertTrue(CharSequences.equalIgnoreCase("abc", new StringBuilder("ABC")));
      assertTrue(CharSequences.equalIgnoreCase("abc", new StringBuffer("ABC")));
      assertTrue(CharSequences.equalIgnoreCase(new StringBuilder("ABC"), "abc"));
      assertTrue(CharSequences.equalIgnoreCase(new StringBuffer("ABC"), "abc"));

      assertFalse(CharSequences.equalIgnoreCase("abc", "efg"));
      assertFalse(CharSequences.equalIgnoreCase("abc", "EFG"));
      assertFalse(CharSequences.equalIgnoreCase("abc", ""));
      assertFalse(CharSequences.equalIgnoreCase("", "EFG"));
      assertFalse(CharSequences.equalIgnoreCase("abc", "abc "));
      assertFalse(CharSequences.equalIgnoreCase("abc", " abc"));
   }





   @Test
   public void testIndexOfChar()
   {
      assertEquals(-1, CharSequences.indexOf(null, 'a'));
      assertEquals(-1, CharSequences.indexOf("", 'a'));
      assertEquals(-1, CharSequences.indexOf("abc", 'd'));
      assertEquals(-1, CharSequences.indexOf("abc", ' '));

      assertEquals(0, CharSequences.indexOf("abc", 'a'));
      assertEquals(1, CharSequences.indexOf("abc", 'b'));
      assertEquals(2, CharSequences.indexOf("abc", 'c'));
   }

   @Test
   public void testIndexOfCharByPosition()
   {
      assertEquals(-1, CharSequences.indexOf(null, 'a', 0));
      assertEquals(-1, CharSequences.indexOf(null, 'a', 4));
      assertEquals(-1, CharSequences.indexOf("", 'a', -4));
      assertEquals(-1, CharSequences.indexOf("", 'a', -1));
      assertEquals(-1, CharSequences.indexOf("abc", 'c', 4));
      assertEquals(-1, CharSequences.indexOf("abc", ' ', 0));

      assertEquals(0, CharSequences.indexOf("abc", 'a', 0));
      assertEquals(1, CharSequences.indexOf("abc", 'b', 1));
      assertEquals(2, CharSequences.indexOf("abc", 'c', 2));

      assertEquals(5, CharSequences.indexOf("abcabc", 'c', 3));
      assertEquals(2, CharSequences.indexOf("abcabc", 'c', -1));
   }

   @Test
   public void testIndexOf()
   {
      assertEquals(-1, CharSequences.indexOf(null, "a"));
      assertEquals(-1, CharSequences.indexOf(null, null));
      assertEquals(-1, CharSequences.indexOf("a", null));
      assertEquals(-1, CharSequences.indexOf("", "a"));
      assertEquals(-1, CharSequences.indexOf("abc", "d"));
      assertEquals(-1, CharSequences.indexOf("abc", " "));
      assertEquals(-1, CharSequences.indexOf("abc", "abcd"));

      assertEquals(0, CharSequences.indexOf("abc", "a"));
      assertEquals(1, CharSequences.indexOf("abc", "b"));
      assertEquals(2, CharSequences.indexOf("abc", "c"));

      assertEquals(1, CharSequences.indexOf("abcabc", "bc"));
      assertEquals(2, CharSequences.indexOf("abcabc", "ca"));
      assertEquals(0, CharSequences.indexOf("abcabc", "ab"));
      assertEquals(4, CharSequences.indexOf("abcabe", "be"));
      assertEquals(2, CharSequences.indexOf("abcabe", "cab"));
      assertEquals(2, CharSequences.indexOf("abcabe", "cabe"));
      assertEquals(0, CharSequences.indexOf("abcabe", "abcabe"));
   }

   @Test
   public void testIndexOfByPosition()
   {
      assertEquals(-1, CharSequences.indexOf(null, "a", 0));
      assertEquals(-1, CharSequences.indexOf(null, "a", 5));
      assertEquals(-1, CharSequences.indexOf(null, "a", -5));
      assertEquals(-1, CharSequences.indexOf("", "", 1));

      assertEquals(-1, CharSequences.indexOf("a", null, 0));
      assertEquals(-1, CharSequences.indexOf("a", null, 5));
      assertEquals(-1, CharSequences.indexOf("a", null, -5));

      assertEquals(0, CharSequences.indexOf("", "", -1));
      assertEquals(0, CharSequences.indexOf("", "", 0));

      assertEquals(0, CharSequences.indexOf("abcabc", "ab", 0));
      assertEquals(3, CharSequences.indexOf("abcabc", "ab", 1));
      assertEquals(3, CharSequences.indexOf("abcabc", "ab", 3));

      assertEquals(1, CharSequences.indexOf("abcabc", "bc", -3));
      assertEquals(4, CharSequences.indexOf("abcabc", "bc", 3));
      assertEquals(3, CharSequences.indexOf("abcabc", "abc", 3));

      assertEquals(-1, CharSequences.indexOf("abcabc", "abc", 4));

      assertEquals(0, CharSequences.indexOf("abcabc", "abcabc", 0));
      assertEquals(-1, CharSequences.indexOf("abcabc", "abcabc", 1));
   }

   @Test
   public void testIndexOfIgnoreCase()
   {
      assertEquals(-1, CharSequences.indexOfIgnoreCase(null, "a"));
      assertEquals(-1, CharSequences.indexOfIgnoreCase(null, null));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("a", null));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("", "a"));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("abc", "d"));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("abc", " "));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("abc", "abcd"));

      assertEquals(0, CharSequences.indexOfIgnoreCase("ABC", "a"));
      assertEquals(1, CharSequences.indexOfIgnoreCase("ABC", "b"));
      assertEquals(2, CharSequences.indexOfIgnoreCase("ABC", "c"));

      assertEquals(0, CharSequences.indexOfIgnoreCase("abc", "A"));
      assertEquals(1, CharSequences.indexOfIgnoreCase("abc", "B"));
      assertEquals(2, CharSequences.indexOfIgnoreCase("abc", "C"));

      assertEquals(1, CharSequences.indexOfIgnoreCase("ABCABC", "bc"));
      assertEquals(2, CharSequences.indexOfIgnoreCase("ABCABC", "ca"));
      assertEquals(0, CharSequences.indexOfIgnoreCase("ABCABC", "ab"));
      assertEquals(4, CharSequences.indexOfIgnoreCase("ABCABE", "be"));
      assertEquals(2, CharSequences.indexOfIgnoreCase("ABCABE", "cab"));
      assertEquals(2, CharSequences.indexOfIgnoreCase("ABCABE", "cabe"));
      assertEquals(0, CharSequences.indexOfIgnoreCase("ABCABE", "abcabe"));

      assertEquals(1, CharSequences.indexOfIgnoreCase("abcabc", "BC"));
      assertEquals(2, CharSequences.indexOfIgnoreCase("abcabc", "CA"));
      assertEquals(0, CharSequences.indexOfIgnoreCase("abcabc", "AB"));
      assertEquals(4, CharSequences.indexOfIgnoreCase("abcabe", "Be"));
      assertEquals(2, CharSequences.indexOfIgnoreCase("abcabe", "CaB"));
      assertEquals(2, CharSequences.indexOfIgnoreCase("abcabe", "cABE"));
      assertEquals(0, CharSequences.indexOfIgnoreCase("abcabe", "ABCabe"));
   }

   @Test
   public void testIndexOfIgnoreCaseByPosition()
   {
      assertEquals(-1, CharSequences.indexOfIgnoreCase(null, "a", 0));
      assertEquals(-1, CharSequences.indexOfIgnoreCase(null, "a", 5));
      assertEquals(-1, CharSequences.indexOfIgnoreCase(null, "a", -5));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("", "", 1));

      assertEquals(-1, CharSequences.indexOfIgnoreCase("a", null, 0));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("a", null, 5));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("a", null, -5));

      assertEquals(0, CharSequences.indexOfIgnoreCase("", "", -1));
      assertEquals(0, CharSequences.indexOfIgnoreCase("", "", 0));

      assertEquals(0, CharSequences.indexOfIgnoreCase("ABCABC", "ab", 0));
      assertEquals(3, CharSequences.indexOfIgnoreCase("ABCABC", "ab", 1));
      assertEquals(3, CharSequences.indexOfIgnoreCase("ABCABC", "ab", 3));
      assertEquals(0, CharSequences.indexOfIgnoreCase("abcabc", "AB", 0));
      assertEquals(3, CharSequences.indexOfIgnoreCase("abcabc", "AB", 1));
      assertEquals(3, CharSequences.indexOfIgnoreCase("abcabc", "AB", 3));


      assertEquals(1, CharSequences.indexOfIgnoreCase("ABCABC", "bc", -3));
      assertEquals(4, CharSequences.indexOfIgnoreCase("ABCABC", "bc", 3));
      assertEquals(3, CharSequences.indexOfIgnoreCase("ABCABC", "abc", 3));

      assertEquals(1, CharSequences.indexOfIgnoreCase("abcabc", "BC", -3));
      assertEquals(4, CharSequences.indexOfIgnoreCase("abcabc", "BC", 3));
      assertEquals(3, CharSequences.indexOfIgnoreCase("abcabc", "ABC", 3));


      assertEquals(-1, CharSequences.indexOfIgnoreCase("abcabc", "ABC", 4));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("ABCABC", "abc", 4));

      assertEquals(0, CharSequences.indexOfIgnoreCase("abcabc", "abcabc", 0));
      assertEquals(-1, CharSequences.indexOfIgnoreCase("abcabc", "abcabc", 1));
   }


   @Test
   public void testLastIndexOfChar()
   {
      assertEquals(-1, CharSequences.lastIndexOf(null, 'c'));
      assertEquals(-1, CharSequences.lastIndexOf("", 'c'));

      assertEquals(7, CharSequences.lastIndexOf("aabaabaa", 'a'));
      assertEquals(5, CharSequences.lastIndexOf("aabaabaa", 'b'));

      assertEquals(0, CharSequences.indexOf("abc", 'a'));
      assertEquals(1, CharSequences.indexOf("abc", 'b'));
      assertEquals(2, CharSequences.indexOf("abc", 'c'));
   }


   @Test
   public void testLastIndexOfCharByPosition()
   {
      assertEquals(-1, CharSequences.lastIndexOf(null, 'c', 2));
      assertEquals(-1, CharSequences.lastIndexOf("", 'c', 2));

      assertEquals(5, CharSequences.lastIndexOf("aabaabaa", 'b', 8));
      assertEquals(2, CharSequences.lastIndexOf("aabaabaa", 'b', 4));
      assertEquals(-1, CharSequences.lastIndexOf("aabaabaa", 'b', 0));
      assertEquals(5, CharSequences.lastIndexOf("aabaabaa", 'b', 9));
      assertEquals(7, CharSequences.lastIndexOf("aabaabaa", 'a', 9));
      assertEquals(-1, CharSequences.lastIndexOf("aabaabaa", 'b', -1));
      assertEquals(0, CharSequences.lastIndexOf("aabaabaa", 'a', 0));
   }

   @Test
   public void testLastIndexOf()
   {
      assertEquals(-1, CharSequences.lastIndexOf(null, null));
      assertEquals(-1, CharSequences.lastIndexOf(null, "yada"));
      assertEquals(-1, CharSequences.lastIndexOf("yada", null));
      assertEquals(-1, CharSequences.lastIndexOf("aabaabaa", "aabaabaab"));
      assertEquals(0, CharSequences.lastIndexOf("", ""));
      assertEquals(7, CharSequences.lastIndexOf("aabaabaa", ""));
      assertEquals(7, CharSequences.lastIndexOf("aabaabaa", "a"));
      assertEquals(5, CharSequences.lastIndexOf("aabaabaa", "b"));
      assertEquals(4, CharSequences.lastIndexOf("aabaabaa", "ab"));
   }

   @Test
   public void testLastIndexOfByPosition()
   {
      assertEquals(-1, CharSequences.lastIndexOf(null, null, 1));
      assertEquals(-1, CharSequences.lastIndexOf(null, "yada", 1));
      assertEquals(-1, CharSequences.lastIndexOf("yada", null, 1));
      assertEquals(-1, CharSequences.lastIndexOf("aabaabaa", "aabaabaab", 0));
      assertEquals(0, CharSequences.lastIndexOf("", "", 0));
      assertEquals(0, CharSequences.lastIndexOf("", "", 1));
      assertEquals(7, CharSequences.lastIndexOf("aabaabaa", "a", 8));
      assertEquals(7, CharSequences.lastIndexOf("aabaabaa", "a", 22));
      assertEquals(5, CharSequences.lastIndexOf("aabaabaa", "b", 8));
      assertEquals(5, CharSequences.lastIndexOf("aabaabaa", "b", 22));
      assertEquals(4, CharSequences.lastIndexOf("aabaabaa", "ab", 8));
      assertEquals(4, CharSequences.lastIndexOf("aabaabaa", "ab", 22));
      assertEquals(5, CharSequences.lastIndexOf("aabaabaa", "b", 9));
      assertEquals(-1, CharSequences.lastIndexOf("aabaabaa", "b", -1));
      assertEquals(0, CharSequences.lastIndexOf("aabaabaa", "a", 0));
      assertEquals(-1, CharSequences.lastIndexOf("aabaabaa", "b", 0));
   }


   @Test
   public void testLastIndexOfIgnoreCase()
   {
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase(null, null));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase(null, ""));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase("", null));

      assertEquals(7, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "A"));
      assertEquals(7, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "a"));

      assertEquals(5, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B"));
      assertEquals(5, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "b"));

      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "AB"));
      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "aB"));
      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "Ab"));
      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "ab"));
   }

   @Test
   public void testLastIndexOfIgnoreCaseByPosition()
   {
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase(null, null, 0));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase(null, null, 9));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase(null, null, -9));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase(null, "", 0));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase(null, "", 9));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase(null, "", -9));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase("", null, 0));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase("", null, 9));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase("", null, -9));

      assertEquals(7, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "A", 8));
      assertEquals(7, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "a", 8));
      assertEquals(7, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "A", 9));
      assertEquals(7, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "a", 9));

      assertEquals(5, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B", 8));
      assertEquals(5, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "b", 8));
      assertEquals(5, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B", 9));
      assertEquals(5, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "b", 9));

      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "AB", 8));
      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "Ab", 8));
      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "Ab", 9));
      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "aB", 8));
      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "ab", 8));
      assertEquals(4, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "ab", 9));


      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B", -1));
      assertEquals(0, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "A", 0));
      assertEquals(-1, CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B", 0));
   }


   @Test
   public void testSubStringToEnd()
   {
      assertEquals(null, CharSequences.substring(null, 0));
      assertEquals(null, CharSequences.substring(null, -1));
      assertEquals(null, CharSequences.substring(null, 20));

      assertEquals("", CharSequences.substring("", -1));
      assertEquals("", CharSequences.substring("", 0));
      assertEquals("", CharSequences.substring("", 20));

      assertEquals("abc", CharSequences.substring("abc", 0));
      assertEquals("bc", CharSequences.substring("abc", 1));
      assertEquals("c", CharSequences.substring("abc", 2));
      assertEquals("", CharSequences.substring("abc", 4));
      assertEquals("", CharSequences.substring("abc", 6));

      assertEquals("c", CharSequences.substring("abc", -1));
      assertEquals("bc", CharSequences.substring("abc", -2));
      assertEquals("abc", CharSequences.substring("abc", -4));
      assertEquals("abc", CharSequences.substring("abc", -22));
   }

   @Test
   public void testSubString()
   {
      assertEquals(null, CharSequences.substring(null, 0, 2));
      assertEquals(null, CharSequences.substring(null, -1, -2));
      assertEquals(null, CharSequences.substring(null, 20, 5));

      assertEquals("", CharSequences.substring("", 1, 5));
      assertEquals("", CharSequences.substring("", -1, 5));
      assertEquals("", CharSequences.substring("", 1, -5));

      assertEquals("ab", CharSequences.substring("abc", 0, 2));
      assertEquals("", CharSequences.substring("abc", 2, 0));
      assertEquals("c", CharSequences.substring("abc", 2, 4));
      assertEquals("", CharSequences.substring("abc", 4, 6));
      assertEquals("", CharSequences.substring("abc", 2, 2));
      assertEquals("b", CharSequences.substring("abc", -2, -1));
      assertEquals("ab", CharSequences.substring("abc", -3, -1));
      assertEquals("ab", CharSequences.substring("abc", -4, 2));
      assertEquals("", CharSequences.substring("abc", -4, -4));
      assertEquals("", CharSequences.substring("abc", -6, -4));

      assertEquals("", CharSequences.substring("abc", -2, 1));
      assertEquals("a", CharSequences.substring("abc", -3, 1));
      assertEquals("", CharSequences.substring("abc", -3, 0));
   }

   @Test
   public void testStartsWith()
   {
      assertTrue(CharSequences.startsWith(null, null));
      assertFalse(CharSequences.startsWith(null, "abc"));
      assertFalse(CharSequences.startsWith("abc", null));

      assertTrue(CharSequences.startsWith("", ""));
      assertTrue(CharSequences.startsWith("abc", ""));
      assertTrue(CharSequences.startsWith("ABC", ""));

      assertTrue(CharSequences.startsWith("abcdef", "abc"));
      assertTrue(CharSequences.startsWith("ABCDEF", "ABC"));
      assertTrue(CharSequences.startsWith("a", "a"));
      assertTrue(CharSequences.startsWith("A", "A"));

      assertFalse(CharSequences.startsWith("ABCDEF", "AbC"));
      assertFalse(CharSequences.startsWith("ABCDEF", "abc"));
      assertFalse(CharSequences.startsWith("abcdef", "ABC"));
      assertFalse(CharSequences.startsWith("abcdef", "AbC"));

      assertFalse(CharSequences.startsWith("ABCDEF", "BCD"));
      assertFalse(CharSequences.startsWith("abcdef", "bcd"));
   }

   @Test
   public void testStartsWithIgnoreCase()
   {
      assertTrue(CharSequences.startsWithIgnoreCase(null, null));
      assertFalse(CharSequences.startsWithIgnoreCase(null, "abc"));
      assertFalse(CharSequences.startsWithIgnoreCase("abc", null));

      assertTrue(CharSequences.startsWithIgnoreCase("", ""));
      assertTrue(CharSequences.startsWithIgnoreCase("abc", ""));
      assertTrue(CharSequences.startsWithIgnoreCase("ABC", ""));


      assertTrue(CharSequences.startsWithIgnoreCase("abcdef", "abc"));
      assertTrue(CharSequences.startsWithIgnoreCase("ABCDEF", "ABC"));
      assertTrue(CharSequences.startsWithIgnoreCase("ABCDEF", "AbC"));
      assertTrue(CharSequences.startsWithIgnoreCase("abcdef", "ABC"));
      assertTrue(CharSequences.startsWithIgnoreCase("abcdef", "AbC"));
      assertTrue(CharSequences.startsWithIgnoreCase("ABCDEF", "abc"));

      assertTrue(CharSequences.startsWithIgnoreCase("A", "a"));
      assertTrue(CharSequences.startsWithIgnoreCase("a", "A"));

      assertFalse(CharSequences.startsWithIgnoreCase("ABCDEF", "BCD"));
      assertFalse(CharSequences.startsWithIgnoreCase("ABCDEF", "bcd"));
      assertFalse(CharSequences.startsWithIgnoreCase("abcdef", "BCD"));
      assertFalse(CharSequences.startsWithIgnoreCase("abcdef", "bcd"));
   }


   @Test
   public void testEndsWith()
   {
      assertTrue(CharSequences.endsWith(null, null));
      assertFalse(CharSequences.endsWith(null, "abc"));
      assertFalse(CharSequences.endsWith("abc", null));

      assertTrue(CharSequences.endsWith("", ""));
      assertTrue(CharSequences.endsWith("abc", ""));
      assertTrue(CharSequences.endsWith("ABC", ""));


      assertTrue(CharSequences.endsWith("abcdef", "def"));
      assertTrue(CharSequences.endsWith("ABCDEF", "DEF"));

      assertTrue(CharSequences.endsWith("f", "f"));
      assertTrue(CharSequences.endsWith("F", "F"));

      assertFalse(CharSequences.endsWith("ABCDEF", "DeF"));
      assertFalse(CharSequences.endsWith("ABCDEF", "def"));
      assertFalse(CharSequences.endsWith("abcdef", "DEF"));
      assertFalse(CharSequences.endsWith("abcdef", "DeF"));

      assertFalse(CharSequences.endsWith("ABCDEF", "CDE"));
      assertFalse(CharSequences.endsWith("abcdef", "cde"));
   }

   @Test
   public void testEndsWithIgnoreCase()
   {
      assertTrue(CharSequences.endsWithIgnoreCase(null, null));
      assertFalse(CharSequences.endsWithIgnoreCase(null, "abc"));
      assertFalse(CharSequences.endsWithIgnoreCase("abc", null));

      assertTrue(CharSequences.endsWithIgnoreCase("", ""));
      assertTrue(CharSequences.endsWithIgnoreCase("abc", ""));
      assertTrue(CharSequences.endsWithIgnoreCase("ABC", ""));

      assertTrue(CharSequences.endsWithIgnoreCase("abcdef", "def"));
      assertTrue(CharSequences.endsWithIgnoreCase("ABCDEF", "DEF"));

      assertTrue(CharSequences.endsWithIgnoreCase("ABCDEF", "DeF"));
      assertTrue(CharSequences.endsWithIgnoreCase("ABCDEF", "def"));
      assertTrue(CharSequences.endsWithIgnoreCase("abcdef", "DEF"));
      assertTrue(CharSequences.endsWithIgnoreCase("abcdef", "DeF"));

      assertTrue(CharSequences.endsWithIgnoreCase("f", "F"));
      assertTrue(CharSequences.endsWithIgnoreCase("F", "f"));

      assertFalse(CharSequences.endsWithIgnoreCase("ABCDEF", "CDE"));
      assertFalse(CharSequences.endsWithIgnoreCase("abcdef", "cde"));
   }

}
