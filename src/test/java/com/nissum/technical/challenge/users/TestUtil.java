package com.nissum.technical.challenge.users;


import com.nissum.technical.challenge.users.model.dto.requests.PhoneNumberRequests;
import com.nissum.technical.challenge.users.model.dto.requests.UserRequests;
import com.nissum.technical.challenge.users.model.dto.response.UserResponse;
import com.nissum.technical.challenge.users.model.entity.Users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TestUtil {


    public static UserResponse buildUserResponse() {
        LocalDateTime currentDate = LocalDateTime.now();
        return new UserResponse(UUID.randomUUID(), currentDate, currentDate, currentDate, "token", true);
    }

    public static UserRequests buildUserRequest() {
        return new UserRequests(UUID.randomUUID(), "name", "email@Email.com", "Nissum123", List.of(buildPhoneNumberRequest()),
                null, null, null, null, true);
    }

    public static PhoneNumberRequests buildPhoneNumberRequest() {
        return new PhoneNumberRequests( null, "2559433", "4", "57");
    }

    public static Users buildUser() {
        return new Users(UUID.randomUUID(), "John Doe", "john@example.com");
    }
}
