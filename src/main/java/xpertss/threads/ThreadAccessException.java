package xpertss.threads;

/**
 * A general exception intended to imply that the current thread is attempting
 * to perform an action on or access another thread that is illegal for the
 * current state and would result in deadlock for example.
 */
public class ThreadAccessException extends RuntimeException {

   public ThreadAccessException()
   {
      super();
   }

   public ThreadAccessException(String msg)
   {
      super(msg);
   }

}
