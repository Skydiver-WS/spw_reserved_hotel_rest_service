package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.service.main.HotelService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<HotelResponse> findAllHotels(){
        return hotelService.findAllHotels();
    }

    @PostMapping("/search")
    public ResponseEntity<HotelResponse> findHotels(@RequestBody HotelRequest hotelRequest){
        return hotelService.searchHotelByParameters(hotelRequest);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.createHotel(hotelRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<HotelResponse> updateHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.updateHotel(hotelRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> deleteHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.deleteService(hotelRequest);
    }


}
