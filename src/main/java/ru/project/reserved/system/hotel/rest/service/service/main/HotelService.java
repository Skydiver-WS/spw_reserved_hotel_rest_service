package ru.project.reserved.system.hotel.rest.service.service.main;

import org.springframework.http.ResponseEntity;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;

import java.util.List;

public interface HotelService {
    ResponseEntity<HotelResponse> findAllHotels();

    ResponseEntity<HotelResponse> searchHotelByParameters(HotelRequest hotelRequest);

    ResponseEntity<HotelResponse> createHotel(HotelRequest hotelRequest);

    ResponseEntity<HotelResponse> updateHotel(HotelRequest hotelRequest);

    ResponseEntity<HotelResponse> deleteService(HotelRequest hotelRequest);
}
