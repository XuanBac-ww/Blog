package com.example.BlogBe.service;

import com.example.BlogBe.dto.UserDto;
import com.example.BlogBe.model.User;
import com.example.BlogBe.request.CreateUserRequest;
import com.example.BlogBe.request.UpdateUserRequest;

public interface IUserService {
    User createUser(CreateUserRequest request);

    User updateUser(UpdateUserRequest request, Long userId);

    User getUserById(Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
