package ru.project.reserved.system.hotel.rest.service.dto;

import org.springframework.data.redis.core.RedisHash;

@RedisHash("Token")
public class Redis {
    private String token;
}
