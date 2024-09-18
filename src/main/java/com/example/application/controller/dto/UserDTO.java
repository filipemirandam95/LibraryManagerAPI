package com.example.application.controller.dto;

import com.example.application.model.User;
import com.example.application.view.View;
import com.fasterxml.jackson.annotation.JsonView;

public class UserDTO {
    @JsonView(View.HideIdView.class)
    private Long id;
    @JsonView(View.DefaultView.class)
    private String name;

    public UserDTO() {
        this.id = 0L;
    }

    public UserDTO(User model) {
        this.id = model.getId();
        this.name = model.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User toModel(){
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        return user;
    }
}
