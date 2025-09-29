package com.sp.llm.ailangchain4jdoctor.guardrail;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailException;
import dev.langchain4j.guardrail.InputGuardrailResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author jiahongping
 * @version 1.0
 * @className SentensiveWordInputGuardrail
 * @description 输入的敏感词检查
 * @date 2025/9/28 11:07
 */
@Slf4j
public class SensitiveWordInputGuardrail implements InputGuardrail {
    private static final Set<String> sensitiveWords = Set.of("性生活", "强奸");

    /**
     * 检测用户输入是否安全
     */
    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        // 获取用户输入并转换为小写以确保大小写不敏感
        String inputText = userMessage.singleText().toLowerCase();
        // 使用正则表达式分割输入文本为单词
        // 遍历所有单词，检查是否存在敏感词
        if (sensitiveWords.contains(inputText)) {
            log.warn("Sensitive word detected: " + inputText);
              throw new InputGuardrailException("Sensitive word detected: " + inputText);
            //return fatal("Sensitive word detected: " + inputText);
           // return failure("Sensitive word detected: " + inputText);
        }
        return success();
    }
}
