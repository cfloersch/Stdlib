package xpertss.text;

import xpertss.lang.SyntaxException;

/**
 * An exception thrown when an error occurs during parsing.
 * <p/>
 * This exception includes the text being parsed and the error index.
 * <p/>
 * This class is intended for use in a single thread.
 */
public class ParseException extends SyntaxException {

   private CharSequence parsedData;
   private int errorIndex;

   /**
    * Constructs a new exception with the specified message.
    *
    * @param message a message describing the error in more detail, should
    *    not be {@code null}
    * @param parsedData the parsed text, should not be {@code null}
    * @param errorIndex the index in the parsed string that was invalid,
    *    should be a valid index
    */
   public ParseException(String message, CharSequence parsedData, int errorIndex)
   {
      super(message);
      this.parsedData = parsedData;
      this.errorIndex = errorIndex;
   }

   /**
    * Constructs a new exception with the specified message and cause.
    *
    * @param message a message describing the error in more detail, should
    *    not be {@code null}
    * @param parsedData the parsed text, should not be {@code null}
    * @param errorIndex the index in the parsed string that was invalid,
    *    should be a valid index
    * @param cause the cause exception, may be {@code null}
    */
   public ParseException(String message, CharSequence parsedData, int errorIndex, Throwable cause)
   {
      super(message, cause);
      this.parsedData = parsedData;
      this.errorIndex = errorIndex;
   }


   /**
    * Returns the string that was being parsed.
    */
   public String getParsedString()
   {
      return (parsedData != null) ? parsedData.toString() : null;
   }

   /**
    * Returns the index where the error was found.
    */
   public int getErrorIndex()
   {
      return errorIndex;
   }
}
