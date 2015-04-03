/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 17, 2002
 * Time: 9:13:35 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package xpertss.text;


import xpertss.lang.Strings;

import java.util.Properties;

import static xpertss.lang.Objects.ifNull;

/**
 * Format utility that will inline Java Properties into a pattern string.
 * <p/>
 * This Format class will allow you to quickly process Property embedded string such
 * as "${java.io.tmpdir}output.log" into a string where "${java.io.tmpdir}" is
 * replaced by the property value named "java.io.tmpdir". This is convenient in
 * handling dependant and embedded property files and configuration files.
 */
public class PropertyFormat {


   private Properties props;

   /**
    * Construct a PropertyFormat with the given Properties object. If the supplied
    * Properties object is null then System Properties are used by default.
    */
   public PropertyFormat(Properties props)
   {
      this.props = ifNull(props, System.getProperties());
   }

   /**
    * Pass a String that looks like "${java.io.tmpdir}output.log"  that you desire
    * to be returned with the variable replaced by the value in the supplied
    * Properties.
    */
   public String format(String msg)
   {
      if (Strings.isEmpty(msg)) return msg;
      StringBuilder buf = new StringBuilder();
      int pos = 0;
      int last = 0;
      while ((pos = msg.indexOf("${", pos)) != -1) {
         buf.append(msg.substring(last, pos));
         last = pos;
         pos = msg.indexOf("}", pos);
         if (pos != -1) {
            String key = msg.substring(last + 2, pos++);
            String val = props.getProperty(key);
            if (val != null) buf.append(val);
            last = pos;
         } else {
            pos = last;
         }
      }
      buf.append(msg.substring(last));
      return (buf.length() == 0) ? msg : buf.toString();
   }


   /**
    * Convenience method to handle quick formats of strings. If props is null
    * then the System Properties is used by default.
    */
   public static String format(Properties props, String pattern)
   {
      PropertyFormat format = new PropertyFormat(props);
      return format.format(pattern);
   }

}
