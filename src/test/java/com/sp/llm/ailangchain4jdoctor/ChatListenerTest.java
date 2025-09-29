package com.sp.llm.ailangchain4jdoctor;

import com.sp.llm.ailangchain4jdoctor.assistant.DoctorChatAssistant;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jiahongping
 * @version 1.0
 * @className ChatListenerTest
 * @description TODO
 * @date 2025/9/28 17:48
 */
@SpringBootTest
public class ChatListenerTest {
    @Resource
    private DoctorChatAssistant doctorChatAssistant;

    @Test
    public void test() {
        String result = doctorChatAssistant.chatListener("请给我一个关于如何使用ChatGPT的例子");
        System.out.println(result);
    }
}
