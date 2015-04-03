package xpertss.util;

import org.junit.Test;
import xpertss.function.Function;
import xpertss.lang.Integers;
import xpertss.lang.Objects;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

public class OrderingTest {


   @Test
   public void testFrom()
   {
      assertSame(Ordering.natural(), Ordering.from(Ordering.<String>natural()));
   }

   @Test
   public void testNaturalOrdering()
   {
      Ordering<String> natural = Ordering.natural();
      assertEquals(-1, natural.compare("A", "B"));
      assertEquals(0, natural.compare("A", "A"));
      assertEquals(1, natural.compare("B", "A"));

      natural = natural.reverse();

      assertEquals(1, natural.compare("A", "B"));
      assertEquals(0, natural.compare("A", "A"));
      assertEquals(-1, natural.compare("B", "A"));

      assertSame(Ordering.natural(), Ordering.natural());
   }

   @Test
   public void testReversedNaturalOrdering()
   {
      Ordering<String> natural = Ordering.reversed();
      assertEquals(1, natural.compare("A", "B"));
      assertEquals(0, natural.compare("A", "A"));
      assertEquals(-1, natural.compare("B", "A"));

      natural = natural.reverse();

      assertEquals(-1, natural.compare("A", "B"));
      assertEquals(0, natural.compare("A", "A"));
      assertEquals(1, natural.compare("B", "A"));

      assertSame(Ordering.reversed(), Ordering.reversed());
   }

   @Test(expected = NullPointerException.class)
   public void testNaturalOrderingNullRightElement()
   {
      Ordering.<String>natural().compare("A", null);
   }

   @Test(expected = NullPointerException.class)
   public void testNaturalOrderingNullLeftElement()
   {
      Ordering.<String>natural().compare(null, "A");
   }



   @Test
   public void testNaturalOrderingNullsFirst()
   {
      Ordering<String> natural = Ordering.nullsFirst();
      assertEquals(-1, natural.compare(null, "B"));
      assertEquals(0, natural.compare("A", "A"));
      assertEquals(1, natural.compare("B", null));

      natural = natural.reverse();

      assertEquals(1, natural.compare(null, "B"));
      assertEquals(0, natural.compare("A", "A"));
      assertEquals(-1, natural.compare("B", null));
   }

   @Test
   public void testOrderingNullsFirst()
   {
      Ordering<String> natural = Ordering.nullsFirst(null);
      assertEquals(-1, natural.compare(null, "B"));
      assertEquals(0, natural.compare("A", "B"));
      assertEquals(1, natural.compare("B", null));

      natural = natural.reverse();

      assertEquals(1, natural.compare(null, "B"));
      assertEquals(0, natural.compare("A", "B"));
      assertEquals(-1, natural.compare("B", null));
   }

   @Test
   public void testNaturalOrderingNullsLast()
   {
      Ordering<String> natural = Ordering.nullsLast();
      assertEquals(1, natural.compare(null, "B"));
      assertEquals(0, natural.compare("A", "A"));
      assertEquals(-1, natural.compare("B", null));

      natural = natural.reverse();

      assertEquals(-1, natural.compare(null, "B"));
      assertEquals(0, natural.compare("A", "A"));
      assertEquals(1, natural.compare("B", null));
   }

   @Test
   public void testOrderingNullsLast()
   {
      Ordering<String> natural = Ordering.nullsLast(null);
      assertEquals(1, natural.compare(null, "B"));
      assertEquals(0, natural.compare("A", "B"));
      assertEquals(-1, natural.compare("B", null));

      natural = natural.reverse();

      assertEquals(-1, natural.compare(null, "B"));
      assertEquals(0, natural.compare("A", "B"));
      assertEquals(1, natural.compare("B", null));
   }


   @Test
   public void testCompund()
   {
      Person chrisFlo = new Person("Chris", "Flo", 40);
      Person chrisSing = new Person("Chris", "Sing", 39);
      Person dougFlo = new Person("Doug", "Flo", 39);
      Person dougJones = new Person("Doug", "Jones", 30);

      Ordering<Person> ordering = Ordering.comparing(new Function<Person, String>() {
         @Override
         public String apply(Person input)
         {
            return input.getLastName();
         }
      }).thenComparing(new Function<Person, String>() {
         @Override
         public String apply(Person input)
         {
            return input.getFirstName();
         }
      });

      assertEquals(-1, ordering.compare(chrisFlo, chrisSing));
      assertEquals(-1, ordering.compare(dougFlo, dougJones));
      assertEquals(-1, ordering.compare(chrisFlo, dougFlo));
      assertEquals(-1, ordering.compare(dougJones, chrisSing));

      Ordering<Person> reverse = ordering.reverse();

      assertEquals(1, reverse.compare(chrisFlo, chrisSing));
      assertEquals(1, reverse.compare(dougFlo, dougJones));
      assertEquals(1, reverse.compare(chrisFlo, dougFlo));
      assertEquals(1, reverse.compare(dougJones, chrisSing));

      Ordering<Person> withNull = Ordering.nullsFirst(ordering);
      assertEquals(-1, withNull.compare(null, chrisSing));
      assertEquals(-1, withNull.compare(chrisFlo, chrisSing));
      assertEquals(-1, withNull.compare(dougFlo, dougJones));
      assertEquals(-1, withNull.compare(chrisFlo, dougFlo));
      assertEquals(-1, withNull.compare(dougJones, chrisSing));
      assertEquals(1, withNull.compare(dougJones, null));

      Ordering<Person> withNullReversed = withNull.reverse();
      assertEquals(1, withNullReversed.compare(null, chrisSing));
      assertEquals(1, withNullReversed.compare(chrisFlo, chrisSing));
      assertEquals(1, withNullReversed.compare(dougFlo, dougJones));
      assertEquals(1, withNullReversed.compare(chrisFlo, dougFlo));
      assertEquals(1, withNullReversed.compare(dougJones, chrisSing));
      assertEquals(-1, withNullReversed.compare(dougJones, null));

   }


