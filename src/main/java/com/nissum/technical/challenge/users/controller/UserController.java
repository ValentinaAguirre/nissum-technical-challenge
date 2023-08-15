package com.nissum.technical.challenge.users.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Users Controller", description = "This controller provides operations for managing users.")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/hello")
    public String getHello() {
        return "Hello World";
    }
}
