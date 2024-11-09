package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.service.main.HotelService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {
    @Override
    public ResponseEntity<List<HotelResponse>> findAllHotels() {
        return null;
    }

    @Override
    public ResponseEntity<List<HotelResponse>> searchHotelByParameters(HotelRequest hotelRequest) {
        return null;
    }

    @Override
    public ResponseEntity<HotelResponse> createHotel(HotelRequest hotelRequest) {
        return null;
    }

    @Override
    public ResponseEntity<HotelResponse> updateHotel(HotelRequest hotelRequest) {
        return null;
    }

    @Override
    public ResponseEntity<HotelResponse> deleteService(HotelRequest hotelRequest) {
        return null;
    }
}
