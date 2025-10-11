package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    // ✅ Let Spring Data JDBC generate SQL automatically
    List<Conversation> findByBuyerIdOrSellerId(Long buyerId, Long sellerId);
}

