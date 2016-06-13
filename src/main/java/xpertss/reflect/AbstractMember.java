package xpertss.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

abstract class AbstractMember implements AnnotatedMember {

   /**
    * Class that was resolved to produce this member instance; either class that declared
    * the member, or one of its subtypes that inherited it.
    */
   final AnnotatedClass context;

   // Transient since information not needed after construction, so
   // no need to persist
   final AnnotationMap annotations;


   AbstractMember(AnnotatedClass context, AnnotationMap annotations)
   {
      this.context = context;
      this.annotations = annotations;
   }



   @Override
   public abstract Member getMember();

   @Override
   public abstract int getModifiers();


   public boolean isPublic()
   {
      return Modifier.isPublic(getModifiers());
   }

   public boolean isPackagePrivate()
   {
      return ((getModifiers() & (Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC)) == 0);
   }

   public boolean isPrivate()
   {
      return Modifier.isPrivate(getModifiers());
   }

   public boolean isProtected()
   {
      return Modifier.isProtected(getModifiers());
   }








   /**
    * Actual physical class in which this member was declared. Note that this may
    * be different from what {@link #getContextClass()} returns; "owner" may be a
    * sub-type of "declaring class".
    */
   @Override
   public abstract Class<?> getDeclaringClass();


   /**
    * Accessor for {@link AnnotatedClass} that was the type that was resolved
    * and that contains this member: this is either the {@link Class}
    * in which member was declared, or one of its super types. If distinction
    * between result type, and actual class in which declaration was found matters,
    * you can compare return value to that of {@link #getDeclaringClass()}.
    * The main use for this accessor is (usually) to access class annotations.
    */
   @Override
   public final AnnotatedClass getContextClass()
   {
      return context;
   }









   @Override
   public final <A extends Annotation> A getAnnotation(Class<A> annotationClass)
   {
      return annotations.get(annotationClass);
   }

   @Override
   public final <A extends Annotation> boolean hasAnnotation(Class<A> annotationClass)
   {
      return getAnnotation(annotationClass) != null;
   }


   @Override
   public final Annotation[] getAnnotations()
   {
      return annotations.getAnnotations();
   }




   /**
    * Method that can be called to modify access rights, by calling
    * {@link AccessibleObject#setAccessible} on
    * the underlying annotated element.
    */
   @Override
   public final void makeAccessible()
   {
      // If a field/method/constructor is public and so is its declaring class then there is no need to set it accessible
      // If a field/method/constructor is already accessible no need to set it accessible again

      // Do we want to allow callers to set final fields accessible??
      AccessibleObject ao = (AccessibleObject) getMember();
      if( (!Modifier.isPublic(getModifiers()) || !Modifier.isPublic(getDeclaringClass().getModifiers()) ) && !ao.isAccessible() ) {
         ao.setAccessible(true);
      }
   }





   /**
    * Full generic type of the annotated element; definition
    * of what exactly this means depends on sub-class.
    */
   @Override
   public abstract Type getGenericType();

   /**
    * "Raw" type (type-erased class) of the annotated element; definition
    * of what exactly this means depends on sub-class.
    */
   @Override
   public abstract Class<?> getRawType();



}
