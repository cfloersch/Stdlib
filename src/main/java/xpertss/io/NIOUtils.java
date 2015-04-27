/*
 * Created on Mar 6, 2006
 */
package xpertss.io;

import xpertss.function.Predicate;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

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













   /**
    * Create a {@link Predicate} that tests whether a file exists.
    * <p/>
    * The options parameter may be used to indicate how symbolic links are handled
    * for the case that the file is a symbolic link. By default, symbolic links are
    * followed. If the option NOFOLLOW_LINKS is present then symbolic links are not
    * followed.
    */
   public static Predicate<Path> exists(LinkOption... options)
   {
      final LinkOption[] linkOptions = options;
      return new Predicate<Path>() {
         @Override public boolean apply(Path input)
         {
            return Files.exists(input, linkOptions);
         }
      };
   }

   /**
    * Create a {@link Predicate} that tests whether a file is a directory.
    * <p/>
    * The options array may be used to indicate how symbolic links are handled for
    * the case that the file is a symbolic link. By default, symbolic links are
    * followed and the file attribute of the final target of the link is read. If
    * the option {@link LinkOption#NOFOLLOW_LINKS} is present then symbolic links
    * are not followed.
    * <p/>
    * This implementation will treat {@link IOException}s as if the file is not
    * a directory.
    *
    * @param options options indicating how symbolic links are handled
    */
   public static Predicate<Path> isDirectory(LinkOption... options)
   {
      final LinkOption[] linkOptions = options;
      return new Predicate<Path>() {
         @Override public boolean apply(Path input)
         {
            return Files.isDirectory(input, linkOptions);
         }
      };
   }

   /**
    * Create a {@link Predicate} that  tests whether a file is a regular file with
    * opaque content.
    * <p/>
    * The options array may be used to indicate how symbolic links are handled for
    * the case that the file is a symbolic link. By default, symbolic links are
    * followed and the file attribute of the final target of the link is read. If
    * the option {@link LinkOption#NOFOLLOW_LINKS} is present then symbolic links
    * are not followed.
    * <p/>
    * This implementation will treat {@link IOException}s as if the file is not
    * a regular file.
    *
    * @param options options indicating how symbolic links are handled
    */
   public static Predicate<Path> isFile(LinkOption... options)
   {
      final LinkOption[] linkOptions = options;
      return new Predicate<Path>() {
         @Override public boolean apply(Path input)
         {
            return Files.isRegularFile(input, linkOptions);
         }
      };
   }


   /**
    * Create a {@link Predicate} that tests whether a file is executable.
    * <p/>
    * This implementation checks that a file exists and that this Java virtual machine
    * has appropriate privileges to execute the file. The semantics may differ when
    * checking access to a directory. For example, on UNIX systems, checking for
    * execute access checks that the Java virtual machine has permission to search the
    * directory in order to access file or subdirectories.
    * <p/>
    * Depending on the platform, this implementation may require to read file permissions,
    * access control lists, or other file attributes in order to check the effective
    * access to the file. Consequently, this implementation may not be atomic with respect
    * to other file system operations.
    * <p/>
    * Note that the result of this implementation is immediately outdated, there is no
    * guarantee that a subsequent attempt to execute the file will succeed (or even that
    * it will access the same file). Care should be taken when using this method in
    * security sensitive applications.
    */
   public static Predicate<Path> isExecutable()
   {
      return new Predicate<Path>() {
         @Override public boolean apply(Path input)
         {
            return Files.isExecutable(input);
         }
      };
   }


   /**
    * Create a {@link Predicate} that tests whether a file is readable.
    * <p/>
    * This implementation checks that a file exists and that this Java virtual machine has
    * appropriate privileges that would allow it open the file for reading.
    * <p/>
    * Depending on the platform, this method may require to read file permissions, access
    * control lists, or other file attributes in order to check the effective access to the
    * file. Consequently, this method may not be atomic with respect to other file system
    * operations.
    * <p/>
    * Note that the result of this method is immediately outdated, there is no guarantee that
    * a subsequent attempt to open the file for reading will succeed (or even that it will
    * access the same file). Care should be taken when using this method in security
    * sensitive applications.
    */
   public static Predicate<Path> isReadable()
   {
      return new Predicate<Path>() {
         @Override public boolean apply(Path input)
         {
            return Files.isReadable(input);
         }
      };
   }

   /**
    * Create a {@link Predicate} that tests whether a file is writable.
    * <p/>
    * This implementation checks that a file exists and that this Java virtual machine has
    * appropriate privileges that would allow it open the file for writing.
    * <p/>
    * Depending on the platform, this method may require to read file permissions, access
    * control lists, or other file attributes in order to check the effective access to the
    * file. Consequently, this method may not be atomic with respect to other file system
    * operations.
    * <p/>
    * Note that the result of this method is immediately outdated, there is no guarantee that
    * a subsequent attempt to open the file for writing will succeed (or even that it will
    * access the same file). Care should be taken when using this method in security
    * sensitive applications.
    */
   public static Predicate<Path> isWritable()
   {
      return new Predicate<Path>() {
         @Override public boolean apply(Path input)
         {
            return Files.isWritable(input);
         }
      };
   }

   /**
    * Create a {@link Predicate} that tests whether a file is a symbolic link.
    * <p/>
    * This implementation will treat {@link IOException}s as if the file is not a symbolic
    * link.
    */
   public static Predicate<Path> isSymbolicLink()
   {
      return new Predicate<Path>() {
         @Override public boolean apply(Path input)
         {
            return Files.isSymbolicLink(input);
         }
      };
   }

   /**
    * Create a {@link Predicate} that tests whether a file is considered hidden.
    * <p/>
    * The exact definition of hidden is platform or provider dependent. On UNIX for example a
    * file is considered to be hidden if its name begins with a period character ('.').  On
    * Windows a file is considered hidden if it isn't a directory and the DOS hidden attribute
    * is set.
    * <p/>
    * This implementation will treat {@link IOException}s as if the file is not hidden.
    */
   public static Predicate<Path> isHidden()
   {
      return new Predicate<Path>() {
         @Override public boolean apply(Path input)
         {
            try { return Files.isHidden(input); } catch(Exception e) { return false; }
         }
      };
   }













   // TODO Do I even want to encourage this old school IO function??


   private static final int BUF_SIZE = 0x1000; // 4K

   /**
    * Copies all bytes from the readable channel to the writable channel.
    * Does not close or flush either channel.
    *
    * @param from the readable channel to read from
    * @param to the writable channel to write to
    * @return the number of bytes copied
    * @throws IOException if an I/O error occurs
    */
   public static long copyTo(ReadableByteChannel from, WritableByteChannel to)
      throws IOException
   {
      return copyTo(from, to, false);
   }

   /**
    * Copies all bytes from the readable channel to the writable channel.
    * Will close both channels when done if specified.
    *
    * @param from the readable channel to read from
    * @param to the writable channel to write to
    * @param close - Close the channels when finished.
    * @return the number of bytes copied
    * @throws IOException if an I/O error occurs
    */
   public static long copyTo(ReadableByteChannel from, WritableByteChannel to, boolean close)
      throws IOException
   {
      ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
      long total = 0;
      try {
         while(from.read(buf) != -1) {
            buf.flip();
            while(buf.hasRemaining()) {
               total += to.write(buf);
            }
            buf.clear();
         }
      } finally {
         if(close) {
            close(from);
            close(to);
         }
      }
      return total;
   }



   /**
    * Utility method to fully read a ReadableByteChannel into a String. This can be
    * useful for loading text files to a string. This is essentially just a clean
    * code utility.
    *
    * @param from - ReadableByteChannel to read from.
    * @param charset - The charset to use when decoding bytes to characters.
    * @throws IOException if an I/O error occurs
    */
   public static String toString(ReadableByteChannel from, Charset charset)
      throws IOException
   {
      return toString(from, charset, false);
   }

   /**
    * Utility method to fully read a ReadableByteChannel into a String. This can be
    * useful for loading text files to a string. This is essentially just a clean
    * code utility. Optionally, close the channel when done.
    *
    * @param from - ReadableByteChannel to read from.
    * @param charset - The charset to use when decoding bytes to characters.
    * @param close - Close the channel after fully reading from it
    * @throws IOException if an I/O error occurs
    */
   public static String toString(ReadableByteChannel from, Charset charset, boolean close)
      throws IOException
   {
      if(from instanceof SelectableChannel && ((SelectableChannel)from).isBlocking())
         throw new IllegalBlockingModeException();
      return IOUtils.toString(Channels.newInputStream(from), charset, close);
   }










}
