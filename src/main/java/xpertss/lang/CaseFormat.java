/**
 * Copyright 2016 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 5/27/2016
 */
package xpertss.lang;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for converting between various case formats.
 */
public enum CaseFormat {


   /**
    * Hyphenated variable naming convention, e.g., "lower-hyphen".
    */
   LOWER_HYPHEN() {
      String create(String[] parts)
      {
         StringBuilder builder = new StringBuilder();
         for(String part : parts) {
            if(builder.length() > 0) builder.append("-");
            builder.append(part);
         }
         return builder.toString();
      }
      boolean matches(String str)
      {
         boolean foundHyphen = false;
         for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(Character.isUpperCase(c)) return false;
            if(c == '_') return false;
            if(c == '-') foundHyphen = true;
         }
         return foundHyphen;
      }
      String[] parts(String str)
      {
         return str.toLowerCase().split("-");
      }
   },

   /**
    * C++ variable naming convention, e.g., "lower_underscore".
    */
   LOWER_UNDERSCORE() {
      String create(String[] parts)
      {
         StringBuilder builder = new StringBuilder();
         for(String part : parts) {
            if(builder.length() > 0) builder.append("_");
            builder.append(part);
         }
         return builder.toString();
      }
      boolean matches(String str)
      {
         boolean foundUnderscore = false;
         for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(Character.isUpperCase(c)) return false;
            if(c == '-') return false;
            if(c == '_') foundUnderscore = true;
         }
         return foundUnderscore;
      }
      String[] parts(String str)
      {
         return str.toLowerCase().split("_");
      }
   },

   /**
    * Java variable naming convention, e.g., "lowerCamel".
    */
   LOWER_CAMEL() {
      String create(String[] parts)
      {
         StringBuilder builder = new StringBuilder();
         for(String part : parts) {
            if(builder.length() > 0) {
               builder.append(Strings.firstCharToUpper(part));
            } else {
               builder.append(part);
            }
         }
         return builder.toString();
      }
      boolean matches(String str)
      {
         boolean firstIsLower = false;
         boolean foundUpper = false;
         for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(i == 0 && Character.isLowerCase(c)) firstIsLower = true;
            if(Character.isUpperCase(c)) foundUpper = true;
            if(c == '-') return false;
            if(c == '_') return false;
         }
         return firstIsLower && foundUpper;
      }
   },

   /**
    * Java and C++ class naming convention, e.g., "UpperCamel".
    */
   UPPER_CAMEL() {
      String create(String[] parts)
      {
         StringBuilder builder = new StringBuilder();
         for(String part : parts) {
            builder.append(Strings.firstCharToUpper(part));
         }
         return builder.toString();
      }
      boolean matches(String str)
      {
         boolean firstIsUpper = false;
         boolean foundUpper = false;
         boolean foundLower = false;
         for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(i == 0 && Character.isUpperCase(c)) firstIsUpper = true;
            if(Character.isUpperCase(c)) foundUpper = true;
            if(Character.isLowerCase(c)) foundLower = true;
            if(c == '-') return false;
            if(c == '_') return false;
         }
         return firstIsUpper && foundUpper && foundLower;
      }
   },

   /**
    * Java and C++ constant naming convention, e.g., "UPPER_UNDERSCORE".
    */
   UPPER_UNDERSCORE() {
      String create(String[] parts)
      {
         StringBuilder builder = new StringBuilder();
         for(String part : parts) {
            if(builder.length() > 0) builder.append("_");
            builder.append(part.toUpperCase());
         }
         return builder.toString();
      }
      boolean matches(String str)
      {
         boolean foundUnderscore = false;
         for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(Character.isLowerCase(c)) return false;
            if(c == '-') return false;
            if(c == '_') foundUnderscore = true;
         }
         return foundUnderscore;
      }
      String[] parts(String str)
      {
         return str.toLowerCase().split("_");
      }
   };







   /**
    * Converts the specified {@code String str} from this format to the specified {@code format}. A
    * "best effort" approach is taken; if {@code str} does not conform to the assumed format, then
    * the behavior of this method is undefined but we make a reasonable effort at converting anyway.
    */
   public final String to(CaseFormat format, String str)
   {
      return (format == this) ? str : format.create(parts(str));
   }


   /**
    * Attempts to determine the case of the string. This will return the CaseFormat for the
    * string if it can be determined, or {@code null} otherwise.
    */
   public static CaseFormat of(String str)
   {
      for(CaseFormat format : values()) {
         if(format.matches(str)) return format;
      }
      return null;
   }



   String create(String[] parts)
   {
      return null;
   }

   boolean matches(String str)
   {
      return false;
   }

   String[] parts(String str)
   {
      StringBuilder builder = new StringBuilder();
      List<String> parts = new ArrayList<>();
      for(int i = 0; i < str.length(); i++) {
         char c = str.charAt(i);
         if(Character.isUpperCase(c)) {
            parts.add(builder.toString().toLowerCase());
            builder.setLength(0);
         }
         builder.append(c);
      }
      parts.add(builder.toString().toLowerCase());
      return parts.toArray(new String[parts.size()]);
   }




}
