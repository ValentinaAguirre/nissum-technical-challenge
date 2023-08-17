package com.nissum.technical.challenge.users.utils;


import com.nissum.technical.challenge.users.model.dto.requests.UserRequests;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTGenerator {

    @Autowired
    private PropertyReader propertyReader;

    public String generateToken(UserRequests userRequests) {
        var jwtSecretKey = propertyReader.getJwtSecretKey();
        Key key = new SecretKeySpec(jwtSecretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDateTime = now.plusHours(3);

        Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setId(userRequests.getName())
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }
}
