package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.properties.DbServiceRestProperties;
import ru.project.reserved.system.hotel.rest.service.properties.SecurityProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

import java.util.List;
import java.util.Optional;

@Service
@Profile("!stub && prod")
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ProxyService proxyService;
    private final DbServiceRestProperties dbProp;
    private final SecurityProperties securityProp;

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("Get all users");
        UserResponse response = Optional.ofNullable((UserResponse) proxyService.proxyOperation(null, UserResponse.class))
                .orElse(new UserResponse());
        return response.getUsers();
    }

    @Override
    public Optional<UserResponse> getUser(String username) {
        log.info("Get user in auth db");
        UserResponse user = (UserResponse) proxyService.proxyOperation(UserRequest.builder()
                .username(username)
                .build(), UserResponse.class);
        return Optional.ofNullable(user);
    }

    @Override
    public UserResponse createUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Create user");
        cryptoPassword(authUserRequestDto);
        return (UserResponse) proxyService.proxyOperation(authUserRequestDto, UserResponse.class);
    }

    @Override
    public UserResponse updateUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Update user");
        return (UserResponse) proxyService.proxyOperation(authUserRequestDto, UserResponse.class);
    }

    @Override
    public UserResponse deleteUser(String username) {
        log.info("Delete user");
        return (UserResponse) proxyService.proxyOperation(UserRequest.builder()
                .username(username)
                .build(), UserResponse.class);
    }

    private void cryptoPassword(AuthUserRequestDto authUserRequestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(securityProp.getLevelCrypto());
        authUserRequestDto.setPassword(encoder.encode(authUserRequestDto.getPassword()));
    }
}
