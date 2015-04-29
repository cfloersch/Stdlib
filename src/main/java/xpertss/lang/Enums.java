/**
 * Created By: cfloersch
 * Date: 1/11/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import xpertss.function.Function;
import xpertss.function.Predicate;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.EnumSet;

/**
 * Static utility methods pertaining to {@code enum} types.
 */
public final class Enums {

   private Enums() { }


   /**
    * Returns the {@link Annotation} for the given {@code enumValue}. For example, to get the
    * {@code Description} annotation on the {@code GOLF} constant of enum {@code Sport}, use
    * {@code Enums.getAnnotation(Sport.GOLF, Description.class)}.
    * <p/>
    * This will return {@code null} if the specified annotation is not found on the given
    * enum.
    *
    * @throws NullPointerException If either enumValue or annotationClass are {@code null}
    */
   public static <T extends Annotation> T getAnnotation(Enum<?> enumValue, Class<T> annotationClass)
   {
      Class<?> clazz = enumValue.getDeclaringClass();
      try {
         return clazz.getDeclaredField(enumValue.name()).getAnnotation(annotationClass);
      } catch (NoSuchFieldException impossible) {
         throw new AssertionError(impossible);
      }
   }


   /**
    * Utility method to create an EnumSet from a var-args list of enum constants.
    * <p/>
    * Why this method is not part of the core jdk is beyond me. Who makes methods with variable
    * lengths of options manually??? By doing wht they did they made it truly variable length
    * and eliminated any possibility of using it to pass an array of items. Maybe that was their
    * intent?
    *
    * @throws NullPointerException If {@code e} is {@code null}
    */
   @SafeVarargs
   public static <E extends Enum<E>> EnumSet<E> of(E ... e)
   {
      @SuppressWarnings("unchecked")
      EnumSet<E> result = EnumSet.noneOf((Class<E>) e.getClass().getComponentType());
      Collections.addAll(result, e);
      return result;
   }


   /**
    * Returns a predicate which reports {@code true} for any enum in the specified set of
    * constants.
    *
    * @param constants The set of enum constants
    * @return A predicate that evaluates to {@code true} for any of the specified enum constants.
    */
   @SafeVarargs
   public static <T extends Enum<T>> Predicate<T> in(T ... constants)
   {
      final EnumSet<T> set = of(constants);
      return new Predicate<T>() {
         @Override public boolean apply(T input)
         {
            return set.contains(input);
         }
      };
   }




   /**
    * Returns an enum constant for the given type, using {@link Enum#valueOf}. If the named
    * constant does not exist, {@code null} is returned.
    *
    * @param enumClass the {@link Class} of the {@code Enum} declaring the constant values.
    * @param name The name of the enum constant
    * @return The named constant or {@code null} if it does not exist
    */
   public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String name)
   {
      try{
         return Enum.valueOf(enumClass, name);
      } catch(IllegalArgumentException e) {
         return null;
      }
   }

   /**
    * Returns an enum constant for the given type, using {@link Enum#valueOf}. If the named
    * constant does not exist, the specified default value is returned.
    *
    * @param enumClass the {@link Class} of the {@code Enum} declaring the constant values.
    * @param name The name of the enum constant
    * @param defValue The default value to return if the named enum doesn't exist
    * @return The named constant or {@code defValue} if it does not exist
    */
   public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String name, T defValue)
   {
      try{
         return Enum.valueOf(enumClass, name);
      } catch(IllegalArgumentException e) {
         return defValue;
      }
   }


   /**
    * Returns a {@link Function} that maps an {@link Enum} name to the associated {@code Enum}
    * constant. The {@code Function} will return {@code null} if the {@code Enum} constant
    * does not exist rather than throwing an exception.
    *
    * @param enumClass the {@link Class} of the {@code Enum} declaring the constant values.
    */
   public static <T extends Enum<T>> Function<String, T> valueOf(Class<T> enumClass)
   {
      return new ValueOfFunction<T>(enumClass);
   }

   /**
    * {@link Function} that maps an {@link Enum} name to the associated constant, or {@code null}
    * if the constant does not exist.
    */
   private static final class ValueOfFunction<T extends Enum<T>> implements Function<String, T>, Serializable {

      private final Class<T> enumClass;

      private ValueOfFunction(Class<T> enumClass)
      {
         this.enumClass = Objects.notNull(enumClass);
      }

      @Override
      public T apply(String value) {
         try {
            return Enum.valueOf(enumClass, value);
         } catch (IllegalArgumentException e) {
            return null;
         }
      }

      @Override public boolean equals(Object obj)
      {
         return obj instanceof ValueOfFunction &&
               enumClass.equals(((ValueOfFunction) obj).enumClass);
      }

      @Override public int hashCode()
      {
         return enumClass.hashCode();
      }

      @Override public String toString()
      {
         return "Enums.valueOf(" + enumClass + ")";
      }

      private static final long serialVersionUID = 0;
   }

}
