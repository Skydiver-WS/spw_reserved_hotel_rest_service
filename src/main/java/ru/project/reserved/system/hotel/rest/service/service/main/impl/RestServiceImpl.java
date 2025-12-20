package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.exception.RestException;
import ru.project.reserved.system.hotel.rest.service.service.main.RestService;

import java.net.http.HttpClient;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class RestServiceImpl implements RestService {

    private final RestClient restClient;

    @Override
    public <T> ResponseEntity<T> sendData(RestDataDto restDataDto, Class<T> type) {
        log.info("Send data to {}", restDataDto.getUrl());
        try {
            return restClient.method(restDataDto.getMethod())
                    .uri(restDataDto.getUrl())
                    .headers(h -> h.addAll(restDataDto.getHeaders()))
                    .body(Objects.nonNull(restDataDto.getBody()) ? restDataDto.getBody() : "")
                    .retrieve()
                    .toEntity(type);
        } catch (HttpClientErrorException.BadRequest ex){
            throw new RestException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (HttpClientErrorException.NotFound ex){
            throw new RestException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (Exception e){
            throw new RestException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }
}
