package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.service.main.RestService;


@Service
@RequiredArgsConstructor
@Slf4j
public class RestServiceImpl implements RestService {

    private final RestClient restClient;

    @Override
    public <T> ResponseEntity<T> sendData(RestDataDto restDataDto, Class<T> type) {
        return restClient.method(restDataDto.getMethod())
                .headers(h -> h.addAll(restDataDto.getHeaders()))
                .body(restDataDto.getBody())
                .retrieve()
                .toEntity(type);
    }
}
