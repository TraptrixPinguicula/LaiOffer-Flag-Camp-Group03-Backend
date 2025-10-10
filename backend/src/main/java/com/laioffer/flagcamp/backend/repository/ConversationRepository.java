package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    /**
     * 自定义查询方法，用于查找某个用户作为买家或卖家参与的所有对话。
     * Spring Data会根据方法名自动生成 SQL 查询，等价于：
     * SELECT * FROM conversations WHERE buyer_id = :buyerId OR seller_id = :sellerId
     *
     * @param buyerId  用户的ID（当他是买家时）
     * @param sellerId 用户的ID（当他是卖家时）
     * @return 该用户参与的所有对话列表
     */
    List<Conversation> findByBuyerIdOrSellerId(Long buyerId, Long sellerId);
}