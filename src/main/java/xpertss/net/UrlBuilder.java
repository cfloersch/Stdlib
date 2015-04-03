package xpertss.net;

import xpertss.lang.Objects;
import xpertss.lang.Strings;

import java.net.URI;
import java.net.URL;


/**
 * Utility class to build a URL from components.
 * <p>
 * This class makes only very basic attempts to validate input components. It
 * is the caller's responsibility to ensure the various components are properly
 * formed and encoded.
 */
@SuppressWarnings({ "UnusedDeclaration"})
public class UrlBuilder {

   private String userinfo;
   private String scheme;
   private String host;
   private int port;

   private String path;
   private String hash;
   private String query;


   private UrlBuilder(String scheme)
   {
      if(scheme.length() < 1) throw new IllegalArgumentException("empty scheme");
      for(int i = 0; i < scheme.length(); i++) {
         char c = scheme.charAt(i);
         if(Character.isLetterOrDigit(c) || c == '+' || c == '-' || c == '.') continue;
         throw new IllegalArgumentException("invalid characters found in scheme");
      }
      this.scheme = scheme.toLowerCase();
   }


   /**
    * Returns the scheme of this UrlBuilder.
    */
   public String getScheme()
   {
      return scheme;
   }




   /**
    * Returns the URL's userinfo component.
    */
   public String getUserInfo()
   {
      return userinfo;
   }

   /**
    * Set the <tt>userinfo</tt> part of the url as an opaque string.
    * <p>
    * A {@code null} value clears this field
    */
   public UrlBuilder setUserInfo(String userinfo)
   {
      this.userinfo = Strings.nullIfEmpty(userinfo);
      return this;
   }

   /**
    * Set the <tt>userinfo</tt> part of the url given a user name and password.
    * <p>
    * The supplied username must be a non-empty string but the password may be
    * null or empty.
    *
    * @throws IllegalArgumentException If the specified user name is empty
    */
   public UrlBuilder setUserInfo(String user,String pass)
   {
      if(Strings.isEmpty(user)) throw new IllegalArgumentException("user can not be empty");
      return (pass == null) ? setUserInfo(user) : setUserInfo(user + ":" + pass);
   }




   /**
    * Returns the URL's host component.
    */
   public String getHost()
   {
      return host;
   }

   /**
    * Set the host component of this URL.
    * <p>
    * A <tt>null</tt> value clears this field
    */
   public UrlBuilder setHost(String host)
   {
      this.host = Strings.nullIfEmpty(host);
      return this;
   }



   /**
    * Returns the URL's port component.
    */
   public int  getPort()
   {
      return port;
   }

   /**
    * Set this URL's port.
    * <p>
    * Setting this to 0 clears the port value.
    *
    * @throws IllegalArgumentException If the specified port number is not within the
    *          range 0 - 65535
    */
   public UrlBuilder setPort(int port)
   {
      if(port < 0 || port > 65535)
         throw new IllegalArgumentException("invalid port value: " + port);
      this.port = port;
      return this;
   }




   /**
    * Returns the URL's path component.
    */
   public String getPath()
   {
      return path;
   }

   /**
    * Set the path component of this URL.
    * <p>
    * A <tt>null</tt> value clears this field
    */
   public UrlBuilder setPath(String path)
   {
      this.path = Strings.nullIfEmpty(path);
      return this;
   }




   /**
    * Returns the URL's fragment component.
    */
   public String getFragment()
   {
      return hash;
   }

   /**
    * Set the fragment component of this URL. The fragment is sometimes known as an
    * anchor.
    * <p>
    * A <tt>null</tt> value clears this field
    */
   public UrlBuilder setFragment(String hash)
   {
      this.hash = Strings.nullIfEmpty(hash);
      return this;
   }




   /**
    * Returns the URL's query component.
    */
   public String getQuery()
   {
      return query;
   }

   /**
    * Set the query component of this URL.
    * <p>
    * A <tt>null</tt> value clears this field
    */
   public UrlBuilder setQuery(String query)
   {
      this.query = Strings.nullIfEmpty(query);
      return this;
   }





   /**
    * Builds a String representation of the url given the builder's current state.
    */
   public String build()
   {
      StringBuilder buf = new StringBuilder(scheme).append(":");
      String authority = createAuthority();
      if(authority != null) {
         buf.append(authority);
         if(path == null || !path.startsWith("/")) buf.append("/");
      }
      if(path != null) buf.append(path);
      if(query != null) {
         buf.append("?").append(query);
      }
      if(hash != null) buf.append("#").append(hash);
      return buf.toString();
   }

   private String createAuthority()
   {
      if(!Strings.isEmpty(host)) {
         StringBuilder buf = new StringBuilder("//");
         if(userinfo != null) buf.append(userinfo).append("@");
         buf.append(host);
         if(port > 0) buf.append(":").append(port);
         return buf.toString();
      }
      return null;
   }


   /**
    * Clone this UrlBuilder
    */
   public UrlBuilder clone()
   {
      UrlBuilder builder = new UrlBuilder(scheme);
      builder.setUserInfo(userinfo).setHost(host).setPort(port);
      builder.setPath(path).setQuery(query).setFragment(hash);
      return builder;
   }


   /**
    * Create a UrlBuilder that will create URLs of the given scheme.
    *
    * @param scheme The UrlBuilders scheme
    * @throws NullPointerException If the specified scheme is null
    * @throws IllegalArgumentException If the given scheme contains invalid
    *          characters or no characters at all
    * @return A new UrlBuilder that will build urls for the given scheme
    */
   public static UrlBuilder create(String scheme)
   {
      return new UrlBuilder(Objects.notNull(scheme, "scheme"));
   }

   /**
    * Create and initialize a UrlBuilder instance using the specified URL
    */
   public static UrlBuilder create(URL url)
   {
      UrlBuilder builder = new UrlBuilder(url.getProtocol());
      builder.setUserInfo(url.getUserInfo()).setHost(url.getHost()).setPort(url.getPort());
      builder.setPath(url.getPath()).setQuery(url.getQuery()).setFragment(url.getRef());
      return builder;
   }

   /**
    * Create and initialize a UrlBuilder instance using the specified URI
    */
   public static UrlBuilder create(URI url)
   {
      UrlBuilder builder = new UrlBuilder(url.getScheme());
      builder.setUserInfo(url.getUserInfo()).setHost(url.getHost()).setPort(url.getPort());
      builder.setPath(url.getPath()).setQuery(url.getQuery()).setFragment(url.getFragment());
      return builder;
   }


}
