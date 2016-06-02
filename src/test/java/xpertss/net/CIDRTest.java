package xpertss.net;

import org.junit.Test;
import xpertss.io.BigEndian;

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
      assertEquals(254, cidr.getAddressCount());
   }

   @Test
   public void testBasicInet4CIDRPrefix32()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/32");
      assertEquals("10.0.0.1/32", cidr.toString());

      assertEquals("10.0.0.1", cidr.getNetworkAddress().getHostAddress());
      assertEquals("255.255.255.255", cidr.getSubnetMask().getHostAddress());
      assertEquals("10.0.0.1", cidr.getBroadcastAddress().getHostAddress());
      assertEquals(0, cidr.getAddressCount());
   }

   @Test
   public void testBasicInet4CIDRPrefix31()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/31");
      assertEquals("10.0.0.0/31", cidr.toString());

      assertEquals("10.0.0.0", cidr.getNetworkAddress().getHostAddress());
      assertEquals("255.255.255.254", cidr.getSubnetMask().getHostAddress());
      assertEquals("10.0.0.1", cidr.getBroadcastAddress().getHostAddress());
      assertEquals(0, cidr.getAddressCount());
   }

   @Test
   public void testBasicInet4CIDRPrefix30()
   {
      CIDR cidr = CIDR.parse("10.0.0.1/30");
      assertEquals("10.0.0.0/30", cidr.toString());

      assertEquals("10.0.0.0", cidr.getNetworkAddress().getHostAddress());
      assertEquals("255.255.255.252", cidr.getSubnetMask().getHostAddress());
      assertEquals("10.0.0.3", cidr.getBroadcastAddress().getHostAddress());
      assertEquals(2, cidr.getAddressCount());
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

   public void testToInt()
   {
      // Looks like 10.0.0.127 = 167772287
      // but        10.0.0.128 = 167772032
      // That's the boundary in a signed 8 bit byte (-128 - + 127)
      for(int i = 0; i <= 255; i++) {
         InetAddress address = NetUtils.getInetAddress(String.format("10.0.0.%d", i));
         System.out.println(address.getHostAddress() + " " + BigEndian.parseInt(address.getAddress()));
      }
   }

}