package xpertss.lang;

import xpertss.function.Consumers;
import xpertss.util.Sets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.function.Consumer;

import static xpertss.lang.Objects.isOneOf;

/**
 * A set of utility methods for {@link Class <code>Class</code>es} and class names.
 */
@SuppressWarnings("UnusedDeclaration")
public final class Classes {

   private Classes() { }


   /**
    * Convert a Class into a Class[] Array. This is useful for using in Reflection
    * operations where the method signature arguments are defined as an array and
    * its such a pain in the butt to create an array of one all the time over and
    * over again. If the supplied argument is null this will return a zero length
    * array.
    */
   public static Class<?>[] toArray(Class<?> ... cls)
   {
      // This works because it is a concrete type. Had it been generic it would have failed
      return cls;
   }

   /**
    * Returns an empty array if the specified data array is {@code null}, otherwise
    * it returns the data array.

    * @param data The data array
    * @return the data array if not {@code null} or a zero length array
    */
   public static Class<?>[] emptyIfNull(Class<?>[] data)
   {
      return (data == null) ? new Class<?>[0] : data;
   }





   /**
    * This takes a Class object that is an array type and returns a String
    * representation of that array as if you saw it in a source file.
    */
   public static String getArrayClassType(Class arrayClass)
   {
      StringBuilder array = new StringBuilder();
      String type;
      for (type = arrayClass.getName(); type.startsWith("["); type = type.substring(1))
         array.append("[]");

      switch (type.charAt(0)) {
         case 66: // 'B'
            array.insert(0, "byte");
            break;

         case 73: // 'I'
            array.insert(0, "int");
            break;

         case 74: // 'J'
            array.insert(0, "long");
            break;

         case 70: // 'F'
            array.insert(0, "float");
            break;

         case 68: // 'D'
            array.insert(0, "double");
            break;

         case 67: // 'C'
            array.insert(0, "char");
            break;

         case 83: // 'S'
            array.insert(0, "short");
            break;

         case 90: // 'Z'
            array.insert(0, "boolean");
            break;

         case 76: // 'L'
            array.insert(0, type.substring(1, type.length() - 1));
            break;
      }
      return array.toString();
   }




   /**
    * Utility method to get a field inside of the specified class or its superclass
    * without throwing an exception. This will return null if the field is not
    * found.
    */
   public static Field getField(Class cls, String name)
   {
      while (cls != null) {
         try {
            return cls.getDeclaredField(name);
         } catch (NoSuchFieldException noSuch) {
            cls = cls.getSuperclass();
         }
      }
      return null;
   }

   /**
    * Get the specified method from the specified class or one of its super classes
    * without throwing an exception. This will return null if the method is not
    * found.
    */
   public static Method getMethod(Class<?> cls, String name, Class ... parameterTypes)
   {
      while (cls != null) {
         try {
            return cls.getDeclaredMethod(name, parameterTypes);
         } catch (NoSuchMethodException noSuch) {
            cls = cls.getSuperclass();
         }
      }
      return null;
   }

   /**
    * Get the constructor from the specified class that has the specified
    * argument set. This will return null if no constructor with the
    * specified argument set exists or if some exception is thrown trying
    * to access it.
    */
   public static <T> Constructor<T> getConstructor(Class<T> cls, Class ... parameterTypes)
   {
      try {
         return cls.getConstructor(parameterTypes);
      } catch(Exception ex) { return null; }
   }




   /**
    * Create a new instance of the specified class. If any errors occur trying
    * to create this new instance then this method will return null.
    */
   public static <T> T newInstance(Class<T> cls)
   {
      try {
         return cls.newInstance();
      } catch(Exception ex) { return null; }
   }


   /**
    * Construct a new instance of a class using the specified constructor and with the
    * specified initArguments. If any exception is encountered trying to fulfill this
    * request null will be returned.
    */
   public static <T> T newInstance(Constructor<T> con, Object ... args)
   {
      try {
         return con.newInstance(args);
      } catch(Exception ex) { return null; }
   }


