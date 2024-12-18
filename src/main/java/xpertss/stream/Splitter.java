package xpertss.stream;


import xpertss.lang.*;
import xpertss.lang.Objects;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Extracts non-overlapping substrings from an input string, typically by recognizing
 * appearances of a <i>separator</i> sequence. This separator can be specified as a
 * single {@linkplain #on(char) character}, fixed {@linkplain #on(String) string},
 * {@linkplain #onPattern regular expression} or {@link #on(CharMatcher) CharMatcher}
 * instance. Or, instead of using a separator at all, a splitter can extract adjacent
 * substrings of a given {@linkplain #onLength fixed length}.
 * <p/>
 * For example, this expression:
 *
 * <pre>{@code
 * Splitter.on(',').split("foo,bar,qux")
 * }</pre>
 *
 * ... produces an {@code Stream} containing {@code "foo"}, {@code "bar"} and {@code
 * "qux"}, in that order.
 * <p/>
 * By default, {@code Splitter}'s behavior is simplistic and unassuming. The following
 * expression:
 *
 * <pre>{@code
 * Splitter.on(',').split(" foo,,,  bar ,")
 * }</pre>
 *
 * ... yields the substrings {@code [" foo", "", "", " bar ", ""]}. If this is not the
 * desired behavior, use configuration methods to obtain a <i>new</i> splitter instance
 * with modified behavior:
 *
 * <pre>{@code
 * private static final Splitter MY_SPLITTER = Splitter.on(',')
 *     .trim()
 *     .omitEmpty();
 * }</pre>
 * <p/>
 * Now {@code MY_SPLITTER.split("foo,,, bar ,")} returns just {@code ["foo", "bar"]}.
 * Note that the order in which these configuration methods are called is never
 * significant.
 * <p/>
 * <b>Warning:</b> Splitter instances are immutable. Invoking a configuration method
 * has no effect on the receiving instance; you must store and use the new splitter
 * instance it returns instead.
 *
 * <pre>{@code
 * // Do NOT do this
 * Splitter splitter = Splitter.on('/');
 * splitter.trim(); // does nothing!
 * return splitter.split("wrong / wrong / wrong");
 * }</pre>
 * <p/>
 * For separator-based splitters that do not use {@code omitEmpty}, an input string
 * containing {@code n} occurrences of the separator naturally yields a stream of
 * size {@code n + 1}. So if the separator does not occur anywhere in the input, a
 * single substring is returned containing the entire input. Consequently, all
 * splitters split the empty string to {@code [""]} (note: even fixed-length
 * splitters).
 * <p/>
 * Splitter instances are thread-safe immutable, and are therefore safe to store as
 * {@code static final} constants.
 */
public final class Splitter {

    private static final long MIN_PARALLEL = 128;

    private final CharMatcher trimmer;
    private final boolean omitEmpty;
    private final Strategy strategy;
    private final int limit;

    private Splitter(Strategy strategy)
    {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    private Splitter(Strategy strategy, boolean omitEmpty, CharMatcher trimmer, int limit)
    {
        this.strategy = strategy;
        this.omitEmpty = omitEmpty;
        this.trimmer = trimmer;
        this.limit = limit;
    }


    /**
     * Returns a splitter that uses the given single-character separator. For example,
     * {@code Splitter.on(',').split("foo,,bar")} returns a stream containing
     * {@code ["foo", "", "bar"]}.
     *
     * @param separator the character to recognize as a separator
     * @return a splitter, with default settings, that recognizes that separator
     */
    public static Splitter on(char separator)
    {
        return on(CharMatcher.is(separator));
    }

    /**
     * Returns a splitter that considers any single character matched by the given
     * {@code CharMatcher} to be a separator. For example,
     * {@code Splitter.on(CharMatcher.anyOf(";,")).split("foo,;bar,quux")}
     * returns a stream containing
     * {@code ["foo", "", "bar", "quux"]}.
     *
     * @param separatorMatcher a {@link CharMatcher} that determines whether a
     *                          character is a separator
     * @return a splitter, with default settings, that uses this matcher
     */
    public static Splitter on(CharMatcher separatorMatcher)
    {
        Objects.notNull(separatorMatcher);
        return new Splitter(
                new Strategy() {
                    @Override
                    public Spliterator<String> spliterator(Splitter splitter, CharSequence sequence)
                    {
                        return new StringSpliterator(splitter, sequence) {
                            @Override
                            int separatorStart(int start)
                            {
                                return separatorMatcher.indexIn(sequence, start);
                            }

                            @Override
                            int separatorEnd(int separatorPosition)
                            {
                                return separatorPosition + 1;
                            }
                        };
                    }
                });
    }

    /**
     * Returns a splitter that uses the given fixed string as a separator. For
     * example, {@code Splitter.on(", ").split("foo, bar,baz")} returns a stream
     * containing {@code ["foo", "bar,baz"]}.
     *
     * @param separator the literal, nonempty string to recognize as a separator
     * @return a splitter, with default settings, that recognizes that separator
     */
    public static Splitter on(String separator)
    {
        Strings.notEmpty(separator, separator);
        if (separator.length() == 1) {
            return Splitter.on(separator.charAt(0));
        }
        return new Splitter(
                new Strategy() {
                    @Override
                    public Spliterator<String> spliterator(Splitter splitter, CharSequence sequence)
                    {
                        return new StringSpliterator(splitter, sequence) {
                            @Override
                            int separatorStart(int start)
                            {
                                int separatorLength = separator.length();

                                positions:
                                for (int p = start, last = sequence.length() - separatorLength; p <= last; p++) {
                                    for (int i = 0; i < separatorLength; i++) {
                                        if (sequence.charAt(i + p) != separator.charAt(i)) {
                                            continue positions;
                                        }
                                    }
                                    return p;
                                }
                                return -1;
                            }

                            @Override
                            int separatorEnd(int separatorPosition)
                            {
                                return separatorPosition + separator.length();
                            }
                        };
                    }
                });
    }

    /**
     * Returns a splitter that considers any subsequence matching {@code pattern}
     * to be a separator. For example,
     * {@code Splitter.on(Pattern.compile("\r?\n")).split(entireFile)} splits a
     * string into lines whether it uses DOS-style or UNIX-style line terminators.
     *
     * @param separatorPattern the pattern that determines whether a subsequence
     *              is a separator. This pattern may not match the empty string.
     * @return a splitter, with default settings, that uses this pattern
     * @throws IllegalArgumentException if {@code separatorPattern} matches the
     *              empty string
     */
    public static Splitter on(Pattern separatorPattern)
    {
        Booleans.check(!separatorPattern.matcher("").matches(), "separatorPattern");
        return new Splitter(
                new Strategy() {
                    @Override
                    public Spliterator<String> spliterator(Splitter splitter, CharSequence sequence)
                    {
                        final Matcher matcher = separatorPattern.matcher(sequence);
                        return new StringSpliterator(splitter, sequence) {
                            @Override
                            int separatorStart(int start)
                            {
                                return matcher.find(start) ? matcher.start() : -1;
                            }

                            @Override
                            int separatorEnd(int separatorIndex)
                            {
                                return matcher.end();
                            }
                        };
                    }
                });
    }

    /**
     * Returns a splitter that considers any subsequence matching a given pattern
     * (regular expression) to be a separator. For example,
     * {@code Splitter.onPattern("\r?\n").split(entireFile)} splits a string into
     * lines whether it uses DOS-style or UNIX-style line terminators. This is
     * equivalent to {@code Splitter.on(Pattern.compile(pattern))}.
     *
     * @param separatorPattern the pattern that determines whether a subsequence
     *     is a separator. This pattern may not match the empty string.
     * @return a splitter, with default settings, that uses this pattern
     * @throws IllegalArgumentException if {@code separatorPattern} matches the
     *      empty string or is a malformed expression
     */
    public static Splitter onPattern(String separatorPattern)
    {
        return on(Pattern.compile(separatorPattern));
    }

    /**
     * Returns a splitter that divides strings into pieces of the given length. For
     * example, {@code Splitter.onLength(2).split("abcde")} returns a stream
     * containing {@code ["ab", "cd", "e"]}. The last piece can be smaller than
     * {@code length} but will never be empty.
     * <p/>
     * <b>Exception:</b> for consistency with separator-based splitters, {@code
     * split("")} does not yield an empty stream, but a stream containing {@code ""}.
     * To avoid this behavior, use {@code omitEmpty}.
     *
     * @param length the desired length of pieces after splitting, a positive integer
     * @return a splitter, with default settings, that can split into fixed sized pieces
     * @throws IllegalArgumentException if {@code length} is zero or negative
     */
    public static Splitter onLength(int length)
    {
        Numbers.gt(0, length, "length");

        return new Splitter(
                new Strategy() {
                    @Override
                    public Spliterator<String> spliterator(Splitter splitter, CharSequence sequence)
                    {
                        return new LengthSpliterator(splitter, sequence, length);
                    }
                });
    }

    /**
     * Returns a splitter that behaves equivalently to {@code this} splitter, but
     * automatically omits empty strings from the results. For example, {@code
     * Splitter.on(',').omitEmpty().split(",a,,,b,c,,")} returns a stream
     * containing only {@code ["a", "b", "c"]}.
     * <p/>
     * If either {@code trim} option is also specified when creating a splitter,
     * that splitter always trims results first before checking for emptiness. So,
     * for example, {@code Splitter.on(':').omitEmpty().trim().split(": : : ")}
     * returns an empty stream.
     * <p/>
     * Note that it is ordinarily not possible for {@link #split(CharSequence)} to
     * return an empty stream, but when using this option, it can (if the input
     * sequence consists of nothing but separators).
     *
     * @return a splitter with the desired configuration
     */
    public Splitter omitEmpty()
    {
        return new Splitter(strategy, true, trimmer, limit);
    }

    /**
     * Returns a splitter that behaves equivalently to {@code this} splitter but stops
     * splitting after it reaches the limit. The limit defines the maximum number of
     * items returned by the stream.
     * <p/>
     * For example, {@code Splitter.on(',').limit(3).split("a,b,c,d")} returns a
     * stream containing {@code ["a", "b", "c"]}. When omitting empty strings,
     * the omitted strings do not count against the limit. Hence,
     * {@code Splitter.on(',').limit(3).omitEmpty().split("a,,,b,,,c,d")}
     * returns a stream containing {@code ["a", "b", "c"]}.

     *
     * @param maxItems the maximum number of items returned
     * @return a splitter with the desired configuration
     */
    public Splitter limit(int maxItems)
    {
        Numbers.gt(0, maxItems, "maxItems");
        return new Splitter(strategy, omitEmpty, trimmer, maxItems);
    }

    /**
     * Returns a splitter that behaves equivalently to {@code this} splitter, but
     * automatically removes leading and trailing {@linkplain CharMatcher#whitespace
     * whitespace} from each returned substring; equivalent to
     * {@code trim(CharMatcher.whitespace())}. For example,
     * {@code Splitter.on(',').trimResults().split(" a, b ,c ")} returns a stream
     * containing {@code ["a", "b", "c"]}.
     *
     * @return a splitter with the desired configuration
     */
    public Splitter trim()
    {
        return trim(CharMatcher.whitespace());
    }

    /**
     * Returns a splitter that behaves equivalently to {@code this} splitter, but
     * removes all leading or trailing characters matching the given {@code
     * CharMatcher} from each returned substring. For example,
     * {@code Splitter.on(',').trim(CharMatcher.is('_')).split("_a ,_b_ ,c__")}
     * returns a stream containing {@code ["a ", "b_ ", "c"]}.
     *
     * @param trimmer a {@link CharMatcher} that determines whether a character should
     *     be removed from the beginning/end of a subsequence
     * @return a splitter with the desired configuration
     */
    public Splitter trim(CharMatcher trimmer)
    {
        Objects.notNull(trimmer);
        return new Splitter(strategy, omitEmpty, trimmer, limit);
    }




    /**
     * Splits {@code sequence} into string components and makes them available through
     * an {@link Stream}, which may be lazily evaluated.
     *
     * @param sequence the sequence of characters to split
     * @return a stream over the segments split from the parameter
     */
    public Stream<String> split(CharSequence sequence)
    {
        Spliterator<String> spliterator = strategy.spliterator(this, sequence);
        return StreamSupport.stream(spliterator, spliterator.estimateSize() != Long.MAX_VALUE);
    }



    private interface Strategy
    {
        Spliterator<String> spliterator(Splitter splitter, CharSequence sequence);
    }


    private static class LengthSpliterator extends StringSpliterator {

        private final int length;
        private final Splitter splitter;

        private LengthSpliterator(Splitter splitter, CharSequence sequence, int length)
        {
            super(splitter, sequence);
            this.splitter = splitter;
            this.length = length;
        }

        @Override
        int separatorStart(int start)
        {
            int nextChunkStart = start + length;
            return (nextChunkStart < sequence.length() ? nextChunkStart : -1);
        }

        @Override
        int separatorEnd(int separatorIndex)
        {
            return separatorIndex;
        }

        @Override
        public long estimateSize()
        {
            return (sequence.length() - offset) / length;
        }

        // TODO One could argue if there were ever a string to split that needs to be parallelized?
        @Override
        public Spliterator<String> trySplit()
        {
            // TODO Adjust the length of each parallel segment based on some predefined segment LENGTH target
            // For example if a segment length was 1024 then I would return null if the remaining was less than that
            // However, if segment is a multiple of that I would possibly just split to that. Otherwise, I might
            // divide the remainder in half (segment size > 512)
            // segments are based on PARTS remaining or based on absolute length?
            // We would definitely want to make the segment equal to a multiple of the length
            int remaining = (sequence.length() - offset) / length;
            if(remaining > 6) {
                int start = offset;
                int end = offset + ((remaining / 2) * length);
                offset = end;
                return new LengthSpliterator(splitter, sequence.subSequence(start, end), length);
            }
            return null;
        }
    }

    private static abstract class StringSpliterator implements Spliterator<String> {
        final CharSequence sequence;
        final Strategy strategy;
        final CharMatcher trimmer;
        final boolean omitEmpty;

        private StringSpliterator(Splitter splitter, CharSequence sequence)
        {
            this.strategy = splitter.strategy;
            this.trimmer = splitter.trimmer;
            this.omitEmpty = splitter.omitEmpty;;
            this.limit = splitter.limit;;
            this.sequence = sequence;
        }


        abstract int separatorStart(int index);
        abstract int separatorEnd(int separatorIndex);


        int offset = 0;
        int limit = 0;

        @Override
        public boolean tryAdvance(Consumer<? super String> action)
        {
            int nextStart = offset;
            while (offset != -1 && limit > 0) {
                int start = nextStart;
                int end;

                int separatorPosition = separatorStart(offset);
                if (separatorPosition == -1) {
                    end = sequence.length();
                    offset = -1;
                } else {
                    end = separatorPosition;
                    offset = separatorEnd(separatorPosition);
                }
                if (offset == nextStart) {
                    /*
                     * This occurs when some pattern has an empty match, even if it doesn't match the empty
                     * string -- for example, if it requires lookahead or the like. The offset must be
                     * increased to look for separators beyond this point, without changing the start position
                     * of the next returned substring -- so nextStart stays the same.
                     */
                    offset++;
                    if (offset > sequence.length()) {
                        offset = -1;
                    }
                    continue;
                }


                while (start < end && trimmer.matches(sequence.charAt(start))) {
                    start++;
                }
                while (end > start && trimmer.matches(sequence.charAt(end - 1))) {
                    end--;
                }

                if (omitEmpty && start == end) {
                    // Don't include the (unused) separator in next split string.
                    nextStart = offset;
                    continue;
                }

                action.accept(sequence.subSequence(start, end).toString());
                limit --;
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<String> trySplit()
        {
            return null;
        }

        @Override
        public long estimateSize()
        {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics()
        {
            return Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.ORDERED;
        }

    }


}
