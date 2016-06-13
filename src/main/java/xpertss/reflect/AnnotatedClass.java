/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import xpertss.function.Consumer;
import xpertss.function.Predicate;
import xpertss.lang.Annotations;
import xpertss.lang.Classes;
import xpertss.lang.Objects;
import xpertss.lang.Strings;
import xpertss.util.Sets;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static xpertss.lang.Objects.*;
import static xpertss.lang.Strings.firstCharToUpper;

/**
 * An AnnotatedClass provides easy access to the under lying classes' creators,
 * mutators, accessors, fields, methods, and annotations. The key benefit is
 * that it aggregates annotations together resolving meta-annotations, etc to
 * make working with them easier than the traditional java reflection packages.
 */
public final class AnnotatedClass implements Annotated {

   /**
    * Class that was used to construct this instance including all of its
    * annotations, members, super classes, etc.
    */
   private final Class<?> clazz;


   /**
    * Combined list of annotations that the class has, including inheritable
    * ones from super classes and interfaces
    */
   private final AnnotationMap annotations;





   /**
    * Default constructor of the annotated class, if it has one.
    */
   private AnnotatedCreator defaultCreator;

   /**
    * Constructors and factory methods the class has, if any.
    */
   private Set<AnnotatedCreator> creators;

   /**
    * Single argument setters
    */
   private Set<AnnotatedMutator> mutators;

   /**
    * Zero argument getters
    */
   private Set<AnnotatedAccessor> accessors;

   /**
    * Member methods (static & non-static???)
    */
   private Set<AnnotatedMethod> methods;


   /**
    * Member fields
    */
   private Set<AnnotatedField> fields;




   /**
    * Constructor will not do any initializations, to allow for
    * configuring instances differently depending on use cases
    */
   private AnnotatedClass(Class<?> cls)
   {
      clazz = cls;

      ClassAnnotationConsumer classAnnotations = new ClassAnnotationConsumer();
      MethodConsumer methodConsumer = new MethodConsumer(this);
      FieldConsumer fieldConsumer = new FieldConsumer(this);

      Classes.walk(cls, classAnnotations, fieldConsumer, methodConsumer);
      methods = methodConsumer.getMethods();
      fields = fieldConsumer.getFields();
      annotations = classAnnotations.getAnnotations();

      // TODO Now build accessor/mutator/creators

   }





   /**
    * Factory method that instantiates an instance for a given class.
    * <p/>
    * TODO It would be nice to construct this with a Type vs a Class
    */
   public static AnnotatedClass of(Class<?> cls)
   {
      if(!Classes.isConcrete(cls)) {
         throw new IllegalArgumentException(format("%s not a concrete class", cls.getName()));
      }
      // TODO Anything else I'd like to filter out?
      return new AnnotatedClass(cls);
   }






   @Override
   public String getName() { return clazz.getName(); }

   @Override
   public Class<?> getAnnotated() { return clazz; }




   @Override
   public <A extends Annotation> A getAnnotation(Class<A> annotationClass)
   {
      return annotations.get(annotationClass);
   }

   @Override
   public <A extends Annotation> boolean hasAnnotation(Class<A> annotationClass)
   {
      return getAnnotation(annotationClass) != null;
   }

   @Override
   public Annotation[] getAnnotations()
   {
      return annotations.getAnnotations();
   }





   public int getModifiers() { return clazz.getModifiers(); }


   /**
    * Helper method that returns {@code true} if the given class is marked as public,
    * {@code false} otherwise.
    */
   public boolean isPublic()
   {
      return Modifier.isPublic(getModifiers());
   }

   /**
    * Helper method that returns {@code true} if the given class is package private,
    * {@code false} otherwise. A class is package private if it is not marked as
    * public, private, nor protected.
    */
   public boolean isPackagePrivate()
   {
      return ((getModifiers() & (Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC)) == 0);
   }

   /**
    * Helper method that returns {@code true} if the given class is marked as private,
    * {@code false} otherwise.
    */
   public boolean isPrivate()
   {
      return Modifier.isPrivate(getModifiers());
   }


   /**
    * Helper method that returns {@code true} if the given class is neither an interface
    * nor marked as abstract, {@code false} otherwise.
    */
   public boolean isConcrete()
   {
      return (getModifiers() & (Modifier.INTERFACE | Modifier.ABSTRACT)) == 0;
   }

   /**
    * Helper method that returns {@code true} if the given class is marked as final,
    * {@code false} otherwise.
    */
   public boolean isFinal()
   {
      return Modifier.isFinal(getModifiers());
   }

   /**
    * Helper method that returns {@code true} if the given class is marked as static,
    * {@code false} otherwise.
    */
   public boolean isStatic()
   {
      return Modifier.isStatic(getModifiers());
   }


