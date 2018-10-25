/**
 * Copyright 2015 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/13/2015
 */
package xpertss.lang;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides default values for all Java types, as defined by the JLS.
 */
public final class Defaults {
   private Defaults() { }

   /** A map from primitive types to their corresponding wrapper types. */
   private static final Map<Class<?>, Object> DEFAULTS;

   static {
      Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
      map.put(boolean.class, false);
      map.put(char.class, '\0');
      map.put(byte.class, (byte) 0);
      map.put(short.class, (short) 0);
      map.put(int.class, 0);
      map.put(long.class, 0L);
      map.put(float.class, 0f);
      map.put(double.class, 0d);
      DEFAULTS = Collections.unmodifiableMap(map);
   }

   /**
    * Returns the default value of {@code type} as defined by JLS --- {@code 0} for numbers, {@code
    * false} for {@code boolean} and {@code '\0'} for {@code char}. For non-primitive types and
    * {@code void}, {@code null} is returned.
    */
   @SuppressWarnings("unchecked")
   public static <T> T defaultFor(Class<T> type)
   {
      return (T) DEFAULTS.get(type);
   }


}
