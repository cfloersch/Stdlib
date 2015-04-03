package xpertss.lang;

/**
 * Enumeration of various styles used to encode boolean values into strings.
 * <p/>
 * {@link Booleans#parse(String)} is very lenient when it comes to what it will
 * allow as input. The parse methods defined below are much more strict, requiring
 * the supplied string to conform to the style.
 * <p/>
 * All implementations of {@link #parse(String)} are null-safe.
 */
public enum BooleanStyle {

   /**
    * Parses <b>True</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>True</b> for {@code true} and <b>False</b> for {@code false}
    */
   TrueFalseTitleCase {
      public boolean parse(String str)
      {
         return "True".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "True" : "False";
      }
   },
   /**
    * Parses <b>true</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>true</b> for {@code true} and <b>false</b> for {@code false}
    */
   TrueFalseLowerCase {
      public boolean parse(String str)
      {
         return "true".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "true" : "false";
      }
   },
   /**
    * Parses <b>TRUE</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>TRUE</b> for {@code true} and <b>FALSE</b> for {@code false}
    */
   TrueFalseUpperCase {
      public boolean parse(String str)
      {
         return "TRUE".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "TRUE" : "FALSE";
      }
   },
   /**
    * Parses any case of <b>true</b> into {@code true}, everything else into
    * {@code false}.
    * <p/>
    * Uses <b>true</b> for {@code true} and <b>false</b> for {@code false}
    */
   TrueFalseIgnoreCase {
      public boolean parse(String str)
      {
         return "true".equalsIgnoreCase(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "true" : "false";
      }
   },
   /**
    * Parses <b>T</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>T</b> for {@code true} and <b>F</b> for {@code false}
    */
   TrueFalseUpperChar {
      public boolean parse(String str)
      {
         return "T".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "T" : "F";
      }
   },
   /**
    * Parses <b>t</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>t</b> for {@code true} and <b>f</b> for {@code false}
    */
   TrueFalseLowerChar {
      public boolean parse(String str)
      {
         return "t".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "t" : "f";
      }
   },
   /**
    * Parses <b>T</b> or <b>t</b> into {@code true}, everything else into
    * {@code false}.
    * <p/>
    * Uses <b>t</b> for {@code true} and <b>f</b> for {@code false}
    */
   TrueFalseAnyChar {
      public boolean parse(String str)
      {
         return "t".equalsIgnoreCase(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "t" : "f";
      }
   },

   /**
    * Parses <b>On</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>On</b> for {@code true} and <b>Off</b> for {@code false}
    */
   OnOffTitleCase {
      public boolean parse(String str)
      {
         return "On".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "On" : "Off";
      }
   },
   /**
    * Parses <b>on</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>on</b> for {@code true} and <b>off</b> for {@code false}
    */
   OnOffLowerCase {
      public boolean parse(String str)
      {
         return "on".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "on" : "off";
      }
   },
   /**
    * Parses <b>ON</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>ON</b> for {@code true} and <b>OFF</b> for {@code false}
    */
   OnOffUpperCase {
      public boolean parse(String str)
      {
         return "ON".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "ON" : "OFF";
      }
   },
   /**
    * Parses <b>On</b>, <b>ON</b>, <b>on</b>, or <b>oN</b> into {@code true},
    * everything else into {@code false}.
    * <p/>
    * Uses <b>on</b> for {@code true} and <b>off</b> for {@code false}
    */
   OnOffIgnoreCase {
      public boolean parse(String str)
      {
         return "On".equalsIgnoreCase(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "on" : "off";
      }
   },

   /**
    * Parses <b>Yes</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>Yes</b> for {@code true} and <b>No</b> for {@code false}
    */
   YesNoTitleCase {
      public boolean parse(String str)
      {
         return "Yes".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "Yes" : "No";
      }
   },
   /**
    * Parses <b>yes</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>yes</b> for {@code true} and <b>no</b> for {@code false}
    */
   YesNoLowerCase {
      public boolean parse(String str)
      {
         return "yes".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "yes" : "no";
      }
   },
   /**
    * Parses <b>YES</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>YES</b> for {@code true} and <b>NO</b> for {@code false}
    */
   YesNoUpperCase {
      public boolean parse(String str)
      {
         return "YES".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "YES" : "NO";
      }
   },
   /**
    * Parses any case of <b>Yes</b> into {@code true}, everything else into
    * {@code false}.
    * <p/>
    * Uses <b>yes</b> for {@code true} and <b>no</b> for {@code false}
    */
   YesNoIgnoreCase {
      public boolean parse(String str)
      {
         return "Yes".equalsIgnoreCase(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "yes" : "no";
      }
   },
   /**
    * Parses <b>Y</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>Y</b> for {@code true} and <b>N</b> for {@code false}
    */
   YesNoUpperChar {
      public boolean parse(String str)
      {
         return "Y".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "Y" : "N";
      }
   },
   /**
    * Parses <b>y</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>y</b> for {@code true} and <b>n</b> for {@code false}
    */
   YesNoLowerChar {
      public boolean parse(String str)
      {
         return "y".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "y" : "n";
      }
   },
   /**
    * Parses <b>Y</b> or <b>y</b> into {@code true}, everything else into
    * {@code false}.
    * <p/>
    * Uses <b>y</b> for {@code true} and <b>n</b> for {@code false}
    */
   YesNoAnyChar {
      public boolean parse(String str)
      {
         return "Y".equalsIgnoreCase(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "y" : "n";
      }
   },

   /**
    * Parses <b>1</b> into {@code true}, everything else into {@code false}.
    * <p/>
    * Uses <b>1</b> for {@code true} and <b>0</b> for {@code false}
    */
   ZeroOne {
      public boolean parse(String str)
      {
         return "1".equals(str);
      }

      public String toString(boolean value)
      {
         return (value) ? "1" : "0";
      }
   };




   public boolean parse(String str)
   {
      return "true".equals(str);
   }

   public String toString(boolean value)
   {
      return (value) ? "true" : "false";
   }

   /**
    * Utility method to parse a boolean string using multiple boolean styles.
    * <p/>
    * This method is null-safe.
    *
    * @param str The possibly {@code null} string to parse
    * @param styles The set of styles to use to parse the string
    * @return {@code true} if any one of the styles returns {@code true}
    */
   public static boolean parse(String str, BooleanStyle ... styles)
   {
      if(styles != null && !Strings.isEmpty(str)) {
         for(BooleanStyle style : styles) {
            if(style.parse(str)) return true;
         }
      }
      return false;
   }

}
