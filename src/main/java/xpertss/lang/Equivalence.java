package xpertss.lang;

import java.io.Serializable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A strategy for determining whether two instances are considered equivalent, and for computing
 * hash codes in a manner consistent with that equivalence. Two examples of equivalences are the
 * {@linkplain #identity(Class)}  identity equivalence} and the {@linkplain #equals(Class)}
 * "equals" equivalence}.
 */
public class Equivalence<T> implements BiPredicate<T, T> {

    private static final Equivalence<?> IDENTITY = new Equivalence<>(new Identity());
    private static final Equivalence<?> EQUALS = new Equivalence<>(new Equals());

    /**
     * Returns an equivalence that delegates to {@link Object#equals} and {@link Object#hashCode}.
     * {@link Equivalence#equivalent} returns {@code true} if both values are null, or if neither
     * value is null and {@link Object#equals} returns {@code true}.
     * <p/>
     * {@link Equivalence#hash} returns {@code 0} if passed a null value.
     */
    public static <T> Equivalence<T> equals(Class<T> cls)
    {
        return Objects.cast(EQUALS);
    }

    /**
     * Returns an equivalence that uses {@code ==} to compare values and {@link
     * System#identityHashCode(Object)} to compute the hash code. {@link Equivalence#equivalent}
     * returns {@code true} if {@code a == b}, including in the case that a and b are both null.
     */
    public static <T> Equivalence<T> identity(Class<T> cls)
    {
        return Objects.cast(IDENTITY);
    }






    private final EquivalenceSpi impl;

    /** Constructor for use by subclasses. */
    private Equivalence(EquivalenceSpi impl)
    {
        this.impl = impl;
    }

    /**
     * Returns {@code true} if the given objects are considered equivalent.
     * <p/>
     * This method describes an <i>equivalence relation</i> on object references, meaning that
     * for all references {@code x}, {@code y}, and {@code z} (any of which may be null):
     * <p/>
     * <ul>
     *   <li>{@code equivalent(x, x)} is true (<i>reflexive</i> property)
     *   <li>{@code equivalent(x, y)} and {@code equivalent(y, x)} each return the same result
     *       (<i>symmetric</i> property)
     *   <li>If {@code equivalent(x, y)} and {@code equivalent(y, z)} are both true, then {@code
     *       equivalent(x, z)} is also true (<i>transitive</i> property)
     * </ul>
     * <p/>
     * Note that all calls to {@code equivalent(x, y)} are expected to return the same result as
     * long as neither {@code x} nor {@code y} is modified.
     */
    public final boolean equivalent(T a, T b)
    {
        if (a == b) return true;
        if (a == null || b == null) return false;
        return impl.doEquivalent(a, b);
    }

    @Override
    public boolean test(T a, T b)
    {
        return equivalent(a, b);
    }





    /**
     * Returns a hash code for {@code t}.
     * <p/>
     * The {@code hash} has the following properties:
     * <p/>
     * <ul>
     *   <li>It is <i>consistent</i>: for any reference {@code x}, multiple invocations of {@code
     *       hash(x}} consistently return the same value provided {@code x} remains unchanged
     *       according to the definition of the equivalence. The hash need not remain consistent from
     *       one execution of an application to another execution of the same application.
     *   <li>It is <i>distributable across equivalence</i>: for any references {@code x} and {@code
     *       y}, if {@code equivalent(x, y)}, then {@code hash(x) == hash(y)}. It is <i>not</i>
     *       necessary that the hash be distributable across <i>inequivalence</i>. If {@code
     *       equivalence(x, y)} is false, {@code hash(x) == hash(y)} may still be true.
     *   <li>{@code hash(null)} is {@code 0}.
     * </ul>
     */
    public final int hash(T t)
    {
        if (t == null) return 0;
        return impl.doHash(t);
    }










    public final Predicate<T> equivalentTo(T target)
    {
        return new EquivalentToPredicate<>(this, target);
    }











    private static interface EquivalenceSpi<T> {
        boolean doEquivalent(T a, T b);
        int doHash(T t);
    }

    private static final class Equals<T> implements EquivalenceSpi<T> {

        @Override
        public boolean doEquivalent(T a, T b)
        {
            return a.equals(b);
        }

        @Override
        public int doHash(T t)
        {
            return t.hashCode();
        }

    }

    private static final class Identity<T> implements EquivalenceSpi<T> {

        @Override
        public boolean doEquivalent(T a, T b)
        {
            return a == b;
        }

        @Override
        public int doHash(T o)
        {
            return System.identityHashCode(o);
        }
    }


    private static final class EquivalentToPredicate<T> implements Predicate<T>, Serializable {

        private final Equivalence<T> equivalence;
        private final T target;

        EquivalentToPredicate(Equivalence<T> equivalence, T target)
        {
            this.equivalence = Objects.notNull(equivalence, "equivalence");
            this.target = target;
        }

        @Override
        public boolean test(T input)
        {
            return equivalence.equivalent(input, target);
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (obj instanceof EquivalentToPredicate) {
                EquivalentToPredicate<?> that = (EquivalentToPredicate<?>) obj;
                return equivalence.equals(that.equivalence) && Objects.equal(target, that.target);
            }
            return false;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(equivalence, target);
        }

        @Override
        public String toString()
        {
            return equivalence + ".equivalentTo(" + target + ")";
        }

        private static final long serialVersionUID = 0;
    }

}
