package xpertss.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

import static org.junit.Assert.*;

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
      Config config = new Config(props);

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
      Config config = new Config(props);
      assertEquals("10.0.0.1", config.subConfig("server.harvester.host").getNotNullProperty("address"));
      assertEquals("162.43.8.122", config.subConfig("server.harvester.stun").getNotNullProperty("address"));
      assertEquals(5328, config.subConfig("server.harvester.stun").getInt("port"));

      // Do not allow partial prefixes
      assertNull(config.subConfig("server.harvester.stu").getProperty("address"));

   }


   @Test(expected = ResourceNotFoundException.class)
   public void loadNonExistent()
   {
      Config.load("/non-existent.txt", true);
   }



}