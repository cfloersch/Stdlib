package xpertss.text;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import java.text.DecimalFormat;

import java.text.Format;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;


/**
 * <code>MapMessageFormat</code> provides a means to produce concatenated
 * messages in language-neutral way. Use this to construct messages
 * displayed for end users.
 *
 * <p>
 * <code>MapMessageFormat</code> takes a Map of objects, formats them, then
 * inserts the formatted strings into the pattern at the appropriate places.
 *
 * <p>
 * <strong>Note:</strong>
 * <code>MessageFormat</code> differs from the other <code>Format</code>
 * classes in that you create a <code>MapMessageFormat</code> object with one
 * of its constructors (not with a <code>getInstance</code> style factory
 * method). The factory methods aren't necessary because <code>MapMessageFormat</code>
 * doesn't require any complex setup for a given locale. In fact,
 * <code>MapMessageFormat</code> doesn't implement any locale specific behavior
 * at all. It just needs to be set up on a sentence by sentence basis.
 * <p>
 * Locale is used by sub Format objects such as SimpleDateFormat and NumberFormat
 *
 * <p>
 * Here are some examples of usage:
 * <blockquote>
 * <pre>
 * Map args = new HashMap();
 * Object[] arguments = {
 * args.put("planet", new Integer(7));
 * args.put("when", new Date());
 * args.put("msg", "a disturbance in the Force");
 *
 * String result = MapMessageFormat.format("At {when,time} on {when,date}, there was {msg} on planet {planet,number,integer}.", args);
 *
 * <em>output</em>: At 12:30 PM on Jul 3, 2053, there was a disturbance
 *           in the Force on planet 7.
 *
 * </pre>
 * </blockquote>
 * Typically, the message format will come from resources, and the arguments will be
 * dynamically set at runtime.
 *
 * <p>
 * Example 2:
 * <blockquote>
 * <pre>
 * Map testArgs = new HashMap();
 * testArgs.put("num", new Long(3));
 * testArgs.put("name", "MyDisk");
 *
 * MapMessageFormat form = new MapMessageFormat("The disk \"{name}\" contains {num} file(s).");
 *
 * System.out.println(form.format(testArgs));
 *
 * // output, with different testArgs
 * <em>output</em>: The disk "MyDisk" contains 0 file(s).
 * <em>output</em>: The disk "MyDisk" contains 1 file(s).
 * <em>output</em>: The disk "MyDisk" contains 1,273 file(s).
 * </pre>
 * </blockquote>
 *
 * <p>
 * The pattern is of the form:
 * <blockquote>
 * <pre>
 * MapMessageFormatPattern := string ( "{" MapMessageFormatElement "}" string )*
 *
 * MapMessageFormatElement := argument { "," elementFormat }
 *
 * elementFormat := "time"    { "," datetimeStyle }
 *                | "date"    { "," datetimeStyle }
 *                | "number"  { "," numberStyle }
 *                | "message" { "," messageStyle }
 *
 * datetimeStyle := "short"
 *                  | "medium"
 *                  | "long"
 *                  | "full"
 *                  | dateFormatPattern
 *
 * numberStyle := "currency"
 *               | "percent"
 *               | "integer"
 *               | numberFormatPattern
 *
 * messageStyle := mapMessageFormatPattern
 *
 * </pre>
 * </blockquote>
 * If there is no <code>elementFormat</code>,
 * then the argument must be a string, which is substituted. If there is
 * no <code>dateTimeStyle</code> or <code>numberStyle</code>, then the
 * default format is used (for example, <code>NumberFormat.getInstance</code>,
 * <code>DateFormat.getTimeInstance</code>, or <code>DateFormat.getInstance</code>).
 *
 * <p>
 * In strings, single quotes can be used to quote the "{"
 * (curly brace) if necessary. A real single quote is represented by ''.
 * Inside a <code>MapMessageFormatElement</code>, quotes are <strong>not</strong>
 * removed. For example, {1,number,$'#',##} will produce a number format
 * with the pound-sign quoted, with a result such as: "$#31,45".
 *
 * <p>
 * If a pattern is used, then unquoted braces in the pattern, if any, must match:
 * that is, "ab {name} de" and "ab '}' de" are ok, but "ab {name'}' de" and "ab } de" are
 * not.
 *
 * <p>
 * The argument is a string key which corresponds to an element in the supplied
 * Map.
 *
 * <p>
 * It is ok to have unused arguments in the Map.
 * With missing arguments or arguments that are not of the right class for
 * the specified format, a <code>ParseException</code> is thrown.
 * <p>
 * The argument is formatted based on the object's type. If the argument is
 * a <code>Number</code>, then <code>format</code> uses
 * <code>NumberFormat.getInstance</code> to format the argument; if the
 * argument is a <code>Date</code>, then <code>format</code> uses
 * <code>DateFormat.getDateTimeInstance</code> to format the argument.
 * Otherwise, it uses the <code>toString</code> method.
 *
 * <p>
 * For more sophisticated patterns, you can use a <code>MapMessageFormat</code> to get
 * output such as:
 * <blockquote>
 * <pre>
 * MapMessageFormat form = new MapMessageFormat(
 *    "The disk # {num} contains {name,message,{num_files,number} {label}}.");
 *
 * Map testArgs = new HashMap();
 * testArgs.put("num", new Long(2));
 * Map subArgs = new HashMap();
 * subArgs.put("num_files", new Long(123));
 * subArgs.put("label", "files");
 * testArgs.put("name", subArgs);
 *
 * System.out.println(form.format(testArgs));
 *
 * output: The disk # 2 contains 123 files.
 *
 * </pre>
 * </blockquote>
 * <p>
 * <strong>Note:</strong> As we see above, the string produced
 * by a <code>MapMessageFormat</code> in <code>MapMessageFormat</code> is treated specially;
 * occurances of '{' are used to indicated subformats, and cause recursion.
 * <p>
 * When a single argument is parsed more than once in the string, the last match
 * will be the final result of the parsing.  For example,
 * <pre>
 * MapMessageFormat mf = new MapMessageFormat("{num,number,#.##}, {num,number,#.#}");
 * Map objs = new HashMap();
 * objs.put("num", new Double(3.1415));
 * String result = mf.format( objs );
 * // result now equals "3.14, 3.1"
 * objs = null;
 * objs = mf.parse(result, new ParsePosition(0));
 * // objs now equals new Double(3.1)
 * </pre>
 * <p>
 * Likewise, parsing with a MapMessageFormat object using patterns containing
 * multiple occurances of the same argument would return the last match.  For
 * example,
 * <pre>
 * MapMessageFormat mf = new MapMessageFormat("{pos}, {pos}, {pos}");
 * String forParsing = "x, y, z";
 * Map objs = mf.parse(forParsing, new ParsePosition(0));
 * // result now equals {new String("z")}
 * </pre>
 * <p>
 * TODO figure out a way to loop through lists using a MapMessageFormat for
 * each sub element in the list. Also figure out how to recurse into
 * MessageFormat if map element's value is an array of Objects. Also check to see
 * if format patterns broken across multiple lines of an input file are processed
 * correctly.
 *
 * @version      1.0, 05/23/02
 * @author       Chris Floersch
 */
