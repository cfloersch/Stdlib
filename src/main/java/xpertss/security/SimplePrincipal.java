package xpertss.security;

import xpertss.lang.Strings;

import java.io.Serializable;
import java.security.Principal;

/**
 * This class implements a simple Principal which is simply a username.
 *
 * @version 1.0
 * @author Chris Floersch
 */
public class SimplePrincipal implements Principal, Serializable, Comparable<Principal> {

   private String username;
   private String displayName;

   /**
    * Construct a SimplePrincipal with the specified username.
    *
    * @throws IllegalArgumentException If {@code username} is empty
    */
   public SimplePrincipal(String username)
   {
      this.username = Strings.notEmpty(username, "username");
   }

   /**
    * Construct a SimplePrincipal with the specified username and display name.
    *
    * @throws IllegalArgumentException If {@code username} is empty
    */
   public SimplePrincipal(String username, String displayName)
   {
      this.username = Strings.notEmpty(username, "username");
      this.displayName = displayName;
   }


   /**
    * Checks to see if this Principal and another object are equal. It returns true
    * if and only if the supplied object is of Type Principal and the getName() method
    * of that Principal returns a String equal to (case sensitive) the local username.
    */
   public boolean equals(Object another)
   {
      if (another instanceof Principal) {
         Principal p = (Principal) another;
         return getName().equals(p.getName());
      }
      return false;
   }


   /**
    * Return the username as a String.
    */
   public String getName()
   {
      return username;
   }


   /**
    * Simple hashcode generated from username string.
    */
   public int hashCode()
   {
      return username.hashCode();
   }


   /**
    * This returns the display name if it is set. Otherwise, it returns the username.
    */
   public String toString()
   {
      return (displayName != null) ? displayName : getName();
   }


   @Override
   public int compareTo(Principal o)
   {
      return username.compareTo(o.getName());
   }

}
