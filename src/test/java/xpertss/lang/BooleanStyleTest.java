package xpertss.lang;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import static xpertss.lang.BooleanStyle.*;

/**
 * Created By: cfloersch
 * Date: 1/7/14
 * Copyright 2013 XpertSoftware
 */
public class BooleanStyleTest  {

   // Test True False Variants


   @Test
   public void testTrueFalseTitleParse()
   {
      assertTrue(TrueFalseTitleCase.parse("True"));
      assertFalse(TrueFalseTitleCase.parse("true"));
      assertFalse(TrueFalseTitleCase.parse("t"));
      assertFalse(TrueFalseTitleCase.parse("T"));
      assertFalse(TrueFalseTitleCase.parse("TRUE"));
      assertFalse(TrueFalseTitleCase.parse("false"));
      assertFalse(TrueFalseTitleCase.parse("ON"));
      assertFalse(TrueFalseTitleCase.parse("On"));
      assertFalse(TrueFalseTitleCase.parse("on"));
      assertFalse(TrueFalseTitleCase.parse("YES"));
      assertFalse(TrueFalseTitleCase.parse("Yes"));
      assertFalse(TrueFalseTitleCase.parse("yes"));
      assertFalse(TrueFalseTitleCase.parse("Y"));
      assertFalse(TrueFalseTitleCase.parse("y"));
      assertFalse(TrueFalseTitleCase.parse("xyz"));
      assertFalse(TrueFalseTitleCase.parse(""));
      assertFalse(TrueFalseTitleCase.parse(null));
   }

   @Test
   public void testTrueFalseTitleString()
   {
      assertEquals("True", TrueFalseTitleCase.toString(true));
      assertEquals("False", TrueFalseTitleCase.toString(false));
   }


   @Test
   public void testTrueFalseLowerParse()
   {
      assertTrue(TrueFalseLowerCase.parse("true"));
      assertFalse(TrueFalseLowerCase.parse("True"));
      assertFalse(TrueFalseLowerCase.parse("t"));
      assertFalse(TrueFalseLowerCase.parse("T"));
      assertFalse(TrueFalseLowerCase.parse("TRUE"));
      assertFalse(TrueFalseLowerCase.parse("false"));
      assertFalse(TrueFalseLowerCase.parse("ON"));
      assertFalse(TrueFalseLowerCase.parse("On"));
      assertFalse(TrueFalseLowerCase.parse("on"));
      assertFalse(TrueFalseLowerCase.parse("YES"));
      assertFalse(TrueFalseLowerCase.parse("Yes"));
      assertFalse(TrueFalseLowerCase.parse("yes"));
      assertFalse(TrueFalseLowerCase.parse("Y"));
      assertFalse(TrueFalseLowerCase.parse("y"));
      assertFalse(TrueFalseLowerCase.parse("xyz"));
      assertFalse(TrueFalseLowerCase.parse(""));
      assertFalse(TrueFalseLowerCase.parse(null));
   }

   @Test
   public void testTrueFalseLowerString()
   {
      assertEquals("true", TrueFalseLowerCase.toString(true));
      assertEquals("false", TrueFalseLowerCase.toString(false));
   }


   @Test
   public void testTrueFalseUpperParse()
   {
      assertTrue(TrueFalseUpperCase.parse("TRUE"));
      assertFalse(TrueFalseUpperCase.parse("True"));
      assertFalse(TrueFalseUpperCase.parse("T"));
      assertFalse(TrueFalseUpperCase.parse("t"));
      assertFalse(TrueFalseUpperCase.parse("true"));
      assertFalse(TrueFalseUpperCase.parse("false"));
      assertFalse(TrueFalseUpperCase.parse("ON"));
      assertFalse(TrueFalseUpperCase.parse("On"));
      assertFalse(TrueFalseUpperCase.parse("on"));
      assertFalse(TrueFalseUpperCase.parse("YES"));
      assertFalse(TrueFalseUpperCase.parse("Yes"));
      assertFalse(TrueFalseUpperCase.parse("yes"));
      assertFalse(TrueFalseUpperCase.parse("Y"));
      assertFalse(TrueFalseUpperCase.parse("y"));
      assertFalse(TrueFalseUpperCase.parse("xyz"));
      assertFalse(TrueFalseUpperCase.parse(""));
      assertFalse(TrueFalseUpperCase.parse(null));
   }

