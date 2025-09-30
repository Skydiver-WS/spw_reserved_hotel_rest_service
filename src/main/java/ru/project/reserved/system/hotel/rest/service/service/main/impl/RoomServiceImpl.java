package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    @Override
    public ResponseEntity<RoomResponse> findAllRooms() {
        return restService.sendData(createData(null, null, null), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> searchRoomByParameters(RoomRequest roomRequest) {
        return restService.sendData(createData(roomRequest, null, null), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> createRoom(RoomRequest roomRequest) {

        return restService.sendData(createData(roomRequest, null, null), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> updateRoom(RoomRequest roomRequest) {
        return restService.sendData(createData(roomRequest, null, null), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> deleteRoom(RoomRequest roomRequest) {
        return restService.sendData(createData(roomRequest, null, null), RoomResponse.class);
    }

    @Override
    public ResponseEntity<RoomResponse> bookingRoom(RoomRequest roomRequest) {
        String uri = getHttpAttributes().getRequestURI();
        if(uri.contains("update")){
            uri = uri.replaceAll("/update", "");
        } else if (uri.contains("remove")){
            uri = uri.replaceAll("/remove", "");
        }
        return restService.sendData(createData(roomRequest, uri, HttpMethod.POST.name()), RoomResponse.class);
    }


    private RestDataDto createData(Object request, String uri, String method) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        String uri2 = Objects.isNull(uri) ? getHttpAttributes().getRequestURI() : uri;
        String method2 = Objects.isNull(method) ? getHttpAttributes().getMethod() : method;
        return RestDataDto.builder()
                .headers(headers)
                .url(prop.getHostData() + uri2)
                .method(HttpMethod.valueOf(method2))
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
