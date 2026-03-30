package ru.project.reserved.system.hotel.rest.service.service.gigachat.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.aop.Cookie;
import ru.project.reserved.system.hotel.rest.service.dto.Redis;
import ru.project.reserved.system.hotel.rest.service.mapper.GigaChatMapper;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatHandlerService;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatService;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;
import ru.project.reserved.system.hotel.rest.service.web.response.HotelRs;

import java.time.Duration;
import java.util.*;

import static ru.project.reserved.system.hotel.rest.service.constant.PromtSearchHotel.PROMT_GIGA_CHAT_CHECK_AND_ADDED_DATA;
import static ru.project.reserved.system.hotel.rest.service.constant.PromtSearchHotel.PROMT_GIGA_CHAT_SEARCH_HOTEL;
import static ru.project.reserved.system.hotel.rest.service.utils.HotelRqValidateUtils.validateHotelRq;


@RequiredArgsConstructor
@Service
@Slf4j
public class GigaChatHandlerServiceImpl implements GigaChatHandlerService {
    private final GigaChatService gigaChatService;
    private final ProxyService proxyService;
    private final GigaChatMapper gigaChatMapper;
    private final ObjectMapper objectMapper;


    @Override
    @SneakyThrows
    public GigaChatRs handle(PromtRq promtRq) {
        GigaChatRs rs = gigaChatService.getRsToPromtSpringAi(promtRq, PROMT_GIGA_CHAT_SEARCH_HOTEL);
        log.info("Giga chat response: {}", rs.toString());
        if (rs.isResult()) {
            HotelRs hotelRs = (HotelRs) proxyService.proxyOperation(rs.getHotelRq(), HotelRs.class);
            return GigaChatRs.builder()
                    .hotelRs(hotelRs)
                    .build();
        }
        gigaChatMapper.gigaPromtRqFromGigaChatRs(rs, promtRq);
        String[] fieldsIsNull = validateHotelRq(promtRq.getGigaChatRsInCache().getHotelRq());
        log.info("Data is null: {}", Arrays.toString(fieldsIsNull));
        if (fieldsIsNull.length > 0){
            log.info("Validate completed");
            return rs;
        }
        GigaChatRs checkRs = gigaChatService.getRsToPromtSpringAi(PromtRq.builder()
                .content(Arrays.toString(fieldsIsNull))
                .build(), PROMT_GIGA_CHAT_CHECK_AND_ADDED_DATA);
        rs.setContent(checkRs.getContent());
        rs.setHotelRq(promtRq.getGigaChatRsInCache().getHotelRq());
        return rs;
    }
}
