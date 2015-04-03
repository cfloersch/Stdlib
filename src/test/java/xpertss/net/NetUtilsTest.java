package xpertss.net;

import org.junit.Test;

import java.net.InetAddress;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * User: cfloersch
 * Date: 12/11/12
 */
public class NetUtilsTest {


   @Test
   public void testCompare()
   {
      assertEquals(-1, NetUtils.compare(NetUtils.getInetAddress("10.1.1.1"), NetUtils.getInetAddress("11.1.1.1")));
      assertEquals(-1, NetUtils.compare(NetUtils.getInetAddress("10.1.1.1"), NetUtils.getInetAddress("10.2.1.1")));
      assertEquals(-1, NetUtils.compare(NetUtils.getInetAddress("10.1.1.1"), NetUtils.getInetAddress("10.1.2.1")));
      assertEquals(-1, NetUtils.compare(NetUtils.getInetAddress("10.1.1.1"), NetUtils.getInetAddress("10.1.1.2")));

      assertEquals(0, NetUtils.compare(NetUtils.getInetAddress("10.1.1.1"), NetUtils.getInetAddress("10.1.1.1")));

      assertEquals(1, NetUtils.compare(NetUtils.getInetAddress("11.1.1.1"), NetUtils.getInetAddress("10.1.1.1")));
      assertEquals(1, NetUtils.compare(NetUtils.getInetAddress("10.2.1.1"), NetUtils.getInetAddress("10.1.1.1")));
      assertEquals(1, NetUtils.compare(NetUtils.getInetAddress("10.1.2.1"), NetUtils.getInetAddress("10.1.1.1")));
      assertEquals(1, NetUtils.compare(NetUtils.getInetAddress("10.1.1.2"), NetUtils.getInetAddress("10.1.1.1")));
   }

   @Test
   public void testCompareNullInputs()
   {
      assertEquals(-1, NetUtils.compare(null, NetUtils.getInetAddress("11.1.1.1")));
      assertEquals(0, NetUtils.compare(null, null));
      assertEquals(1, NetUtils.compare(NetUtils.getInetAddress("11.1.1.1"), null));
   }





