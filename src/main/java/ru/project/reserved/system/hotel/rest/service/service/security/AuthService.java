package ru.project.reserved.system.hotel.rest.service.service.security;

import ru.project.reserved.system.hotel.rest.service.web.request.UserRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

public interface AuthService {

    UserResponse authenticate(UserRequest userRequest);
}
