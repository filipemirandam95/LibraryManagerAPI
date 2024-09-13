package com.example.application.service.impl;

import com.example.application.controller.dto.AuthorDTO;
import com.example.application.controller.dto.BookDTO;
import com.example.application.model.Author;
import com.example.application.model.Book;
import com.example.application.repository.AuthorRepository;
import com.example.application.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTO> findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOs = authors.stream().map(this::convertToDTO).collect(Collectors.toList());
        return authorDTOs;
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public AuthorDTO createAuthor(Author authorToCreate) {
        if(authorRepository.existsById(authorToCreate.getId())){
            throw new IllegalArgumentException("This Author ID already exists");
        }
        Author authorCreated = authorRepository.save(authorToCreate);
        return convertToDTO(authorCreated);
    }

    private AuthorDTO convertToDTO(Author authorCreated){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(authorCreated.getId());
        authorDTO.setName(authorCreated.getName());
        authorDTO.setNationality(authorCreated.getNationality());
        List<BookDTO> booksDTO = authorCreated.getBooks().stream().map(this::convertBookToDTO).collect(Collectors.toList());
        authorDTO.setBooks(booksDTO);

        return authorDTO;
    }

    private BookDTO convertBookToDTO(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAvailableCopies(book.getAvailableCopies());
        bookDTO.setAuthorId(book.getAuthor().getId());
        return bookDTO;
    }
}
