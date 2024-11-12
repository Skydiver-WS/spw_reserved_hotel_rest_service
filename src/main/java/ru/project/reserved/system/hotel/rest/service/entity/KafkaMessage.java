package ru.project.reserved.system.hotel.rest.service.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("KafkaMessage")
@Data
@Builder
public class KafkaMessage implements Serializable {

    @Id
    private String id;
    private String message;
}
