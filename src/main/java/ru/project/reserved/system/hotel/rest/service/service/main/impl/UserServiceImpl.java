package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.properties.DbServiceRestProperties;
import ru.project.reserved.system.hotel.rest.service.properties.SecurityProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.RestService;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Profile("!stub && prod")
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestService restService;
    private final DbServiceRestProperties dbProp;
    private final SecurityProperties securityProp;

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("Get all users");
        UserResponse response = Optional.ofNullable(restService.sendData(createData(null),
                        UserResponse.class).getBody())
                .orElse(new UserResponse());
        return response.getUsers();
    }

    @Override
    public Optional<UserResponse> getUser(String username) {
        log.info("Get user in auth db");
        UserResponse user = restService.sendData(createData(UserRequest.builder()
                        .username(username)
                        .build()), UserResponse.class)
                .getBody();
        return Optional.ofNullable(user);
    }

    @Override
    public UserResponse createUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Create user");
        cryptoPassword(authUserRequestDto);
        ResponseEntity<UserResponse> resp = restService.sendData(createData(authUserRequestDto), UserResponse.class);
        return resp.getBody();
    }

    @Override
    public UserResponse updateUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Update user");
        ResponseEntity<UserResponse> resp = restService.sendData(createData(authUserRequestDto), UserResponse.class);
        return resp.getBody();
    }

    @Override
    public UserResponse deleteUser(String username) {
        log.info("Delete user");
        ResponseEntity<UserResponse> resp = restService.sendData(createData(UserRequest.builder()
                        .username(username)
                        .build()),
                UserResponse.class);
        return resp.getBody();
    }

    private RestDataDto createData(AuthUserRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return RestDataDto.builder()
                .headers(headers)
                .url(dbProp.getHostAuthDb() + getHttpAttributes().getRequestURI())
                .method(HttpMethod.valueOf(getHttpAttributes().getMethod()))
                .body(request)
                .build();
    }

    private void cryptoPassword(AuthUserRequestDto authUserRequestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(securityProp.getLevelCrypto());
        authUserRequestDto.setPassword(encoder.encode(authUserRequestDto.getPassword()));
    }

    private HttpServletRequest getHttpAttributes() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            log.error("ServletRequest is null");
            throw new RuntimeException();
        }
        return attributes.getRequest();
    }
}
