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

   public static Stream<String> lines(Reader input) throws IOException
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


   public static Stream<String> lines(InputStream input, Charset cs) throws IOException
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
