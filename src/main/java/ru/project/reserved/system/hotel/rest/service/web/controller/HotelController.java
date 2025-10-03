package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRequest;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelResponse;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final ProxyService proxyService;

    @GetMapping
    public ResponseEntity<HotelResponse> findAllHotels(){
        return ResponseEntity.ok((HotelResponse) proxyService.proxyOperation(null, HotelResponse.class));
    }

    @PostMapping("/search")
    public ResponseEntity<HotelResponse> findHotels(@RequestBody HotelRequest hotelRequest){
        return ResponseEntity.ok((HotelResponse) proxyService.proxyOperation(hotelRequest, HotelResponse.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody HotelRequest hotelRequest){
        return ResponseEntity.ok((HotelResponse) proxyService.proxyOperation(hotelRequest, HotelResponse.class));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<HotelResponse> updateHotel(@RequestBody HotelRequest hotelRequest){
        return ResponseEntity.ok((HotelResponse) proxyService.proxyOperation(hotelRequest, HotelResponse.class));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> deleteHotel(@RequestBody HotelRequest hotelRequest){
        return ResponseEntity.ok((HotelResponse) proxyService.proxyOperation(hotelRequest, HotelResponse.class));
    }


}
