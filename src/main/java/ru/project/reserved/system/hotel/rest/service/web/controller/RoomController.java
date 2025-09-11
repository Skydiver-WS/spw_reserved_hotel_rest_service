package ru.project.reserved.system.hotel.rest.service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.service.main.RoomService;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;


import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<RoomResponse> findAllRooms() {
        return roomService.findAllRooms();
    }

    @PutMapping("/booking")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    public ResponseEntity<RoomResponse> bookingRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.bookingRoom(roomRequest);
    }

    @PutMapping("/booking/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomResponse> bookingUpdateBooking(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.bookingRoom(roomRequest);
    }

    @PutMapping("/booking/remove")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomResponse> bookingRoomRemove(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.bookingRoom(roomRequest);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    public ResponseEntity<RoomResponse> findRooms(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.searchRoomByParameters(roomRequest);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.createRoom(roomRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomResponse> updateRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.updateRoom(roomRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<RoomResponse> deleteRoom(@RequestParam Long hotelId, @RequestParam Long roomId) {
        return roomService.deleteRoom(hotelId, roomId);
    }
}
