package ru.project.reserved.system.hotel.rest.service.configuration;

import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ai.model.ApiKey;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import ru.project.reserved.system.hotel.rest.service.properties.GigaChatProp;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatGetTokenService;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AIConfiguration {

    private final GigaChatProp gigaChatProp;
    private final GigaChatGetTokenService gigaChatGetTokenService;
    private static String token;

    @Bean
    @Primary
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi, OpenAiChatOptions defaultOptions, ToolCallingManager toolCallingManager, RetryTemplate retryTemplate, ObservationRegistry observationRegistry) {
        log.info("OpenAiChatModel");
        return new OpenAiChatModel(openAiApi, defaultOptions, toolCallingManager, retryTemplate, observationRegistry);
    }

    @Bean
    @Primary
    public OpenAiChatOptions openAiChatOptions() {
        return OpenAiChatOptions.builder()
                .model(gigaChatProp.getModel())
                .temperature(0.2)
                .build();
    }

    @Bean
    @Primary
    public OpenAiApi openAiApi(ApiKey apiKey, @Qualifier("promt-headers") MultiValueMap<String, String> headers,
                               @Qualifier("promt-builder") RestClient.Builder restClientBuilder,
                               WebClient.Builder clientBuilder,
                               ResponseErrorHandler responseErrorHandler) {
        log.info("Create OpenAiApi bean");
        return new OpenAiApi(gigaChatProp.getCreatePromt(), apiKey, headers, gigaChatProp.getUrlPromt(), "/stub", restClientBuilder, clientBuilder, responseErrorHandler);
    }

    @Bean
    @Primary
    public OpenAiEmbeddingModel openAiEmbeddingModel(OpenAiApi openAiApi) {
        log.info("OpenAiEmbeddingModel");
        return new OpenAiEmbeddingModel(openAiApi);
    }

    @Bean
    @Primary
    public ApiKey apiKey(){
        log.info("Create ApiKey");
        return new SimpleApiKey(getToken());
    }

    @Bean("promt-headers")
    public MultiValueMap<String, String> createGigaChatHeadersPromt() {
        log.info("Create bean headers for promt");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        return headers;
    }

    private String getToken(){
        if(Strings.isBlank(token)){
            token = gigaChatGetTokenService.gotGigaChatToken().getToken();
        }
        return token;
    }
}
