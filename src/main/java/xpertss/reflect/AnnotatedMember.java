/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import java.lang.reflect.Member;
import java.lang.reflect.Type;


/**
 * Intermediate base class for annotated entities that are members of a class;
 * fields, methods and constructors. This is a superset of things that can
 * represent logical properties as it contains constructors in addition to
 * fields and methods.
 */
public interface AnnotatedMember extends Annotated {


   /**
    * Return the underlying {@link java.lang.reflect.Member} this annotated
    * member encapsulates.
    */
   Member getMember();


   /**
    * Actual physical class in which this member was declared. Note that this may
    * be different from what {@link #getContextClass()} returns; "owner" may be a
    * sub-type of "declaring class".
    */
   Class<?> getDeclaringClass();


   /**
    * Accessor for {@link AnnotatedClass} that was the type that was resolved
    * and that contains this member: this is either the {@link java.lang.Class}
    * in which member was declared, or one of its super types. If distinction
    * between result type, and actual class in which declaration was found matters,
    * you can compare return value to that of {@link #getDeclaringClass()}.
    * The main use for this accessor is (usually) to access class annotations.
    */
   AnnotatedClass getContextClass();













   /**
    * Method that can be called to modify access rights, by calling
    * {@link java.lang.reflect.AccessibleObject#setAccessible} on the underlying
    * annotated element.
    */
   void fixAccess();





   /**
    * Full generic type of the annotated element; definition
    * of what exactly this means depends on sub-class.
    */
   Type getGenericType();

   /**
    * "Raw" type (type-erased class) of the annotated element; definition
    * of what exactly this means depends on sub-class.
    */
   Class<?> getRawType();






   /**
    * Returns the Java language modifiers for the member represented by this object, as
    * an integer. The {@code Modifier} class should be used to decode the modifiers.
    * <p/>
    * TODO Hide this???
    */
   int getModifiers();



   /**
    * Return {@code true} if this member is defined as {@code public}, {@code false}
    * otherwise.
    */
   boolean isPublic();

   /**
    * Return {@code true} if this member is not defined as {@code public}, {@code private},
    * nor {@code protected}; {@code false} otherwise.
    */
   boolean isPackagePrivate();

   /**
    * Return {@code true} if this member is defined as {@code protected}, {@code false}
    * otherwise.
    */
   boolean isProtected();

   /**
    * Return {@code true} if this member is defined as {@code private}, {@code false}
    * otherwise.
    */
   boolean isPrivate();

}
