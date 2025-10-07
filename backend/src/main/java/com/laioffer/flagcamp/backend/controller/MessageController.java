package com.laioffer.flagcamp.backend.controller;

import com.laioffer.flagcamp.backend.entity.Message;
import com.laioffer.flagcamp.backend.service.MessageService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // POST /api/messages
    @PostMapping
    public Message sendMessage(
            @RequestParam Long conversationId,
            @RequestParam Long senderId,
            @RequestParam String content) {
        return messageService.sendMessage(conversationId, senderId, content);
    }

    // GET /api/messages/{conversationId}
    @GetMapping("/{conversationId}")
    public List<Message> getMessages(@PathVariable Long conversationId) {
        return messageService.getMessages(conversationId);
    }

    // DELETE /api/messages/{messageId}
    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
    }
}
