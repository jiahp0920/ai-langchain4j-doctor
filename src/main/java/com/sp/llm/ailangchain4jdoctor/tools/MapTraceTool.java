package com.sp.llm.ailangchain4jdoctor.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jiahongping
 * @version 1.0
 * @className MapTraceTool
 * @description 地图轨迹查询
 * @date 2025/9/28 16:23
 */
@Slf4j
@Component
public class MapTraceTool {
    @Tool(name = "地图轨迹查询", value = "根据地址查询到达目的的过程或路线")
    public String queryMapTrace(String address){
        //TODO 调用百度地图接口查询地图轨迹
        return address + ",按照如下轨迹就可以到达目的地: 1,做地跌到三山街站2号口出 2，然后向东走150米到汽车站 3、乘坐111公交车到南京站下 4、南京站向东过马路走200米，到达目的地";
    }
}
