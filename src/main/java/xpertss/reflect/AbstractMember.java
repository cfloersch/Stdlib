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



   public abstract Member getMember();


   public abstract int getModifiers();


   public boolean isPublic()
   {
      return Modifier.isPublic(getModifiers());
   }

   public boolean isPackagePrivate()
   {
      return !(Modifier.isPrivate(getModifiers())
               || Modifier.isPublic(getModifiers())
                  || Modifier.isProtected(getModifiers()));
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
   public abstract Class<?> getDeclaringClass();


   /**
    * Accessor for {@link AnnotatedClass} that was the type that was resolved
    * and that contains this member: this is either the {@link Class}
    * in which member was declared, or one of its super types. If distinction
    * between result type, and actual class in which declaration was found matters,
    * you can compare return value to that of {@link #getDeclaringClass()}.
    * The main use for this accessor is (usually) to access class annotations.
    */
   public AnnotatedClass getContextClass() {
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
   public final void fixAccess()
   {
      // We know all members are also accessible objects...
      AccessibleObject ao = (AccessibleObject) getMember();

      try {
         ao.setAccessible(true);
      } catch (SecurityException se) {
         if (!ao.isAccessible()) throw se;
      }
   }





   /**
    * Full generic type of the annotated element; definition
    * of what exactly this means depends on sub-class.
    */
   public abstract Type getGenericType();

   /**
    * "Raw" type (type-erased class) of the annotated element; definition
    * of what exactly this means depends on sub-class.
    */
   public abstract Class<?> getRawType();







}
