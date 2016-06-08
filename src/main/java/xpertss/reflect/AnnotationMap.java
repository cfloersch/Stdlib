/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import xpertss.lang.Annotations;
import xpertss.lang.Objects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Collection providing for rapid access to a collection of annotations by type.
 * This is needed when introspecting annotation-based features from different
 * kinds of things, not just objects that Java Reflection interface exposes.
 */
public final class AnnotationMap implements Iterable<Annotation> {


   protected final Map<Class<? extends Annotation>,Annotation> annotations;

   private AnnotationMap(Map<Class<? extends Annotation>,Annotation> annotations)
   {
      this.annotations = Collections.unmodifiableMap(Objects.notNull(annotations));
   }


   /**
    * Main access method used to find value for given annotation.
    */
   public <A extends Annotation> A get(Class<A> cls)
   {
      return cls.cast(annotations.get(cls));
   }


   /**
    * Returns number of annotation entries in this collection.
    */
   public int size() {
      return annotations.size();
   }

   /**
    * Returns {@code true} if this collection does not contain any annotations,
    * {@code false} otherwise.
    */
   public boolean isEmpty()
   {
      return annotations.isEmpty();
   }


   @Override
   public Iterator<Annotation> iterator()
   {
      return annotations.values().iterator();
   }


   /**
    * Returns a mutation safe array of annotations associated with this map.
    */
   public Annotation[] getAnnotations()
   {
      Collection<Annotation> values = annotations.values();
      return values.toArray(new Annotation[values.size()]);
   }



   /**
    * Returns a new AnnotationMap that includes all the annotations in this map
    * as well as those in the supplied set if they are not already present.
    */
   public AnnotationMap with(Set<Annotation> additions)
   {
      Map<Class<? extends Annotation>,Annotation> newSet = new HashMap<>(annotations);
      for(Annotation ann : additions) {
         if(!newSet.containsKey(ann.annotationType()))
            newSet.put(ann.annotationType(), ann);
      }
      return new AnnotationMap(newSet);
   }

   /**
    * Creates and returns a new AnnotationMap that overlays the supplied annotations
    * over those from this collection.
    */
   public AnnotationMap overlay(AnnotationMap other)
   {
      if (other == null || other.isEmpty()) return this;
      Map<Class<? extends Annotation>,Annotation> annotations = new HashMap<Class<? extends Annotation>,Annotation>();
      // add these annotations first
      annotations.putAll(this.annotations);
      // overlay supplied annotations
      annotations.putAll(other.annotations);
      return new AnnotationMap(annotations);
   }



   /**
    * Creates and returns an AnnotationMap for the given set of annotations.
    */
   public static AnnotationMap of(Set<Annotation> annotations)
   {
      Map<Class<? extends Annotation>,Annotation> newSet = new HashMap<>();
      for(Annotation ann : annotations) {
         newSet.put(ann.annotationType(), ann);
      }
      return new AnnotationMap(newSet);
   }


   public static AnnotationMap of(Constructor<?> constructor)
   {
      return of(Annotations.getAnnotations(constructor));
   }

   public static AnnotationMap of(Method method)
   {
      return of(Annotations.getAnnotations(method));
   }

   public static AnnotationMap of(Field field)
   {
      return of(Annotations.getAnnotations(field));
   }



   @Override
   public String toString()
   {
      return annotations.toString();
   }


}
