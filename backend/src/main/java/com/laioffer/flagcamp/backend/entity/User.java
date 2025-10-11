package com.laioffer.flagcamp.backend.entity;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("users")
public record User(
        @Id 
        @Column("userid")
        Long userId,

        @NotNull 
        String password,

        @NotNull 
        String email,
        
        @NotNull
        @Column("phonenum")
        Long phoneNum,

        @NotNull
        String address,

        @Column("usericon")
        String userIcon,

        String nickname,

        String notes
) {
}

