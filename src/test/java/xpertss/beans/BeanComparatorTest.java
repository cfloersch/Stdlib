package xpertss.beans;

import org.junit.Test;

import static org.junit.Assert.*;

public class BeanComparatorTest {

   @Test
   public void testSimple()
   {
      BeanComparator<TestBean, String> objectUnderTest = new BeanComparator<>("name");
      TestBean chris = new TestBean("Chris");
      TestBean doug = new TestBean("Doug");
      assertEquals(-1, objectUnderTest.compare(chris, doug));
   }



   private static class TestBean {


      private String name;

      private TestBean(String name)
      {
         this.name = name;
      }

      public String getName()
      {
         return name;
      }


   }

}