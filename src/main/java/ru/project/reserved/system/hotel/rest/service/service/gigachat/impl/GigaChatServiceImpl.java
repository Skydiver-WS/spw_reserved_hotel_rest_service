package ru.project.reserved.system.hotel.rest.service.service.gigachat.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatService;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;

import static ru.project.reserved.system.hotel.rest.service.constant.PromtSearchHotel.PROMT_GIGA_CHAT_START;


@RequiredArgsConstructor
@Service
@Slf4j
public class GigaChatServiceImpl implements GigaChatService {

    private final OpenAiChatModel openAiChatModel;
    private final OpenAiChatOptions options;

    @Override
    public GigaChatRs getRsToPromtSpringAi(PromtRq promt, String promtDefault) {
        return ChatClient.create(openAiChatModel)
                .prompt()
                .system(PROMT_GIGA_CHAT_START + promtDefault)
                .options(options)
                .user(promt.getContent())
                .call()
                .entity(GigaChatRs.class);
    }

}