   @Test
   public void testThenComparingWithOrdering()
   {
      Person chrisOne = new Person("Chris", "Flo", 40);
      Person chrisTwo = new Person("chris", "Flo", 39);

      Ordering<Person> ordering = Ordering.comparing(new Function<Person, Comparable>() {
         @Override
         public Comparable apply(Person input)
         {
            return input.getLastName();
         }
      }).thenComparing(new Function<Person, String>() {
         @Override
         public String apply(Person input)
         {
            return input.getFirstName();
         }
      }, Ordering.<String>from(String.CASE_INSENSITIVE_ORDER));

      assertEquals(0, ordering.compare(chrisOne, chrisTwo));

      Ordering<Person> reverse = ordering.reverse();

      assertEquals(0, reverse.compare(chrisOne, chrisTwo));
   }


   @Test
   public void testNullsLastStringsReversed()
   {
      Ordering<String> ordering = Ordering.nullsLast(Ordering.<String>reversed());
      assertEquals(1, ordering.compare(null, "Hello"));
      assertEquals(1, ordering.compare("Goodbye", "Hello"));
      assertEquals(-1, ordering.compare("Doug", "Chris"));
      assertEquals(-1, ordering.compare("Hello", null));
      assertEquals(0, ordering.compare(null, null));

      Ordering<String> reversed = ordering.reverse();
      assertEquals(-1, reversed.compare(null, "Hello"));
      assertEquals(-1, reversed.compare("Goodbye", "Hello"));
      assertEquals(1, reversed.compare("Doug", "Chris"));
      assertEquals(1, reversed.compare("Hello", null));
      assertEquals(0, reversed.compare(null, null));


   }


   @Test
   public void testOrderingIntArray()
   {
      int[] zero = {};
      int[] first = {0,1};
      int[] second = {0,1,2};
      int[] third = {2};

      Ordering<int[]> ordering = Ordering.from(Integers.natural());
      assertEquals(-1, ordering.compare(zero, first));
      assertEquals(-1, ordering.compare(first, second));
      assertEquals(-1, ordering.compare(second, third));
      assertEquals(-1, ordering.compare(first, third));

      Ordering<int[]> reverse = ordering.reverse();
      assertEquals(1, reverse.compare(zero, first));
      assertEquals(1, reverse.compare(first, second));
      assertEquals(1, reverse.compare(second, third));
      assertEquals(1, reverse.compare(first, third));


   }

   @Test
   public void testOrderingIntArrayNullsFirst()
   {
      int[] zero = {};
      int[] first = {0,1};
      int[] second = {0,1,2};
      int[] third = {2};

      Ordering<int[]> ordering = Ordering.nullsFirst(Integers.natural());
      assertEquals(0, ordering.compare(null, null));
      assertEquals(-1, ordering.compare(null, zero));
      assertEquals(-1, ordering.compare(zero, first));
      assertEquals(-1, ordering.compare(first, second));
      assertEquals(-1, ordering.compare(second, third));
      assertEquals(-1, ordering.compare(first, third));
      assertEquals(1, ordering.compare(third, null));
   }


   @Test
   public void testIntegerArray()
   {
      Integer[] zero = {};
      Integer[] first = {0,1};
      Integer[] second = {0,1,2};
      Integer[] third = {2};


      Ordering<Integer[]> ordering = Ordering.from(Objects.<Integer>ordering());
      assertEquals(-1, ordering.compare(zero, first));
      assertEquals(-1, ordering.compare(first, second));
      assertEquals(-1, ordering.compare(second, third));
      assertEquals(-1, ordering.compare(first, third));
   }




   private static class Person {

      private String first;
      private String last;
      private int age;
      private Person(String first, String last, int age)
      {
         this.first = first;
         this.last = last;
         this.age = age;
      }
      public String getFirstName() { return first; }
      public String getLastName() { return last; }
      public int getAge() { return age; }

   }

}


