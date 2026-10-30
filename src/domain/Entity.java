package domain;

import java.util.Objects;

/**
 * Base class for domain objects identified by an ID.
 *
 * @param <ID> the type of the entity ID
 */
public abstract class Entity<ID> {
    private final ID id;

    protected Entity(ID id) {
        this.id = id;
    }

    public final ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Entity<?> entity = (Entity<?>) object;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
