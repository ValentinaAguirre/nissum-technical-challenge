package com.nissum.technical.challenge.users.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertyReader {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${regular.expression.password}")
    private String regexPassword;

}