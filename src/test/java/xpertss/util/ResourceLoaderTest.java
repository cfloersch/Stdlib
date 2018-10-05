package xpertss.util;

import org.junit.Test;


import static org.junit.Assert.*;

/*
 * Copyright 2018 XpertSoftware
 *
 * Created By: cfloersch
 * Date: 7/13/2018
 */
public class ResourceLoaderTest {
   
   @Test
   public void testGetResourceWithNonExistentResource()
   {
      assertNull(ResourceLoader.getResource("doesnotexist.xml"));
   }

   @Test(expected = NullPointerException.class)
   public void testGetResourceWithNull()
   {
      assertNull(ResourceLoader.getResource(null));
   }

}