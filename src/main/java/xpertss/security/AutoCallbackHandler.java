/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 20, 2004
 * Time: 2:55:57 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package xpertss.security;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import java.io.IOException;

/**
 * This class allows back end processes which do not have a capability
 * to interact with a user to support the CallbackHandler authentication
 * interface with a property based username and password.
 * <p>
 * Rather than prompting for a username and password on a handle callback
 * operation this will iterate the callbacks and set the username and
 * password that it was constructed with for every NameCallback and
 * PasswordCallback respectively.
 * <p>
 * Note it does not discern between callback types. For example a single
 * handler could be used for both a Proxy Auth and an HTTP Auth. If the
 * passwords are different on the two machines then this class will fail.
 */
public class AutoCallbackHandler implements CallbackHandler {

   private String user;
   private char[] pass;

   public AutoCallbackHandler(String user, String pass)
   {
      this.user = user;
      this.pass = pass.toCharArray();
   }

   public void handle(Callback[] callbacks)
      throws IOException, UnsupportedCallbackException
   {
      int count = 0;
      for(Callback callback : callbacks) {
         if(callback instanceof NameCallback) {
            NameCallback name = (NameCallback) callback;
            name.setName(user);
            count++;
         } else if(callback instanceof PasswordCallback) {
            PasswordCallback passwd = (PasswordCallback) callback;
            passwd.setPassword(pass);
            count++;
         }
         if(count >= 2) break;
      }
   }

}
