package ru.project.reserved.system.hotel.rest.service.service.main.stub;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@Profile("stub")
@RequiredArgsConstructor
public class UserServiceStub implements UserService {
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Optional<AuthUserRequestDto> getUser(String username) {
        List<AuthUserRequestDto> listUsers = objectMapper.readValue(new File("src/main/resources/stub/users.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, AuthUserRequestDto.class));
        return listUsers
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public boolean createUser(AuthUserRequestDto authUserRequestDto) {
        return true;
    }

    @Override
    public boolean updateUser(AuthUserRequestDto authUserRequestDto) {
        return true;
    }

    @Override
    public boolean deleteUser(String username) {
        return true;
    }
}
