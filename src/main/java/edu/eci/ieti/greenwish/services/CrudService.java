package edu.eci.ieti.greenwish.services;

import java.util.List;

/**
 * Interface for generic CRUD operations service for a specific type.
 */
public interface CrudService<T, D, I, E extends Exception> {

    /**
     * Saves a given entity.
     *
     * @param entityDto must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     */
    T save(D entityDto);

    /**
     * Updates an entity of type S with the specified ID.
     *
     * @param entityDto the entity to be updated
     * @param id        the ID of the entity
     * @throws E if an error occurs during the update process
     */
    void update(D entityDto, I id) throws E;

    /**
     * Retrieves all entities of type T.
     *
     * @return a list containing all entities of type T
     */
    List<T> findAll();

    /**
     * Finds an entity by its identifier.
     *
     * @param id the identifier of the entity
     * @return the entity with the specified identifier
     * @throws E if an error occurs while finding the entity
     */
    T findById(I id) throws E;

    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to delete
     * @throws E if an error occurs while deleting the entity
     */
    void deleteById(I id) throws E;

}
