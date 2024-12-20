package xpertss.util;


import org.junit.jupiter.api.Test;
import xpertss.time.Day;

import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ConfigTest {


   @Test
   public void testGetStrings()
   {
      String[] result = new String[] { "eth0", "eth1" };



      Properties props = new Properties();
      props.setProperty("comma-no-space", "eth0,eth1");
      props.setProperty("space-no-comma", "eth0 eth1");
      props.setProperty("space-and-comma", "eth0, eth1");
      props.setProperty("multiple-space", "eth0   eth1");
      props.setProperty("space-comma-space", "eth0 , eth1");
      Config config = Config.create(props);

      assertTrue(Arrays.equals(result, config.getStrings("comma-no-space")));
      assertTrue(Arrays.equals(result, config.getStrings("space-no-comma")));
      assertTrue(Arrays.equals(result, config.getStrings("space-and-comma")));
      assertTrue(Arrays.equals(result, config.getStrings("multiple-space")));
      assertTrue(Arrays.equals(result, config.getStrings("space-comma-space")));

   }


   @Test
   public void testSubConfig()
   {
      Properties props = new Properties();
      props.setProperty("server.harvester.host.address", "10.0.0.1");
      props.setProperty("server.harvester.host.allowed", "eth0,eth1");
      props.setProperty("server.harvester.stun.address", "162.43.8.122");
      props.setProperty("server.harvester.stun.port", "5328");
      Config config = Config.create(props);
      assertEquals("10.0.0.1", config.subConfig("server.harvester.host").getNotNullProperty("address"));
      assertEquals("162.43.8.122", config.subConfig("server.harvester.stun").getNotNullProperty("address"));
      assertEquals(5328, config.subConfig("server.harvester.stun").getInt("port"));

      // Do not allow partial prefixes
      assertNull(config.subConfig("server.harvester.stu").getProperty("address"));

   }


   @Test
   public void loadNonExistent()
   {
      assertThrows(ResourceNotFoundException.class, ()->{
         Config.load("/non-existent.txt", true);
      });
   }


   @Test
   public void testResolveSimple()
   {
      Properties props = new Properties();
      props.setProperty("user.home", "/home/joeblow");
      props.setProperty("home.directory", "${user.home}");
      Config config = Config.create(props).resolve();
      assertEquals("/home/joeblow", config.getProperty("home.directory"));
   }

   @Test
   public void testResolveComplex()
   {
      Properties props = new Properties();
      props.setProperty("user.name", "joeblow");
      props.setProperty("user.dir", "/home/${user.name}/${none}/${temp.dir}/pdf");
      props.setProperty("temp.dir", "tmp");
      Config config = Config.create(props).resolve();
      assertEquals("/home/joeblow/${none}/tmp/pdf", config.getProperty("user.dir"));
      Properties overlay = new Properties();
      overlay.setProperty("none", "documents");
      config = config.overlayWith(overlay).resolve();
      assertEquals("/home/joeblow/documents/tmp/pdf", config.getProperty("user.dir"));
   }

}