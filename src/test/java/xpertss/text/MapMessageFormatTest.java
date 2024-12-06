package xpertss.text;

import org.junit.jupiter.api.Test;
import xpertss.time.Chronology;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MapMessageFormatTest {

   @Test
   public void testSimple()
   {
      MapMessageFormat objectUnderTest = new MapMessageFormat("Property {field} cannot be set to {value}", Locale.US);
      Map<String,String> propertise = new LinkedHashMap<>();
      propertise.put("field", "type");
      propertise.put("value", "ATF");
      assertEquals("Property type cannot be set to ATF", objectUnderTest.format(propertise));
   }

   @Test
   public void testDoubleQuote()
   {
      MapMessageFormat objectUnderTest = new MapMessageFormat("Property ''{field}'' cannot be set to {value}", Locale.US);
      Map<String,String> propertise = new LinkedHashMap<>();
      propertise.put("field", "type");
      propertise.put("value", "ATF");
      assertEquals("Property 'type' cannot be set to ATF", objectUnderTest.format(propertise));
   }


   @Test
   public void testSimpleDateTime()
   {
      Chronology chrono = Chronology.create(TimeZone.getDefault());
      MapMessageFormat objectUnderTest = new MapMessageFormat("At {when,time} on {when,date}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("when", chrono.newDate(2016,5,4,12,15,30));
      assertEquals("At 12:15:30 PM on May 4, 2016", objectUnderTest.format(propertise));
   }

   @Test
   public void testShortDateTime()
   {
      Chronology chrono = Chronology.create(TimeZone.getDefault());
      MapMessageFormat objectUnderTest = new MapMessageFormat("At {when,time,short} on {when,date,short}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("when", chrono.newDate(2016,5,4,12,15,30));
      assertEquals("At 12:15 PM on 5/4/16", objectUnderTest.format(propertise));
   }

   @Test
   public void testMediumDateTime()
   {
      Chronology chrono = Chronology.create(TimeZone.getDefault());
      MapMessageFormat objectUnderTest = new MapMessageFormat("At {when,time,medium} on {when,date,medium}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("when", chrono.newDate(2016,5,4,12,15,30));
      assertEquals("At 12:15:30 PM on May 4, 2016", objectUnderTest.format(propertise));
   }

   @Test
   public void testLongDateTime()
   {
      Chronology chrono = Chronology.create(TimeZone.getDefault());
      MapMessageFormat objectUnderTest = new MapMessageFormat("At {when,time,long} on {when,date,long}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("when", chrono.newDate(2016,5,4,12,15,30));
      assertEquals("At 12:15:30 PM EDT on May 4, 2016", objectUnderTest.format(propertise));
   }

   @Test
   public void testFullDateTime()
   {
      Chronology chrono = Chronology.create(TimeZone.getDefault());
      MapMessageFormat objectUnderTest = new MapMessageFormat("At {when,time,full} on {when,date,full}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("when", chrono.newDate(2016,5,4,12,15,30));
      assertEquals("At 12:15:30 PM EDT on Wednesday, May 4, 2016", objectUnderTest.format(propertise));
   }


   @Test
   public void testCustomDateTime()
   {
      Chronology chrono = Chronology.create(TimeZone.getDefault());
      MapMessageFormat objectUnderTest = new MapMessageFormat("At {when,time,HH:MM:ss.SSS} on {when,date,YYYY-MM-dd}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("when", chrono.newDate(2016,5,4,12,15,30));
      assertEquals("At 12:05:30.000 on 2016-05-04", objectUnderTest.format(propertise));
   }



   @Test
   public void testIntegerNumber()
   {
      MapMessageFormat objectUnderTest = new MapMessageFormat("Planet number {planet,number,integer}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("planet", 22);
      assertEquals("Planet number 22", objectUnderTest.format(propertise));
   }

   @Test
   public void testPercentageNumber()
   {
      MapMessageFormat objectUnderTest = new MapMessageFormat("Completion: {percent,number,percent}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("percent", .22D);
      assertEquals("Completion: 22%", objectUnderTest.format(propertise));
   }

   @Test
   public void testCurrencyNumber()
   {
      MapMessageFormat objectUnderTest = new MapMessageFormat("Cost: {amount,number,currency}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("amount", 5.99D);
      assertEquals("Cost: $5.99", objectUnderTest.format(propertise));
   }

   @Test
   public void testCustomNumber()
   {
      MapMessageFormat objectUnderTest = new MapMessageFormat("Cost: {amount,number,#.##}", Locale.US);
      Map<String,Object> propertise = new LinkedHashMap<>();
      propertise.put("amount", 105.12345D);
      assertEquals("Cost: 105.12", objectUnderTest.format(propertise));
   }

   @Test
   public void testEmbeddedMap()
   {
      MapMessageFormat objectUnderTest = new MapMessageFormat("The disk # {num} contains {name,message,{num_files,number} {label}}.");

      Map propertise = new LinkedHashMap();
      propertise.put("num", new Long(2));
      Map embedded = new LinkedHashMap();
      embedded.put("num_files", new Long(123));
      embedded.put("label", "files");
      propertise.put("name", embedded);

      assertEquals("The disk # 2 contains 123 files.", objectUnderTest.format(propertise));
   }


}