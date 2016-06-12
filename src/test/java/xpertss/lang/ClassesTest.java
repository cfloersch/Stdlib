package xpertss.lang;

import junit.framework.Assert;
import org.junit.Test;


import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * User: cfloersch
 * Date: 12/9/12
 */
public class ClassesTest {


   @Test
   public void testFindSuperClasses()
   {
      Set<Class<?>> classes = Classes.findSuperClasses(Integer.class);
      assertTrue(classes.contains(Comparable.class));
      assertTrue(classes.contains(Integer.class));
      assertTrue(classes.contains(Number.class));
      assertTrue(classes.contains(Serializable.class));
      assertFalse(classes.contains(Object.class));
      assertEquals(4, classes.size());
   }


   @Test
   public void testToArray()
   {
      Class[] array = Classes.toArray(Long.class, Integer.class);
      assertNotNull(array);
      assertEquals(2, array.length);
      assertSame(Long.class, array[0]);
      assertSame(Integer.class, array[1]);
   }

   @Test
   public void testArrayClassType()
   {
      boolean[] bs = new boolean[0];
      assertEquals("boolean[]", Classes.getArrayClassType(bs.getClass()));
      byte[] bytes = new byte[0];
      assertEquals("byte[]", Classes.getArrayClassType(bytes.getClass()));
      short[] shorts = new short[0];
      assertEquals("short[]", Classes.getArrayClassType(shorts.getClass()));
      char[] chars = new char[0];
      assertEquals("char[]", Classes.getArrayClassType(chars.getClass()));
      int[] ints = new int[0];
      assertEquals("int[]", Classes.getArrayClassType(ints.getClass()));
      long[] longs = new long[0];
      assertEquals("long[]", Classes.getArrayClassType(longs.getClass()));
      float[] floats = new float[0];
      assertEquals("float[]", Classes.getArrayClassType(floats.getClass()));
      double[] doubles = new double[0];
      assertEquals("double[]", Classes.getArrayClassType(doubles.getClass()));
      double[][] multidouble = new double[0][0];
      assertEquals("double[][]", Classes.getArrayClassType(multidouble.getClass()));
      String[] strings = new String[0];
      assertEquals("java.lang.String[]", Classes.getArrayClassType(strings.getClass()));
      String[][] multistr = new String[0][0];
      assertEquals("java.lang.String[][]", Classes.getArrayClassType(multistr.getClass()));

   }



   @Test
   public void getGetDeclaredField()
   {
      assertNotNull(Classes.getField(Rectangle2D.Double.class, "x"));
   }

   @Test
   public void getGetSecondLevelField()
   {
      assertNotNull(Classes.getField(Rectangle2D.Double.class, "OUT_LEFT"));
   }

   @Test
   public void getGetNonExistentField()
   {
      assertNull(Classes.getField(Rectangle2D.Double.class, "IN_LEFT"));
   }

   @Test
   public void testGetDeclaredMethod()
   {
      assertNotNull(Classes.getMethod(LinkedHashMap.class, "get", Object.class));
   }

   @Test
   public void testGetSecondLevelMethod()
   {
      assertNotNull(Classes.getMethod(LinkedHashMap.class, "remove", Object.class));
   }

   @Test
   public void testGetThirdLevelMethod()
   {
      assertNotNull(Classes.getMethod(LinkedHashMap.class, "equals", Object.class));
   }

   @Test
   public void testGetFourthLevelMethodNoArgs()
   {
      assertNotNull(Classes.getMethod(LinkedHashMap.class, "finalize"));
   }

   @Test
   public void testGetFourthLevelMethodTwoArgs()
   {
      assertNotNull(Classes.getMethod(LinkedHashMap.class, "wait", long.class, int.class));
   }

   @Test
   public void testGetNonExistentMethod()
   {
      // wait has long and int args not Long and Integer
      assertNull(Classes.getMethod(LinkedHashMap.class, "wait", Long.class, Integer.class));
   }


   @Test
   public void testGetAndCreateConstructor()
   {
      Constructor<String> cons = Classes.getConstructor(String.class, String.class);
      assertNotNull(cons);
      String str = Classes.newInstance(cons, "Hello");
      Assert.assertEquals("Hello", str);
   }

   @Test
   public void testGetNoneExistentConstructor()
   {
      assertNull(Classes.getConstructor(String.class, Integer.class));
   }

   @Test
   public void testNewInstance()
   {
      StringBuffer buf = Classes.newInstance(StringBuffer.class);
      assertNotNull(buf);
   }

   @Test
   public void testNewInstanceNoZeroArgConstructor()
   {
      Integer i = Classes.newInstance(Integer.class);
      assertNull(i);
   }

   @Test
   public void testLoadClass()
   {
      Class<?> str = Classes.loadNamedClass(getClass().getClassLoader(), "java.lang.String");
      assertEquals(String.class, str);
   }

   @Test
   public void testLoadClassChildClassLoader()
   {
      Class<?> cls = Classes.loadNamedClass(getClass().getClassLoader(), "xpertss.lang.Classes");
      assertEquals(Classes.class, cls);
   }

   @Test
   public void testLoadClassDoesNotExist()
   {
      Class<?> cls = Classes.loadNamedClass(getClass().getClassLoader(), "java.lang.DoesNotExist");
      assertNull(cls);
   }


   @Test
   public void testIsSubClassOfTrue()
   {
      assertTrue(Classes.isSubclassOf(LinkedHashMap.class, AbstractMap.class));
   }

   @Test
   public void testIsSubClassOfFalse()
   {
      assertFalse(Classes.isSubclassOf(ArrayList.class, AbstractMap.class));
   }

   @Test
   public void testIsSubClassOfOutOfOrder()
   {
      assertFalse(Classes.isSubclassOf(AbstractMap.class, LinkedHashMap.class));
   }

   @Test (expected = NullPointerException.class)
   public void testIsSubClassOfNullSuper()
   {
      assertFalse(Classes.isSubclassOf(LinkedHashMap.class, null));
   }

   @Test (expected = NullPointerException.class)
   public void testIsSubClassOfNullSub()
   {
      Classes.isSubclassOf(null, AbstractMap.class);
   }


}
