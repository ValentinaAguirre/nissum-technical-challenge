package com.nissum.technical.challenge.users.controller;

import com.nissum.technical.challenge.users.exception.EmailAlreadyRegisteredException;
import com.nissum.technical.challenge.users.model.dto.requests.UserRequests;
import com.nissum.technical.challenge.users.model.dto.response.UserResponse;
import com.nissum.technical.challenge.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users Controller", description = "This controller provides operations for managing users.")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Creates a new user.", description = "This operation creates a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Unexpected system error")})
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Parameter(name = "UserRequests", description = "User data to be created")
                                                   @RequestBody UserRequests userRequests) throws EmailAlreadyRegisteredException {
        UserResponse createdUser = userService.createUser(userRequests);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

}
