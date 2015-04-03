package xpertss.lang;

/**
 * Static utility methods for operating on {@link CharSequence} instances..
 */
public final class CharSequences {

   private CharSequences() { }


   /**
    * A character sequence is empty if it is either {@code null} or made up of nothing
    * but spaces.
    */
   public static boolean isEmpty(CharSequence text)
   {
      return (text == null || trim(text).length() == 0);
   }


   /**
    * Gets a CharSequence length or {@code 0} if the CharSequence is {@code null}.
    *
    * @param cseq a CharSequence or {@code null}
    * @return CharSequence length or {@code 0} if the input is {@code null}.
    */
   public static int length(CharSequence cseq)
   {
      return cseq == null ? 0 : cseq.length();
   }


   /**
    * Trims all the leading and trailing whitespace from the specified CharSequence.
    * <p>
    * If the specified char sequence is {@code null} then this will return {@code null}.
    *
    * @param seq The CharSequence to trim
    * @return The trimmed CharSequence or {@code null} if input is {@code null}
    */
   public static CharSequence trim(CharSequence seq)
   {
      if(seq == null) return null;
      int start = -1, end = -1;
      for(int i = 0; i < seq.length(); i++) {
         if(!Character.isWhitespace(seq.charAt(i))) {
            start = i;
            break;
         }
      }
      if(start == -1) return seq.subSequence(0, 0);
      for(int i = seq.length() - 1; i >= 0; i--) {
         if(!Character.isWhitespace(seq.charAt(i))) {
            end = i + 1;
            break;
         }
      }
      return seq.subSequence(start, end);
   }

   /**
    * Returns true if and only if the specified sequence contains the specified sub-sequence
    * of char values. If either the specified sequence or sub-sequence are {@code null} this
    * will return {@code false}.
    *
    * @param seq The base sequence to search within
    * @param sub The sub-sequence to search for
    * @return {@code true} is the sub-sequence is found in the specified sequence,
    *          {@code false} otherwise.
    */
   public static boolean contains(CharSequence seq, CharSequence sub)
   {
      return !(seq == null || sub == null) && seq.toString().contains(sub);
   }






   // equals
   //-----------------------------------------------------------------------

   /**
    * Compares two CharSequences, returning {@code true} if they represent equal sequences
    * of characters.
    * <p>
    * {@code null}s are handled without exceptions. Two {@code null} references are considered
    * to be equal. The comparison is case sensitive.
    * <p>
    * <pre>
    * CharSequences.equal(null, null)   = true
    * CharSequences.equal(null, "abc")  = false
    * CharSequences.equal("abc", null)  = false
    * CharSequences.equal("abc", "abc") = true
    * CharSequences.equal("abc", "ABC") = false
    * </pre>
    *
    * @param cseq1  the first CharSequence, may be {@code null}
    * @param cseq2  the second CharSequence, may be {@code null}
    * @return {@code true} if the CharSequences are equal (case-sensitive), or both {@code null}
    */
   public static boolean equal(CharSequence cseq1, CharSequence cseq2)
   {
      if(cseq1 == cseq2) return true;
      if((cseq1 == null || cseq2 == null)) return false;
      int n = cseq1.length();
      if(n == cseq2.length()) {
         int i = 0;
         while(n-- != 0) {
            if(cseq1.charAt(i) != cseq2.charAt(i)) return false;
            i++;
         }
         return true;
      }
      return false;
   }

   /**
    * Compares two CharSequences, returning {@code true} if they represent equal sequences of
    * characters, ignoring case.
    * <p>
    * {@code null}s are handled without exceptions. Two {@code null} references are considered
    * equal. Comparison is case insensitive.
    * <p>
    * <pre>
    * CharSequences.equalIgnoreCase(null, null)   = true
    * CharSequences.equalIgnoreCase(null, "abc")  = false
    * CharSequences.equalIgnoreCase("abc", null)  = false
    * CharSequences.equalIgnoreCase("abc", "abc") = true
    * CharSequences.equalIgnoreCase("abc", "ABC") = true
    * </pre>
    *
    * @param cseq1  the first CharSequence, may be null
    * @param cseq2  the second CharSequence, may be null
    * @return {@code true} if the CharSequence are equal, case insensitive, or both {@code null}
    */
   public static boolean equalIgnoreCase(CharSequence cseq1, CharSequence cseq2)
   {
      if(cseq1 == cseq2) return true;
      if((cseq1 == null || cseq2 == null)) return false;
      int n = cseq1.length();
      if(n == cseq2.length()) {
         int i = 0;
         while(n-- != 0) {
            char c1 = Character.toUpperCase(cseq1.charAt(i));
            char c2 = Character.toUpperCase(cseq2.charAt(i));
            if(c1 != c2) return false;
            i++;
         }
         return true;
      }
      return false;
   }



