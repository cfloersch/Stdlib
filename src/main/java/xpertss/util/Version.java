/**
 * Created By: cfloersch
 * Date: 2/6/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import xpertss.lang.Numbers;
import xpertss.lang.Objects;

import java.io.Serializable;

/**
 * A basic class that supports a three digit version number. The class provides
 * various comparison methods.
 */
public final class Version implements Comparable<Version>, Serializable {


   private int major;
   private int minor;
   private int point;

   /**
    * Create a version object from the given major and minor version numbers.
    */
   public Version(int major, int minor)
   {
      this(major, minor, 0);
   }

   /**
    * Create a version object from the given major, minor, and point version
    * numbers.
    */
   public Version(int major, int minor, int point)
   {
      this.major = Numbers.gt(0, major, "major");
      this.minor = Numbers.gte(0, minor, "minor");
      this.point = Numbers.gte(0, point, "point");
   }



   /**
    * Returns the major version as an int.
    */
   public int getMajorVersion()
   {
      return major;
   }

   /**
    * Returns the minor version as an int.
    */
   public int getMinorVersion()
   {
      return minor;
   }

   /**
    * Returns the point version as an int.
    */
   public int getPointVersion()
   {
      return point;
   }





   /**
    * Returns {@code true} if the version is at least the specified major and minor
    * version, {@code false} otherwise.
    */
   public boolean isAtLeast(int major, int minor)
   {
      return isAtLeast(major, minor, 0);
   }

   /**
    * Returns {@code true} if the version is at least the specified major, minor, and
    * point version, {@code false} otherwise.
    */
   public boolean isAtLeast(int major, int minor, int point)
   {
      return getPointVersion() >= point &&
               getMinorVersion() >= minor &&
                  getMajorVersion() >= major;
   }




   @Override
   public int compareTo(Version o)
   {
      return Comparison.start()
            .compare(major, o.major)
            .compare(minor, o.minor)
            .compare(point, o.point)
            .result();
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(major, minor, point);
   }

   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof Version) {
         Version other = (Version) obj;
         return major == other.major &&
                  minor == other.minor &&
                  point == other.point;
      }
      return false;
   }


   @Override
   public String toString()
   {
      StringBuilder buf = new StringBuilder();
      buf.append(major).append(".").append(minor);
      if(point > 0) buf.append(".").append(point);
      return buf.toString();
   }


}
