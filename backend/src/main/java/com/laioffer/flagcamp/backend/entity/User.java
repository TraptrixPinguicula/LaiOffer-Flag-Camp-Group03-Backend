package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column; // 1. Add this import
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(

        @Column("userid") // 2. Add this annotation to specify the correct column name
        @Id Long userId,
        @NotNull String password,
        @NotNull String email,
        @Column("phonenum") // <-- Add this annotation
        Integer phoneNum,
        String address,
        @Column("usericon") // <-- Add this annotation
        String userIcon,
        String nickname,
        String notes
) {
}