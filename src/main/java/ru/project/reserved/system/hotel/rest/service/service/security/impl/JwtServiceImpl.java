package ru.project.reserved.system.hotel.rest.service.service.security.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.service.security.JwtService;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
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
    public String generateToken(String username, String password) {
        return Jwts.builder()
                .header()
                .keyId(UUID.randomUUID().toString())
                .add(username, password)
                .and()
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

    @Override
    public Claims decoderToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            log.info("Check valid token");
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);
            log.info("Token is valid");
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
