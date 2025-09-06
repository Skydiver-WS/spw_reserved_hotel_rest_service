package ru.project.reserved.system.hotel.rest.service.mapper;


import org.mapstruct.Mapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

import java.util.List;

@Mapper(componentModel = "spring",
        imports = {
                BCryptPasswordEncoder.class
        })
public interface UserMapper {

    UserResponse userResponseFromAuthUserDto(AuthUserRequestDto authUserRequestDto);

    AuthUserRequestDto authUserRequestFromUserResponse(UserResponse userResponse);

    List<UserResponse> listUserResponseFromAuthUserDtoList(List<AuthUserRequestDto> authUserRequestDtoList);
}
