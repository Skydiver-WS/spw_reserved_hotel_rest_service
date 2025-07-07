package ru.project.reserved.system.hotel.rest.service.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class RestConfiguration {

    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }
}
