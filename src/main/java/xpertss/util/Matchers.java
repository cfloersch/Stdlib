/*
 * Copyright 2020 XpertSoftware
 *
 * Created By: cfloersch
 * Date: 11/4/2020
 */
package xpertss.util;

import java.util.regex.Matcher;

/**
 * Simple Regex Matcher utility functions.
 */
public final class Matchers {

   private Matchers() { }

   /**
    * Converts the groups associated with the given matcher into an array
    * excluding group zero.
    *
    * @param matcher The matcher from which groups are pulled.
    * @return An array of group values excluding group 0
    */
   public static String[] groups(Matcher matcher)
   {
      String[] result = new String[matcher.groupCount()];
      for(int i = 0; i < result.length; i++) {
         result[i] = matcher.group(i+1);
      }
      return result;
   }

}
