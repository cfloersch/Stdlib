/*
 * Copyright 2020 XpertSoftware
 *
 * Created By: cfloersch
 * Date: 11/4/2020
 */
package xpertss.util;

import java.util.regex.Matcher;

public final class Matchers {

   private Matchers() { }

   public static String[] groups(Matcher matcher)
   {
      String[] result = new String[matcher.groupCount()];
      for(int i = 0; i < result.length; i++) {
         result[i] = matcher.group(i+1);
      }
      return result;
   }

}
