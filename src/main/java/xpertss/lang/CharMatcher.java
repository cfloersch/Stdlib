package xpertss.lang;

import java.util.Arrays;
import java.util.function.Predicate;

import static xpertss.lang.Objects.notNull;
import static xpertss.lang.Strings.*;

/**
 * Determines a true or false value for any Java {@code char} value. Also offers basic text
 * processing methods based on this function. Implementations are strongly encouraged to be
 * side-effect-free and immutable.
 * <p/>
 * Throughout the documentation of this class, the phrase "matching character" is used to
 * mean "any {@code char} value {@code c} for which {@code this.matches(c)} returns
 * {@code true}".
 * <p/>
 * <b>Warning:</b> This class deals only with {@code char} values, that is, <a
 * href="http://www.unicode.org/glossary/#BMP_character">BMP characters</a>. It does not
 * understand <a href="http://www.unicode.org/glossary/#supplementary_code_point">
 * supplementary Unicode code points</a> in the range {@code 0x10000} to {@code 0x10FFFF}
 * which includes the majority of assigned characters, including important CJK characters
 * and emoji.
 * <p/>
 * Supplementary characters are <a
 * href="https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html#supplementary">
 * encoded into a {@code String} using surrogate pairs</a>, and a {@code CharMatcher} treats
 * these just as two separate characters. {@link #countIn} counts each supplementary character
 * as 2 {@code char}s.
 * <p/>
 * For up-to-date Unicode character properties (digit, letter, etc.) and support for
 * supplementary code points, use ICU4J UCharacter and UnicodeSet (freeze() after building).
 * For basic text processing based on UnicodeSet use the ICU4J UnicodeSetSpanner.
 *
 * <p>Example usages:
 *
 * <pre>
 *   String trimmed = {@link #whitespace() whitespace()}.{@link #trimFrom trimFrom}(userInput);
 *   if ({@link #ascii() ascii()}.{@link #matchesAllOf matchesAllOf}(s)) { ... }
 * </pre>
 */
public abstract class CharMatcher {

    /**
     * Matches any character.
     */
    public static CharMatcher any()
    {
        return Any.INSTANCE;
    }

    /**
     * Matches no characters.
     */
    public static CharMatcher none()
    {
        return None.INSTANCE;
    }

    /**
     * Determines whether a character is whitespace according to the latest Unicode standard,
     * as illustrated <a
     * href="http://unicode.org/cldr/utility/list-unicodeset.jsp?a=%5Cp%7Bwhitespace%7D">here</a>.
     * This is not the same definition used by other Java APIs. (See a <a
     * href="https://docs.google.com/spreadsheets/d/1kq4ECwPjHX9B8QUCTPclgsDCXYaj7T-FlT4tB5q3ahk/edit">comparison
     * of several definitions of "whitespace"</a>.)
     *
     * <p>All Unicode White_Space characters are on the BMP and thus supported by this API.
     *
     * <p><b>Note:</b> as the Unicode definition evolves, we will modify this matcher to keep it
     * up to date.
     */
    public static CharMatcher whitespace()
    {
        return Whitespace.INSTANCE;
    }

    /**
     * Determines whether a character is a breaking whitespace (that is, a whitespace
     * which can be interpreted as a break between words for formatting purposes). See
     * {@link #whitespace()} for a discussion of that term.
     */
    public static CharMatcher breakingWhitespace()
    {
        return BreakingWhitespace.INSTANCE;
    }

    /**
     * Determines whether a character is an ISO control character as specified by
     * {@link Character#isISOControl(char)}.
     *
     * <p>All ISO control codes are on the BMP and thus supported by this API.
     */
    public static CharMatcher javaIsoControl()
    {
        return JavaIsoControl.INSTANCE;
    }

    /**
     * Determines whether a character is ASCII, meaning that its code point is less
     * than 128.
     */
    public static CharMatcher ascii()
    {
        return Ascii.INSTANCE;
    }





    /** Returns a {@code char} matcher that matches only one specified BMP character. */
    public static CharMatcher is(final char match)
    {
        return new Is(match);
    }

    /**
     * Returns a {@code char} matcher that matches any character except the BMP character
     * specified.
     *
     * <p>To negate another {@code CharMatcher}, use {@link #negate()}.
     */
    public static CharMatcher isNot(final char match)
    {
        return new IsNot(match);
    }

