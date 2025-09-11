package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.properties.SecurityProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.UserService;
import ru.project.reserved.system.hotel.rest.service.service.security.AuthService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

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
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PostMapping("/sing-in")
    public ResponseEntity<UserResponse> singIn(@RequestBody UserRequest userRequest) {
        UserResponse response = authService.authenticate(userRequest);
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest, @RequestParam String user){
        userRequest.setUser(user);
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<UserResponse> deleteUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.deleteUser(userRequest.getUsername()));
    }


    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());

    }
}
