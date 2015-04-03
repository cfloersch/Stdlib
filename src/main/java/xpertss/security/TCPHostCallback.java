/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 29, 2002
 * Time: 1:03:33 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package xpertss.security;

import javax.security.auth.callback.Callback;
import java.net.InetSocketAddress;

/**
 * A TCP Host Callback is the prompt information a network connection should use
 * to signal the CallbackHandler that authentication credentials are desired. This
 * object should contain details info the CallbackHandler can display to the user.
 */
public class TCPHostCallback implements Callback {

   private InetSocketAddress site;
   private String protocol;
   private String prompt;
   private String scheme;

   public TCPHostCallback(InetSocketAddress site, String protocol, String prompt, String scheme)
   {
      this.site = site;
      this.protocol = protocol;
      this.prompt = prompt;
      this.scheme = scheme;
   }

   /**
    * This returns the hostname of the server requesting authentication so that the user
    * may determine if he/she either one has a login for the site or two wants to give
    * his/her credentials to the site.
    */
   public String getRequestingHost()
   {
      return site.getHostName();
   }

   /**
    * This may be useful to know not only what site but also what port they are
    * authenticating against.
    */
   public int getRequestingPort()
   {
      return site.getPort();
   }


   /**
    * Give the protocol that's requesting the connection. Often this will be based on a
    * URL, but in a future SDK it could be, for example, "SOCKS" for a password-protected
    * SOCKS5 firewall.
    */
   public String getRequestingProtocol()
   {
      return protocol;
   }


   /**
    * This is a String prompt to display to the user. In HTTP it is typically the realm
    * the user is being asked to login to. In Ftp it is the welcome message.
    */
   public String getRequestingPrompt()
   {
      return prompt;
   }

   /**
    * This can be used to determine what scheme to use. Typical schemes might be HTTP,
    * DIGEST, CERTIFICATE, Proxy, OTP, Socks, etc..
    */
   public String getRequestingScheme()
   {
      return scheme;
   }
}
