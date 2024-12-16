package xpertss.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharMatcherTest {

    @Test
    public void testMatchAny()
    {
        CharMatcher TEST = CharMatcher.any();

        assertTrue(TEST.matches('H'));
        assertTrue(TEST.matches('e'));
        assertTrue(TEST.matches('l'));
        assertTrue(TEST.matches('o'));
        assertTrue(TEST.matches('0'));
        assertTrue(TEST.matches('9'));
        assertTrue(TEST.matches('~'));
        assertTrue(TEST.matches('%'));
        assertTrue(TEST.matches('<'));
        assertTrue(TEST.matches('?'));
        assertTrue(TEST.matches(' '));
        assertTrue(TEST.matches('\t'));
        assertTrue(TEST.matches('\r'));
        assertTrue(TEST.matches('\n'));

        assertTrue(TEST.matches('\u001f'));
        assertTrue(TEST.matches('\u007f'));
        assertTrue(TEST.matches('\u009f'));
    }

    @Test
    public void testMatchNone()
    {
        CharMatcher TEST = CharMatcher.none();

        assertFalse(TEST.matches('H'));
        assertFalse(TEST.matches('e'));
        assertFalse(TEST.matches('l'));
        assertFalse(TEST.matches('o'));
        assertFalse(TEST.matches('0'));
        assertFalse(TEST.matches('9'));
        assertFalse(TEST.matches('~'));
        assertFalse(TEST.matches('%'));
        assertFalse(TEST.matches('<'));
        assertFalse(TEST.matches('?'));
        assertFalse(TEST.matches(' '));
        assertFalse(TEST.matches('\t'));
        assertFalse(TEST.matches('\r'));
        assertFalse(TEST.matches('\n'));

        assertFalse(TEST.matches('\u001f'));
        assertFalse(TEST.matches('\u007f'));
        assertFalse(TEST.matches('\u009f'));
    }

    @Test
    public void testMatchAscii()
    {
        CharMatcher TEST = CharMatcher.ascii();

        assertTrue(TEST.matches('H'));
        assertTrue(TEST.matches('e'));
        assertTrue(TEST.matches('l'));
        assertTrue(TEST.matches('o'));
        assertTrue(TEST.matches('0'));
        assertTrue(TEST.matches('9'));
        assertTrue(TEST.matches('~'));
        assertTrue(TEST.matches('%'));
        assertTrue(TEST.matches('<'));
        assertTrue(TEST.matches('?'));
        assertTrue(TEST.matches(' '));

        assertTrue(TEST.matches('\t'));
        assertTrue(TEST.matches('\r'));
        assertTrue(TEST.matches('\n'));

        assertTrue(TEST.matches('\u001f'));
        assertTrue(TEST.matches('\u007f'));

        assertFalse(TEST.matches('\u0080'));
        assertFalse(TEST.matches('\u0177'));
        assertFalse(TEST.matches('\u009f'));
        assertFalse(TEST.matches('\u0201'));
        assertFalse(TEST.matches('\u0a9f'));
    }

    @Test
    public void testMatchJavaIso()
    {
        CharMatcher TEST = CharMatcher.javaIsoControl();

        assertFalse(TEST.matches('H'));
        assertFalse(TEST.matches('e'));
        assertFalse(TEST.matches('l'));
        assertFalse(TEST.matches('o'));
        assertFalse(TEST.matches('0'));
        assertFalse(TEST.matches('9'));
        assertFalse(TEST.matches('~'));
        assertFalse(TEST.matches('%'));
        assertFalse(TEST.matches('<'));
        assertFalse(TEST.matches('?'));
        assertFalse(TEST.matches(' '));

        assertTrue(TEST.matches('\t'));
        assertTrue(TEST.matches('\r'));
        assertTrue(TEST.matches('\n'));

        assertTrue(TEST.matches('\u001f'));
        assertTrue(TEST.matches('\u007f'));
        assertTrue(TEST.matches('\u009f'));
    }

    @Test
    public void testIs()
    {
        CharMatcher TEST = CharMatcher.is('A');

        assertTrue(TEST.matches('A'));
        assertFalse(TEST.matches('a'));
        assertFalse(TEST.matches(' '));

    }

    @Test
    public void testIsNot()
    {
        CharMatcher TEST = CharMatcher.isNot('A');

        assertFalse(TEST.matches('A'));
        assertTrue(TEST.matches('a'));
        assertTrue(TEST.matches(' '));

    }

    @Test
    public void testIsEither()
    {
        CharMatcher TEST = CharMatcher.isEither('A','a');

        assertTrue(TEST.matches('A'));
        assertTrue(TEST.matches('a'));
        assertFalse(TEST.matches(' '));

    }

    @Test
    public void testAnyOf()
    {
        CharMatcher TEST = CharMatcher.anyOf('A','a', 'b', 'B','8');

        assertTrue(TEST.matches('A'));
        assertTrue(TEST.matches('a'));
        assertTrue(TEST.matches('B'));
        assertTrue(TEST.matches('b'));
        assertTrue(TEST.matches('8'));
        assertFalse(TEST.matches(' '));
        assertFalse(TEST.matches('7'));
        assertFalse(TEST.matches('9'));
    }

    @Test
    public void testAnyOfSequence()
    {
        CharMatcher TEST = CharMatcher.anyOf("a8BAb");

        assertTrue(TEST.matches('A'));
        assertTrue(TEST.matches('a'));
        assertTrue(TEST.matches('B'));
        assertTrue(TEST.matches('b'));
        assertTrue(TEST.matches('8'));
        assertFalse(TEST.matches(' '));
        assertFalse(TEST.matches('7'));
        assertFalse(TEST.matches('9'));
    }

    @Test
    public void testNoneOfSequence()
    {
        CharMatcher TEST = CharMatcher.noneOf("a8BAb");

        assertFalse(TEST.matches('A'));
        assertFalse(TEST.matches('a'));
        assertFalse(TEST.matches('B'));
        assertFalse(TEST.matches('b'));
        assertFalse(TEST.matches('8'));
        assertTrue(TEST.matches(' '));
        assertTrue(TEST.matches('7'));
        assertTrue(TEST.matches('9'));
    }

    @Test
    public void testInRange()
    {
        CharMatcher TEST = CharMatcher.inRange('A','Z');

        assertTrue(TEST.matches('A'));
        assertTrue(TEST.matches('F'));
        assertTrue(TEST.matches('I'));
        assertTrue(TEST.matches('Z'));

        assertFalse(TEST.matches('a'));
        assertFalse(TEST.matches('z'));
        assertFalse(TEST.matches('$'));
        assertFalse(TEST.matches(' '));

    }

    @Test
    public void testOr()
    {
        CharMatcher UPPER = CharMatcher.inRange('A','Z');
        CharMatcher LOWER = CharMatcher.inRange('a','z');
        CharMatcher TEST = UPPER.or(LOWER);

        assertTrue(TEST.matches('A'));
        assertTrue(TEST.matches('Z'));
        assertTrue(TEST.matches('a'));
        assertTrue(TEST.matches('z'));
        assertTrue(TEST.matches('g'));

        assertFalse(TEST.matches('$'));
        assertFalse(TEST.matches('-'));
        assertFalse(TEST.matches(' '));
    }

    @Test
    public void testAnd()
    {
        CharMatcher FIRST = CharMatcher.inRange('B','F');
        CharMatcher SECOND = CharMatcher.inRange('D','H');
        CharMatcher TEST = FIRST.and(SECOND);

        assertTrue(TEST.matches('D'));
        assertTrue(TEST.matches('E'));
        assertTrue(TEST.matches('F'));

        assertFalse(TEST.matches('A'));
        assertFalse(TEST.matches('C'));
        assertFalse(TEST.matches('G'));
        assertFalse(TEST.matches('H'));
        assertFalse(TEST.matches(' '));
        assertFalse(TEST.matches('$'));

    }

    @Test
    public void testMatchesAny()
    {
        CharMatcher TEST = CharMatcher.inRange('B', 'F');

        assertTrue(TEST.matchesAnyOf("BOAT"));
        assertTrue(TEST.matchesAnyOf("FRED"));
        assertTrue(TEST.matchesAnyOf("DANG"));
        assertFalse(TEST.matchesAnyOf("HAY"));
    }

    @Test
    public void testMatchesAllOf()
    {
        CharMatcher TEST = CharMatcher.inRange('A', 'D');

        assertTrue(TEST.matchesAllOf("BAD"));
        assertFalse(TEST.matchesAllOf("FRED"));
        assertFalse(TEST.matchesAllOf("DANG"));
        assertFalse(TEST.matchesAllOf("BASS"));
    }

    @Test
    public void testIndexIn()
    {
        CharMatcher TEST = CharMatcher.inRange('A', 'D');
        assertEquals(0, TEST.indexIn("Bad", 0));
        assertEquals(-1, TEST.indexIn("Bad", 1));
        assertEquals(1, TEST.indexIn("DAD", 1));
        assertEquals(2, TEST.indexIn("DAD", 2));
    }

    @Test
    public void testRemoveFrom()
    {
        assertEquals("bzr", CharMatcher.is('a').removeFrom("bazaar"));
        assertEquals("Crs", CharMatcher.inRange('h', 'i').removeFrom("Chris"));
    }

}