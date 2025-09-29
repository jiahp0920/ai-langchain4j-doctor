package com.sp.llm.ailangchain4jdoctor.bean;

import lombok.Data;

/**
 * @author jiahongping
 * @version 1.0
 * @className ChatForm
 * @description TODO
 * @date 2025/9/27 15:08
 */
@Data
public class ChatForm {
    private Long memoryId;//对话id
    private String message;//用户问题
}
