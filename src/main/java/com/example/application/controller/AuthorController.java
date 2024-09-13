package com.example.application.controller;

import com.example.application.controller.dto.AuthorDTO;
import com.example.application.model.Author;
import com.example.application.service.AuthorService;
import com.example.application.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @JsonView(View.DefaultView.class)
    public ResponseEntity<List<AuthorDTO>> getAllAuthors(){
        return ResponseEntity.ok(authorService.findAllAuthors());
    }

    @GetMapping("{id}")
    @JsonView(View.DefaultView.class)
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.findAuthorById(id));
    }

    @PostMapping
    @JsonView(View.DefaultView.class)
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorToCreate){
        var authorCreated = authorService.createAuthor(authorToCreate.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(authorCreated.getId()).toUri();
        return ResponseEntity.created(location).body(authorCreated);
    }
}
