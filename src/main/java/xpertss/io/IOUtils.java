package xpertss.io;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Simple Utilities for managing IO. Most of these are easy short hand ways
 * to manipulate IO in your code without having to write the loop over and
 * over again. It also helps to keep your code cleaner and more readable.
 *
 * @author Chris Floersch
 */
@SuppressWarnings("UnusedDeclaration")
public final class IOUtils {

   private IOUtils() { }

   /**
    * Given the supplied InputStream read all the bytes from it and return them as a
    * byte array. This does not close the supplied stream.
    */
   public static byte[] getBytes(InputStream in)
      throws IOException
   {
      return getBytes(in, false);
   }


   /**
    * Given the supplied InputStream read all the bytes from it and return them as a
    * byte array. This closes the supplied stream if specified.
    */
   public static byte[] getBytes(InputStream in, boolean close)
      throws IOException
   {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] buf = new byte[2048];
      int len;
      while((len = in.read(buf)) != -1) {
         baos.write(buf, 0, len);
      }
      if(close) close(in);
      return baos.toByteArray();
   }




   /**
    * Utility method to fully read an InputStream into a String. This can be useful
    * for loading text files to a string or reading a URL input stream into a String.
    * This is essentially just a clean code utility.
    *
    * @param in - InputStream to read from.
    * @param charset - Use specified charset to decode input stream bytes into characters.
    */
   public static String toString(InputStream in, Charset charset)
      throws IOException
   {
      return toString(in, charset, false);
   }


   /**
    * Utility method to fully read an InputStream into a String. This can be useful
    * for loading text files to a string or reading a URL input stream into a String.
    * This is essentially just a clean code utility.
    *
    * @param in - InputStream to read from.
    * @param charset - Use specified charset to decode input stream bytes into characters.
    * @param close - Close stream when finished.
    */
   public static String toString(InputStream in, Charset charset, boolean close)
      throws IOException
   {
      return toString(new InputStreamReader(in, charset), close);
   }


   /**
    * Utility method to fully read a Reader into a String. This can be useful for
    * loading text files to a string. This is essentially just a clean code utility.
    *
    * @param in - Reader to read from.
    */
   public static String toString(Reader in)
      throws IOException
   {
      return toString(in, false);
   }


   /**
    * Utility method to fully read a Reader into a String. This can be useful for
    * loading text files to a string. This is essentially just a clean code utility.
    *
    * @param in - Reader to read from.
    * @param close - Close reader when finished.
    */
   public static String toString(Reader in, boolean close)
      throws IOException
   {
      StringWriter sw = new StringWriter();
      int len;
      char[] buf = new char[2048];
      while((len = in.read(buf)) != -1) {
         sw.write(buf,0,len);
      }
      if(close) close(in);
      return sw.toString();
   }




   /**
    * Utility method to copy InputStream bytes to an OutputStream without having to
    * code all of the loop code. This just makes your code cleaner.
    *
    * @param in - InputStream to read bytes from.
    * @param out - OutputStream to write bytes to.
    * @return the number of bytes copied
    * @throws IOException if an I/O error occurs
    */
   public static long copyTo(InputStream in, OutputStream out)
      throws IOException
   {
      return copyTo(in, out, false);
   }


   /**
    * Utility method to copy InputStream bytes to an OutputStream without having to
    * code all of the loop code. This just makes your code cleaner.
    *
    * @param in - InputStream to read bytes from.
    * @param out - OutputStream to write bytes to.
    * @param close - Close streams when finished.
    * @return the number of bytes copied
    * @throws IOException if an I/O error occurs
    */
   public static long copyTo(InputStream in, OutputStream out, boolean close)
      throws IOException
   {
      long total = 0;
      try {
         int len;
         byte[] buf = new byte[2048];
         while((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
            total += len;
         }
      } finally {
         if(close) close(in, out);
      }
      return total;
   }




   /**
    * Utility method to copy Reader chars to a Writer without having to code all of
    * the loop code. This just makes your code cleaner.
    *
    * @param in - Reader to read chars from.
    * @param out - Writer to write chars to.
    * @return the number of bytes copied
    * @throws IOException if an I/O error occurs
    */
   public static long copyTo(Reader in, Writer out)
      throws IOException
   {
      return copyTo(in, out, false);
   }


   /**
    * Utility method to copy Reader chars to a Writer without having to code all of
    * the loop code. This just makes your code cleaner.
    *
    * @param in - Reader to read chars from.
    * @param out - Writer to write chars to.
    * @param close - Close the reader and writer when finished.
    * @return the number of bytes copied
    * @throws IOException if an I/O error occurs
    */
   public static long copyTo(Reader in, Writer out, boolean close)
      throws IOException
   {
      long total = 0;
      try {
         int len;
         char[] buf = new char[2048];
         while((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
            total += len;
         }
      } finally {
         if(close) close(in, out);
      }
      return total;
   }




   /**
    * This method takes to InputStreams and compares them byte by byte. The first time
    * anything is different it returns false and discontinues parsing the stream. It
    * returns true only if both streams are exact copies byte for byte.
    */
   public static boolean sameStream(InputStream is1, InputStream is2)
      throws IOException
   {
      while(true) {
         int b1 = is1.read();
         int b2 = is2.read();
         if(b1 == -1) {
            if(b2 != -1) return false;
            else break;
         }
         if(b1 != b2) return false;
      }
      return true;
   }

   /**
    * This method takes two Readers and compares them char by char. The first time
    * anything is different it returns false and discontinues parsing the stream.
    * It returns true only if both streams are exact copies char for char.
    */
   public static boolean sameStream(Reader is1, Reader is2)
      throws IOException
   {
      while(true) {
         int b1 = is1.read();
         int b2 = is2.read();
         if(b1 == -1) {
            if(b2 != -1) return false;
            else break;
         }
         if(b1 != b2) return false;
      }
      return true;
   }




   /**
    * Unconditionally close an <code>OutputStream</code>.
    * <p>
    * Equivalent to {@link java.io.OutputStream#close()}, except any exceptions will be
    * ignored.
    *
    * @param output - A (possibly null) OutputStream
    */
   public static boolean close(OutputStream output)
   {
      if( null == output ) return false;
      try { output.close(); return true; }
      catch( IOException ioe ) { return false; }
   }


   /**
    * Unconditionally close an <code>InputStream</code>.
    * <p>
    * Equivalent to {@link java.io.InputStream#close()}, except any exceptions will be
    * ignored.
    *
    * @param input - A (possibly null) InputStream
    */
   public static boolean close(InputStream input)
   {
      if( null == input ) return false;
      try { input.close(); return true; }
      catch( IOException ioe ) { return false; }
   }


   /**
    * Unconditionally close an <code>InputStream</code>/<code>OutputStream</code> pair.
    *
    * @param in - A (possibly null) InputStream
    * @param out - A (possibly null) OutputStream
    */
   public static void close(InputStream in, OutputStream out)
   {
      try { if(in != null) in.close(); } catch(Exception ignored) { }
      try { if(out != null) out.close(); } catch(Exception ignored) { }
   }





   /**
    * Unconditionally close an <code>Writer</code>.
    * <p>
    * Equivalent to {@link java.io.Writer#close()}, except any exceptions will be
    * ignored.
    *
    * @param output - A (possibly null) Writer
    */
   public static boolean close(Writer output)
   {
      if( null == output ) return false;
      try { output.close(); return true; }
      catch( IOException ioe ) { return false; }
   }


   /**
    * Unconditionally close an <code>Reader</code>.
    * <p>
    * Equivalent to {@link java.io.Reader#close()}, except any exceptions will be
    * ignored.
    *
    * @param input - A (possibly null) Reader
    */
   public static boolean close(Reader input)
   {
      if( null == input ) return false;
      try { input.close(); return true; }
      catch( IOException ioe ) { return false; }
   }


   /**
    * Unconditionally close a <code>Reader</code>/<code>Writer</code> pair.
    *
    * @param in - A (possibly null) Reader
    * @param out - A (possibly null) Writer
    */
   public static void close(Reader in, Writer out)
   {
      try { if(in != null) in.close(); } catch(Exception ignored) { }
      try { if(out != null) out.close(); } catch(Exception ignored) { }
   }


}
