package com.in28minutes.rest.webservices.restfulwebservices.exception;

import com.in28minutes.rest.webservices.restfulwebservices.user.errors.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// This class binds your custom exception handlers at the project level to the exception formatting created
// at the organization level (in this case, using the formatting in the ExceptionResponse class

@ControllerAdvice
@RestController
public class OrgNameExceptionHandler extends ResponseEntityExceptionHandler {

    // sets the default exception formatting for all exceptions and returns a 500 error
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest) {

        // format the exception response according to custom formatting from ExceptionResponse class
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));

        // return the exception response along with a 500 error
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // formats the custom UserNotFoundException handling from the User project and returns a 404 error
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(UserNotFoundException ex, WebRequest webRequest) {

        // format the exception response according to custom formatting from ExceptionResponse class
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), webRequest.getDescription(false));

        // return the exception response along with a 404 error
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // format the exception response according to custom formatting and pass along details of the bad request from the BindingResult
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().getAllErrors().toString());

        // return the exception response along with a 400 error
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

}