public class MapMessageFormat extends Format {


   private Locale locale = Locale.getDefault();
   private String pattern = "";

   private static final int MAX_ARGUMENTS = 100;
   private Format[] formats = new Format[MAX_ARGUMENTS];
   private int[] offsets = new int[MAX_ARGUMENTS];
   private String[] argumentKeys = new String[MAX_ARGUMENTS];
   private int maxOffset = -1;


   /**
    * Constructs with the specified pattern.
    * @see xpertss.text.MapMessageFormat#applyPattern
    */
   public MapMessageFormat(String pattern)
   {
      applyPattern(pattern);
   }

   /**
    * Constructs with the specified pattern.
    * @see xpertss.text.MapMessageFormat#applyPattern
    */
   public MapMessageFormat(String pattern, Locale locale)
   {
      this.locale = locale;
      applyPattern(pattern);
   }


   /**
    * Sets the pattern. See the class description.
    */
   public void applyPattern(String newPattern)
   {
      StringBuffer[] segments = new StringBuffer[4];
      for (int i = 0; i < segments.length; ++i) {
         segments[i] = new StringBuffer();
      }
      int part = 0;
      int formatNumber = 0;
      boolean inQuote = false;
      int braceStack = 0;
      maxOffset = -1;
      for (int i = 0; i < newPattern.length(); ++i) {
         char ch = newPattern.charAt(i);
         if (part == 0) {
            if (ch == '\'') {
               if (i + 1 < newPattern.length() && newPattern.charAt(i + 1) == '\'') {
                  segments[part].append(ch);  // handle doubles
                  ++i;
               } else {
                  inQuote = !inQuote;
               }
            } else if (ch == '{' && !inQuote) {
               part = 1;
            } else {
               segments[part].append(ch);
            }
         } else if (inQuote) {              // just copy quotes in parts
            segments[part].append(ch);
            if (ch == '\'') {
               inQuote = false;
            }
         } else {
            switch (ch) {
               case ',':
                  if (part < 3)
                     part += 1;
                  else
                     segments[part].append(ch);
                  break;
               case '{':
                  ++braceStack;
                  segments[part].append(ch);
                  break;
               case '}':
                  if (braceStack == 0) {
                     part = 0;
                     makeFormat(i, formatNumber, segments);
                     formatNumber++;
                  } else {
                     --braceStack;
                     segments[part].append(ch);
                  }
                  break;
               case '\'':
                  inQuote = true;
                  // fall through, so we keep quotes in other parts
               default:
                  segments[part].append(ch);
                  break;
            }
         }
      }
      if (braceStack == 0 && part != 0) {
         maxOffset = -1;
         throw new IllegalArgumentException("Unmatched braces in the pattern.");
      }
      pattern = segments[0].toString();
   }


