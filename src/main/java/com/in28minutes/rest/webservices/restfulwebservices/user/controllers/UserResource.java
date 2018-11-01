package com.in28minutes.rest.webservices.restfulwebservices.user.controllers;

import com.in28minutes.rest.webservices.restfulwebservices.user.dao.UserDaoService;
import com.in28minutes.rest.webservices.restfulwebservices.user.domain.User;
import com.in28minutes.rest.webservices.restfulwebservices.user.errors.UserNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserResource {

    private UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    // HATEOS (Hypermedia As The Engine Of Application State)

    // retrieve all users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> findUser(@PathVariable int id) {

        User user = userDaoService.findOne(id);

        // if user not found, throw custom 404 (not found) error
        if(user==null) throw new UserNotFoundException("id-" + id);

        // add related link "all-users" to the JSON response
        // "all-users",  SERVER_PATH + "/users"

        // Create a new Resource of type "User"
        Resource<User> resource = new Resource<User>(user);

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
    @PostMapping("/users")
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);

        // build the response REST URI from the saved user
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        // Return "CREATED" status to the response URI
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userDaoService.deleteById(id);

        if(user == null) throw new UserNotFoundException("id-" + id);

    }

}
