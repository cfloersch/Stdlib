/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 3/8/2016
 */
package xpertss.net;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Utilities for working with old school java sockets.
 */
public final class Sockets {

   private Sockets() { }

   /**
    * Close a socket and catch any exceptions. Returns {@code false} if an error was
    * encountered closing the socket, {@code true} otherwise.
    */
   public static boolean close(Socket sock)
   {
      try { if(sock != null) sock.close(); return true; } catch(Exception ex) { return false; }
   }

   /**
    * Close a datagram socket and catch any exceptions. Returns {@code false} if an
    * error was encountered closing the socket, {@code true} otherwise.
    */
   public static boolean close(DatagramSocket ds)
   {
      try { if(ds != null) ds.close(); return true; } catch(Exception ex) { return false; }
   }

   /**
    * Close a server socket and catch any exceptions. Returns {@code false} if an
    * error was encountered closing the socket, {@code true} otherwise.
    */
   public static boolean close(ServerSocket ss)
   {
      try { if(ss != null) ss.close(); return true; } catch(Exception ex) { return false; }
   }




   /**
    * Shutdown the input stream of the specified socket. Catches all exceptions.
    * Returns {@code true} if the input stream was shutdown without error,
    * {@code false} otherwise.
    */
   public static boolean shutdownInput(Socket sock)
   {
      try {
         if(sock != null) sock.shutdownInput();
         return true;
      } catch(Exception ignored) { return false; }
   }

   /**
    * Shutdown the output stream of the specified socket. Catches all exceptions.
    * Returns {@code true} if the output stream was shutdown without error,
    * {@code false} otherwise.
    */
   public static boolean shutdownOutput(Socket sock)
   {
      try {
         if(sock != null) sock.shutdownOutput();
         return true;
      } catch(Exception ignored) { return false; }
   }


}
