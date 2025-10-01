package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    Optional<Tag> findByTagContent(String tagContent);
}
