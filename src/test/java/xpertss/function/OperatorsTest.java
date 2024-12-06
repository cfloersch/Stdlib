/**
 * Created By: cfloersch
 * Date: 2/6/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.function;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class OperatorsTest {


   @Test
   public void testIdentity()
   {
      assertNull(Operators.identity().apply(null));

      String str = "Hello";
      assertSame(str, Operators.identity().apply(str));

      Integer i = 1;
      assertSame(i, Operators.identity().apply(i));

      Long l = 1L;
      assertSame(l, Operators.identity().apply(l));

      Double d = 1D;
      assertSame(d, Operators.identity().apply(d));
   }


}
