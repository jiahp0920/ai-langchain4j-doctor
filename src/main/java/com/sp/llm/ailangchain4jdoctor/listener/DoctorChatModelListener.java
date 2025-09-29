package com.sp.llm.ailangchain4jdoctor.listener;

import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jiahongping
 * @version 1.0
 * @className DoctorChatModelListener
 * @description TODO
 * @date 2025/9/28 11:10
 */
@Component
@Slf4j
public class DoctorChatModelListener implements ChatModelListener {

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        ChatResponse chatResponse = responseContext.chatResponse();
        String text = chatResponse.aiMessage().text();
        log.info("响应：ChatModelResponse text:{}", text);
    }
}
