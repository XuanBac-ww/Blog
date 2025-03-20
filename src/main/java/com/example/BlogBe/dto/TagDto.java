package com.example.BlogBe.dto;

import com.example.BlogBe.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


public class TagDto {

    private String name;

    private Set<Post> posts = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
