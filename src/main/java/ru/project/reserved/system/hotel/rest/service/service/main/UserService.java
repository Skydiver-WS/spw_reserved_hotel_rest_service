package ru.project.reserved.system.hotel.rest.service.service.main;

import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;


import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponse> getAllUsers();
    Optional<UserResponse> getUser(String username);
    UserResponse createUser(AuthUserRequestDto authUserRequestDto);
    UserResponse updateUser(AuthUserRequestDto authUserRequestDto);
    UserResponse deleteUser(String username);
}
