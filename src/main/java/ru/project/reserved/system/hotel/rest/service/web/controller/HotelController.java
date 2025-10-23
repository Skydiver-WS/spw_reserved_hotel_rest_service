package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRq;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelRs;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final ProxyService proxyService;

    @GetMapping
    public ResponseEntity<HotelRs> findAllHotels(){
        return ResponseEntity.ok((HotelRs) proxyService.proxyOperation(null, HotelRs.class));
    }

    @PostMapping("/search")
    public ResponseEntity<HotelRs> findHotels(@RequestBody HotelRq hotelRq){
        return ResponseEntity.ok((HotelRs) proxyService.proxyOperation(hotelRq, HotelRs.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<HotelRs> createHotel(@RequestBody HotelRq hotelRq){
        return ResponseEntity.ok((HotelRs) proxyService.proxyOperation(hotelRq, HotelRs.class));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    public ResponseEntity<HotelRs> updateHotel(@RequestBody HotelRq hotelRq){
        return ResponseEntity.ok((HotelRs) proxyService.proxyOperation(hotelRq, HotelRs.class));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<HotelRs> deleteHotel(@RequestBody HotelRq hotelRq){
        return ResponseEntity.ok((HotelRs) proxyService.proxyOperation(hotelRq, HotelRs.class));
    }


}
