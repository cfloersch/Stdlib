/**
 * Created By: cfloersch
 * Date: 8/24/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.text;

/**
 * An enumeration of supported {@link StringPrep} profiles.
 */
public enum Profile {
   /**
    * The SASLPrep profile defined in RFC4013 used for preparing
    * user names and passwords.
    */
   SASLPrep,

   /**
    * The NamePrep profile defined in RFC3491 used for preparing
    * Internationalized Domain Names (IDN)
    */
   NamePrep,

   /**
    * The NodePrep profile defined in Appendix-A of RFC6122 used
    * for preparing XMPP local part strings.
    */
   NodePrep,

   /**
    * The NodePrep profile defined in Appendix-B of RFC6122 used
    * for preparing XMPP resource part strings.
    */
   ResourcePrep,


   /**
    * The ISCSIPrep profile defined in RFC3722 used for preparing
    * Systems Interface (iSCSI) Names
    */
   ISCSIPrep,

   /**
    * The TracePrep profile defined in RFC4505 used for preparing
    * trace information in the Anonymous SASL mechanism.
    */
   TracePrep

}
