package ru.project.reserved.system.hotel.rest.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.model.ApiKey;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class UpdateTokenScheduler {

    private final ApplicationContext context;

    @Scheduled(cron = "${scheduler.update-token}")
    @Async
    public void updateToken(){
        log.info("Update ApiKey");
        ApiKey key = context.getBean(ApiKey.class);
        key.getValue();
    }
}
