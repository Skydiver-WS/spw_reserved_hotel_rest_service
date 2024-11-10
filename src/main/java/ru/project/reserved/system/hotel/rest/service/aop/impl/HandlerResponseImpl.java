package ru.project.reserved.system.hotel.rest.service.aop.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.aop.HandlerResponse;
import ru.project.reserved.system.hotel.rest.service.service.main.KafkaService;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class HandlerResponseImpl {

    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;

    @Around("@annotation(handlerResponse)")
    @SneakyThrows
    public Object checkingResponseOnResponseFromDbService(ProceedingJoinPoint joinPoint, HandlerResponse handlerResponse) {
        Object object = joinPoint.proceed();
        if (!(object instanceof ResponseEntity<?> responseEntity)) {
            log.info("Object not ResponseEntity");
            return object;
        }
        log.info("Object ResponseEntity");
        Object body = responseEntity.getBody();

        if (Objects.nonNull(body)) {
            log.info("Object body is not null");
            return object;
        }
        String response = "";
        int count = 1;
        while (count <= 5) {
            Thread.sleep(5000);
            log.info("Waiting response from db service. Attempt {}", count);
            response = kafkaService.getResponseFromKafka();
            if (Strings.isNotBlank(response)) {
                log.info("Response from service db: {}", response);
                return ResponseEntity.ok().body(response);
            }
            count++;
        }

        log.warn("No response from kafka {}", HttpStatus.GATEWAY_TIMEOUT);
        return ResponseEntity
                .status(HttpStatus.GATEWAY_TIMEOUT)  // 504 Gateway Timeout
                .body(HotelResponse.builder()
                        .errorMessage(getResponseType(handlerResponse))
                        .build());
    }

    @SneakyThrows
    private String getResponseType(HandlerResponse handlerResponse) {
        Class<?> object = handlerResponse.typeObjectResponse();
        String response = "Unknown response";
        if (object.equals(HotelResponse.class)) {
            response = objectMapper.writeValueAsString(HotelResponse.builder()
                    .errorMessage("Not response from server db")
                    .build());
        } else if (object.equals(RoomResponse.class)) {
            response = objectMapper.writeValueAsString(HotelResponse.builder()
                    .errorMessage("Not response from server db")
                    .build());
        }
        return response;
    }
}