    public static CharMatcher isEither(char c1, char c2)
    {
        return new CharMatcher.IsEither(c1, c2);
    }


    /**
     * Returns a {@code char} matcher that matches any BMP character present in
     * the given character sequence. Returns a bogus matcher if the sequence
     * contains supplementary characters.
     */
    public static CharMatcher anyOf(final CharSequence sequence)
    {
        return anyOf(sequence.toString().toCharArray());
    }

    public static CharMatcher anyOf(final char ... sequence)
    {
        switch (sequence.length) {
            case 0:
                return none();
            case 1:
                return is(sequence[0]);
            case 2:
                return isEither(sequence[0], sequence[1]);
            default:
                return new AnyOf(sequence);
        }
    }

    /**
     * Returns a {@code char} matcher that matches any BMP character not present
     * in the given character sequence. Returns a bogus matcher if the sequence
     * contains supplementary characters.
     */
    public static CharMatcher noneOf(char ... sequence)
    {
        return anyOf(sequence).negate();
    }

    public static CharMatcher noneOf(CharSequence sequence)
    {
        return anyOf(sequence).negate();
    }

    /**
     * Returns a {@code char} matcher that matches any character in a given BMP range (both endpoints
     * are inclusive). For example, to match any lowercase letter of the English alphabet, use {@code
     * CharMatcher.inRange('a', 'z')}.
     *
     * @throws IllegalArgumentException if {@code endInclusive < startInclusive}
     */
    public static CharMatcher inRange(final char startInclusive, final char endInclusive)
    {
        return new InRange(startInclusive, endInclusive);
    }

    /**
     * Returns a {@code char} matcher that matches using the given predicate.
     */
    public static CharMatcher forPredicate(final Predicate<? super Character> predicate)
    {
        return new ForPredicate(predicate);
    }




    /**
     * Constructor for use by subclasses. When subclassing, you may want to override {@code
     * toString()} to provide a useful description.
     */
    protected CharMatcher() {}

    // Abstract methods

    /** Determines a true or false value for the given character. */
    public abstract boolean matches(char c);

    // Non-static factories

    /** Returns a matcher that matches any character not matched by this matcher. */
    // This is not an override in java7, where Guava's Predicate does not extend the JDK's Predicate.
    @SuppressWarnings("MissingOverride")
    public CharMatcher negate()
    {
        return new Negated(this);
    }

    /**
     * Returns a matcher that matches any character matched by both this matcher and {@code other}.
     */
    public CharMatcher and(CharMatcher other)
    {
        return new And(this, other);
    }

    /**
     * Returns a matcher that matches any character matched by either this matcher or {@code other}.
     */
    public CharMatcher or(CharMatcher other)
    {
        return new Or(this, other);
    }



    // Text processing routines

    /**
     * Returns {@code true} if a character sequence contains at least one matching
     * BMP character. Equivalent to {@code !matchesNoneOf(sequence)}.
     * <p/>
     * The default implementation iterates over the sequence, invoking {@link
     * #matches} for each character, until this returns {@code true} or the end is
     * reached.
     *
     * @param sequence the character sequence to examine, possibly empty
     * @return {@code true} if this matcher matches at least one character in the
     *     sequence
     */
    public boolean matchesAnyOf(CharSequence sequence)
    {
        return !matchesNoneOf(sequence);
    }

