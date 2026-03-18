package ru.project.reserved.system.hotel.rest.service.service.gigachat.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatHandlerService;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatService;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelRs;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ru.project.reserved.system.hotel.rest.service.constant.PromtSearchHotel.PROMT_GIGA_CHAT_CHECK_AND_ADDED_DATA;
import static ru.project.reserved.system.hotel.rest.service.constant.PromtSearchHotel.PROMT_GIGA_CHAT_SEARCH_HOTEL;


@RequiredArgsConstructor
@Service
@Slf4j
public class GigaChatHandlerServiceImpl implements GigaChatHandlerService {
    private final GigaChatService gigaChatService;
    private final ProxyService proxyService;
    private final RedisTemplate<UUID, Redis> redisTemplate;


    @Override
    public GigaChatRs handle(PromtRq promtRq) {
        GigaChatRs rs = gigaChatService.getRsToPromtSpringAi(promtRq, PROMT_GIGA_CHAT_SEARCH_HOTEL);
        log.info("Giga chat response: {}", rs.toString());
        if (rs.isResult()) {
            HotelRs hotelRs = (HotelRs) proxyService.proxyOperation(rs.getHotelRq(), HotelRs.class);
            return GigaChatRs.builder()
                    .hotelRs(hotelRs)
                    .build();
        }
        UUID uuid = UUID.randomUUID();
        redisTemplate.opsForValue().set(uuid, Redis.builder()
                .gigaChatRs(rs)
                .build(), Duration.ofMinutes(10));
        return gigaChatService.getRsToPromtSpringAi(promtRq, PROMT_GIGA_CHAT_CHECK_AND_ADDED_DATA);
    }
}
