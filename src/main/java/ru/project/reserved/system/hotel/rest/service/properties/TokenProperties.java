package ru.project.reserved.system.hotel.rest.service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Data
@ConfigurationProperties(prefix = "token.signing")
public class TokenProperties {
    private String key;
    private Duration expiration;
}
