/**
 * Copyright 2016 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public final class AnnotatedField extends AbstractMember implements AnnotatedAccessor, AnnotatedMutator, Comparable<AnnotatedMember> {

   /**
    * Actual {@link Field} used for access.
    */
   private final Field field;


    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */

   AnnotatedField(AnnotatedClass contextClass, Field field, AnnotationMap annMap)
   {
      super(contextClass, annMap);
      this.field = field;
   }


    /*
    /**********************************************************
    /* Annotated impl
    /**********************************************************
     */

   @Override
   public String getName() { return field.getName(); }

   @Override
   public Field getAnnotated() { return field; }

   @Override
   public int getModifiers() { return field.getModifiers(); }

   public String getFullName() {
      return getDeclaringClass().getName() + "#" + getName();
   }




   /**
    * Return {@code true} if this field is defined as {@code transient}, {@code false}
    * otherwise.
    */
   public boolean isTransient()
   {
      return Modifier.isTransient(getModifiers());
   }

   /**
    * Return {@code true} if this field is defined as {@code volatile}, {@code false}
    * otherwise.
    */
   public boolean isVolatile()
   {
      return Modifier.isVolatile(getModifiers());
   }


   /**
    * Return {@code true} if this field is defined as {@code static}, {@code false}
    * otherwise.
    */
   public boolean isStatic()
   {
      return Modifier.isStatic(getModifiers());
   }

   /**
    * Return {@code true} if this field is defined as {@code final}, {@code false}
    * otherwise.
    */
   public boolean isFinal()
   {
      return Modifier.isStatic(getModifiers());
   }

   /**
    * Return {@code true} if this field is defined as {@code synchronized}, {@code
    * false} otherwise.
    */
   public boolean isSynchronized()
   {
      return Modifier.isSynchronized(getModifiers());
   }





   @Override
   public Type getGenericType() {
      return field.getGenericType();
   }

   @Override
   public Class<?> getRawType() {
      return field.getType();
   }



    /*
    /**********************************************************
    /* AnnotatedMember impl
    /**********************************************************
     */

   @Override
   public Class<?> getDeclaringClass() { return field.getDeclaringClass(); }

   @Override
   public Member getMember() { return field; }















   @Override
   public void setValue(Object pojo, Object value) throws Exception
   {
      field.set(pojo, value);
   }

   @Override
   public Object getValue(Object pojo) throws Exception
   {
      return field.get(pojo);
   }





   @Override
   public int compareTo(AnnotatedMember o)
   {
      if(o instanceof AnnotatedMethod) return 1;
      return Utils.accessModifier(this) - Utils.accessModifier(o);
   }


   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof AnnotatedField) {
         AnnotatedField o = (AnnotatedField) obj;
         return o.field == field;
      }
      return false;
   }

   @Override
   public int hashCode() {
      return field.getName().hashCode();
   }

   @Override
   public String toString() {
      return "[field " + getFullName() + "]";
   }

}
