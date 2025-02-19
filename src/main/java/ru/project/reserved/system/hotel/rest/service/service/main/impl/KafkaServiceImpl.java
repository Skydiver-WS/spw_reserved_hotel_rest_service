package ru.project.reserved.system.hotel.rest.service.service.main.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.dto.KafkaDto;
import ru.project.reserved.system.hotel.rest.service.listener.ListenerKafka;
import ru.project.reserved.system.hotel.rest.service.service.main.KafkaService;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@Getter
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final ListenerKafka listenerKafka;


    @Override
    @SneakyThrows
    public String getResponseFromKafka(String key) {
        Thread.sleep(1000);
        int count = 1;
        String response;
        while (count <= 5) {
            response = redisTemplate.opsForValue().get(key);
            if (Strings.isNotBlank(response)) {
                redisTemplate.delete(key);
                return response;
            }
            Thread.sleep(5000);
            log.info("Waiting response from db service. Attempt {}", count);
            count++;
        }
        return null;
    }

    @Override
    public void sendMessage(KafkaDto kafkaDto, String message) {
        log.info("Sending message to {}", message);
        String key = UUID.randomUUID().toString();

        kafkaTemplate.send(kafkaDto.getTopic().getTopic(),
                kafkaDto.getKey(), message);
        log.info("Message send is successful");
    }

}
