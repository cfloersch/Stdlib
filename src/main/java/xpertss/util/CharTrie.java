package xpertss.util;


import xpertss.lang.CharSequences;
import xpertss.lang.Objects;

import java.util.Arrays;

/**
 * A trie, also called digital tree, is an ordered tree data structure that is
 * used to store a dynamic set or associative array where the keys are usually
 * strings. Unlike a binary search tree, no node in the tree stores the key
 * associated with that node; instead, its position in the tree defines the
 * key with which it is associated. All the descendants of a node have a common
 * prefix of the string associated with that node, and the root is associated
 * with the empty string. Values are normally not associated with every node,
 * only with leaves and some inner nodes that correspond to keys of interest.
 * <p/>
 * This class is NOT thread safe and external thread synchronization will be
 * necessary if multiple concurrent threads read and write to it.
 * <p/>
 * TODO I was thinking of reimplementing this using a base of 256 (aka a byte)
 * then I could take any character (1 byte or 2) and the tree could be based
 * not on binarySearch but an actual index. In this case a word of 20 characters
 * might be as deep as 40 hops (2 bytes per character). Of course in the US the
 * vast majority of the second bytes (aka the high order bytes) would be 0 and
 * only the internationalized words would go the other route. Not sure exactly
 * how this would work but I bet it would annihilate this impl as the binary
 * search could be replaced with a simple index reference. Of course it would
 * likely use much more memory.. How much I am not sure..
 *
 * I could of course skip the second hop for characters which do not have any
 * high order bits.
 */
public class CharTrie<T> {

   private CharTrie<T>[] children = new CharTrie[1<<4];
   private int count = 0;

   private final char code;

   private T value;

   /**
    * Create the root Trie node.
    */
   public CharTrie()
   {
      this.code = 0;
   }

   private CharTrie(char code)
   {
      this.code = code;
   }


   /**
    * Find the value within the Trie hierarchy identified by the given sequence of
    * characters.
    *
    * @throws IllegalArgumentException if the character sequence is empty
    */
   public T find(CharSequence key)
   {
      char c = CharSequences.notEmpty(key, "key must not be empty").charAt(0);
      int idx = binarySearch(0, count, c);
      if(idx < 0) return null;
      if(key.length() == 1) return children[idx].get();
      return children[idx].find(key.subSequence(1, key.length()));
   }


   /**
    * Returns {@code true} if {@code this} is the root node, {@code false}
    * otherwise.
    */
   public boolean isRoot()
   {
      return code == 0;
   }


   /**
    * Get the value associated with this Trie or {@code null} if no value is
    * associated.
    * <p/>
    * Many of the middle nodes will not have any value associated with them.
    */
   public T get()
   {
      return value;
   }


   /**
    * Returns the child Trie of this Trie identified by the specified character or
    * {@code null} if no such child exists.
    * <p/>
    * The returned Trie will be read-only. Calls to {@link #put(CharSequence, Object)}
    * will throw {@link UnsupportedOperationException}
    */
   public CharTrie<T> findChild(char c)
   {
      int index = binarySearch(0, count, c);
      if(index < 0) return null;
      return new ROCharTrie<>(children[index]);
   }


   /**
    * Map the given value into this Trie hierarchy using the specified key.
    * <p/>
    * Returns the previously mapped value if one existed or {@code null} if
    * the key was not previously mapped to a value.
    * <p/>
    * This method may only be called on the root node.
    *
    * @throws NullPointerException if the value is {@code null}
    * @throws IllegalArgumentException if the key is empty
    * @throws UnsupportedOperationException if {@code this} is not the root node
    */
   public T put(CharSequence key, T value)
   {
      CharSequences.notEmpty(key, "key must not be empty");
      Objects.notNull(value);
      ensureCapacity(count + 1);
      char c = key.charAt(0);
      int index = binarySearch(0, count, c);
      CharTrie<T> child = (index < 0) ? insert(-(index+1), c) : children[index];
      if(key.length() > 1) {
         return child.put(key.subSequence(1, key.length()), value);
      }
      T result = child.value;
      child.value = value;
      return result;
   }


   /**
    * Returns the number of child elements that belong to this Trie.
    */
   public int size()
   {
      return count;
   }



   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof CharTrie) {
         CharTrie o = (CharTrie) obj;
         return Objects.equal(code, o.children)
                  && Objects.equal(value, o.value);
      }
      return false;
   }
   @Override
   public int hashCode()
   {
      return Objects.hash(code, value);
   }

   @Override
   public String toString()
   {
      StringBuilder buf = new StringBuilder(String.format("CharTrie(%s)", code));
      if(value != null) buf.append(" {").append(value).append("}");
      return buf.toString();
   }

   private CharTrie<T> insert(int index, char c)
   {
      CharTrie<T> child = new CharTrie<>(c);
      if(index == count) {
         children[index] = child;
      } else {
         System.arraycopy(children, index, children, index + 1, count - index);
         children[index] = child;
      }
      count++;
      return child;
   }

   void ensureCapacity(int minCapacity)
   {
      if (minCapacity - children.length > 0) {
         children = Arrays.copyOf(children, children.length << 1);
      }
   }
   int binarySearch(int fromIndex, int toIndex, char c)
   {
      int low = fromIndex;
      int high = toIndex - 1;
      while (low <= high) {
         int mid = (low + high) >>> 1;
         char midVal = children[mid].code;
         if(midVal < c) {
            low = mid + 1;
         } else if(midVal > c) {
            high = mid - 1;
         } else {
            return mid;
         }
      }
      return -(low + 1);  // key not found.
   }



   private static class ROCharTrie<T> extends CharTrie<T> {

      private CharTrie<T> delegate;

      private ROCharTrie(CharTrie<T> delegate)
      {
         this.delegate = delegate;
      }


      @Override
      public T find(CharSequence key)
      {
         return delegate.find(key);
      }

      @Override
      public boolean isRoot()
      {
         return false;
      }

      @Override
      public T get()
      {
         return delegate.get();
      }

      @Override
      public int size()
      {
         return delegate.size();
      }

      @Override
      public T put(CharSequence key, T value)
      {
         throw new UnsupportedOperationException();
      }

      @Override
      public CharTrie<T> findChild(char c)
      {
         return delegate.findChild(c);
      }

      @Override
      public boolean equals(Object obj)
      {
         if(obj instanceof ROCharTrie) {
            ROCharTrie o = (ROCharTrie) obj;
            return delegate.equals(o.delegate);
         }
         return false;
      }
      @Override
      public int hashCode()
      {
         return Objects.hash("RO", delegate);
      }

      @Override
      public String toString()
      {
         return delegate.toString();
      }



   }
}
