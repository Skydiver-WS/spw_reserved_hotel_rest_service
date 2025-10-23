package ru.project.reserved.system.hotel.rest.service.service.main;

import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.web.response.UserRs;


import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserRs> getAllUsers();
    Optional<UserRs> getUser(String username);
    UserRs createUser(AuthUserRequestDto authUserRequestDto);
    UserRs updateUser(AuthUserRequestDto authUserRequestDto);
    UserRs deleteUser(String username);
}
