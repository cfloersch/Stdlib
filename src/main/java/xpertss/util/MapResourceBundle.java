package xpertss.util;

import xpertss.lang.Objects;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * <code>MapResourceBundle</code> is a subclass of {@link ResourceBundle} that
 * manages resources for a locale in a convenient and easy to use map. See
 * <code>ResourceBundle</code> for more information about resource bundles in
 * general.
 * <p>
 * Unlike the default resource bundles shipped with the JDK this resource
 * bundle operates on a general data type which may be supplied by any number
 * of control implementations. The ones shipped with the JDK not only define
 * a structure they also define a file encoding and are tightly bound.
 * <p>
 * This bundle could easily be used with a control which parses the map data
 * from a properties file, or some other custom file format including java
 * serialized data, json data, or xml data.
 */
public class MapResourceBundle extends ResourceBundle {

   private Map<String,?> resources;

   /**
    * Construct a {@code MapResourceBundle} from a given map that utilizes string
    * based keys.
    *
    * @param resources The underlying map of resource data
    * @throws NullPointerException if the supplied resource map is {@code null}
    */
   public MapResourceBundle(Map<String,?> resources)
   {
      this.resources = Objects.notNull(resources);
   }



   @Override
   public Enumeration<String> getKeys()
   {
      ResourceBundle parent = this.parent;
      return new ResourceBundleEnumeration(resources.keySet(),
            (parent != null) ? parent.getKeys() : null);
   }

   @Override
   protected Object handleGetObject(String key)
   {
      return resources.get(key);
   }

   @Override
   protected Set<String> handleKeySet()
   {
      return resources.keySet();
   }


   private class ResourceBundleEnumeration implements Enumeration<String> {

      Set<String> set;
      Iterator<String> iterator;
      Enumeration<String> enumeration; // may remain null

      ResourceBundleEnumeration(Set<String> set, Enumeration<String> enumeration)
      {
         this.set = set;
         this.iterator = set.iterator();
         this.enumeration = enumeration;
      }

      String next = null;

      public boolean hasMoreElements()
      {
         if (next == null) {
            if (iterator.hasNext()) {
               next = iterator.next();
            } else if (enumeration != null) {
               while (next == null && enumeration.hasMoreElements()) {
                  next = enumeration.nextElement();
                  if (set.contains(next)) {
                     next = null;
                  }
               }
            }
         }
         return next != null;
      }

      public String nextElement()
      {
         if (hasMoreElements()) {
            String result = next;
            next = null;
            return result;
         } else {
            throw new NoSuchElementException();
         }
      }
   }


}
