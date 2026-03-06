package ru.project.reserved.system.hotel.rest.service.service.gigachat.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import ru.project.reserved.system.hotel.rest.service.properties.GigaChatProp;
import ru.project.reserved.system.hotel.rest.service.service.gigachat.GigaChatService;
import ru.project.reserved.system.hotel.rest.service.service.main.ProxyService;
import ru.project.reserved.system.hotel.rest.service.web.request.HotelRq;
import ru.project.reserved.system.hotel.rest.service.web.request.PromtRq;
import ru.project.reserved.system.hotel.rest.service.web.response.GigaChatRs;

import static ru.project.reserved.system.hotel.rest.service.constant.Promt.PROMT_GIGA_CHAT_START;


@RequiredArgsConstructor
@Service
@Slf4j
public class GigaChatServiceImpl implements GigaChatService {

    private final ProxyService proxyService;
    private final GigaChatProp gigaChatProp;
    private final OpenAiChatModel openAiChatModel;
    private final OpenAiChatOptions options;

    @Override
    public HotelRq getRsToPromtSpringAi(PromtRq promt) {
        return ChatClient.create(openAiChatModel)
                .prompt()
                .system(PROMT_GIGA_CHAT_START)
                .options(options)
                .user(promt.getContent())
                .call()
                .entity(HotelRq.class);
    }

    @Override
    @McpTool(
            name = "calculate_sum",
            description = "Calculates the sum of two integers. Useful for basic arithmetic."
    )
    public int testMcpTool(@McpToolParam(description = "The first number to add", required = true) int a,
                                  @McpToolParam(description = "The second number to add", required = true) int b) {
        log.info("Calling Test MCP Tool");
        return a + b;
    }

}
