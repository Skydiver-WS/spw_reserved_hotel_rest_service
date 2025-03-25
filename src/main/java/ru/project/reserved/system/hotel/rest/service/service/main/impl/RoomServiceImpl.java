package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.KafkaDto;
import ru.project.reserved.system.hotel.rest.service.dto.type.TopicType;
import ru.project.reserved.system.hotel.rest.service.service.main.KafkaService;
import ru.project.reserved.system.hotel.rest.service.service.main.RoomService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<List<RoomResponse>> findAllRooms() {
        String key = createEventAndReturnKey(TopicType.FIND_ALL_ROOM, null);
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntityList(HttpStatus.OK, response);
    }

    @Override
    public ResponseEntity<List<RoomResponse>> searchRoomByParameters(RoomRequest roomRequest) {
        String key = createEventAndReturnKey(TopicType.FIND_BY_PARAMETER_ROOM, roomRequest);
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntityList(HttpStatus.OK, response);
    }

    @Override
    public ResponseEntity<RoomResponse> createRoom(RoomRequest roomRequest) {
        String key = createEventAndReturnKey(TopicType.CREATE_ROOM, roomRequest);
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntity(HttpStatus.CREATED, response);
    }

    @Override
    public ResponseEntity<RoomResponse> updateRoom(RoomRequest roomRequest) {
        String key = createEventAndReturnKey(TopicType.UPDATE_ROOM, roomRequest);
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntity(HttpStatus.OK, response);
    }

    @Override
    public ResponseEntity<RoomResponse> deleteRoom(Long hotelId, Long roomId) {
        String key = createEventAndReturnKey(TopicType.REMOVE_ROOM, RoomRequest.builder()
                .id(roomId)
                .hotelId(hotelId)
                .build());
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntity(HttpStatus.OK, response);
    }

    @Override
    public ResponseEntity<RoomResponse> bookingRoom(RoomRequest roomRequest) {
        String key = createEventAndReturnKey(TopicType.RESERVED_ROOM, roomRequest);
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntity(HttpStatus.OK, response);
    }


    @SneakyThrows
    private ResponseEntity<RoomResponse>  createResponseEntity(HttpStatus status, String body){
        return ResponseEntity.status(status)
                .body(Strings.isNotBlank(body) ?
                        objectMapper.readValue(body, RoomResponse.class) :
                        null);
    }

    @SneakyThrows
    private ResponseEntity<List<RoomResponse>> createResponseEntityList(HttpStatus status, String body) {
        return ResponseEntity.status(status)
                .body(Strings.isNotBlank(body) ?
                        objectMapper.readValue(body, new TypeReference<>() {
                        }) :
                        null);
    }

    @SneakyThrows
    private String createEventAndReturnKey(TopicType topicType, RoomRequest roomRequest){
        String key = UUID.randomUUID().toString();
        String hotelJson = Objects.nonNull(roomRequest) ? objectMapper.writeValueAsString(roomRequest) : "";
        kafkaService.sendMessage(KafkaDto.builder()
                .key(key)
                .topic(topicType)
                .build(), hotelJson);
        return key;
    }
}
