/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Feb 6, 2003
 * Time: 9:31:39 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package xpertss.security;

import xpertss.lang.Objects;
import xpertss.lang.Strings;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import java.net.PasswordAuthentication;
import java.net.Authenticator;
import java.net.InetSocketAddress;

/**
 * A simple java.net.Authenticator wrapper which converts that old crappy method for
 * password authentication that all protocol handlers use with the newer Callback
 * mechanism introduced with the JAAS package.
 */
public class AuthenticatorCallbackWrapper extends Authenticator {

   private CallbackHandler handler;
   private String namePrompt;
   private String passPrompt;

   private String defaultUserName;

   /**
    * Construct a simple Authenticator wrapper with the supplied CallbackHandler
    *
    * @throws NullPointerException If the handler is {@code null}
    */
   public AuthenticatorCallbackWrapper(CallbackHandler handler)
   {
      this.handler = Objects.notNull(handler);
   }

   /**
    * Construct a simple Authenticator wrapper with the supplied CallbackHandler
    *
    * @param handler - CallbackHandler implementation that will handle authentication requests
    * @param namePrompt - The name label applied to the NameCallback. Ideal for Internationaliztion of
    *                      display labels
    * @param passPrompt - The password label applied to the PasswordCallback. Ideal for
    *                      Internationaliztion of display label.
    * @throws NullPointerException If the handler is {@code null}
    * @throws IllegalArgumentException If name or password are empty
    */
   public AuthenticatorCallbackWrapper(CallbackHandler handler, String namePrompt, String passPrompt)
   {
      this.handler = Objects.notNull(handler);
      this.namePrompt = Strings.notEmpty(namePrompt, "namePrompt");
      this.passPrompt = Strings.notEmpty(passPrompt, "passPrompt");
   }


   /**
    * Get the default user name. This value will allways be the last username entered and returned
    * by the NameCallback.
    */
   public String getDefaultUserName()
   {
      return defaultUserName;
   }

   /**
    * Set the initial default username to pass back with the NameCallback. This value will
    * change with each successive NameCallback value assuming that the NameCallback getName()
    * returns a value different from the default.
    */
   public void setDefaultUserName(String defaultUserName)
   {
      this.defaultUserName = defaultUserName;
   }



   /**
    * Internal method implementation that gets called by the Authenticator architecture. This
    * implements the wrapper logic.
    */
   protected PasswordAuthentication getPasswordAuthentication()
   {
      NameCallback namecb = new NameCallback(namePrompt, defaultUserName);
      PasswordCallback passcb = new PasswordCallback(passPrompt, true);

      Callback[] callbacks = new Callback[3];
      InetSocketAddress addr = new InetSocketAddress(getRequestingSite(), getRequestingPort());
      callbacks[0] = new TCPHostCallback(addr, getRequestingProtocol(),
                                             getRequestingPrompt(), getRequestingScheme());
      callbacks[1] = namecb;
      callbacks[2] = passcb;
      try {
         handler.handle(callbacks);
         if(namecb.getName() != null) defaultUserName = namecb.getName();
         return new PasswordAuthentication(namecb.getName(), passcb.getPassword());
      } catch(Exception ex) {
         return null;
      } finally {
         passcb.clearPassword();
      }
   }

}
