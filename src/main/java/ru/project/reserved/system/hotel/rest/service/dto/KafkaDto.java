package ru.project.reserved.system.hotel.rest.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.project.reserved.system.hotel.rest.service.dto.type.TopicType;

@Getter
@Setter
@Builder
public class KafkaDto {
    private TopicType topic;
    private String key;
}
