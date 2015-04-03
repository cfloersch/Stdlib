/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 14, 2004
 * Time: 11:16:31 PM
 * To change this template use File | Settings | File Templates.
 */
package xpertss.lang;

/**
 * A basic life cycle definition signifying that this object can be stopped.
 */
public interface Stoppable {

   /**
    * Stop the underlying process.
    */
   public void stop();

}
