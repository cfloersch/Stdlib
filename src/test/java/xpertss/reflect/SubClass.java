/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/10/2016
 */
package xpertss.reflect;


import javax.annotation.Nullable;
import javax.annotation.meta.Exclusive;
import javax.annotation.meta.Exhaustive;
import javax.xml.bind.annotation.XmlElement;

@Exhaustive
public class SubClass extends BaseClass {

   @XmlElement
   protected String test = "sub";

   @Override
   @Exclusive
   public void baseMethod(@Nullable Integer i)
   {
   }

   public String getSuperTest()
   {
      return super.test;
   }

   public String getTest()
   {
      return test;
   }

}
