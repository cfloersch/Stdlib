/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 30, 2003
 * Time: 6:21:14 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package xpertss.security;

import xpertss.lang.Objects;

import javax.security.auth.callback.Callback;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * Callback used to request the client's public/private keys to use for certificate
 * based authentication and proof of ownership.
 */
public class AuthKeySetCallback implements Callback {

   private Certificate[] certChain;
   private PrivateKey priKey;
   private String algorithm;

   /**
    * Create an auth key set callback that does not care what algorithm the returned
    * private key is used with.
    */
   public AuthKeySetCallback()
   {
   }

   /**
    * Create an auth key set that requests a private key usable with the specified
    * algorithm.
    */
   public AuthKeySetCallback(String algorithm)
   {
      this.algorithm = algorithm;
   }


   /**
    * Returns the algorithm the requested key should be usable with.
    * <p>
    * If the requester has not specified a particular algorithm this will return
    * {@code null}.
    */
   public String getAlgorithm()
   {
      return algorithm;
   }



   /**
    * Called by the CallbackHandler to supply the client's private key and public
    * certificate chain.
    *
    * @throws NullPointerException if the key or cert chain are {@code null}
    */
   public void setKeys(PrivateKey key, Certificate[] certChain)
   {
      this.priKey = Objects.notNull(key, "key");
      this.certChain = Objects.notNull(certChain, "certChain");
   }

   /**
    * Return the client's public certificate chain to use in authentication.
    */
   public Certificate[] getCertificateChain()
   {
      return certChain;
   }

   /**
    * Return the client's corresponding private key used to prove ownership of the
    * certificate chain.
    */
   public PrivateKey getPrivateKey()
   {
      return priKey;
   }

}
