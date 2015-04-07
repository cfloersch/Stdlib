/**
 * Created By: cfloersch
 * Date: 7/15/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import xpertss.lang.Objects;
import xpertss.lang.SyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A configuration object represents an immutable set of properties.
 */
@SuppressWarnings("UnusedDeclaration")
public class Config {


   protected final Properties properties;


   /**
    * Create a new <code>Config</code>.
    */
   public Config()
   {
      properties = new Properties();
   }

   /**
    * Create a new <code>Config</code>.
    */
   public Config(Properties properties)
   {
      this.properties = new Properties();
      this.properties.putAll(properties);
   }





   /**
    * Returns the specified property.
    */
   public String getProperty(String key)
   {
      return properties.getProperty(key);
   }

   /**
    * Returns a property.
    */
   public String getProperty(String name, String def)
   {
      String prop = getProperty(name);
      return (prop == null) ? def : prop;
   }


   /**
    * Assert that the given property is not {@code null}.  If it is,
    * a {@link NullPointerException} is thrown.
    */
   public String getNotNullProperty(String name)
   {
      String prop = getProperty(name);
      if(prop == null) {
         throw new NullPointerException("Property '" + name + "' is not set");
      }
      return prop;
   }




   /**
    * Returns the specified property as an <code>int</code>.
    *
    * @throws NullPointerException  if the property is not defined
    * @throws NumberFormatException if the property is not a numeric
    */
   public int getInt(String key)
   {
      return Integer.parseInt(getNotNullProperty(key).trim());
   }

   /**
    * Returns the given property as an <code>int</code> or the specified
    * default if there was an issue accessing the specified value.
    */
   public int getInt(String key, int def)
   {
      String prop = getProperty(key);
      try {
         return Integer.parseInt(prop.trim());
      } catch(Exception e) {
         return def;
      }
   }



   /**
    * Returns the specified property as an <code>long</code>.
    *
    * @throws NullPointerException  if the property is not defined
    * @throws NumberFormatException if the property is not a numeric
    */
   public long getLong(String key)
   {
      return Long.parseLong(getNotNullProperty(key).trim());
   }

   /**
    * Returns the given property as a <code>long</code> or the specified
    * default if there was an issue accessing the specified value.
    */
   public long getLong(String key, long def)
   {
      String prop = getProperty(key);
      try {
         return Long.parseLong(prop.trim());
      } catch(Exception e) {
         return def;
      }
   }



   /**
    * Returns the specified property as an <code>double</code>.
    *
    * @throws NullPointerException  if the property is not defined
    * @throws NumberFormatException if the property is not a numeric
    */
   public double getDouble(String key)
   {
      return Double.parseDouble(getNotNullProperty(key).trim());
   }

   /**
    * Returns the given property as an <code>double</code> or the specified
    * default if there was an issue accessing the specified value.
    */
   public double getDouble(String key, double def)
   {
      String prop = getProperty(key);
      try {
         return Double.valueOf(prop.trim());
      } catch(Exception e) {
         return def;
      }
   }



   /**
    * Returns the specified property as a <code>boolean</code> or the default
    * if the property does not exist or is not true or false.
    */
   public boolean getBoolean(String name, boolean def)
   {
      if(getProperty(name) == null) return def;
      if(def) return !getProperty(name).equals("false");
      return getProperty(name).equals("true");
   }



   /**
    * Returns an array of string values.  Given a property name this will split
    * the property value into parts based on commas and/or spaces and return the
    * parts as an array.
    *
    * @throws NullPointerException if the property identified by name does
    *    not exist
    */
   public String[] getStrings(String name)
   {
      return getNotNullProperty(name).split("\\s*(,|\\s)\\s*");
   }

   /**
    * Returns an array of string values or a default set if the property does
    * not exist.  Given a property name this will split the property value into
    * parts based on commas and/or spaces and return the parts as an array.
    */
   public String[] getStrings(String name, String ... def)
   {
      String property = getProperty(name);
      return (property == null) ? def : property.split("\\s*(,|\\s)\\s*");
   }



