package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("tags")
public record Tag(
        @Id
        @Column("tagid") 
        Long tagId,

        @NotNull 
        @Column("tagcontent")
        String tagContent
) {
}
