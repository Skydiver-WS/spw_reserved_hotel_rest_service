package ru.project.reserved.system.hotel.rest.service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<RoomResponse>> findAllRooms() {
        return roomService.findAllRooms();
    }

    @PutMapping("/booking")
    public ResponseEntity<RoomResponse> bookingRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.bookingRoom(roomRequest);
    }

    @PutMapping("/booking/update")
    public ResponseEntity<RoomResponse> bookingUpdateBooking(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.bookingRoom(roomRequest);
    }

    @PutMapping("/booking/remove")
    public ResponseEntity<RoomResponse> bookingRoomRemove(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.bookingRoom(roomRequest);
    }

    @PostMapping("/search")
    public ResponseEntity<List<RoomResponse>> findRooms(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.searchRoomByParameters(roomRequest);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.createRoom(roomRequest);
    }

    @PutMapping
    public ResponseEntity<RoomResponse> updateRoom(@RequestBody @Valid RoomRequest roomRequest) {
        return roomService.updateRoom(roomRequest);
    }

    @DeleteMapping
    public ResponseEntity<RoomResponse> deleteRoom(@RequestParam Long hotelId, @RequestParam Long roomId) {
        return roomService.deleteRoom(hotelId, roomId);
    }
}
