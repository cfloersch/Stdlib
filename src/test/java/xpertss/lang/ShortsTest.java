package xpertss.lang;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortsTest {

   @Test
   public void testParse()
   {
      assertEquals((short)10, Shorts.parse("10", (short) -1));
      assertEquals((short)10, Shorts.parse(new StringBuilder("10"), (short) -1));
   }


}