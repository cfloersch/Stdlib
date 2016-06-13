package xpertss.reflect;

import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * Things I have learned.
 * 1) There is no built in support for inheriting annotations from interfaces nor super classes
 * 2) Methods which override super class methods are NOT considered equal
 * 3) Methods which implement a method in an interface are NOT considered equal
 * 4) Annotations are considered equal based on type and property values
 *    a.annotationType().equals(b.annotationType()) operates on just the annotation type
 */
public class AnnotatedClassTest {


   @Test
   public void testSimple() throws Exception
   {
      AnnotatedClass annotated = AnnotatedClass.of(SubClass.class);
      assertEquals(5, annotated.getAnnotations().length);
      assertEquals(5, annotated.getMethods().length);
      assertEquals(2, annotated.getFields().length);
   }

   @Test
   public void testPackagePrivate()
   {
      int modifiers = AbstractMember.class.getModifiers();
      assertTrue((modifiers & (Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC)) == 0);
   }


   /*
      So it looks like any given method should include annotations in this order
        1) Those directly defined on the given method
        2) Those defined on the method in any of the interfaces directly defined on the declaring class
        3) Those defined on the method in a super class
        4) Those defined on the method in any of the interfaces directly defined by said super class
        5) Those defined on the method in any of the super classes of any of the interfaces
        6) Repeat 3, 4, & 5 all the way to Object

      To make this efficient I would like to iterate the tree once collecting everything as I go.
      But that means that I need to make Methods mutable or provide some means to take current and
      replace with copy (add extra details).

      Not sure exactly where bridge methods will come into play but those will also exist and must be dealt
      with.

      I also learned that even if fields are exposed (aka protected, public, etc) that if the same
      field is defined in a subclass they are treated separately. Obviously, that could lead to all
      sorts of confusion. Even though it would be impossible to access the base classes' version
      without the use of reflection it is possible via reflection.

      Apparently, the base classes' test is accessible by the sub class using super.test.. where its own
      variant is accessible via this.test

      When dealing with methods I may run across a method in an interface before I find it in a class.
      If that is the case I need to store a place holder with the annotations. When I find the class
      method I need to replace the method temporarily stored but copy over the annotations.

      I think that ordering wise (regardless of how I find methods) anything on the top most method
      is king. Followed by anything found on an interface, followed by super class methods. The merge
      method on Java 1.8 map kind gives me what I need. Given the old method I could determine that
      it was declared in an interface and thus swap it out with new annotations or I could simply add
      the new annotations to the existing method.
    */
}