package ru.project.reserved.system.hotel.rest.service.service.security;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.AuthUserRequestDto;

import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
@Service
@Slf4j
public class AppUserPrincipalService implements UserDetails {

    private final AuthUserRequestDto user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("Get authorities role type");
        return user.getRole().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRolesType().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
