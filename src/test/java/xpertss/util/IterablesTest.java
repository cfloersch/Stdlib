package xpertss.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by cfloersch on 1/17/14.
 */
public class IterablesTest {

   @Test
   public void testOrderingOptimization()
   {
      assertSame(Iterables.ordering(), Iterables.ordering());
      assertNotSame(Iterables.ordering(Comparator.<String>naturalOrder()), Iterables.ordering(Comparator.<String>naturalOrder()));
   }

   @Test
   public void testList()
   {
      List<String> zero = Lists.of();
      List<String> one = Lists.of("Hello");
      List<String> two = Lists.of("Hello", "goodbye");

      List<String> left = Lists.of("Hello");
      List<String> right = Lists.of("Goodbye");

      Comparator<Iterable<String>> comparator = Iterables.ordering();
      assertEquals(-1, comparator.compare(zero, one));
      assertEquals(-1, comparator.compare(one, two));
      assertEquals(1, comparator.compare(two, zero));

      assertEquals(1, comparator.compare(left, right));
      assertEquals(-1, comparator.compare(right, left));

      assertEquals(0, comparator.compare(left, Lists.of("Hello")));
      assertEquals(0, comparator.compare(zero, Lists.<String>of()));
      assertEquals(0, comparator.compare(right, Lists.of("Goodbye")));

      Comparator<Iterable<String>> reversedElem = Iterables.ordering(Comparator.<String>naturalOrder().reversed());
      assertEquals(-1, reversedElem.compare(zero, one));
      assertEquals(-1, reversedElem.compare(one, two));
      assertEquals(1, reversedElem.compare(two, zero));

      assertEquals(-1, reversedElem.compare(left, right));
      assertEquals(1, reversedElem.compare(right, left));

      assertEquals(0, reversedElem.compare(left, Lists.of("Hello")));
      assertEquals(0, reversedElem.compare(zero, Lists.<String>of()));
      assertEquals(0, reversedElem.compare(right, Lists.of("Goodbye")));
   }


   @Test
   public void testForEach()
   {
      final List<String> copy = new ArrayList<>();
      List<String> source = Lists.of("Chris", "Carl", "Fred", "Bob", "Alex", "Craig");
      Iterables.forEach(source, s -> {
         if(s.toLowerCase().startsWith("c")) copy.add(s);
      });
      assertEquals(Lists.of("Chris", "Carl", "Craig"), copy);
   }




   @Test
   public void testAny()
   {
      List<String> source = Lists.of("Chris", "Carl", "Fred", "Bob", "Alex", "Craig");
      assertTrue(Iterables.any(source, input -> input.startsWith("C")));
      assertFalse(Iterables.any(source, input -> input.startsWith("c")));
   }

   @Test
   public void testAll()
   {
      List<String> source = Lists.of("Chris", "Carl", "Fred", "Bob", "Alex", "Craig");
      assertTrue(Iterables.all(source, input -> Character.isUpperCase(input.charAt(0))));
      assertFalse(Iterables.all(source, input -> input.startsWith("C")));
   }

   @Test
   public void testNone()
   {
      List<String> source = Lists.of("Chris", "Carl", "Fred", "Bob", "Alex", "Craig");
      assertTrue(Iterables.none(source, input -> Character.isLowerCase(input.charAt(0))));
      assertFalse(Iterables.none(source, input -> input.startsWith("C")));
   }





   @Test
   public void testMin()
   {
      List<Integer> numbers = Lists.of(2,5,1,4,3);
      assertEquals(Integer.valueOf(1), Iterables.min(numbers));
      List<String> strings = Lists.of("b", "d",  "a", "c");
      assertEquals("a", Iterables.min(strings));
   }

   @Test
   public void testMinWithComparator()
   {
      List<Integer> numbers = Lists.of(2,5,1,4,3);
      assertEquals(Integer.valueOf(5), Iterables.min(numbers, Comparator.<Integer>naturalOrder().reversed()));
      List<String> strings = Lists.of("b", "d",  "a", "c");
      assertEquals("d", Iterables.min(strings, Comparator.<String>naturalOrder().reversed()));
   }


   @Test
   public void testMax()
   {
      List<Integer> numbers = Lists.of(2,5,1,4,3);
      assertEquals(Integer.valueOf(5), Iterables.max(numbers));
      List<String> strings = Lists.of("b", "d",  "a", "c");
      assertEquals("d", Iterables.max(strings));
   }

   @Test
   public void testMaxWithComparator()
   {
      List<Integer> numbers = Lists.of(2,5,1,4,3);
      assertEquals(Integer.valueOf(1), Iterables.max(numbers, Comparator.<Integer>naturalOrder().reversed()));
      List<String> strings = Lists.of("b", "d",  "a", "c");
      assertEquals("a", Iterables.max(strings, Comparator.<String>naturalOrder().reversed()));
   }

}
