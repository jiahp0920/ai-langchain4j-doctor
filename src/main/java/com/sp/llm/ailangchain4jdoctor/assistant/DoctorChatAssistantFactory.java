package com.sp.llm.ailangchain4jdoctor.assistant;

import com.sp.llm.ailangchain4jdoctor.listener.DoctorChatModelListener;
import com.sp.llm.ailangchain4jdoctor.store.MongoChatMemoryStore;
import com.sp.llm.ailangchain4jdoctor.tools.AppointmentTool;
import com.sp.llm.ailangchain4jdoctor.tools.MapTraceTool;
import com.sp.llm.ailangchain4jdoctor.tools.WeatherTool;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author jiahongping
 * @version 1.0
 * @className DoctorChatAssistantFactory
 * @description TODO
 * @date 2025/9/28 10:58
 */
@Configuration
public class DoctorChatAssistantFactory {

    @Resource
    private ChatModel jhpChatModel;

    @Resource
    private StreamingChatModel jhpQwenStreamingChatModel;

    @Resource
    private ContentRetriever contentRetrieverMemory;

    @Resource
    private McpToolProvider mcpToolProvider;

    @Resource
    private ChatMemoryProvider chatMemoryProvider;

    @Resource
    private AppointmentTool appointmentTool;

    @Resource
    private WeatherTool weatherTool;

    @Resource
    private MapTraceTool mapTraceTool;

    @Resource
    private DoctorChatModelListener doctorChatModelListener;

    @Bean
    public DoctorChatAssistant doctorChatAssistant() {
        List<ChatModelListener> listeners = jhpChatModel.listeners();
        listeners.add(doctorChatModelListener);

        return AiServices.builder(DoctorChatAssistant.class)
                .chatModel(jhpChatModel) //返回字符串的测试
                .streamingChatModel(jhpQwenStreamingChatModel)
                .chatMemoryProvider(chatMemoryProvider) // 每个会话独立存储
                .contentRetriever(contentRetrieverMemory) // RAG 检索增强生成
                .tools(appointmentTool, weatherTool, mapTraceTool) // 工具调用
                .toolProvider(mcpToolProvider) // MCP 工具调用

                .build();
    }
}
