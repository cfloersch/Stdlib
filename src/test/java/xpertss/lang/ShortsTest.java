package xpertss.lang;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

public class ShortsTest {

   @Test
   public void testParse()
   {
      assertEquals((short)10, Shorts.parse("10", (short) -1));
      assertEquals((short)10, Shorts.parse(new StringBuilder("10"), (short) -1));
   }


}