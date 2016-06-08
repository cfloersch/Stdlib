/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import xpertss.function.Predicate;
import xpertss.lang.Classes;
import xpertss.lang.Strings;
import xpertss.util.Sets;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static xpertss.lang.Objects.*;
import static xpertss.lang.Strings.firstCharToUpper;


public final class AnnotatedClass implements Annotated {

   /**
    * Class for which annotations apply, and that owns other
    * components (constructors, methods)
    */
   private final Class<?> clazz;

   /**
    * Ordered set of super classes and interfaces of the
    * class itself: included in order of precedence
    */
   private final List<Class<?>> superTypes;


   /**
    * Combined list of Jackson annotations that the class has,
    * including inheritable ones from super classes and interfaces
    */
   private AnnotationMap annotations;





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
   private AnnotatedClass(Class<?> cls, List<Class<?>> superTypes, AnnotationMap classAnnotations)
   {
      // TODO Need to parse the class into annotations, creators, mutators, accessors, fields, and methods
      clazz = cls;
      this.superTypes = superTypes;
      this.annotations = classAnnotations;
   }





   /**
    * Factory method that instantiates an instance. Returned instance
    * will only be initialized with class annotations, but not with
    * any method information.
    * <p/>
    * TODO It would be nice to construct this with a Type vs a Class
    */
   public static AnnotatedClass of(Class<?> cls)
   {
      return new AnnotatedClass(cls, Classes.findSuperTypes(cls, null), null);
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


   public boolean isPublic()
   {
      return Modifier.isPublic(getModifiers());
   }

   public boolean isConcrete()
   {
      return (getModifiers() & (Modifier.INTERFACE | Modifier.ABSTRACT)) == 0;
   }

   public boolean isFinal()
   {
      return Modifier.isFinal(getModifiers());
   }

   public boolean isStatic()
   {
      return Modifier.isStatic(getModifiers());
   }

   public boolean isPackagePrivate()
   {
      return !(Modifier.isPrivate(getModifiers()) || Modifier.isPublic(getModifiers()));
   }

   public boolean isPrivate()
   {
      return Modifier.isPrivate(getModifiers());
   }




   public Type getGenericType() {
      return clazz;
   }

   public Class<?> getRawType() {
      return clazz;
   }




// Creator methods

   public AnnotatedCreator getCreator()
   {
      return defaultCreator;
   }

   public AnnotatedCreator getCreator(final Class<?> ... params)  // TODO Use Type instead of Class??
   {
      Predicate<AnnotatedWithParams> filter = Utils.parameters(params);
      for(AnnotatedCreator creator : creators) {
         if(filter.apply(creator)) return creator;
      }
      return null;
   }

   public <A extends Annotation> AnnotatedCreator[] getCreators(final Class<A> annotationClass)
   {
      return getCreators(new Predicate<AnnotatedCreator>() {
         @Override
         public boolean apply(AnnotatedCreator input) {
            return input.hasAnnotation(annotationClass);
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

   public AnnotatedMutator[] getMutator(Predicate<AnnotatedMutator> selector)
   {
      Set<AnnotatedMutator> results = Sets.newLinkedHashSet();
      for(AnnotatedMutator mutator : mutators) {
         if(selector.apply(mutator)) results.add(mutator);
      }
      return results.toArray(new AnnotatedMutator[results.size()]);
   }

   public <A extends Annotation> AnnotatedMutator[] getMutator(Class<A> annotationClass)
   {
      final Class<A> ann = annotationClass;
      return getMutator(new Predicate<AnnotatedMutator>() {
         @Override
         public boolean apply(AnnotatedMutator input) {
            return input.hasAnnotation(ann);
         }
      });
   }

   public AnnotatedMutator[] getMutator()
   {
      return mutators.toArray(new AnnotatedMutator[mutators.size()]);
   }



// Field methods

   public AnnotatedField getField(String propName)
   {
      final String name = propName;
      Predicate<AnnotatedField> selector = new Predicate<AnnotatedField>() {
         @Override
         public boolean apply(AnnotatedField input) {
            return Strings.equal(input.getName(), name);
         }
      };
      for(AnnotatedField field : fields) {
         if(selector.apply(field)) return field;
      }
      return null;
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


}