   /**
    * Returns the generic type encapsulated by this wrapper.
    */
   public Type getGenericType()
   {
      return clazz;
   }

   /**
    * Returns the raw type encapsulated by this wrapper.
    */
   public Class<?> getRawType()
   {
      return clazz;
   }




// Creator methods

   public AnnotatedCreator getCreator()
   {
      return defaultCreator;
   }

   public AnnotatedCreator getCreator(Class<?> ... params)  // TODO Use Type instead of Class??
   {
      Predicate<AnnotatedWithParams> filter = Utils.parameters(params);
      for(AnnotatedCreator creator : creators) {
         if(filter.apply(creator)) return creator;
      }
      return null;
   }

   public <A extends Annotation> AnnotatedCreator[] getCreators(Class<A> annotationClass)
   {
      final Class<A> ann = annotationClass;
      return getCreators(new Predicate<AnnotatedCreator>() {
         @Override
         public boolean apply(AnnotatedCreator input) {
            return input.hasAnnotation(ann);
         }
      });
   }

   public AnnotatedCreator[] getCreators(Predicate<AnnotatedCreator> selector)
   {
      Set<AnnotatedCreator> results = Sets.newLinkedHashSet();
      for(AnnotatedCreator creator : creators) {
         if(selector.apply(creator)) results.add(creator);
      }
      return results.toArray(new AnnotatedCreator[results.size()]);
   }

   public AnnotatedCreator[] getCreators()
   {
      return creators.toArray(new AnnotatedCreator[creators.size()]);
   }





// Accessor methods

   public AnnotatedAccessor getAccessor(String propName)
   {
      String getter = format("get%", firstCharToUpper(propName));
      for(AnnotatedAccessor accessor : accessors) {
         if(isOneOf(accessor.getRawType(), Boolean.class, boolean.class)) {
            if(isOneOf(accessor.getName(), propName, getter,
                     format("is%", firstCharToUpper(propName)))) {
               return accessor;
            }
         } else {
            if(isOneOf(accessor.getName(), propName, getter)) {
               return accessor;
            }
         }
      }
      return null;
   }

   public AnnotatedAccessor[] getAccessors(Predicate<AnnotatedAccessor> selector)
   {
      Set<AnnotatedAccessor> results = Sets.newLinkedHashSet();
      for(AnnotatedAccessor accessor : accessors) {
         if(selector.apply(accessor)) results.add(accessor);
      }
      return results.toArray(new AnnotatedAccessor[results.size()]);
   }

   public <A extends Annotation> AnnotatedAccessor[] getAccessors(Class<A> annotationClass)
   {
      final Class<A> ann = annotationClass;
      return getAccessors(new Predicate<AnnotatedAccessor>() {
         @Override
         public boolean apply(AnnotatedAccessor input) {
            return input.hasAnnotation(ann);
         }
      });
   }

   public AnnotatedAccessor[] getAccessors()
   {
      return accessors.toArray(new AnnotatedAccessor[accessors.size()]);
   }



// Mutator methods

   public AnnotatedMutator getMutator(String propName)
   {
      String setter = format("set%", firstCharToUpper(propName));
      String[] names = toArray(propName, setter);
      for(AnnotatedMutator mutator : mutators) {
         if(isOneOf(mutator.getName(), names)) {
            return mutator;
         }
      }
      return null;
   }

   public AnnotatedMutator[] getMutators(Predicate<AnnotatedMutator> selector)
   {
      Set<AnnotatedMutator> results = Sets.newLinkedHashSet();
      for(AnnotatedMutator mutator : mutators) {
         if(selector.apply(mutator)) results.add(mutator);
      }
      return results.toArray(new AnnotatedMutator[results.size()]);
   }

   public <A extends Annotation> AnnotatedMutator[] getMutators(Class<A> annotationClass)
   {
      final Class<A> ann = annotationClass;
      return getMutators(new Predicate<AnnotatedMutator>() {
         @Override
         public boolean apply(AnnotatedMutator input) {
            return input.hasAnnotation(ann);
         }
      });
   }

   public AnnotatedMutator[] getMutators()
   {
      return mutators.toArray(new AnnotatedMutator[mutators.size()]);
   }



// Field methods

   public AnnotatedField getField(String propName)
   {
      final String name = propName;
      AnnotatedField[] results = getFields(new Predicate<AnnotatedField>() {
         @Override
         public boolean apply(AnnotatedField input) {
            return Strings.equal(input.getName(), name);
         }
      });
      return (results.length > 0) ? results[0] : null;
   }

   public AnnotatedField[] getFields(Predicate<AnnotatedField> selector)
   {
      Set<AnnotatedField> results = Sets.newLinkedHashSet();
      for(AnnotatedField field : fields) {
         if(selector.apply(field)) results.add(field);
      }
      return results.toArray(new AnnotatedField[results.size()]);
   }

