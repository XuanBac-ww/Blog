package com.example.BlogBe.controller;

import com.example.BlogBe.dto.TagDto;
import com.example.BlogBe.model.Tag;
import com.example.BlogBe.response.ApiResponse;
import com.example.BlogBe.service.Tag.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final ITagService tagService;

    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }

    // ========== PUBLIC ENDPOINTS ==========

    @GetMapping("/tags/all")
    public ResponseEntity<ApiResponse> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(new ApiResponse("Found",tags));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTagById(@PathVariable Long id) {
        Tag tag = tagService.getTagById(id);
        return ResponseEntity.ok(new ApiResponse("Found",tag));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getTagsByPostId(@PathVariable Long postId) {
        List<Tag> tags = tagService.getTagsByPostId(postId);
        return ResponseEntity.ok(new ApiResponse("Found",tags));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchTags(@RequestParam String keyword) {
        // Assuming you add this method to your service and repository
        List<Tag> tags = tagService.searchTags(keyword);
        return ResponseEntity.ok(new ApiResponse("Found",tags));
    }

    // ========== ADMIN ONLY ENDPOINTS ==========

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createTag(@RequestBody TagDto tagDto) {
        Tag tag = tagService.createTag(tagDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Tag created successfully", tag));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateTag(@PathVariable Long id, @RequestBody TagDto tagDto) {
        TagDto updatedTag = tagService.updateTag(id, tagDto);
        return ResponseEntity.ok(new ApiResponse("Tag updated successfully", updatedTag));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok(new ApiResponse("Tag deleted successfully", null));
    }
}
