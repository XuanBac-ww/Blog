package com.example.BlogBe.repository;

import com.example.BlogBe.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Post findPostByTitle(String title);

    boolean existsByTitle(String title);

    List<Post> findByCategoriesId(Long categoryId);

    List<Post> findByTagsId(Long tagId);
}
