package xpertss.util;

import xpertss.lang.Strings;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Static utility methods for operating with {@link Locale} objects.
 */
public final class Locales {

   private Locales() { }

   /**
    * Get a set of locale objects available on this JVM for the given ISO 3166 2-letter country
    * code.
    *
    * @param country The ISO 3166 2-letter country code
    * @return A set of all vm supported locales for the given country
    */
   public static Set<Locale> byCountry(String country)
   {
      if(Strings.isEmpty(country)) return Collections.emptySet();
      Set<Locale> locales = new LinkedHashSet<>();
      for(Locale locale : Locale.getAvailableLocales()) {
         if(locale.getVariant().isEmpty()) {
            if(country.equalsIgnoreCase(locale.getCountry())) locales.add(locale);
         }
      }
      return Collections.unmodifiableSet(locales);

   }

   /**
    * Get a set of locale objects available on this JVM for the given ISO 639 2-letter language
    * code.
    *
    * @param language The ISO 639 2-letter language code
    * @return A set of all vm supported locales for the given language
    */
   public static Set<Locale> byLanguage(String language)
   {
      if(Strings.isEmpty(language)) return Collections.emptySet();
      Set<Locale> locales = new LinkedHashSet<>();
      for(Locale locale : Locale.getAvailableLocales()) {
         if(locale.getVariant().isEmpty() && locale.getCountry().length() > 0) {
            if(language.equalsIgnoreCase(locale.getLanguage())) locales.add(locale);
         }
      }
      return Collections.unmodifiableSet(locales);
   }



   /**
    * Split apart the string representation of the locale and construct the
    * corresponding locale.
    *
    * @param str A locale encoded as a String
    * @return the desired Locale
    * @throws IllegalArgumentException If the locale string is not parseable
    */
   public static Locale parse(String str)
   {
      Locale result = parse(str, null);
      if (result == null) throw new IllegalArgumentException("invalid locale: " + str);
      return result;
   }

   /**
    * Split apart the string representation of the locale and construct the
    * corresponding locale.
    *
    * @param str The string to parse
    * @param def a default locale to be used in case the passed in value is empty
    *             or null
    * @return A Locale version of the parsed string or the specified default
    */
   public static Locale parse(String str, Locale def)
   {
      if (!Strings.isEmpty(str)) {
         String[] parts = str.split("[-_]");
         switch (parts.length) {
            case 1:
               return new Locale(parts[0].toLowerCase());
            case 2:
               return new Locale(parts[0].toLowerCase(), parts[1].toUpperCase());
            case 3:
               return new Locale(parts[0].toLowerCase(), parts[1].toUpperCase(), parts[2]);
         }
      }
      return def;
   }



   /**
    * Converts a locale object into a properly formatted language string usable by
    * various mime based systems such as the Content-Language header of HTTP.
    * <p>
    * Note this strips the variant and will include only language and country
    * if the country is defined.
    *
    * @param locale The locale to convert
    * @return A MIME compliant language identifier
    */
   public static String toMimeString(Locale locale)
   {
      if(Strings.isEmpty(locale.getCountry())) return locale.getLanguage().toLowerCase();
      return locale.getLanguage().toLowerCase() + "-" + locale.getCountry().toUpperCase();
   }

}
