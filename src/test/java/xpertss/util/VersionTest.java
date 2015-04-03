/**
 * Created By: cfloersch
 * Date: 2/7/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class VersionTest {


   @Test
   public void testBasic()
   {
      Version version = new Version(6,1);
      assertTrue(version.isAtLeast(5,0));
      assertTrue(version.isAtLeast(6,0));
      assertTrue(version.isAtLeast(6,1));
   }


}
