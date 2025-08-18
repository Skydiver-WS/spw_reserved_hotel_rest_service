package ru.project.reserved.system.hotel.rest.service.service.security.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import ru.project.reserved.system.hotel.rest.service.service.security.JwtService;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${token.signing.key}")
    private String secretToken;

    @Value("${token.expiration}")
    private Duration expirationToken;

    @Override
    public String extractUserName(String jwtToken) {
        return "";
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return false;
    }

    private String generateToken(String username, String password) {
        return Jwts.builder()
                .subject(username + " " + password)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expirationToken.toMillis()))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        //  byte[] keyBytes = Decoders.BASE64.decode(secretToken);
        return Jwts.SIG.HS512.key().build();
    }
}
