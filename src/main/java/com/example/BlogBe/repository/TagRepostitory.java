package com.example.BlogBe.repository;

import com.example.BlogBe.model.Tag;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepostitory extends JpaRepository<Tag,Long> {
    List<Tag> findTagsByPostsId(Long postId);

    boolean existsByName(String name);

    List<Tag> findByNameContaining(String keyword);
}
