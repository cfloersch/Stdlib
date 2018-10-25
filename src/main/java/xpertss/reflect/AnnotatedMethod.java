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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Set;

/**
 *
 */
public class AnnotatedMethod extends AbstractMember implements AnnotatedWithParams {

   private final AnnotatedParameter[] params;
   private final Method method;



    /*
    /*****************************************************
    /* Life-cycle
    /*****************************************************
     */

   AnnotatedMethod(AnnotatedClass context, Method method)
   {
      super(context, AnnotationMap.of(method));
      this.method = Objects.notNull(method, "method");

      Set<AnnotatedParameter> parameters = Sets.newLinkedHashSet();
      Type[] paramTypes = method.getGenericParameterTypes();
      for(int i = 0; i < paramTypes.length; i++) {
         Annotation[] paramAnnotations = method.getParameterAnnotations()[i];
         Set<Annotation> annotations = Sets.newLinkedHashSet();
         for(Annotation ann : paramAnnotations) {
            annotations.add(ann);
            annotations.addAll(Annotations.getMetaAnnotations(ann));
         }
         parameters.add(new AnnotatedParameter(this, paramTypes[i], AnnotationMap.of(annotations), i));
      }
      this.params = parameters.toArray(new AnnotatedParameter[parameters.size()]);
   }

   AnnotatedMethod(AnnotatedMethod method)
   {
      super(method.getContextClass(), method.annotations);
      this.method = Objects.notNull(method, "method").getMember();
      this.params = method.params;
   }

   AnnotatedMethod(AnnotatedMethod method, AnnotationMap annotations)
   {
      super(method.getContextClass(), method.annotations.with(annotations));
      this.method = Objects.notNull(method, "method").getMember();
      this.params = method.params;
   }


   AnnotatedMethod with(AnnotationMap annotations)
   {
      return new AnnotatedMethod(this, annotations);
   }


    /*
    /*****************************************************
    /* Annotated impl
    /*****************************************************
     */

   @Override
   public String getName() { return method.getName(); }

   @Override
   public Method getAnnotated() { return method; }

   @Override
   public int getModifiers() { return method.getModifiers(); }




   /**
    * Return {@code true} if this method is defined as {@code static}, {@code false}
    * otherwise.
    */
   public boolean isStatic()
   {
      return Modifier.isStatic(getModifiers());
   }

   /**
    * Return {@code true} if this method is defined as {@code final}, {@code false}
    * otherwise.
    */
   public boolean isFinal()
   {
      return Modifier.isStatic(getModifiers());
   }

   /**
    * Return {@code true} if this method is defined as {@code synchronized}, {@code
    * false} otherwise.
    */
   public boolean isSynchronized()
   {
      return Modifier.isSynchronized(getModifiers());
   }





   /**
    * For methods, this returns declared return type, which is only
    * useful with getters (setters do not usually return anything;
    * hence "void" type is returned here)
    */
   @Override
   public Class<?> getRawType() {
      return method.getReturnType();
   }

   /**
    * For methods, this returns declared return type, which is only
    * useful with getters (setters do not return anything; hence "void"
    * type is returned here)
    */
   @Override
   public Type getGenericType() {
      return method.getGenericReturnType();
   }







    /*
    /********************************************************
    /* AnnotatedMember impl
    /********************************************************
     */

   @Override
   public Class<?> getDeclaringClass() { return method.getDeclaringClass(); }

   @Override
   public Method getMember() { return method; }





    /*
    /*****************************************************
    /* Extended API, generic
    /*****************************************************
     */


   public String getFullName()
   {
      return getDeclaringClass().getName() + "#" + getName() + "("
                           + getParameterCount() + " params)";
   }


   @Override
   public boolean isVarArgs()
   {
      return method.isVarArgs();
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
    * Helper method that can be used to check whether method returns
    * a value or not; if return type declared as <code>void</code>, returns
    * false, otherwise true
    */
   public boolean hasReturnType()
   {
      Class<?> rt = getRawType();
      return (rt != Void.TYPE && rt != Void.class);
   }






   public Object invoke(Object instance, Object ... args) throws Exception
   {
      return method.invoke(instance, args);
   }



    /*
    /********************************************************
    /* Other
    /********************************************************
     */


   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof AnnotatedMethod) {
         AnnotatedMethod o = (AnnotatedMethod) obj;
         return o.method == method;
      }
      return false;
   }

   @Override
   public int hashCode() {
      return method.getName().hashCode();
   }

   @Override
   public String toString() {
      return "[method " + getFullName() + "]";
   }





   AnnotatedCreator asCreator()
   {
      return new AnnotatedMethodCreator(this);
   }

   AnnotatedAccessor asAccessor()
   {
      return new AnnotatedMethodAccessor(this);
   }

   AnnotatedMutator asMutator()
   {
      return new AnnotatedMethodMutator(this);
   }


   private class AnnotatedMethodMutator extends AnnotatedMethod implements AnnotatedMutator {

      AnnotatedMethodMutator(AnnotatedMethod method)
      {
         super(method);
      }

      @Override
      public void setValue(Object pojo, Object value) throws Exception
      {
         super.invoke(pojo, value);
      }
   }

   private class AnnotatedMethodAccessor extends AnnotatedMethod implements AnnotatedAccessor {

      AnnotatedMethodAccessor(AnnotatedMethod method)
      {
         super(method);
      }

      @Override
      public Object getValue(Object pojo) throws Exception
      {
         return super.invoke(pojo);
      }
   }

   private class AnnotatedMethodCreator extends AnnotatedMethod implements AnnotatedCreator {

      AnnotatedMethodCreator(AnnotatedMethod method)
      {
         super(method);
      }

      @Override
      public Object invoke(Object... args) throws Exception
      {
         return super.invoke(null, args);
      }
   }

}
