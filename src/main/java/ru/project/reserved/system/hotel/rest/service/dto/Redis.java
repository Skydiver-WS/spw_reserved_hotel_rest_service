package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Token")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Redis implements Serializable {
    private String token;
}