   /**
    * Return a new Config which represents a subset of the properties of
    * this Config identified by the property name prefix. The resulting
    * config will include property names without the prefix.
    * <pre>
    *    server.harvester.host.address=10.0.0.1
    *    server.harvester.host.allowed=eth0,eth1
    *    server.harvester.stun.address=162.43.8.122
    *    server.harvester.stun.port=5328
    *
    *    Config sub = main.subConfig("server.harvester.host");
    *    String address = sub.getProperty("address")
    *    String[] allowed = sub.getStrings("allowed");
    * </pre>
    *
    * @param prefix The property name prefix to include in the sub config
    * @return A config object with the included properties minus their name
    *    prefix.
    */
   public Config subConfig(String prefix)
   {
      prefix = prefix + ".";
      Properties props = new Properties();
      for(String propName : properties.stringPropertyNames()) {
         if(propName.startsWith(prefix)) {
            props.setProperty(propName.substring(prefix.length()),
                              properties.getProperty(propName));
         }
      }
      return new Config(props);
   }



   /**
    * Returns a new Config object which includes the properties of this config
    * overlaid with the specified properties.
    * <p/>
    * A common model is to load some sort of application properties and then
    * overlay it with system properties from the command line.
    *
    * @param properties The properties set to overlay our current properties
    * @return A new config object with the product of the overlay
    * @throws NullPointerException if the overlay properties are {@code null}
    */
   public Config overlayWith(Properties properties)
   {
      Properties props = new Properties(this.properties);
      props.putAll(Objects.notNull(properties));
      return new Config(props);
   }


   /**
    * This will return a new Config object in which all of the properties
    * within this Config are fully resolved.   This means that property
    * values of the form ${property.name} will be replaced by any defined
    * property value that matches property.name.
    */
   public Config resolve()
   {
      Properties copy = new Properties();
      for(String propName : properties.stringPropertyNames()) {
         String value = properties.getProperty(propName);
         copy.put(propName, resolve(properties, value));
      }
      return new Config(copy);
   }


   /**
    * Create and return a copy of the current configuration in {@link Map} form.
    */
   public Map<String,String> toMap()
   {
      Map<String,String> map = new LinkedHashMap<>();
      for(String propName : properties.stringPropertyNames()) {
         map.put(propName, properties.getProperty(propName));
      }
      return map;
   }




   /**
    * Utility method to load a specified configuration.
    * <p/>
    * If overlay is {@code true} then the system properties will be overlaid over the
    * loaded configuration.
    *
    * @param url The configuration properties file to load
    * @param system if {@code true}, overlay the configuration with system properties.
    * @return the newly loaded configuration
    * @throws ResourceNotFoundException If the given {@code url} cannot be opened
    * @throws SyntaxException If the configuration could not be parsed
    */
   public static Config load(URL url, boolean system)
   {
      Properties props = new Properties();

      try {
         try (InputStream input = open(url)) {
            props.load(new InputStreamReader(input, "UTF-8"));
         }
      } catch (IOException e) {
         throw new SyntaxException(e);
      }
      if(system) props.putAll(System.getProperties());
      return new Config(props);
   }

   /**
    * Utility method to load a specified configuration.
    * <p/>
    * If overlay is {@code true} then the system properties will be overlaid over the
    * loaded configuration.
    * <p/>
    * This will attempt to resolve the {@code ctxUrlStr} against the most appropriate
    * class loader for the current operating environment.
    *
    * @param ctxUrlStr The configuration properties file to load
    * @param overlay if {@code true}, overlay the configuration with system properties.
    * @return the newly loaded configuration
    * @throws ResourceNotFoundException If the given {@code ctxUrlStr} cannot be opened
    * @throws SyntaxException If the configuration could not be parsed
    */
   public static Config load(String ctxUrlStr, boolean overlay)
   {
      return load(ResourceLoader.getResource(ctxUrlStr), overlay);
   }




   private static final Pattern pattern = Pattern.compile("\\$\\{([\\w.\\-_]+)\\}");

   public static String resolve(Properties props, String msg)
   {
      StringBuilder builder = new StringBuilder();
      Matcher matcher = pattern.matcher(msg);
      int last = 0;
      while(matcher.find()) {
         builder.append(msg.substring(last, matcher.start()));
         String value = props.getProperty(matcher.group(1));
         if(value != null) builder.append(value);
         else builder.append(matcher.group());
         last = matcher.end();
      }
      builder.append(msg.substring(last));
      return builder.toString();
   }


   private static InputStream open(URL url)
   {
      try { return url.openStream(); } catch(Exception e) { throw new ResourceNotFoundException(e); }
   }

}
