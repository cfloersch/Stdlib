package xpertss.security.action;


import xpertss.lang.Strings;

/**
 * A convenience class for retrieving the string value of a system
 * property as a privileged action.
 *
 * <p>An instance of this class can be used as the argument of
 * <code>AccessController.doPrivileged</code>.
 *
 * <p>The following code retrieves the value of the system
 * property named <code>"prop"</code> as a privileged action: <p>
 *
 * <pre>
 * String s = (String) java.security.AccessController.doPrivileged(
 *                         new GetPropertyAction("prop"));
 * </pre>
 */
public class GetPropertyAction implements java.security.PrivilegedAction {

   private String theProp;
   private String defaultVal;

   /**
    * Constructor that takes the name of the system property whose
    * string value needs to be determined.
    *
    * @param property the name of the system property.
    */
   public GetPropertyAction(String property)
   {
      this.theProp = Strings.notEmpty(property, "property name may not be null");
   }

   /**
    * Constructor that takes the name of the system property and the default
    * value of that property.
    *
    * @param property the name of the system property.
    * @param defaultVal the default value.
    */
   public GetPropertyAction(String property, String defaultVal)
   {
      this.theProp = Strings.notEmpty(property, "property name may not be null");
      this.defaultVal = defaultVal;
   }

   /**
    * Determines the string value of the system property whose
    * name was specified in the constructor.
    *
    * @return the string value of the system property,
    *         or the default value if there is no property with that key.
    */
   public Object run()
   {
      String value = System.getProperty(theProp);
      return (value == null) ? defaultVal : value;
   }
}
