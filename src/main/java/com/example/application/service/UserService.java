package com.example.application.service;

import com.example.application.controller.dto.UserDTO;
import java.util.List;

public interface UserService {

    List<UserDTO> findAllUsers();
    List<UserDTO> findAllUsersByName(String name);
    UserDTO createUser(UserDTO userToCreate);
    UserDTO modifyUser(UserDTO userToModify);
    void deleteUser(Long userId);
}
