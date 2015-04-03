package xpertss.io;

import xpertss.lang.Objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


/**
 * Utility methods to assist with the serialization process.
 * <ul>
 *    <li>Deep clone using serialization</li>
 *    <li>Serialize managing finally and IOException</li>
 *    <li>Deserialize managing finally and IOException</li>
 * </ul>
 */
public final class Serialization {

   private Serialization() { }

   /**
    * Deep clone an Object using serialization.
    * <p>
    * This is many times slower than writing clone methods by hand on all objects in
    * your object graph. However, for complex object graphs, or for those that don't
    * support deep cloning this can be a simple alternative implementation. Of course
    * all the objects must be Serializable.
    *
    * @param object The object to clone
    * @return The cloned object or null if there was an error cloning
    */
   public static <T> T clone(T object)
   {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      if(serialize(object, baos)) return Objects.cast(deserialize(baos.toByteArray()));
      return null;
   }



   /**
    * Serializes an Object to the specified stream. This will return {@code true} if the
    * object was successfully serialized to the specified output stream, {@code false}
    * otherwise.
    *
    * @param obj The object to serialize
    * @param outputStream The output stream to serialize the object to
    * @return {@code true} if the object was serialized, {@code false} otherwise
    */
   public static boolean serialize(Object obj, OutputStream outputStream)
   {
      try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
         oos.writeObject(obj);
         return true;
      } catch(Exception e) {
         return false;
      }
   }

   /**
    * Serializes an Object to a byte array for storage. This will return {@code null} if
    * the specified object cannot be serialized for any reason including because it was
    * {@code null}.
    *
    * @param obj The Serializable object to serialize
    * @return The serialized object data or <tt>null</tt>
    */
   public static byte[] serialize(Object obj)
   {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
         oos.writeObject(obj);
      } catch(Exception e) {
         return null;
      }
      return baos.toByteArray();
   }


   /**
    * Deserializes an Object from the specified stream. Returns {@code null} if the
    * supplied data does not represent an encoded object identifiable on this VM or
    * if the supplied input stream is {@code null}.
    *
    * @param inputStream The input source of the serialized object data
    * @return The deserialized object or {@code null}
    */
   public static <T> T deserialize(InputStream inputStream)
   {
      try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
         return Objects.cast(ois.readObject());
      } catch(Exception e) {
         return null;
      }
   }

   /**
    * Deserializes a single Object from an array of bytes. Returns {@code null} if the
    * supplied data does not represent an encoded object identifiable on this VM or if
    * the supplied object data is {@code null}.
    *
    * @param objectData The previously serialized object data
    * @return  The deserialized object or {@code null}
    */
   public static <T> T deserialize(byte[] objectData)
   {
      ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
      try (ObjectInputStream ois = new ObjectInputStream(bais)) {
         return Objects.cast(ois.readObject());
      } catch(Exception e) {
         return null;
      }
   }

}
