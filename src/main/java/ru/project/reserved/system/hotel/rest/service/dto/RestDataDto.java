package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Getter
@Setter
@Builder
public class RestDataDto {

    private Object body;
    private String url;
    private HttpMethod method;
    private HttpHeaders headers;

}
