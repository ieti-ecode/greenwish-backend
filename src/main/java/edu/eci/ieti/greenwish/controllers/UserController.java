package edu.eci.ieti.greenwish.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.eci.ieti.greenwish.exceptions.ImageReadException;
import edu.eci.ieti.greenwish.models.domain.User;
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
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * Retrieves the image associated with the specified user ID.
     *
     * @param id The ID of the user.
     * @return The byte array representing the image.
     */
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable("id") String id) {
        return userService.getImage(id);
    }

    /**
     * Creates a new user.
     *
     * @param userDto the UserDto object containing the user data
     * @return a ResponseEntity containing the created User object and the location
     *         of the new resource
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDto userDto) {
        User savedUser = userService.save(userDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Uploads an image for a user with the specified ID.
     *
     * @param id    The ID of the user.
     * @param image The image file to be uploaded.
     * @return A ResponseEntity with a success status code if the image was uploaded
     *         successfully.
     * @throws ImageReadException If an error occurs while reading the image file.
     */
    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable("id") String id, @RequestParam("image") MultipartFile image) {
        try {
            userService.uploadImage(id, image);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new ImageReadException(e.getMessage());
        }
    }

    /**
     * Updates an existing user.
     *
     * @param id      the ID of the user to update
     * @param userDto the UserDto object containing the updated user data
     * @return a ResponseEntity with the HTTP status indicating the success of the
     *         update operation
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        userService.update(userDto, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity with the HTTP status indicating the success of the
     *         delete operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
