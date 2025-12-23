package ru.project.reserved.system.hotel.rest.service.service.security.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.aop.Metric;
import ru.project.reserved.system.hotel.rest.service.dto.Role;
import ru.project.reserved.system.hotel.rest.service.dto.type.MetricType;
import ru.project.reserved.system.hotel.rest.service.properties.SecurityProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.MetricService;
import ru.project.reserved.system.hotel.rest.service.service.security.JwtService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtServiceImpl implements JwtService {

    private final SecurityProperties securityProperties;
    private final MetricService metricService;



    @Override
    public String extractUserName(String jwtToken) {
        Claims claims = decoderToken(jwtToken);
        return claims.getSubject();
    }


    @Override
    public String generateToken(String username, String password, List<Role> role) {
        return Jwts.builder()
                .header()
                .keyId(UUID.randomUUID().toString())
                .add(username, password)
                .add("roles", role)
                .and()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + securityProperties.getExpiration().toMillis()))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Claims decoderToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e){
            log.error("Token is expired: ", e);
            metricService.sendExceptionMetric(MetricType.NO_AUTH_USER, e, "Token is expired");
            throw new SignatureException(e.getMessage());
        }
    }

    @Override
    public JwsHeader getHeader(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getHeader();
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
            log.error("Invalid JWT signature: ", e);
            metricService.sendExceptionMetric(MetricType.NO_AUTH_USER, e, "Invalid JWT signature");
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: ", e);
            metricService.sendExceptionMetric(MetricType.NO_AUTH_USER, e, "Invalid JWT token");
            throw e;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: ", e);
            metricService.sendExceptionMetric(MetricType.NO_AUTH_USER, e, "Expired JWT token");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: ", e);
            metricService.sendExceptionMetric(MetricType.NO_AUTH_USER, e, "Unsupported JWT token");
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: ", e);
            metricService.sendExceptionMetric(MetricType.NO_AUTH_USER, e, "JWT claims string is empty");
            throw e;
        }
    }
}
