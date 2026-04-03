package ru.project.reserved.system.hotel.rest.service.service.gigachat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.model.ApiKey;
import org.springframework.stereotype.Component;
import ru.project.reserved.system.hotel.rest.service.scheduler.UpdateTokenScheduler;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomApiKeyService implements ApiKey {

    private final UpdateTokenScheduler updateTokenScheduler;

    @Override
    public String getValue() {
        log.info("Getting token");
        return updateTokenScheduler.getToken();
    }
}
