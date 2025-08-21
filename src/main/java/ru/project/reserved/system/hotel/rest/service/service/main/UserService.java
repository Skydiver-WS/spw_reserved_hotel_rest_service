package ru.project.reserved.system.hotel.rest.service.service.main;

import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;

import java.util.Optional;

public interface UserService {

    Optional<AuthUserRequestDto> getUser(String username);
    boolean createUser(AuthUserRequestDto authUserRequestDto);
    boolean updateUser(AuthUserRequestDto authUserRequestDto);
    boolean deleteUser(String username);
}
