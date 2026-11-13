package repository;

import domain.Entity;
import java.util.Collection;
import java.util.Optional;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;



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
     * @return an Optional containing the entity, or an empty Optional if it does not exist
     * @throws IllegalArgumentException if the ID is null
     */
    Optional<E> findOne(ID id);

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


    /**
     *Returns all entities that match the given filter.
     *
     * @param filter the condition each entity must satisfy
     * @return a list containing the mathcing entities
     * #@throws NullPointerException if the filter is null
     */
    default List<E> findAll(Predicate<E> filter) {
        Objects.requireNonNull(filter, "Filter cannout be null");

        return getAll().stream()
                .filter(filter)
                .collect(Collectors.toList()); //makes the result into a list
    }
}