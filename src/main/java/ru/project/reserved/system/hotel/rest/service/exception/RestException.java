package ru.project.reserved.system.hotel.rest.service.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class RestException extends HttpClientErrorException {
    public RestException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
