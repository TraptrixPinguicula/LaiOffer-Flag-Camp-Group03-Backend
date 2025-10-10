package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    /**
     * Spring Data 会自动根据方法名实现查询：找到所有 tagId 匹配的帖子
     * @param tagId 标签ID
     * @return 匹配该标签的所有帖子的列表
     */
    List<Post> findByTagId(Long tagId);
}