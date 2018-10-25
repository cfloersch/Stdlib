package xpertss.reflect;

/**
 * Interface applied to members that can create instances of an object.
 * <p>
 * This is applied to static methods with a return type of the given
 * class or to constructors belonging to the class.
 * <p>
 * TODO Generify this so invoke returns our object type
 */
public interface AnnotatedCreator extends AnnotatedWithParams {


   /**
    * Return the annotated parameter at the given index position.
    */
   AnnotatedParameter getParameter(int index);

   /**
    * Return the number of parameters belonging to this creator.
    */
   int getParameterCount();


   /**
    * Method that can be used to (try to) invoke this object with specified arguments.
    * This may succeed or fail, depending on expected number of arguments: caller
    * needs to take care to pass correct number. Exceptions are thrown directly
    * from actual low-level call.
    */
   Object invoke(Object ... args) throws Exception;


}
