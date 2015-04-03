/**
 * Date: Mar 31, 2005
 * Copyright 2014 XpertSoftware
 */
package xpertss.lang;

/**
 * Unchecked exception which indicates that a buffer is insufficient in limit
 * for the data that needs to be written.
 * <p/>
 * TODO Get rid of this if its just duplicating java.nio.BufferOverflowException
 */
public class OverflowException extends RuntimeException {

   public OverflowException()
   {
      super();
   }

   public OverflowException(String msg)
   {
      super(msg);
   }

}
