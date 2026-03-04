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
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi, OpenAiChatOptions defaultOptions, ToolCallingManager toolCallingManager, RetryTemplate retryTemplate, ObservationRegistry observationRegistry) {
        log.info("OpenAiChatModel");
        return new OpenAiChatModel(openAiApi, defaultOptions, toolCallingManager, retryTemplate, observationRegistry);
    }

    @Bean
    public OpenAiChatOptions openAiChatOptions() {
        return OpenAiChatOptions.builder()
                .model(gigaChatProp.getModel())
                .temperature(0.2)
                .build();
    }

    @Bean
    public OpenAiApi openAiApi(ApiKey apiKey, @Qualifier("promt-headers") MultiValueMap<String, String> headers,  @Qualifier("promt-builder") RestClient.Builder restClientBuilder, WebClient.Builder clientBuilder, ResponseErrorHandler responseErrorHandler) {
        log.info("Create OpenAiApi bean");
        return new OpenAiApi(gigaChatProp.getCreatePromt(), apiKey, headers, gigaChatProp.getUrlPromt(), "", restClientBuilder, clientBuilder, responseErrorHandler);
    }

    @Bean
    public OpenAiEmbeddingModel openAiEmbeddingModel(OpenAiApi openAiApi) {
        log.info("OpenAiEmbeddingModel");
        return new OpenAiEmbeddingModel(openAiApi);
    }

    @Bean
    public ApiKey apiKey(){
        return new SimpleApiKey(getToken());
    }

    @Bean("promt-headers")
    public MultiValueMap<String, String> createGigaChatHeadersPromt() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        return headers;
    }

//    @Bean
//    public OpenAiImageModel openAiImageModel(OpenAiImageApi openAiImageApi) {
//        log.info("OpenAiImageModel");
//        return new OpenAiImageModel(openAiImageApi);
//    }
//
//    @Bean
//    public OpenAiImageApi openAiImageApi( @Qualifier("promt-builder") RestClient.Builder restClientBuilder) {
//        log.info("OpenAiImageApi");
//        return new OpenAiImageApi(gigaChatProp.getCreatePromt(), getToken(), restClientBuilder);
//    }
//
//    @Bean
//    public OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel(OpenAiAudioApi audioApi) {
//        log.info("OpenAiAudioTranscriptionModel");
//        return new OpenAiAudioTranscriptionModel(audioApi);
//    }
//
//    @Bean
//    public OpenAiAudioApi openAiAudioApi(@Qualifier("promt-builder") RestClient.Builder restClientBuilder, ResponseErrorHandler errorHandler) {
//        log.info("OpenAiAudioApi");
//        return new OpenAiAudioApi(gigaChatProp.getCreatePromt(), getToken(), restClientBuilder, errorHandler);
//    }
//
//    @Bean
//    public OpenAiModerationModel openAiModerationModel(OpenAiModerationApi openAiModerationApi){
//        log.info("OpenAiModerationModel");
//        return new OpenAiModerationModel(openAiModerationApi);
//    }
//
//    @Bean
//    public OpenAiModerationApi openAiModerationApi(@Qualifier("promt-builder") RestClient.Builder restClientBuilder, ResponseErrorHandler errorHandler) {
//        log.info("OpenAiModerationApi");
//        return new OpenAiModerationApi(gigaChatProp.getCreatePromt(), getToken(), restClientBuilder, errorHandler);
//    }
//
//    @Bean
//    public OpenAiAudioSpeechModel openAiAudioSpeechModel(OpenAiAudioApi audioApi) {
//        log.info("OpenAiAudioSpeechModel");
//        return new OpenAiAudioSpeechModel(audioApi);
//    }

//    @Bean
//    public ChatLanguageModel chatLanguageModel(OpenAiApi openAiApi){
//        return new dev.langchain4j.model.openai.OpenAiChatModel();
//    }

    private String getToken(){
        if(Strings.isBlank(token)){
            token = gigaChatGetTokenService.gotGigaChatToken().getToken();
        }
        return token;
    }
}
