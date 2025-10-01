package com.laioffer.flagcamp.backend.repository;

import com.laioffer.flagcamp.backend.entity.Tag;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends ListCrudRepository<Tag, Long> {
    Optional<Tag> findByTagContent(String tagContent);
}
