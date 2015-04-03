/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 22, 2002
 * Time: 11:47:22 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package xpertss.lang;

/**
 * Interface that defines a class as Resettable. This means that there is some sort
 * of state that can or should be reset before it can be reused. This is typically
 * used by Pooled objects so that they can be reset before being put back in the
 * Pool.
 */
public interface Resettable {

   /**
    * Reset the internal state of the object so that it can be reused.
    */
   public void reset();

}
