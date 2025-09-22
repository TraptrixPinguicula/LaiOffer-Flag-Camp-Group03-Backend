package com.laioffer.flagcamp.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
public record User(
        @Id Long id,
        String email,
        String password,
        String name
) {
}