   /**
    * Gets the pattern. See the class description.
    */
   public String toPattern()
   {
      // later, make this more extensible
      int lastOffset = 0;
      StringBuffer result = new StringBuffer();
      for (int i = 0; i <= maxOffset; ++i) {
         copyAndFixQuotes(pattern, lastOffset, offsets[i], result);
         lastOffset = offsets[i];
         result.append('{');
         result.append(argumentKeys[i]);
         if (formats[i] == null) {
            // do nothing, string format
         } else if (formats[i] instanceof DecimalFormat) {
            if (formats[i].equals(NumberFormat.getInstance(locale))) {
               result.append(",number");
            } else if (formats[i].equals(NumberFormat.getCurrencyInstance(locale))) {
               result.append(",number,currency");
            } else if (formats[i].equals(NumberFormat.getPercentInstance(locale))) {
               result.append(",number,percent");
            } else if (formats[i].equals(getIntegerFormat(locale))) {
               result.append(",number,integer");
            } else {
               result.append(",number," + ((DecimalFormat) formats[i]).toPattern());
            }
         } else if (formats[i] instanceof SimpleDateFormat) {
            if (formats[i].equals(DateFormat.getDateInstance(DateFormat.DEFAULT, locale))) {
               result.append(",date");
            } else if (formats[i].equals(DateFormat.getDateInstance(DateFormat.SHORT, locale))) {
               result.append(",date,short");
            } else if (formats[i].equals(DateFormat.getDateInstance(DateFormat.DEFAULT, locale))) {
               result.append(",date,medium");
            } else if (formats[i].equals(DateFormat.getDateInstance(DateFormat.LONG, locale))) {
               result.append(",date,long");
            } else if (formats[i].equals(DateFormat.getDateInstance(DateFormat.FULL, locale))) {
               result.append(",date,full");
            } else if (formats[i].equals(DateFormat.getTimeInstance(DateFormat.DEFAULT, locale))) {
               result.append(",time");
            } else if (formats[i].equals(DateFormat.getTimeInstance(DateFormat.SHORT, locale))) {
               result.append(",time,short");
            } else if (formats[i].equals(DateFormat.getTimeInstance(DateFormat.DEFAULT, locale))) {
               result.append(",time,medium");
            } else if (formats[i].equals(DateFormat.getTimeInstance(DateFormat.LONG, locale))) {
               result.append(",time,long");
            } else if (formats[i].equals(DateFormat.getTimeInstance(DateFormat.FULL, locale))) {
               result.append(",time,full");
            } else {
               result.append(",date," + ((SimpleDateFormat) formats[i]).toPattern());
            }
         } else if (formats[i] instanceof MapMessageFormat) {
            result.append(",message," + ((MapMessageFormat) formats[i]).toPattern());
         } else {
            //result.append(", unknown");
         }
         result.append('}');
      }
      copyAndFixQuotes(pattern, lastOffset, pattern.length(), result);
      return result.toString();
   }


