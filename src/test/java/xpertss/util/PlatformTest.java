/**
 * Created By: cfloersch
 * Date: 2/6/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class PlatformTest {



   public void testCurrentOnWindowsSeven()
   {
      // difficult to test as properties are cached on class load
      System.setProperty("os.name", "Windows 7");
      System.setProperty("os.version", "6.1");

      Platform current = Platform.current();
      assertSame(Platform.WinAero, current);
      Version version = Platform.osVersion();
      assertEquals(6, version.getMajorVersion());
      assertEquals(1, version.getMinorVersion());
      assertEquals(0, version.getPointVersion());
      assertFalse(current.isUnix());
   }

   public void testCurrentOnHPUXC5510()
   {
      // difficult to test as properties are cached on class load
      System.setProperty("os.name", "HP UX");
      System.setProperty("os.version", "C.55.10");

      Platform current = Platform.current();
      assertSame(Platform.Unix, current);
      Version version = Platform.osVersion();
      assertEquals(55, version.getMajorVersion());
      assertEquals(10, version.getMinorVersion());
      assertEquals(0, version.getPointVersion());
      assertTrue(current.isUnix());
   }


   private static final String[][][] results = {
         {{ "C.55.00" }, { "C", "55", "00" }},
         {{ "B.10.20" }, { "B", "10", "20" }},
         {{ "2.2.2-RELEASE" }, { "2", "2", "2", "RELEASE" }},
         {{ "V5.1" }, { "V5", "1" }},
         {{ "V7.2-1" }, { "V7", "2", "1" }},
         {{ "3.0 build 11171" }, { "3", "0", "build", "11171" }},
         {{ "10.3.1" }, { "10", "3", "1" }},
         {{ "4.10" }, { "4", "10" }},

   };

   @Test
   public void testSplit()
   {
      for(String[][] test : results) {
         String[] parts = test[0][0].split("[\\.\\s-]");
         assertTrue(Arrays.equals(test[1], parts));
      }
   }

   @Test
   public void testUserPrincipal()
   {
      System.setProperty("user.name","jblow");
      assertEquals("jblow", Platform.userPrincipal().getName());
   }

}
