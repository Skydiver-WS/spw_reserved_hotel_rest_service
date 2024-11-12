package ru.project.reserved.system.hotel.rest.service.service.main;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.dto.KafkaDto;
import ru.project.reserved.system.hotel.rest.service.dto.KafkaResponseDto;
import ru.project.reserved.system.hotel.rest.service.listener.ListenerKafka;

@Component
@Slf4j
@RequiredArgsConstructor
@Getter
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ListenerKafka listenerKafka;

    private String message;
    private String key;
    private String keySend;
    private Acknowledgment ack;



    @EventListener
    private void getMessageGroupDataBase(KafkaResponseDto responseDto) {
        log.info("Get message: {}", message);
        this.message = responseDto.getMessage();
        this.key = responseDto.getKey();
        this.ack = responseDto.getAck();
    }

    @Override
    @SneakyThrows
    public String getResponseFromKafka() {
        Thread.sleep(1000);
        if (keySend.equals(key)){
            log.debug("Return message: {}", message);
            String currentMessage = message;
            message = null; // Сброс значения после возврата
            ack.acknowledge();
            return currentMessage;
        }
        return null;
    }

    @Override
    public void sendMessage(KafkaDto kafkaDto, String message) {
        log.info("Sending message to {}", message);
        kafkaTemplate.send(kafkaDto.getTopic().getTopic(),
                kafkaDto.getKey(), message);
        this.keySend = kafkaDto.getKey();
        log.info("Message send is successful");
    }

}
