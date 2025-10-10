package com.laioffer.flagcamp.backend.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.laioffer.flagcamp.backend.entity.Message;
import com.laioffer.flagcamp.backend.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Message REST API Controller
 * 提供消息的 REST API 接口
 */
@RestController
@RequestMapping("/api/messages")
@Validated

public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 发送新消息
     * POST /api/messages
     * 
     * @param request 消息请求体，包含发送者ID、会话ID和消息内容
     * @return 创建的消息对象，返回 201 Created 状态码
     */
    @PostMapping
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody SendMessageRequest request) {
        // 规范化消息内容
        String content = request.messageContent.trim();
        
        // 调用服务层发送消息
        Message savedMessage = messageService.sendMessage(
                request.senderId,
                request.conversationId,
                content
        );
        
        // 设置 Location 响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/messages/" + savedMessage.messageId()));
        
        return new ResponseEntity<>(savedMessage, headers, HttpStatus.CREATED);
    }

    /**
     * 获取某个会话的所有消息
     * GET /api/messages/{conversationId}
     * 
     * @param conversationId 会话ID
     * @return 该会话的所有消息列表，按创建时间降序排列（最新的在最上面）
     */
    @GetMapping("/{conversationId}")
    public ResponseEntity<List<Message>> getMessagesByConversation(@PathVariable Long conversationId) {
        return ResponseEntity.ok(messageService.getMessagesByConversation(conversationId));
    }

    /**
     * 删除指定消息
     * DELETE /api/messages/{messageId}
     * 
     * @param messageId 要删除的消息ID
     * @return 204 No Content 状态码
     */
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 发送消息请求 DTO
     */
    public static class SendMessageRequest {
        /**
         * 发送者用户ID（必填）
         */
        @NotNull(message = "发送者ID不能为空")
        public Long senderId;

        /**
         * 会话ID（必填）
         */
        @NotNull(message = "会话ID不能为空")
        public Long conversationId;

        /**
         * 消息内容（必填，非空白）
         */
        @NotBlank(message = "消息内容不能为空")
        public String messageContent;

    }
}
