/**
 * Copyright 2016 XpertSoftware
 * <p>
 * Created By: cfloersch
 * Date: 2/26/2016
 */
package xpertss.net;

import xpertss.io.BigEndian;
import xpertss.lang.Bytes;
import xpertss.lang.Integers;
import xpertss.lang.Objects;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Arrays;


/**
 * Classless Inter-Domain Routing (CIDR) is a method for allocating IP addresses
 * which replaced the previous addressing scheme utilizing network classes. Its
 * goal was to slow the growth of routing tables and to slow the exhaustion of
 * IPv4 addresses.
 */
public class CIDR {

   private final InetAddress address;
   private final int prefix;

   private CIDR(InetAddress address, int prefix)
   {
      this.address = address;
      this.prefix = prefix;
   }

   /**
    * Get the CIDR prefix value as an integer.
    */
   public int getPrefix()
   {
      return prefix;
   }

   /**
    * Get the network address for this CIDR.
    */
   public InetAddress getNetworkAddress()
   {
      return address;
   }


   /**
    * Get the broadcast address for this CIDR.
    */
   public InetAddress getBroadcastAddress()
   {
      return null;
   }


   /**
    * Get the subnet mask address for this CIDR.
    */
   public InetAddress getSubnetMask()
   {
      return null;
   }


   /**
    * Check to see if the CIDR contains the supplied inet address. Returns <tt>true</tt>
    * if the argument falls between the CIDR's network address and broadcast address and
    * <tt>false</tt> otherwise.
    * <p><pre>
    *    this.network &lt; address &lt; this.broadcast
    * </pre>
    */
   public boolean contains(InetAddress address)
   {
      return false;
   }





   @Override
   public int hashCode()
   {
      return Objects.hash(address.getAddress(), prefix);
   }

   @Override
   public boolean equals(Object obj)
   {
      if(obj != null && obj.getClass() == getClass()) {
         CIDR o = (CIDR) obj;
         return Arrays.equals(address.getAddress(), o.address.getAddress())
                  && Objects.equal(prefix, o.prefix);
      }
      return false;
   }

   @Override
   public String toString()
   {
      return String.format("%s/%d", address.getHostAddress(), prefix);
   }


   /**
    * Create a CIDR from a base address and prefix.
    */
   public static CIDR create(InetAddress address, int prefix)
   {
      Objects.notNull(address, "address");
      if(prefix < 0) throw new IllegalArgumentException("invalid CIDR prefix");
      if(address instanceof Inet4Address) {
         return CIDR4.create(address, prefix);
      }
      return CIDR6.create(address, prefix);
   }

   /**
    * Parse a standard CIDR notation string into its associated CIDR object
    * i.e.:
    * CIDR  subnet = parse("10.10.10.0/24"); or
    * CIDR  subnet = parse("1fff:0:0a88:85a3:0:0:ac1f:8001/24"); or
    * CIDR  subnet = parse("10.10.10.0/255.255.255.0");
    */
   public static CIDR parse(String encoded)
   {
      int p = encoded.indexOf('/');
      if(p < 0) throw new IllegalArgumentException("invalid CIDR notation");
      InetAddress addr = NetUtils.getInetAddress(encoded.substring(0, p));
      int prefix = Integers.parse(encoded.substring(p+1), -1);
      if(encoded.contains(":") && addr instanceof Inet4Address) {
         prefix = prefix - 96;
      }
      return create(addr, prefix);
   }
















