package edu.eci.ieti.greenwish.service.user;

import edu.eci.ieti.greenwish.controller.user.UserDto;
import edu.eci.ieti.greenwish.exception.UserNotFoundException;
import edu.eci.ieti.greenwish.repository.document.User;

import java.util.List;

/**
 * The UserService interface provides methods to manage user data.
 */
public interface UserService {

    /**
     * Saves a new user.
     *
     * @param user the user to be saved
     * @return the saved user
     */
    User save(UserDto user);

    /**
     * Finds a user by its ID.
     *
     * @param id the ID of the user to find
     * @return the found user
     * @throws UserNotFoundException if the user is not found
     */
    User findById(String id) throws UserNotFoundException;

    /**
     * Finds a user by its email.
     *
     * @param email the email of the user to find
     * @return the found user
     * @throws UserNotFoundException if the user is not found
     */
    User findByEmail(String email) throws UserNotFoundException;

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    List<User> all();

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @throws UserNotFoundException if the user is not found
     */
    void deleteById(String id) throws UserNotFoundException;

    /**
     * Updates a user by its ID.
     *
     * @param user the updated user data
     * @param id   the ID of the user to update
     * @throws UserNotFoundException if the user is not found
     */
    void update(UserDto user, String id) throws UserNotFoundException;

}
