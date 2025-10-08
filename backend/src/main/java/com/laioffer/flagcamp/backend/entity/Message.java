package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Message 实体类
 * 对应数据库中的 messages 表
 */
@Table("messages")
public record Message(
        @Id
        @Column("messageid")
        Long messageId,

        @Column("createdat")
        @NotNull
        LocalDateTime createdAt,

        @Column("senderid")
        @NotNull
        Integer senderId,

        @Column("conversationid")
        @NotNull
        Integer conversationId,

        @Column("messagecontent")
        @NotNull
        String messageContent
) {
    /**
     * 创建新消息的构造方法（不包含ID，由数据库自动生成）
     */
    public static Message createNew(Integer senderId, Integer conversationId, String messageContent) {
        return new Message(null, LocalDateTime.now(), senderId, conversationId, messageContent);
    }
}
