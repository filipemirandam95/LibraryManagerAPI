package com.example.application.service;

import com.example.application.model.User;

public interface UserService {

    Iterable<User> findAllUsers();
    User findUserById(Long id);
    User createUser(User userToCreate);
}
