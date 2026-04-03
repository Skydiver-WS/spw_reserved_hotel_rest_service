package ru.project.reserved.system.hotel.rest.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ai.model.ApiKey;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatGetTokenService;

@RequiredArgsConstructor
@Component
@Slf4j
public class UpdateTokenScheduler {

    private final GigaChatGetTokenService gigaChatGetTokenService;
    private static String token;


    public  String getToken(){
        if (Strings.isBlank(token)){
            token = gigaChatGetTokenService.gotGigaChatToken().getToken();
        }
        return token;
    }

    @Scheduled(cron = "${scheduler.update-token}")
    @Async
    public void updateToken() {
        log.info("Update ApiKey");
        token = gigaChatGetTokenService.gotGigaChatToken().getToken();
    }
}