   @Test
   public void testTrueFalseUpperString()
   {
      assertEquals("TRUE", TrueFalseUpperCase.toString(true));
      assertEquals("FALSE", TrueFalseUpperCase.toString(false));
   }


   @Test
   public void testTrueFalseUpperCharParse()
   {
      assertTrue(TrueFalseUpperChar.parse("T"));
      assertFalse(TrueFalseUpperChar.parse("t"));
      assertFalse(TrueFalseUpperChar.parse("True"));
      assertFalse(TrueFalseUpperChar.parse("TRUE"));
      assertFalse(TrueFalseUpperChar.parse("true"));
      assertFalse(TrueFalseUpperChar.parse("false"));
      assertFalse(TrueFalseUpperChar.parse("ON"));
      assertFalse(TrueFalseUpperChar.parse("On"));
      assertFalse(TrueFalseUpperChar.parse("on"));
      assertFalse(TrueFalseUpperChar.parse("YES"));
      assertFalse(TrueFalseUpperChar.parse("Yes"));
      assertFalse(TrueFalseUpperChar.parse("yes"));
      assertFalse(TrueFalseUpperChar.parse("Y"));
      assertFalse(TrueFalseUpperChar.parse("y"));
      assertFalse(TrueFalseUpperChar.parse("xyz"));
      assertFalse(TrueFalseUpperChar.parse(""));
      assertFalse(TrueFalseUpperChar.parse(null));
   }

   @Test
   public void testTrueFalseUpperCharString()
   {
      assertEquals("T", TrueFalseUpperChar.toString(true));
      assertEquals("F", TrueFalseUpperChar.toString(false));
   }


   @Test
   public void testTrueFalseLowerCharParse()
   {
      assertTrue(TrueFalseLowerChar.parse("t"));
      assertFalse(TrueFalseLowerChar.parse("T"));
      assertFalse(TrueFalseLowerChar.parse("True"));
      assertFalse(TrueFalseLowerChar.parse("TRUE"));
      assertFalse(TrueFalseLowerChar.parse("true"));
      assertFalse(TrueFalseLowerChar.parse("false"));
      assertFalse(TrueFalseLowerChar.parse("ON"));
      assertFalse(TrueFalseLowerChar.parse("On"));
      assertFalse(TrueFalseLowerChar.parse("on"));
      assertFalse(TrueFalseLowerChar.parse("YES"));
      assertFalse(TrueFalseLowerChar.parse("Yes"));
      assertFalse(TrueFalseLowerChar.parse("yes"));
      assertFalse(TrueFalseLowerChar.parse("Y"));
      assertFalse(TrueFalseLowerChar.parse("y"));
      assertFalse(TrueFalseLowerChar.parse("xyz"));
      assertFalse(TrueFalseLowerChar.parse(""));
      assertFalse(TrueFalseLowerChar.parse(null));
   }

   @Test
   public void testTrueFalseLowerCharString()
   {
      assertEquals("t", TrueFalseLowerChar.toString(true));
      assertEquals("f", TrueFalseLowerChar.toString(false));
   }


