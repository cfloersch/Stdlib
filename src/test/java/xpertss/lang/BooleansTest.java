/**
 * Created By: cfloersch
 * Date: 1/7/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class BooleansTest {


   @Test
   public void testParse()
   {
      assertTrue(Booleans.parse("ON"));
      assertTrue(Booleans.parse(new StringBuilder("ON")));

      assertTrue(Booleans.parse("On"));
      assertTrue(Booleans.parse(new StringBuilder("On")));

      assertTrue(Booleans.parse("on"));
      assertTrue(Booleans.parse(new StringBuilder("on")));

      assertTrue(Booleans.parse("oN"));
      assertTrue(Booleans.parse(new StringBuilder("oN")));

      assertTrue(Booleans.parse("YES"));
      assertTrue(Booleans.parse(new StringBuilder("YES")));

      assertTrue(Booleans.parse("YEs"));
      assertTrue(Booleans.parse(new StringBuilder("YEs")));

      assertTrue(Booleans.parse("yES"));
      assertTrue(Booleans.parse("YeS"));
      assertTrue(Booleans.parse("yEs"));
      assertTrue(Booleans.parse("Yes"));
      assertTrue(Booleans.parse(new StringBuilder("Yes")));
      assertTrue(Booleans.parse("yes"));
      assertTrue(Booleans.parse(new StringBuilder("yes")));

      assertTrue(Booleans.parse("Y"));
      assertTrue(Booleans.parse(new StringBuilder("Y")));
      assertTrue(Booleans.parse("y"));
      assertTrue(Booleans.parse(new StringBuilder("y")));

      assertTrue(Booleans.parse("TRUE"));
      assertTrue(Booleans.parse(new StringBuilder("TRUE")))
      ;
      assertTrue(Booleans.parse("TRUe"));
      assertTrue(Booleans.parse(new StringBuilder("TRUe")));

      assertTrue(Booleans.parse("TRue"));
      assertTrue(Booleans.parse("True"));
      assertTrue(Booleans.parse(new StringBuilder("True")));
      assertTrue(Booleans.parse("TrUE"));
      assertTrue(Booleans.parse("TRuE"));
      assertTrue(Booleans.parse("true"));
      assertTrue(Booleans.parse(new StringBuilder("true")));

      assertTrue(Booleans.parse("T"));
      assertTrue(Booleans.parse(new StringBuilder("T")));
      assertTrue(Booleans.parse("t"));
      assertTrue(Booleans.parse(new StringBuilder("t")));

      assertTrue(Booleans.parse("1"));
      assertTrue(Booleans.parse(new StringBuilder("1")));

      assertFalse(Booleans.parse("0"));
      assertFalse(Booleans.parse("False"));
      assertFalse(Booleans.parse("false"));
      assertFalse(Booleans.parse("No"));
      assertFalse(Booleans.parse("no"));
      assertFalse(Booleans.parse("OFF"));
      assertFalse(Booleans.parse("Off"));
      assertFalse(Booleans.parse("F"));
      assertFalse(Booleans.parse("N"));
      assertFalse(Booleans.parse(""));
      assertFalse(Booleans.parse(null));
   }

}
