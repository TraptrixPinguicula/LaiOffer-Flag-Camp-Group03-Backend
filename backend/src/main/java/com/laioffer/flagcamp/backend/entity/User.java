package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
        @Id Long userId,
        @NotNull String password,
        @NotNull String email,
        Integer phoneNum,
        String address,
        String userIcon,
        String nickname,
        String notes
) {
}