   @Test
   public void testTrueFalseIgnoreParse()
   {
      assertTrue(TrueFalseIgnoreCase.parse("true"));
      assertTrue(TrueFalseIgnoreCase.parse("TRUE"));
      assertTrue(TrueFalseIgnoreCase.parse("True"));
      assertTrue(TrueFalseIgnoreCase.parse("tRue"));
      assertTrue(TrueFalseIgnoreCase.parse("trUe"));
      assertTrue(TrueFalseIgnoreCase.parse("truE"));
      assertTrue(TrueFalseIgnoreCase.parse("tRUe"));
      assertTrue(TrueFalseIgnoreCase.parse("trUE"));
      assertTrue(TrueFalseIgnoreCase.parse("TrUE"));
      assertTrue(TrueFalseIgnoreCase.parse("TruE"));
      assertTrue(TrueFalseIgnoreCase.parse("TRuE"));
      assertTrue(TrueFalseIgnoreCase.parse("TRue"));

      assertFalse(TrueFalseIgnoreCase.parse("T"));
      assertFalse(TrueFalseIgnoreCase.parse("false"));
      assertFalse(TrueFalseIgnoreCase.parse("ON"));
      assertFalse(TrueFalseIgnoreCase.parse("On"));
      assertFalse(TrueFalseIgnoreCase.parse("on"));
      assertFalse(TrueFalseIgnoreCase.parse("YES"));
      assertFalse(TrueFalseIgnoreCase.parse("Yes"));
      assertFalse(TrueFalseIgnoreCase.parse("yes"));
      assertFalse(TrueFalseIgnoreCase.parse("Y"));
      assertFalse(TrueFalseIgnoreCase.parse("y"));
      assertFalse(TrueFalseIgnoreCase.parse("xyz"));
      assertFalse(TrueFalseIgnoreCase.parse(""));
      assertFalse(TrueFalseIgnoreCase.parse(null));
   }

   @Test
   public void testTrueFalseIgnoreString()
   {
      assertEquals("true", TrueFalseIgnoreCase.toString(true));
      assertEquals("false", TrueFalseIgnoreCase.toString(false));
   }


   @Test
   public void testTrueFalseAnyCharParse()
   {
      assertTrue(TrueFalseAnyChar.parse("t"));
      assertTrue(TrueFalseAnyChar.parse("T"));

      assertFalse(TrueFalseAnyChar.parse("y"));
      assertFalse(TrueFalseAnyChar.parse("1"));
      assertFalse(TrueFalseAnyChar.parse("True"));
      assertFalse(TrueFalseAnyChar.parse("TRUE"));
      assertFalse(TrueFalseAnyChar.parse("true"));
      assertFalse(TrueFalseAnyChar.parse("false"));
      assertFalse(TrueFalseAnyChar.parse("ON"));
      assertFalse(TrueFalseAnyChar.parse("On"));
      assertFalse(TrueFalseAnyChar.parse("on"));
      assertFalse(TrueFalseAnyChar.parse("YES"));
      assertFalse(TrueFalseAnyChar.parse("Yes"));
      assertFalse(TrueFalseAnyChar.parse("yes"));
      assertFalse(TrueFalseAnyChar.parse("Y"));
      assertFalse(TrueFalseAnyChar.parse("y"));
      assertFalse(TrueFalseAnyChar.parse("xyz"));
      assertFalse(TrueFalseAnyChar.parse(""));
      assertFalse(TrueFalseAnyChar.parse(null));
   }

   @Test
   public void testTrueFalseAnyCharString()
   {
      assertEquals("t", TrueFalseAnyChar.toString(true));
      assertEquals("f", TrueFalseAnyChar.toString(false));
   }








   // Test Yes No Variants

   @Test
   public void testYesNoTitleParse()
   {
      assertTrue(YesNoTitleCase.parse("Yes"));
      assertFalse(YesNoTitleCase.parse("YES"));
      assertFalse(YesNoTitleCase.parse("yes"));
      assertFalse(YesNoTitleCase.parse("Y"));
      assertFalse(YesNoTitleCase.parse("y"));
      assertFalse(YesNoTitleCase.parse("TRUE"));
      assertFalse(YesNoTitleCase.parse("True"));
      assertFalse(YesNoTitleCase.parse("true"));
      assertFalse(YesNoTitleCase.parse("T"));
      assertFalse(YesNoTitleCase.parse("t"));
      assertFalse(YesNoTitleCase.parse("false"));
      assertFalse(YesNoTitleCase.parse("ON"));
      assertFalse(YesNoTitleCase.parse("On"));
      assertFalse(YesNoTitleCase.parse("on"));
      assertFalse(YesNoTitleCase.parse("xyz"));
      assertFalse(YesNoTitleCase.parse(""));
      assertFalse(YesNoTitleCase.parse(null));
   }

