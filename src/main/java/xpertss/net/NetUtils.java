/*
 * Date: Feb 7, 2003
 * Copyright 2014 XpertSoftware
 */
package xpertss.net;

import xpertss.io.BigEndian;
import xpertss.lang.Bytes;
import xpertss.lang.Integers;
import xpertss.lang.Numbers;
import xpertss.lang.Range;
import xpertss.lang.Strings;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.*;
import java.util.Enumeration;

/**
 * A set of utility methods useful for networking related code.
 */
@SuppressWarnings("UnusedDeclaration")
public final class NetUtils {

   private NetUtils() { }


   /**
    * This method will convert an IPv4 address to its corresponding IPv6 address
    * and vise versa.
    *
    * @param source The source InetAddress to convert
    * @return the opposite type of inet address
    * @throws IllegalArgumentException if the IPv6 address cannot be represented
    *          as an IPv4 address
    * @throws NullPointerException if the source address is {@code null}
    */
   public static InetAddress convert(InetAddress source)
   {
      if(source instanceof Inet4Address) {
         return getInetAddress(Bytes.append(new byte[12], source.getAddress()));
      } else if(source instanceof Inet6Address) {
         byte[] bytes = source.getAddress();
         for (int i = 0; i <= 9; i++) {
            if (bytes[i] != 0) {
               throw new IllegalArgumentException("This IPv6 address cannot be used in IPv4 context");
            }
         }
         byte FF = (byte) 0xFF;
         if (bytes[10] != 0 && bytes[10] != FF || bytes[11] != 0 && bytes[11] != FF) {
            throw new IllegalArgumentException("This IPv6 address cannot be used in IPv4 context");
         }
         return getInetAddress(new byte[] {bytes[12], bytes[13], bytes[14], bytes[15]});
      }
      throw new NullPointerException();
   }


   /**
    * Compare two InetAddresses for ordering purposes. The comparison coverts
    * the {@code InetAddress} to its numeric representation and then compares
    * their values.
    * <p>
    * {@code null} is ordered less than non-null values.
    *
    * @param one The first address to compare
    * @param two The second address to compare
    * @return a negative integer, zero, or a positive integer as the first
    *          argument is less than, equal to, or greater than the second
    */
   public static int compare(InetAddress one, InetAddress two)
   {
      if(one == two) return 0;
      if(one == null) return -1;
      if(two == null) return 1;

      BigInteger oneInt = new BigInteger(1, one.getAddress());
      BigInteger twoInt = new BigInteger(1, two.getAddress());

      return oneInt.compareTo(twoInt);
   }



   /*
    * IPv6 addresses are enclosed in brackets when used in urls/uris
    *   http://[2001:db8:85a3:8d3:1319:8a2e:370:7348]/
    * When the URL also contains a port number the notation is:
    *   https://[2001:db8:85a3:8d3:1319:8a2e:370:7348]:443/
    *
    *   These two types of IPv6 addresses use this alternative format:
    *   IPv4–mapped IPv6 address
    *     This type of address is used to represent IPv4 nodes as IPv6 addresses. It
    *     allows IPv6 applications to communicate directly with IPv4 applications.
    *     For example, 0:0:0:0:0:ffff:192.1.56.10 and ::ffff:192.1.56.10/96 (shortened format).
    *   IPv4–compatible IPv6 address
    *     This type of address is used for tunneling. It allows IPv6 nodes to communicate
    *     across an IPv4 infrastructure.
    *     For example, 0:0:0:0:0:0:192.1.56.10 and ::192.1.56.10/96 (shortened format).
    */



   /**
    * Returns true if the supplied address string has the form of either an
    * IPv6 or IPv4 internet address.
    */
   public static boolean isIPAddress(String address)
   {
      if(Strings.isEmpty(address)) return false;
      if(address.contains(":")) {      // Must be IPv6
         return isIP6Address(Strings.trim(address));
      } else {                         // Must be IPv4
         return isIP4Address(Strings.trim(address));
      }
   }