   // IndexOf
   //-----------------------------------------------------------------------

   /**
    * Finds the first index of a search character within a CharSequence.
    * <p>
    * A {@code null} or empty ("") CharSequence will return {@code -1}.
    * <p>
    * <pre>
    * CharSequence.indexOf(null, *)         = -1
    * CharSequence.indexOf("", *)           = -1
    * CharSequence.indexOf("aabaabaa", 'a') = 0
    * CharSequence.indexOf("aabaabaa", 'b') = 2
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchChar  the character to find
    * @return the first index of the search character, -1 if no match or {@code null}
    *          input
    */
   public static int indexOf(CharSequence seq, char searchChar)
   {
      if(seq == null) return -1;
      for(int i = 0; i < seq.length(); i++) {
         if(seq.charAt(i) == searchChar) return i;
      }
      return -1;
   }

   /**
    * Finds the first index of a search character within a CharSequence from a given
    * start position.
    * <p>
    * A {@code null} or empty ("") CharSequence will return {@code -1}. A negative start
    * position is treated as zero. A start position greater than the char sequence length
    * returns {@code -1}.
    * <p>
    * <pre>
    * CharSequence.indexOf(null, *, *)          = -1
    * CharSequence.indexOf("", *, *)            = -1
    * CharSequence.indexOf("aabaabaa", 'b', 0)  = 2
    * CharSequence.indexOf("aabaabaa", 'b', 3)  = 5
    * CharSequence.indexOf("aabaabaa", 'b', 9)  = -1
    * CharSequence.indexOf("aabaabaa", 'b', -1) = 2
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchChar  the character to find
    * @param startPos  the start position, negative treated as zero
    * @return the first index of the search character, -1 if no match or {@code null}
    *          input
    */
   public static int indexOf(CharSequence seq, char searchChar, int startPos)
   {
      if(seq == null) return -1;
      for(int i = (startPos < 0) ? 0 : startPos; i < seq.length(); i++) {
         if(seq.charAt(i) == searchChar) return i;
      }
      return -1;
   }

   /**
    * Finds the first index of a search CharSequence within a CharSequence.
    * <p>
    * A {@code null} CharSequence will return {@code -1}.
    * <p>
    * <pre>
    * CharSequence.indexOf(null, *)          = -1
    * CharSequence.indexOf(*, null)          = -1
    * CharSequence.indexOf("", "")           = 0
    * CharSequence.indexOf("", *)            = -1 (except when * = "")
    * CharSequence.indexOf("aabaabaa", "a")  = 0
    * CharSequence.indexOf("aabaabaa", "b")  = 2
    * CharSequence.indexOf("aabaabaa", "ab") = 1
    * CharSequence.indexOf("aabaabaa", "")   = 0
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchSeq  the CharSequence to find, may be null
    * @return the first index of the search CharSequence, -1 if no match or
    *          {@code null} input
    */
   public static int indexOf(CharSequence seq, CharSequence searchSeq)
   {
      if(seq == null || searchSeq == null) return -1;
      if(searchSeq.length() > seq.length()) return -1;
      for(int i = 0; i <= seq.length() - searchSeq.length(); i++) {
         if(equal(seq.subSequence(i, i + searchSeq.length()), searchSeq)) return i;
      }
      return -1;
   }

