package ru.project.reserved.system.hotel.rest.service.exception;

import io.jsonwebtoken.JwtException;

public class SecurityException extends JwtException {
    public SecurityException(String message) {
        super(message);
    }
}
