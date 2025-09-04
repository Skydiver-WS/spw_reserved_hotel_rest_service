package ru.project.reserved.system.hotel.rest.service.service.main.stub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.mapper.UserMapper;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Profile("stub")
@RequiredArgsConstructor
public class UserServiceStub implements UserService {
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;

    @Override
    @SneakyThrows
    public List<UserResponse> getAllUsers() {
        log.info("Get all users stub");
        List<AuthUserRequestDto> listUsers = objectMapper.readValue(new File("src/main/resources/stub/users.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, AuthUserRequestDto.class));
        return userMapper.listUserResponseFromAuthUserDtoList(listUsers);
    }

    @Override
    @SneakyThrows
    public Optional<AuthUserRequestDto> getUser(String username) {
        log.info("Getting user stub {}", username);
        List<AuthUserRequestDto> listUsers = objectMapper.readValue(new File("src/main/resources/stub/users.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, AuthUserRequestDto.class));
        return listUsers
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public boolean createUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Creating user stub {}", authUserRequestDto.getUsername());
        return true;
    }

    @Override
    public boolean updateUser(AuthUserRequestDto authUserRequestDto) {
        log.info("Updating user stub {}", authUserRequestDto.getUsername());
        return true;
    }

    @Override
    public boolean deleteUser(String username) {
        log.info("Deleting user stub {}", username);
        return true;
    }
}
