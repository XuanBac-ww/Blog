package com.example.BlogBe.service.Tag;

import com.example.BlogBe.dto.TagDto;
import com.example.BlogBe.model.Tag;

import java.util.List;

public interface ITagService {
    List<Tag> getAllTags();

    List<TagDto> getAllTagDtos();

    Tag getTagById(Long id);

    TagDto getTagDtoById(Long id);

    List<Tag> searchTags(String keyword);

    Tag createTag(TagDto tagDto);

    TagDto updateTag(Long id, TagDto tagDto);

    void deleteTag(Long id);

    List<Tag> getTagsByPostId(Long postId);

    TagDto mapToDto(Tag tag);

    Tag mapToEntity(TagDto tagDto);
}
