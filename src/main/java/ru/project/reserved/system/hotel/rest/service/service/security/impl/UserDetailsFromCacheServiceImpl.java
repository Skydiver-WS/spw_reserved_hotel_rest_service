package ru.project.reserved.system.hotel.rest.service.service.security.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.service.security.JwtService;
import ru.project.reserved.system.hotel.rest.service.service.security.UserDetailsFromCacheService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailsFromCacheServiceImpl implements UserDetailsFromCacheService {

    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String jwt) {
        JwsHeader header = jwtService.getHeader(jwt);
        Claims claims = jwtService.decoderToken(jwt);
        List<Map<String, Object>> obj = (List<Map<String, Object>>) header.get("roles");
        String username = (String) claims.get("sub");
        String password = (String) header.get(username);
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return obj.stream().map(m ->
                        new SimpleGrantedAuthority((String) m.get("rolesType"))
                ).toList();
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getUsername() {
                return username;
            }
        };
    }
}
