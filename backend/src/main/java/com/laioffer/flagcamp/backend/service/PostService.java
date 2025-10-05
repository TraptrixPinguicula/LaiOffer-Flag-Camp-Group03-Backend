package com.laioffer.flagcamp.backend.service;

import com.laioffer.flagcamp.backend.entity.Post;
import com.laioffer.flagcamp.backend.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Post 服务层
 * 提供帖子相关的业务逻辑处理
 */
@Service
public class PostService {

    private final PostRepository postRepository;

    // 使用构造函数注入依赖
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 创建新帖子
     * @param post 帖子信息
     * @return 保存后的帖子对象（包含自动生成的ID）
     */
    @Transactional
    public Post createPost(Post post) {
        // 创建新帖子时，postId 应该为 null，由数据库自动生成
        Post postToSave = new Post(
                null,
                post.postItemId(),
                post.tagId(),
                post.postOwnerId()
        );
        return postRepository.save(postToSave);
    }

    /**
     * 获取所有帖子
     * @return 所有帖子的列表
     */
    public List<Post> getAllPosts() {
        // CrudRepository 的 findAll() 返回 Iterable，需要转换为 List
        return StreamSupport.stream(postRepository.findAll().spliterator(), false)
                .toList();
    }

    /**
     * 根据用户ID获取该用户发布的所有帖子
     * @param userId 用户ID
     * @return 该用户发布的所有帖子列表
     */
    public List<Post> getPostsByUser(Long userId) {
        // 将 Long 转换为 Integer，因为数据库字段是 int 类型
        return postRepository.findByPostOwnerId(userId.intValue());
    }

    /**
     * 根据标签ID获取所有带该标签的帖子
     * @param tagId 标签ID
     * @return 带有该标签的所有帖子列表
     */
    public List<Post> getPostsByTag(Long tagId) {
        // 将 Long 转换为 Integer，因为数据库字段是 int 类型
        return postRepository.findByTagId(tagId.intValue());
    }

    /**
     * 删除指定ID的帖子
     * @param postId 要删除的帖子ID
     * @throws RuntimeException 如果帖子不存在
     */
    @Transactional
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }
        postRepository.deleteById(postId);
    }
}
