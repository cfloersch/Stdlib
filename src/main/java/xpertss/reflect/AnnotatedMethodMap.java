/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/5/2016
 */
package xpertss.reflect;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;

public final class AnnotatedMethodMap implements Iterable<AnnotatedMethod> {

   protected LinkedHashMap<MemberKey,AnnotatedMethod> methods;

   public AnnotatedMethodMap() { }

   /**
    * Method called to add specified annotated method in the Map.
    */
   public void add(AnnotatedMethod am)
   {
      if (methods == null) methods = new LinkedHashMap<>();
      methods.put(new MemberKey(am.getAnnotated()), am);
   }

   /**
    * Method called to remove specified method, assuming
    * it exists in the Map
    */
   public AnnotatedMethod remove(AnnotatedMethod am)
   {
      return remove(am.getAnnotated());
   }

   public AnnotatedMethod remove(Method m)
   {
      return  (methods != null) ? methods.remove(new MemberKey(m)) : null;
   }

   public boolean isEmpty() {
      return (methods == null || methods.size() == 0);
   }

   public int size() {
      return (methods == null) ? 0 : methods.size();
   }

   public AnnotatedMethod find(String name, Class<?>[] paramTypes)
   {
      return (methods != null) ? methods.get(new MemberKey(name, paramTypes)) : null;
   }

   public AnnotatedMethod find(Method m)
   {
      return (methods == null) ? null : methods.get(new MemberKey(m));
   }

    /*
    /**********************************************************
    /* Iterable implementation (for iterating over values)
    /**********************************************************
     */

   @Override
   public Iterator<AnnotatedMethod> iterator()
   {
      if (methods != null) return methods.values().iterator();
      return Collections.emptyIterator();
   }

}
