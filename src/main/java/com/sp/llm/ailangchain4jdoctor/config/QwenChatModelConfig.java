package com.sp.llm.ailangchain4jdoctor.config;

import com.sp.llm.ailangchain4jdoctor.listener.DoctorChatModelListener;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "langchain4j.community.dashscope.chat-model")
@Data
public class QwenChatModelConfig {

    private String modelName;

    private String apiKey;

    @Resource
    private DoctorChatModelListener doctorChatModelListener;
//    @Resource
//    private ChatModelListener chatModelListener;

    @Bean
    public ChatModel jhpChatModel() {
        return QwenChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .listeners(List.of(doctorChatModelListener))
                .build();
    }
}
