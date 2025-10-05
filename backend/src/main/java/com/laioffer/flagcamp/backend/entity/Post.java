package com.laioffer.flagcamp.backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Post 实体类
 * 映射到数据库 posts 表
 */
@Table("posts")
public record Post(
        @Id
        @Column("postid")
        Long postId,         // 帖子ID，主键，自增
        
        @Column("postitemid")
        Integer postItemId,  // 关联的物品ID
        
        @Column("tagid")
        Integer tagId,       // 关联的标签ID
        
        @Column("postownerid")
        Integer postOwnerId  // 帖子所有者ID，关联用户表
) {
}
