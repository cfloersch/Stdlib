/**
 * Created By: cfloersch
 * Date: 5/26/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.lang;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ObjectsTest {


   @Test
   public void testNaturalSort()
   {
      Integer[] integers = {0,1,2,3,4};
      assertTrue(Arrays.equals(integers, Objects.sort(new Integer[]{4,1,3,2,0})));

      Float[] floats = {0F,1F,2F,3F,4F};
      assertTrue(Arrays.equals(floats, Objects.sort(new Float[]{4F,1F,3F,2F,0F})));

      String[] strings = {"A","B","C","D"};
      assertTrue(Arrays.equals(strings, Objects.sort(new String[] { "D", "A", "C", "B" })));
   }


   @Test
   public void testSortWithComparator()
   {
      Person chris = new Person("Chris", "Flo");
      Person doug = new Person("Doug", "Flo");
      Person singer = new Person("Chris", "Singer");
      Comparator<Person> ordering = Comparator.comparing((Function<Person, String>) input -> input.getLastName()).thenComparing(new Function<Person, Comparable>() {
         @Override
         public Comparable apply(Person input)
         {
            return input.getFirstName();
         }
      });
      Person[] people = { chris, doug, singer };
      assertTrue(Arrays.equals(people, Objects.sort(new Person[] { doug, singer, chris }, ordering)));
   }


   @Test
   public void testOrderingOptimization()
   {
      assertSame(Objects.ordering(), Objects.ordering());
      assertNotSame(Objects.ordering(Comparator.<String>naturalOrder()), Objects.ordering(Comparator.<String>naturalOrder()));
   }


   @Test
   public void testIsArray()
   {
      assertTrue(Objects.isArray(new String[0]));
      assertTrue(Objects.isArray(new boolean[0]));
      assertTrue(Objects.isArray(new Integer[0]));
      assertTrue(Objects.isArray(new int[0]));
      assertTrue(Objects.isArray(new double[0]));
      assertTrue(Objects.isArray(new double[0][0]));

      assertFalse(Objects.isArray(Double.valueOf(2.5D)));
      assertFalse(Objects.isArray("Hello"));
      assertFalse(Objects.isArray(1));
   }




   @Test
   public void testSubset()
   {
      String[] strings = Objects.subset(new String[] {"Hello", "There", "Cuttie"}, 0, 2);
      assertTrue(Arrays.equals(new String[] {"Hello", "There"}, strings));

      Integer[] ints = Objects.subset(new Integer[] {1, 2, 3}, 0, 2);
      assertTrue(Arrays.equals(new Integer[] {1, 2}, ints));

      Long[] longs = Objects.subset(new Long[] {1L, 2L, 3L}, 0, 0);
      assertTrue(Arrays.equals(new Long[0], longs));
   }

   @Test
   public void testSubsetNullSourceArray()
   {
      assertThrows(NullPointerException.class, ()->{
         Objects.subset(null, 0, 2);
      });
   }

   @Test
   public void testSubsetEmptySourceArray()
   {
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Objects.subset(new String[0], 0, 2);
      });
   }

   @Test
   public void testSubsetNegativeOffset()
   {
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Objects.subset(new String[] {"Hello", "There", "Cuttie"}, -1, 2);
      });
   }


   @Test
   public void testSubsetNegativeLength()
   {
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Objects.subset(new String[] {"Hello", "There", "Cuttie"}, 0, -1);
      });
   }

   @Test
   public void testSubsetInvalidArrayOffset()
   {
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Objects.subset(new String[] {"Hello", "There", "Cuttie"}, 4, 2);
      });
   }

   @Test
   public void testSubsetInvalidArrayLength()
   {
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Objects.subset(new String[] {"Hello", "There", "Cuttie"}, 1, 3);
      });
   }



   @Test
   public void testEmptyIfNull()
   {
      assertEquals(1, Objects.emptyIfNull(new String[] {"hello"}, String.class).length);
      assertEquals(0, Objects.emptyIfNull(null, String.class).length);
      assertEquals(2, Objects.emptyIfNull(new Integer[] {1,2}, Integer.class).length);
      assertEquals(0, Objects.emptyIfNull(null, Integer.class).length);
   }



   @Test
   public void testObjectHashCodeWithNullElement()
   {
      assertTrue(Objects.hash("hello", null) != Objects.hash("hello"));
   }



   @Test
   public void testIsOneOf()
   {
      assertTrue(Objects.isOneOf("T", "T", "B", "C"));
      assertTrue(Objects.isOneOf("B", "T", "B", "C"));
      assertTrue(Objects.isOneOf("C", "T", "B", "C"));
      assertTrue(Objects.isOneOf(null, "T", "B", "C", null));
      assertFalse(Objects.isOneOf("D", "T", "B", "C"));
      assertFalse(Objects.isOneOf(null, "T", "B", "C"));
      assertFalse(Objects.isOneOf("D", null));
   }

   @Test
   public void testEqual()
   {
      assertTrue(Objects.equal(1,1,1,1));
      assertTrue(Objects.equal(null, null, null));
      assertTrue(Objects.equal("hi", "hi", "hi"));

      assertFalse(Objects.equal(1,1,1,2));
      assertFalse(Objects.equal(null, null, 1));
      assertFalse(Objects.equal("hi", "hi", null));
      assertFalse(Objects.equal("hi", "hi", "bye"));
   }


   @Test
   public void testIsSameType()
   {
      assertTrue(Objects.isSameType(1,12));
      assertTrue(Objects.isSameType("Hi","hi"));
      assertTrue(Objects.isSameType(new String[] {"hi"}, new String[] {"hello", "yellow"}));


      assertFalse(Objects.isSameType(1, 12.3D));
      assertFalse(Objects.isSameType(1, "hi"));
   }

   @Test
   public void testIsSameTypeNullLeft()
   {
      assertThrows(NullPointerException.class, ()->{
         Objects.isSameType(null,12);
      });
   }

   @Test
   public void testIsSameTypeNullRight()
   {
      assertThrows(NullPointerException.class, ()->{
         Objects.isSameType(1,null);
      });
   }



   @Test
   public void testCloneIntArray() throws Exception
   {
      int[] array = new int[] { 0, 1 };
      int[] copy = Objects.clone(array);
      assertFalse(array == copy);
      assertEquals(array[0], copy[0]);
      assertEquals(array[1], copy[1]);
   }

   @Test
   public void testCloneStringArray() throws Exception
   {
      String[] array = new String[] {"Hello", "There"};
      String[] copy = Objects.clone(array);
      assertFalse(array == copy);
      assertEquals(array[0], copy[0]);
      assertEquals(array[1], copy[1]);
   }

   @Test
   public void testCloneCloneable() throws Exception
   {
      List<String> list = new ArrayList<>();
      Collections.addAll(list, "Hello", "There");
      List<String> copy = Objects.clone(list);
      assertFalse(list == copy);
      assertEquals(list.get(0), copy.get(0));
      assertEquals(list.get(1), copy.get(1));
   }

   @Test
   public void testCloneNonCloneable() throws Exception
   {
      assertNull(Objects.clone(1));
   }



   @Test
   public void testPrepend()
   {
      String[] array = new String[] { "two", "three" };
      String[] result = Objects.prepend(array, "zero", "one");
      assertEquals(4, result.length);
      assertTrue(Arrays.equals(new String[] {"zero", "one", "two", "three"}, result));
   }

   @Test
   public void testPrependNullItems()
   {
      String[] array = new String[] { "two", "three" };
      String[] result = Objects.prepend(array, null);
      assertEquals(2, result.length);
      assertTrue(Arrays.equals(new String[] {"two", "three"}, result));
   }

   @Test
   public void testPrependNullArray()
   {
      String[] array = new String[] { "two", "three" };
      assertThrows(NullPointerException.class, ()->{
         String[] result = Objects.prepend(null, "one", "two");
      });
   }

   @Test
   public void testAppend()
   {
      String[] array = new String[] { "two", "three" };
      String[] result = Objects.append(array, "four", "five");
      assertEquals(4, result.length);
      assertTrue(Arrays.equals(new String[] {"two", "three", "four", "five"}, result));
   }

   @Test
   public void testAppendNullItems()
   {
      String[] array = new String[] { "two", "three" };
      String[] result = Objects.append(array, null);
      assertEquals(2, result.length);
      assertTrue(Arrays.equals(new String[] {"two", "three"}, result));
   }

   @Test
   public void testAppendNullArray()
   {
      String[] array = new String[] { "two", "three" };
      assertThrows(NullPointerException.class, ()->{
         String[] result = Objects.append(null, "four", "five");
      });
   }


   @Test
   public void testInsert()
   {
      String[] array = new String[] { "one", "four" };
      String[] result = Objects.insert(1, array, "two", "three");
      assertEquals(4, result.length);
      assertTrue(Arrays.equals(new String[] {"one", "two", "three", "four"}, result));
   }

   @Test
   public void testInsertNullItems()
   {
      String[] array = new String[] { "two", "three" };
      String[] result = Objects.insert(1, array, null);
      assertEquals(2, result.length);
      assertTrue(Arrays.equals(new String[] {"two", "three"}, result));
   }

   @Test
   public void testInsertNullArray()
   {
      String[] array = new String[] { "two", "three" };
      assertThrows(NullPointerException.class, ()->{
         String[] result = Objects.insert(1, null, "four", "five");
      });
   }

   @Test
   public void testInsertNegativeIndex()
   {
      String[] array = new String[] { "two", "three" };
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Objects.insert(-1, array, "four", "five");
      });
   }

   @Test
   public void testInsertIndexOutOfBounds()
   {
      String[] array = new String[] { "two", "three" };
      assertThrows(IndexOutOfBoundsException.class, ()->{
         Objects.insert(3, array, "four", "five");
      });
   }

   @Test
   public void testIfNull()
   {
      assertSame(Boolean.TRUE, Objects.ifNull(null, Boolean.TRUE));
      assertSame(Boolean.FALSE, Objects.ifNull(Boolean.FALSE, Boolean.TRUE));
   }

   @Test
   public void testFirstNonNull()
   {
      Integer ZERO = Integer.valueOf(0);
      Integer ONE = Integer.valueOf(1);
      Integer TWO = Integer.valueOf(2);
      Integer TEN = Integer.valueOf(10);

      assertSame(ZERO, Objects.firstNonNull(ZERO, ONE, TWO, TEN));
      assertSame(ONE, Objects.firstNonNull(null, ONE, TWO, TEN));
      assertSame(TWO, Objects.firstNonNull(null, null, TWO, TEN));
      assertSame(TEN, Objects.firstNonNull(null, null, null, TEN));
      assertSame(ONE, Objects.firstNonNull(null, ONE, null, TEN));
      assertNull(Objects.firstNonNull(null));
      assertNull(Objects.firstNonNull(null, null));
   }

   @Test
   public void testGetClass()
   {
      assertNull(Objects.getClass(null));
      assertSame(Integer.class, Objects.getClass(Integer.valueOf(0)));
      assertSame(Boolean.class, Objects.getClass(Boolean.TRUE));
      assertSame(Boolean.class, Objects.getClass(true));
   }


   @Test
   public void testMin()
   {
      assertEquals(1, (int) Objects.min(5,2,1,4));
      assertEquals("a", Objects.min("c","d","a","b"));
   }

   @Test
   public void testMinWithComparator()
   {
      assertEquals(5, (int) Objects.min(Comparator.<Integer>naturalOrder().reversed(), 5,2,1,4));
      assertEquals("d", Objects.min(Comparator.<String>naturalOrder().reversed(), "c","d","a","b"));
   }

   @Test
   public void testMax()
   {
      assertEquals(5, (int) Objects.max(5, 2, 1, 4));
      assertEquals("d", Objects.max("c", "d", "a", "b"));
   }

   @Test
   public void testMaxWithComparator()
   {
      assertEquals(1, (int) Objects.max(Comparator.<Integer>naturalOrder().reversed(), 5, 2, 1, 4));
      assertEquals("a", Objects.max(Comparator.<String>naturalOrder().reversed(), "c", "d", "a", "b"));
   }



   private static class Person {
      private String firstName;
      private String lastName;
      public Person(String firstName, String lastName)
      {
         this.firstName = firstName;
         this.lastName = lastName;
      }
      public String getFirstName() { return firstName; }
      public String getLastName() { return lastName; }
   }


}
