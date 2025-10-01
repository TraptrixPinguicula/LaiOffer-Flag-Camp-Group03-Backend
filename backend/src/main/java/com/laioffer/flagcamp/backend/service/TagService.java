package com.laioffer.flagcamp.backend.service;

import com.laioffer.flagcamp.backend.entity.Tag;
import com.laioffer.flagcamp.backend.repository.TagRepository;
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
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    // 根据内容获取标签
    public Optional<Tag> getTagByContent(String content) {
        return tagRepository.findByTagContent(content);
    }

    // 删除标签
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
