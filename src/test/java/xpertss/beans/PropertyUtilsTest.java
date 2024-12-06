/**
 * Created By: cfloersch
 * Date: 6/12/13
 * Copyright 2013 XpertSoftware
 */
package xpertss.beans;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PropertyUtilsTest {

   private SimpleBean objectToTestWith;

   @BeforeEach
   public void setUp()
   {
      objectToTestWith = new SimpleBean();
      objectToTestWith.setAge(30);
      objectToTestWith.setIncome(6.99);
      objectToTestWith.setName("John");
      objectToTestWith.setOpen(true);
      objectToTestWith.getData().add("Hello");
      objectToTestWith.setCount("one", 1);
   }

   @Test
   public void testGetSimpleIntProperty() throws Exception
   {
      assertEquals(30, PropertyUtils.getProperty(objectToTestWith, "age"));
   }

   @Test
   public void testGetSimpleBooleanProperty() throws Exception
   {
      assertEquals(true, PropertyUtils.getProperty(objectToTestWith, "open"));
   }

   @Test
   public void testGetSimpleDoubleProperty() throws Exception
   {
      assertEquals(6.99D, PropertyUtils.getProperty(objectToTestWith, "income"));
   }

   @Test
   public void testGetSimpleStringProperty() throws Exception
   {
      assertEquals("John", PropertyUtils.getProperty(objectToTestWith, "name"));
   }

   @Test
   public void testGetMappedProperty() throws Exception
   {
      assertEquals(1, PropertyUtils.getProperty(objectToTestWith, "count(one)"));
   }

   @Test
   public void testGetIndexedProperty() throws Exception
   {
      assertEquals("Hello", PropertyUtils.getProperty(objectToTestWith, "data[0]"));
   }

   @Test
   public void testGetInvalidIndexedPropertyThrowsIndexOutOfBoundsException() throws Exception
   {
      IndexOutOfBoundsException thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            PropertyUtils.getProperty(objectToTestWith, "data[1]");
         },
         "Expected IndexOutOfBoundsException"
      );

   }

   @Test
   public void testNestedGetters() throws Exception
   {
      NestedBean root = new NestedBean(null, "root");
      NestedBean second = new NestedBean(root, "second");
      NestedBean third = new NestedBean(second, "third");
      assertEquals("root", PropertyUtils.getProperty(third, "parent.parent.name"));
      assertEquals("second", PropertyUtils.getProperty(third, "parent.name"));
      assertEquals("root", PropertyUtils.getProperty(second, "parent.name"));
   }


   @Test
   public void testIndexedGetter() throws Exception
   {
      IndexedBean testObj = new IndexedBean();
      assertEquals("Hello", PropertyUtils.getProperty(testObj, "data[0]"));
      assertEquals("World", PropertyUtils.getProperty(testObj, "data[1]"));
   }

   @Test
   public void testIndexedGetterBadIndex() throws Exception
   {
      IndexOutOfBoundsException thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            PropertyUtils.getProperty(new IndexedBean(), "data[2]");
         },
         "Expected IndexOutOfBoundsException"
      );
   }



   @Test
   public void testCopy() throws Exception
   {
      SimpleBean copy = new SimpleBean();
      PropertyUtils.copyProperties(copy, objectToTestWith);
      assertEquals(objectToTestWith.getAge(), copy.getAge());
      assertEquals(objectToTestWith.getIncome(), copy.getIncome());
      assertEquals(objectToTestWith.getName(), copy.getName());
      assertEquals(objectToTestWith.isOpen(), copy.isOpen());
      assertEquals(objectToTestWith.getData(), copy.getData());
   }


   private static class IndexedBean {

      private String[] data = {"Hello", "World"};

      public String getData(int index)
      {
         return data[index];
      }



   }

   private static class NestedBean {

      private NestedBean parent;
      private String name;
      private NestedBean(NestedBean parent, String name)
      {
         this.parent = parent;
         this.name = name;
      }

      public NestedBean getParent()
      {
         return parent;
      }

      public String getName()
      {
         return name;
      }
   }

   private static class SimpleBean {

      private List<String> data = new ArrayList<>();
      private Map<String,Integer> count = new HashMap<>();
      private boolean open;
      private String name;
      private int age;
      private double income;


      public boolean isOpen()
      {
         return open;
      }

      public void setOpen(boolean open)
      {
         this.open = open;
      }

      public String getName()
      {
         return name;
      }

      public void setName(String name)
      {
         this.name = name;
      }

      public int getAge()
      {
         return age;
      }

      public void setAge(int age)
      {
         this.age = age;
      }

      public double getIncome()
      {
         return income;
      }

      public void setIncome(double income)
      {
         this.income = income;
      }

      public List<String> getData()
      {
         return data;
      }

      public void setData(List<String> data)
      {
         this.data = data;
      }


      public int getCount(String key)
      {
         return count.get(key);
      }

      public void setCount(String key, int value)
      {
         count.put(key, value);
      }

   }

}
