package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Conversation;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    /**
     * 手写 SQL 避免不同数据库的标识符大小写、引号差异导致的语法错误。
     * 这里不包裹引号，让数据库按照自身规则解析列名。
     */
    @Query("""
            SELECT *
            FROM conversations
            WHERE buyerid = :userId OR sellerid = :userId
            """)
    List<Conversation> findConversationsForUser(@Param("userId") Long userId);
}
