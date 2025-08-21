package ru.project.reserved.system.hotel.rest.service.service.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.exception.ServiceDbException;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.service.security.AppUserPrincipalService;

@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {

private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserRequestDto authUserRequestDto = userService.getUser(username)
                .orElseThrow(() -> new ServiceDbException("User not found"));
        return new AppUserPrincipalService(authUserRequestDto);
    }
}
