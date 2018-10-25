/**
 * Copyright 2016 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * Shared base class used for anything on which annotations (included within a
 * {@link AnnotationMap}).
 */
public interface Annotated {

   String getName();

   /**
    * Method that can be used to find actual JDK element that this instance
    * represents. It is non-null, except for method/constructor parameters
    * which do not have a JDK counterpart.
    */
   AnnotatedElement getAnnotated();









   /**
    * Returns this element's annotation for the specified type if such an annotation
    * is present, else {@code null}.
    *
    * @param annotationClass the Class object corresponding to the annotation type
    * @return this element's annotation for the specified annotation type if
    *     present on this element, else null
    * @throws NullPointerException if the given annotation class is null
    */
   <A extends Annotation> A getAnnotation(Class<A> annotationClass);

   /**
    * Returns {@code true} if an annotation for the specified type is present on this
    * element, else {@code false}.  This method is designed primarily for convenient
    * access to marker annotations.
    *
    * @param annotationClass the Class object corresponding to the annotation type
    * @return true if an annotation for the specified annotation type is present on
    * this element, else false
    * @throws NullPointerException if the given annotation class is null
    */
   <A extends Annotation> boolean hasAnnotation(Class<A> annotationClass);

   /**
    * Returns all annotations present on this element.  (Returns an array of length
    * zero if this element has no annotations.)  The caller of this method is free
    * to modify the returned array; it will have no effect on the arrays returned to
    * other callers.
    */
   Annotation[] getAnnotations();




}
