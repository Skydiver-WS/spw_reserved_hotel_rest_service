package ru.project.reserved.system.hotel.rest.service.service.main;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;

import java.util.List;

public interface RoomService {
    ResponseEntity<List<RoomResponse>> findAllRooms();

    ResponseEntity<List<RoomResponse>> searchRoomByParameters(RoomRequest roomRequest);

    ResponseEntity<RoomResponse> createRoom(RoomRequest roomRequest);

    ResponseEntity<RoomResponse> updateRoom(RoomRequest roomRequest);

    ResponseEntity<RoomResponse> deleteRoom(Long hotelId, Long roomId);

    ResponseEntity<RoomResponse> bookingRoom(@Valid RoomRequest roomRequest);

}
