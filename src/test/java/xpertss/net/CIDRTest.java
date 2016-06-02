package xpertss.net;

import org.junit.Test;

import java.math.BigInteger;
import java.net.InetAddress;

import static org.junit.Assert.*;

/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 2/26/2016
 */
public class CIDRTest {

   @Test
   public void testBasicInet4CIDR()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/24");
      assertEquals("10.0.0.0/24", cidr.toString());

      assertEquals("10.0.0.0", cidr.getNetworkAddress().getHostAddress());
      assertEquals("255.255.255.0", cidr.getSubnetMask().getHostAddress());
      assertEquals("10.0.0.255", cidr.getBroadcastAddress().getHostAddress());
   }

   @Test
   public void testBasicInet4CIDRPrefix32()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/32");
      assertEquals("10.0.0.1/32", cidr.toString());

      assertEquals("10.0.0.1", cidr.getNetworkAddress().getHostAddress());
      assertEquals("255.255.255.255", cidr.getSubnetMask().getHostAddress());
      assertEquals("10.0.0.1", cidr.getBroadcastAddress().getHostAddress());
   }

   @Test
   public void testBasicInet4CIDRPrefix31()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/31");
      assertEquals("10.0.0.0/31", cidr.toString());

      assertEquals("10.0.0.0", cidr.getNetworkAddress().getHostAddress());
      assertEquals("255.255.255.254", cidr.getSubnetMask().getHostAddress());
      assertEquals("10.0.0.1", cidr.getBroadcastAddress().getHostAddress());
   }

   @Test
   public void testBasicInet4CIDRPrefix30()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/30");
      assertEquals("10.0.0.0/30", cidr.toString());

      assertEquals("10.0.0.0", cidr.getNetworkAddress().getHostAddress());
      assertEquals("255.255.255.252", cidr.getSubnetMask().getHostAddress());
      assertEquals("10.0.0.3", cidr.getBroadcastAddress().getHostAddress());
   }

   @Test
   public void testBasicInet4CIDRContains()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/24");
      assertFalse(cidr.contains(NetUtils.getInetAddress("10.0.0.0")));
      assertTrue(cidr.contains(NetUtils.getInetAddress("10.0.0.1")));
      assertTrue(cidr.contains(NetUtils.getInetAddress("10.0.0.254")));
      assertFalse(cidr.contains(NetUtils.getInetAddress("10.0.0.255")));
      assertFalse(cidr.contains(NetUtils.getInetAddress("10.0.1.2")));
      assertFalse(cidr.contains(NetUtils.getInetAddress("10.1.0.2")));
   }





   @Test
   public void testBasicInet4CIDRPrefix8()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/8");
      assertEquals("10.0.0.0/8", cidr.toString());

      assertEquals("10.0.0.0", cidr.getNetworkAddress().getHostAddress());
      assertEquals("255.0.0.0", cidr.getSubnetMask().getHostAddress());
      assertEquals("10.255.255.255", cidr.getBroadcastAddress().getHostAddress());
   }











   @Test
   public void testBigInteger()
   {
      InetAddress addrOne = NetUtils.getInetAddress("FFFF:FFFF::FFFF");
      BigInteger one = new BigInteger(1, addrOne.getAddress());
      assertEquals(1, one.signum());
      InetAddress addrTwo = NetUtils.getInetAddress("00FF:FFFF::FFFF");
      BigInteger two = new BigInteger(1, addrTwo.getAddress());
      assertEquals(1, two.signum());
   }








   @Test
   public void testIPv6CIDR_1()
   {
      byte[] start = {
            (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
      };
      InetAddress network = NetUtils.getInetAddress(start);

      byte[] end = {
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
      };
      InetAddress broadcast = NetUtils.getInetAddress(end);


      CIDR cidr = CIDR.parse("FFFF::/1");
      assertEquals(network, cidr.getNetworkAddress());
      assertEquals(broadcast, cidr.getBroadcastAddress());
   }


   @Test
   public void testIPv6CIDR_32()
   {
      byte[] start = {
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
      };
      InetAddress network = NetUtils.getInetAddress(start);

      byte[] end = {
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
      };
      InetAddress broadcast = NetUtils.getInetAddress(end);


      CIDR cidr = CIDR.parse("FFFF::/32");
      assertEquals(network, cidr.getNetworkAddress());
      assertEquals(broadcast, cidr.getBroadcastAddress());
   }

   @Test
   public void testIPv6CIDR_64()
   {
      byte[] start = {
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
      };
      InetAddress network = NetUtils.getInetAddress(start);

      byte[] end = {
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
      };
      InetAddress broadcast = NetUtils.getInetAddress(end);


      CIDR cidr = CIDR.parse("FFFF::/64");
      assertEquals(network, cidr.getNetworkAddress());
      assertEquals(broadcast, cidr.getBroadcastAddress());
   }

   @Test
   public void testIPv6CIDR_128()
   {
      byte[] start = {
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
      };
      InetAddress network = NetUtils.getInetAddress(start);

      byte[] end = {
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
      };
      InetAddress broadcast = NetUtils.getInetAddress(end);


      CIDR cidr = CIDR.parse("FFFF::/128");
      assertEquals(network, cidr.getNetworkAddress());
      assertEquals(broadcast, cidr.getBroadcastAddress());
   }


}