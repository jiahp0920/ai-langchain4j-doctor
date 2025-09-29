package com.sp.llm.ailangchain4jdoctor.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jiahongping
 * @version 1.0
 * @className WeatherTool
 * @description TODO
 * @date 2025/9/28 16:19
 */
@Slf4j
@Component
public class WeatherTool {
    @Tool(name = "查询天气", value = "根据城市名称查询天气")
    public String queryWeather(String city){
        log.info("查询天气：{}", city);
        return city+":"+"晴天、26度";
    }
}
