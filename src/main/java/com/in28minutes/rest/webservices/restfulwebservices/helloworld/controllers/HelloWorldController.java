package com.in28minutes.rest.webservices.restfulwebservices.helloworld.controllers;

import com.in28minutes.rest.webservices.restfulwebservices.helloworld.domain.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // method to return "Hello World"
    // GET "Hello World" URI ("/hello-world")
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // GET mapping to return a POJO in JSON format
    // When the "RestController" annotation is used, POJOs are automatically converted to JSON format
    // in this case, the String "message" is the only property of the HelloWorldBean object
    // so, using Getters and Setters, Jackson will use that property to create JSON
    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    // using "String.format" to replace %s with the PathVariable "name"
    // that string is then sent to the HelloWorldBean and returned as a JSON object
    @GetMapping("/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    // method to return "Hello World"
    // GET "Hello World" URI ("/hello-world")
    @GetMapping("/hello-world-i18n")
    public String helloWorldIntl(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
}
