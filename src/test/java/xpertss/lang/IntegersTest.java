package xpertss.lang;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class IntegersTest {


   @Test
   public void testMin()
   {
      assertEquals(1, Integers.min(1,2,3,4));
      assertEquals(2, Integers.min(3,4,2,4));
      assertEquals(1, Integers.min(1));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testMinEmpty()
   {
      Integers.min();
   }

   @Test(expected = NullPointerException.class)
   public void testMinNull()
   {
      Integers.min((int[])null);
   }




   @Test
   public void testMax()
   {
      assertEquals(4, Integers.max(1, 2, 3, 4));
      assertEquals(4, Integers.max(3, 4, 2, 3));
      assertEquals(1, Integers.max(1));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testMaxEmpty()
   {
      Integers.max();
   }

   @Test(expected = NullPointerException.class)
   public void testMaxNull()
   {
      Integers.max((int[]) null);
   }




   @Test
   public void testMedian()
   {
      assertEquals(1, Integers.median(1));
      assertEquals(2, Integers.median(1, 2, 3, 4));
      assertEquals(3, Integers.median(1, 2, 4, 6));
      assertEquals(3, Integers.median(1, 2, 3, 4, 5));
      assertEquals(3, Integers.median(1, 3, 4, 5, 2));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testMedianEmpty()
   {
      Integers.median();
   }

   @Test(expected = NullPointerException.class)
   public void testMedianNull()
   {
      Integers.median((int[]) null);
   }



   @Test
   public void testMode()
   {
      int[] empty = new int[0];
      assertTrue(Arrays.equals(empty, Integers.mode(4)));
      assertTrue(Arrays.equals(empty, Integers.mode(1,2,3,4)));
      assertTrue(Arrays.equals(empty, Integers.mode(4, 3, 2, 1)));

      int[] oneTwoThree = Integers.toArray(1,2,3);
      assertTrue(Arrays.equals(oneTwoThree, Integers.mode(1,1,2,2,3,3,4)));

      int[] twoThree = Integers.toArray(2,3);
      assertTrue(Arrays.equals(twoThree, Integers.mode(1,2,2,3,3,4)));
      assertTrue(Arrays.equals(twoThree, Integers.mode(1,3,3,2,2,4)));

      int[] three = Integers.toArray(3);
      assertTrue(Arrays.equals(three, Integers.mode(1,2,2,3,3,3,4)));
      assertTrue(Arrays.equals(three, Integers.mode(1,3,4,2,3,2,5,3,8,3,4)));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testModeEmpty()
   {
      Integers.mode();
   }

   @Test(expected = NullPointerException.class)
   public void testModeNull()
   {
      Integers.mode((int[]) null);
   }




   @Test
   public void testMean()
   {
      assertEquals(1, Integers.mean(1));
      assertEquals(2, Integers.mean(2));
      assertEquals(1, Integers.mean(1, 2));
      assertEquals(2, Integers.mean(1, 2, 3, 4));
      assertEquals(3, Integers.mean(1, 2, 3, 4, 5));
      assertEquals(3, Integers.mean(1, 3, 4, 5, 2));
      assertEquals(150, Integers.mean(100, 200));
      assertEquals(Integer.MAX_VALUE, Integers.mean(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE));
      assertEquals(Integer.MIN_VALUE, Integers.mean(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testMeanEmpty()
   {
      Integers.mean();
   }

   @Test(expected = NullPointerException.class)
   public void testMeanNull()
   {
      Integers.mean((int[]) null);
   }





   @Test
   public void testOrdering()
   {
      int[] zero = new int[0];
      int[] one = {1};
      int[] two= {1,1};
      int[] three = {1,2};
      int[] four = {2};

      assertEquals(-1, Integers.natural().compare(zero, one));
      assertEquals(-1, Integers.natural().compare(zero, two));
      assertEquals(-1, Integers.natural().compare(zero, three));
      assertEquals(-1, Integers.natural().compare(zero, four));

      assertEquals(-1, Integers.natural().compare(one, two));
      assertEquals(-1, Integers.natural().compare(one, three));
      assertEquals(-1, Integers.natural().compare(one, four));

      assertEquals(-1, Integers.natural().compare(two, three));
      assertEquals(-1, Integers.natural().compare(two, four));

      assertEquals(-1, Integers.natural().compare(three, four));
   }


   @Test
   public void testParse()
   {
      assertEquals(10, Integers.parse("10", -1));
      assertEquals(10, Integers.parse(new StringBuilder("10"), -1));
   }


}
