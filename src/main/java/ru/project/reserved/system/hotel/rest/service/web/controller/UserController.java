package ru.project.reserved.system.hotel.rest.service.web.controller;

import io.micrometer.core.instrument.Counter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.aop.Metric;
import ru.project.reserved.system.hotel.rest.service.dto.type.MetricType;
import ru.project.reserved.system.hotel.rest.service.properties.SecurityProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.service.security.AuthService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRq;
import ru.project.reserved.system.hotel.rest.service.web.response.UserRs;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final SecurityProperties securityProperties;

    @PostMapping("/create")
    @Metric(type = MetricType.CREATE_USER, description = "metric create user")
    public ResponseEntity<UserRs> createUser(@RequestBody @Valid UserRq userRq) {
        return ResponseEntity.ok(userService.createUser(userRq));
    }

    @PostMapping("/sing-in")
    @Metric(type = MetricType.SIGN_IN_USER, description = "sign in user")
    public ResponseEntity<UserRs> singIn(@RequestBody UserRq userRq) {
        UserRs response = authService.authenticate(userRq);
        ResponseCookie cookie = ResponseCookie.from("token", response.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(securityProperties.getExpiration())
                .sameSite("Strict")
                .build();
        response.setToken(null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }


    @PutMapping
    @PreAuthorize("(hasAnyRole('ROLE_MANAGER', 'ROLE_CLIENT') && #user.equals(authentication.principal.username)) " +
            "or (hasAnyRole('ROLE_ADMIN'))")
    @Metric(type = MetricType.UPDATE_USER, description = "update user")
    public ResponseEntity<UserRs> updateUser(@RequestBody UserRq userRq, @RequestParam String user){
        userRq.setUsername(user);
        return ResponseEntity.ok(userService.updateUser(userRq));
    }

    @DeleteMapping
    @PreAuthorize("(hasAnyRole('ROLE_MANAGER', 'ROLE_CLIENT') and #userRq.username.equals(authentication.principal.username)) " +
            "or hasRole('ROLE_ADMIN')")
    @Metric(type = MetricType.DELETE_USER)
    public ResponseEntity<UserRs> deleteUser(@RequestBody UserRq userRq){
        return ResponseEntity.ok(userService.deleteUser(userRq.getUsername()));
    }


    @GetMapping("/all-users")
    public ResponseEntity<List<UserRs>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());

    }
}
