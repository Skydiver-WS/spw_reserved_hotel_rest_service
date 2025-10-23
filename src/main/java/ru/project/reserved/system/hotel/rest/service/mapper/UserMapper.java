package ru.project.reserved.system.hotel.rest.service.mapper;


import org.mapstruct.Mapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;
import ru.project.reserved.system.hotel.rest.service.web.response.UserRs;

import java.util.List;

@Mapper(componentModel = "spring",
        imports = {
                BCryptPasswordEncoder.class
        })
public interface UserMapper {

    UserRs userResponseFromAuthUserDto(AuthUserRequestDto authUserRequestDto);

    AuthUserRequestDto authUserRequestFromUserResponse(UserRs userRs);

    List<UserRs> listUserResponseFromAuthUserDtoList(List<AuthUserRequestDto> authUserRequestDtoList);
}
