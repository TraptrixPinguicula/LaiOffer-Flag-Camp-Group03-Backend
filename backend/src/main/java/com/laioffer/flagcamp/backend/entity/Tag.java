package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column; // 1. Add this import
import org.springframework.data.relational.core.mapping.Table;

@Table("tags")
public record Tag(
        @Id Long tagId,
        @NotNull String tagContent
) {
}
