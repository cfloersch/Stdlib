/**
 * Created By: cfloersch
 * Date: 2/6/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import xpertss.lang.Integers;
import xpertss.lang.Strings;
import xpertss.security.SimplePrincipal;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

/**
 * General utility class that provides access to some frequently accessed system
 * properties. This includes a means to identify the operating system class the
 * jvm is running on as well as the versions of the operating system and the jvm.
 * <p/>
 * It also provides quick access to system paths like the users home directory,
 * the current working directory, the temp file directory, and the java install
 * directory.
 *
 */
public enum Platform {

   /*
      Win95(4,0), Win98(4,1), WinME(4,9),
      WinNT(4,0), Win2000(5,0), WinXP(5,1), Win2003(5,2),
      WinVista(6,0), Win2008(6,0), Win7(6,1),
      Win8(6,2), Win2012(6,2), Win81(6,3);
    */


   // Unix like operating systems
   /**
    * Special variant of Linux pioneered by Google
    */
   Android(true, false),

   /**
    * Apples new BSD based operating system
    */
   MacOSX(true, false),

   /**
    * All Linux variants including RedHat, Fedora, CentOS, Ubuntu, Caldera , SUSE, etc
    */
   Linux(true, false),

   /**
    * Sun solaris and other SunOS variants
    */
   Solaris(true, false),

   /**
    * Berkeley Software Distribution (BSD) based unix platforms including NetBSD,
    * OpenBSD, and FreeBSD among others.
    */
   Berkeley(true, false),

   /**
    * Other unix variants like HP-UX, AIX, Irix, Digital Unix, OSF/1, and MPE/iX.
    */
   Unix(true, false),


   // Non-unix like operating systems
   /**
    * Windows 95, 98, and Me
    */
   Win9x(false, true),

   /**
    * Windows Embedded CE
    */
   WinCE(false, true),

   /**
    * Windows NT, 2000, Server 2003, and XP. The WinNT type is a general
    * catch all for windows operating systems not specifically identified
    * by other values.
    */
   WinNT(false, true),

   /**
    * Windows Vista, 7, and Server 2008
    */
   WinAero(false, true),

   /**
    * Windows 8, 8.1 and Server 2012. This value will be returned for all
    * windows operating systems that have a version of 6.2 or higher until
    * this class is updated with newer operating systems.
    */
   WinRT(false, true),

   /**
    * Mac operating systems that preceded the release of OS X
    */
   Mac(false, false),

   /**
    * Operating systems like OS/2, Netware, VMS, etc
    */
   Other(false, false);








   private final boolean unix;
   private final boolean windows;

   private Platform(boolean unix, boolean windows)
   {
      this.unix = unix;
      this.windows = windows;
   }


   /**
    * Returns {@code true} if the operating system represents a flavor
    * of unix, {@code false} otherwise.
    */
   public boolean isUnix()
   {
      return unix;
   }

   /**
    * Returns {@code true} if the operating system represents a flavor
    * of unix, {@code false} otherwise.
    */
   public boolean isWindows()
   {
      return windows;
   }

   /**
    * Returns a string which represents the display name for the actual
    * underlying operating system. This value is not i18n.
    * <p/>
    * This is the result of {@code System.getProperty(os.name)}
    */
   public String getDisplayName() { return System.getProperty("os.name"); }






   /**
    * Get the current OS Version.
    */
   public static Version osVersion()
   {
      String[] parts = System.getProperty("os.version", "0.0").split("[\\.\\s-]");
      int[] segments = new int[parts.length];
      int count = 0, segment;
      for(String part : parts) {
         if(part.startsWith("V")) part = part.substring(1);
         if((segment = Integers.parse(part, -1)) < 0) continue;
         segments[count++] = segment;
      }
      if(count == 1) {
         return new Version(segments[0], 0);
      } else if(count > 2) {
         return new Version(segments[0], segments[1], segments[2]);
      }
      return new Version(segments[0], segments[1]);
   }

   /**
    * Get the current JVM Version.
    */
   public static Version javaVersion()
   {
      String[] parts = System.getProperty("java.version", "0.0").split("\\.");
      String[] subs = parts[2].split("_");
      return new Version(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(subs[0]));
   }








   /**
    * Returns the number of processors available to the Java virtual machine.
    */
   public static int processors()
   {
      return Runtime.getRuntime().availableProcessors();
   }



   /**
    * Returns the current java home property as a Path
    */
   public static Path javaHome()
   {
      return Paths.get(System.getProperty("java.home"));
   }

   /**
    * Returns the current temp directory property as a Path
    */
   public static Path tempDir()
   {
      return Paths.get(System.getProperty("java.io.tmpdir"));
   }

   /**
    * Returns the current working directory as a Path
    */
   public static Path workingDir()
   {
      return Paths.get(System.getProperty("user.dir"));
   }

   /**
    * Returns the current user's home directory as a Path
    */
   public static Path userHome()
   {
      return Paths.get(System.getProperty("user.home"));
   }


   /**
    * Returns the current user Principal or {@code null} if no user principal
    * can be determined.
    */
   public static Principal userPrincipal()
   {
      String username = System.getProperty("user.name");
      return (username == null) ? null : new SimplePrincipal(username);
   }



   /**
    * Returns an enumeration that represents the platform's operating system.
    * <p/>
    * If its not one of the well known operating systems this will return a
    * special type called {@link #Other}. That includes Netware, OS/2, VMS,
    * and many of the IBM operating systems.
    * <p/>
    * The generic {@link #Unix} type is returned for HP-UX, AIX, Irix, MPE/iX,
    * Digital Unix, NetBSD, OpenBSD, and FreeBSD.
    */
   public static Platform current()
   {
      String osName = System.getProperty("os.name", "generic").toLowerCase();
      if(osName.contains("os x")) {
         return MacOSX;
      } else if(osName.contains("mac")) {
         return Mac; // Do any of these still exist??
      } else if(osName.contains("windows")) {
         Version osVersion = osVersion();
         if(osName.contains("95") || osName.contains("98") || osName.contains("me")) {
            return Win9x;
         } else if(osName.contains("ce")) {
            return WinCE;
         } else if(osVersion.isAtLeast(6, 2)) {
            return WinRT;
         } else if(osVersion.isAtLeast(6, 0)) {
            return WinAero;
         }
         return WinNT;  // NT, 2000, 2003, and XP
      } else if(osName.contains("solaris") || osName.contains("sunos")) {
         return Solaris;
      } else if(osName.contains("Linux")) {
         return (contains("java.vm.name", "dalvik")) ? Android : Linux;
      } else if(osName.contains("bsd")) {
         // NetBSD, OpenBSD, FreeBSD (possibly other BSDs)
         return Berkeley;
      } else if(osName.contains("aix") || osName.contains("unix") ||
                  osName.contains("irix") || osName.contains("mpe/ix") ||
                  (osName.contains("hp") && osName.contains("ux")) ||
                  osName.contains("osf1")) {
         // Aix, Digital Unix, Irix, MPE/iX, HP-UX, and OSF1
         return Unix;
      }
      return Other;
   }



   private static boolean contains(String key, String value)
   {
      String propValue = Strings.toLower(System.getProperty("java.vm.name"));
      return propValue != null && propValue.contains(value);
   }

}
