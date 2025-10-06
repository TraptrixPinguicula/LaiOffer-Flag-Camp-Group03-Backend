package com.laioffer.flagcamp.backend.exception;

// 当注册的用户已存在时抛出
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}