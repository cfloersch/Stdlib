package xpertss.util;

/**
 * Simple Enumeration of US states, US Territories, and Canadian Provinces with helper methods.
 *
 * User: cfloersch
 * Date: 10/16/13
 * Time: 11:57 AM
 */
public enum StateProvince {

   // Question why do we have Ontario twice?? I am sure there is some sort of legacy
   // reason but maybe its been resolved??

   NF("Newfoundland", 1), NS("Nova Scotia", 1), PE("Prince Edward Island", 1),
   NB("New Brunswick", 1), QC("Quebec", 1), ON("Ontario", 1), MB("Manitoba", 1),
   SK("Sasketchewan", 1), AB("Alberta", 1), BC("British Columbia", 1),
   ONT("Ontario", 1),


   AK("Alaska", 4),             AL("Alabama", 4 | 8),              AR("Arkansas", 4 | 8),
   AZ("Arizona", 4 | 8),        CA("California", 4 | 8),           CO("Colorado", 4 | 8),
   CT("Connecticut", 4 | 8),    DC("District of Columbia", 2 | 8), DE("Delaware", 4 | 8),
   FL("Florida", 4 | 8),        GA("Georgia", 4 | 8),              HI("Hawaii", 4),
   IA("Iowa", 4 | 8),           ID("Idaho", 4 | 8),                IL("Illinois", 4 | 8),
   IN("Indiana", 4 | 8),        KS("Kansas", 4 | 8),               KY("Kentucky", 4 | 8),
   LA("Louisiana", 4 | 8),      MA("Massachusetts", 4 | 8),        MD("Maryland", 4 | 8),
   ME("Maine", 4 | 8),          MI("Michigan", 4 | 8),             MN("Minnesota", 4 | 8),
   MO("Missouri", 4 | 8),       MS("Mississippi", 4 | 8),          MT("Montana", 4 | 8),
   NC("North Carolina", 4 | 8), ND("North Dakota", 4 | 8),         NE("Nebraska", 4 | 8),
   NV("Nevada", 4 | 8),         NH("New Hampshire", 4 | 8),        NJ("New Jersey", 4 | 8),
   NM("New Mexico", 4 | 8),     NY("New York", 4 | 8),             OH("Ohio", 4 | 8),
   OK("Oklahoma", 4 | 8),       OR("Oregon", 4 | 8),               PA("Pennsylvania", 4 | 8),
   PR("Puerto Rico", 2),        RI("Rhode Island", 4 | 8),         SC("South Carolina", 4 | 8),
   SD("South Dakota", 4 | 8),   TN("Tennessee", 4 | 8),            TX("Texas", 4 | 8),
   UT("Utah", 4 | 8),           VA("Virginia", 4 | 8),             VT("Vermont", 4 | 8),
   WA("Washington", 4 | 8),     WI("Wisconsin", 4 | 8),            WV("West Virginia", 4 | 8),
   WY("Wyoming", 4 | 8);




   private static final int PROVINCE    = 1;
   private static final int TERRITORY   = 2;
   private static final int STATE       = 4;
   private static final int CONTINENTAL = 8;


   private String name;
   private int type;

   StateProvince(String name, int type)
   {
      this.name = name;
      this.type = type;
   }

   public String getDisplayName()
   {
      return name;
   }


   public boolean isLowerFortyEight()
   {
      return (type & CONTINENTAL) != 0;
   }

   public boolean isProvince()
   {
      return (type & PROVINCE) != 0;
   }

   public boolean isState()
   {
      return (type & STATE) != 0;
   }

   public boolean isTerritory()
   {
      return (type & TERRITORY) != 0;
   }

}
