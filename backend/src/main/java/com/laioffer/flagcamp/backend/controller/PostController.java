package com.laioffer.flagcamp.backend.controller;

import com.laioffer.flagcamp.backend.dto.CreatePostRequest;
import com.laioffer.flagcamp.backend.entity.Post;
import com.laioffer.flagcamp.backend.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // GET /api/posts -> 获取所有帖子 (支持按标签筛选)
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) Long tagId) {
        List<Post> posts = postService.getAllPosts(Optional.ofNullable(tagId));
        return ResponseEntity.ok(posts);
    }

    // GET /api/posts/{id} -> 获取某个帖子详情
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // POST /api/posts -> 发布帖子 (绑定商品 + 标签)
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request, @RequestParam Long ownerId) {
        // ‼️ 安全警告: 在生产环境中，ownerId 必须从安全认证信息(如JWT Token)中获取，
        // 而不是通过 @RequestParam 从 URL 参数传递，否则任何用户都可以冒充他人发帖。
        Post newPost = new Post(null, request.postedItemId(), request.tagId(), ownerId);
        Post createdPost = postService.createPost(newPost);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // DELETE /api/posts/{id} -> 删除帖子
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestParam Long userId) {
        // ‼️ 安全警告: 同上，userId 必须从安全认证信息中获取，以验证操作权限。
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }
}