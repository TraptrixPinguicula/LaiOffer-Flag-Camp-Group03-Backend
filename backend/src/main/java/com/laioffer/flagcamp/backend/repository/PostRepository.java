package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Post 数据访问层
 * 提供对 posts 表的 CRUD 操作
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    /**
     * 根据帖子所有者ID查询所有帖子
     * @param postOwnerId 帖子所有者的用户ID
     * @return 该用户发布的所有帖子列表
     */
    List<Post> findByPostOwnerId(Integer postOwnerId);

    /**
     * 根据标签ID查询所有帖子
     * @param tagId 标签ID
     * @return 带有该标签的所有帖子列表
     */
    List<Post> findByTagId(Integer tagId);
}