   @Test
   public void testYesNoTitleString()
   {
      assertEquals("Yes", YesNoTitleCase.toString(true));
      assertEquals("No", YesNoTitleCase.toString(false));
   }


   @Test
   public void testYesNoLowerParse()
   {
      assertTrue(YesNoLowerCase.parse("yes"));

      assertFalse(YesNoLowerCase.parse("YES"));
      assertFalse(YesNoLowerCase.parse("Yes"));
      assertFalse(YesNoLowerCase.parse("Y"));
      assertFalse(YesNoLowerCase.parse("y"));

      assertFalse(YesNoLowerCase.parse("TRUE"));
      assertFalse(YesNoLowerCase.parse("True"));
      assertFalse(YesNoLowerCase.parse("true"));
      assertFalse(YesNoLowerCase.parse("T"));
      assertFalse(YesNoLowerCase.parse("t"));
      assertFalse(YesNoLowerCase.parse("false"));
      assertFalse(YesNoLowerCase.parse("ON"));
      assertFalse(YesNoLowerCase.parse("On"));
      assertFalse(YesNoLowerCase.parse("on"));
      assertFalse(YesNoLowerCase.parse("xyz"));
      assertFalse(YesNoLowerCase.parse(""));
      assertFalse(YesNoLowerCase.parse(null));
   }

   @Test
   public void testYesNoLowerString()
   {
      assertEquals("yes", YesNoLowerCase.toString(true));
      assertEquals("no", YesNoLowerCase.toString(false));
   }


   @Test
   public void testYesNoUpperParse()
   {
      assertTrue(YesNoUpperCase.parse("YES"));

      assertFalse(YesNoUpperCase.parse("Yes"));
      assertFalse(YesNoUpperCase.parse("yes"));
      assertFalse(YesNoUpperCase.parse("Y"));
      assertFalse(YesNoUpperCase.parse("y"));

      assertFalse(YesNoUpperCase.parse("TRUE"));
      assertFalse(YesNoUpperCase.parse("True"));
      assertFalse(YesNoUpperCase.parse("T"));
      assertFalse(YesNoUpperCase.parse("t"));
      assertFalse(YesNoUpperCase.parse("true"));
      assertFalse(YesNoUpperCase.parse("false"));
      assertFalse(YesNoUpperCase.parse("ON"));
      assertFalse(YesNoUpperCase.parse("On"));
      assertFalse(YesNoUpperCase.parse("on"));
      assertFalse(YesNoUpperCase.parse("xyz"));
      assertFalse(YesNoUpperCase.parse(""));
      assertFalse(YesNoUpperCase.parse(null));
   }

   @Test
   public void testYesNoUpperString()
   {
      assertEquals("YES", YesNoUpperCase.toString(true));
      assertEquals("NO", YesNoUpperCase.toString(false));
   }


   @Test
   public void testYesNoUpperCharParse()
   {
      assertTrue(YesNoUpperChar.parse("Y"));

      assertFalse(YesNoUpperChar.parse("YES"));
      assertFalse(YesNoUpperChar.parse("Yes"));
      assertFalse(YesNoUpperChar.parse("yes"));
      assertFalse(YesNoUpperChar.parse("y"));

      assertFalse(YesNoUpperChar.parse("T"));
      assertFalse(YesNoUpperChar.parse("t"));
      assertFalse(YesNoUpperChar.parse("TRUE"));
      assertFalse(YesNoUpperChar.parse("True"));
      assertFalse(YesNoUpperChar.parse("true"));
      assertFalse(YesNoUpperChar.parse("false"));
      assertFalse(YesNoUpperChar.parse("ON"));
      assertFalse(YesNoUpperChar.parse("On"));
      assertFalse(YesNoUpperChar.parse("on"));
      assertFalse(YesNoUpperChar.parse("xyz"));
      assertFalse(YesNoUpperChar.parse(""));
      assertFalse(YesNoUpperChar.parse(null));
   }

