package com.laioffer.flagcamp.backend.controller;

import com.laioffer.flagcamp.backend.entity.Conversation;
import com.laioffer.flagcamp.backend.service.ConversationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    // POST /api/conversations
    @PostMapping
    public Conversation createConversation(@RequestParam Long buyerId, @RequestParam Long sellerId) {
        return conversationService.createConversation(buyerId, sellerId);
    }

    // GET /api/conversations/{userId}
    @GetMapping("/{userId}")
    public List<Conversation> getUserConversations(@PathVariable Long userId) {
        return conversationService.getUserConversations(userId);
    }

    // DELETE /api/conversations/{conversationId}
    @DeleteMapping("/{conversationId}")
    public void deleteConversation(@PathVariable Long conversationId) {
        conversationService.deleteConversation(conversationId);
    }
}
