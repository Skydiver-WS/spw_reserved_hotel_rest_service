package ru.project.reserved.system.hotel.rest.service.service.main;

import org.springframework.http.HttpMethod;

public interface ProxyService {

    <T> Object proxyOperation(Object rq, Class<T> clazz);
    <T> Object proxyOperation(Object rq, HttpMethod method, Class<T> clazz);
}