   @Test
   public void testYesNoUpperCharString()
   {
      assertEquals("Y", YesNoUpperChar.toString(true));
      assertEquals("N", YesNoUpperChar.toString(false));
   }


   @Test
   public void testYesNoLowerCharParse()
   {
      assertTrue(YesNoLowerChar.parse("y"));

      assertFalse(YesNoLowerChar.parse("YES"));
      assertFalse(YesNoLowerChar.parse("Yes"));
      assertFalse(YesNoLowerChar.parse("yes"));
      assertFalse(YesNoLowerChar.parse("Y"));

      assertFalse(YesNoLowerChar.parse("T"));
      assertFalse(YesNoLowerChar.parse("t"));
      assertFalse(YesNoLowerChar.parse("True"));
      assertFalse(YesNoLowerChar.parse("TRUE"));
      assertFalse(YesNoLowerChar.parse("true"));
      assertFalse(YesNoLowerChar.parse("false"));
      assertFalse(YesNoLowerChar.parse("ON"));
      assertFalse(YesNoLowerChar.parse("On"));
      assertFalse(YesNoLowerChar.parse("on"));
      assertFalse(YesNoLowerChar.parse("xyz"));
      assertFalse(YesNoLowerChar.parse(""));
      assertFalse(YesNoLowerChar.parse(null));
   }

   @Test
   public void testYesNoLowerCharString()
   {
      assertEquals("y", YesNoLowerChar.toString(true));
      assertEquals("n", YesNoLowerChar.toString(false));
   }


   @Test
   public void testYesNoIgnoreParse()
   {
      assertTrue(YesNoIgnoreCase.parse("yes"));
      assertTrue(YesNoIgnoreCase.parse("YES"));
      assertTrue(YesNoIgnoreCase.parse("Yes"));
      assertTrue(YesNoIgnoreCase.parse("YEs"));
      assertTrue(YesNoIgnoreCase.parse("YeS"));
      assertTrue(YesNoIgnoreCase.parse("yES"));
      assertTrue(YesNoIgnoreCase.parse("yeS"));
      assertTrue(YesNoIgnoreCase.parse("yEs"));

      assertFalse(YesNoIgnoreCase.parse("TRUE"));
      assertFalse(YesNoIgnoreCase.parse("True"));
      assertFalse(YesNoIgnoreCase.parse("true"));
      assertFalse(YesNoIgnoreCase.parse("T"));
      assertFalse(YesNoIgnoreCase.parse("t"));
      assertFalse(YesNoIgnoreCase.parse("false"));

      assertFalse(YesNoIgnoreCase.parse("ON"));
      assertFalse(YesNoIgnoreCase.parse("On"));
      assertFalse(YesNoIgnoreCase.parse("on"));
      assertFalse(YesNoIgnoreCase.parse("Y"));
      assertFalse(YesNoIgnoreCase.parse("y"));
      assertFalse(YesNoIgnoreCase.parse("xyz"));
      assertFalse(YesNoIgnoreCase.parse(""));
      assertFalse(YesNoIgnoreCase.parse(null));
   }

   @Test
   public void testYesNoIgnoreString()
   {
      assertEquals("yes", YesNoIgnoreCase.toString(true));
      assertEquals("no", YesNoIgnoreCase.toString(false));
   }


   @Test
   public void testYesNoAnyCharParse()
   {
      assertTrue(YesNoAnyChar.parse("y"));
      assertTrue(YesNoAnyChar.parse("Y"));

      assertFalse(YesNoAnyChar.parse("T"));
      assertFalse(YesNoAnyChar.parse("t"));
      assertFalse(YesNoAnyChar.parse("1"));
      assertFalse(YesNoAnyChar.parse("True"));
      assertFalse(YesNoAnyChar.parse("TRUE"));
      assertFalse(YesNoAnyChar.parse("true"));
      assertFalse(YesNoAnyChar.parse("false"));
      assertFalse(YesNoAnyChar.parse("ON"));
      assertFalse(YesNoAnyChar.parse("On"));
      assertFalse(YesNoAnyChar.parse("on"));
      assertFalse(YesNoAnyChar.parse("YES"));
      assertFalse(YesNoAnyChar.parse("Yes"));
      assertFalse(YesNoAnyChar.parse("yes"));
      assertFalse(YesNoAnyChar.parse("xyz"));
      assertFalse(YesNoAnyChar.parse(""));
      assertFalse(YesNoAnyChar.parse(null));
   }

