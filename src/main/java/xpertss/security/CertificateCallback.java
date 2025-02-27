package xpertss.security;


import javax.security.auth.callback.Callback;
import java.security.cert.Certificate;

/**
 * A Callback providing a certificate chain supplied by another party for visual
 * inspection by the client.
 * <p>
 * An example of this Callback's use would be with SSL where the server sends a
 * certificate chain that the client may use to authenticate the server. This
 * callback would contain the server's certificate chain that may be displayed
 * to the user for visual inspection or it may be verified using a certificate
 * path algorithm.
 */
public class CertificateCallback implements Callback {

   private Certificate[] certChain;

   /**
    * Construct a CertificateCallback with a remote party's certificate
    * chain.
    * <p>
    * Note this uses the Certificate class from java.security.cert not the
    * one from the java.security package.
    */
   public CertificateCallback(Certificate[] certChain)
   {
      this.certChain = certChain;
   }


   /**
    * Get the certificate chain supplied by the remote party.
    */
   public Certificate[] getCertificateChain()
   {
      return certChain;
   }


}
