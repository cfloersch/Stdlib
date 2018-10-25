/**
 * Copyright 2016 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class AnnotatedParameter extends AbstractMember {

   /**
    * Member (method, constructor) that this parameter belongs to
    */
   protected final AnnotatedWithParams owner;

   /**
    * JDK type of the parameter, possibly contains generic type information
    */
   protected final Type type;

   /**
    * Index of the parameter within argument list
    */
   protected final int index;

    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */

   AnnotatedParameter(AnnotatedWithParams owner, Type type, AnnotationMap annotations, int index)
   {
      super((owner == null) ? null : owner.getContextClass(), annotations);
      this.owner = owner;
      this.type = type;
      this.index = index;
   }




    /*
    /**********************************************************
    /* Annotated impl
    /**********************************************************
     */

   /**
    * Since there is no matching JDK element, this method will
    * always return null
    */
   @Override
   public AnnotatedElement getAnnotated() { return null; }

   /**
    * Returns modifiers of the constructor, as parameters do not
    * have independent modifiers.
    */
   @Override
   public int getModifiers() { return owner.getModifiers(); }

   /**
    * Parameters have no names in bytecode (unlike in source code),
    * will always return empty String ("").
    */
   @Override
   public String getName() { return ""; }





   @Override
   public Type getGenericType() {
      return type;
   }

   @Override
   public Class<?> getRawType()
   {
      // TODO Check this impl
      if (type instanceof Class<?>) {
         return (Class<?>) type;
      } else if(type instanceof ParameterizedType) {
         return (Class<?>) ((ParameterizedType)type).getRawType();
      } else if(type instanceof GenericArrayType) {
         return (Class<?>) ((GenericArrayType)type).getGenericComponentType();

      }
      // TODO What to do if not those types?
      return null;
   }

    /*
    /**********************************************************
    /* AnnotatedMember extras
    /**********************************************************
     */

   @Override
   public Class<?> getDeclaringClass() {
      return owner.getDeclaringClass();
   }

   @Override
   public Member getMember()
   {
        /* This is bit tricky: since there is no JDK equivalent; can either
         * return null or owner... let's do latter, for now.
         */
      return owner.getMember();
   }






    /*
    /**********************************************************
    /* Extended API  TODO Do I want these?
    /**********************************************************
     */

   public Type getParameterType() { return type; }

   /**
    * Accessor for 'owner' of this parameter; method or constructor that
    * has this parameter as member of its argument list.
    *
    * @return Owner (member or creator) object of this parameter
    */
   public AnnotatedWithParams getOwner() { return owner; }

   /**
    * Accessor for index of this parameter within argument list
    *
    * @return Index of this parameter within argument list
    */
   public int getIndex() { return index; }





    /*
    /********************************************************
    /* Other
    /********************************************************
     */


   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof AnnotatedParameter) {
         AnnotatedParameter o = (AnnotatedParameter) obj;
         return o.owner.equals(owner) && (o.index == index);
      }
      return false;
   }

   @Override
   public int hashCode() {
      return owner.hashCode() + index;
   }

   @Override
   public String toString() {
      return "[parameter #" + getIndex() + ", annotations: " + annotations + "]";
   }



}
