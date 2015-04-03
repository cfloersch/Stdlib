package xpertss.security;


/**
 * This exception is useful in reporting that a login procedure failed.
 *
 * @author Chris Floersch
 * @version 1.0
 */
public class LoginFailedException extends SecurityException {

   public LoginFailedException() {
      super();
   }
   
   
   public LoginFailedException(String msg) {
      super(msg);
   }

   
}