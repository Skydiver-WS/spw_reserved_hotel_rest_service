package ru.project.reserved.system.hotel.rest.service.exception.controllerException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.project.reserved.system.hotel.rest.service.exception.ServiceDbException;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler({ServiceDbException.class})
    public ResponseEntity<ExceptionResponse> exceptionResponse(ServiceDbException e) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ExceptionResponse.builder()
                .message(e.getMessage())
                .build());

    }

    @ExceptionHandler({SecurityException.class})
    public ResponseEntity<ExceptionResponse> exceptionResponse(SecurityException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .message(e.getMessage())
                        .build());

    }
}