   /**
    * Finds the first index of a search CharSequence within a CharSequence from a given
    * start position.
    * <p>
    * A {@code null} CharSequence will return {@code -1}. A negative start position is
    * treated as zero. An empty ("") search CharSequence always matches. A start position
    * greater than the char sequence length only matches an empty search CharSequence.
    * <p>
    * <pre>
    * CharSequence.indexOf(null, *, *)          = -1
    * CharSequence.indexOf(*, null, *)          = -1
    * CharSequence.indexOf("", "", 0)           = 0
    * CharSequence.indexOf("", *, 0)            = -1 (except when * = "")
    * CharSequence.indexOf("aabaabaa", "a", 0)  = 0
    * CharSequence.indexOf("aabaabaa", "b", 0)  = 2
    * CharSequence.indexOf("aabaabaa", "ab", 0) = 1
    * CharSequence.indexOf("aabaabaa", "b", 3)  = 5
    * CharSequence.indexOf("aabaabaa", "b", 9)  = -1
    * CharSequence.indexOf("aabaabaa", "b", -1) = 2
    * CharSequence.indexOf("aabaabaa", "", 2)   = 2
    * CharSequence.indexOf("abc", "", 9)        = 3
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchSeq  the CharSequence to find, may be null
    * @param startPos  the start position, negative treated as zero
    * @return the first index of the search CharSequence, -1 if no match or
    *          {@code null} input
    */
   public static int indexOf(CharSequence seq, CharSequence searchSeq, int startPos)
   {
      if(seq == null || searchSeq == null) return -1;
      if(searchSeq.length() > seq.length() - startPos) return -1;
      for(int i = Math.max(0, startPos); i <= seq.length() - searchSeq.length(); i++) {
         if(equal(seq.subSequence(i, i + searchSeq.length()), searchSeq)) return i;
      }
      return -1;
   }


   // TODO Do we want a case insensitive search using character??

   /**
    * Case in-sensitive find of the first index of a search CharSequence within a
    * CharSequence.
    * <p>
    * A {@code null} CharSequence will return {@code -1}. A negative start position
    * is treated as zero. An empty ("") search CharSequence always matches. A start
    * position greater than the char sequence length only matches an empty search
    * CharSequence.
    * <p>
    * <pre>
    * CharSequence.indexOfIgnoreCase(null, *)          = -1
    * CharSequence.indexOfIgnoreCase(*, null)          = -1
    * CharSequence.indexOfIgnoreCase("", "")           = 0
    * CharSequence.indexOfIgnoreCase("aabaabaa", "a")  = 0
    * CharSequence.indexOfIgnoreCase("aabaabaa", "b")  = 2
    * CharSequence.indexOfIgnoreCase("aabaabaa", "ab") = 1
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchSeq  the CharSequence to find, may be null
    * @return the first index of the search CharSequence, -1 if no match or
    *          {@code null} input
    */
   public static int indexOfIgnoreCase(CharSequence seq, CharSequence searchSeq)
   {
      if(seq == null || searchSeq == null) return -1;
      if(searchSeq.length() > seq.length()) return -1;
      for(int i = 0; i <= seq.length() - searchSeq.length(); i++) {
         if(equalIgnoreCase(seq.subSequence(i, i + searchSeq.length()), searchSeq)) return i;
      }
      return -1;
   }

   /**
    * Case in-sensitive find of the first index of a CharSequence within a CharSequence
    * from the specified position.
    * <p>
    * A {@code null} CharSequence will return {@code -1}. A negative start position is
    * treated as zero. An empty ("") search CharSequence always matches. A start
    * position greater than the char sequence length only matches an empty search
    * CharSequence.
    * <p>
    * <pre>
    * CharSequence.indexOfIgnoreCase(null, *, *)          = -1
    * CharSequence.indexOfIgnoreCase(*, null, *)          = -1
    * CharSequence.indexOfIgnoreCase("", "", 0)           = 0
    * CharSequence.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
    * CharSequence.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
    * CharSequence.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
    * CharSequence.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
    * CharSequence.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
    * CharSequence.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
    * CharSequence.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
    * CharSequence.indexOfIgnoreCase("abc", "", 9)        = 3
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchSeq  the CharSequence to find, may be null
    * @param startPos  the start position, negative treated as zero
    * @return the first index of the search CharSequence, -1 if no match or
    *          {@code null} input
    */
   public static int indexOfIgnoreCase(CharSequence seq, CharSequence searchSeq, int startPos)
   {
      if(seq == null || searchSeq == null) return -1;
      if(searchSeq.length() > seq.length() - startPos) return -1;
      for(int i = Math.max(0, startPos); i <= seq.length() - searchSeq.length(); i++) {
         if(equalIgnoreCase(seq.subSequence(i, i + searchSeq.length()), searchSeq)) return i;
      }
      return -1;
   }



