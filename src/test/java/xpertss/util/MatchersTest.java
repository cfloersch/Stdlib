package xpertss.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/*
 * Copyright 2020 XpertSoftware
 *
 * Created By: cfloersch
 * Date: 11/4/2020
 */
public class MatchersTest {

   Pattern pattern = Pattern.compile("^.*insightcr.*coid=(\\d*).*won=(\\d*).*lc=([A-Z0-9]{3,5})$");

   
   @Test
   public void testPositiveOne()
   {
      assertTrue(matches("//insightcr.manheim.com/cr-display?coid=18705537&won=4444651&lc=FAAO", "18705537", "4444651", "FAAO"));
   }

   @Test
   public void testPositiveTwo()
   {
      assertTrue(matches("//insightcr.manheim.com/cr-display?coid=1&won=23423&lc=MP45", "1", "23423", "MP45"));
   }

   @Test
   public void testNegativeNoMatch()
   {
      assertFalse(matches("//mabel.manheim.com/cr-display?coid=1&won=23423&lc=MP45", "1", "23423", "MP45"));
   }

   @Test
   public void testNegativeNotNumber()
   {
      assertFalse(matches("//insightcr.manheim.com/cr-display?coid=abc&won=23423&lc=MP45", "abc", "23423", "MP45"));
   }

   @Test
   public void testNegativeNotLocation()
   {
      assertFalse(matches("//insightcr.manheim.com/cr-display?coid=1&won=23423&lc=hello", "1", "23423", "hello"));
   }

   private boolean matches(String url, String ... args)
   {
      Matcher m = pattern.matcher(url);
      if(!m.find()) return false;
      return Arrays.equals(args, Matchers.groups(m));
   }


}