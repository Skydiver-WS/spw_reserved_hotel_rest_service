package ru.project.reserved.system.room.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.service.main.RoomService;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;


import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAllRooms() {
        return roomService.findAllRooms();
    }

    @PostMapping("/search")
    public ResponseEntity<List<RoomResponse>> findRooms(@RequestBody RoomRequest roomRequest) {
        return roomService.searchRoomByParameters(roomRequest);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest roomRequest) {
        return roomService.createRoom(roomRequest);
    }

    @PutMapping
    public ResponseEntity<RoomResponse> updateRoom(@RequestBody RoomRequest roomRequest) {
        return roomService.updateRoom(roomRequest);
    }

    @DeleteMapping
    public ResponseEntity<RoomResponse> deleteRoom(@RequestBody RoomRequest roomRequest) {
        return roomService.deleteService(roomRequest);
    }
}
