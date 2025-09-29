package com.sp.llm.ailangchain4jdoctor.config;

import com.sp.llm.ailangchain4jdoctor.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiahongping
 * @version 1.0
 * @className DoctorChatAssistantLLMConfig
 * @description 聊天内容持久化
 * @date 2025/9/27 15:30
 */
@Configuration
public class ChatMemoryConfig {
    @Resource
    private MongoChatMemoryStore mongoChatMemoryStore;
    //创建一个聊天内存提供者
    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .chatMemoryStore(mongoChatMemoryStore)
                .maxMessages(200)
                .build();
    }

}
