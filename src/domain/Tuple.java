package domain;

import java.util.Objects;

/**
 * Holds two values together, for example the IDs that identify a friendship.
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 */
public class Tuple<A, B> {
    private final A first;
    private final B second;

    public Tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Tuple<?, ?>)) {
            return false;
        }
        Tuple<?, ?> tuple = (Tuple<?, ?>) object;
        return Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
