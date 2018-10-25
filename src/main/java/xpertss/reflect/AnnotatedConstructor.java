/**
 * Copyright 2016 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import xpertss.lang.Annotations;
import xpertss.lang.Objects;
import xpertss.util.Sets;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * TODO Could make this package private
 */
public final class AnnotatedConstructor extends AbstractMember implements AnnotatedCreator {

   protected final Constructor<?> constructor;
   private final AnnotatedParameter[] params;


    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */

   AnnotatedConstructor(AnnotatedClass context, Constructor<?> constructor)
   {
      super(context, AnnotationMap.of(constructor));
      this.constructor = Objects.notNull(constructor, "constructor");

      Set<AnnotatedParameter> parameters = Sets.newLinkedHashSet();
      Type[] paramTypes = constructor.getGenericParameterTypes();
      for(int i = 0; i < paramTypes.length; i++) {
         Annotation[] paramAnnotations = constructor.getParameterAnnotations()[i];
         Set<Annotation> annotations = Sets.newLinkedHashSet();
         for(Annotation ann : paramAnnotations) {
            annotations.addAll(Annotations.getMetaAnnotations(ann));
         }
         parameters.add(new AnnotatedParameter(this, paramTypes[i], AnnotationMap.of(annotations), i));
      }
      this.params = parameters.toArray(new AnnotatedParameter[parameters.size()]);
   }





    /*
    /**********************************************************
    /* Annotated impl
    /**********************************************************
     */

   @Override
   public String getName() { return constructor.getName(); }

   @Override
   public Constructor<?> getAnnotated() { return constructor; }

   @Override
   public Class<?> getDeclaringClass() { return constructor.getDeclaringClass(); }

   @Override
   public Member getMember() { return constructor; }






   @Override
   public int getModifiers() { return constructor.getModifiers(); }


   @Override
   public Type getGenericType() {
      return getRawType();
   }

   @Override
   public Class<?> getRawType() {
      return constructor.getDeclaringClass();
   }





   @Override
   public boolean isVarArgs()
   {
      return constructor.isVarArgs();
   }




   @Override
   public AnnotatedParameter getParameter(int index)
   {
      return params[index];
   }

   @Override
   public int getParameterCount()
   {
      return params.length;
   }



   /**
    * Method that can be used to (try to) invoke this object with specified arguments.
    * This may succeed or fail, depending on expected number of arguments: caller needs
    * to take care to pass correct number. Exceptions are thrown directly from actual
    * low-level call.
    */
   @Override
   public Object invoke(Object ... args) throws Exception
   {
      return constructor.newInstance(args);
   }






    /*
    /**********************************************************
    /* Extended API, specific annotations
    /**********************************************************
     */



   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof AnnotatedConstructor) {
         AnnotatedConstructor o = (AnnotatedConstructor) obj;
         return o.constructor == constructor;
      }
      return false;
   }

   @Override
   public int hashCode() {
      return constructor.getName().hashCode();
   }

   @Override
   public String toString() {
      return "[constructor for " + getName() + ", annotations: " + annotations + "]";
   }


   int accessModifier()
   {
      return (isPublic()) ? 0 : (isProtected()) ? 1 : (isPrivate()) ? 3 : 2;
   }

}
