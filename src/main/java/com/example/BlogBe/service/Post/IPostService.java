package com.example.BlogBe.service.Post;

import com.example.BlogBe.dto.PostDto;
import com.example.BlogBe.model.Post;
import com.example.BlogBe.request.CreatePostRequest;
import com.example.BlogBe.request.UpdatePostRequest;

import java.util.List;

public interface IPostService {
    Post addPost(CreatePostRequest request);

    void deletePostById(Long id);

    Post getPostById(Long id);

    Post updatePost(UpdatePostRequest request);

    List<Post> getAllPost();

    List<Post> getPostByCategory(Long categoryId);

    List<PostDto> getPostsByTag(Long tagId);

    PostDto convertToDto(Post post);
}