   /**
    * Returns true if the supplied address string has the form of an
    * IPv6 internet address.
    */
   public static boolean isIP6Address(String address)
   {
      if(Strings.isEmpty(address)) return false;
      if(address.equals("::")) return true;
      boolean zeros = false;
      int size = 8;
      Range<Integer> range = new Range<>(0,65535);
      String[] parts = address.split(":", -1);
      if(parts.length < 3) return false;

      for(int i = 0; i < parts.length; i++) {
         if(Strings.isEmpty(parts[i])) {
            // :: entry somewhere???
            if(zeros) {
               return false;
            } else if(i == 0) {
               if(!Strings.isEmpty(parts[i+1])) return false;
               i++;  // skip over next empty part
            } else if(i == parts.length - 2) {
               if(Strings.isEmpty(parts[i+1])) i++;
            } else if(i == parts.length - 1) {
               return false;  // trailing ':' needs to be '::'
            } else {
               if(Strings.isEmpty(parts[i+1])) return false;
            }
            zeros = true;
         } else if(i == parts.length - 1 && parts[i].contains(".")) {
            // last part can be IPv4 format
            if(!isIP4Address(parts[i])) return false;
            size = 7;
         } else {
            if(parts[i].length() > 4) return false;
            if(!range.within(Integers.parse(parts[i], 16, -1))) return false;
         }
      }
      return (parts.length == size || zeros);
   }

   /**
    * Returns true if the supplied address string has the form of an
    * IPv4 internet address.
    */
   public static boolean isIP4Address(String address)
   {
      if(Strings.isEmpty(address)) return false;
      String[] parts = address.split("\\.");
      if(parts.length != 4) return false;
      Range<Integer> range = new Range<>(0,255);
      for(String part : parts) {
         if(part.length() > 1 && part.startsWith("0")) return false;
         if(!range.within(Integers.parse(part, -1))) return false;
      }
      return true;
   }





   /**
    * This method returns the local IP address as a String. If for some reason
    * the local IP address can't be determined it returns <code>0.0.0.0</code>
    * <p>
    * An example of a situation where the local IP address can't be determined
    * is a machine without a network config and not connected to the internet
    * with a modem.
    */
   public static String getLocalAddress()
   {
      try {
         return InetAddress.getLocalHost().getHostAddress();
      } catch(Exception ex) { return "0.0.0.0"; }
   }


   /**
    * Gets the local machines host name if it has one. If it does not have a
    * local host name then localhost is returned.
    */
   public static String getLocalHost()
   {
      try {
         return InetAddress.getLocalHost().getHostName();
      } catch(Exception _ex) { return "localhost"; }
   }



   /**
    * Get an InetAddress by name returning {@code null} if there is an error
    * resolving the name.
    */
   public static InetAddress getInetAddress(String name)
   {
      try { return InetAddress.getByName(name); } catch(Exception ex) { return null; }

   }

   /**
    * Create and return an InetAddress from the given array of octets returning
    * {@code null} if there are any errors instead of throwing an exception.
    */
   public static InetAddress getInetAddress(byte[] addrBytes)
   {
      try { return InetAddress.getByAddress(addrBytes); } catch(Exception ex) { return null; }

   }



   /**
    * Get all InetAddresses for the given name returning {@code null} if there
    * is an error resolving the name.
    */
   public static InetAddress[] getInetAddresses(String name)
   {
      try { return InetAddress.getAllByName(name); } catch(Exception ex) { return null; }
   }

