package repository;

import domain.Entity;
import java.util.Collection;

public interface Repository<ID, E extends Entity<ID>> {
    E save(E entity);

    E delete(ID id);

    E findOne(ID id);

    E update(E entity);

    Collection<E> getAll();
}
