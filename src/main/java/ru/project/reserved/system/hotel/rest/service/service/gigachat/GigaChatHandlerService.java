package ru.project.reserved.system.hotel.rest.service.service.gigachat;

import org.springframework.data.domain.Pageable;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;

public interface GigaChatHandlerService {

        GigaChatRs handle(PromtRq promtRq, Pageable pageable);
}
