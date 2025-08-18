package ru.project.reserved.system.hotel.rest.service.exception.controllerException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.project.reserved.system.hotel.rest.service.exception.ServiceDbException;

public record SecurityExceptionHandler() {

    @ExceptionHandler({SecurityException.class})
    public ResponseEntity<ExceptionResponse> exceptionResponse(ServiceDbException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                .message(e.getMessage())
                .build());

    }
}