   @Test
   public void testYesNoAnyCharString()
   {
      assertEquals("y", YesNoAnyChar.toString(true));
      assertEquals("n", YesNoAnyChar.toString(false));
   }











   // Test On Off Variants

   @Test
   public void testOnOffTitleParse()
   {
      assertTrue(OnOffTitleCase.parse("On"));

      assertFalse(OnOffTitleCase.parse("ON"));
      assertFalse(OnOffTitleCase.parse("on"));
      assertFalse(OnOffTitleCase.parse("off"));

      assertFalse(OnOffTitleCase.parse("YES"));
      assertFalse(OnOffTitleCase.parse("Yes"));
      assertFalse(OnOffTitleCase.parse("yes"));
      assertFalse(OnOffTitleCase.parse("Y"));
      assertFalse(OnOffTitleCase.parse("y"));
      assertFalse(OnOffTitleCase.parse("TRUE"));
      assertFalse(OnOffTitleCase.parse("True"));
      assertFalse(OnOffTitleCase.parse("T"));
      assertFalse(OnOffTitleCase.parse("t"));
      assertFalse(OnOffTitleCase.parse("true"));
      assertFalse(OnOffTitleCase.parse("false"));
      assertFalse(OnOffTitleCase.parse("xyz"));
      assertFalse(OnOffTitleCase.parse(""));
      assertFalse(OnOffTitleCase.parse(null));
   }

   @Test
   public void testOnOffTitleString()
   {
      assertEquals("On", OnOffTitleCase.toString(true));
      assertEquals("Off", OnOffTitleCase.toString(false));
   }


   @Test
   public void testOnOffLowerParse()
   {
      assertTrue(OnOffLowerCase.parse("on"));

      assertFalse(OnOffLowerCase.parse("On"));
      assertFalse(OnOffLowerCase.parse("ON"));
      assertFalse(OnOffLowerCase.parse("off"));

      assertFalse(OnOffLowerCase.parse("YES"));
      assertFalse(OnOffLowerCase.parse("Yes"));
      assertFalse(OnOffLowerCase.parse("yes"));
      assertFalse(OnOffLowerCase.parse("Y"));
      assertFalse(OnOffLowerCase.parse("y"));
      assertFalse(OnOffLowerCase.parse("TRUE"));
      assertFalse(OnOffLowerCase.parse("True"));
      assertFalse(OnOffLowerCase.parse("T"));
      assertFalse(OnOffLowerCase.parse("t"));
      assertFalse(OnOffLowerCase.parse("true"));
      assertFalse(OnOffLowerCase.parse("false"));
      assertFalse(OnOffLowerCase.parse("xyz"));
      assertFalse(OnOffLowerCase.parse(""));
      assertFalse(OnOffLowerCase.parse(null));
   }

   @Test
   public void testOnOffLowerString()
   {
      assertEquals("on", OnOffLowerCase.toString(true));
      assertEquals("off", OnOffLowerCase.toString(false));
   }


   @Test
   public void testOnOffUpperParse()
   {
      assertTrue(OnOffUpperCase.parse("ON"));

      assertFalse(OnOffUpperCase.parse("On"));
      assertFalse(OnOffUpperCase.parse("on"));
      assertFalse(OnOffUpperCase.parse("off"));

      assertFalse(OnOffUpperCase.parse("YES"));
      assertFalse(OnOffUpperCase.parse("Yes"));
      assertFalse(OnOffUpperCase.parse("yes"));
      assertFalse(OnOffUpperCase.parse("Y"));
      assertFalse(OnOffUpperCase.parse("y"));
      assertFalse(OnOffUpperCase.parse("TRUE"));
      assertFalse(OnOffUpperCase.parse("True"));
      assertFalse(OnOffUpperCase.parse("T"));
      assertFalse(OnOffUpperCase.parse("t"));
      assertFalse(OnOffUpperCase.parse("true"));
      assertFalse(OnOffUpperCase.parse("false"));
      assertFalse(OnOffUpperCase.parse("xyz"));
      assertFalse(OnOffUpperCase.parse(""));
      assertFalse(OnOffUpperCase.parse(null));
   }

