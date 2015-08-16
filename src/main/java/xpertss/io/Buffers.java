package xpertss.io;

import xpertss.function.Predicate;
import xpertss.lang.Integers;
import xpertss.lang.Numbers;
import xpertss.lang.Objects;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * A collection of utilities for working with {@link java.nio.Buffer}s.
 */
public final class Buffers {

   private Buffers() { }

   /**
    * Wraps the given {@link java.nio.ByteBuffer} in an input stream much like a
    * {@link java.io.ByteArrayInputStream} wraps a byte array.
    */
   public static InputStream newInputStream(ByteBuffer buffer)
   {
      if(!buffer.hasRemaining()) buffer.flip();
      return new ByteBufferInputStream(buffer);
   }

   /**
    * Wraps the given {@link java.nio.ByteBuffer} in a {@link java.io.Reader} that will
    * decode the bytes into characters using the given {@link java.nio.charset.Charset}.
    */
   public static Reader newReader(ByteBuffer buffer, Charset charset)
   {
      return new InputStreamReader(newInputStream(buffer), charset);
   }


   /**
    * Wraps the given {@link java.nio.CharBuffer} in a {@link java.io.Reader} much like
    * a {@link java.io.ByteArrayInputStream} wraps a byte array.
    */
   public static Reader newReader(CharBuffer buffer)
   {
      return new CharBufferReader(buffer);
   }





   /**
    * Safely copies data from the source buffer to the destination buffer ensuring
    * that an overflow does not occur. It returns the number of characters transferred.
    * <p/>
    * It is assumed that the source buffer is in a state ready for relative gets and
    * the destination buffer is in a state ready for relative puts.
    *
    * @param src The source characters to copy
    * @param dst The destination to copy them
    * @return The number of characters copied
    */
   public static int copyTo(CharBuffer src, CharBuffer dst)
   {
      int count = 0;
      char[] buffer = new char[1024];
      while(src.hasRemaining() && dst.hasRemaining()) {
         int size = Math.min(Math.min(src.remaining(), dst.remaining()), buffer.length);
         src.get(buffer, 0, size);
         dst.put(buffer, 0, size);
         count += size;
      }
      return count;
   }

   /**
    * Safely copies data from the source buffer to the destination buffer ensuring
    * that an overflow does not occur. It returns the number of bytes transferred.
    * <p/>
    * It is assumed that the source buffer is in a state ready for relative gets
    * and the destination buffer is in a state ready for relative puts.
    *
    * @param src The source of the byes to copy
    * @param dst The destination to copy them to
    * @return The number of bytes written.
    */
   public static int copyTo(ByteBuffer src, ByteBuffer dst)
   {
      int count = 0;
      byte[] buffer = new byte[1024];
      while(src.hasRemaining() && dst.hasRemaining()) {
         int size = Math.min(Math.min(src.remaining(), dst.remaining()), buffer.length);
         src.get(buffer, 0, size);
         dst.put(buffer, 0, size);
         count += size;
      }
      return count;
   }





   /**
    * Null safe method to convert the contents of a given {@link ByteBuffer} to a
    * string using the specified {@link Charset} to decode the bytes.
    * <p/>
    * If the byte buffer is {@code null} this will return {@code null}.
    * <p/>
    * If the {@link Charset} is {@code null} the default charset will be used.
    *
    * @param src The source bytes to decode
    * @param charset The charset to use to decode the bytes
    * @return A string decoded from the bytes or {@code null}
    */
   public static String toString(ByteBuffer src, Charset charset)
   {
      if(src == null) return null;
      if(charset == null) charset = Charset.defaultCharset();
      return charset.decode(src).toString();
   }


   /**
    * Null safe method to transform a {@link CharBuffer} into a string. This will
    * return {@code null} if the given char buffer is {@code null}.
    */
   public static String toString(CharBuffer src)
   {
      return (src == null) ? null : src.toString();
   }








   /**
    * Returns {@code true} if the buffer content starting at its current
    * position equals the content of the specified byte array.
    * <p/>
    * This function will not alter the buffer's position, mark, nor limit.
    *
    * @param buffer The data to compare
    * @param data The data to compare against
    * @return {@code true} if the region matches the given array data, {@code
    *      false} otherwise.
    * @throws NullPointerException if the given byte buffer or byte array are
    *      {@code null}
    */
   public static boolean equal(ByteBuffer buffer, byte[] data)
   {
      return equal(buffer, buffer.position(), data, 0, data.length);
   }

