package ru.project.reserved.system.hotel.rest.service.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class DbServiceRestProperties {

    @Value("${db.service.data.host}")
    private String hostData;

    @Value("${db.service.auth.host}")
    private String hostAuthDb;
}
