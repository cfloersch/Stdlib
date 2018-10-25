package xpertss.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * A simple service-provider loading facility.
 * <p>
 * A service is a well-known set of interfaces and (usually abstract) classes. A
 * service provider is a specific implementation of a service. The classes in a
 * provider typically implement the interfaces and subclass the classes defined
 * in the service itself. Service providers can be installed in an implementation
 * of the Java platform in the form of extensions, that is, jar files placed into
 * any of the usual extension directories. Providers can also be made available
 * by adding them to the application's class path or by some other
 * platform-specific means.
 * <p>
 * For the purpose of loading, a service is represented by a single type, that is,
 * a single interface or abstract class. (A concrete class can be used, but this
 * is not recommended.) A provider of a given service contains one or more concrete
 * classes that extend this service type with data and code specific to the provider.
 * The provider class is typically not the entire provider itself but rather a proxy
 * which contains enough information to decide whether the provider is able to
 * satisfy a particular request together with code that can create the actual provider
 * on demand. The details of provider classes tend to be highly service-specific; no
 * single class or interface could possibly unify them, so no such type is defined
 * here. The only requirement enforced by this facility is that provider classes must
 * have a zero-argument constructor so that they can be instantiated during loading.
 * <p>
 * A service provider is identified by placing a provider-configuration file in the
 * resource directory META-INF/services. The file's name is the fully-qualified binary
 * name of the service's type. The file contains a list of fully-qualified binary names
 * of concrete provider classes, one per line. Space and tab characters surrounding each
 * name, as well as blank lines, are ignored. The comment character is '#' ('\u0023',
 * NUMBER SIGN); on each line all characters following the first comment character are
 * ignored. The file must be encoded in UTF-8.
 * <p>
 * If a particular concrete provider class is named in more than one configuration file,
 * or is named in the same configuration file more than once, then the duplicates are
 * ignored. The configuration file naming a particular provider need not be in the same
 * jar file or other distribution unit as the provider itself. The provider must be
 * accessible from the same class loader that was initially queried to locate the
 * configuration file; note that this is not necessarily the class loader from which the
 * file was actually loaded.
 * <p>
 * Providers are located and instantiated on construction. A service loader maintains a
 * cache of all providers for a given service. The cache can be cleared and reloaded via
 * the reload method.
 * <p>
 * Service loaders always execute in the security context of the caller. Trusted system
 * code should typically invoke the methods in this class, and the methods of the iterators
 * which they return, from within a privileged security context.
 * <p>
 * Instances of this class are not safe for use by multiple concurrent threads.
 * <p>
 * Unless otherwise specified, passing a null argument to any method in this class will
 * cause a NullPointerException to be thrown.
 */
public class ServiceLoader<S> implements Iterable<S> {

   private static final String PREFIX = "META-INF/services/";

   // The class or interface representing the service being loaded
   private Class<S> service;

   // The class loader used to locate, load, and instantiate providers
   private ClassLoader loader;

   // Cached providers, in instantiation order
   private Set<S> providers;


   private ServiceLoader(Class<S> svc, ClassLoader cl)
   {
      service = svc;
      loader = cl;
      reload();
   }


   /**
    * Clear this loader's provider cache and reload it.
    * <p>
    * This method is intended for use in situations in which new providers can be
    * installed into a running Java virtual machine.
    */
   public void reload()
   {
      Set<S> list = new LinkedHashSet<S>();
      String fullName = PREFIX + service.getName();
      try {
         Enumeration<URL> configs = loader.getResources(fullName);
         Set<Class<?>> classes = new HashSet<Class<?>>();
         while(configs.hasMoreElements()) {
            try {
               InputStream in = configs.nextElement().openStream();
               try {
                  for(String clsName : parse(in)) {
                     try {
                        Class<?> clazz = loader.loadClass(clsName);
                        if(!classes.contains(clazz) && service.isAssignableFrom(clazz)) {
                           list.add(service.cast(clazz.newInstance()));
                           classes.add(clazz);
                        }
                     } catch(Exception e) { }
                  }
               } finally {
                  if(in != null) in.close();
               }
            } catch(Exception iex) { }
         }
      } catch(Exception ex) { }
      providers = Collections.unmodifiableSet(list);
   }
   
   
   private List<String> parse(InputStream in)
      throws IOException
   {
      List<String> classes = new ArrayList<String>();
      BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
      String line;
      while((line = br.readLine()) != null) {
         if(line.contains("#")) {
            line = line.substring(0, line.indexOf("#")).trim();
         }
         if(line.length() > 0) classes.add(line);
      }
      return classes;
   }
   

   /**
    * An iterator over the loaded service providers.
    * <p>
    * The iterator returned by this method does not support removal. Invoking its
    * {@link java.util.Iterator#remove() remove} method will cause an {@link
    * UnsupportedOperationException} to be thrown.
    * 
    * @return An iterator for this loader's service providers
    */
   public Iterator<S> iterator()
   {
      return providers.iterator();
   }

   /**
    * Returns a string describing this service.
    * 
    * @return A descriptive string
    */
   public String toString()
   {
      return "ServiceLoader[" + service.getName() + "]";
   }

   
   
   
   
   /**
    * Creates a new service loader for the given service type and class loader.
    * 
    * @param service The interface or abstract class representing the service
    * @param loader The class loader to be used to load provider-configuration
    *               files and provider classes, or <tt>null</tt> if the system
    *               class loader (or, failing that, the bootstrap class loader)
    *               is to be used
    * @return A new service loader
    */
   public static <S> ServiceLoader<S> load(Class<S> service, ClassLoader loader)
   {
      if(loader == null) loader = ClassLoader.getSystemClassLoader();
      return new ServiceLoader<S>(service, loader);
   }

   /**
    * Creates a new service loader for the given service type, using the current
    * thread's {@linkplain Thread#getContextClassLoader context class loader}.
    *
    * @param service The interface or abstract class representing the service
    * @return A new service loader
    */
   public static <S> ServiceLoader<S> load(Class<S> service)
   {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      return ServiceLoader.load(service, cl);
   }

}
