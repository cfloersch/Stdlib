package xpertss.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

   @Test
   public void testGetResourceWithNull()
   {
      assertThrows(NullPointerException.class, ()->{
         ResourceLoader.getResource(null);
      });
   }

}