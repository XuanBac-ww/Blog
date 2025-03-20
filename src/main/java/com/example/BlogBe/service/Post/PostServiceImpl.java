package com.example.BlogBe.service.Post;

import com.example.BlogBe.dto.CategoryDto;
import com.example.BlogBe.dto.PostDto;
import com.example.BlogBe.dto.TagDto;
import com.example.BlogBe.model.Category;
import com.example.BlogBe.model.Post;
import com.example.BlogBe.model.Tag;
import com.example.BlogBe.model.User;
import com.example.BlogBe.repository.CategoryRepository;
import com.example.BlogBe.repository.PostRepository;
import com.example.BlogBe.repository.TagRepostitory;
import com.example.BlogBe.request.CreatePostRequest;
import com.example.BlogBe.request.UpdatePostRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService{

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepostitory tagRepository;

    public PostServiceImpl(PostRepository postRepository,
                           CategoryRepository categoryRepository,
                           TagRepostitory tagRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Post addPost(CreatePostRequest request) {
        if (postRepository.existsByTitle(request.getTitle())) {
            throw new RuntimeException("Post with this title already exists");
        }

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : LocalDateTime.now());

        // Xử lý categories
        Set<Category> categories = new HashSet<>();
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            for (Long categoryId : request.getCategoryIds()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Category with ID " + categoryId + " not found"));
                categories.add(category);
            }
        }
        post.setCategories(categories);

        // Xử lý tags
        Set<Tag> tags = new HashSet<>();
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            for (Long tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag with ID " + tagId + " not found"));
                tags.add(tag);
            }
        }
        post.setTags(tags);

        return postRepository.save(post);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.findById(id)
                .ifPresentOrElse(postRepository::delete,() -> {
                    throw new RuntimeException("Post not found ");
                });
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found "));
    }

    @Override
    public Post updatePost(UpdatePostRequest request) {
        Post post = postRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Kiểm tra xem nếu title đã thay đổi và title mới đã tồn tại
        if (request.getTitle() != null && !request.getTitle().equals(post.getTitle())) {
            if (postRepository.existsByTitle(request.getTitle())) {
                throw new RuntimeException("Post with this title already exists");
            }
            post.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }

        // Xử lý categories nếu có cập nhật
        if (request.getCategoryIds() != null) {
            Set<Category> categories = new HashSet<>();
            for (Long categoryId : request.getCategoryIds()) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Category with ID " + categoryId + " not found"));
                categories.add(category);
            }
            post.setCategories(categories);
        }

        // Xử lý tags nếu có cập nhật
        if (request.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new RuntimeException("Tag with ID " + tagId + " not found"));
                tags.add(tag);
            }
            post.setTags(tags);
        }

        post.setUpdatedAt(request.getUpdatedAt() != null ? request.getUpdatedAt() : LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostByCategory(Long categoryId) {
        return postRepository.findByCategoriesId(categoryId);
    }

    @Override
    public List<PostDto> getPostsByTag(Long tagId) {
        List<Post> posts = postRepository.findByTagsId(tagId);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());

        // Chuyển đổi categories
        Set<CategoryDto> categoryDtos = new HashSet<>();
        for(Category category : post.getCategories()) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDtos.add(categoryDto);
        }
        postDto.setCategories(categoryDtos);

        // Chuyển đổi tags
        Set<TagDto> tagDtos = new HashSet<>();
        for (Tag tag : post.getTags()) {
            TagDto tagDto = new TagDto();
            tagDto.setName(tag.getName());
            tagDtos.add(tagDto);
        }
        postDto.setTags(tagDtos);
        return postDto;
    }
}
