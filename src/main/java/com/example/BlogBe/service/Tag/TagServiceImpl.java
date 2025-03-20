package com.example.BlogBe.service.Tag;

import com.example.BlogBe.dto.TagDto;
import com.example.BlogBe.model.Tag;
import com.example.BlogBe.repository.TagRepostitory;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements ITagService {


    private final TagRepostitory tagRepository;

    @Autowired
    public TagServiceImpl(TagRepostitory tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<TagDto> getAllTagDtos() {
        return getAllTags().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found "));
    }

    @Override
    public TagDto getTagDtoById(Long id) {
        return mapToDto(getTagById(id));
    }
    @Override
    public List<Tag> searchTags(String keyword) {
        return tagRepository.findByNameContaining(keyword);
    }

    @Override
    public Tag createTag(TagDto tagDto) {
        if (existsTagByName(tagDto.getName())) {
            throw new EntityExistsException("Tag already exists");
        }
        Tag tag = mapToEntity(tagDto);
        return tagRepository.save(tag);
    }

    @Override
    public TagDto updateTag(Long id, TagDto tagDto) {
        Tag existingTag = getTagById(id);

        // Check if another tag with the new name already exists (only if name is changed)
        if (!existingTag.getName().equals(tagDto.getName()) &&
                existsTagByName(tagDto.getName())) {
            throw new EntityExistsException("Tag already exists");
        }

        existingTag.setName(tagDto.getName());

        Tag updatedTag = tagRepository.save(existingTag);
        return mapToDto(updatedTag);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.findById(id).ifPresentOrElse(
                tagRepository::delete,() -> {
                    throw new EntityNotFoundException("Tag not found");
                }
        );
    }

    @Override
    public List<Tag> getTagsByPostId(Long postId) {
        return tagRepository.findTagsByPostsId(postId);
    }

    @Override
    public TagDto mapToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setName(tag.getName());

        return tagDto;
    }

    @Override
    public Tag mapToEntity(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());

        return tag;
    }

    private boolean existsTagByName(String name) {
        return tagRepository.existsByName(name);
    }
}
