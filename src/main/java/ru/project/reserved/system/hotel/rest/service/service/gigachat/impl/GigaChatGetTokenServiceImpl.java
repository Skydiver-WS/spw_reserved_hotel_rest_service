package ru.project.reserved.system.hotel.rest.service.service.gigachat.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.dto.type.BeanType;
import ru.project.reserved.system.hotel.rest.service.properties.GigaChatProp;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatGetTokenService;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.service.main.RestService;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;


import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class GigaChatGetTokenServiceImpl implements GigaChatGetTokenService {

    private final RestService restService;
    private final GigaChatProp gigaChatProp;

    public static String token;

    @Override
    public GigaChatRs gotGigaChatToken() {
        log.info("Got gigaChat token");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("scope", "GIGACHAT_API_PERS");
        ResponseEntity<GigaChatRs> rs = restService.sendData(RestDataDto.builder()
                .url(gigaChatProp.getHost())
                .method(HttpMethod.POST)
                .body(body)
                .build(), BeanType.GET_TOKEN, GigaChatRs.class);
        GigaChatRs gigaChatRs = rs.getBody();
        token = Objects.nonNull(gigaChatRs) ? gigaChatRs.getToken() : "";
        log.info("GigaChat token get is successful");
        return gigaChatRs;
    }


}