   /**
    * Returns {@code true} if the buffer content starting at the specified
    * offset equals the content of the specified byte array.
    * <p/>
    * Returns {@code false} if the specified offset is negative or if the
    * regions do not match.
    * <p/>
    * This function will not alter the buffer's position, mark, nor limit.
    *
    * @param buffer The data to compare
    * @param bOffset The offset into the buffer to begin the comparison from
    * @param data The data to compare against
    * @return {@code true} if the region matches the given array data, {@code
    *      false} otherwise.
    * @throws NullPointerException if the given byte buffer or byte array are
    *      {@code null}
    */
   public static boolean equal(ByteBuffer buffer, int bOffset, byte[] data)
   {
      return equal(buffer, bOffset, data, 0, data.length);
   }

   /**
    * Returns {@code true} if the buffer content starting at the specified
    * offset equals the content of the specified byte array starting at its
    * specified offset and including the specified number of positions in
    * each.
    * <p/>
    * This will return {@code false} if either of the offsets are negative,
    * if the offset + len would overflow the buffer or extend beyond the
    * bounds of the array, or if any of the bytes in the specified regions
    * do not match.
    * <p/>
    * This function will not alter the buffer's position, mark, nor limit.
    *
    * @param buffer The data to compare
    * @param bOffset The offset into the buffer to begin the comparison from
    * @param data The data to compare against
    * @param dOffset The offset into the array to begin the comparison from
    * @param len The length of the compare region.
    * @return {@code true} if the region matches the given array data, {@code
    *      false} otherwise.
    * @throws NullPointerException if the given byte buffer or byte array are
    *      {@code null}
    */
   public static boolean equal(ByteBuffer buffer, int bOffset, byte[] data, int dOffset, int len)
   {
      if(bOffset < 0 || dOffset < 0) return false;
      if(buffer.limit() - bOffset < len) return false;
      if(data.length - dOffset < len) return false;
      for(int i = 0; i < len; i++) {
         if(data[i + dOffset] != buffer.get(i + bOffset)) return false;
      }
      return true;
   }


















   // Hex functions


   private static final char[] hexDigits = {
      '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
   };



   /**
    * Returns a string of hexadecimal digits from a ByteBuffer. Each byte is
    * converted to 2 hex symbols and a space.
    * <p><pre>
    *    0f 23 3a 3f ff
    * </pre>
    * <p/>
    * This method will read length number of bytes from the specified offset
    * and will not advance the buffer's position.
    */
   public static String toFingerPrint(ByteBuffer buffer, int offset, int length)
   {
      StringBuilder builder = new StringBuilder(buffer.remaining() * 3);
      int k;
      for(int i = offset; i < offset + length; i++) {
         if(builder.length() > 0) builder.append(" ");
         k = buffer.get(i);
         builder.append(hexDigits[(k >>> 4) & 0x0F]);
         builder.append(hexDigits[ k        & 0x0F]);
      }
      return builder.toString();
   }

   /**
    * Returns a string of hexadecimal digits from a ByteBuffer. Each byte is
    * converted to 2 hex symbols and a space.
    * <p/><pre>
    *    0f 23 3a 3f ff
    * </pre>
    * <p/>
    * This method will read from the current position to the limit and will not
    * advance the buffer's position.
    */
   public static String toFingerPrint(ByteBuffer buffer)
   {
      return toFingerPrint(buffer, buffer.position(), buffer.remaining());
   }





   /**
    * Returns a string of hexadecimal digits from a ByteBuffer. Each byte is
    * converted to 2 hex symbols.
    * <p><pre>
    *    0f233a3fff
    * </pre>
    * <p/>
    * This method will read length number of bytes from the specified offset
    * and will not advance the buffer's position.
    */
   public static String toHexString(ByteBuffer buffer, int offset, int length)
   {
      StringBuilder builder = new StringBuilder(length * 3);
      int k;
      for (int i = offset; i < offset + length; i++) {
         k = buffer.get(i);
         builder.append(hexDigits[(k >>> 4) & 0x0F]);
         builder.append(hexDigits[ k        & 0x0F]);
      }
      return builder.toString();
   }


   /**
    * Returns a string of hexadecimal digits from a ByteBuffer. Each byte is
    * converted to 2 hex symbols.
    * <p/><pre>
    *    0f233a3fff
    * </pre>
    * <p/>
    * This method will read from the current position to the limit and will not
    * advance the buffer's position.
    * </pre>
    */
   public static String toHexString(ByteBuffer buffer)
   {
      return toHexString(buffer, buffer.position(), buffer.remaining());
   }




