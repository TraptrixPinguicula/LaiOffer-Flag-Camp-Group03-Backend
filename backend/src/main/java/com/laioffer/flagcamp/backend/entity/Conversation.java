package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import java.time.LocalDateTime;

@Table("conversations")
public record Conversation(
        @Id
        @Column("conversationid")  
        Long conversationId,
        
        @Column("buyerid")
        @NotNull 
        Long buyerId,
        
        @Column("sellerid")
        @NotNull 
        Long sellerId,

        @Column("updatedat")
        @NotNull 
        LocalDateTime updatedAt
) {
}
