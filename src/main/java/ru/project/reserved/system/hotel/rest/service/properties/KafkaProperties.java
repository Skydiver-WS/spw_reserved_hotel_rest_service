package ru.project.reserved.system.hotel.rest.service.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.kafka.consumer.topic")
public class KafkaProperties {
    @Value("${spring.kafka.bootstrap-servers}")
    private String url;

    @Value("${spring.kafka.consumer.topic.kafkaMessageGroupId}")
    private String messageGroupId;

    @Value("${spring.kafka.consumer.topic.default-topic}")
    private String topic;

    private String[] topicList;

    private String[] typeKey;
}
