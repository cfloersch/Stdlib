package xpertss.function;

import org.junit.Test;
import xpertss.util.Iterables;
import xpertss.util.Lists;

import java.util.List;
import java.util.function.Consumer;

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
      consumer.accept("Hello");
      consumer.accept("Goodbye");
      verify(first, times(2)).accept(anyString());
      verify(second, times(2)).accept(anyString());
      verify(first).accept(eq("Hello"));
      verify(first).accept(eq("Goodbye"));
      verify(second).accept(eq("Hello"));
      verify(second).accept(eq("Goodbye"));
   }

   @Test
   public void testComposeOrdering()
   {
      Consumer<String> first = mock(Consumer.class);
      doThrow(new RuntimeException()).when(first).accept(anyString());
      Consumer<String> second = mock(Consumer.class);

      Consumer<String> consumer = Consumers.compose(first, second);
      try {
         consumer.accept("Hello");
         fail();
      } catch(Exception e) { /* Ignore */}

      verify(first, times(1)).accept(anyString());
      verify(second, times(0)).accept(anyString());
      verify(first).accept(eq("Hello"));
      verify(second, times(0)).accept(eq("Hello"));
   }

   @Test
   public void testNullAndEmpty()
   {
      Consumer<String> first = mock(Consumer.class);
      Consumer<String> second = mock(Consumer.class);
      Consumers.compose(null, second).accept("Hello");
      Consumers.compose(first, null).accept("Hello");
      Consumers.compose(first, second, null).accept("Hello");
      Consumers.compose((Consumer<String>)null).accept("Hello");
      Consumers.compose((Consumer<String>[])null).accept("Hello");
      Consumers.compose().accept("Hello");

      verify(first, times(2)).accept(anyString());
      verify(second, times(2)).accept(anyString());
   }

   @Test
   public void testStdOut()
   {
      Consumers.stdOut().accept("Hello");
      Consumers.stdOut().accept(3);
      Consumers.stdOut().accept(Thread.currentThread());
   }

   @Test
   public void testStdErr()
   {
      Consumers.stdErr().accept("Hello");
      Consumers.stdErr().accept(3);
      Consumers.stdErr().accept(Thread.currentThread());
   }


   @Test
   public void testFunctional()
   {
      List<String> strings = Lists.of("Hello", "goodbye");
      final List<String> result = Lists.newArrayList();
      Iterables.forEach(strings, s -> {
         if(s != null && s.length() > 0 && Character.isLowerCase(s.charAt(0))) result.add(s);
      });
      assertEquals(Lists.of("goodbye"), result);
   }
}
