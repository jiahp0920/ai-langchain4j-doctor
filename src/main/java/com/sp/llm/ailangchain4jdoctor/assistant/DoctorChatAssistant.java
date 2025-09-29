package com.sp.llm.ailangchain4jdoctor.assistant;

import com.sp.llm.ailangchain4jdoctor.guardrail.SensitiveWordInputGuardrail;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * @author jiahongping
 * @version 1.0
 * @className DoctorChatAssistant
 * @description 医疗小智
 * @date 2025/9/27 13:59
 */
//@AiService(
//        wiringMode = EXPLICIT,
//        streamingChatModel = "qwenStreamingChatModel",
//        chatMemoryProvider = "chatMemoryProvider",
//        tools = "appointmentTool", //tools配置
//        contentRetriever = "contentRetrieverMemory" //配置向量存储:先直接存储到内存
//
//)
@InputGuardrails({SensitiveWordInputGuardrail.class})
public interface DoctorChatAssistant {
    //聊天
    @SystemMessage(fromResource = "zoe-prompt-template.txt")
    Flux<String> chat(@MemoryId Long memoryId, @UserMessage String userMessage);


    String chatListener(String userMessage);
}
