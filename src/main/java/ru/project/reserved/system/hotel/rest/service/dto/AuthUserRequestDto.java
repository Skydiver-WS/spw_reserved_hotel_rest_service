package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.reserved.system.hotel.rest.service.dto.type.RolesType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserRequestDto {
    private String username;
    private String password;
    private String inn;
    private String ogrn;
    private String address;
    private RolesType role;
}
