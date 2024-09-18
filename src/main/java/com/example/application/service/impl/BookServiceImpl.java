package com.example.application.service.impl;

import com.example.application.controller.dto.BookDTO;
import com.example.application.model.Author;
import com.example.application.model.Book;
import com.example.application.repository.AuthorRepository;
import com.example.application.repository.BookRepository;
import com.example.application.service.BookService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findAllBooksByName(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return books.stream().map(BookDTO::new).collect(Collectors.toList());
    }

    @Override
    public BookDTO findBookByIsbn(Long isbn) {
        return new BookDTO(bookRepository.findById(isbn).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(NoSuchElementException::new);
        if(bookRepository.existsById(bookDTO.getIsbn())){
            throw new IllegalArgumentException("This Book ISBN already exists.");
        }
        if(bookDTO.getAvailableCopies()<=0){
            throw new IllegalArgumentException("The amount of available copies must be greater than 0.");
        }
        Book bookToCreate = bookDTO.toModel(author);
        Book bookCreated = bookRepository.save(bookToCreate);
        return new BookDTO(bookCreated);
    }

    @Override
    public BookDTO modifyBook(BookDTO bookToModify) {
        Optional<Book> book = bookRepository.findById(bookToModify.getIsbn());
        if(book.isPresent()){
            if(bookToModify.getAvailableCopies()<0){
                throw new IllegalArgumentException("The amount of available copies must be at least 0.");
            }
            return new BookDTO(bookRepository.save(bookToModify.toModel(book.get().getAuthor())));
        }else{
            throw new IllegalArgumentException("This book ISBN was not found.");
        }
    }
}
