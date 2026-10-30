package repository;

import domain.Entity;
import exception.EntityNotFoundException;
import validation.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores entities in memory and validates them before saving or updating.
 */
public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private final Map<ID, E> entities = new HashMap<>();
    private final Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
    }

    @Override
    public E save(E entity) {
        validator.validate(entity);
        ID id = entity.getId();
        if (id == null) {
            throw new IllegalArgumentException("Entity ID cannot be null.");
        }
        if (entities.containsKey(id)) {
            throw new IllegalArgumentException("Entity with this ID already exists.");
        }
        entities.put(id, entity);
        return entity;
    }

    @Override
    public E delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        E entity = entities.remove(id);
        if (entity == null) {
            throw new EntityNotFoundException("Entity does not exist.");
        }
        return entity;
    }

    @Override
    public E findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        E entity = entities.get(id);
        if (entity == null) {
            throw new EntityNotFoundException("Entity does not exist.");
        }
        return entity;
    }

    @Override
    public E update(E entity) {
        validator.validate(entity);
        ID id = entity.getId();
        if (id == null) {
            throw new IllegalArgumentException("Entity ID cannot be null.");
        }
        if (!entities.containsKey(id)) {
            throw new EntityNotFoundException("Entity does not exist.");
        }
        entities.put(id, entity);
        return entity;
    }

    @Override
    public Collection<E> getAll() {
        return new ArrayList<>(entities.values());
    }
}