   /**
    * Returns an array of InetAddresses containing {@code count} elements, beginning
    * with the specified base address and indexing the last octet by one for each
    * subsequent element. This will not overflow the given IP space so if the base
    * IP of 255.255.255.254 is given and a count greater than 2 is specified this
    * will return a collection of only two addresses.
    *
    * @throws NullPointerException if {@code base} is {@code null}
    * @throws IllegalArgumentException if {@code count} less than one
    */
   public static InetAddress[] getInetAddresses(InetAddress base, int count)
   {
      Numbers.gt(0, count, "count");
      int baseInt = BigEndian.parseInt(base.getAddress());
      if(((long)baseInt + count) > 0x00000000ffffffff) {
         count = Integers.safeCast(0x00000000ffffffff - baseInt) + 1;
      }
      InetAddress[] result = new InetAddress[count];
      for(int i = 0; i < result.length; i++) {
         result[i] = getInetAddress(BigEndian.toBytes(baseInt + i));
      }
      return result;
   }






   /**
    * Return the Network Interface object that has the given InetAddress configured
    * on it. Returns {@code null} if there are any errors locating the interface.
    */
   public static NetworkInterface getInterface(InetAddress addr)
   {
      try { return NetworkInterface.getByInetAddress(addr); } catch(Exception e) { return null; }
   }

   /**
    * Return the Network Interface object identified with the given name. Returns
    * {@code null} if there are any errors locating the interface.
    */
   public static NetworkInterface getInterface(String name)
   {
      try { return NetworkInterface.getByName(name); } catch(Exception e) { return null; }
   }

   /**
    * Returns the system's default interface. Returns {@code null} if there are no
    * non-loopback interfaces in the UP state.
    * <p/>
    * The default interface is the first interface returned by the system that is UP
    * and is not a loop back interface. It does not imply that it represents the
    * default route.
    *
    * @throws SocketException  if an I/O error occurs while querying the interfaces.
    */
   public static NetworkInterface getDefaultInterface() throws SocketException
   {
      Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
      while(e.hasMoreElements()) {
         NetworkInterface inter = e.nextElement();
         if(inter.isUp() && !inter.isLoopback()) return inter;
      }
      return null;
   }





   /**
    * Gets the broadcast address for the network interface identified by the given
    * address. This will only work if the given address is a local address. It will
    * return {@code null} if it can't find an interface with the given inet address.
    */
   public static InetAddress getBroadcast(InetAddress address)
   {
      NetworkInterface inter = getInterface(address);
      if(inter != null) {
         for (InterfaceAddress iaddr : inter.getInterfaceAddresses()) {
            if(iaddr.getAddress().equals(address)) {
               return iaddr.getBroadcast();
            }
         }
      }
      return null;
   }




   /**
    * Get the class of the IP Address. IP addresses are categorized into several
    * classes such as class A, class B, ... class D (multicast), etc. This returns
    * an enum representing the class type of the supplied IP address.
    *
    * @throws IllegalArgumentException if the supplied ip is not a V4 address
    * @throws NullPointerException if the supplied ip is null
    */
   public static IPClass getIPClass( InetAddress address )
   {
      if(!(address instanceof Inet4Address))
         throw new IllegalArgumentException( "address has more than 4 bytes; possibly IPv6 address" );

      int firstByte = address.getAddress()[0];
      if( ( firstByte & 0x80 ) == 0 ) return IPClass.A;
      else if( ( firstByte & 0xC0 ) == 0x80 ) return IPClass.B;
      else if( ( firstByte & 0xE0 ) == 0xC0 ) return IPClass.C;
      else if( ( firstByte & 0xF0 ) == 0xE0 ) return IPClass.D;
      else if( ( firstByte & 0xF8 ) == 0xF0 ) return IPClass.E;
      return IPClass.E;
   }



   private static final byte IP6_PRIVATE_PREFIX = (byte) 0xfd;

   /**
    * Determines if the supplied IP Address is a private non-routed IP
    * Address. RFC 1918 dictates the private IP space for IPv4
    * <p/>
    * <pre>
    *    10/8        - 10.0.0.0 through 10.255.255.255 (class A)
    *    172.16/12   - 172.16.0.0 through 172.31.255.255 (class B)
    *    192.168/16  - 192.168.0.0 through 192.168.255.255 (class C)
    * </pre>
    * <p/>
    * For IPv6 there are unique local addresses in the FD00::/8 space
    * as well as a deprecated site local addresses in the FEC0::/10
    * space which will both result in this returning {@code true}.
    */
   public static boolean isPrivateIP( InetAddress ip )
   {
      if(ip.isSiteLocalAddress()) return true;
      else if(ip instanceof Inet6Address)
         return ip.getAddress()[0] == IP6_PRIVATE_PREFIX;
      return false;
   }


