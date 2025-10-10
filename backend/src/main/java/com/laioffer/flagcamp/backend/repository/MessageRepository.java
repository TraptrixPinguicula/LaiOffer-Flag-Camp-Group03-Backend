package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Message 数据访问层
 * 提供对 messages 表的 CRUD 操作
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    /**
     * 根据会话ID查询所有消息
     * @param conversationId 会话ID
     * @return 该会话的所有消息列表
     */
    List<Message> findByConversationId(Integer conversationId);

    /**
     * 根据发送者ID查询所有消息
     * @param senderId 发送者用户ID
     * @return 该用户发送的所有消息列表
     */
    List<Message> findBySenderId(Integer senderId);

    /**
     * 根据会话ID查询所有消息，按创建时间降序排列（最新的在最上面）
     * @param conversationId 会话ID
     * @return 该会话的所有消息列表（最新消息在前）
     */
    List<Message> findByConversationIdOrderByCreatedAtDesc(Integer conversationId);
}
