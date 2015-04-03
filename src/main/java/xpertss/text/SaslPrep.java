package xpertss.text;

/**
 * Implements the StringPrep profile defined in RFC4013
 */
class SaslPrep extends StringPrep {


   private static final int[] SPACE = { 0x0020 };

   // b2 and b3 are huge mapping tables not used by SASLPrep

   private static final int[][] cOneTwo = {
      {0x00A0, 0x00A0},
      {0x1680, 0x1680},
      {0x2000, 0x200B},
      {0x202F, 0x202F},
      {0x205F, 0x205F},
      {0x3000, 0x3000}
   };


   // Combined C.1.2, C.2.1, C.2.2, C.3, C.4, C.5, C.6, C.7, C.8, & C.9
   private static final int[][] prohibited = {
      {0x00000000, 0x0000001F},
      {0x0000007F, 0x000000A0},
      {0x00000340, 0x00000341},
      {0x000006DD, 0x000006DD},
      {0x0000070F, 0x0000070F},
      {0x00001680, 0x00001680},
      {0x0000180E, 0x0000180E},
      {0x00002000, 0x0000200F},
      {0x00002028, 0x0000202F},
      {0x0000205F, 0x00002063},
      {0x0000206A, 0x0000206F},
      {0x00002FF0, 0x00002FFB},
      {0x00003000, 0x00003000},
      {0x0000D800, 0x0000F8FF},
      {0x0000FDD0, 0x0000FDEF},
      {0x0000FEFF, 0x0000FEFF},
      {0x0000FFF9, 0x0000FFFF},
      {0x0001D173, 0x0001D17A},
      {0x0001FFFE, 0x0001FFFF},
      {0x0002FFFE, 0x0002FFFF},
      {0x0003FFFE, 0x0003FFFF},
      {0x0004FFFE, 0x0004FFFF},
      {0x0005FFFE, 0x0005FFFF},
      {0x0006FFFE, 0x0006FFFF},
      {0x0007FFFE, 0x0007FFFF},
      {0x0008FFFE, 0x0008FFFF},
      {0x0009FFFE, 0x0009FFFF},
      {0x000AFFFE, 0x000AFFFF},
      {0x000BFFFE, 0x000BFFFF},
      {0x000CFFFE, 0x000CFFFF},
      {0x000DFFFE, 0x000DFFFF},
      {0x000E0001, 0x000E0001},
      {0x000E0020, 0x000E007F},
      {0x000EFFFE, 0x0010FFFF}
   };






   SaslPrep()
   {
      super(true, true);
   }



   @Override
   protected int[] mapCodePoint(int codePoint)
   {
      // map to space [StringPrep, C.1.2]
      if(contains(cOneTwo, codePoint)) return SPACE;
      // map to nothing [StringPrep, B.1]
      if(contains(bOne, codePoint)) return EMPTY;
      // No mapping
      return null;
   }

   @Override
   protected boolean prohibit(int codePoint)
   {
      return contains(prohibited, codePoint);
   }




}
