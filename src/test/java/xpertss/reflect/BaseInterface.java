/**
 * Copyright 2016 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 6/10/2016
 */
package xpertss.reflect;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.Tainted;

@Tainted
public interface BaseInterface {

   @PostConstruct
   void baseInterMethod(@Nullable String param);

}
