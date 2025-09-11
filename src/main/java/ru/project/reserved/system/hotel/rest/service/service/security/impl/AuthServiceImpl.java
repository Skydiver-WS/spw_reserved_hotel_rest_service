package ru.project.reserved.system.hotel.rest.service.service.security.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;
import ru.project.reserved.system.hotel.rest.service.exception.ServiceDbException;
import ru.project.reserved.system.hotel.rest.service.properties.SecurityProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.service.security.AuthService;
import ru.project.reserved.system.hotel.rest.service.service.security.JwtService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RedisTemplate<UUID, Redis> redisTemplate;
    private final SecurityProperties securityProperties;

    @Override
    public UserResponse authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.getUsername(),
                userRequest.getPassword()));
        UserResponse user = userService.getUser(userRequest.getUsername())
                .orElseThrow(() -> new ServiceDbException("User not found"));
        String jwt = jwtService.generateToken(user.getUsername(), user.getPassword(), user.getRole());
        String idKey = jwtService.getHeader(jwt).getKeyId();
        redisTemplate.opsForValue().set(UUID.fromString(idKey), Redis.builder()
                .token(jwt)
                .build(), securityProperties.getExpiration());
        return UserResponse.builder()
                .username(user.getUsername())
                .token(jwt)
                .build();
    }
}