   /**
    * Returns pattern with formatted objects.  If source is null, the
    * original pattern is returned, if source contains null objects, the
    * formatted result will substitute each argument with the string "null".
    *
    * @param source an array of objects to be formatted &amp; substituted.
    * @param result where text is appended.
    * @param ignore no useful status is returned.
    */
   public final StringBuffer format(Map source, StringBuffer result, FieldPosition ignore)
   {
      return format2(source, result, ignore);
   }


   /**
    * Convenience routine.
    * Avoids explicit creation of MapMessageFormat,
    * but doesn't allow future optimizations.
    */
   public static String format(String pattern, Map arguments)
   {
      MapMessageFormat temp = new MapMessageFormat(pattern);
      return temp.format(arguments);
   }


   /**
    * Convenience routine.
    * Avoids explicit creation of MapMessageFormat,
    * but doesn't allow future optimizations.
    */
   public static String format(String pattern, Map arguments, Locale locale)
   {
      MapMessageFormat temp = new MapMessageFormat(pattern, locale);
      return temp.format(arguments);
   }


   /**
    * Returns pattern with formatted objects.  If source is null, the
    * original pattern is returned, if source contains null objects, the
    * formatted result will substitute each argument with the string "null".
    *
    * @param source an array of objects to be formatted &amp; substituted.
    * @param result where text is appended.
    * @param ignore no useful status is returned.
    */
   public final StringBuffer format(Object source, StringBuffer result, FieldPosition ignore)
   {
      return format2((Map) source, result, ignore);
   }


   /**
    * Parses the string.
    *
    * <p>Caveats: The parse may fail in a number of circumstances.
    * For example:
    * <ul>
    * <li>If one of the arguments does not occur in the pattern.
    * <li>If the format of an argument loses information, such as
    *     with a choice format where a large number formats to "many".
    * <li>Does not yet handle recursion (where
    *     the substituted strings contain {n} references.)
    * <li>Will not always find a match (or the correct match)
    *     if some part of the parse is ambiguous.
    *     For example, if the pattern "{1},{2}" is used with the
    *     string arguments {"a,b", "c"}, it will format as "a,b,c".
    *     When the result is parsed, it will return {"a", "b,c"}.
    * <li>If a single argument is parsed more than once in the string,
    *     then the later parse wins.
    * </ul>
    * When the parse fails, use ParsePosition.getErrorIndex() to find out
    * where in the string did the parsing failed.  The returned error
    * index is the starting offset of the sub-patterns that the string
    * is comparing with.  For example, if the parsing string "AAA {0} BBB"
    * is comparing against the pattern "AAD {0} BBB", the error index is
    * 0. When an error occurs, the call to this method will return null.
    * If the source is null, return an empty array.
    */
   public Map parse(String source, ParsePosition status)
   {
      Map result = new HashMap();
      if (source == null) return result;

      Object[] resultArray = new Object[10];

      int patternOffset = 0;
      int sourceOffset = status.getIndex();
      ParsePosition tempStatus = new ParsePosition(0);
      for (int i = 0; i <= maxOffset; ++i) {
         // match up to format
         int len = offsets[i] - patternOffset;
         if (len == 0 || pattern.regionMatches(patternOffset, source, sourceOffset, len)) {
            sourceOffset += len;
            patternOffset += len;
         } else {
            status.setErrorIndex(sourceOffset);
            return null; // leave index as is to signal error
         }

         // now use format
         if (formats[i] == null) {   // string format
            // if at end, use longest possible match
            // otherwise uses first match to intervening string
            // does NOT recursively try all possibilities
            int tempLength = (i != maxOffset) ? offsets[i + 1] : pattern.length();

            int next;
            if (patternOffset >= tempLength) {
               next = source.length();
            } else {
               next = source.indexOf(pattern.substring(patternOffset, tempLength), sourceOffset);
            }

            if (next < 0) {
               status.setErrorIndex(sourceOffset);
               return null; // leave index as is to signal error
            } else {
               String strValue = source.substring(sourceOffset, next);
               if (!strValue.equals("{" + argumentKeys[i] + "}"))
                  result.put(argumentKeys[i], source.substring(sourceOffset, next));
               sourceOffset = next;
            }
         } else {
            tempStatus.setIndex(sourceOffset);
            result.put(argumentKeys[i], formats[i].parseObject(source, tempStatus));
            if (tempStatus.getIndex() == sourceOffset) {
               status.setErrorIndex(sourceOffset);
               return null; // leave index as is to signal error
            }
            sourceOffset = tempStatus.getIndex(); // update
         }
      }
      int len = pattern.length() - patternOffset;
      if (len == 0 || pattern.regionMatches(patternOffset, source, sourceOffset, len)) {
         status.setIndex(sourceOffset + len);
      } else {
         status.setErrorIndex(sourceOffset);
         return null; // leave index as is to signal error
      }
      return result;
   }