   /**
    * This method returns the specified class as a <code>Class</code> object. If the
    * class can not be loaded using the specified class loader then {@code null} will
    * be returned.
    * <p>
    * The class loader must be specified for this to work in all contexts. In most
    * cases it is <tt>getClass().getClassLoader()</tt>
    *
    * @param loader The class loader to use to load the class.
    * @param clsName The class name as a string to load
    * @return The loaded Class object or null.
    */
   public static Class<?> loadNamedClass(ClassLoader loader, String clsName)
   {
      try {
         return Class.forName(clsName, true, loader);
      } catch (Exception ex) { return null; }
   }




   /**
    * Checks to see if a class is a sub class of another class.
    *
    * @param subClass The class to check
    * @param superClass The class you think sub may be.
    * @return {@code True} if sub is a sub class of sup
    * @throws NullPointerException If either subClass or superClass are {@code null}
    */
   public static boolean isSubclassOf(Class subClass, Class superClass)
   {
      Class sup;
      if(Objects.notNull(subClass, "subClass") == Objects.notNull(superClass, "superClass")) return true;
      for(sup = subClass.getSuperclass(); sup != null && sup != superClass;) sup = sup.getSuperclass();
      return sup != null;
   }








   /**
    * Checks if the specified object is an instance of or a subclass of one of the
    * specified types. Returns true if it is, false otherwise.
    *
    * @param obj The object to check ancestry on
    * @param types The types that may be the class' ancestor.
    * @return true if the class is an instance of, or a subclass of one of the
    *          specified types, false otherwise.
    * @throws NullPointerException If {@code types} is {@code null}
    */
   public static boolean isInstanceOf(Object obj, Class<?> ... types)
   {
      if(obj == null) return false;
      for(Class<?> type : Objects.notNull(types)) {
         if(type.isInstance(obj)) return true;
      }
      return false;
   }


   /**
    * Determines if the class or interface represented by the first argument is either
    * the same as, or is a super class or super interface of, the class or interface
    * represented by the second argument. It returns {@code true} if so; otherwise it
    * returns {@code false}.
    * <p>
    * If the first argument represents a primitive type, this method returns {@code true}
    * if the second argument is exactly the same class object; otherwise it returns
    * {@code false}.
    * <p>
    * If either of the arguments is {@code null} then {@code false} will be returned.
    *
    * @param toClass The class to be assigned
    * @param cls The class to be checked
    * @return {@code true} if cls can be assigned to toClass, {@code false} otherwise
    */
   public static boolean isAssignableFrom(Class<?> toClass, Class<?> cls)
   {
      return !(toClass == null || cls == null) && toClass.isAssignableFrom(cls);
      // TODO Maybe be more sophisticated and handle autoboxing/unboxing as well
   }


   /**
    * Null safe method to determine if the specified class is an inner class or static
    * nested class.
    *
    * @param cls  the class to check, may be {@code null}
    * @return {@code true} if the class is an inner or static nested class, {@code false}
    *    if not or {@code null}
    */
   public static boolean isInnerClass(Class<?> cls)
   {
      return cls != null && cls.getEnclosingClass() != null;
   }




   /**
    * Returns the array's component type if the given class is an array type or the
    * given class if not.
    *
    * @param cls The class to inspect
    * @return The component type if the class is an array, otherwise the class itself
    * @throws NullPointerException If the given class is {@code null}
    */
   public static Class<?> getComponentType(Class<?> cls)
   {
      return (cls.isArray()) ? cls.getComponentType() : cls;
   }


