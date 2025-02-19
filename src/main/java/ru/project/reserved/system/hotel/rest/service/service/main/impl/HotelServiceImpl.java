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
import ru.project.reserved.system.hotel.rest.service.aop.HandlerResponse;
import ru.project.reserved.system.hotel.rest.service.dto.KafkaDto;
import ru.project.reserved.system.hotel.rest.service.dto.type.TopicType;
import ru.project.reserved.system.hotel.rest.service.service.main.HotelService;
import ru.project.reserved.system.hotel.rest.service.service.main.KafkaService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final ObjectMapper objectMapper;
    private final KafkaService kafkaService;

    @Override
    //@HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<List<HotelResponse>> findAllHotels() {
        String key = createEventAndReturnKey(TopicType.FIND_ALL_HOTEL, null);
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntityList(HttpStatus.OK, response);
    }

    @Override
    //@HandlerResponse(typeObjectResponse = HotelResponse.class)
    @SneakyThrows
    public ResponseEntity<List<HotelResponse>> searchHotelByParameters(HotelRequest hotelRequest) {
        String key = createEventAndReturnKey(TopicType.FIND_BY_PARAMETER_HOTEL, hotelRequest);
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntityList(HttpStatus.OK, response);
    }

    @Override
    @SneakyThrows
    //@HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<HotelResponse> createHotel(HotelRequest hotelRequest) {
        String key = createEventAndReturnKey(TopicType.CREATE_HOTEL, hotelRequest);
        String response = kafkaService.getResponseFromKafka(key);
        return createResponseEntity(HttpStatus.CREATED, response);
    }

    @Override
    //@HandlerResponse(typeObjectResponse = HotelResponse.class)
    @SneakyThrows
    public ResponseEntity<HotelResponse> updateHotel(HotelRequest hotelRequest) {
        String key = createEventAndReturnKey(TopicType.UPDATE_HOTEL, hotelRequest);
        return createResponseEntity(HttpStatus.OK, kafkaService.getResponseFromKafka(key));
    }

    @Override
    //@HandlerResponse(typeObjectResponse = HotelResponse.class)
    @SneakyThrows
    public ResponseEntity<HotelResponse> deleteService(Long hotelId) {
        String key = createEventAndReturnKey(TopicType.REMOVE_HOTEL, HotelRequest.builder()
                .id(hotelId).build());
        return createResponseEntity(HttpStatus.OK, kafkaService.getResponseFromKafka(key));
    }

    @SneakyThrows
    private ResponseEntity<HotelResponse> createResponseEntity(HttpStatus status, String body) {
        return ResponseEntity.status(status)
                .body(Strings.isNotBlank(body) ?
                        objectMapper.readValue(body, HotelResponse.class) :
                        null);
    }

    @SneakyThrows
    private ResponseEntity<List<HotelResponse>> createResponseEntityList(HttpStatus status, String body) {
        return ResponseEntity.status(status)
                .body(Strings.isNotBlank(body) ?
                        objectMapper.readValue(body, new TypeReference<>() {
                        }) :
                        null);
    }

    @SneakyThrows
    private String createEventAndReturnKey(TopicType topicType, HotelRequest hotelRequest) {
        String key = UUID.randomUUID().toString();
        String hotelJson = Objects.nonNull(hotelRequest) ? objectMapper.writeValueAsString(hotelRequest) : "";
        kafkaService.sendMessage(KafkaDto.builder()
                .key(key)
                .topic(topicType)
                .build(), hotelJson);
        return key;
    }

}
