package com.laioffer.flagcamp.backend.service;

import com.laioffer.flagcamp.backend.entity.Post;
import com.laioffer.flagcamp.backend.exception.ResourceNotFoundException;
import com.laioffer.flagcamp.backend.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 获取所有帖子，如果提供了 tagId，则按标签筛选
     */
    public List<Post> getAllPosts(Optional<Integer> tagId) {
        if (tagId.isPresent()) {
            logger.info("Fetching posts with tagId: {}", tagId.get());
            return postRepository.findByTagId(tagId.get());
        } else {
            logger.info("Fetching all posts.");
            return (List<Post>) postRepository.findAll();
        }
    }

    /**
     * 根据ID获取单个帖子的详情
     */
    public Post getPostById(Long postId) {
        logger.info("Fetching post with ID: {}", postId);
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    }

    /**
     * 创建一个新帖子
     */
    @Transactional
    public Post createPost(Post post) {
        logger.info("Creating a new post for ownerId: {}", post.postOwnerId());
        Post postToSave = new Post(
            null,
            post.postItemId(),
            post.tagId(),
            post.postOwnerId()
        );
        return postRepository.save(postToSave);
    }

    /**
     * 删除一个帖子，并进行权限校验
     */
    @Transactional
    public void deletePost(Long postId, Integer currentUserId) {
        logger.info("Attempting to delete post with ID: {} by user: {}", postId, currentUserId);
        Post post = getPostById(postId); // getPostById 会在帖子不存在时抛出异常

        // 权限检查：确保只有帖子的所有者才能删除
        if (!post.postOwnerId().equals(currentUserId)) {
            logger.warn("Unauthorized delete attempt: User {} tried to delete post {} owned by {}",
                    currentUserId, postId, post.postOwnerId());
            // 在实际应用中，可以抛出一个更具体的权限异常
            throw new SecurityException("User is not authorized to delete this post");
        }

        postRepository.deleteById(postId);
        logger.info("Successfully deleted post with ID: {}", postId);
    }
}