   /**
    * Returns a proxy instance that implements {@code interfaceType} by dispatching
    * method invocations to {@code handler}. The class loader of {@code interfaceType}
    * will be used to define the proxy class.
    *
    * @param interfaceType The interface type the proxy will implement
    * @param handler The handler that will process calls on the proxy's methods
    * @return A new proxy of the specified type
    * @throws NullPointerException if either interfaceType or handler are {@code null}
    * @throws IllegalArgumentException if interfaceType does not specify the type of a
    *    Java interface
    */
   public static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler)
   {
      if(handler == null) throw new NullPointerException("handler");
      if(interfaceType == null) throw new NullPointerException("interfaceType");
      if(!interfaceType.isInterface()) throw new IllegalArgumentException("interfaceType not an interface");

      Object proxy = Proxy.newProxyInstance(interfaceType.getClassLoader(),
                                             new Class[]{interfaceType},
                                             handler);
      return interfaceType.cast(proxy);
   }



   /**
    * Helper method that checks if given class is a concrete one; that is, not an
    * interface nor abstract class.
    */
   public static boolean isConcrete(Class<?> type)
   {
      int mod = type.getModifiers();
      return (mod & (Modifier.INTERFACE | Modifier.ABSTRACT)) == 0;
   }

   /**
    * Helper method that checks if given member is a concrete one; that is, not an
    * interface nor abstract class.
    */
   public static boolean isConcrete(Member member)
   {
      int mod = member.getModifiers();
      return (mod & (Modifier.INTERFACE | Modifier.ABSTRACT)) == 0;
   }




   /**
    * Determine whether the given method explicitly declares the given exception
    * or one of its sub classes.
    *
    * @param method the declaring method
    * @param exceptionType the exception to throw
    * @return {@code true} if the specified method throws the given exception type;
    *    {@code false} otherwise
    */
   public static boolean declaresException(Method method, Class<?> exceptionType)
   {
      Objects.notNull(method, "method");
      Class<?>[] declaredExceptions = method.getExceptionTypes();
      for (Class<?> declaredException : declaredExceptions) {
         if (declaredException.isAssignableFrom(exceptionType)) {
            return true;
         }
      }
      return false;
   }




   /**
    * Iterate over the classes' declared fields passing each to the specified
    * consumer.
    */
   public static void fields(Class<?> cls, Consumer<Field> consumer)
   {
      Objects.notNull(cls, "cls"); Objects.notNull(consumer, "consumer");
      for(Field field : cls.getDeclaredFields()) consumer.accept(field);
   }

   /**
    * Iterate over the classes' declared methods passing each to the specified
    * consumer.
    */
   public static void methods(Class<?> cls, Consumer<Method> consumer)
   {
      Objects.notNull(cls, "cls"); Objects.notNull(consumer, "consumer");
      for(Method method : cls.getDeclaredMethods()) consumer.accept(method);
   }


   /**
    * Walks the given class hierarchy and collects all the fields from the given
    * class and all of its super classes. This excludes fields found in interfaces.
    */
   public static Set<Field> findFields(Class<?> cls)
   {
      final Set<Field> fields = Sets.newLinkedHashSet();
      walk(cls, null, field -> {
         if(!field.getDeclaringClass().isInterface()) fields.add(field);
      }, null);
      return fields;
   }

   /**
    * Walks the given class hierarchy and collects all the methods from the given class
    * and all of its super classes and interfaces.
    */
   public static Set<Method> findMethods(Class<?> cls)
   {
      final Set<Method> methods = Sets.newLinkedHashSet();
      walk(cls, null, null, method -> methods.add(method));
      return methods;
   }

   /**
    * Walks the given class hierarchy and collects all the classes and interfaces starting
    * with the given class and all of its super classes and interfaces.
    */
   public static Set<Class<?>> findSuperClasses(Class<?> cls)
   {
      final Set<Class<?>> classes = Sets.newLinkedHashSet();
      walk(cls, clazz -> classes.add(clazz), null, null);
      return classes;
   }


   /**
    * Walk the given class hierarchy consuming classes, fields, methods, or all of the
    * above. The classes consumer, fields consumer or the methods consumer may be {@code
    * null} if you're not interested in consuming those.
    * <p>
    * This will visit the fields and methods of the given class first. It will then
    * recurse into any declared interfaces, walking up the interface's super class
    * structure before coming back and walking up the given classes' super class
    * structure. It will stop when it encounters a {@code null} super class or it
    * encounters {@link Object}
    *
    * @throws NullPointerException if the specified class is {@code null}
    */
   public static void walk(Class<?> cls, Consumer<Class> classes,
                                             Consumer<Field> fields,
                                                Consumer<Method> methods)
   {
      Objects.notNull(cls, "cls");
      classes = Objects.ifNull(classes, Consumers.<Class>noop());
      fields = Objects.ifNull(fields, Consumers.<Field>noop());
      methods = Objects.ifNull(methods, Consumers.<Method>noop());
      while(!isOneOf(cls, null, Object.class)) {
         classes.accept(cls); fields(cls, fields); methods(cls, methods);
         for(Class<?> inter : cls.getInterfaces()) {
            walk(inter, classes, fields, methods);
         }
         cls = cls.getSuperclass();
      }
   }



   /**
    * This implementation attempts to determine the most appropriate class loader to
    * use for the given caller. This typically, implies choosing between the current
    * class loader and the context class loader associated with the calling thread.
    */
   public static ClassLoader resolveClassLoader()
   {
      return ClassLoaderResolver.getClassLoader(1);
   }



   /**
    * This non-instantiable non-subclassable class acts as the global point
    * for choosing a ClassLoader for dynamic class/resource loading at any
    * point in an application.
    */
   static abstract class ClassLoaderResolver {


      private static final int CALL_CONTEXT_OFFSET = 3; // may need to change if this class is redesigned

      private static Resolver CALLER_RESOLVER;

      static {
         try {
            // this can fail if the current SecurityManager does not allow
            // RuntimePermission ("createSecurityManager"):

            CALLER_RESOLVER = new CallerResolver();
         } catch (SecurityException se) {
            CALLER_RESOLVER = new SimpleResolver();
         }
      }


      /**
       * This method selects the "best" classloader instance to be used for
       * class/resource loading by whoever calls this method. The decision
       * typically involves choosing between the caller's current, thread
       * context, system, and other classloaders in the JVM.
       *
       * @return classloader to be used by the caller
       *          ['null' indicates the primordial loader]
       */
      public static synchronized ClassLoader getClassLoader()
      {
         return getClassLoader(0);
      }


      static synchronized ClassLoader getClassLoader(int level)
      {
         final Class<?> caller = getCallerClass(level);

         final ClassLoader callerLoader = caller.getClassLoader();
         final ClassLoader contextLoader = Thread.currentThread ().getContextClassLoader ();

         ClassLoader result;

         // if 'callerLoader' and 'contextLoader' are in a parent-child
         // relationship, always choose the child:

         if (isChild (contextLoader, callerLoader)) {
            result = callerLoader;
         } else if (isChild (callerLoader, contextLoader)) {
            result = contextLoader;
         } else {
            // this else branch could be merged into the previous one,
            // but I show it here to emphasize the ambiguous case:
            result = contextLoader;
         }

         final ClassLoader systemLoader = ClassLoader.getSystemClassLoader ();

         // precaution for when deployed as a bootstrap or extension class:
         if (isChild (result, systemLoader))
            result = systemLoader;

         return result;
      }


      private ClassLoaderResolver()
      {
      }

      /*
       * Indexes into the current method call context with a given offset.
       */
      private static Class<?> getCallerClass(final int callerOffset)
      {
         return CALLER_RESOLVER.getClassContext(CALL_CONTEXT_OFFSET + callerOffset);
      }


      /**
       * Returns 'true' if 'loader2' is a delegation child of 'loader1' [or if
       * 'loader1'=='loader2']. Of course, this works only for classloaders that
       * set their parent pointers correctly. 'null' is interpreted as the
       * primordial loader [i.e., everybody's parent].
       */
      private static boolean isChild (final ClassLoader loader1, ClassLoader loader2)
      {
         if (loader1 == loader2) return true;
         if (loader2 == null) return false;
         if (loader1 == null) return true;

         for ( ; loader2 != null; loader2 = loader2.getParent ()) {
            if (loader2 == loader1) return true;
         }

         return false;
      }




      private static interface Resolver {
         Class<?> getClassContext(int level);
      }

      /**
       * A helper class to get the call context. It subclasses SecurityManager to
       * make getClassContext() accessible. An instance of CallerResolver only
       * needs to be created, not installed as an actual security manager.
       */
      private static final class CallerResolver extends SecurityManager implements Resolver {
         public Class<?> getClassContext(int level)
         {
            return super.getClassContext()[level];
         }
      }

      private static final class SimpleResolver implements Resolver {
         public Class<?> getClassContext(int level)
         {
            return SimpleResolver.class;
         }
      }

   }

}
