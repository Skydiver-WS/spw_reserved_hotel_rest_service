package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.project.reserved.system.hotel.rest.service.service.security.AuthService;
import ru.project.reserved.system.hotel.rest.service.web.request.UserRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.UserResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthService authService;

    @PostMapping("/create/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(UserResponse.builder().build());
    }

    @PostMapping("/sing-in")
    public ResponseEntity<UserResponse> singIn(@RequestBody UserRequest userRequest) {
        UserResponse response = authService.authenticate(userRequest);
        return ResponseEntity.ok(response);
    }
}
