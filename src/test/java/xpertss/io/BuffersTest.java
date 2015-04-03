package xpertss.io;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class BuffersTest {

   private ByteBuffer testData;

   @Before
   public void setUp()
   {
      testData = Charsets.ISO_8859_1.encode("Test data");
   }


// newInputSteam methods

   @Test
   public void testFlip() throws IOException
   {
      testData = ByteBuffer.allocate(4);
      testData.put((byte)'T').put((byte)'e').put((byte)'s').put((byte)'t');
      assertFalse(testData.hasRemaining());
      assertEquals(testData.position(), testData.limit());

      InputStream in = Buffers.newInputStream(testData);
      assertEquals((byte)'T', in.read());
      assertEquals((byte)'e', in.read());
      assertEquals((byte)'s', in.read());
      assertEquals((byte)'t', in.read());
      assertEquals(-1, in.read());
   }

   @Test
   public void testMark() throws IOException
   {
      InputStream in = Buffers.newInputStream(testData);
      assertTrue(in.markSupported());
      in.mark(200);
      assertEquals((byte) 'T', in.read());
      assertEquals((byte) 'e', in.read());
      assertEquals((byte) 's', in.read());
      assertEquals((byte) 't', in.read());
      in.reset();
      assertEquals((byte) 'T', in.read());
      assertEquals((byte) 'e', in.read());
      assertEquals((byte) 's', in.read());
      assertEquals((byte) 't', in.read());
      assertEquals((byte) ' ', in.read());
      assertEquals((byte) 'd', in.read());
      assertEquals((byte) 'a', in.read());
      assertEquals((byte) 't', in.read());
      assertEquals((byte) 'a', in.read());
      assertEquals(-1, in.read());
      in.reset();
      assertEquals((byte) 'T', in.read());
   }

   @Test
   public void testSkip() throws IOException
   {
      InputStream in = Buffers.newInputStream(testData);
      assertTrue(in.markSupported());
      in.mark(200);
      in.skip(4);
      assertEquals((byte) ' ', in.read());
      in.skip(4);
      assertEquals(-1, in.read());
      in.reset();
      assertEquals((byte) 'T', in.read());
   }

   @Test
   public void testBulkRead() throws IOException
   {
      InputStream in = Buffers.newInputStream(testData);
      byte[] data = new byte[4];
      assertTrue(in.read(data) != -1);
      assertEquals((byte) 'T', data[0]);
      assertEquals((byte) 't', data[3]);
      assertEquals((byte) ' ', in.read());
      assertTrue(in.read(data) != -1);
      assertEquals((byte) 'd', data[0]);
      assertEquals((byte) 'a', data[3]);
      assertTrue(in.read(data) == -1);
   }

   @Test
   public void testAvailable() throws IOException
   {
      InputStream in = Buffers.newInputStream(testData);
      assertEquals(9, in.available());
   }

   @Test
   public void testBulkReadLargerThanBuffer() throws IOException
   {
      InputStream in = Buffers.newInputStream(testData);
      byte[] data = new byte[2*in.available()];
      assertEquals(9, in.read(data));
      assertEquals((byte)'a', data[8]);
      assertEquals((byte)0x00, data[9]);
      assertEquals((byte)0x00, data[11]);
      assertEquals((byte)0x00, data[13]);
      assertEquals((byte)0x00, data[15]);
      assertEquals(-1, in.read());
   }





   @Test
   public void testToHexString()
   {
      ByteBuffer buffer = ByteBuffer.allocate(11);
      for(int c = 0; c < 11; c++) buffer.put((byte) (c & 0xff));
      buffer.flip();
      String str = Buffers.toHexString(buffer);
      assertEquals(22, str.length());
      assertEquals("000102030405060708090A", str);
   }

   @Test
   public void testToFingerprint()
   {
      ByteBuffer buffer = ByteBuffer.allocate(11);
      for(int c = 0; c < 11; c++) buffer.put((byte) (c & 0xff));
      buffer.flip();
      String str = Buffers.toFingerPrint(buffer);
      assertEquals(32, str.length());
      assertEquals("00 01 02 03 04 05 06 07 08 09 0A", str);
   }

}