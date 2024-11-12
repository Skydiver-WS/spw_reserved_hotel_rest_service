package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.shaded.io.opentelemetry.proto.trace.v1.Status;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.aop.HandlerResponse;
import ru.project.reserved.system.hotel.rest.service.dto.KafkaDto;
import ru.project.reserved.system.hotel.rest.service.dto.KeyType;
import ru.project.reserved.system.hotel.rest.service.dto.TopicType;
import ru.project.reserved.system.hotel.rest.service.service.main.HotelService;
import ru.project.reserved.system.hotel.rest.service.service.main.KafkaService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final ObjectMapper objectMapper;
    private final KafkaService kafkaService;

    @Override
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<HotelResponse> findAllHotels() {
        return null;
    }

    @Override
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<HotelResponse> searchHotelByParameters(HotelRequest hotelRequest) {
        return null;
    }

    @Override
    @SneakyThrows
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<HotelResponse> createHotel(HotelRequest hotelRequest) {
        String key = UUID.randomUUID().toString();
        String hotelJson = objectMapper.writeValueAsString(hotelRequest);
        kafkaService.sendMessage(KafkaDto.builder()
                        .topic(TopicType.CREATE_HOTEL)
                        .key(key)
                        .build(),
                hotelJson);
        String response = kafkaService.getResponseFromKafka(key);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Strings.isNotBlank(response) ?
                        objectMapper.readValue(response, HotelResponse.class) :
                        null);
    }

    @Override
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<HotelResponse> updateHotel(HotelRequest hotelRequest) {
        return null;
    }

    @Override
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<HotelResponse> deleteService(HotelRequest hotelRequest) {
        return null;
    }
}
