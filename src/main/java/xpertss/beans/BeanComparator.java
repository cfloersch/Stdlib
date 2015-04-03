/**
 * Created By: cfloersch
 * Date: 8/21/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.beans;

import xpertss.lang.Objects;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import static xpertss.lang.Strings.notEmpty;

/**
 * This comparator compares two beans by the specified bean property.
 * <p/>
 * It is also possible to compare beans based on nested, indexed, combined,
 * mapped bean properties. Please see the {@link PropertyUtils} documentation
 * for all property name possibilities.
 *
 * @param <T> The type of the beans being compared
 * @param <S> The type of the returned property
 */
public class BeanComparator<T,S> implements Comparator<T> {

   private Comparator<S> comparator;
   private String property;

   public BeanComparator(String property)
   {
      this(null, property);
   }

   public BeanComparator(Comparator<S> comparator, String property)
   {
      this.comparator = comparator;
      this.property = notEmpty(property, "property must be specified");
   }


   @Override
   public int compare(T o1, T o2)
   {
      try {
         S value1 = Objects.cast(PropertyUtils.getProperty(o1, property));
         S value2 = Objects.cast(PropertyUtils.getProperty(o2, property));
         if(comparator == null) {
            Comparable<S> comp1 = Objects.cast(value1);
            return comp1.compareTo(value2);
         }
         return comparator.compare(value1, value2);
      } catch (IllegalAccessException iae ) {
         throw new RuntimeException( "IllegalAccessException: " + iae.toString() );
      } catch (InvocationTargetException ite ) {
         throw new RuntimeException( "InvocationTargetException: " + ite.toString() );
      } catch (NoSuchMethodException nsme ) {
         throw new RuntimeException( "NoSuchMethodException: " + nsme.toString() );
      }
   }
}
