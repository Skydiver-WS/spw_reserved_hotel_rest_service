package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> findAllHotels(){
        return hotelService.findAllHotels();
    }

    @PostMapping("/search")
    public ResponseEntity<String> findHotels(@RequestBody HotelRequest hotelRequest){
        return hotelService.searchHotelByParameters(hotelRequest);
    }

    @PostMapping
    public ResponseEntity<String> createHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.createHotel(hotelRequest);
    }

    @PutMapping
    public ResponseEntity<String> updateHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.updateHotel(hotelRequest);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.deleteService(hotelRequest);
    }


}
