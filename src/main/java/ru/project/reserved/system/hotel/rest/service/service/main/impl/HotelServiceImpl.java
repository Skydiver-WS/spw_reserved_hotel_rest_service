package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.properties.DbServiceRestProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.HotelService;
import ru.project.reserved.system.hotel.rest.service.service.main.RestService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final RestService restService;
    private final DbServiceRestProperties prop;

    @Override
    public ResponseEntity<HotelResponse> findAllHotels() {
        RestDataDto rq = createData(null);
        return restService.sendData(rq, HotelResponse.class);

    }

    @Override
    @SneakyThrows
    public ResponseEntity<HotelResponse> searchHotelByParameters(HotelRequest hotelRequest) {
        RestDataDto rq = createData(hotelRequest);
        return restService.sendData(rq, HotelResponse.class);
    }

    @Override
    @SneakyThrows
    public ResponseEntity<HotelResponse> createHotel(HotelRequest hotelRequest) {
        RestDataDto rq = createData(hotelRequest);
        return restService.sendData(rq, HotelResponse.class);
    }

    @Override
    @SneakyThrows
    public ResponseEntity<HotelResponse> updateHotel(HotelRequest hotelRequest) {
        RestDataDto rq = createData(hotelRequest);
        return restService.sendData(rq, HotelResponse.class);
    }

    @Override
    @SneakyThrows
    public ResponseEntity<HotelResponse> deleteService(HotelRequest hotelRequest) {
        RestDataDto rq = createData(hotelRequest);
        return restService.sendData(rq, HotelResponse.class);
    }


    private RestDataDto createData(Object request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return RestDataDto.builder()
                .headers(headers)
                .url(prop.getHostData() + getHttpAttributes().getRequestURI())
                .method(HttpMethod.valueOf(getHttpAttributes().getMethod()))
                .body(request)
                .build();
    }

    private HttpServletRequest getHttpAttributes(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)){
            log.error("ServletRequest is null");
            throw new RuntimeException();
        }
        return attributes.getRequest();
    }


}