    /**
     * Returns {@code true} if a character sequence contains only matching BMP characters.
     * <p/>
     * The default implementation iterates over the sequence, invoking {@link #matches}
     * for each character, until this returns {@code false} or the end is reached.
     *
     * @param sequence the character sequence to examine, possibly empty
     * @return {@code true} if this matcher matches every character in the sequence,
     *     including when the sequence is empty
     */
    public boolean matchesAllOf(CharSequence sequence)
    {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (!matches(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns {@code true} if a character sequence contains no matching BMP characters.
     * Equivalent to {@code !matchesAnyOf(sequence)}.
     * <p/>
     * The default implementation iterates over the sequence, invoking {@link #matches}
     * for each character, until this returns {@code true} or the end is reached.
     *
     * @param sequence the character sequence to examine, possibly empty
     * @return {@code true} if this matcher matches no characters in the sequence,
     *      including when the sequence is empty
     */
    public boolean matchesNoneOf(CharSequence sequence)
    {
        return indexIn(sequence) == -1;
    }

    /**
     * Returns the index of the first matching BMP character in a character sequence,
     * or {@code -1} if no matching character is present.
     * <p/>
     * The default implementation iterates over the sequence in forward order calling
     * {@link #matches} for each character.
     *
     * @param sequence the character sequence to examine from the beginning
     * @return an index, or {@code -1} if no character matches
     */
    public int indexIn(CharSequence sequence)
    {
        return indexIn(sequence, 0);
    }

    /**
     * Returns the index of the first matching BMP character in a character sequence,
     * starting from a given position, or {@code -1} if no character matches after that
     * position.
     * <p/>
     * The default implementation iterates over the sequence in forward order, beginning
     * at {@code start}, calling {@link #matches} for each character.
     *
     * @param sequence the character sequence to examine
     * @param start the first index to examine; must be nonnegative and no greater than
     *     {@code sequence.length()}
     * @return the index of the first matching character, guaranteed to be no less than
     *     {@code start}, or {@code -1} if no character matches
     * @throws IndexOutOfBoundsException if start is negative or greater than {@code
     *     sequence.length()}
     */
    public int indexIn(CharSequence sequence, int start)
    {
        int length = sequence.length();
        for (int i = start; i < length; i++) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last matching BMP character in a character sequence,
     * or {@code -1} if no matching character is present.
     * <p/>
     * The default implementation iterates over the sequence in reverse order calling
     * {@link #matches} for each character.
     *
     * @param sequence the character sequence to examine from the end
     * @return an index, or {@code -1} if no character matches
     */
    public int lastIndexIn(CharSequence sequence)
    {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the number of matching {@code char}s found in a character sequence.
     * <p/>
     * Counts 2 per supplementary character, such as for {@link #whitespace}().
     * {@link #negate}().
     */
    public int countIn(CharSequence sequence)
    {
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (matches(sequence.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns a string containing all non-matching characters of a character sequence,
     * in order. For example:
     *
     * <pre>{@code
     * CharMatcher.is('a').removeFrom("bazaar")
     * }</pre>
     *
     * ... returns {@code "bzr"}.
     */
    public String removeFrom(CharSequence sequence)
    {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }

        char[] chars = string.toCharArray();
        int spread = 1;

        // This unusual loop comes from extensive benchmarking
        OUT:
        while (true) {
            pos++;
            while (true) {
                if (pos == chars.length) {
                    break OUT;
                }
                if (matches(chars[pos])) {
                    break;
                }
                chars[pos - spread] = chars[pos];
                pos++;
            }
            spread++;
        }
        return new String(chars, 0, pos - spread);
    }

    /**
     * Returns a string containing all matching BMP characters of a character sequence,
     * in order. For example:
     *
     * <pre>{@code
     * CharMatcher.is('a').retainFrom("bazaar")
     * }</pre>
     *
     * ... returns {@code "aaa"}.
     */
    public String retainFrom(CharSequence sequence)
    {
        return negate().removeFrom(sequence);
    }

    /**
     * Returns a string copy of the input character sequence, with each matching BMP
     * character* replaced by a given replacement character. For example:
     *
     * <pre>{@code
     * CharMatcher.is('a').replaceFrom("radar", 'o')
     * }</pre>
     *
     * ... returns {@code "rodor"}.
     * <p/>
     * The default implementation uses {@link #indexIn(CharSequence)} to find the
     * first matching character, then iterates the remainder of the sequence calling
     * {@link #matches(char)} for each character.
     *
     * @param sequence the character sequence to replace matching characters in
     * @param replacement the character to append to the result string in place of
     *          each matching character in {@code sequence}
     * @return the new string
     */
    public String replaceFrom(CharSequence sequence, char replacement)
    {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        chars[pos] = replacement;
        for (int i = pos + 1; i < chars.length; i++) {
            if (matches(chars[i])) {
                chars[i] = replacement;
            }
        }
        return new String(chars);
    }

    /**
     * Returns a string copy of the input character sequence, with each matching
     * BMP character replaced by a given replacement sequence. For example:
     *
     * <pre>{@code
     * CharMatcher.is('a').replaceFrom("yaha", "oo")
     * }</pre>
     *
     * ... returns {@code "yoohoo"}.
     * <p/>
     * <b>Note:</b> If the replacement is a fixed string with only one character,
     * you are better off calling {@link #replaceFrom(CharSequence, char)} directly.
     *
     * @param sequence the character sequence to replace matching characters in
     * @param replacement the characters to append to the result string in place of
     *                    each matching character in {@code sequence}
     * @return the new string
     */
    public String replaceFrom(CharSequence sequence, CharSequence replacement)
    {
        int replacementLen = replacement.length();
        if (replacementLen == 0) {
            return removeFrom(sequence);
        }
        if (replacementLen == 1) {
            return replaceFrom(sequence, replacement.charAt(0));
        }

        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }

        int len = string.length();
        StringBuilder buf = new StringBuilder((len * 3 / 2) + 16);

        int oldpos = 0;
        do {
            buf.append(string, oldpos, pos);
            buf.append(replacement);
            oldpos = pos + 1;
            pos = indexIn(string, oldpos);
        } while (pos != -1);

        buf.append(string, oldpos, len);
        return buf.toString();
    }

    /**
     * Returns a substring of the input character sequence that omits all matching
     * BMP characters from the beginning and from the end of the string. For example:
     *
     * <pre>{@code
     * CharMatcher.anyOf("ab").trimFrom("abacatbab")
     * }</pre>
     *
     * ... returns {@code "cat"}.
     *
     * <p>Note that:
     *
     * <pre>{@code
     * CharMatcher.inRange('\0', ' ').trimFrom(str)
     * }</pre>
     *
     * ... is equivalent to {@link String#trim()}.
     */
    public String trimFrom(CharSequence sequence)
    {
        int len = sequence.length();
        int first;
        int last;

        for (first = 0; first < len; first++) {
            if (!matches(sequence.charAt(first))) {
                break;
            }
        }
        for (last = len - 1; last > first; last--) {
            if (!matches(sequence.charAt(last))) {
                break;
            }
        }

        return sequence.subSequence(first, last + 1).toString();
    }

    /**
     * Returns a substring of the input character sequence that omits all matching
     * BMP characters from the beginning of the string. For example:
     *
     * <pre>{@code
     * CharMatcher.anyOf("ab").trimLeadingFrom("abacatbab")
     * }</pre>
     *
     * ... returns {@code "catbab"}.
     */
    public String trimLeadingFrom(CharSequence sequence)
    {
        int len = sequence.length();
        for (int first = 0; first < len; first++) {
            if (!matches(sequence.charAt(first))) {
                return sequence.subSequence(first, len).toString();
            }
        }
        return "";
    }

    /**
     * Returns a substring of the input character sequence that omits all matching
     * BMP characters from the end of the string. For example:
     *
     * <pre>{@code
     * CharMatcher.anyOf("ab").trimTrailingFrom("abacatbab")
     * }</pre>
     *
     * ... returns {@code "abacat"}.
     */
    public String trimTrailingFrom(CharSequence sequence)
    {
        int len = sequence.length();
        for (int last = len - 1; last >= 0; last--) {
            if (!matches(sequence.charAt(last))) {
                return sequence.subSequence(0, last + 1).toString();
            }
        }
        return "";
    }

    /**
     * Returns a string copy of the input character sequence, with each group of
     * consecutive matching BMP characters replaced by a single replacement character.
     * For example:
     *
     * <pre>{@code
     * CharMatcher.anyOf("eko").collapseFrom("bookkeeper", '-')
     * }</pre>
     *
     * ... returns {@code "b-p-r"}.
     * <p/>
     * The default implementation uses {@link #indexIn(CharSequence)} to find the first
     * matching character, then iterates the remainder of the sequence calling {@link
     * #matches(char)} for each character.
     *
     * @param sequence the character sequence to replace matching groups of characters in
     * @param replacement the character to append to the result string in place of each
     *                    group of matching characters in {@code sequence}
     * @return the new string
     */
    public String collapseFrom(CharSequence sequence, char replacement)
    {
        // This implementation avoids unnecessary allocation.
        int len = sequence.length();
        for (int i = 0; i < len; i++) {
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (c == replacement && (i == len - 1 || !matches(sequence.charAt(i + 1)))) {
                    // a no-op replacement
                    i++;
                } else {
                    StringBuilder builder = new StringBuilder(len).append(sequence, 0, i).append(replacement);
                    return finishCollapseFrom(sequence, i + 1, len, replacement, builder, true);
                }
            }
        }
        // no replacement needed
        return sequence.toString();
    }

    /**
     * Collapses groups of matching characters exactly as {@link #collapseFrom}
     * does, except that groups of matching BMP characters at the start or end of
     * the sequence are removed without replacement.
     */
    public String trimAndCollapseFrom(CharSequence sequence, char replacement)
    {
        // This implementation avoids unnecessary allocation.
        int len = sequence.length();
        int first = 0;
        int last = len - 1;

        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }

        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }

        return (first == 0 && last == len - 1)
                ? collapseFrom(sequence, replacement)
                : finishCollapseFrom(
                sequence, first, last + 1, replacement, new StringBuilder(last + 1 - first), false);
    }

    private String finishCollapseFrom(
            CharSequence sequence,
            int start,
            int end,
            char replacement,
            StringBuilder builder,
            boolean inMatchingGroup)
    {
        for (int i = start; i < end; i++) {
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (!inMatchingGroup) {
                    builder.append(replacement);
                    inMatchingGroup = true;
                }
            } else {
                builder.append(c);
                inMatchingGroup = false;
            }
        }
        return builder.toString();
    }


    /**
     * Returns a string representation of this {@code CharMatcher}, such as
     * {@code CharMatcher.or(WHITESPACE, JAVA_DIGIT)}.
     */
    @Override
    public String toString()
    {
        return super.toString();
    }

    /**
     * Returns the Java Unicode escape sequence for the given {@code char}, in the
     * form "\u12AB" where "12AB" is the four hexadecimal digits representing the
     * 16-bit code unit.
     */
    private static String showCharacter(char c)
    {
        String hex = "0123456789ABCDEF";
        char[] tmp = {'\\', 'u', '\0', '\0', '\0', '\0'};
        for (int i = 0; i < 4; i++) {
            tmp[5 - i] = hex.charAt(c & 0xF);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(tmp);
    }




    // Fast matchers

    /** A matcher for which precomputation will not yield any significant benefit. */
    abstract static class FastMatcher extends CharMatcher {

        @Override
        public CharMatcher negate()
        {
            return new NegatedFastMatcher(this);
        }
    }

    /** {@link FastMatcher} which overrides {@code toString()} with a custom name. */
    abstract static class NamedFastMatcher extends FastMatcher {

        private final String description;

        NamedFastMatcher(String description)
        {
            this.description = notNull(description);
        }

        @Override
        public final String toString()
        {
            return description;
        }
    }

    /** Negation of a {@link FastMatcher}. */
    private static class NegatedFastMatcher extends Negated {

        NegatedFastMatcher(CharMatcher original)
        {
            super(original);
        }

    }


    // Static constant implementation classes

    /** Implementation of {@link #any()}. */
    private static final class Any extends NamedFastMatcher {

        static final CharMatcher INSTANCE = new Any();

        private Any()
        {
            super("CharMatcher.any()");
        }

        @Override
        public boolean matches(char c)
        {
            return true;
        }

        @Override
        public int indexIn(CharSequence sequence)
        {
            return (sequence.length() == 0) ? -1 : 0;
        }

        @Override
        public int indexIn(CharSequence sequence, int start)
        {
            int length = sequence.length();
            checkPositionIndex(start, length);
            return (start == length) ? -1 : start;
        }

        @Override
        public int lastIndexIn(CharSequence sequence)
        {
            return sequence.length() - 1;
        }

        @Override
        public boolean matchesAllOf(CharSequence sequence)
        {
            notNull(sequence);
            return true;
        }

        @Override
        public boolean matchesNoneOf(CharSequence sequence)
        {
            return sequence.length() == 0;
        }

        @Override
        public String removeFrom(CharSequence sequence)
        {
            notNull(sequence);
            return "";
        }

        @Override
        public String replaceFrom(CharSequence sequence, char replacement)
        {
            char[] array = new char[sequence.length()];
            Arrays.fill(array, replacement);
            return new String(array);
        }

        @Override
        public String replaceFrom(CharSequence sequence, CharSequence replacement)
        {
            StringBuilder result = new StringBuilder(sequence.length() * replacement.length());
            for (int i = 0; i < sequence.length(); i++) {
                result.append(replacement);
            }
            return result.toString();
        }

        @Override
        public String collapseFrom(CharSequence sequence, char replacement)
        {
            return (sequence.length() == 0) ? "" : String.valueOf(replacement);
        }

        @Override
        public String trimFrom(CharSequence sequence)
        {
            notNull(sequence);
            return "";
        }

        @Override
        public int countIn(CharSequence sequence)
        {
            return sequence.length();
        }

        @Override
        public CharMatcher and(CharMatcher other)
        {
            return notNull(other);
        }

        @Override
        public CharMatcher or(CharMatcher other)
        {
            notNull(other);
            return this;
        }

        @Override
        public CharMatcher negate()
        {
            return none();
        }
    }

    /** Implementation of {@link #none()}. */
    private static final class None extends NamedFastMatcher {

        static final CharMatcher INSTANCE = new None();

        private None()
        {
            super("CharMatcher.none()");
        }

        @Override
        public boolean matches(char c)
        {
            return false;
        }

        @Override
        public int indexIn(CharSequence sequence)
        {
            notNull(sequence);
            return -1;
        }

        @Override
        public int indexIn(CharSequence sequence, int start)
        {
            int length = sequence.length();
            checkPositionIndex(start, length);
            return -1;
        }

        @Override
        public int lastIndexIn(CharSequence sequence)
        {
            notNull(sequence);
            return -1;
        }

        @Override
        public boolean matchesAllOf(CharSequence sequence)
        {
            return sequence.length() == 0;
        }

        @Override
        public boolean matchesNoneOf(CharSequence sequence)
        {
            notNull(sequence);
            return true;
        }

        @Override
        public String removeFrom(CharSequence sequence)
        {
            return sequence.toString();
        }

        @Override
        public String replaceFrom(CharSequence sequence, char replacement)
        {
            return sequence.toString();
        }

        @Override
        public String replaceFrom(CharSequence sequence, CharSequence replacement)
        {
            notNull(replacement);
            return sequence.toString();
        }

        @Override
        public String collapseFrom(CharSequence sequence, char replacement)
        {
            return sequence.toString();
        }

        @Override
        public String trimFrom(CharSequence sequence)
        {
            return sequence.toString();
        }

        @Override
        public String trimLeadingFrom(CharSequence sequence)
        {
            return sequence.toString();
        }

        @Override
        public String trimTrailingFrom(CharSequence sequence)
        {
            return sequence.toString();
        }

        @Override
        public int countIn(CharSequence sequence)
        {
            notNull(sequence);
            return 0;
        }

        @Override
        public CharMatcher and(CharMatcher other)
        {
            notNull(other);
            return this;
        }

        @Override
        public CharMatcher or(CharMatcher other)
        {
            return notNull(other);
        }

        @Override
        public CharMatcher negate()
        {
            return any();
        }
    }

    /** Implementation of {@link #whitespace()}. */
    static final class Whitespace extends NamedFastMatcher {

        // TABLE is a precomputed hashset of whitespace characters. MULTIPLIER serves as a hash function
        // whose key property is that it maps 25 characters into the 32-slot table without collision.
        // Basically this is an opportunistic fast implementation as opposed to "good code". For most
        // other use-cases, the reduction in readability isn't worth it.
        static final String TABLE =
                "\u2002\u3000\r\u0085\u200A\u2005\u2000\u3000"
                        + "\u2029\u000B\u3000\u2008\u2003\u205F\u3000\u1680"
                        + "\u0009\u0020\u2006\u2001\u202F\u00A0\u000C\u2009"
                        + "\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
        static final int MULTIPLIER = 1682554634;
        static final int SHIFT = Integer.numberOfLeadingZeros(TABLE.length() - 1);

        static final CharMatcher INSTANCE = new Whitespace();

        Whitespace()
        {
            super("CharMatcher.whitespace()");
        }

        @Override
        public boolean matches(char c)
        {
            return TABLE.charAt((MULTIPLIER * c) >>> SHIFT) == c;
        }


    }

    /** Implementation of {@link #breakingWhitespace()}. */
    private static final class BreakingWhitespace extends CharMatcher {

        static final CharMatcher INSTANCE = new BreakingWhitespace();

        @Override
        public boolean matches(char c)
        {
            switch (c) {
                case '\t':
                case '\n':
                case '\013':
                case '\f':
                case '\r':
                case ' ':
                case '\u0085':
                case '\u1680':
                case '\u2028':
                case '\u2029':
                case '\u205f':
                case '\u3000':
                    return true;
                case '\u2007':
                    return false;
                default:
                    return c >= '\u2000' && c <= '\u200a';
            }
        }

        @Override
        public String toString()
        {
            return "CharMatcher.breakingWhitespace()";
        }
    }

    private static final class Ascii extends NamedFastMatcher {

        static final CharMatcher INSTANCE = new Ascii();

        Ascii()
        {
            super("CharMatcher.ascii()");
        }

        @Override
        public boolean matches(char c)
        {
            return c <= '\u007f';
        }
    }

    /** Implementation that matches characters that fall within multiple ranges. */
    private static class RangesMatcher extends CharMatcher {

        private final String description;
        private final char[] rangeStarts;
        private final char[] rangeEnds;

        RangesMatcher(String description, char[] rangeStarts, char[] rangeEnds)
        {
            this.description = description;
            this.rangeStarts = rangeStarts;
            this.rangeEnds = rangeEnds;
            Booleans.check(rangeStarts.length == rangeEnds.length);
            for (int i = 0; i < rangeStarts.length; i++) {
                Booleans.check(rangeStarts[i] <= rangeEnds[i]);
                if (i + 1 < rangeStarts.length) {
                    Booleans.check(rangeEnds[i] < rangeStarts[i + 1]);
                }
            }
        }


        @Override
        public boolean matches(char c)
        {
            int index = Arrays.binarySearch(rangeStarts, c);
            if (index >= 0) {
                return true;
            } else {
                index = ~index - 1;
                return index >= 0 && c <= rangeEnds[index];
            }
        }

        @Override
        public String toString()
        {
            return description;
        }
    }

    /** Implementation of {@link #javaIsoControl()}. */
    private static final class JavaIsoControl extends NamedFastMatcher {

        static final CharMatcher INSTANCE = new JavaIsoControl();

        private JavaIsoControl()
        {
            super("CharMatcher.javaIsoControl()");
        }

        @Override
        public boolean matches(char c)
        {
            return c <= '\u001f' || (c >= '\u007f' && c <= '\u009f');
        }
    }






    // Non-static factory implementation classes

    /** Implementation of {@link #negate()}. */
    private static class Negated extends CharMatcher {

        final CharMatcher original;

        Negated(CharMatcher original)
        {
            this.original = notNull(original);
        }

        @Override
        public boolean matches(char c)
        {
            return !original.matches(c);
        }

        @Override
        public boolean matchesAllOf(CharSequence sequence)
        {
            return original.matchesNoneOf(sequence);
        }

        @Override
        public boolean matchesNoneOf(CharSequence sequence)
        {
            return original.matchesAllOf(sequence);
        }

        @Override
        public int countIn(CharSequence sequence)
        {
            return sequence.length() - original.countIn(sequence);
        }



        @Override
        public CharMatcher negate()
        {
            return original;
        }

        @Override
        public String toString()
        {
            return original + ".negate()";
        }
    }

    /** Implementation of {@link #and(CharMatcher)}. */
    private static final class And extends CharMatcher {

        final CharMatcher first;
        final CharMatcher second;

        And(CharMatcher a, CharMatcher b)
        {
            first = notNull(a);
            second = notNull(b);
        }

        @Override
        public boolean matches(char c)
        {
            return first.matches(c) && second.matches(c);
        }



        @Override
        public String toString()
        {
            return "CharMatcher.and(" + first + ", " + second + ")";
        }
    }

    /** Implementation of {@link #or(CharMatcher)}. */
    private static final class Or extends CharMatcher {

        final CharMatcher first;
        final CharMatcher second;

        Or(CharMatcher a, CharMatcher b)
        {
            first = notNull(a);
            second = notNull(b);
        }



        @Override
        public boolean matches(char c)
        {
            return first.matches(c) || second.matches(c);
        }

        @Override
        public String toString()
        {
            return "CharMatcher.or(" + first + ", " + second + ")";
        }
    }

    // Static factory implementations

    /** Implementation of {@link #is(char)}. */
    private static final class Is extends FastMatcher {

        private final char match;

        Is(char match)
        {
            this.match = match;
        }

        @Override
        public boolean matches(char c)
        {
            return c == match;
        }

        @Override
        public String replaceFrom(CharSequence sequence, char replacement)
        {
            return sequence.toString().replace(match, replacement);
        }

        @Override
        public CharMatcher and(CharMatcher other)
        {
            return other.matches(match) ? this : none();
        }

        @Override
        public CharMatcher or(CharMatcher other)
        {
            return other.matches(match) ? other : super.or(other);
        }

        @Override
        public CharMatcher negate()
        {
            return isNot(match);
        }



        @Override
        public String toString()
        {
            return "CharMatcher.is('" + showCharacter(match) + "')";
        }
    }

    /** Implementation of {@link #isNot(char)}. */
    private static final class IsNot extends FastMatcher {

        private final char match;

        IsNot(char match)
        {
            this.match = match;
        }

        @Override
        public boolean matches(char c)
        {
            return c != match;
        }

        @Override
        public CharMatcher and(CharMatcher other)
        {
            return other.matches(match) ? super.and(other) : other;
        }

        @Override
        public CharMatcher or(CharMatcher other)
        {
            return other.matches(match) ? any() : this;
        }



        @Override
        public CharMatcher negate()
        {
            return is(match);
        }

        @Override
        public String toString()
        {
            return "CharMatcher.isNot('" + showCharacter(match) + "')";
        }
    }


    /** Implementation of {@link #anyOf(CharSequence)} for exactly two characters. */
    private static final class IsEither extends FastMatcher {

        private final char match1;
        private final char match2;

        IsEither(char match1, char match2)
        {
            this.match1 = match1;
            this.match2 = match2;
        }

        @Override
        public boolean matches(char c)
        {
            return c == match1 || c == match2;
        }

        @Override
        public String toString()
        {
            return "CharMatcher.isEither(\"" + showCharacter(match1) + showCharacter(match2) + "\")";
        }
    }

    /** Implementation of {@link #anyOf(CharSequence)} for three or more characters. */
    private static final class AnyOf extends CharMatcher {

        private final char[] chars;

        public AnyOf(char ... sequence)
        {
            this.chars = sequence;
            Arrays.sort(this.chars);
        }

        @Override
        public boolean matches(char c)
        {
            return Arrays.binarySearch(chars, c) >= 0;
        }



        @Override
        public String toString()
        {
            StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");
            for (char c : chars) {
                description.append(showCharacter(c));
            }
            description.append("\")");
            return description.toString();
        }
    }

    /** Implementation of {@link #inRange(char, char)}. */
    private static final class InRange extends FastMatcher {

        private final char startInclusive;
        private final char endInclusive;

        InRange(char startInclusive, char endInclusive)
        {
            if(endInclusive < startInclusive)
                throw new IllegalArgumentException("invalid range specified");
            this.startInclusive = startInclusive;
            this.endInclusive = endInclusive;
        }

        @Override
        public boolean matches(char c)
        {
            return startInclusive <= c && c <= endInclusive;
        }



        @Override
        public String toString()
        {
            return "CharMatcher.inRange('"
                    + showCharacter(startInclusive)
                    + "', '"
                    + showCharacter(endInclusive)
                    + "')";
        }
    }


    /** Implementation of {@link #forPredicate(Predicate)}. */
    private static final class ForPredicate extends CharMatcher {

        private final Predicate<? super Character> predicate;

        ForPredicate(Predicate<? super Character> predicate)
        {
            this.predicate = notNull(predicate, "predicate");
        }

        @Override
        public boolean matches(char c)
        {
            return predicate.test(c);
        }

        @Override
        public String toString()
        {
            return "CharMatcher.forPredicate(" + predicate + ")";
        }
    }


}