   @Test
   public void testIsIpV6Address()
   {
      assertTrue(NetUtils.isIPAddress("fe80:0000:0000:0000:0204:61ff:fe9d:f156"));
      assertTrue(NetUtils.isIPAddress("fe80:0:0:0:204:61ff:fe9d:f156"));
      assertTrue(NetUtils.isIPAddress("fe80::204:61ff:fe9d:f156"));
      assertTrue(NetUtils.isIPAddress("fe80:0:0:0:0204:61ff:254.157.241.86"));
      assertTrue(NetUtils.isIPAddress("fe80::204:61ff:254.157.241.86"));
      assertTrue(NetUtils.isIPAddress("fe80:0000:0000:0000:0204:61ff:254.157.241.86"));
      assertTrue(NetUtils.isIPAddress("::1"));
      assertTrue(NetUtils.isIPAddress("fe80::"));
      assertTrue(NetUtils.isIPAddress("2001::"));
      assertTrue(NetUtils.isIPAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
      assertTrue(NetUtils.isIPAddress("::"));
      assertTrue(NetUtils.isIPAddress("::ffff:c000:0280"));
      assertTrue(NetUtils.isIPAddress("::ffff:192.0.2.128"));
      assertTrue(NetUtils.isIPAddress("::192.0.2.128"));
      assertTrue(NetUtils.isIPAddress("abcd::efff"));
      assertTrue(NetUtils.isIPAddress("ABCD::EFFF"));

      assertTrue(NetUtils.isIPAddress("::0:0:0:0:0:0:0"));
      assertTrue(NetUtils.isIPAddress("::0:0:0:0:0:0"));
      assertTrue(NetUtils.isIPAddress("::0:0:0:0:0"));
      assertTrue(NetUtils.isIPAddress("::0:0:0:0"));
      assertTrue(NetUtils.isIPAddress("::0:0:0"));
      assertTrue(NetUtils.isIPAddress("::0:0"));
      assertTrue(NetUtils.isIPAddress("::0"));

      assertTrue(NetUtils.isIPAddress("0::"));
      assertTrue(NetUtils.isIPAddress("0:0::"));
      assertTrue(NetUtils.isIPAddress("0:0:0::"));
      assertTrue(NetUtils.isIPAddress("0:0:0:0::"));
      assertTrue(NetUtils.isIPAddress("0:0:0:0:0::"));
      assertTrue(NetUtils.isIPAddress("0:0:0:0:0:0::"));
      assertTrue(NetUtils.isIPAddress("0:0:0:0:0:0:0::"));

      assertFalse(NetUtils.isIPAddress("1111:2222:3333:4444:5555:6666:7777"));
      assertFalse(NetUtils.isIPAddress("1111:2222:3333:4444:5555:6666"));
      assertFalse(NetUtils.isIPAddress("1111:2222:3333:4444:5555"));
      assertFalse(NetUtils.isIPAddress("1111:2222:3333:4444"));
      assertFalse(NetUtils.isIPAddress("1111:2222:3333"));
      assertFalse(NetUtils.isIPAddress("1111:2222"));
      assertFalse(NetUtils.isIPAddress("1111"));

      assertFalse(NetUtils.isIPAddress("02001:0000:1234:::C1C0:ABCD:0876"));
      assertFalse(NetUtils.isIPAddress("02001:0000:1234:0000:0000:C1C0:ABCD:0876"));
      assertFalse(NetUtils.isIPAddress("2001:0000:01234:0000:0000:C1C0:ABCD:0876"));
      assertFalse(NetUtils.isIPAddress("2001:0000:1234:0000:00000:C1C0:ABCD:0876"));
      assertFalse(NetUtils.isIPAddress("2001:0000:1234:0000:0000:0C1C0:ABCD:0876"));
      assertFalse(NetUtils.isIPAddress("fe80:0000:0000:0000:0204:61ff:254.157.241.086"));

      assertFalse(NetUtils.isIPAddress("2001:0000:1234:0000:0000:C1C0:ABCD:0876:AAAA"));
      assertFalse(NetUtils.isIPAddress("2001:0000:1234:0000:0000:C1C0:ABCD"));
      assertFalse(NetUtils.isIPAddress("2001:0000:1234: 0000:0000:C1C0:ABCD"));


      assertFalse(NetUtils.isIPAddress("2001:0000:1234:0000:0000:C1C0:ABCD:"));
      assertFalse(NetUtils.isIPAddress(":1111:2222:3333:4444:5555:6666:7777"));

      assertFalse(NetUtils.isIPAddress(":::7777"));
      assertFalse(NetUtils.isIPAddress("7777:::"));
      assertFalse(NetUtils.isIPAddress("7777:::6666"));


      assertFalse(NetUtils.isIPAddress("::1111:2222:3333:4444:5555:6666::"));
      assertFalse(NetUtils.isIPAddress(":1111:2222:3333:4444:5555:6666:7777:8888"));
      assertFalse(NetUtils.isIPAddress("::ffff:2.3.4"));
      assertFalse(NetUtils.isIPAddress("::ffff:2.3.4."));
      assertFalse(NetUtils.isIPAddress("2.3.4.5:ffff::"));
      assertFalse(NetUtils.isIPAddress("2.3.4.:ffff::"));
      assertFalse(NetUtils.isIPAddress("2.3.4:ffff::"));
      assertFalse(NetUtils.isIPAddress("3ffe:b00::1::a"));
      assertFalse(NetUtils.isIPAddress(":::"));
      assertFalse(NetUtils.isIPAddress(":"));

      assertFalse(NetUtils.isIPAddress("::ffff:1.2.3.4:7777"));
      assertFalse(NetUtils.isIPAddress("::ffff:1.2.3.4:"));
      assertFalse(NetUtils.isIPAddress("::ffff:.2.3.4"));
      assertFalse(NetUtils.isIPAddress("::ffff:..3.4"));
      assertFalse(NetUtils.isIPAddress("::ffff:...4"));
      assertFalse(NetUtils.isIPAddress(":10.1.1.1"));

      assertFalse(NetUtils.isIPAddress("abcdabcde:::123987987526"));
      assertFalse(NetUtils.isIPAddress("hell::damn"));
      assertFalse(NetUtils.isIPAddress("abcd:efff"));
      assertFalse(NetUtils.isIPAddress("Hello"));
      assertFalse(NetUtils.isIPAddress("2001"));
      assertFalse(NetUtils.isIPAddress("2001:"));
   }


   @Test
   public void testIsIpV4Address()
   {
      assertTrue(NetUtils.isIPAddress("0.0.0.0"));
      assertTrue(NetUtils.isIPAddress("10.1.1.4"));
      assertTrue(NetUtils.isIPAddress("10.5.1.0"));
      assertTrue(NetUtils.isIPAddress("192.168.1.0"));
      assertTrue(NetUtils.isIPAddress("192.168.0.0"));
      assertTrue(NetUtils.isIPAddress("239.168.1.0"));
      assertTrue(NetUtils.isIPAddress("255.255.255.255"));


      assertFalse(NetUtils.isIPAddress("0.0.0."));
      assertFalse(NetUtils.isIPAddress(".0.0.0"));
      assertFalse(NetUtils.isIPAddress("0.0.."));
      assertFalse(NetUtils.isIPAddress("..0.0"));
      assertFalse(NetUtils.isIPAddress("0.0..0"));
      assertFalse(NetUtils.isIPAddress("0..0.0"));
      assertFalse(NetUtils.isIPAddress(".0.0.0"));
      assertFalse(NetUtils.isIPAddress(".0.0."));
      assertFalse(NetUtils.isIPAddress(".0.."));
      assertFalse(NetUtils.isIPAddress("..0."));
      assertFalse(NetUtils.isIPAddress("0..."));
      assertFalse(NetUtils.isIPAddress("...0"));


      assertFalse(NetUtils.isIPAddress("232.128.1"));
      assertFalse(NetUtils.isIPAddress("10.5."));
      assertFalse(NetUtils.isIPAddress("232.128"));
      assertFalse(NetUtils.isIPAddress("192."));
      assertFalse(NetUtils.isIPAddress("239"));
      assertFalse(NetUtils.isIPAddress("www.manheim.com"));
      assertFalse(NetUtils.isIPAddress("232.128.254.012"));
      assertFalse(NetUtils.isIPAddress("232.128.06.12"));
      assertFalse(NetUtils.isIPAddress("232.028.256.12"));
      assertFalse(NetUtils.isIPAddress("032.28.251.12"));

      assertFalse(NetUtils.isIPAddress("256.255.255.255"));
      assertFalse(NetUtils.isIPAddress("255.256.255.255"));
      assertFalse(NetUtils.isIPAddress("255.255.256.255"));
      assertFalse(NetUtils.isIPAddress("255.255.255.256"));

   }


   @Test
   public void testGetLocalAddress()
   {
      assertNotNull(NetUtils.getLocalAddress());
      assertTrue(NetUtils.isIPAddress(NetUtils.getLocalAddress()));
   }

   @Test
   public void testGetLocalHost()
   {
      assertNotNull(NetUtils.getLocalHost());
      assertFalse(NetUtils.isIPAddress(NetUtils.getLocalHost()));
   }

   @Test
   public void testGetInetAddress()
   {
      byte[] valid = new byte[] { 0x01, 0x01, 0x01, 0x01 };
      byte[] invalid = new byte[] { 0x01, 0x01, 0x01, 0x01, 0x01 };
      assertNotNull(NetUtils.getInetAddress(valid));
      assertNull(NetUtils.getInetAddress(invalid));
   }

   @Test
   public void testGetInetAddresses()
   {
      InetAddress[] addresses = NetUtils.getInetAddresses("www.google.com");
      assertTrue(addresses.length > 1);
      assertNull(NetUtils.getInetAddresses("does.not.exist"));
   }

   @Test
   public void testGetGoogleInetAddress()
   {
      assertNotNull(NetUtils.getInetAddress("www.google.com"));
      assertNull(NetUtils.getInetAddress("does.not.exist"));
   }

   @Test
   public void testSplit()
   {
      String[] parts = "::".split(":", -1);
      assertEquals(3, parts.length);
   }


}
