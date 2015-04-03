package xpertss.function;

import org.junit.Test;
import xpertss.util.Iterables;
import xpertss.util.Lists;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by cfloersch on 1/17/14.
 */
public class ConsumersTest {

   @Test
   public void testCompose()
   {
      Consumer<String> first = mock(Consumer.class);
      Consumer<String> second = mock(Consumer.class);

      Consumer<String> consumer = Consumers.compose(first, second);
      consumer.apply("Hello");
      consumer.apply("Goodbye");
      verify(first, times(2)).apply(anyString());
      verify(second, times(2)).apply(anyString());
      verify(first).apply(eq("Hello"));
      verify(first).apply(eq("Goodbye"));
      verify(second).apply(eq("Hello"));
      verify(second).apply(eq("Goodbye"));
   }

   @Test
   public void testComposeOrdering()
   {
      Consumer<String> first = mock(Consumer.class);
      doThrow(new RuntimeException()).when(first).apply(anyString());
      Consumer<String> second = mock(Consumer.class);

      Consumer<String> consumer = Consumers.compose(first, second);
      try {
         consumer.apply("Hello");
         fail();
      } catch(Exception e) { /* Ignore */}

      verify(first, times(1)).apply(anyString());
      verify(second, times(0)).apply(anyString());
      verify(first).apply(eq("Hello"));
      verify(second, times(0)).apply(eq("Hello"));
   }

   @Test
   public void testNullAndEmpty()
   {
      Consumer<String> first = mock(Consumer.class);
      Consumer<String> second = mock(Consumer.class);
      Consumers.compose(null, second).apply("Hello");
      Consumers.compose(first, null).apply("Hello");
      Consumers.compose(first, second, null).apply("Hello");
      Consumers.compose((Consumer<String>)null).apply("Hello");
      Consumers.compose((Consumer<String>[])null).apply("Hello");
      Consumers.compose().apply("Hello");

      verify(first, times(2)).apply(anyString());
      verify(second, times(2)).apply(anyString());
   }

   @Test
   public void testStdOut()
   {
      Consumers.stdOut().apply("Hello");
      Consumers.stdOut().apply(3);
      Consumers.stdOut().apply(Thread.currentThread());
   }

   @Test
   public void testStdErr()
   {
      Consumers.stdErr().apply("Hello");
      Consumers.stdErr().apply(3);
      Consumers.stdErr().apply(Thread.currentThread());
   }


   @Test
   public void testFunctional()
   {
      List<String> strings = Lists.of("Hello", "goodbye");
      final List<String> result = Lists.newArrayList();
      Iterables.forEach(strings, new Consumer<String>() {
         @Override
         public void apply(String s)
         {
            if(s != null && s.length() > 0 && Character.isLowerCase(s.charAt(0))) result.add(s);
         }
      });
      assertEquals(Lists.of("goodbye"), result);
   }
}
