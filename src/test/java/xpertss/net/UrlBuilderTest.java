package xpertss.net;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * http://www.ietf.org/rfc/rfc3986.txt
 *
 * URL Structure and characters
 *
 * User: cfloersch
 * Date: 12/11/12
 * Time: 12:20 PM
 */
public class UrlBuilderTest {

   @Test
   public void testBasicFtpUrl()
   {
      UrlBuilder builder = UrlBuilder.create("ftp");
      builder.setHost("ftp.manheim.com").setPath("/downloads/file.gz");
      assertEquals("ftp://ftp.manheim.com/downloads/file.gz", builder.build());
   }

   @Test
   public void testUserInfoFtpUrl()
   {
      UrlBuilder builder = UrlBuilder.create("ftp");
      builder.setUserInfo("cfloersch", "password").setHost("ftp.manheim.com").setPath("/downloads/file.gz");
      assertEquals("ftp://cfloersch:password@ftp.manheim.com/downloads/file.gz", builder.build());
   }

   @Test
   public void testBasicHttpUrl()
   {
      UrlBuilder builder = UrlBuilder.create("https");
      builder.setHost("www.manheim.com").setPath("/simulcast/index.html");
      assertEquals("https://www.manheim.com/simulcast/index.html", builder.build());
   }

   @Test
   public void testUserHomeHttpUrl()
   {
      UrlBuilder builder = UrlBuilder.create("http");
      builder.setHost("www.ksu.edu").setPath("~cfloersch/cgi-bin/my.cgi");
      assertEquals("http://www.ksu.edu/~cfloersch/cgi-bin/my.cgi", builder.build());
   }

   @Test
   public void testNonStandardHttpUrl()
   {
      UrlBuilder builder = UrlBuilder.create("https");
      builder.setHost("www.manheim.com").setPort(8080).setPath("/simulcast/index.html");
      assertEquals("https://www.manheim.com:8080/simulcast/index.html", builder.build());
   }

   @Test
   public void testAnchoredUrl()
   {
      UrlBuilder builder = UrlBuilder.create("https");
      builder.setHost("www.manheim.com").setPath("/simulcast/index.html").setFragment("main");
      assertEquals("https://www.manheim.com/simulcast/index.html#main", builder.build());
   }

   @Test
   public void testSimpleQueryUrl()
   {
      UrlBuilder builder = UrlBuilder.create("https");
      builder.setHost("www.manheim.com").setPath("/simulcast/index.html").setQuery("user=cfloersch");
      assertEquals("https://www.manheim.com/simulcast/index.html?user=cfloersch", builder.build());
   }

   @Test
   public void testMultiQueryUrl()
   {
      UrlBuilder builder = UrlBuilder.create("https");
      builder.setHost("www.manheim.com").setPath("/simulcast/index.html").setQuery("user=cfloersch&pass=password");
      assertEquals("https://www.manheim.com/simulcast/index.html?user=cfloersch&pass=password", builder.build());
   }

   @Test
   public void testQueryPlusAnchoredUrl()
   {
      UrlBuilder builder = UrlBuilder.create("https");
      builder.setHost("www.manheim.com").setPath("/simulcast/index.html").setFragment("main").setQuery("user=cfloersch");
      assertEquals("https://www.manheim.com/simulcast/index.html?user=cfloersch#main", builder.build());
   }



   @Test
   public void testMailtoUrl()
   {
      UrlBuilder builder = UrlBuilder.create("mailto");
      builder.setPath("cfloersch@manheim.com");
      assertEquals("mailto:cfloersch@manheim.com", builder.build());
   }


   // TODO All of these file url's should have 3 slashes
   // http://en.wikipedia.org/wiki/File_URI_scheme
   // Both URL and URI support the exclusion of the leading two // chars

   @Test
   public void testFileUrl()
   {
      UrlBuilder builder = UrlBuilder.create("file");
      builder.setPath("/tmp/file.gz");
      assertEquals("file:/tmp/file.gz", builder.build());
   }

   @Test
   public void testWindowsFileUrl()
   {
      UrlBuilder builder = UrlBuilder.create("file");
      builder.setPath("/C:/tmp/file.gz");
      assertEquals("file:/C:/tmp/file.gz", builder.build());
   }

   @Test
   public void testWindowsAlternateFileUrl()
   {
      UrlBuilder builder = UrlBuilder.create("file");
      builder.setPath("/C|/tmp/file.gz");
      assertEquals("file:/C|/tmp/file.gz", builder.build());
   }




   @Test
   public void testJarUrl()
   {
      UrlBuilder builder = UrlBuilder.create("jar");
      builder.setPath("file:/C:/cfloersch/4.10/junit-4.10.jar!/test.txt");
      assertEquals("jar:file:/C:/cfloersch/4.10/junit-4.10.jar!/test.txt", builder.build());
   }

}
