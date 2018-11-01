package com.in28minutes.rest.webservices.restfulwebservices.user.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Adding the following @ResponseStatus annotation causes all exceptions in this class to return a 404 error
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
