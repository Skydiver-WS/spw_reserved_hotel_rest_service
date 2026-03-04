package ru.project.reserved.system.hotel.rest.service.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.ai.openai.api.OpenAiModerationApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import ru.project.reserved.system.hotel.rest.service.properties.GigaChatProp;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatGetTokenService;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AIConfiguration {

    private final GigaChatProp gigaChatProp;
    private final GigaChatGetTokenService gigaChatGetTokenService;

    @Bean
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi) {
        log.info("OpenAiChatModel");
        return new OpenAiChatModel(openAiApi);
    }

    @Bean
    public OpenAiChatOptions openAiChatOptions() {
        return OpenAiChatOptions.builder()
                .withModel(gigaChatProp.getModel())
                .withTemperature(0.2f)
                .build();
    }

    @Bean
    public OpenAiApi openAiApi(@Qualifier("promt-builder") RestClient.Builder restClientBuilder, WebClient.Builder clientBuilder) {
        log.info("Create OpenAiApi bean");
        return new OpenAiApi(gigaChatProp.getCreatePromt(), getToken(), restClientBuilder, clientBuilder);
    }

    @Bean
    public OpenAiEmbeddingModel openAiEmbeddingModel(OpenAiApi openAiApi) {
        log.info("OpenAiEmbeddingModel");
        return new OpenAiEmbeddingModel(openAiApi);
    }

    @Bean
    public OpenAiImageModel openAiImageModel(OpenAiImageApi openAiImageApi) {
        log.info("OpenAiImageModel");
        return new OpenAiImageModel(openAiImageApi);
    }

    @Bean
    public OpenAiImageApi openAiImageApi( @Qualifier("promt-builder") RestClient.Builder restClientBuilder) {
        log.info("OpenAiImageApi");
        return new OpenAiImageApi(gigaChatProp.getCreatePromt(), getToken(), restClientBuilder);
    }

    @Bean
    public OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel(OpenAiAudioApi audioApi) {
        log.info("OpenAiAudioTranscriptionModel");
        return new OpenAiAudioTranscriptionModel(audioApi);
    }

    @Bean
    public OpenAiAudioApi openAiAudioApi(@Qualifier("promt-builder") RestClient.Builder restClientBuilder, ResponseErrorHandler errorHandler) {
        log.info("OpenAiAudioApi");
        return new OpenAiAudioApi(gigaChatProp.getCreatePromt(), getToken(), restClientBuilder, errorHandler);
    }

    @Bean
    public OpenAiModerationModel openAiModerationModel(OpenAiModerationApi openAiModerationApi){
        log.info("OpenAiModerationModel");
        return new OpenAiModerationModel(openAiModerationApi);
    }

    @Bean
    public OpenAiModerationApi openAiModerationApi(@Qualifier("promt-builder") RestClient.Builder restClientBuilder, ResponseErrorHandler errorHandler) {
        log.info("OpenAiModerationApi");
        return new OpenAiModerationApi(gigaChatProp.getCreatePromt(), getToken(), restClientBuilder, errorHandler);
    }

    @Bean
    public OpenAiAudioSpeechModel openAiAudioSpeechModel(OpenAiAudioApi audioApi) {
        log.info("OpenAiAudioSpeechModel");
        return new OpenAiAudioSpeechModel(audioApi);
    }

//    @Bean
//    public ChatLanguageModel chatLanguageModel(OpenAiApi openAiApi){
//        return new dev.langchain4j.model.openai.OpenAiChatModel();
//    }
    
    private String getToken(){
        return gigaChatGetTokenService.gotGigaChatToken().getToken();
    }
}
