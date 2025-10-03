package ru.project.reserved.system.hotel.rest.service.service.main;

public interface ProxyService {

    <T> Object proxyOperation(Object rq, Class<T> clazz);
}
