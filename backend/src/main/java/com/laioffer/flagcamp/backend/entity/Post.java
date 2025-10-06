package com.laioffer.flagcamp.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;



@Table("posts")
public record Post(
        @Column("postid")
        @Id Long postId,

        @Column("posteditemid")
        Integer postedItemId,

        @Column("tagid")
        Integer tagId,

        @Column("postownerid") // 注意：您的 SQL 中 owner 拼写为 owener
        Integer postOwnerId
) {
}