   // LastIndexOf
   //-----------------------------------------------------------------------

   /**
    * Finds the last index of a search character within a CharSequence.
    * <p>
    * A {@code null} or empty ("") CharSequence will return {@code -1}.
    * <p>
    * <pre>
    * CharSequences.lastIndexOf(null, *)         = -1
    * CharSequences.lastIndexOf("", *)           = -1
    * CharSequences.lastIndexOf("aabaabaa", 'a') = 7
    * CharSequences.lastIndexOf("aabaabaa", 'b') = 5
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchChar  the character to find
    * @return the last index of the search character, -1 if no match or {@code null}
    *          input
    */
   public static int lastIndexOf(CharSequence seq, char searchChar)
   {
      if(seq == null) return -1;
      return lastIndexOf(seq, searchChar, seq.length() - 1);
   }

   /**
    * Finds the last index of a search character within a CharSequence from a given start
    * position.
    * <p>
    * A {@code null} or empty ("") CharSequence will return {@code -1}. A negative start
    * position returns {@code -1}. A start position greater than the string length searches
    * the whole string.
    * <p>
    * <pre>
    * CharSequences.lastIndexOf(null, *, *)          = -1
    * CharSequences.lastIndexOf("", *,  *)           = -1
    * CharSequences.lastIndexOf("aabaabaa", 'b', 8)  = 5
    * CharSequences.lastIndexOf("aabaabaa", 'b', 4)  = 2
    * CharSequences.lastIndexOf("aabaabaa", 'b', 0)  = -1
    * CharSequences.lastIndexOf("aabaabaa", 'b', 9)  = 5
    * CharSequences.lastIndexOf("aabaabaa", 'b', -1) = -1
    * CharSequences.lastIndexOf("aabaabaa", 'a', 0)  = 0
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchChar  the character to find
    * @param startPos  the start position
    * @return the last index of the search character, -1 if no match or {@code null} input
    */
   public static int lastIndexOf(CharSequence seq, char searchChar, int startPos)
   {
      if(seq == null || startPos < 0) return -1;
      for(int i = Math.min(startPos, seq.length() - 1); i >= 0; i--) {
         if(seq.charAt(i) == searchChar) return i;
      }
      return -1;
   }

   /**
    * Finds the last index of a search CharSequence within a CharSequence.
    * <p>
    * A {@code null} CharSequence will return {@code -1}.
    * <p>
    * <pre>
    * CharSequences.lastIndexOf(null, *)          = -1
    * CharSequences.lastIndexOf(*, null)          = -1
    * CharSequences.lastIndexOf("", "")           = -1
    * CharSequences.lastIndexOf("aabaabaa", "a")  = 7
    * CharSequences.lastIndexOf("aabaabaa", "b")  = 5
    * CharSequences.lastIndexOf("aabaabaa", "ab") = 4
    * CharSequences.lastIndexOf("aabaabaa", "")   = -1
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchSeq  the CharSequence to find, may be null
    * @return the last index of the search CharSequence, -1 if no match or {@code null}
    *          input
    */
   public static int lastIndexOf(CharSequence seq, CharSequence searchSeq)
   {
      if(seq == null || searchSeq == null) return -1;
      return lastIndexOf(seq, searchSeq, Math.max(seq.length() - 1, 0));
   }


