package com.in28minutes.rest.webservices.restfulwebservices.user.controllers;

import com.in28minutes.rest.webservices.restfulwebservices.user.dao.UserDaoService;
import com.in28minutes.rest.webservices.restfulwebservices.user.domain.User;
import com.in28minutes.rest.webservices.restfulwebservices.user.errors.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.user.repositories.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.user.repositories.UserRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    private UserDaoService userDaoService;

    private UserRepository userRepository;

    private PostRepository postRepository;

    public UserJPAResource(UserDaoService userDaoService, UserRepository userRepository, PostRepository postRepository) {
        this.userDaoService = userDaoService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

// HATEOS (Hypermedia As The Engine Of Application State)

    // retrieve all users
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public Resource<User> findUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);

        // if user not found, throw custom 404 (not found) error
        if(!user.isPresent()) throw new UserNotFoundException("id-" + id);

        // add related link "all-users" to the JSON response
        // "all-users",  SERVER_PATH + "/users"

        // Create a new Resource of type "User"
        Resource<User> resource = new Resource<User>(user.get());

        // Get the link from the "retrieveAllUsers" method of this service
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        // add the link "all-users" to the resource
        resource.add(linkTo.withRel("all-users"));

        // return the user object plus the new link
        return resource;

    }


    // retrieve user from id

    // input details of new user
    // output CREATED if successful and return created URI
    // @RequestBody maps the REST request to the User entity
    @PostMapping("/jpa/users")
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        // build the response REST URI from the saved user
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        // Return "CREATED" status to the response URI
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);

    }

}
