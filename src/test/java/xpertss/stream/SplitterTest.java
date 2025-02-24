package xpertss.stream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SplitterTest {


    @Test
    public void testSplit()
    {
        Stream<String> stream = Splitter.on(',').split("foo,bar,qux");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(3, results.size());
        assertEquals("foo", results.get(0));
        assertEquals("bar", results.get(1));
        assertEquals("qux", results.get(2));
    }

    @Test
    public void testSplitNoTrim()
    {
        Stream<String> stream = Splitter.on(',').split("foo , bar , qux");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(3, results.size());
        assertEquals("foo ", results.get(0));
        assertEquals(" bar ", results.get(1));
        assertEquals(" qux", results.get(2));
    }

    @Test
    public void testSplitWithTrim()
    {
        Stream<String> stream = Splitter.on(',').trim().split("foo , bar , qux");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(3, results.size());
        assertEquals("foo", results.get(0));
        assertEquals("bar", results.get(1));
        assertEquals("qux", results.get(2));
    }

    @Test
    public void testSplitWithTrimAndOmitEmpty()
    {
        Stream<String> stream = Splitter.on(',').trim().omitEmpty().split("foo ,,bar, qux");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(3, results.size());
        assertEquals("foo", results.get(0));
        assertEquals("bar", results.get(1));
        assertEquals("qux", results.get(2));
    }

    @Test
    public void testSplitAndOmitEmpty()
    {
        Stream<String> stream = Splitter.on(',').omitEmpty().split("foo ,,bar, qux");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(3, results.size());
        assertEquals("foo ", results.get(0));
        assertEquals("bar", results.get(1));
        assertEquals(" qux", results.get(2));
    }

    @Test
    public void testSplitLimitToTwo()
    {
        Stream<String> stream = Splitter.on(',').limit(2).split("foo,bar,qux");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(2, results.size());
        assertEquals("foo", results.get(0));
        assertEquals("bar", results.get(1));
    }

    @Test
    public void testSplitLimitToTwoWithOmitEmpty()
    {
        Stream<String> stream = Splitter.on(',').omitEmpty().limit(2).split("foo,,bar,qux");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(2, results.size());
        assertEquals("foo", results.get(0));
        assertEquals("bar", results.get(1));
    }


    @Test
    public void testSplitOnLengthShort()
    {
        Stream<String> stream = Splitter.onLength(2).split("abcde");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(3, results.size());
        assertEquals("ab", results.get(0));
        assertEquals("cd", results.get(1));
        assertEquals("e", results.get(2));
    }


    @Test
    public void testSplitOnLengthFull()
    {
        Stream<String> stream = Splitter.onLength(2).split("abcdefghijklmnopqrstuvwxyz");
        List<String> results = stream.collect(Collectors.toList());
        assertEquals(13, results.size());
        assertEquals("ab", results.get(0));
        assertEquals("cd", results.get(1));
        assertEquals("ef", results.get(2));
        assertEquals("op", results.get(7));
        assertEquals("yz", results.get(12));
    }


}