package com.nissum.technical.challenge.users.service;

import com.nissum.technical.challenge.users.TestUtil;
import com.nissum.technical.challenge.users.exception.EmailAlreadyRegisteredException;
import com.nissum.technical.challenge.users.model.dto.requests.UserRequests;
import com.nissum.technical.challenge.users.model.dto.response.UserResponse;
import com.nissum.technical.challenge.users.model.entity.Users;
import com.nissum.technical.challenge.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @SpyBean
    private UserRepository userRepository;

    @Test
    void testShouldCreateUserWhenTheUserResponseIsValid() throws EmailAlreadyRegisteredException {
        UserRequests userRequests = TestUtil.buildUserRequest();
        UserResponse userResponse = userService.createUser(userRequests);
        assertNotNull(userResponse);
    }

    @Test
    void testShouldThrowEmailAlreadyRegisteredExceptionWhenEmailExists() {
        UserRequests userRequests = new UserRequests();
        userRequests.setEmail("johndoe@gmail.com");

        doReturn(true).when(userRepository).existsByEmail(anyString());

        assertThrows(EmailAlreadyRegisteredException.class, () -> userService.createUser(userRequests));
    }

    @Test
    void testShouldThrowIllegalArgumentExceptionWhenEmailIsInvalid() {
        UserRequests userRequests = new UserRequests();
        userRequests.setEmail("invalid_email");

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(userRequests));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenPasswordIsInvalid() {
        UserRequests userRequests = new UserRequests();
        userRequests.setEmail("email@email.com");
        userRequests.setPassword("invalid_password");

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> userService.createUser(userRequests));
        assertEquals("El formato de la contraseña es inválido.", exception.getMessage());
    }

    @Test
    void testShouldGetUsers() throws EmailAlreadyRegisteredException {
        UserRequests userRequests = TestUtil.buildUserRequest();
        userRequests.setEmail("johnwick@gmail.com");
        userService.createUser(userRequests);

        assertTrue(userService.getUsers().stream().anyMatch((Users users) -> users.getEmail().equals("johnwick@gmail.com")));
    }
}