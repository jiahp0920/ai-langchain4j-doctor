package com.sp.llm.ailangchain4jdoctor.config;

import com.sp.llm.ailangchain4jdoctor.listener.DoctorChatModelListener;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author jiahongping
 * @version 1.0
 * @className StreamingChatModelConfig
 * @description TODO
 * @date 2025/9/28 17:56
 */
@Configuration
@ConfigurationProperties(prefix = "langchain4j.community.dashscope.streaming-chat-model")
@Data
public class StreamingChatModelConfig {
    private String modelName;

    private String apiKey;

    @Resource
    private DoctorChatModelListener doctorChatModelListener;
    @Bean
    public StreamingChatModel  jhpQwenStreamingChatModel () {
        return QwenStreamingChatModel.builder()
                .modelName(modelName)
                .apiKey(apiKey)
                .listeners(List.of(doctorChatModelListener))
                .build();
    }
}
