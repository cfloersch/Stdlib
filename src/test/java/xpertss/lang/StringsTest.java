/**
 * Created By: cfloersch
 * Date: 6/6/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringsTest {

   @Test
   public void testJoin()
   {
      assertEquals("1", Strings.join(" ", "1"));
      assertEquals("1,2,3", Strings.join(",", "1", "2", "3"));
      assertEquals("1 2 3", Strings.join(" ", "1", "2", "3"));
   }

   @Test
   public void testJoinNullParts()
   {
      assertEquals("", Strings.join(",", (String) null));
   }

   @Test
   public void testJoinNullSeparator()
   {
      assertThrows(NullPointerException.class, ()->{
         Strings.join(null, "1", "2", "3");
      });
   }

   @Test
   public void testJoinNullIterator()
   {
      assertThrows(NullPointerException.class, ()->{
         Strings.join(" ", (Iterable<String>)null);
      });
   }

   @Test
   public void testJoinNullArray()
   {
      assertThrows(NullPointerException.class, ()->{
         Strings.join(" ", null, 0, 1);
      });
   }

   @Test
   public void testJoinInvalidOffset()
   {
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Strings.join(" ", new String[0], 1, 1);
      });
   }

   @Test
   public void testJoinInvalidLength()
   {
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Strings.join(" ", new String[0], 0, 1);
      });
   }



   @Test
   public void testContains()
   {
      assertFalse(Strings.contains(null, null));
      assertFalse(Strings.contains("", null));
      assertFalse(Strings.contains(null, ""));

      assertTrue(Strings.contains("", ""));
      assertTrue(Strings.contains("abcdef", ""));
      assertTrue(Strings.contains("abcdef", "abc"));
      assertTrue(Strings.contains("abcdef", "def"));
      assertTrue(Strings.contains("abcdef", "bcd"));
      assertTrue(Strings.contains("abcdef", "cde"));

      assertFalse(Strings.contains("abcdef", "fed"));
      assertFalse(Strings.contains("abcdef", "ddd"));

      assertFalse(Strings.contains("abcdef", "BCD"));
      assertFalse(Strings.contains("abcdef", "BcD"));
      assertFalse(Strings.contains("abcdef", "CDE"));
      assertFalse(Strings.contains("abcdef", "cdE"));
   }

   @Test
   public void testChomp()
   {
      assertEquals("Hello", Strings.chomp("Hello"));
      assertEquals("Hello", Strings.chomp("Hello\r"));
      assertEquals("Hello", Strings.chomp("Hello\n"));
      assertEquals("Hello", Strings.chomp("Hello\r\n"));
      assertEquals("Hello", Strings.chomp("Hello\r\r"));
      assertEquals("Hello", Strings.chomp("Hello\n\n"));
      assertEquals("Hello", Strings.chomp("Hello\r\n\r\n"));
      assertEquals("\r\nHello", Strings.chomp("\r\nHello\r\n\r\n"));
      assertNull(Strings.chomp(null));
      assertEquals("", Strings.chomp(""));
      assertEquals(" ", Strings.chomp(" "));
      assertEquals(" ", Strings.chomp(" \r\n"));
   }

   @Test
   public void testCount()
   {
      assertEquals(1, Strings.count("::1", "::"));
      assertEquals(1, Strings.count("2001::", "::"));
      assertEquals(1, Strings.count("fe80::204:61ff:fe9d:f156", "::"));
      assertEquals(5, Strings.count("fe80::204:61ff:fe9d:f156", ":"));
      assertEquals(1, Strings.count("::", "::"));
   }

   @Test
   public void testSplit()
   {
      assertEquals(2, ":1".split(":").length);
      assertEquals(2, "1:".split(":", -1).length);
   }
}
