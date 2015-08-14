package xpertss.time;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Support duration parameters that may specify the source time unit.
 * <pre>
 *    Duration:: 1d = 24h = 1440m = 86400s
 * </pre>
 * This api supports source units of seconds (s), minutes (m), hours (h), and
 * days (d).
 */
public final class Duration {

   private static final Pattern pattern = Pattern.compile("(\\d+)([smhd]{0,1})");

   /**
    * Parse the given string duration into a value of the specified unit. If the
    * source duration includes unit information the value will be converted to
    * the specified time unit. Otherwise, the value is assumed to be in the
    * specified time unit.
    *
    * @param str The source duration
    * @param unit The target time unit the source should be converted to
    * @return The duration converted to the specified unit
    * @throws IllegalArgumentException if the specified string is invalid or negative
    * @throws NullPointerException if teh specified unit or string are {@code null}
    */
   public static long parse(String str, TimeUnit unit)
   {
      //if(str == null) return 0L;
      Matcher matcher = pattern.matcher(str);
      if(matcher.matches()) {
         long value = Long.parseLong(matcher.group(1));
         switch(matcher.group(2)) {
            case "s":
               return unit.convert(value, TimeUnit.SECONDS);
            case "m":
               return unit.convert(value, TimeUnit.MINUTES);
            case "h":
               return unit.convert(value, TimeUnit.HOURS);
            case "d":
               return unit.convert(value, TimeUnit.DAYS);
            default:
               return value;
         }
      }
      throw new IllegalArgumentException("invalid time expression: " + str);
   }


}
