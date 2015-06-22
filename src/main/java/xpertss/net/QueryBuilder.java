package xpertss.net;

import xpertss.lang.Strings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Utility method to build url query strings. This class does only rudimentary validation
 * of inputs. It is the callers responsibility to ensure parameter names and values are
 * properly formed and encoded.
 *
 * User: cfloersch
 * Date: 12/12/12
 */
public class QueryBuilder {

   private List<String> params = new ArrayList<>();

   private QueryBuilder() { }


   /**
    * Clear all the current parameters from this builder.
    */
   public QueryBuilder clear()
   {
      params.clear();
      return this;
   }

   /**
    * Returns the number of parameters in this query
    */
   public int size()
   {
      return params.size();
   }


   /**
    * Get an array of parameter values for the given parameter name.
    */
   public String[] get(String name)
   {
      List<String> result = new ArrayList<>();
      if(name != null) {
         for(String param : params) {
            if(param.startsWith(name + "=")) {
               String[] parts = param.split("=");
               if(parts.length > 2) {
                  result.add(Strings.join("=", parts, 1, parts.length - 1));
               } else if(parts.length == 2) {
                  result.add(parts[1]);
               } else if(parts.length == 1) {
                  result.add("");
               }
            }
         }
      }
      return result.toArray(new String[result.size()]);
   }

   /**
    * Remove all parameter values from this builder with the given name
    */
   public QueryBuilder remove(String name)
   {
      if(name != null) {
         for(Iterator<String> it = params.iterator(); it.hasNext(); ) {
            String param = it.next();
            if(param.startsWith(name + "=")) it.remove();
         }
      }
      return this;
   }

   /**
    * Add the given named paramter to this builder.
    *
    * @throws IllegalArgumentException If the specified name is empty
    */
   public QueryBuilder add(String name, String value)
   {
      params.add(Strings.notEmpty(name, "name can't be empty") + "=" + Strings.emptyIfNull(value));
      return this;
   }

   /**
    * Replaces all existing parameters with the given name with the specified
    * value mapping.
    *
    * @throws IllegalArgumentException If the specified name is empty
    */
   public QueryBuilder set(String name, String value)
   {
      name = Strings.notEmpty(name, "name can't be empty");
      for(Iterator<String> it = params.iterator(); it.hasNext(); ) {
         String param = it.next();
         if(param.startsWith(name + "=")) it.remove();
      }
      params.add(name + "=" + Strings.emptyIfNull(value));
      return this;
   }



   /**
    * Set the builder's query to contain the name value pairs specified clearing any
    * existing parameters that may be set.
    * <p>
    * The given arguments represent name1, value1, name2, value2, ... nameN, valueN
    * <p>
    * The input parameters must be passed in pairs or an IllegalArgumentException
    * will be thrown. Additionally, this method accepts empty values but it does
    * not accept empty keys.
    */
   public QueryBuilder setAll(String... nameValuePairs)
   {
      if(nameValuePairs.length % 2 != 0) throw new IllegalArgumentException("uneven input arguments");
      params.clear();
      for(int i = 0; i < nameValuePairs.length; i+=2) {
         add(nameValuePairs[i], nameValuePairs[i+1]);
      }
      return this;
   }



   /**
    * Parse the given raw query string and set this builder's current parameter state
    * to match.
    */
   public QueryBuilder setQuery(String query)
   {
      params.clear();
      for(String pair : Strings.emptyIfNull(query).split("&")) {
         if(!Strings.isEmpty(pair)) params.add(pair);
      }
      return this;
   }



   /**
    * Construct a query string suitable for use with UrlBuilder, URI, and URL.
    */
   public String build()
   {
      StringBuilder buf = new StringBuilder();
      for(String pair : params) {
         if(buf.length() > 0) buf.append("&");
         buf.append(pair);
      }
      return buf.toString();
   }



   /**
    * Create a QueryBuilder with no preexisting parameters defined.
    */
   public static QueryBuilder create()
   {
      return new QueryBuilder();
   }

   /**
    * Create a QueryBuilder and preseet the parameter set to those parsed from
    * the specified raw query string.
    */
   public static QueryBuilder create(String query)
   {
      return new QueryBuilder().setQuery(query);
   }

}
