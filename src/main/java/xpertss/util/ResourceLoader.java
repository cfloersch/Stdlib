package xpertss.util;

import xpertss.lang.Classes;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;


/**
 * A static API that can be used as a drop-in replacement for java.lang.ClassLoader 
 * API (the class/resource loading part). This implementation attempts to determine
 * the most appropriate class loader to use for the given caller. This typically,
 * implies choosing between the current class loader and the context class loader
 * associated with the calling thread.
 * <p>
 * Example of its need. Suppose you create a service provider that is loaded by a
 * web container's class loader. Your service provider needs to be capable of loading
 * a configuration from a web application that is loaded in a child class loader. To
 * do this you will likely need to use the context class loader. This utility aims to
 * eliminate the need for you to care or worry which class loader to use by solving
 * that for you.
 *
 */
public abstract class ResourceLoader {


   /**
    * @see ClassLoader#loadClass(String)
    */
   public static Class<?> loadClass(final String name)
      throws ClassNotFoundException
   {
      final ClassLoader loader = Classes.resolveClassLoader();
      return Class.forName(name, false, loader);
   }

   /**
    * @see ClassLoader#getResource(String)
    */
   public static URL getResource(final String name)
   {
      final ClassLoader loader = Classes.resolveClassLoader();
      if (loader != null) return loader.getResource(name);
      return ClassLoader.getSystemResource(name);
   }

   /**
    * @see ClassLoader#getResourceAsStream(String)
    */
   public static InputStream getResourceAsStream(final String name)
   {
      final ClassLoader loader = Classes.resolveClassLoader();
      if (loader != null)return loader.getResourceAsStream(name);
      return ClassLoader.getSystemResourceAsStream(name);
   }

   /**
    * @see ClassLoader#getResources(String)
    */
   public static Enumeration<URL> getResources(final String name) 
      throws IOException
   {
      final ClassLoader loader = Classes.resolveClassLoader();
      if (loader != null)return loader.getResources(name);
      return ClassLoader.getSystemResources(name);
   }


   private ResourceLoader()
   {
   }


}
