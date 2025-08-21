package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthUserRequestDto {
    private String username;
    private String password;
    private String inn;
    private String ogrn;
    private String address;
    private List<Role> role;
}
