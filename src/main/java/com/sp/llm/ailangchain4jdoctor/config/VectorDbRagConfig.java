//package com.sp.llm.ailangchain4jdoctor.config;
//
//import dev.langchain4j.data.document.Document;
//import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
//import dev.langchain4j.data.segment.TextSegment;
//import dev.langchain4j.model.embedding.EmbeddingModel;
//import dev.langchain4j.rag.content.retriever.ContentRetriever;
//import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
//import dev.langchain4j.store.embedding.EmbeddingStore;
//import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
//import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
//import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
//import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
//import jakarta.annotation.Resource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author jiahongping
// * @version 1.0
// * @className RagConfig
// * @description 基于外部的向量数据库
// * @date 2025/9/27 16:14
// */
//@Configuration
//public class VectorDbRagConfig {
//
//    @Resource
//    private EmbeddingStore pineconEembeddingStore;
//
//    @Resource
//    private EmbeddingModel embeddingModel;
//
//    //创建一个嵌入存储实例，用于存储文本片段的嵌入向量 基于 Pinecone
//    @Bean
//    public EmbeddingStore<TextSegment> pineconEembeddingStore() {
//        EmbeddingStore<TextSegment> embeddingStore = PineconeEmbeddingStore.builder()
//                .apiKey(System.getenv("PINECONE_API_KEY"))
//                .index("zoe-index")//如果指定的索引不存在，将创建一个新的索引
//                .nameSpace("zoe-namespace") //如果指定的名称空间不存在，将创建一个新的名称空间
//                .createIndex(PineconeServerlessIndexConfig.builder()
//                        .cloud("AWS") //指定索引部署在 AWS 云服务上。
//                        .region("us-east-1") //指定索引所在的 AWS 区域为 us-east-1。
//                        .dimension(embeddingModel.dimension()) //指定索引的向量维度，该维度与 embeddedModel 生成的向量维度相同。
//                        .build())
//                .build();
//        return embeddingStore;
//    }
//
//
//
//    //创建一个内容检索器
//    @Bean
//    public ContentRetriever contentRetriever(){
//        // 创建一个 EmbeddingStoreContentRetriever 对象，用于从嵌入存储中检索内容
//        return EmbeddingStoreContentRetriever
//                .builder()
//                // 设置用于生成嵌入向量的嵌入模型
//                .embeddingModel(embeddingModel)
//                // 指定要使用的嵌入存储
//                .embeddingStore(pineconEembeddingStore)
//                // 设置最大检索结果数量，这里表示最多返回 1 条匹配结果
//                .maxResults(1)
//                // 设置最小得分阈值，只有得分大于等于 0.8 的结果才会被返回
//                .minScore(0.8)
//                // 构建最终的 EmbeddingStoreContentRetriever 实例
//                .build();
//
//    }
//}