   private static final byte[] PREFIX = {
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
      (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff
   };


   static class CIDR4 extends CIDR {

      private final int baseInt;
      private final int endInt;

      private final InetAddress end;
      private final InetAddress netmask;

      private CIDR4(int baseInt, int endInt, int netmask, int mask)
      {
         super(NetUtils.getInetAddress(BigEndian.toBytes(baseInt)), mask);
         this.baseInt = baseInt;
         this.endInt = endInt;

         this.end = NetUtils.getInetAddress(BigEndian.toBytes(endInt));
         this.netmask = NetUtils.getInetAddress(BigEndian.toBytes(netmask));
      }

      public InetAddress getBroadcastAddress()
      {
         return end;
      }

      public InetAddress getSubnetMask()
      {
         return netmask;
      }

      public boolean contains(InetAddress address)
      {
         if(address instanceof Inet6Address) {
            byte[] bytes = address.getAddress();
            if(!Bytes.regionMatches(bytes, 0, PREFIX, 0, PREFIX.length)) {
               return false;
            }
            // This shouldn't happen in modern JVMs
            int addressInt = BigEndian.parseInt(Arrays.copyOfRange(bytes, 12, 16));
            return baseInt < addressInt && addressInt < endInt;
         }
         int addressInt = BigEndian.parseInt(address.getAddress());
         return baseInt < addressInt && addressInt < endInt;
      }


      public static CIDR create(InetAddress address, int mask)
      {
         if(mask > 32) throw new IllegalArgumentException("invalid CIDR prefix");
         int netmask = ~((1 << 32 - mask) - 1);
         int baseInt = BigEndian.parseInt(address.getAddress()) & netmask;
         int endInt = baseInt + (1 << 32 - mask) - 1;
         return new CIDR4(baseInt, endInt, netmask, mask);
      }

   }
















   static class CIDR6 extends CIDR {

      private final BigInteger baseInt;
      private final BigInteger endInt;

      private final InetAddress end;
      private final InetAddress netmask;

      private CIDR6(InetAddress address, int prefix, BigInteger baseInt, BigInteger endInt, BigInteger netmask)
      {
         super(address, prefix);
         this.baseInt = baseInt;
         this.endInt = endInt;
         this.end = bigIntToIPv6Address(endInt);
         this.netmask = bigIntToIPv6Address(netmask);
      }


      public InetAddress getBroadcastAddress()
      {
         return end;
      }

      public InetAddress getSubnetMask()
      {
         return netmask;
      }

      public boolean contains(InetAddress address)
      {
         BigInteger addressInt = ipv6AddressToBigInteger(address);
         return addressInt.compareTo(baseInt) > 0 && addressInt.compareTo(endInt) < 0;
      }



      public static CIDR create(InetAddress address, int prefix)
      {
         if(prefix > 128) throw new IllegalArgumentException("invalid CIDR prefix");

         BigInteger base = ipv6AddressToBigInteger(address);
         BigInteger netmask = ipv6CidrMaskToMask(prefix);
         BigInteger baseInt = base.and(netmask);
         InetAddress baseAddress = bigIntToIPv6Address(baseInt);
         BigInteger endInt = baseInt.add(ipv6CidrMaskToBaseAddress(prefix)).subtract(BigInteger.ONE);

         return new CIDR6(baseAddress, prefix, baseInt, endInt, netmask);
      }



      /**
       * Given an IPv6 baseAddress length, return the block length.  I.e., a
       * baseAddress length of 96 will return 2**32.
       */
      private static BigInteger ipv6CidrMaskToBaseAddress(int cidrMask)
      {
         return BigInteger.ONE.shiftLeft(128 - cidrMask);
      }

      private static BigInteger ipv6CidrMaskToMask(int cidrMask)
      {
         return BigInteger.ONE.shiftLeft(128 - cidrMask).subtract(BigInteger.ONE).not();
      }





      /**
       * Given an IPv6 address, convert it into a BigInteger.
       *
       * @return the integer representation of the InetAddress
       * @throws IllegalArgumentException if the address is not an IPv6
       *                                  address.
       */
      private static BigInteger ipv6AddressToBigInteger(InetAddress address)
      {
         byte[] bytes = address.getAddress();
         if (address instanceof Inet4Address) {
            bytes = Bytes.append(PREFIX, bytes);
         }
         return new BigInteger(1, bytes);
      }

      /**
       * Convert a big integer into an IPv6 address.
       *
       * @return the inetAddress from the integer
       */
      private static InetAddress bigIntToIPv6Address(BigInteger addr)
      {
         byte[] b = addr.toByteArray();
         if (b.length > 16) {
            if(!(b.length == 17 && b[0] == 0)) {
               throw new AssertionError("IPv6 address produced more than 16 bytes");
            } else {
               b = Arrays.copyOfRange(b, 1, 17);
            }
         } else if(b.length < 16) {
            b = Bytes.prepend(b, new byte[16 - b.length]);
         }
         return NetUtils.getInetAddress(b);
      }

   }


}