   @Test
   public void testOnOffUpperString()
   {
      assertEquals("ON", OnOffUpperCase.toString(true));
      assertEquals("OFF", OnOffUpperCase.toString(false));
   }




   @Test
   public void testOnOffIgnoreParse()
   {
      assertTrue(OnOffIgnoreCase.parse("ON"));
      assertTrue(OnOffIgnoreCase.parse("On"));
      assertTrue(OnOffIgnoreCase.parse("oN"));
      assertTrue(OnOffIgnoreCase.parse("on"));

      assertFalse(OnOffIgnoreCase.parse("TRUE"));
      assertFalse(OnOffIgnoreCase.parse("True"));
      assertFalse(OnOffIgnoreCase.parse("true"));
      assertFalse(OnOffIgnoreCase.parse("T"));
      assertFalse(OnOffIgnoreCase.parse("t"));
      assertFalse(OnOffIgnoreCase.parse("false"));
      assertFalse(OnOffIgnoreCase.parse("YES"));
      assertFalse(OnOffIgnoreCase.parse("Yes"));
      assertFalse(OnOffIgnoreCase.parse("yes"));
      assertFalse(OnOffIgnoreCase.parse("Y"));
      assertFalse(OnOffIgnoreCase.parse("y"));
      assertFalse(OnOffIgnoreCase.parse("xyz"));
      assertFalse(OnOffIgnoreCase.parse(""));
      assertFalse(OnOffIgnoreCase.parse(null));
   }

   @Test
   public void testOnOffIgnoreString()
   {
      assertEquals("on", OnOffIgnoreCase.toString(true));
      assertEquals("off", OnOffIgnoreCase.toString(false));
   }






   // Test Zero One

   @Test
   public void testZeroOneParse()
   {
      assertTrue(ZeroOne.parse("1"));

      assertFalse(ZeroOne.parse("0"));
      assertFalse(ZeroOne.parse("ON"));
      assertFalse(ZeroOne.parse("On"));
      assertFalse(ZeroOne.parse("on"));
      assertFalse(ZeroOne.parse("off"));
      assertFalse(ZeroOne.parse("YES"));
      assertFalse(ZeroOne.parse("Yes"));
      assertFalse(ZeroOne.parse("yes"));
      assertFalse(ZeroOne.parse("Y"));
      assertFalse(ZeroOne.parse("y"));
      assertFalse(ZeroOne.parse("TRUE"));
      assertFalse(ZeroOne.parse("True"));
      assertFalse(ZeroOne.parse("T"));
      assertFalse(ZeroOne.parse("t"));
      assertFalse(ZeroOne.parse("true"));
      assertFalse(ZeroOne.parse("false"));
      assertFalse(ZeroOne.parse("xyz"));
      assertFalse(ZeroOne.parse(""));
      assertFalse(ZeroOne.parse(null));
   }

   @Test
   public void testZeroOneString()
   {
      assertEquals("1", ZeroOne.toString(true));
      assertEquals("0", ZeroOne.toString(false));
   }



   // Test static methods

   @Test
   public void testParse()
   {
      assertTrue(BooleanStyle.parse("on", TrueFalseLowerCase, OnOffLowerCase));
      assertTrue(BooleanStyle.parse("true", TrueFalseLowerCase, OnOffLowerCase));
      assertFalse(BooleanStyle.parse("On", TrueFalseLowerCase, OnOffLowerCase));
      assertFalse(BooleanStyle.parse("TRUE", TrueFalseLowerCase, OnOffLowerCase));
      assertFalse(BooleanStyle.parse(null, TrueFalseLowerCase, OnOffLowerCase));
      assertFalse(BooleanStyle.parse("On", null));
   }
}
