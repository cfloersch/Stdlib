/**
 * Created By: cfloersch
 * Date: 1/12/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;

import org.junit.Test;
import xpertss.lang.Numbers;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

public class FunctionsTest {



   @Test
   public void testToString()
   {
      String str = "Hello";
      assertEquals("Hello", Functions.string().apply(str));

      Integer i = 1;
      assertEquals("1", Functions.string().apply(i));

      Long l = 1L;
      assertEquals("1", Functions.string().apply(l));

      Double d = 1D;
      assertEquals("1.0", Functions.string().apply(d));
   }

   @Test(expected = NullPointerException.class)
   public void testToStringNull()
   {
      Functions.string().apply(null);
   }



   @Test
   public void testForMap()
   {
      Map<String,String> map = new HashMap<>();
      map.put("1","1");
      map.put("2","2");
      map.put("3","3");
      map.put("4","4");
      Function<String,String> function = Functions.forMap(map);
      assertSame(map.get("1"), function.apply("1"));
      assertSame(map.get("2"), function.apply("2"));
      assertSame(map.get("3"), function.apply("3"));
      assertSame(map.get("4"), function.apply("4"));
   }

   @Test(expected = NullPointerException.class)
   public void testForMapNull()
   {
      Functions.forMap(null);
   }

   @Test
   public void testCompose()
   {
      Function<Integer,String> intToString = Functions.string();
      Function<String,Long> stringToLong = new Function<String,Long>() {
         @Override public Long apply(String input)
         {
            return Long.parseLong(input);
         }
      };
      Function<Integer,Long> intToLong = Functions.compose(intToString, stringToLong);
      assertTrue(Numbers.equal(intToLong.apply(Integer.valueOf(1))));
      assertSame(Long.class, intToLong.apply(Integer.valueOf(1)).getClass());
   }

}
