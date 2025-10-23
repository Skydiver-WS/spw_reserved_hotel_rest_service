package ru.project.reserved.system.hotel.rest.service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.properties.DbServiceRestProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRq;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomRs;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final ProxyService proxyService;

    @GetMapping
    public ResponseEntity<RoomRs> findAllRooms() {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(null, RoomRs.class));
    }

    @PostMapping("/booking")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    public ResponseEntity<RoomRs> bookingRoom(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @PostMapping("/booking/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL', 'ROLE_EMPLOYEE')")
    public ResponseEntity<RoomRs> bookingUpdateBooking(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @DeleteMapping("/booking/remove")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL', 'ROLE_EMPLOYEE')")
    public ResponseEntity<RoomRs> bookingRoomRemove(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @PostMapping("/search")
    public ResponseEntity<RoomRs> findRooms(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomRs> createRoom(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomRs> updateRoom(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomRs> deleteRoom(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }
}
