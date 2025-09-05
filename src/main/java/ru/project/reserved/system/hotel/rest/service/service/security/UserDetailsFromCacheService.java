package ru.project.reserved.system.hotel.rest.service.service.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsFromCacheService {

    UserDetails loadUserByUsername(String jwt);
}
