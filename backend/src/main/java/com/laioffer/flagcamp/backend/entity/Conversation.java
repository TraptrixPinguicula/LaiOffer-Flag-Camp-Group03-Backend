package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("conversations")
public record Conversation(
        @Id Long conversationId,
        @NotNull Long buyerId,
        @NotNull Long sellerId,
        @NotNull LocalDateTime updatedAt
) {
}
