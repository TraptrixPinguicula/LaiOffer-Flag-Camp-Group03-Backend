package com.laioffer.flagcamp.backend.dto;

// 这个 record 专门用来接收登录请求的 JSON 数据
public record LoginRequest(String email, String password) {
}