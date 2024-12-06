package xpertss.text;

import org.junit.jupiter.api.Test;
import xpertss.io.BigEndian;
import xpertss.lang.Bytes;

import java.util.*;


/**
 */
public class TestStringPrepRanges {

   private static final int[][] bOne = {
      {0x00AD, 0x00AD},
      {0x034F, 0x034F},
      {0x1806, 0x1806},
      {0x180B, 0x180D},
      {0x200B, 0x200D},
      {0x2060, 0x2060},
      {0xFE00, 0xFE0F},
      {0xFEFF, 0xFEFF}
   };




   // b2 and b3 are huge mapping tables not used by SASLPrep

   private static final int[][] cOneOne = {
      { 0x0020, 0x0020 },
   };

   private static final int[][] cOneTwo = {
      {0x00A0, 0x00A0},
      {0x1680, 0x1680},
      {0x2000, 0x200B},
      {0x202F, 0x202F},
      {0x205F, 0x205F},
      {0x3000, 0x3000}
   };


   private static final int[][] cTwoOne = {
      {0x0000, 0x001F},
      {0x007F, 0x007F}
   };

   private static final int[][] cTwoTwo = {
      {0x0080,  0x009F},
      {0x06DD,  0x06DD},
      {0x070F,  0x070F},
      {0x180E,  0x180E},
      {0x200C,  0x200D},      // Precedes item in C.8
      {0x2028,  0x2029},      // Precedes item in C.8
      {0x2060,  0x2063},
      {0x206A,  0x206F},      // Also found in C.8
      {0xFEFF,  0xFEFF},
      {0xFFF9,  0xFFFC},      // FFF9 found in C.6  Leads item in C.6
      {0x1D173, 0x1D17A}
   };

   private static final int[][] cThree = {
      {0xE000,   0xF8FF},
      {0xF0000,  0xFFFFD},    // Follows item in C.4, Precedes item in C.4
      {0x100000, 0x10FFFD}    // Follows item in C.4, Precedes item in C.4
   };

   private static final int[][] cFour = {
      {0xFDD0,   0xFDEF},
      {0xFFFE,   0xFFFF},     // Follows item in C.6
      {0x1FFFE,  0x1FFFF},
      {0x2FFFE,  0x2FFFF},
      {0x3FFFE,  0x3FFFF},
      {0x4FFFE,  0x4FFFF},
      {0x5FFFE,  0x5FFFF},
      {0x6FFFE,  0x6FFFF},
      {0x7FFFE,  0x7FFFF},
      {0x8FFFE,  0x8FFFF},
      {0x9FFFE,  0x9FFFF},
      {0xAFFFE,  0xAFFFF},
      {0xBFFFE,  0xBFFFF},
      {0xCFFFE,  0xCFFFF},
      {0xDFFFE,  0xDFFFF},
      {0xEFFFE,  0xEFFFF},                   // Precedes item in C.3
      {0xFFFFE,  0xFFFFF},                   // Follows item in C.3, Precedes item in C.3
      {0x10FFFE, 0x10FFFF}                   // Follows item in C.3
   };


   private static final int[][] cFive = {
      { 0xD800, 0xDFFF }                     // Precedes item in C.9
   };

   private static final int[][] cSix = {
      { 0xFFF9, 0xFFF9 },                    // Also found in C.2.2
      { 0xFFFA, 0xFFFD }                     // Trails item in C.2.2, Precedes item in C.4
   };

   private static final int[][] cSeven = {
      { 0x2FF0, 0x2FFB }
   };


   private static final int[][] cEight = {
      { 0x0340, 0x0341 },
      { 0x200E, 0x200F },                    // Follows item in C.2.2
      { 0x202A, 0x202E }                     // Follows item in C.2.2
   };

   private static final int[][] cNine = {
      { 0xE0001, 0xE0001 },                  // Follows item in C.5
      { 0xE0020, 0xE007F }
   };



   private static final int[][] nodePrep = {
      { 0x0022, 0x0022 },
      { 0x0026, 0x0027 },
      { 0x002F, 0x002F },
      { 0x003A, 0x003A },
      { 0x003C, 0x003C },
      { 0x003E, 0x003E },
      { 0x0040, 0x0040 }
   };

   private static final int[][] iscsiPrep = {
      { 0x3002, 0x3002 },
      { 0x0000, 0x002C },
      { 0x002F, 0x002F },
      { 0x003B, 0x0040 },
      { 0x005B, 0x0060 },
      { 0x007B, 0x007F }
   };


   @Test
   public void findGaps()
   {
      List<int[]> ranges = new ArrayList<>();
      addAll(ranges, cOneTwo, "C.1.1");
      addAll(ranges, cOneTwo, "C.1.2");
      addAll(ranges, cTwoOne, "C.2.1");
      addAll(ranges, cTwoTwo, "C.2.2");
      addAll(ranges, cThree, "C.3");
      addAll(ranges, cFour, "C.4");
      addAll(ranges, cFive, "C.5");
      addAll(ranges, cSix, "C.6");
      addAll(ranges, cSeven, "C.7");
      addAll(ranges, cEight, "C.8");
      addAll(ranges, cNine, "C.9");
      addAll(ranges, iscsiPrep, "iscsiPrep");

      Collections.sort(ranges, new Comparator<int[]>() {
         @Override
         public int compare(int[] o1, int[] o2)
         {
            if(o1[1] < o2[1]) {
               return -1;
            } else if(o1[0] > o2[0]) {
               return 1;
            }
            return 0;
         }
      });

      ranges = adjust(ranges);

      for(int[] range : ranges) {
         System.out.println(toString(range));
      }

   }

   private void addAll(List<int[]> ranges, int[][] table, String name)
   {
      for(int i = 0; i < table.length; i++) {
         ranges.add(table[i]);
      }
   }

   private List<int[]> adjust(List<int[]> ranges)
   {
      List<int[]> result = new ArrayList<>();
      int[] last = ranges.get(0);
      for(int i = 1; i < ranges.size(); i++) {
         int[] next = ranges.get(i);
         if(last[1] + 1 < next[0]) {
            result.add(last);
            last = next;
         } else {
            last = new int[] { last[0], next[1] };
         }
      }
      result.add(last);
      return result;
   }




   private String toString(int[] range)
   {
      StringBuilder buf = new StringBuilder();
      buf.append("{");
      buf.append(toHex(range[0]));
      buf.append(", ");
      buf.append(toHex(range[1]));
      buf.append("},");
      return buf.toString();
   }

   private static String toHex(int codePoint)
   {
      String hex = Bytes.toHexString(BigEndian.toBytes(codePoint));
      StringBuilder buf = new StringBuilder(hex);
      for(int i = 0; i < 6 - buf.length(); i++) buf.insert(0, "0");
      return buf.insert(0, "0x").toString();
   }


}
