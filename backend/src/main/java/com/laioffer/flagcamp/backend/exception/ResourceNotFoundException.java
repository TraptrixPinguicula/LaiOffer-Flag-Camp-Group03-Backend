package com.laioffer.flagcamp.backend.exception;

// 当查询的资源（如用户或帖子）不存在时抛出
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}