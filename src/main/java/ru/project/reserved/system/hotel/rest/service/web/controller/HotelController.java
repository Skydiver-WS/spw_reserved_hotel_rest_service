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
    public ResponseEntity<HotelResponse> findAllHotels(){
        return hotelService.findAllHotels();
    }

    @PostMapping("/search")
    public ResponseEntity<HotelResponse> findHotels(@RequestBody HotelRequest hotelRequest){
        return hotelService.searchHotelByParameters(hotelRequest);
    }

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.createHotel(hotelRequest);
    }

    @PutMapping
    public ResponseEntity<HotelResponse> updateHotel(@RequestBody HotelRequest hotelRequest){
        return hotelService.updateHotel(hotelRequest);
    }

    @DeleteMapping
    public ResponseEntity<HotelResponse> deleteHotel(@RequestParam Long id){
        return hotelService.deleteService(id);
    }


}
