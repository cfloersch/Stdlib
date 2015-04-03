/**
 * Created By: cfloersch
 * Date: 8/15/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.text;

class ISCSIPrep extends StringPrep {

   // Combined C.1.1, C.1.2, C.2.1, C.2.2, C.3, C.4, C.5, C.6, C.7, C.8, & C.9
   // Also includes specials defined in iSCSIPrep itself
   private static final int[][] prohibited = {
      {0x00000000, 0x0000002C},
      {0x0000002F, 0x0000002F},
      {0x0000003B, 0x00000040},
      {0x0000005B, 0x00000060},
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
      {0x00003002, 0x00003002},
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

   ISCSIPrep()
   {
      super(true, true);
   }

   @Override
   protected int[] mapCodePoint(int codePoint)
   {
      // map to nothing [StringPrep, B.1]
      if(contains(bOne, codePoint)) return EMPTY;

      // case mapping [StringPrep, B.2]
      return map(bTwo, codePoint);
   }

   @Override
   protected boolean prohibit(int codePoint)
   {
      return contains(prohibited, codePoint);
   }

}