   public <A extends Annotation> AnnotatedField[] getFields(Class<A> annotationClass)
   {
      final Class<A> ann = annotationClass;
      return getFields(new Predicate<AnnotatedField>() {
         @Override
         public boolean apply(AnnotatedField input) {
            return input.hasAnnotation(ann);
         }
      });
   }

   public AnnotatedField[] getFields()
   {
      return fields.toArray(new AnnotatedField[fields.size()]);
   }




// Method methods

   public AnnotatedMethod getMethod(String name, Class<?> ... paramTypes)   // TODO Use Type instead of Class??
   {
      Predicate<AnnotatedWithParams> selector = Utils.parameters(paramTypes);
      for(AnnotatedMethod method : methods) {
         if(Strings.equal(name, method.getName())) {
            if(selector.apply(method)) return method;
         }
      }
      return null;
   }

   public AnnotatedMethod[] getMethods(Predicate<AnnotatedMethod> selector)
   {
      Set<AnnotatedMethod> results = Sets.newLinkedHashSet();
      for(AnnotatedMethod method : methods) {
         if(selector.apply(method)) results.add(method);
      }
      return results.toArray(new AnnotatedMethod[results.size()]);
   }

   public <A extends Annotation> AnnotatedMethod[] getMethods(Class<A> annotationClass)
   {
      final Class<A> ann = annotationClass;
      return getMethods(new Predicate<AnnotatedMethod>() {
         @Override
         public boolean apply(AnnotatedMethod input) {
            return input.hasAnnotation(ann);
         }
      });
   }

   public AnnotatedMethod[] getMethods()
   {
      return methods.toArray(new AnnotatedMethod[methods.size()]);
   }





// Other methods


   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof AnnotatedClass) {
         AnnotatedClass o = (AnnotatedClass) obj;
         return o.clazz == clazz;
      }
      return false;
   }

   @Override
   public int hashCode()
   {
      return clazz.getName().hashCode();
   }

   @Override
   public String toString()
   {
      return "[AnnotatedClass " + clazz.getName() + "]";
   }



   private static class FieldConsumer implements Consumer<Field> {

      Set<AnnotatedField> fields = Sets.newLinkedHashSet();
      AnnotatedClass context;

      private FieldConsumer(AnnotatedClass context)
      {
         this.context = context;
      }

      @Override
      public void apply(Field field)
      {
         fields.add(new AnnotatedField(context, field, AnnotationMap.of(field)));
      }

      public Set<AnnotatedField> getFields()
      {
         return fields;
      }

   }

   private static class MethodConsumer implements Consumer<Method> {

      Map<MethodKey, AnnotatedMethod> methods = new LinkedHashMap();
      AnnotatedClass context;

      private MethodConsumer(AnnotatedClass context)
      {
         this.context = context;
      }

      @Override
      public void apply(Method method)
      {
         MethodKey key = MethodKey.of(method);
         AnnotatedMethod annotated = methods.get(key);
         if(annotated == null) {
            annotated = new AnnotatedMethod(context, method);
         } else {
            // We want the first instance method (not interface method)
            if(annotated.getDeclaringClass().isInterface()) {
               AnnotationMap annotations = AnnotationMap.of(annotated.getAnnotations());
               // We would like the class method rather than interface method
               annotated = new AnnotatedMethod(context, method);
               annotated = annotated.with(annotations);
            } else {
               AnnotationMap annotations = AnnotationMap.of(method);
               annotated = annotated.with(annotations);
            }
         }
         methods.put(key, annotated);
      }

      public Set<AnnotatedMethod> getMethods()
      {
         return Sets.newLinkedHashSet(methods.values());
      }


   }

   private static class ClassAnnotationConsumer implements Consumer<Class> {

      AnnotationMap annotations;

      @Override
      public void apply(Class aClass)
      {
         Set<Annotation> ann = Annotations.getAnnotations((AnnotatedElement)aClass);
         if(annotations == null) {
            annotations = AnnotationMap.of(ann);
         } else {
            annotations = annotations.with(AnnotationMap.of(ann));
         }
      }

      public AnnotationMap getAnnotations()
      {
         return annotations;
      }
   }

   private static class MethodKey {

      private String name;
      private Class[] params;

      private MethodKey(String name, Class[] params)
      {
         this.name = name;
         this.params = params;
      }

      public boolean equals(Object obj)
      {
         if(obj instanceof MethodKey) {
            MethodKey key = (MethodKey) obj;
            return Objects.equal(name, key.name)
                     && Arrays.equals(params, key.params);
         }
         return false;
      }

      public int hashCode()
      {
         return Objects.hash(name, params);
      }

      public static MethodKey of(Method method)
      {
         return new MethodKey(method.getName(), method.getParameterTypes());
      }
   }

}
