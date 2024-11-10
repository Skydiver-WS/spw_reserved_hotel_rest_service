package ru.project.reserved.system.hotel.rest.service.service.main;

import org.springframework.http.ResponseEntity;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;

import java.util.List;

public interface HotelService {
    ResponseEntity<String> findAllHotels();

    ResponseEntity<String> searchHotelByParameters(HotelRequest hotelRequest);

    ResponseEntity<String> createHotel(HotelRequest hotelRequest);

    ResponseEntity<String> updateHotel(HotelRequest hotelRequest);

    ResponseEntity<String> deleteService(HotelRequest hotelRequest);
}
