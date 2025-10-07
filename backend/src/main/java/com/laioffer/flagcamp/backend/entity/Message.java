package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("messages")
public record Message(
        @Id Long messageId,
        @NotNull Long sellerId,
        @NotNull Long conversationId,
        @NotNull String messageContent,
        @NotNull LocalDateTime createdAt
) {
}
