package ru.project.reserved.system.hotel.rest.service.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.properties.RedisProperties;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListenerKafka {

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisProperties redisProperties;


    @KafkaListener(groupId = "${spring.kafka.producer.topic.kafkaMessageGroupId}",
            topics = "#{@kafkaConsumerTopics}",
            containerFactory = "kafkaListenerContainerFactory")
    public void kafkaListener(String message,
                              @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
           log.info("Get key {}, message: {}", key, message);
           redisTemplate.opsForValue().set(key, message, redisProperties.getTimeLiveMessages());
    }
}
