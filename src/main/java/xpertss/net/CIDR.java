/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 2/26/2016
 */
package xpertss.net;

import xpertss.io.BigEndian;
import xpertss.lang.BigMath;
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
    *    this.network < address < this.broadcast
    * </pre>
    */
   public boolean contains(InetAddress address)
   {
      return false;
   }




   /**
    * Returns the number of addresses available in this CIDR excluding the network
    * address and the broadcast address.
    */
   public long getAddressCount()
   {
      return 0;
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




   public static CIDR create(InetAddress address, int mask)
   {
      Objects.notNull(address, "address");
      if(mask < 0) throw new IllegalArgumentException("invalid CIDR prefix");
      if(address instanceof Inet4Address) {
         return CIDR4.create(address, mask);
      }
      return null; //return CIDR6.create(address, mask);
   }

   public static CIDR parse(String encoded)
   {
      int p = encoded.indexOf('/');
      if(p < 0) throw new IllegalArgumentException("invalid CIDR notation");
      return create(NetUtils.getInetAddress(encoded.substring(0, p)),
                     Integers.parse(encoded.substring(p+1), -1));
   }



   private static class CIDR4 extends CIDR {

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
            address = NetUtils.convert(address);
         }
         int addressInt = BigEndian.parseInt(address.getAddress());
         return baseInt < addressInt && addressInt < endInt;
      }


      public long getAddressCount()
      {
         return Math.max(0, endInt - baseInt - 1);
      }

      public static CIDR create(InetAddress address, int mask)
      {
         if(mask > 32) throw new IllegalArgumentException("invalid CIDR prefix");
         int netmask = ~((1 << 32 - mask) - 1);;
         int baseInt = BigEndian.parseInt(address.getAddress()) & netmask;
         int endInt = baseInt + (1 << 32 - mask) - 1;
         return new CIDR4(baseInt, endInt, netmask, mask);
      }

   }

   /*
   private static class CIDR6 extends CIDR {

      private final BigInteger baseInt;
      private final BigInteger endInt;

      private final InetAddress end;
      private final InetAddress netmask;

      private CIDR6(BigInteger baseInt, BigInteger endInt, BigInteger netmask, int mask)
      {
         super(address, prefix);
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
         if(address instanceof Inet4Address) {
            address = NetUtils.convert(address);
         }
         BigInteger addressInt = BigInteger.ONE;
         return addressInt.compareTo(baseInt) > 0 && addressInt.compareTo(endInt) < 0;
      }


      public long getAddressCount()
      {
         // TODO long really isn't big enough to represent the number of addresses a IPv6 CIDR block might contain which could be up to 128 bit in size
         return BigMath.max(BigInteger.ZERO, endInt.subtract(baseInt).subtract(BigInteger.ONE)).longValue();
      }


      public static CIDR create(InetAddress address, int mask)
      {
         if(mask > 128) throw new IllegalArgumentException("invalid CIDR prefix");
         return null;
      }
   }
   */
}
