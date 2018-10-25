package xpertss.reflect;

/**
 * Interface applied to members that provide property write access.
 * <p>
 * This can be a getter method or a field itself.
 */
public interface AnnotatedMutator extends AnnotatedMember {

   /**
    * Method that can be used to assign value to a field or property on the
    * given object.
    * <p>
    * This is implemented for fields and single-argument member methods
    */
   void setValue(Object pojo, Object value) throws Exception;


}
