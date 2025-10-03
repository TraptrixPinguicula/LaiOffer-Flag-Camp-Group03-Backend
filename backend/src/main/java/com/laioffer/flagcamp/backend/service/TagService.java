package com.laioffer.flagcamp.backend.service;

import com.laioffer.flagcamp.backend.entity.Tag;
import com.laioffer.flagcamp.backend.repository.TagRepository;
import com.laioffer.flagcamp.backend.exception.TagNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // 创建新标签
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // 获取所有标签
    public List<Tag> getAllTags() {
        return (List<Tag>) tagRepository.findAll();
    }

    // 根据ID获取标签
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
            .orElseThrow(() -> new TagNotFoundException("标签不存在");
    }

    // 根据内容获取标签
    public Tag getTagByContent(String content) {
        return tagRepository.findByTagContent(content)
            .orElseThrow(() -> new TagNotFoundException("标签不存在");
    }

    // 删除标签
    public void deleteTag(Long id) {
        Tag tag = getTagById(id);
        tagRepository.delete(tag);
    }
}
