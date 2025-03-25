package ru.project.reserved.system.hotel.rest.service.exception;

public class ServiceDbException extends RuntimeException {
    public ServiceDbException(String message) {
        super(message);
    }
}
