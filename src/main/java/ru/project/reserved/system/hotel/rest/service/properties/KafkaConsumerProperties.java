package ru.project.reserved.system.hotel.rest.service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.kafka.consumer.topic")
public class KafkaConsumerProperties {

    private String [] topicList;
}
