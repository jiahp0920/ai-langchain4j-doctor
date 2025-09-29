package com.sp.llm.ailangchain4jdoctor.store;

import com.sp.llm.ailangchain4jdoctor.bean.ChatContext;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author jiahongping
 * @version 1.0
 * @className MongoChatMemoryStore
 * @description TODO
 * @date 2025/9/27 15:15
 */
@Component
@Slf4j
public class MongoChatMemoryStore implements ChatMemoryStore {
    @Resource
    private MongoTemplate mongoTemplate;
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        //基于memoryId查询数据库，返回聊天记录
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = Query.query(criteria);
        ChatContext chatContext = mongoTemplate.findOne(query, ChatContext.class);
        if (chatContext != null){
           return ChatMessageDeserializer.messagesFromJson(chatContext.getContent());
        }
        return new LinkedList<>();
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", ChatMessageSerializer.messagesToJson(messages));
        //根据query条件能查询出文档，则修改文档；否则新增文档
        mongoTemplate.upsert(query, update, ChatContext.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatContext.class);
    }
}
