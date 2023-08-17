package com.nissum.technical.challenge.users.model.dto.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequests {

    @Schema(description = "Id", hidden = true)
    private UUID id;

    @Schema(description = "Name", example = "John Doe")
    private String name;

    @Schema(description = "Email", example = "john@doe.com")
    private String email;

    @Schema(description = "Password", example = "Pass123")
    private String password;

    @Schema(description = "Phones")
    private List<PhoneNumberRequests> phones;

    @Schema(description = "Creation date", hidden = true)
    private LocalDateTime created;

    @Schema(description = "Modification date",hidden = true)
    private LocalDateTime modified;

    @Schema(description = "Last login date", hidden = true)
    private LocalDateTime lastLogin;

    @Schema(description = "Token", hidden = true)
    private String token;

    @Schema(description = "User status", hidden = true)
    private boolean isActive;

}