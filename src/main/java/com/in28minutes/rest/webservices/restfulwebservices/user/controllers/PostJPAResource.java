package com.in28minutes.rest.webservices.restfulwebservices.user.controllers;

import com.in28minutes.rest.webservices.restfulwebservices.user.domain.Post;
import com.in28minutes.rest.webservices.restfulwebservices.user.domain.User;
import com.in28minutes.rest.webservices.restfulwebservices.user.errors.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.user.repositories.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.user.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostJPAResource {

    private UserRepository userRepository;

    private PostRepository postRepository;

    public PostJPAResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPostsByUserId(@PathVariable int id) {

        // optional is necessary when using JPA to retrieve data from dB
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User Not Found with: id=" + id);
        }

        return userOptional.get().getPosts();

    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {

        // optional is necessary when using JPA to retrieve data from dB
        Optional<User> userOptional = userRepository.findById(id);

        // if no user found, throw an exception
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User Not Found with: id=" + id);
        }

        // since a user WAS found, get it and call it "user"
        User user = userOptional.get();

        // set the user for the new post
        post.setUser(user);

        // save the new post
        postRepository.save(post);

        // build the response REST URI from the saved post
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        // Return "CREATED" status to the response URI
        return ResponseEntity.created(location).build();

    }
}