   /**
    * This method takes a host pattern in the form.
    * <p>
    * <pre>
    *    127.0.0.*
    *       or
    *    *.domain.com
    *       or
    *    hostname
    *       or
    *    hostname.domain.com
    * </pre><p>
    * and checks to see if it matches the supplied host string. The supplied
    * host string will be resolved into both a fully qualified DNS name and
    * an IP to do appropriate wildcard matching.
    * <p>
    *
    */
   public static boolean matches(String pattern, String host)
   {
      try { return matches(pattern, getInetAddress(host)); } catch(Exception ex) { return false; }
   }

   /**
    * This method takes a host pattern in the form.
    * <p>
    * <pre>
    *    127.0.0.*
    *       or
    *    *.domain.com
    *       or
    *    hostname
    *       or
    *    hostname.domain.com
    * </pre><p>
    * and checks to see if it matches the supplied host. The inet address
    * will provide name service resolution to enable both IP based and host
    * based wildcard matching.
    */
   public static boolean matches(String pattern, InetAddress host)
   {
      // TODO Make sure this works with IPv4/IPv6 addresses
      if(pattern == null || host == null) return false;
      if(pattern.endsWith(".*")) {
         // IP Address wildcard match
         String ip = host.getHostAddress();
         return ip.startsWith(pattern.substring(0, pattern.length() - 1));
      } else if(pattern.startsWith("*.")) {
         // Domain wildcard match
         String domain = host.getCanonicalHostName();
         return domain.endsWith(pattern.substring(1));
      } else {
         InetAddress[] rAddr = getInetAddresses(pattern);
         for(InetAddress aRAddr : (rAddr != null) ? rAddr : new InetAddress[0]) {
            if(aRAddr.equals(host)) return true;
         }
      }
      return false;
   }





   /**
    * Returns a PasswordAuthentication object populated with the values of the userInfo
    * portion of a Url. If there is no colon ':' or no data after the colon then the
    * password portion will be a zero length char array.
    * <p>
    * If the supplied userInfo string is null this will return null.
    * <p>
    * UserInfo always takes the form of username:password where anything prior to the
    * colon is interpreted as the username, and everything after the colon is interpreted
    * as the password. If no colon is found then the entire string is interpreted as the
    * username.
    */
   public static PasswordAuthentication parseUserInfo(String userInfo)
   {
      if(userInfo != null) {
         String[] parts = userInfo.split("\\:");
         if(parts.length == 2)
            return new PasswordAuthentication(parts[0], parts[1].toCharArray());
         return new PasswordAuthentication(parts[0], new char[0]);
      }
      return null;
   }


   /**
    * Utility method to URL Encode a string. This is helpful if you are not interested in
    * catching all the exceptions that may or may not be thrown by Sun's implementation.
    * <p>
    * This always uses utf-8 encoding which should be present on all JVMs.
    *
    * @param str A string to encode.
    * @return The encoded string or null if there was an exception.
    */
   public static String urlEncode(String str)
   {
      try {
         return URLEncoder.encode(str, "utf-8");
      } catch(UnsupportedEncodingException e) { return null; }
   }

   /**
    * Utility method to URL Decode an encoded string. This is helpful if you are not interested
    * in catching all the exceptions that may or may not be thrown by Sun's implementation.
    * <p>
    * This always uses utf-8 encoding which should be present on all JVMs.
    *
    * @param str An encoded string to decode.
    * @return The decoded string or null if there was an exception.
    */
   public static String urlDecode(String str)
   {
      try {
         return URLDecoder.decode(str, "utf-8");
      } catch(UnsupportedEncodingException e) { return null; }
   }



}

