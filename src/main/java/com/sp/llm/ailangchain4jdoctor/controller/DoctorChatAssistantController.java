package com.sp.llm.ailangchain4jdoctor.controller;

import com.sp.llm.ailangchain4jdoctor.assistant.DoctorChatAssistant;
import com.sp.llm.ailangchain4jdoctor.bean.ChatForm;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author jiahongping
 * @version 1.0
 * @className DoctorChatAssistantController
 * @description TODO
 * @date 2025/9/27 15:30
 */
@Slf4j
@Resource
@RequestMapping("/sp")
@RestController
public class DoctorChatAssistantController {
    @Resource
    private DoctorChatAssistant doctorChatAssistant;

    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatForm chatForm)  {
        //try catch
        try {
            log.info("用户提问：{}", chatForm.getMessage());
            return doctorChatAssistant.chat(chatForm.getMemoryId(), chatForm.getMessage());
        } catch (Exception e) {
            return Flux.just("您输入的问题涉及敏感词,请检查输入的合法性");
        }

    }
}
