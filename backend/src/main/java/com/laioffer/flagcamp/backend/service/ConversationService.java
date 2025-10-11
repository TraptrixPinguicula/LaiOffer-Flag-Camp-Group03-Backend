package com.laioffer.flagcamp.backend.service;

import com.laioffer.flagcamp.backend.entity.Conversation;
import com.laioffer.flagcamp.backend.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    // 使用构造函数注入 ConversationRepository，这是 Spring 推荐的做法
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    /**
     * 创建一个新的对话。
     *
     * @param buyerId  买家ID
     * @param sellerId 卖家ID
     * @return 创建并保存到数据库后的 Conversation 对象
     */
    public Conversation createConversation(Long buyerId, Long sellerId) {
        // 创建一个新的 Conversation 实例。
        // conversationId 传入 null，让数据库自动生成主键。
        // updatedAt 设置为当前时间。
        Conversation newConversation = new Conversation(null, buyerId, sellerId, LocalDateTime.now());
        
        // 调用 repository 的 save 方法将其持久化到数据库，并返回保存后的对象
        return conversationRepository.save(newConversation);
    }

    /**
     * 获取指定用户的所有对话。
     *
     * @param userId 用户的ID
     * @return 该用户作为买家或卖家参与的所有对话列表
     */
    public List<Conversation> getUserConversations(Long userId) {
        // 调用我们自定义的 repository 方法。
        // 将同一个 userId 同时传给 buyerId 和 sellerId 参数，
        // Spring Data 会执行 "WHERE buyer_id = ? OR seller_id = ?" 的查询。
	return conversationRepository.findByBuyerIdOrSellerId(userId, userId);


    }

    /**
     * 根据ID删除一个对话。
     *
     * @param conversationId 要删除的对话的ID
     */
    public void deleteConversation(Long conversationId) {
        // 调用 CrudRepository 自带的 deleteById 方法
        conversationRepository.deleteById(conversationId);
    }
}
