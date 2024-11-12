package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.kafka.support.Acknowledgment;

@Data
@Builder
public class KafkaResponseDto {

    private String key;
    private String message;
    private Acknowledgment ack;
}
