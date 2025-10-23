package ru.project.reserved.system.hotel.rest.service.service.security;

import ru.project.reserved.system.hotel.rest.service.web.request.UserRq;
import ru.project.reserved.system.hotel.rest.service.web.response.UserRs;

public interface AuthService {

    UserRs authenticate(UserRq userRq);
}
