package ru.project.reserved.system.hotel.rest.service.exception.controllerException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.project.reserved.system.hotel.rest.service.exception.RestException;

@ControllerAdvice
public class RestExceptionImpl {

    @ExceptionHandler({RestException.class})
    public ResponseEntity<ExceptionResponse> badRequest(RestException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ExceptionResponse.builder()
                        .errorMessage(e.getMessage())
                        .build());
    }
}
