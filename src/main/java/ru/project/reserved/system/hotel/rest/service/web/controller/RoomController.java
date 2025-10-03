package ru.project.reserved.system.hotel.rest.service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;


import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final ProxyService proxyService;

    @GetMapping
    public ResponseEntity<RoomResponse> findAllRooms() {
        return ResponseEntity.ok((RoomResponse) proxyService.proxyOperation(null, RoomResponse.class));
    }

    @PostMapping("/booking")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    public ResponseEntity<RoomResponse> bookingRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return ResponseEntity.ok((RoomResponse) proxyService.proxyOperation(roomRequest, RoomResponse.class));
    }

    @PostMapping("/booking/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL', 'ROLE_EMPLOYEE')")
    public ResponseEntity<RoomResponse> bookingUpdateBooking(@RequestBody @Valid RoomRequest roomRequest) {
        return ResponseEntity.ok((RoomResponse) proxyService.proxyOperation(roomRequest, RoomResponse.class));
    }

    @DeleteMapping("/booking/remove")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL', 'ROLE_EMPLOYEE')")
    public ResponseEntity<RoomResponse> bookingRoomRemove(@RequestBody @Valid RoomRequest roomRequest) {
        return ResponseEntity.ok((RoomResponse) proxyService.proxyOperation(roomRequest, RoomResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<RoomResponse> findRooms(@RequestBody @Valid RoomRequest roomRequest) {
        return ResponseEntity.ok((RoomResponse) proxyService.proxyOperation(roomRequest, RoomResponse.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return ResponseEntity.ok((RoomResponse) proxyService.proxyOperation(roomRequest, RoomResponse.class));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomResponse> updateRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return ResponseEntity.ok((RoomResponse) proxyService.proxyOperation(roomRequest, RoomResponse.class));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomResponse> deleteRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return ResponseEntity.ok((RoomResponse) proxyService.proxyOperation(roomRequest, RoomResponse.class));
    }
}
