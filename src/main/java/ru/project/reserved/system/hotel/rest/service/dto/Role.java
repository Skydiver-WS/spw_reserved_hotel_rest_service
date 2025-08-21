package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.project.reserved.system.hotel.rest.service.dto.type.RolesType;

@Data
public class Role {

    private RolesType rolesType;

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(rolesType.name());
    }

    public static Role from (RolesType rolesType) {
        Role role = new Role();
        role.setRolesType(rolesType);
        return role;
    }
}
