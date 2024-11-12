package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class KafkaDto {
    private TopicType topic;
    private String key;
}
