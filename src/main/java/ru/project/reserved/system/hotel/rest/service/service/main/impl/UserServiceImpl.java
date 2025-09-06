package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.mapper.UserMapper;
import ru.project.reserved.system.hotel.rest.service.properties.DbServiceRestProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.RestService;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Profile("!stub")
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final RestService restService;
    private final DbServiceRestProperties dbProp;

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
    public boolean createUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Create user");
        ResponseEntity<Boolean> resp = restService.sendData(createData(authUserRequestDto), Boolean.class);
        return resp.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean updateUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Update user");
        ResponseEntity<Boolean> resp = restService.sendData(createData(authUserRequestDto), Boolean.class);
        return resp.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean deleteUser(String username) {
        log.info("Delete user");
        ResponseEntity<Boolean> resp = restService.sendData(createData(UserRequest.builder()
                        .username(username)
                        .build()),
                Boolean.class);
        return resp.getStatusCode().is2xxSuccessful();
    }

    private RestDataDto createData(AuthUserRequestDto request) {
        return RestDataDto.builder()
                //.headers() TODO://Сделать хэдер
                .url(dbProp.getHostAuthDb() + getHttpAttributes().getRequestURI())
                .method(HttpMethod.valueOf(getHttpAttributes().getMethod()))
                .body(request)
                .build();
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
