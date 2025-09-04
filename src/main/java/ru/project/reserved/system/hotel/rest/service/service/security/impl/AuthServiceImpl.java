package ru.project.reserved.system.hotel.rest.service.service.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;
import ru.project.reserved.system.hotel.rest.service.exception.ServiceDbException;
import ru.project.reserved.system.hotel.rest.service.properties.TokenProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.service.security.AuthService;
import ru.project.reserved.system.hotel.rest.service.service.security.JwtService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RedisTemplate<UUID, Redis> redisTemplate;
    private final TokenProperties tokenProperties;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequest.getUsername(),
                userRequest.getPassword()));
        AuthUserRequestDto user = userService.getUser(userRequest.getUsername())
                .orElseThrow(() -> new ServiceDbException("User not found"));
        String jwt = jwtService.generateToken(user.getUsername(), user.getPassword());
        redisTemplate.opsForValue().set(UUID.randomUUID(), Redis.builder()
                .token(jwt)
                .build(), tokenProperties.getExpiration());
        return UserResponse.builder()
                .username(user.getUsername())
                .token(jwt)
                .build();
    }
}
