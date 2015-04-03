/**
 * Created By: cfloersch
 * Date: 4/3/2015
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

/**
 * General runtime exception that indicates a resource was not found.
 */
public class ResourceNotFoundException extends RuntimeException {

   public ResourceNotFoundException()
   {
   }

   public ResourceNotFoundException(String message)
   {
      super(message);
   }

   public ResourceNotFoundException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public ResourceNotFoundException(Throwable cause)
   {
      super(cause);
   }
}
