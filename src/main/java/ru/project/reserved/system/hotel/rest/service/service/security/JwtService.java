package ru.project.reserved.system.hotel.rest.service.service.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import ru.project.reserved.system.hotel.rest.service.dto.Role;

import java.util.List;

public interface JwtService {

    String extractUserName(String jwtToken);

    Claims decoderToken(String token);

    JwsHeader getHeader(String token);

    boolean isValidToken(String token);

    String generateToken(String username, String password, List<Role> role);


}
