package xpertss.util;

import org.junit.Test;
import xpertss.function.Consumer;
import xpertss.function.Predicate;

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
      assertNotSame(Iterables.ordering(Ordering.natural()), Iterables.ordering(Ordering.natural()));
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

      Comparator<Iterable<String>> reversedElem = Iterables.ordering(Ordering.<String>reversed());
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
      Iterables.forEach(source, new Consumer<String>() {
         @Override public void apply(String s)
         {
            if(s.toLowerCase().startsWith("c")) copy.add(s);
         }
      });
      assertEquals(Lists.of("Chris", "Carl", "Craig"), copy);
   }

   @Test
   public void testAny()
   {
      List<String> source = Lists.of("Chris", "Carl", "Fred", "Bob", "Alex", "Craig");
      assertTrue(Iterables.any(source, new Predicate<String>() {
         @Override public boolean apply(String input)
         {
            return input.startsWith("C");
         }
      }));
      assertFalse(Iterables.any(source, new Predicate<String>() {
         @Override public boolean apply(String input)
         {
            return input.startsWith("c");
         }
      }));
   }

   @Test
   public void testAll()
   {
      List<String> source = Lists.of("Chris", "Carl", "Fred", "Bob", "Alex", "Craig");
      assertTrue(Iterables.all(source, new Predicate<String>() {
         @Override
         public boolean apply(String input) {
            return Character.isUpperCase(input.charAt(0));
         }
      }));
      assertFalse(Iterables.all(source, new Predicate<String>() {
         @Override
         public boolean apply(String input) {
            return input.startsWith("C");
         }
      }));
   }

   @Test
   public void testNone()
   {
      List<String> source = Lists.of("Chris", "Carl", "Fred", "Bob", "Alex", "Craig");
      assertTrue(Iterables.none(source, new Predicate<String>() {
         @Override
         public boolean apply(String input) {
            return Character.isLowerCase(input.charAt(0));
         }
      }));
      assertFalse(Iterables.none(source, new Predicate<String>() {
         @Override
         public boolean apply(String input) {
            return input.startsWith("C");
         }
      }));
   }

}
