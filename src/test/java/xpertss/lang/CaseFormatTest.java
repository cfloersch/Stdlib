package xpertss.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static xpertss.lang.CaseFormat.*;

/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 5/27/2016
 */
public class CaseFormatTest {


   @Test
   public void testForStringUpperUnderScore()
   {
      assertEquals(UPPER_UNDERSCORE, of("HELLO_KITTY"));
   }

   @Test
   public void testForStringLowerUnderScore()
   {
      assertEquals(LOWER_UNDERSCORE, of("hello_kitty"));
   }

   @Test
   public void testForStringLowerHyphen()
   {
      assertEquals(LOWER_HYPHEN, of("hello-kitty"));
   }

   @Test
   public void testForStringLowerCamel()
   {
      assertEquals(LOWER_CAMEL, of("helloKitty"));
   }

   @Test
   public void testForStringUpperCamel()
   {
      assertEquals(UPPER_CAMEL, of("HelloKitty"));
   }

   @Test
   public void testForSingleWord()
   {
      assertNull(of("hello"));
   }


   @Test
   public void testUpperUnderscoreToUpperCamel()
   {
      assertEquals("HelloKitty", UPPER_UNDERSCORE.to(UPPER_CAMEL, "HELLO_KITTY"));
   }

   @Test
   public void testUpperUnderscoreToLowerCamel()
   {
      assertEquals("helloKitty", UPPER_UNDERSCORE.to(LOWER_CAMEL, "HELLO_KITTY"));
   }

   @Test
   public void testUpperUnderscoreToLowerUnderscore()
   {
      assertEquals("hello_kitty", UPPER_UNDERSCORE.to(LOWER_UNDERSCORE, "HELLO_KITTY"));
   }

   @Test
   public void testUpperUnderscoreToLowerHyphen()
   {
      assertEquals("hello-kitty", UPPER_UNDERSCORE.to(LOWER_HYPHEN, "HELLO_KITTY"));
   }



   @Test
   public void testUpperCamelToUpperUnderscore()
   {
      assertEquals("HELLO_KITTY", UPPER_CAMEL.to(UPPER_UNDERSCORE, "HelloKitty"));
   }

   @Test
   public void testUpperCamelToLowerCamel()
   {
      assertEquals("helloKitty", UPPER_CAMEL.to(LOWER_CAMEL, "HelloKitty"));
   }

   @Test
   public void testUpperCamelToLowerUnderscore()
   {
      assertEquals("hello_kitty", UPPER_CAMEL.to(LOWER_UNDERSCORE, "HelloKitty"));
   }

   @Test
   public void testUpperCamelToLowerHyphen()
   {
      assertEquals("hello-kitty", UPPER_CAMEL.to(LOWER_HYPHEN, "HelloKitty"));
   }



   @Test
   public void testLowerCamelToUpperUnderscore()
   {
      assertEquals("HELLO_KITTY", LOWER_CAMEL.to(UPPER_UNDERSCORE, "helloKitty"));
   }

   @Test
   public void testLowerCamelToUpperCamel()
   {
      assertEquals("HelloKitty", LOWER_CAMEL.to(UPPER_CAMEL, "helloKitty"));
   }

   @Test
   public void testLowerCamelToLowerUnderscore()
   {
      assertEquals("hello_kitty", LOWER_CAMEL.to(LOWER_UNDERSCORE, "helloKitty"));
   }

   @Test
   public void testLowerCamelToLowerHyphen()
   {
      assertEquals("hello-kitty", LOWER_CAMEL.to(LOWER_HYPHEN, "helloKitty"));
   }




   @Test
   public void testLowerHyphenToUpperUnderscore()
   {
      assertEquals("HELLO_KITTY", LOWER_HYPHEN.to(UPPER_UNDERSCORE, "hello-kitty"));
   }

   @Test
   public void testLowerHyphenToUpperCamel()
   {
      assertEquals("HelloKitty", LOWER_HYPHEN.to(UPPER_CAMEL, "hello-kitty"));
   }

   @Test
   public void testLowerHyphenToLowerCamel()
   {
      assertEquals("helloKitty", LOWER_HYPHEN.to(LOWER_CAMEL, "hello-kitty"));
   }

   @Test
   public void testLowerHyphenToLowerUnderscore()
   {
      assertEquals("hello_kitty", LOWER_HYPHEN.to(LOWER_UNDERSCORE, "hello-kitty"));
   }




   @Test
   public void testLowerUnderscoreToUpperUnderscore()
   {
      assertEquals("HELLO_KITTY", LOWER_UNDERSCORE.to(UPPER_UNDERSCORE, "hello_kitty"));
   }

   @Test
   public void testLowerUnderscoreToUpperCamel()
   {
      assertEquals("HelloKitty", LOWER_UNDERSCORE.to(UPPER_CAMEL, "hello_kitty"));
   }

   @Test
   public void testLowerUnderscoreToLowerCamel()
   {
      assertEquals("helloKitty", LOWER_UNDERSCORE.to(LOWER_CAMEL, "hello_kitty"));
   }

   @Test
   public void testLowerUnderscoreToLowerHyphen()
   {
      assertEquals("hello-kitty", LOWER_UNDERSCORE.to(LOWER_HYPHEN, "hello_kitty"));
   }



}