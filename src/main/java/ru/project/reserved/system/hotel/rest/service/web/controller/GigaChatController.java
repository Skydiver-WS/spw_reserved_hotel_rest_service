package ru.project.reserved.system.hotel.rest.service.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.reserved.system.hotel.rest.service.aop.Cookie;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatHandlerService;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatService;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;

import static ru.project.reserved.system.hotel.rest.service.constant.PromtSearchHotel.PROMT_GIGA_CHAT_SEARCH_HOTEL;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/ai")
public class GigaChatController {

    private final GigaChatHandlerService gigaChatHandlerService;


    @PostMapping("/promt/spring")
    @Cookie
    public ResponseEntity<GigaChatRs> promtSpringAi(@RequestBody PromtRq promt){
        log.info("Send promt request to GigaChat");
        return ResponseEntity.ok(gigaChatHandlerService.handle(promt));
    }


}
