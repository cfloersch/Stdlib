package xpertss.lang;

/**
 * Thrown to indicate an error in the syntax of a source content.
 */
public class SyntaxException extends RuntimeException {

   public SyntaxException()
   {
   }

   public SyntaxException(String message)
   {
      super(message);
   }

   public SyntaxException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public SyntaxException(Throwable cause)
   {
      super(cause);
   }
}
