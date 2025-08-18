package ru.project.reserved.system.hotel.rest.service.service.security;


import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUserName(String jwtToken);

    boolean validateToken(String token, UserDetails userDetails);
}
