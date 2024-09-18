package com.example.application.controller;

import com.example.application.controller.dto.UserDTO;
import com.example.application.service.UserService;
import com.example.application.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/findAllUsersByName")
    public ResponseEntity<List<UserDTO>> findAllUsersByName(@RequestParam String name){
        return ResponseEntity.ok(userService.findAllUsersByName(name));
    }

    @PostMapping
    @JsonView(View.DefaultView.class)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        var userCreated = userService.createUser(userDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userCreated.getId()).toUri();
        return ResponseEntity.created(location).body(userCreated);
    }

    @PutMapping()
    public ResponseEntity<UserDTO> modifyUser(@RequestBody UserDTO userDTO){
        var userModified = userService.modifyUser(userDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userModified.getId()).toUri();
        return ResponseEntity.created(location).body(userModified);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
