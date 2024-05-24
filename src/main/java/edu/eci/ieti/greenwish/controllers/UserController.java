package edu.eci.ieti.greenwish.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.ieti.greenwish.exceptions.UserNotFoundException;
import edu.eci.ieti.greenwish.models.User;
import edu.eci.ieti.greenwish.models.dto.UserDto;
import edu.eci.ieti.greenwish.services.UserService;
import lombok.RequiredArgsConstructor;

/**
 * The UserController class handles HTTP requests related to user operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users.
     *
     * @return a ResponseEntity containing a list of User objects
     */
    @GetMapping
    public ResponseEntity<List<User>> all() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing the User object if found, or a not found
     *         response if not found
     */
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new user.
     *
     * @param userDto the UserDto object containing the user data
     * @return a ResponseEntity containing the created User object and the location
     *         of the new resource
     */
    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        User savedUser = userService.save(userDto);
        URI location = URI.create(String.format("/users/%s", savedUser.getId()));
        return ResponseEntity.created(location).body(savedUser);
    }

    /**
     * Updates an existing user.
     *
     * @param id      the ID of the user to update
     * @param userDto the UserDto object containing the updated user data
     * @return a ResponseEntity with the HTTP status indicating the success of the
     *         update operation
     */
    @PutMapping("{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        try {
            userService.update(userDto, id);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity with the HTTP status indicating the success of the
     *         delete operation
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
