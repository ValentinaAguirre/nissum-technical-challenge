package com.nissum.technical.challenge.users.service;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nissum.technical.challenge.users.exception.EmailAlreadyRegisteredException;
import com.nissum.technical.challenge.users.model.dto.requests.PhoneNumberRequests;
import com.nissum.technical.challenge.users.model.dto.requests.UserRequests;
import com.nissum.technical.challenge.users.model.dto.response.UserResponse;
import com.nissum.technical.challenge.users.model.entity.PhoneNumber;
import com.nissum.technical.challenge.users.model.entity.Users;
import com.nissum.technical.challenge.users.repository.UserRepository;
import com.nissum.technical.challenge.users.utils.JWTGenerator;
import com.nissum.technical.challenge.users.utils.PropertyReader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final ObjectMapper objectMapper;
    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final PropertyReader propertyReader;


    public UserService(JWTGenerator jwtGenerator, UserRepository userRepository, PropertyReader propertyReader) {
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
        this.propertyReader = propertyReader;

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public UserResponse createUser(UserRequests userRequests) throws EmailAlreadyRegisteredException {

        if (userRepository.existsByEmail(userRequests.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        if (!isValidEmail(userRequests.getEmail())) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido.");
        }

        if (!isValidPassword(userRequests.getPassword())) {
            throw new IllegalArgumentException("El formato de la contraseña es inválido.");
        }

        String token = jwtGenerator.generateToken(userRequests);
        Users users = saveUser(userRequests, token);

        return objectMapper.convertValue(users, UserResponse.class);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = propertyReader.getRegexPassword();
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Transactional
    private Users saveUser(UserRequests userRequests, String token) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        LocalDateTime currentDate = LocalDateTime.now();
        var users = new Users();

        users.setName(userRequests.getName());
        users.setEmail(userRequests.getEmail());
        users.setPassword(passwordEncoder.encode(userRequests.getPassword()));
        users.setPhones(getPhoneNumber(userRequests.getPhones()));
        users.setCreated(currentDate);
        users.setModified(currentDate);
        users.setLastLogin(currentDate);
        users.setToken(token);
        users.setActive(true);
        return userRepository.save(users);
    }

    private List<PhoneNumber> getPhoneNumber(List<PhoneNumberRequests> phones) {
        return phones.stream()
                .map(phoneDTO -> objectMapper.convertValue(phoneDTO, PhoneNumber.class))
                .collect(Collectors.toList());
    }

}
