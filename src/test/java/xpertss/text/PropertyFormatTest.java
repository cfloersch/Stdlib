package xpertss.text;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class PropertyFormatTest {

   private Properties props;

   @Before
   public void setUp()
   {
      props = new Properties();
      props.setProperty("user.home", "/home/jblow");
      props.setProperty("java.io.tmpdir", "/tmp");
      props.setProperty("user.name", "jblow");
   }


   @Test
   public void testSingleReplace()
   {
      String key = "${user.home}/output.txt";
      assertEquals("/home/jblow/output.txt", PropertyFormat.format(props, key));
   }

   @Test
   public void testDoubleReplace()
   {
      String key = "${java.io.tmpdir}/${user.name}.txt";
      assertEquals("/tmp/jblow.txt", PropertyFormat.format(props, key));
   }

   @Test
   public void testReplaceNotNeeded()
   {
      String key = "$100 dollars";
      String value = PropertyFormat.format(null, key);
      assertEquals(key, value);
   }

   @Test
   public void testFormatNull()
   {
      assertNull(PropertyFormat.format(null, null));
   }

   @Test
   public void testFormatEmpty()
   {
      assertEquals("", PropertyFormat.format(null, ""));
   }

   @Test
   public void testSystemPropertiesFallback()
   {
      String key = "/tmp/${user.name}.txt";
      assertEquals("/tmp/" + System.getProperty("user.name") + ".txt", PropertyFormat.format(null, key));
   }

}