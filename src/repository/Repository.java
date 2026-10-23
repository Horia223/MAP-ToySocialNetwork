package repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<ID, E> {
    E add(E entity);

    void remove(ID id);

    E findById(ID id);

    Collection<E> getAll();

}
