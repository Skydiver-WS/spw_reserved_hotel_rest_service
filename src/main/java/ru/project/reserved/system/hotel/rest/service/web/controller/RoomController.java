package ru.project.reserved.system.hotel.rest.service.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.aop.Metric;
import ru.project.reserved.system.hotel.rest.service.dto.type.MetricType;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.RoomRq;
import ru.project.reserved.system.hotel.rest.service.web.response.BookingRs;
import ru.project.reserved.system.hotel.rest.service.web.response.RoomRs;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final ProxyService proxyService;

    @GetMapping
    @Metric(type = MetricType.ALL_ROOMS)
    public ResponseEntity<RoomRs> findAllRooms() {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(null, RoomRs.class));
    }

    @PostMapping("/booking")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @Metric(type = MetricType.CREATE_RESERVATION)
    public ResponseEntity<BookingRs> bookingRoom(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((BookingRs) proxyService.proxyOperation(roomRq, BookingRs.class));
    }

    @PostMapping("/booking/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL', 'ROLE_EMPLOYEE')")
    @Metric(type = MetricType.UPDATE_RESERVATION)
    public ResponseEntity<BookingRs> bookingUpdateBooking(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((BookingRs) proxyService.proxyOperation(roomRq, BookingRs.class));
    }

    @DeleteMapping("/booking/remove")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL', 'ROLE_EMPLOYEE', 'ROLE_CLIENT')")
    @Metric(type = MetricType.DELETE_RESERVATION)
    public ResponseEntity<BookingRs> bookingRoomRemove(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((BookingRs) proxyService.proxyOperation(roomRq, BookingRs.class));
    }

    @PostMapping("/search")
    @Metric(type = MetricType.FIND_ROOMS)
    public ResponseEntity<RoomRs> findRooms(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    @Metric(type = MetricType.CREATE_ROOM)
    public ResponseEntity<RoomRs> createRoom(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    @Metric(type = MetricType.UPDATE_ROOM)
    public ResponseEntity<RoomRs> updateRoom(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER_HOTEL')")
    @Metric(type = MetricType.DELETE_ROOM)
    public ResponseEntity<RoomRs> deleteRoom(@RequestBody @Valid RoomRq roomRq) {
        return ResponseEntity.ok((RoomRs) proxyService.proxyOperation(roomRq, RoomRs.class));
    }
}
