package xpertss.time;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: cfloersch
 * Date: 12/18/12
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimeProviderTest {

   @Before
   public void setUp()
   {
      TimeProvider.stub(null);
   }

   @Test
   public void testSystemTimeProvider()
   {
      Class<?> cls = TimeProvider.get().getClass();
      assertTrue(cls.getSimpleName().indexOf("SystemTime") != -1);
   }

   @Test
   public void testSystemTimeProviderChildThread() throws Exception
   {
      ExecutorService executor = Executors.newFixedThreadPool(1);
      Callable<Boolean> child = new Callable<Boolean>() {
         @Override
         public Boolean call()
               throws Exception
         {
            Class<?> cls = TimeProvider.get().getClass();
            return cls.getSimpleName().indexOf("SystemTime") != -1;
         }
      };
      assertTrue(executor.submit(child).get());
   }

   @Test
   public void testTimeProviderStubSameThread()
   {
      TimeProvider mock = mock(TimeProvider.class);
      TimeProvider.stub(mock);
      assertSame(mock, TimeProvider.get());
   }

   @Test
   public void testTimeProviderStubChildThread() throws Exception
   {
      ExecutorService executor = Executors.newFixedThreadPool(1);

      final TimeProvider mock = mock(TimeProvider.class);
      TimeProvider.stub(mock);

      Callable<Boolean> child = new Callable<Boolean>() {
         @Override
         public Boolean call()
               throws Exception
         {
            return mock == TimeProvider.get();
         }
      };
      assertTrue(executor.submit(child).get());
   }

}
