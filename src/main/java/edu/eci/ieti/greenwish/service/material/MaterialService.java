package edu.eci.ieti.greenwish.service.material;

import edu.eci.ieti.greenwish.controller.material.MaterialDto;
import edu.eci.ieti.greenwish.exception.MaterialNotFoundException;
import edu.eci.ieti.greenwish.repository.document.Material;

import java.util.List;

/**
 * The MaterialService interface provides methods to manage materials.
 */
public interface MaterialService {

    /**
     * Saves a new material.
     *
     * @param materialDto The material data transfer object.
     * @return The saved material.
     */
    Material save(MaterialDto materialDto);

    /**
     * Finds a material by its ID.
     *
     * @param id The ID of the material to find.
     * @return The found material.
     * @throws MaterialNotFoundException If the material with the given ID does not
     *                                   exist.
     */
    Material findById(String id) throws MaterialNotFoundException;

    /**
     * Retrieves all materials.
     *
     * @return A list of all materials.
     */
    List<Material> all();

    /**
     * Deletes a material by its ID.
     *
     * @param id The ID of the material to delete.
     * @throws MaterialNotFoundException If the material with the given ID does not
     *                                   exist.
     */
    void deleteById(String id) throws MaterialNotFoundException;

    /**
     * Updates a material with new data.
     *
     * @param material The updated material data transfer object.
     * @param id       The ID of the material to update.
     * @throws MaterialNotFoundException If the material with the given ID does not
     *                                   exist.
     */
    void update(MaterialDto material, String id) throws MaterialNotFoundException;

}
