package com.sp.llm.ailangchain4jdoctor.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author jiahongping
 * @version 1.0
 * @className WebSearchMcpClient
 * @description 用户提问的问题，不再知识库范围以内的，大模型调用网页搜索，返回结果
 * @date 2025/9/28 10:18
 */
@Slf4j
@Configuration
public class WebSearchMcpClient {
    @Value("${bigmodel.api-key}")
    private String apiKey;

    @Value("${weath.api-key}")
    private String weatherApiKey;

    @Bean
    public McpToolProvider mcpToolProvider() {
        // 和 MCP 服务通讯
        McpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl("https://open.bigmodel.cn/api/mcp/web_search/sse?Authorization=" + apiKey)
                .logRequests(true) // 开启日志，查看更多信息
                .logResponses(true)
                .build();

        // 创建 MCP 客户端
        McpClient mcpClient = new DefaultMcpClient.Builder()
                .key("yupiMcpClient")
                .transport(transport)
                .build();

        //mac系统不支持，要改成http形式的
//        McpTransport transport2 = new StdioMcpTransport.Builder()
//                .command(List.of("cmd", "/c", "npx", "-y", "@baidumap/mcp-server-baidu-map"))
//                .environment(Map.of("BAIDU_MAP_API_KEY", weatherApiKey))
//                .build();
//
//        // 2.构建McpClient客户端
//
//
//        McpClient mcpClient2 = new DefaultMcpClient.Builder()
//                .transport(transport2)
//                .build();
        // 从 MCP 客户端获取工具
        McpToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();
        return toolProvider;
    }
}
