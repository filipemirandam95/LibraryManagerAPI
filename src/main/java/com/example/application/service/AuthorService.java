package com.example.application.service;

import com.example.application.controller.dto.AuthorDTO;
import com.example.application.model.Author;
import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAllAuthors();
    Author findAuthorById(Long id);
    AuthorDTO createAuthor(Author authorToCreate);
}
