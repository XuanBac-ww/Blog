package com.example.BlogBe.dto;

import com.example.BlogBe.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


public class CategoryDto {


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
