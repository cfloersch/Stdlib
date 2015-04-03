package xpertss.net;


import java.net.InetAddress;

/**
 * A simple enumeration of IPv4 class types.
 * <p/>
 * Note IP Classes are not really used any more as they have been
 * superseded by classless inter-domain routing (CIDR).
 */
public enum IPClass {
   A {
      @Override public boolean matches(InetAddress address)
      {
         if(address != null && address.getAddress().length == 4) {
            byte first = address.getAddress()[0];
            return ((first & 0x80) == 0);
         }
         return false;
      }
   },
   B {
      @Override public boolean matches(InetAddress address)
      {
         if(address != null && address.getAddress().length == 4) {
            byte first = address.getAddress()[0];
            return ((first & 0xC0) == 0x80);
         }
         return false;
      }
   },
   C {
      @Override public boolean matches(InetAddress address)
      {
         if(address != null && address.getAddress().length == 4) {
            byte first = address.getAddress()[0];
            return ((first & 0xE0) == 0xC0);
         }
         return false;
      }
   },
   D {
      @Override public boolean matches(InetAddress address)
      {
         if(address != null && address.getAddress().length == 4) {
            byte first = address.getAddress()[0];
            return ((first & 0xF0) == 0xE0);
         }
         return false;
      }
   },
   E {
      @Override public boolean matches(InetAddress address)
      {
         if(address != null && address.getAddress().length == 4) {
            byte first = address.getAddress()[0];
            return ((first & 0xF8) == 0xF0);
         }
         return false;
      }
   };


   public abstract boolean matches(InetAddress address);
}
