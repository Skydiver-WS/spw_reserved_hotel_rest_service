package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.properties.DbServiceRestProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.RestService;
import ru.project.reserved.system.hotel.rest.service.service.main.RoomService;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RestService restService;
    private final DbServiceRestProperties prop;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<RoomResponse> findAllRooms() {
        return restService.sendData(createData(null), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> searchRoomByParameters(RoomRequest roomRequest) {
        return restService.sendData(createData(roomRequest), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> createRoom(RoomRequest roomRequest) {

        return restService.sendData(createData(roomRequest), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> updateRoom(RoomRequest roomRequest) {
        return restService.sendData(createData(roomRequest), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> deleteRoom(Long hotelId, Long roomId) {
       var roomRequest = RoomRequest.builder()
                .id(roomId)
                .hotelId(hotelId)
                .build();
        return restService.sendData(createData(roomRequest), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> bookingRoom(RoomRequest roomRequest) {
        return restService.sendData(createData(roomRequest), RoomResponse.class);
    }


    private RestDataDto createData(Object request) {
        return RestDataDto.builder()
                //.headers() TODO://Сделать хэдер
                .url(prop.getUrl() + getHttpAttributes().getRequestURI())
                .method(HttpMethod.valueOf(getHttpAttributes().getMethod()))
                .body(request)
                .build();
    }

    private HttpServletRequest getHttpAttributes() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            log.error("ServletRequest is null");
            throw new RuntimeException();
        }
        return attributes.getRequest();
    }
}
