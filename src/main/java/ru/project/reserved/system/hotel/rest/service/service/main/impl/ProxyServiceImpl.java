package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.project.reserved.system.hotel.rest.service.dto.RestDataDto;
import ru.project.reserved.system.hotel.rest.service.properties.DbServiceRestProperties;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.service.main.RestService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyServiceImpl implements ProxyService {

    private final RestService restService;
    private final DbServiceRestProperties prop;

    @Override
    public <T> Object proxyOperation(Object rq, Class<T> clazz) {
        RestDataDto restDataDto = createData(rq, null);
        return restService.sendData(restDataDto, clazz).getBody();
    }

    @Override
    public <T> Object proxyOperation(Object rq, HttpMethod method, Class<T> clazz) {
        RestDataDto restDataDto = createData(rq, method);
        return restService.sendData(restDataDto, clazz).getBody();
    }

    private RestDataDto createData(Object request, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String param = request instanceof Pageable p ?
                String.format("?page=%d&size=%d", p.getPageNumber(), p.getPageSize()) : "";
        return RestDataDto.builder()
                .headers(headers)
                .url(host() + uri() + param)
                .method(Objects.isNull(method) ? httpMethod() : method)
                .body(request)
                .build();
    }

    private HttpServletRequest getHttpAttributes(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)){
            log.error("ServletRequest is null");
            throw new RuntimeException();
        }
        return attributes.getRequest();
    }

    private String uri(){
        String uri = getHttpAttributes().getRequestURI();
        if(uri.contains("booking/update")){
            uri = uri.replaceAll("/update", "");
        } else if (uri.contains("booking/remove")){
            uri = uri.replaceAll("/remove", "");
        }
        return uri;
    }

    private HttpMethod httpMethod(){
        String uri = getHttpAttributes().getRequestURI();
        if (uri.contains("booking/update") || uri.contains("booking/remove")){
            return HttpMethod.POST;
        }
        return HttpMethod.valueOf(getHttpAttributes().getMethod());
    }

    private String host(){
        String uri = getHttpAttributes().getRequestURI();
        if(uri.contains("/api/v1/user")){
            return prop.getHostAuthDb();
        }
        return prop.getHostData();
    }
}
