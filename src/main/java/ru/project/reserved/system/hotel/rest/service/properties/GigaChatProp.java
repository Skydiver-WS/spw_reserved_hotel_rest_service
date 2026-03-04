package ru.project.reserved.system.hotel.rest.service.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Component
@ConfigurationProperties(prefix = "giga-chat.property")
public class GigaChatProp {

    private String model;
    private String host;
    private String getModelsUrl;
    private String createPromt;
    private String urlPromt;
    private String authToken;
}
