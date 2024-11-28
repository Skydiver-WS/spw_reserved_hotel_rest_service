package ru.project.reserved.system.hotel.rest.service.aop.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.aop.HandlerResponse;
import ru.project.reserved.system.hotel.rest.service.dto.ErrorType;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomResponse;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class HandlerResponseImpl {

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
        log.warn("No response from kafka {}", HttpStatus.GATEWAY_TIMEOUT);
        return ResponseEntity
                .status(HttpStatus.GATEWAY_TIMEOUT)  // 504 Gateway Timeout
                .body(getResponseType(handlerResponse));
    }

    @SneakyThrows
    private Object getResponseType(HandlerResponse handlerResponse) {
        Class<?> object = handlerResponse.typeObjectResponse();
        Object response = null;
        if (object.equals(HotelResponse.class)) {
            response = HotelResponse.builder()
                    .errorMessage(ErrorType.NOT_RESPONSE_FROM_DB.getErrorMessage())
                    .build();
        } else if (object.equals(RoomResponse.class)) {
            response = HotelResponse.builder()
                    .errorMessage(ErrorType.NOT_RESPONSE_FROM_DB.getErrorMessage())
                    .build();
        }
        return response;
    }
}
