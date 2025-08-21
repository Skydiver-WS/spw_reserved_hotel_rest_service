package ru.project.reserved.system.hotel.rest.service.service.security;


import io.jsonwebtoken.Claims;

public interface JwtService {

    String extractUserName(String jwtToken);

    Claims decoderToken(String token);

    boolean isValidToken(String token);

    String generateToken(String username, String password);


}
