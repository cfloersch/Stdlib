package xpertss.net;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: cfloersch
 * Date: 12/13/12
 * Time: 7:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryBuilderTest {

   @Test
   public void testCreate()
   {
      QueryBuilder builder = QueryBuilder.create("name=cfloersch&pass=password");
      assertEquals("name=cfloersch&pass=password", builder.build());
      assertEquals(2, builder.size());

      builder = QueryBuilder.create();
      assertEquals("", builder.build());
      assertEquals(0, builder.size());
   }


   @Test
   public void testMutators()
   {
      QueryBuilder builder = QueryBuilder.create();
      assertEquals(0, builder.size());
      builder.add("user", null).add("user", "chris");
      assertEquals("user=&user=chris", builder.build());
      builder.set("user", "fred");
      assertEquals("user=fred", builder.build());
      builder.setAll("user", "joe", "pass", "password");
      assertEquals("user=joe&pass=password", builder.build());
      builder.add("locale", "en_US");
      assertEquals("user=joe&pass=password&locale=en_US", builder.build());
      builder.setQuery("lang=en&country=US&currency=dollar");
      assertEquals("lang=en&country=US&currency=dollar", builder.build());
      builder.clear();
      assertEquals("", builder.build());
   }


   @Test
   public void testRemove()
   {
      QueryBuilder builder = QueryBuilder.create("name=Chris&pass=password&name=Joe&name=Beth");
      assertEquals(4, builder.size());
      String[] values = builder.get("name");
      assertEquals(3, values.length);
      assertEquals("Chris", values[0]);
      assertEquals("Beth", values[2]);
      builder.remove("name");
      assertEquals(1, builder.size());
      builder.remove("pas");
      assertEquals(1, builder.size());
   }

   @Test
   public void testRemoveNull()
   {
      QueryBuilder builder = QueryBuilder.create("name=Chris&pass=password&name=Joe&name=Beth");
      assertEquals(4, builder.size());
      builder.remove(null);
      assertEquals(4, builder.size());
   }

   @Test
   public void testGet()
   {
      QueryBuilder builder = QueryBuilder.create("name=Chris&pass=password&name=&name=Beth");
      String[] values = builder.get("name");
      assertEquals(3, values.length);
      assertEquals("Chris", values[0]);
      assertEquals("", values[1]);
      assertEquals("Beth", values[2]);
      assertEquals(0, builder.get("pas").length);
   }

   @Test
   public void testGetNull()
   {
      QueryBuilder builder = QueryBuilder.create("name=Chris&pass=password&name=&name=Beth");
      String[] values = builder.get(null);
      assertEquals(0, values.length);
   }

   @Test
   public void testAdd()
   {
      QueryBuilder builder = QueryBuilder.create();
      builder.add("name", "Beth").add("name", "Joe").add("name","Fred");
      assertEquals("name=Beth&name=Joe&name=Fred", builder.build());
      assertEquals(3, builder.size());
   }

   @Test (expected = IllegalArgumentException.class)
   public void testAddNullKey()
   {
      QueryBuilder builder = QueryBuilder.create();
      builder.add(null, "joe");
   }

   @Test
   public void testAddNullValue()
   {
      QueryBuilder builder = QueryBuilder.create();
      builder.add("name", "Beth").add("name", null).add("name","Fred");
      assertEquals("name=Beth&name=&name=Fred", builder.build());
      assertEquals(3, builder.size());
   }

   @Test (expected = IllegalArgumentException.class)
   public void testSetNullKey()
   {
      QueryBuilder builder = QueryBuilder.create("name=Chris&pass=password&name=&name=Beth");
      builder.set(null, "joe");
   }

   @Test
   public void testSetNullValue()
   {
      QueryBuilder builder = QueryBuilder.create("name=Chris&pass=password&name=&name=Beth");
      builder.set("name",null);
      assertEquals("pass=password&name=", builder.build());
   }

   @Test
   public void testSetQueryEmptyArray()
   {
      QueryBuilder builder = QueryBuilder.create("name=Chris&pass=password&name=&name=Beth");
      builder.setAll(new String[0]);
      assertEquals("", builder.build());
   }

   @Test
   public void testSetQueryNull()
   {
      QueryBuilder builder = QueryBuilder.create("name=Chris&pass=password&name=&name=Beth");
      builder.setQuery(null);
      assertEquals("", builder.build());
   }
}