   /**
    * Returns a byte buffer from a string of hexadecimal digits. This assumes
    * that the String does not contain any spaces such as a fingerprint.
    *
    * @throws NumberFormatException if the string contains characters outside
    *          the valid range for hexadecimal notation or its length is not
    *          a multiple of two.
    */
   public static ByteBuffer fromHexString(String hex)
   {
      final int len = hex.length();

      // "111" is not a valid hex encoding.
      if(len % 2 != 0)
         throw new NumberFormatException("hexBinary needs to be even-length: " + hex);

      ByteBuffer buffer = ByteBuffer.allocate(hex.length() / 2);

      for(int i = 0; i < len; i += 2) {
         int h = hexToBin(hex.charAt(i));
         int l = hexToBin(hex.charAt(i + 1));
         buffer.put((byte) ((h << 4) + l));
      }

      return (ByteBuffer) buffer.flip();
   }

   private static int hexToBin( char ch )
   {
      if( '0'<=ch && ch<='9' )    return ch-'0';
      if( 'A'<=ch && ch<='F' )    return ch-'A'+10;
      if( 'a'<=ch && ch<='f' )    return ch-'a'+10;
      throw new NumberFormatException("contains illegal character for hexBinary: " + Character.toString(ch));
   }




   // Predicate utils

   /**
    * Predicate which returns {@code true} if the supplied buffer has
    * data remaining, {@code false} otherwise.
    */
   public static Predicate<Buffer> hasRemaining()
   {
      return HasRemainingPredicate.INSTANCE;
   }

   private enum HasRemainingPredicate implements Predicate<Buffer> {
      INSTANCE;
      @Override public boolean apply(Buffer input) {
         return input.hasRemaining();
      }
   }


   /**
    * Predicate which returns {@code true} if the supplied buffer is a
    * direct buffer, {@code false} otherwise.
    */
   public static Predicate<Buffer> direct()
   {
      return DirectPredicate.INSTANCE;
   }

   private enum DirectPredicate implements Predicate<Buffer> {
      INSTANCE;
      @Override public boolean apply(Buffer input) {
         return input.isDirect();
      }
   }

   /**
    * Predicate which returns {@code true} if the supplied buffer is a
    * read-only buffer, {@code false} otherwise.
    */
   public static Predicate<Buffer> readOnly()
   {
      return ReadOnlyPredicate.INSTANCE;
   }

   private enum ReadOnlyPredicate implements Predicate<Buffer> {
      INSTANCE;
      @Override public boolean apply(Buffer input) {
         return input.isReadOnly();
      }
   }






   private static class ByteBufferInputStream extends InputStream {
      private final ByteBuffer buffer;
      public ByteBufferInputStream(ByteBuffer buffer)
      {
         this.buffer = Objects.notNull(buffer);
      }

      @Override
      public int read() throws IOException
      {
         return (buffer.hasRemaining()) ? buffer.get() : -1;
      }

      public int read(byte[] b, int off, int len)
      {
         if (b == null) {
            throw new NullPointerException();
         } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
         }
         int amount = Math.min(len, buffer.remaining());
         if(amount <= 0) return -1;
         buffer.get(b, off, amount);
         return amount;
      }

      public long skip(long n)
      {
         Numbers.gte(0L, n, "skip value is negative");
         long amount = Math.min(n, buffer.remaining());
         buffer.position(Integers.safeCast(buffer.position() + amount));
         return amount;
      }

      public int available()
      {
         return buffer.remaining();
      }

      public boolean markSupported() {
         return true;
      }

      public void mark(int readAheadLimit)
      {
         buffer.mark();
      }
      public void reset() {
         buffer.reset();
      }

      public void close() throws IOException { }

   }

   private static class CharBufferReader extends Reader {
      private final CharBuffer buffer;
      public CharBufferReader(CharBuffer buffer)
      {
         this.buffer = Objects.notNull(buffer);
      }


      @Override
      public int read(char[] cbuf, int off, int len)
         throws IOException
      {
         if (cbuf == null) {
            throw new NullPointerException();
         } else if (off < 0 || len < 0 || len > cbuf.length - off) {
            throw new IndexOutOfBoundsException();
         }
         int amount = Math.min(len, buffer.remaining());
         if(amount <= 0) return -1;
         buffer.get(cbuf, off, amount);
         return amount;

      }

      @Override
      public long skip(long n) throws IOException
      {
         Numbers.gte(0L, n, "skip value is negative");
         long amount = Math.min(n, buffer.remaining());
         buffer.position(Integers.safeCast(buffer.position() + amount));
         return amount;
      }


      @Override
      public boolean ready() throws IOException
      {
         return true;
      }

      @Override
      public boolean markSupported() {
         return true;
      }

      @Override
      public void mark(int readAheadLimit) throws IOException
      {
         buffer.mark();
      }

      @Override
      public void reset() throws IOException
      {
         buffer.reset();
      }

      @Override
      public void close() throws IOException { }

   }

}
