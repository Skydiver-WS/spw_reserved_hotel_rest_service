package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.ok(UserResponse.builder()
                .build());
    }

    @PostMapping("/sing-in")
    public ResponseEntity<UserResponse> singIn(@RequestBody UserRequest userRequest) {
        UserResponse response = authService.authenticate(userRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());

    }
}
