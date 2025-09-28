package xpertss.lang;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class EquivalenceTest {

    @Test
    public void testBasicIdentity()
    {
        Integer a = new Integer(10);
        Integer b = new Integer(10);
        Equivalence<Integer> identity = Equivalence.identity(Integer.class);

        assertFalse(identity.equivalent(a, b));
        assertNotEquals(identity.hash(a), identity.hash(b));

        assertTrue(identity.equivalent(a, a));
        assertEquals(identity.hash(a), identity.hash(a));
    }

    @Test
    public void testBasicEquals()
    {
        Integer a = new Integer(10);
        Integer b = new Integer(10);
        Integer c = new Integer(25);
        Equivalence<Integer> equals = Equivalence.equals(Integer.class);
        assertTrue(equals.equivalent(a,b));
        assertFalse(equals.equivalent(a,c));
        assertEquals(equals.hash(a), equals.hash(b));
        assertNotEquals(equals.hash(a), equals.hash(c));
    }

    @Test
    public void testEquivalentTo()
    {
        Integer a = new Integer(10);
        Predicate<Integer> equals = Equivalence.equals(Integer.class).equivalentTo(a);

        assertTrue(equals.test(new Integer(10)));
        assertTrue(equals.test(10));
        assertFalse(equals.test(11));

        Predicate<Integer> identity = Equivalence.identity(Integer.class).equivalentTo(a);
        assertFalse(identity.test(new Integer(10)));
        assertFalse(identity.test(10));
        assertFalse(identity.test(11));
        assertTrue(identity.test(a));


    }

}