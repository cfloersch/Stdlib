package xpertss.security.action;


import xpertss.lang.Strings;

/**
 * A convenience class for loading a system library as a privileged action.
 *
 * <p>An instance of this class can be used as the argument of
 * <code>AccessController.doPrivileged</code>.
 *
 * <p>The following code attempts to load the system library named
 * <code>"lib"</code> as a privileged action: <p>
 *
 * <pre>
 * java.security.AccessController.doPrivileged(new LoadLibraryAction("lib"));
 * </pre>
 */
public class LoadLibraryAction implements java.security.PrivilegedAction {

   private String theLib;

   /**
    * Constructor that takes the name of the system library that needs to be
    * loaded.
    *
    * <p>The manner in which a library name is mapped to the
    * actual system library is system dependent.
    *
    * @param theLib the name of the library.
    */
   public LoadLibraryAction(String theLib)
   {
      this.theLib = Strings.notEmpty(theLib, "the library may not be empty");
   }

   /**
    * Loads the system library whose name was specified in the constructor.
    */
   public Object run()
   {
      System.loadLibrary(theLib);
      return null;
   }
}
