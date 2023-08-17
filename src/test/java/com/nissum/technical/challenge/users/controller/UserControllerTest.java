package com.nissum.technical.challenge.users.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nissum.technical.challenge.users.TestUtil;
import com.nissum.technical.challenge.users.model.dto.requests.UserRequests;
import com.nissum.technical.challenge.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void testShouldCreateUserWithValidData() throws Exception {
        var userDTO = TestUtil.buildUserRequest();
        when(userService.createUser(any(UserRequests.class))).thenReturn(TestUtil.buildUserResponse());
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    void testShouldReturnTheServiceValueWith200StatusCode() throws Exception {
        when(userService.getUsers()).thenReturn(List.of(TestUtil.buildUser()));
        mockMvc.perform(get("/api/users")).andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void testShouldReturnTheServiceValueWith204StatusCode() throws Exception {
        when(userService.getUsers()).thenReturn(List.of());
        mockMvc.perform(get("/api/users")).andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }
}