package xpertss.io;

/**
 * Utility class for operating with numeric types in a big endian format.
 * <p/>
 * BigEndian is also sometime referred to as network byte order.
 */
@SuppressWarnings("UnusedDeclaration")
public final class BigEndian {

   private BigEndian() { }

   /**
    * Parse a big endian collection of bytes into a short. The supplied bytes
    * must be at least two bytes long.
    */
   public static short parseShort(byte[] data)
   {
      return parseShort(data, 0);
   }

   /**
    * Parse a big endian collection of bytes into a short starting at the specified
    * index. The supplied bytes must be at least two bytes long from the specified
    * index.
    */
   public static short parseShort(byte[] data, int offset)
   {
      if(data.length - offset < 2) throw new IllegalArgumentException("invalid input size, expected 2 found " + (data.length - offset));
      short result = 0;
      for(int i = offset; i < offset + 2; i++) {
         result <<= 8;
         result |= data[i] & 0xFF;
      }
      return result;
   }



   /**
    * Parse a big endian collection of bytes into a char. The supplied bytes
    * must be at least two bytes long.
    */
   public static char parseChar(byte[] data)
   {
      return parseChar(data, 0);
   }

   /**
    * Parse a big endian collection of bytes into a char starting at the specified
    * index. The supplied bytes must be at least two bytes long from the specified
    * index.
    */
   public static char parseChar(byte[] data, int offset)
   {
      if(data.length - offset < 2) throw new IllegalArgumentException("invalid input size, expected 2 found " + (data.length - offset));
      char result = 0;
      for(int i = offset; i < offset + 2; i++) {
         result <<= 8;
         result |= data[i] & 0xFF;
      }
      return result;
   }



   /**
    * Parse a big endian collection of bytes into an int. The supplied bytes
    * must be at least four bytes long.
    */
   public static int parseInt(byte[] data)
   {
      return parseInt(data, 0);
   }

   /**
    * Parse a big endian collection of bytes into an int starting at the specified
    * index. The supplied bytes must be at least four bytes long from the specified
    * index.
    */
   public static int parseInt(byte[] data, int offset)
   {
      if(data.length - offset < 4) throw new IllegalArgumentException("invalid input size, expected 4 found " + (data.length - offset));
      int result = 0;
      for(int i = offset; i < offset + 4; i++) {
         result <<= 8;
         result |= data[i] & 0xFF;
      }
      return result;
   }




   /**
    * Parse a big endian collection of bytes into a long. The supplied bytes
    * must be at least eight bytes long.
    */
   public static long parseLong(byte[] data)
   {
      return parseLong(data, 0);
   }

   /**
    * Parse a big endian collection of bytes into a long starting at the specified
    * index. The supplied bytes must be at least eight bytes long from the specified
    * index.
    */
   public static long parseLong(byte[] data, int offset)
   {
      if(data.length - offset < 8) throw new IllegalArgumentException("invalid input size, expected 8 found " + (data.length - offset));
      long result = 0;
      for(int i = offset; i < offset + 8; i++) {
         result <<= 8;
         result |= data[i] & 0xFF;
      }
      return result;
   }



   /**
    * Parse a big endian collection of bytes into a float. The supplied bytes
    * must be at least four bytes long.
    */
   public static float parseFloat(byte[] data)
   {
      return parseFloat(data, 0);
   }

   /**
    * Parse a big endian collection of bytes into a float starting at the specified
    * index. The supplied bytes must be at least four bytes long from the specified
    * index.
    */
   public static float parseFloat(byte[] data, int offset)
   {
      if(data.length - offset < 4) throw new IllegalArgumentException("invalid input size, expected 4 found " + (data.length - offset));
      return Float.intBitsToFloat(parseInt(data, offset));
   }



   /**
    * Parse a big endian collection of bytes into a double. The supplied bytes
    * must be at least eight bytes long.
    */
   public static double parseDouble(byte[] data)
   {
      return parseDouble(data, 0);
   }

   /**
    * Parse a big endian collection of bytes into a double starting at the specified
    * index. The supplied bytes must be at least eight bytes long from the specified
    * index.
    */
   public static double parseDouble(byte[] data, int offset)
   {
      if(data.length - offset < 4) throw new IllegalArgumentException("invalid input size, expected 4 found " + (data.length - offset));
      return Double.longBitsToDouble(parseLong(data, offset));
   }




   /**
    * Simple utility method to return a byte array from the given short. The array
    * will be 2 positions long and the high order bit will be the first byte.
    */
   public static byte[] toBytes(short s)
   {
      byte[] result = new byte[2];
      result[0] = (byte) ((s >>> 8) & 0xFF);
      result[1] = (byte) ((s) & 0xFF);
      return result;
   }


   /**
    * Simple utility method to return a byte array from the given char. The array
    * will be 2 positions long and the high order bit will be the first byte.
    */
   public static byte[] toBytes(char c)
   {
      byte[] ret = new byte[2];
      ret[0] = (byte) ((c >>> 8) & 0xff);
      ret[1] = (byte) ((c)       & 0xff);
      return ret;
   }


   /**
    * Simple utility method to return a byte array from the given int. The array
    * will be 4 positions long and the high order bit will be the first byte.
    */
   public static byte[] toBytes(int i)
   {
      byte[] result = new byte[4];
      result[0] = (byte) ((i >>> 24) & 0xFF);
      result[1] = (byte) ((i >>> 16) & 0xFF);
      result[2] = (byte) ((i >>>  8) & 0xFF);
      result[3] = (byte) ((i) & 0xFF);
      return result;
   }


   /**
    * Simple utility method to return a byte array from the given long. The array
    * will be 8 positions long and the high order bit will be the first byte.
    */
   public static byte[] toBytes(long l)
   {
      byte[] result = new byte[8];
      result[0] = (byte) ((l >>> 56) & 0xFF);
      result[1] = (byte) ((l >>> 48) & 0xFF);
      result[2] = (byte) ((l >>> 40) & 0xFF);
      result[3] = (byte) ((l >>> 32) & 0xFF);
      result[4] = (byte) ((l >>> 24) & 0xFF);
      result[5] = (byte) ((l >>> 16) & 0xFF);
      result[6] = (byte) ((l >>>  8) & 0xFF);
      result[7] = (byte) ((l) & 0xFF);
      return result;
   }


   /**
    * Simple utility method to return a byte array from the given float. The array
    * will be 4 positions long and the high order bit will be the first byte.
    */
   public static byte[] toBytes(float f)
   {
      return toBytes(Float.floatToIntBits(f));
   }


   /**
    * Simple utility method to return a byte array from the given double. The array
    * will be 8 positions long and the high order bit will be the first byte.
    */
   public static byte[] toBytes(double d)
   {
      return toBytes(Double.doubleToLongBits(d));
   }


}