   /**
    * Parses the string. Does not yet handle recursion (where
    * the substituted strings contain {n} references.)
    *
    * @return Map - This is actually a Map
    * @exception java.text.ParseException if the string can't be parsed.
    */
   public Map parse(String source)
      throws ParseException
   {
      ParsePosition status = new ParsePosition(0);
      Map result = parse(source, status);
      if (status.getIndex() == 0)  // unchanged, returned object is null
         throw new ParseException("MapMessageFormat parse error!", status.getErrorIndex());
      return result;
   }


   /**
    * Parses the string. Does not yet handle recursion (where
    * the substituted strings contain %n references.)
    */
   public Object parseObject(String text, ParsePosition status)
   {
      return parse(text, status);
   }


   /**
    * Equality comparision between two message format objects
    */
   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      MapMessageFormat other = (MapMessageFormat) obj;
      return (pattern.equals(other.pattern) && locale.equals(other.locale));
   }


   /**
    * Generates a hash code for the message format object.
    */
   public int hashCode()
   {
      return pattern.hashCode() ^ locale.hashCode();
   }


   /**
    * Internal routine used by format.
    */
   private StringBuffer format2(Map arguments, StringBuffer result, FieldPosition status)
   {
      // note: this implementation assumes a fast substring & index.
      // if this is not true, would be better to append chars one by one.
      int lastOffset = 0;
      for (int i = 0; i <= maxOffset; ++i) {
         result.append(pattern.substring(lastOffset, offsets[i]));
         lastOffset = offsets[i];
         String argumentKey = argumentKeys[i];
         if (arguments == null || arguments.containsKey(argumentKey) == false) {
            result.append("{" + argumentKey + "}");
            continue;
         }
         Object obj = arguments.get(argumentKey);
         String arg;
         boolean tryRecursion = false;
         if (obj == null) {
            arg = "null";
         } else if (formats[i] != null) {
            arg = formats[i].format(obj);
         } else if (obj instanceof Number) {
            // format number if can
            arg = NumberFormat.getInstance(locale).format(obj); // fix
         } else if (obj instanceof Date) {
            // format a Date if can
            arg = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale).format(obj);//fix
         } else if (obj instanceof String) {
            arg = (String) obj;
         } else {
            arg = obj.toString();
            if (arg == null) arg = "null";
         }

         result.append(arg);
      }
      result.append(pattern.substring(lastOffset, pattern.length()));
      return result;
   }


   private static final String[] typeList = {"", "", "number", "", "date", "", "time", "", "message"};
   private static final String[] modifierList = {"", "", "currency", "", "percent", "", "integer"};
   private static final String[] dateModifierList = {"", "", "short", "", "medium", "", "long", "", "full"};


   private void makeFormat(int position, int offsetNumber, StringBuffer[] segments)
   {
      // get the number
      int argumentNumber;
      int oldMaxOffset = maxOffset;
      argumentKeys[offsetNumber] = segments[1].toString();
      maxOffset = offsetNumber;
      offsets[offsetNumber] = segments[0].length();

      // now get the format
      Format newFormat = null;
      switch (findKeyword(segments[2].toString(), typeList)) {
         case 0:
            break;
         case 1:
         case 2:// number
            switch (findKeyword(segments[3].toString(), modifierList)) {
               case 0: // default;
                  newFormat = NumberFormat.getInstance(locale);
                  break;
               case 1:
               case 2:// currency
                  newFormat = NumberFormat.getCurrencyInstance(locale);
                  break;
               case 3:
               case 4:// percent
                  newFormat = NumberFormat.getPercentInstance(locale);
                  break;
               case 5:
               case 6:// integer
                  newFormat = getIntegerFormat(locale);
                  break;
               default: // pattern
                  newFormat = NumberFormat.getInstance(locale);
                  try {
                     ((DecimalFormat) newFormat).applyPattern(segments[3].toString());
                  } catch (Exception e) {
                     maxOffset = oldMaxOffset;
                     throw new IllegalArgumentException("Pattern incorrect or locale does not support formats, error at ");
                  }
                  break;
            }
            break;
         case 3:
         case 4: // date
            switch (findKeyword(segments[3].toString(), dateModifierList)) {
               case 0: // default
                  newFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
                  break;
               case 1:
               case 2: // short
                  newFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
                  break;
               case 3:
               case 4: // medium
                  newFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
                  break;
               case 5:
               case 6: // long
                  newFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
                  break;
               case 7:
               case 8: // full
                  newFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
                  break;
               default:
                  newFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
                  try {
                     ((SimpleDateFormat) newFormat).applyPattern(segments[3].toString());
                  } catch (Exception e) {
                     maxOffset = oldMaxOffset;
                     throw new IllegalArgumentException("Pattern incorrect or locale does not support formats, error at ");
                  }
                  break;
            }
            break;
         case 5:
         case 6:// time
            switch (findKeyword(segments[3].toString(), dateModifierList)) {
               case 0: // default
                  newFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
                  break;
               case 1:
               case 2: // short
                  newFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
                  break;
               case 3:
               case 4: // medium
                  newFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
                  break;
               case 5:
               case 6: // long
                  newFormat = DateFormat.getTimeInstance(DateFormat.LONG, locale);
                  break;
               case 7:
               case 8: // full
                  newFormat = DateFormat.getTimeInstance(DateFormat.FULL, locale);
                  break;
               default:
                  newFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
                  try {
                     ((SimpleDateFormat) newFormat).applyPattern(segments[3].toString());
                  } catch (Exception e) {
                     maxOffset = oldMaxOffset;
                     throw new IllegalArgumentException("Pattern incorrect or locale does not support formats, error at ");
                  }
                  break;
            }
            break;
         case 7:
         case 8:// MapMessage
            try {
               newFormat = new MapMessageFormat(segments[3].toString(), locale);
            } catch (Exception e) {
               maxOffset = oldMaxOffset;
               throw new IllegalArgumentException("MapMessageFormat Pattern incorrect, error at ");
            }
            break;
         default:
            maxOffset = oldMaxOffset;
            throw new IllegalArgumentException("unknown format type at ");
      }
      formats[offsetNumber] = newFormat;
      segments[1].setLength(0);   // throw away other segments
      segments[2].setLength(0);
      segments[3].setLength(0);
   }


   private static final int findKeyword(String s, String[] list)
   {
      s = s.trim().toLowerCase();
      for (int i = 0; i < list.length; ++i) {
         if (s.equals(list[i]))
            return i;
      }
      return -1;
   }

   /**
    * Convenience method that ought to be in NumberFormat
    */
   NumberFormat getIntegerFormat(Locale locale)
   {
      NumberFormat temp = NumberFormat.getInstance(locale);
      if (temp instanceof DecimalFormat) {
         DecimalFormat temp2 = (DecimalFormat) temp;
         temp2.setMaximumFractionDigits(0);
         temp2.setDecimalSeparatorAlwaysShown(false);
         temp2.setParseIntegerOnly(true);
      }
      return temp;
   }


   private static final void copyAndFixQuotes(String source, int start, int end, StringBuffer target)
   {
      for (int i = start; i < end; ++i) {
         char ch = source.charAt(i);
         if (ch == '{') {
            target.append("'{'");
         } else if (ch == '}') {
            target.append("'}'");
         } else if (ch == '\'') {
            target.append("''");
         } else {
            target.append(ch);
         }
      }
   }


}
