/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 22, 2002
 * Time: 11:46:56 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package xpertss.lang;

/**
 * Interface that defines the class as Destroyable. This means that there is some
 * resource that a class holds that needs to be destroyed before the class is finalized.
 * A good example of this would be a pooled connection of some sort that wants to
 * close the socket connection or file before the object is finalized.
 */
public interface Destroyable {

   /**
    * Destroy the resources being held by this class.
    */
   public void destroy();

}
