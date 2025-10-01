package com.laioffer.flagcamp.backend.controller;

import com.laioffer.flagcamp.backend.entity.Tag;
import com.laioffer.flagcamp.backend.repository.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // POST /api/tags -> 创建新标签
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag savedTag = tagRepository.save(tag);
        return ResponseEntity.ok(savedTag);
    }

    // GET /api/tags -> 获取所有标签
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = (List<Tag>) tagRepository.findAll();
        return ResponseEntity.ok(tags);
    }
}
