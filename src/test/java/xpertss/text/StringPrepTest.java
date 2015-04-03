package xpertss.text;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringPrepTest {

   @Test
   public void testGetInstance()
   {
      assertEquals(SaslPrep.class, StringPrep.getInstance(Profile.SASLPrep).getClass());
      assertEquals(NamePrep.class, StringPrep.getInstance(Profile.NamePrep).getClass());
      assertEquals(NodePrep.class, StringPrep.getInstance(Profile.NodePrep).getClass());
      assertEquals(ResourcePrep.class, StringPrep.getInstance(Profile.ResourcePrep).getClass());
      assertEquals(ISCSIPrep.class, StringPrep.getInstance(Profile.ISCSIPrep).getClass());
      assertEquals(TracePrep.class, StringPrep.getInstance(Profile.TracePrep).getClass());



      assertSame(StringPrep.getInstance(Profile.SASLPrep), StringPrep.getInstance(Profile.SASLPrep));
      assertSame(StringPrep.getInstance(Profile.NamePrep), StringPrep.getInstance(Profile.NamePrep));
      assertSame(StringPrep.getInstance(Profile.NodePrep), StringPrep.getInstance(Profile.NodePrep));
      assertSame(StringPrep.getInstance(Profile.ResourcePrep), StringPrep.getInstance(Profile.ResourcePrep));
      assertSame(StringPrep.getInstance(Profile.ISCSIPrep), StringPrep.getInstance(Profile.ISCSIPrep));
      assertSame(StringPrep.getInstance(Profile.TracePrep), StringPrep.getInstance(Profile.TracePrep));

      assertNotSame(StringPrep.getInstance(Profile.SASLPrep), StringPrep.getInstance(Profile.NamePrep));
      assertNotSame(StringPrep.getInstance(Profile.SASLPrep), StringPrep.getInstance(Profile.TracePrep));
      assertNotSame(StringPrep.getInstance(Profile.SASLPrep), StringPrep.getInstance(Profile.ResourcePrep));
      assertNotSame(StringPrep.getInstance(Profile.SASLPrep), StringPrep.getInstance(Profile.NodePrep));
      assertNotSame(StringPrep.getInstance(Profile.SASLPrep), StringPrep.getInstance(Profile.ISCSIPrep));

      assertNotSame(StringPrep.getInstance(Profile.TracePrep), StringPrep.getInstance(Profile.NamePrep));
      assertNotSame(StringPrep.getInstance(Profile.TracePrep), StringPrep.getInstance(Profile.NodePrep));
      assertNotSame(StringPrep.getInstance(Profile.TracePrep), StringPrep.getInstance(Profile.ResourcePrep));
      assertNotSame(StringPrep.getInstance(Profile.TracePrep), StringPrep.getInstance(Profile.ISCSIPrep));

      assertNotSame(StringPrep.getInstance(Profile.NamePrep), StringPrep.getInstance(Profile.NodePrep));
      assertNotSame(StringPrep.getInstance(Profile.NamePrep), StringPrep.getInstance(Profile.ResourcePrep));
      assertNotSame(StringPrep.getInstance(Profile.NamePrep), StringPrep.getInstance(Profile.ISCSIPrep));

      assertNotSame(StringPrep.getInstance(Profile.ISCSIPrep), StringPrep.getInstance(Profile.NodePrep));
      assertNotSame(StringPrep.getInstance(Profile.ISCSIPrep), StringPrep.getInstance(Profile.ResourcePrep));

      assertNotSame(StringPrep.getInstance(Profile.NodePrep), StringPrep.getInstance(Profile.ResourcePrep));
   }

}