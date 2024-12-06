/*
 * Copyright 2022 XpertSoftware
 *
 * Created By: cfloersch
 * Date: 5/12/2022
 */
package xpertss.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.stream.Stream;

public final class Streams {

   private Streams() { }

   /**
    * Returns a Stream, the elements of which are lines read from the specified Reader.
    * The Stream is lazily populated, i.e., read only occurs during the terminal stream
    * operation.
    * <p/>
    * Operation is unknown if anything else is operating on the supplied Reader during
    * operation.
    * <p/>
    * If an IOException is thrown when accessing the underlying Reader, it is wrapped
    * in an UncheckedIOException which will be thrown from the Stream method that
    * caused the read to take place.
    *
    * @param input The source of string content
    * @return a Stream<String> providing the lines of text described by the Reader
    */
   public static Stream<String> lines(Reader input)
   {
      BufferedReader br = new BufferedReader(input);
      try {
         return br.lines().onClose(asUncheckedRunnable(br));
      } catch (Error|RuntimeException e) {
         try {
            br.close();
         } catch (IOException ex) {
            try {
               e.addSuppressed(ex);
            } catch (Throwable ignore) {}
         }
         throw e;
      }
   }


   /**
    * Returns a Stream, the elements of which are lines read from the specified InputStream.
    * The Stream is lazily populated, i.e., read only occurs during the terminal stream
    * operation.
    * <p/>
    * Operation is unknown if anything else is operating on the supplied InputStream during
    * operation.
    * <p/>
    * If an IOException is thrown when accessing the underlying InputStream, it is wrapped
    * in an UncheckedIOException which will be thrown from the Stream method that caused
    * the read to take place.
    * <p/>
    * InputStream content is converted into String content using the specified Charset.
    *
    * @param input The source of string content
    * @param cs The Charset used to convert the input into Strings.
    * @return a Stream<String> providing the lines of text described by the InputStream
    */
   public static Stream<String> lines(InputStream input, Charset cs)
   {
      InputStreamReader reader = new InputStreamReader(input, cs);
      BufferedReader br = new BufferedReader(reader);
      try {
         return br.lines().onClose(asUncheckedRunnable(br));
      } catch (Error|RuntimeException e) {
         try {
            br.close();
         } catch (IOException ex) {
            try {
               e.addSuppressed(ex);
            } catch (Throwable ignore) {}
         }
         throw e;
      }
   }



   private static Runnable asUncheckedRunnable(Closeable c)
   {
      return () -> {
         try {
            c.close();
         } catch (IOException e) {
            throw new UncheckedIOException(e);
         }
      };
   }

}
