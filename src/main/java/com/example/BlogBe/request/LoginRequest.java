package com.example.BlogBe.request;


import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Invalid login credentials")
    private String email;
    @NotBlank(message = "Invalid login credentials")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
