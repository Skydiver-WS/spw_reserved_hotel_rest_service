package ru.project.reserved.system.hotel.rest.service.service.security.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.exception.ServiceDbException;
import ru.project.reserved.system.hotel.rest.service.mapper.UserMapper;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.service.security.AppUserPrincipalService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username {}", username);
        AuthUserRequestDto authUserRequestDto = userMapper
                .authUserRequestFromUserResponse(userService.getUser(username)
                .orElseThrow(() -> new ServiceDbException("User not found")));
        return new AppUserPrincipalService(authUserRequestDto);
    }
}
