package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.service.main.RoomService;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    @Override
    public ResponseEntity<List<RoomResponse>> findAllRooms() {
        return null;
    }

    @Override
    public ResponseEntity<List<RoomResponse>> searchRoomByParameters(RoomRequest roomRequest) {
        return null;
    }

    @Override
    public ResponseEntity<RoomResponse> createRoom(RoomRequest roomRequest) {
        return null;
    }

    @Override
    public ResponseEntity<RoomResponse> updateRoom(RoomRequest roomRequest) {
        return null;
    }

    @Override
    public ResponseEntity<RoomResponse> deleteService(RoomRequest roomRequest) {
        return null;
    }
}
