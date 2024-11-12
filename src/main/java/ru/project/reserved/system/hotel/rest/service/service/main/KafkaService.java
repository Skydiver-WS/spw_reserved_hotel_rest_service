package ru.project.reserved.system.hotel.rest.service.service.main;

import ru.project.reserved.system.hotel.rest.service.dto.KafkaDto;

public interface KafkaService {
    String getResponseFromKafka(String key);

    void sendMessage(KafkaDto kafkaDto, String message);
}
