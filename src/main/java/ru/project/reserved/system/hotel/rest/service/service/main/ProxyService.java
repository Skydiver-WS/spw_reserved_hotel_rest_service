package ru.project.reserved.system.hotel.rest.service.service.main;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;

public interface ProxyService {

    <T> Object proxyOperation(Object rq, Class<T> clazz);
    <T> Object proxyOperation(Object rq, Pageable pageable, Class<T> clazz);
    <T> Object proxyOperation(Object rq, String url, Pageable pageable, HttpMethod method, Class<T> clazz);
}
