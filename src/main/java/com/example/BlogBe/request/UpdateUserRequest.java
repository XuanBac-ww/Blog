package com.example.BlogBe.request;

import lombok.Data;

public class UpdateUserRequest {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
