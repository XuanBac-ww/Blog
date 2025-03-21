package com.example.BlogBe.controller;

import com.example.BlogBe.dto.PostDto;
import com.example.BlogBe.model.Post;
import com.example.BlogBe.request.CreatePostRequest;
import com.example.BlogBe.request.UpdatePostRequest;
import com.example.BlogBe.response.ApiResponse;
import com.example.BlogBe.service.Post.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createPost(@RequestBody CreatePostRequest request) {
            Post createdPost = postService.addPost(request);
            return ResponseEntity.ok(new ApiResponse("Created Successfully", createdPost));
    }

    @GetMapping("/getPostById/{id}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(new ApiResponse("sucess",post));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPosts() {
        List<Post> posts = postService.getAllPost();
        return ResponseEntity.ok(new ApiResponse("success",posts));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updatePost(@RequestBody UpdatePostRequest request) {
        Post updatedPost = postService.updatePost(request);
        return ResponseEntity.ok(new ApiResponse("sucess", updatedPost));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePostById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getPostsByCategory(@PathVariable Long categoryId) {
        List<Post> posts = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(new ApiResponse("sucess",posts));
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<ApiResponse> getPostsByTag(@PathVariable Long tagId) {
        List<PostDto> posts = postService.getPostsByTag(tagId);
        return ResponseEntity.ok(new ApiResponse("success", posts));
    }
}
