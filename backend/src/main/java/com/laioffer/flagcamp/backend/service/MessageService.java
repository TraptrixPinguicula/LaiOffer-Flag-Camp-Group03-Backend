package com.laioffer.flagcamp.backend.service;

import com.laioffer.flagcamp.backend.entity.Message;
import com.laioffer.flagcamp.backend.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Message 服务层
 * 提供消息相关的业务逻辑处理
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    // 使用构造函数注入依赖
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * 发送新消息
     * @param senderId 发送者ID
     * @param conversationId 会话ID
     * @param content 消息内容
     * @return 保存后的消息对象（包含自动生成的ID和时间戳）
     */
    @Transactional
    public Message sendMessage(Integer senderId, Integer conversationId, String content) {
        // 使用静态工厂方法创建新消息，messageId 为 null 由数据库自动生成
        Message newMessage = Message.createNew(senderId, conversationId, content);
        return messageRepository.save(newMessage);
    }

    /**
     * 根据会话ID获取所有消息（按时间降序排列，最新的在最上面）
     * @param conversationId 会话ID
     * @return 该会话的所有消息列表，最新消息在前
     */
    public List<Message> getMessagesByConversation(Integer conversationId) {
        return messageRepository.findByConversationIdOrderByCreatedAtDesc(conversationId);
    }

    /**
     * 根据消息ID获取单条消息
     * @param messageId 消息ID
     * @return 消息对象
     * @throws RuntimeException 如果消息不存在
     */
    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));
    }

    /**
     * 删除指定ID的消息
     * @param messageId 要删除的消息ID
     * @throws RuntimeException 如果消息不存在
     */
    @Transactional
    public void deleteMessage(Long messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new RuntimeException("Message not found with id: " + messageId);
        }
        messageRepository.deleteById(messageId);
    }

    /**
     * 根据发送者ID获取所有消息
     * @param senderId 发送者ID
     * @return 该用户发送的所有消息列表
     */
    public List<Message> getMessagesBySender(Integer senderId) {
        return messageRepository.findBySenderId(senderId);
    }
}
