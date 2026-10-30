package repository;

import domain.Entity;
import java.util.Collection;

/**
 * Stores and retrieves entities.
 *
 * @param <ID> the type of entity ID
 * @param <E> the type of entity
 */
public interface Repository<ID, E extends Entity<ID>> {
    /**
     * Validates and saves a new entity.
     *
     * @param entity the entity to save
     * @return the saved entity
     * @throws exception.ValidationException if the entity is invalid
     * @throws IllegalArgumentException if its ID already exists
     */
    E save(E entity);

    /**
     * Deletes an entity by ID.
     *
     * @param id the ID of the entity
     * @return the deleted entity
     * @throws IllegalArgumentException if the ID is null
     * @throws exception.EntityNotFoundException if the entity does not exist
     */
    E delete(ID id);

    /**
     * Finds an entity by ID.
     *
     * @param id the ID of the entity
     * @return the found entity
     * @throws IllegalArgumentException if the ID is null
     * @throws exception.EntityNotFoundException if the entity does not exist
     */
    E findOne(ID id);

    /**
     * Replaces an existing entity with the same ID.
     *
     * @param entity the updated entity
     * @return the updated entity
     * @throws exception.ValidationException if the entity is invalid
     * @throws exception.EntityNotFoundException if the entity does not exist
     */
    E update(E entity);

    /**
     * Returns all stored entities.
     *
     * @return a collection of entities
     */
    Collection<E> getAll();
}