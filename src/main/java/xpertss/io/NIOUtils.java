/*
 * Created on Mar 6, 2006
 */
package xpertss.io;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;

/**
 * Utility methods for operating with NIO Buffers, Channels, and Selectors.
 */
@SuppressWarnings("UnusedDeclaration")
public final class NIOUtils {

   private NIOUtils() { }


   /**
    * Open and return a server socket channel with the specified blocking mode
    * enabled or {@code null} if there is an error opening the channel.
    */
   public static ServerSocketChannel openServerSocket(boolean blocking)
   {
      try {
         return (ServerSocketChannel) ServerSocketChannel.open().configureBlocking(blocking);
      } catch(Exception ex) { return null; }
   }

   /**
    * Open and return a datagram channel with the specified blocking mode enabled
    * or {@code null} if there is an error opening the channel.
    */
   public static DatagramChannel openUdpSocket(boolean blocking)
   {
      try {
         return (DatagramChannel) DatagramChannel.open().configureBlocking(blocking);
      } catch(Exception ex) { return null; }
   }


   /**
    * Open and return a socket channel with the specified blocking mode enabled or
    * {@code null} if there is an error opening the channel.
    */
   public static SocketChannel openTcpSocket(boolean blocking)
   {
      try {
         return (SocketChannel) SocketChannel.open().configureBlocking(blocking);
      } catch(Exception ex) { return null; }
   }


   /**
    * Open and return a selector or {@code null} if there is an error opening the
    * selector.
    */
   public static Selector openSelector()
   {
      try {
         return Selector.open();
      } catch(Exception ex) { return null; }
   }



   /**
    * Shutdown the given channel.
    *
    * @param channel The channel to close.
    * @return True if successful, false if an error occurred
    */
   public static boolean close(Channel channel)
   {
      try { 
         channel.close(); 
         return true;
      } catch(Exception ex) {
         return false;
      }
   }
   
   /**
    * Shutdown the given selector.
    *
    * @param selector The selector to close.
    * @return True if successful, false if an error occurred
    */
   public static boolean close(Selector selector)
   {
      try { 
         selector.close();
         return true;
      } catch(Exception ex) {
         return false;
      }
   }





   /**
    * Modify the given selection key so that it does not include the read
    * operation.
    *
    * @param key The selection key to modify
    * @return True is the key was modified, false otherwise
    */
   public static boolean disableRead(SelectionKey key)
   {
      try {
         key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
         return true;
      } catch(Exception ex) {
         return false;
      }
   }
   
   /**
    * Modify the given selection key so that it does not include the write
    * operation.
    *
    * @param key The selection key to modify
    * @return True is the key was modified, false otherwise
    */
   public static boolean disableWrite(SelectionKey key)
   {
      try {
         key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
         return true;
      } catch(Exception ex) {
         return false;
      }
   }
   
   /**
    * Modify the given selection key so that it does not include the accept
    * operation.
    *
    * @param key The selection key to modify
    * @return True is the key was modified, false otherwise
    */
   public static boolean disableAccept(SelectionKey key)
   {
      try {
         key.interestOps(key.interestOps() & ~SelectionKey.OP_ACCEPT);
         return true;
      } catch(Exception ex) {
         return false;
      }
   }

   /**
    * Modify the given selection key so that it does not include the connect
    * operation.
    *
    * @param key The selection key to modify
    * @return True is the key was modified, false otherwise
    */
   public static boolean disableConnect(SelectionKey key)
   {
      try {
         key.interestOps(key.interestOps() & ~SelectionKey.OP_CONNECT);
         return true;
      } catch(Exception ex) {
         return false;
      }
   }
   

   /**
    * Modify the given selection key so that it does include the read
    * operation.
    *
    * @param key The selection key to modify
    * @return True is the key was modified, false otherwise
    */
   public static boolean enableRead(SelectionKey key)
   {
      try {
         key.interestOps(key.interestOps() | SelectionKey.OP_READ);
         return true;
      } catch(Exception ex) {
         return false;
      }
   }

   /**
    * Modify the given selection key so that it does include the write
    * operation.
    *
    * @param key The selection key to modify
    * @return True is the key was modified, false otherwise
    */
   public static boolean enableWrite(SelectionKey key)
   {
      try {
         key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
         return true;
      } catch(Exception ex) {
         return false;
      }
   }
   
   /**
    * Modify the given selection key so that it does include the accept
    * operation.
    *
    * @param key The selection key to modify
    * @return True is the key was modified, false otherwise
    */
   public static boolean enableAccept(SelectionKey key)
   {
      try {
         key.interestOps(key.interestOps() | SelectionKey.OP_ACCEPT);
         return true;
      } catch(Exception ex) {
         return false;
      }
   }
   
   /**
    * Modify the given selection key so that it does include the connect
    * operation.
    *
    * @param key The selection key to modify
    * @return True is the key was modified, false otherwise
    */
   public static boolean enableConnect(SelectionKey key)
   {
      try {
         key.interestOps(key.interestOps() | SelectionKey.OP_CONNECT);
         return true;
      } catch(Exception ex) {
         return false;
      }
   }



   /**
    * Returns {@code true} if the specified selection key has read enabled,
    * {@code false} otherwise.
    */
   public static boolean isReadEnabled(SelectionKey key)
   {
      return ((key.interestOps() & SelectionKey.OP_READ) != 0);
   }

   /**
    * Returns {@code true} if the specified selection key has write enabled,
    * {@code false} otherwise.
    */
   public static boolean isWriteEnabled(SelectionKey key)
   {
      return ((key.interestOps() & SelectionKey.OP_WRITE) != 0);
   }

   /**
    * Returns {@code true} if the specified selection key has accept enabled,
    * {@code false} otherwise.
    */
   public static boolean isAcceptEnabled(SelectionKey key)
   {
      return ((key.interestOps() & SelectionKey.OP_ACCEPT) != 0);
   }

   /**
    * Returns {@code true} if the specified selection key has connect enabled,
    * {@code false} otherwise.
    */
   public static boolean isConnectEnabled(SelectionKey key)
   {
      return ((key.interestOps() & SelectionKey.OP_CONNECT) != 0);
   }



}
