/**
 * Created By: cfloersch
 * Date: 1/3/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import org.junit.Test;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import static junit.framework.Assert.*;

public class SetsTest {



   @Test
   public void testNewSetStandardSets()
   {
      assertSame(LinkedHashSet.class, Sets.newSet(Sets.newLinkedHashSet()).getClass());
      assertSame(HashSet.class, Sets.newSet(Sets.newHashSet()).getClass());
      assertSame(CopyOnWriteArraySet.class, Sets.newSet(Sets.newCopyOnWriteArraySet()).getClass());

      Set<String> treeSetWithoutComparator = Sets.newSet(Sets.newTreeSet((Comparator)null));
      assertSame(TreeSet.class, Sets.newSet(Sets.newTreeSet((Comparator)null)).getClass());
      assertNull(((SortedSet) treeSetWithoutComparator).comparator());

      Set<String> treeSetWithComparator = Sets.newSet(Sets.newTreeSet(Collections.reverseOrder()));
      assertSame(TreeSet.class, treeSetWithComparator.getClass());
      assertNotNull(((SortedSet)treeSetWithComparator).comparator());

      Set<String> concurrentSetWithoutComparator = Sets.newSet(Sets.newConcurrentSkipListSet((Comparator)null));
      assertSame(ConcurrentSkipListSet.class, concurrentSetWithoutComparator.getClass());
      assertNull(((SortedSet) concurrentSetWithoutComparator).comparator());

      Set<String> concurrentSetWithComparator = Sets.newSet(Sets.newConcurrentSkipListSet(Collections.reverseOrder()));
      assertSame(ConcurrentSkipListSet.class, concurrentSetWithComparator.getClass());
      assertNotNull(((SortedSet) concurrentSetWithComparator).comparator());
   }

   @Test
   public void testNewSetWrapperSets()
   {
      Set<String> set = Sets.newLinkedHashSet();
      assertSame(LinkedHashSet.class, Sets.newSet(Collections.checkedSet(set, String.class)).getClass());
      assertSame(LinkedHashSet.class, Sets.newSet(Collections.unmodifiableSet(set)).getClass());
      assertSame(LinkedHashSet.class, Sets.newSet(Collections.synchronizedSet(set)).getClass());
      assertSame(LinkedHashSet.class, Sets.newSet(Collections.singleton("Hello")).getClass());
   }

   @Test
   public void testNewSetViewSets()
   {
      Map<String,String> map = new HashMap<>();
      assertSame(LinkedHashSet.class, Sets.newSet(map.keySet()).getClass());
      assertSame(LinkedHashSet.class, Sets.newSet(map.entrySet()).getClass());
   }




   @Test
   public void testFirst()
   {
      Set<Integer> one = Sets.of(1, 2, 3, 4);
      assertEquals(Integer.valueOf(1), Sets.first(one));

      Set<Integer> two = Sets.newHashSet(4, 3, 2, 1);
      Integer first = two.iterator().next();
      assertEquals(first, Sets.first(two));
   }

   @Test
   public void testLast()
   {
      Set<Integer> one = Sets.of(1, 2, 3, 4);
      assertEquals(Integer.valueOf(4), Sets.last(one));

      Set<Integer> two = Sets.newHashSet(4, 3, 2, 1);
      Integer first = null;
      for(Integer item : two) {
         first = item;
      }
      assertEquals(first, Sets.last(two));
   }

}
