package com.example.application.service.impl;

import com.example.application.controller.dto.AuthorDTO;
import com.example.application.controller.dto.BookDTO;
import com.example.application.controller.dto.UserDTO;
import com.example.application.model.Author;
import com.example.application.model.Book;
import com.example.application.repository.AuthorRepository;
import com.example.application.repository.BookRepository;
import com.example.application.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<AuthorDTO> findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(AuthorDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> findAllAuthorsByName(String name) {
        List<Author> authors = authorRepository.findByNameContainingIgnoreCase(name);
        return authors.stream().map(AuthorDTO::new).collect(Collectors.toList());
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        if(authorRepository.existsById(authorDTO.getId())){
            throw new IllegalArgumentException("This Author ID already exists");
        }
        for(BookDTO book: authorDTO.getBooks()){
            if(book.getAvailableCopies()<=0){
                throw new IllegalArgumentException("The amount of available copies must be greater than 0 for every book.");
            }
        }
        Author authorToCreate = authorDTO.toModel();
        Author authorCreated = authorRepository.save(authorToCreate);
        return new AuthorDTO(authorCreated);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);
        if(!books.isEmpty()){
            throw new IllegalArgumentException("This Author has at least one registered book therefore he can not be deleted. ");
        }
        authorRepository.deleteById(authorId);
    }
}
