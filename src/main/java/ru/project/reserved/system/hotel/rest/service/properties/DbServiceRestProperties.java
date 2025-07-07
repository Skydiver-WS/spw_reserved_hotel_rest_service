package ru.project.reserved.system.hotel.rest.service.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db.service")
@Data
public class DbServiceRestProperties {

    private String url;
}
