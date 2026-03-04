package ru.project.reserved.system.hotel.rest.service.service.main;

import org.springframework.http.ResponseEntity;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.dto.type.BeanType;

public interface RestService {

    <T> ResponseEntity<T> sendData(RestDataDto restDataDto, BeanType beanType, Class<T> type);
}
