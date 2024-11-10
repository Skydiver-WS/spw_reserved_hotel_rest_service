package ru.project.reserved.system.hotel.rest.service.service.main;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.dto.KafkaDto;
import ru.project.reserved.system.hotel.rest.service.listener.ListenerKafka;

@Component
@Slf4j
@RequiredArgsConstructor
@Getter
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ListenerKafka listenerKafka;

    private String message;



    @EventListener
    private void getMessageGroupDataBase(String message) {
        log.info("Get message: {}", message);
        this.message = message;
    }

    @Override
    @SneakyThrows
    public String getResponseFromKafka() {
        Thread.sleep(1000);
        log.info("Return message: {}", message);
        String currentMessage = message;
        message = null; // Сброс значения после возврата
        return currentMessage;
    }

    @Override
    public void sendMessage(KafkaDto kafkaDto, String message) {
        log.info("Sending message to {}", message);
        kafkaTemplate.send(kafkaDto.getTopic().getTopic(),
                kafkaDto.getKeyType().getKey(), message);
        log.info("Message send is successful");
    }

}
