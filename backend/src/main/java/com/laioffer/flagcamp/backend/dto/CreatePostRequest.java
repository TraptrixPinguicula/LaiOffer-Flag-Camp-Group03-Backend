package com.laioffer.flagcamp.backend.dto;

// 这个 record 用于封装创建帖子的请求体
public record CreatePostRequest(Long postedItemId, Long tagId) {
}