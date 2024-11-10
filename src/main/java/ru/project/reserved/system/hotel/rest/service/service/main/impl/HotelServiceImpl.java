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

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final ObjectMapper objectMapper;
    private final KafkaService kafkaService;

    @Override
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<String> findAllHotels() {
        return null;
    }

    @Override
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<String> searchHotelByParameters(HotelRequest hotelRequest) {
        return null;
    }

    @Override
    @SneakyThrows
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<String> createHotel(HotelRequest hotelRequest) {
        String hotelJson = objectMapper.writeValueAsString(hotelRequest);
        kafkaService.sendMessage(KafkaDto.builder()
                        .topic(TopicType.CREATE_UPDATE_HOTEL)
                        .keyType(KeyType.CREATE_HOTEL)
                        .build(),
                hotelJson);
        return ResponseEntity.status(HttpStatus.CREATED).body(kafkaService.getResponseFromKafka());
    }

    @Override
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<String> updateHotel(HotelRequest hotelRequest) {
        return null;
    }

    @Override
    @HandlerResponse(typeObjectResponse = HotelResponse.class)
    public ResponseEntity<String> deleteService(HotelRequest hotelRequest) {
        return null;
    }
}
