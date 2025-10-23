package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.properties.DbServiceRestProperties;
import ru.project.reserved.system.hotel.rest.service.properties.SecurityProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRq;
import ru.project.reserved.system.hotel.rest.service.web.response.UserRs;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("!stub && prod")
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ProxyService proxyService;
    private final DbServiceRestProperties dbProp;
    private final SecurityProperties securityProp;

    @Override
    public List<UserRs> getAllUsers() {
        log.info("Get all users");
        UserRs response = Optional.ofNullable((UserRs) proxyService.proxyOperation(null, UserRs.class))
                .orElse(new UserRs());
        return response.getUsers();
    }

    @Override
    public Optional<UserRs> getUser(String username) {
        log.info("Get user in auth db");
        UserRs user = (UserRs) proxyService.proxyOperation(UserRq.builder()
                .username(username)
                .build(), UserRs.class);
        return Optional.ofNullable(user);
    }

    @Override
    public UserRs createUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Create user");
        cryptoPassword(authUserRequestDto);
        return (UserRs) proxyService.proxyOperation(authUserRequestDto, UserRs.class);
    }

    @Override
    public UserRs updateUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Update user");
        return (UserRs) proxyService.proxyOperation(authUserRequestDto, UserRs.class);
    }

    @Override
    public UserRs deleteUser(String username) {
        log.info("Delete user");
        return (UserRs) proxyService.proxyOperation(UserRq.builder()
                .username(username)
                .build(), UserRs.class);
    }

    private void cryptoPassword(AuthUserRequestDto authUserRequestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(securityProp.getLevelCrypto());
        authUserRequestDto.setPassword(encoder.encode(authUserRequestDto.getPassword()));
    }
}