   /**
    * Finds the last index of a search CharSequence within a CharSequence from a given start
    * position.
    * <p>
    * A {@code null} CharSequence will return {@code -1}. A negative start position returns
    * {@code -1}. An empty ("") search CharSequence always matches unless the start position
    * is negative. A start position greater than the CharSequence length searches the whole
    * sequence.
    * <p>
    * <pre>
    * CharSequences.lastIndexOf(null, *, *)          = -1
    * CharSequences.lastIndexOf(*, null, *)          = -1
    * CharSequences.lastIndexOf("aabaabaa", "a", 8)  = 7
    * CharSequences.lastIndexOf("aabaabaa", "b", 8)  = 5
    * CharSequences.lastIndexOf("aabaabaa", "ab", 8) = 4
    * CharSequences.lastIndexOf("aabaabaa", "b", 9)  = 5
    * CharSequences.lastIndexOf("aabaabaa", "b", -1) = -1
    * CharSequences.lastIndexOf("aabaabaa", "a", 0)  = 0
    * CharSequences.lastIndexOf("aabaabaa", "b", 0)  = -1
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchSeq  the CharSequence to find, may be null
    * @param startPos  the start position, negative treated as zero
    * @return the first index of the search CharSequence, -1 if no match or {@code null}
    *          input
    */
   public static int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos)
   {
      if(seq == null || searchSeq == null || startPos < 0) return -1;
      if(searchSeq.length() > seq.length()) return -1;
      for(int i = Math.min(startPos, seq.length() - searchSeq.length()); i >= 0; i--) {
         if(equal(seq.subSequence(i, i + searchSeq.length()), searchSeq)) return i;
      }
      return -1;
   }


   // TODO Case insensitive search with char???

   /**
    * Case in-sensitive find of the last index of a search CharSequence within a CharSequence.
    * <p>
    * A {@code null} CharSequence will return {@code -1}. A negative start position returns
    * {@code -1}. An empty ("") search CharSequence always matches unless the start position
    * is negative. A start position greater than the string length searches the whole string.
    * <p>
    * <pre>
    * CharSequences.lastIndexOfIgnoreCase(null, *)          = -1
    * CharSequences.lastIndexOfIgnoreCase(*, null)          = -1
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchSeq  the CharSequence to find, may be null
    * @return the first index of the search CharSequence, -1 if no match or {@code null} input
    */
   public static int lastIndexOfIgnoreCase(CharSequence seq, CharSequence searchSeq)
   {
      if(seq == null || searchSeq == null) return -1;
      return lastIndexOfIgnoreCase(seq, searchSeq, seq.length() - 1);
   }

   /**
    * Case in-sensitive find of the last index of a search CharSequence within a CharSequence
    * from the specified start position.
    * <p>
    * A {@code null} CharSequence will return {@code -1}. A negative start position returns
    * {@code -1}. An empty ("") search CharSequence always matches unless the start position
    * is negative. A start position greater than the string length searches the whole string.
    * <p>
    * <pre>
    * CharSequences.lastIndexOfIgnoreCase(null, *, *)          = -1
    * CharSequences.lastIndexOfIgnoreCase(*, null, *)          = -1
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
    * CharSequences.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param searchSeq  the CharSequence to find, may be null
    * @param startPos  the start position
    * @return the first index of the search CharSequence, -1 if no match or {@code null} input
    */
   public static int lastIndexOfIgnoreCase(CharSequence seq, CharSequence searchSeq, int startPos)
   {
      if(seq == null || searchSeq == null || startPos < 0) return -1;
      if(searchSeq.length() > seq.length()) return -1;
      for(int i =  Math.min(startPos, seq.length() - searchSeq.length()); i >= 0; i--) {
         if(equalIgnoreCase(seq.subSequence(i, i + searchSeq.length()), searchSeq)) return i;
      }
      return -1;
   }





   // substring


   /**
    * Gets a substring from the specified CharSequence avoiding exceptions.
    * <p>
    * A negative start position can be used to start {@code n} characters from the end
    * of the CharSequence.
    * <p>
    * A {@code null} CharSequence will return {@code null}. An empty ("") CharSequence
    * will return "".
    * <p>
    * <pre>
    * CharSequences.substring(null, *)   = null
    * CharSequences.substring("", *)     = ""
    * CharSequences.substring("abc", 0)  = "abc"
    * CharSequences.substring("abc", 2)  = "c"
    * CharSequences.substring("abc", 4)  = ""
    * CharSequences.substring("abc", -2) = "bc"
    * CharSequences.substring("abc", -4) = "abc"
    * </pre>
    *
    * @param seq  the CharSequence to get the substring from, may be null
    * @param start  the position to start from, negative means count back from the end of
    *               the CharSequence by this many characters
    * @return sub sequence from start position, {@code null} if null input
    */
   public static CharSequence substring(CharSequence seq, int start)
   {
      if(seq == null) return null;
      if(start >= 0) {
         if(start >= seq.length()) return "";
         return seq.subSequence(start, seq.length());
      } else {
         if(Math.abs(start) >= seq.length()) return seq.subSequence(0,seq.length());
         return seq.subSequence(seq.length() + start, seq.length());
      }
   }

   /**
    * Gets a substring from the specified CharSequence avoiding exceptions.
    * <p>
    * A negative start position can be used to start/end {@code n} characters from the end
    * of the CharSequence.
    * <p>
    * The returned substring starts with the character in the {@code start} position and ends
    * before the {@code end} position. All position counting is zero-based -- i.e., to start
    * at the beginning of the CharSequence use {@code start = 0}.  Negative start and end
    * positions can be used to specify offsets relative to the end of the CharSequence.
    * <p>
    * If {@code start} is not strictly to the left of {@code end}, "" is returned.
    * <p>
    * <pre>
    * CharSequences.substring(null, *, *)    = null
    * CharSequences.substring("", * ,  *)    = "";
    * CharSequences.substring("abc", 0, 2)   = "ab"
    * CharSequences.substring("abc", 2, 0)   = ""
    * CharSequences.substring("abc", 2, 4)   = "c"
    * CharSequences.substring("abc", 4, 6)   = ""
    * CharSequences.substring("abc", 2, 2)   = ""
    * CharSequences.substring("abc", -2, -1) = "b"
    * CharSequences.substring("abc", -4, 2)  = "ab"
    * </pre>
    *
    * @param seq  the CharSequence to get the substring from, may be null
    * @param start  the position to start from, negative means count back from the end
    *               of the CharSequence by this many characters
    * @param end  the position to end at (exclusive), negative means count back from the
    *                end of the CharSequence by this many characters
    * @return sub sequence from start position to end position, {@code null} if null input
    */
   public static CharSequence substring(CharSequence seq, int start, int end)
   {
      if(seq == null) return null;
      int startPos = (start < 0) ? Math.max(0, seq.length() + start) : start;
      int endPos = (end < 0) ? Math.max(0, seq.length() + end) : end;
      if(startPos >= endPos || startPos >= seq.length()) return "";
      return seq.subSequence(startPos, Math.min(endPos, seq.length()));
   }





   // startsWith


   /**
    * Check if a CharSequence starts with a specified prefix.
    * <p>
    * {@code null}s are handled without exceptions. Two {@code null} references are
    * considered to be equal. The comparison is case sensitive.
    * <p>
    * <pre>
    * CharSequences.startsWith(null, null)      = true
    * CharSequences.startsWith(null, "abc")     = false
    * CharSequences.startsWith("abcdef", null)  = false
    * CharSequences.startsWith("abcdef", "abc") = true
    * CharSequences.startsWith("ABCDEF", "abc") = false
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param prefix the prefix to find, may be null
    * @return {@code true} if the CharSequence starts with the prefix, case sensitive,
    *             or both {@code null}
    */
   public static boolean startsWith(CharSequence seq, CharSequence prefix)
   {
      return startsWith(seq, prefix, false);
   }

   /**
    * Case insensitive check if a CharSequence starts with a specified prefix.
    * <p>
    * {@code null}s are handled without exceptions. Two {@code null} references are
    * considered to be equal. The comparison is case insensitive.
    * <p>
    * <pre>
    * CharSequences.startsWithIgnoreCase(null, null)      = true
    * CharSequences.startsWithIgnoreCase(null, "abc")     = false
    * CharSequences.startsWithIgnoreCase("abcdef", null)  = false
    * CharSequences.startsWithIgnoreCase("abcdef", "abc") = true
    * CharSequences.startsWithIgnoreCase("ABCDEF", "abc") = true
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param prefix the prefix to find, may be null
    * @return {@code true} if the CharSequence starts with the prefix, case insensitive,
    *          or both {@code null}
    */
   public static boolean startsWithIgnoreCase(CharSequence seq, CharSequence prefix)
   {
      return startsWith(seq, prefix, true);
   }

   /**
    * Check if a CharSequence starts with a specified prefix (optionally case insensitive).
    *
    * @param seq  the CharSequence to check, may be null
    * @param prefix the prefix to find, may be null
    * @param ignoreCase indicates whether the compare should ignore case (case insensitive)
    *                   or not.
    * @return {@code true} if the CharSequence starts with the prefix or both {@code null}
    */
   private static boolean startsWith(CharSequence seq, CharSequence prefix, boolean ignoreCase)
   {
      if(seq == null) return prefix == null;
      if(prefix == null || prefix.length() > seq.length()) return false;
      for(int i = 0; i < prefix.length(); i++) {
         char c1 = seq.charAt(i);
         char c2 = prefix.charAt(i);
         if(c1 != c2) {
            if(ignoreCase && Character.toUpperCase(c1) == Character.toUpperCase(c2)) continue;
            return false;
         }
      }
      return true;
   }




   // endsWith


   /**
    * Check if a CharSequence ends with a specified suffix.
    * <p>
    * {@code null}s are handled without exceptions. Two {@code null} references are considered
    * to be equal. The comparison is case sensitive.
    * <p>
    * <pre>
    * CharSequences.endsWith(null, null)      = true
    * CharSequences.endsWith(null, "def")     = false
    * CharSequences.endsWith("abcdef", null)  = false
    * CharSequences.endsWith("abcdef", "def") = true
    * CharSequences.endsWith("ABCDEF", "def") = false
    * CharSequences.endsWith("ABCDEF", "cde") = false
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param suffix the suffix to find, may be null
    * @return {@code true} if the CharSequence ends with the suffix, case sensitive, or both
    *          {@code null}
    */
   public static boolean endsWith(CharSequence seq, CharSequence suffix)
   {
      return endsWith(seq, suffix, false);
   }

   /**
    * Case insensitive check if a CharSequence ends with a specified suffix.
    * <p>
    * {@code null}s are handled without exceptions. Two {@code null} references are considered
    * to be equal. The comparison is case insensitive.
    * <p>
    * <pre>
    * CharSequence.endsWithIgnoreCase(null, null)      = true
    * CharSequence.endsWithIgnoreCase(null, "def")     = false
    * CharSequence.endsWithIgnoreCase("abcdef", null)  = false
    * CharSequence.endsWithIgnoreCase("abcdef", "def") = true
    * CharSequence.endsWithIgnoreCase("ABCDEF", "def") = true
    * CharSequence.endsWithIgnoreCase("ABCDEF", "cde") = false
    * </pre>
    *
    * @param seq  the CharSequence to check, may be null
    * @param suffix the suffix to find, may be null
    * @return {@code true} if the CharSequence ends with the suffix, case insensitive, or both
    *          {@code null}
    */
   public static boolean endsWithIgnoreCase(CharSequence seq, CharSequence suffix)
   {
      return endsWith(seq, suffix, true);
   }

   /**
    * Check if a CharSequence ends with a specified suffix (optionally case insensitive).
    *
    * @param seq  the CharSequence to check, may be null
    * @param suffix the suffix to find, may be null
    * @param ignoreCase indicates whether the compare should ignore case (case insensitive)
    *                   or not.
    * @return {@code true} if the CharSequence starts with the prefix or both {@code null}
    */
   private static boolean endsWith(CharSequence seq, CharSequence suffix, boolean ignoreCase)
   {
      if(seq == null) return suffix == null;
      if(suffix == null || suffix.length() > seq.length()) return false;
      int offset = seq.length() - suffix.length();
      for(int i = 0; i < suffix.length(); i++) {
         char c1 = seq.charAt(i + offset);
         char c2 = suffix.charAt(i);
         if(c1 != c2) {
            if(ignoreCase && Character.toUpperCase(c1) == Character.toUpperCase(c2)) continue;
            return false;
         }
      }
      return true;
   }






   // Argument checking


   /**
    * Argument checking utility that will throw an {@link IllegalArgumentException} with
    * the given message if the specified argument is {@code null} or zero length.
    */
   public static CharSequence notEmpty(CharSequence arg, String msg)
   {
      if(isEmpty(arg)) throw new IllegalArgumentException(msg);
      return arg;
   }


}
