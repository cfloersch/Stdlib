package xpertss.reflect;

/**
 * Interface applied to members that provide property read access.
 * <p/>
 * This can be a getter method or a field itself.
 */
public interface AnnotatedAccessor extends AnnotatedMember {

   /**
    * Method that can be used to access the value of a field or property
    * on given object.
    * <p/>
    * This is implemented for fields and no-argument member methods.
    */
   Object getValue(Object pojo) throws Exception;


}
