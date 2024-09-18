package com.example.application.service;

import com.example.application.controller.dto.AuthorDTO;
import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAllAuthors();
    List<AuthorDTO> findAllAuthorsByName(String name);
    AuthorDTO createAuthor(AuthorDTO authorToCreate);
    void deleteAuthor(Long AuthorId);
}
