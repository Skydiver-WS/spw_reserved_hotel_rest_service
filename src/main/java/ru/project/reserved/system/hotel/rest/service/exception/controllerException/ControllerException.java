package ru.project.reserved.system.hotel.rest.service.exception.controllerException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.project.reserved.system.hotel.rest.service.exception.ServiceDbException;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler({ServiceDbException.class})
    public ResponseEntity<ExceptionResponse> exceptionResponse(ServiceDbException e) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ExceptionResponse.builder()
                .errorMessage(e.getMessage())
                .build());
    }

    @ExceptionHandler({SecurityException.class, SignatureException.class})
    public ResponseEntity<ExceptionResponse> exceptionResponse(SecurityException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .errorMessage(e.getMessage())
                        .build());

    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionResponse> exceptionResponse(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .errorMessage(e.getMessage())
                        .build());

    }
}
