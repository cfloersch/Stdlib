/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/10/2016
 */
package xpertss.reflect;

import org.junit.Test;
import org.mockito.Mock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.meta.Exclusive;
import java.util.List;

@Exclusive
public class BaseClass implements BaseInterface {

   @Mock
   protected String test = "base";

   @Override
   @Test
   public void baseInterMethod(@Nullable String param)
   {
   }

   public void baseMethod(@Nonnull Integer i)
   {

   }

   public void baseGenericMethod(List<String> keys)
   {
   }


}
