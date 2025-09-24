package com.laioffer.flagcamp.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
        @Id Long userId,
        String password,
        String email,
        Integer phoneNum,
        String address,
        String userIcon,
        String nickname,
        String notes
) {
}
