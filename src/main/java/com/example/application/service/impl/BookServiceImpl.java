package com.example.application.service.impl;

import com.example.application.controller.dto.BookDTO;
import com.example.application.model.Book;
import com.example.application.repository.BookRepository;
import com.example.application.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = books.stream().map(this::convertBookToDTO).collect(Collectors.toList());
        return bookDTOs;
    }

    @Override
    public Book findBookByIsbn(Long isbn) {
        return bookRepository.findById(isbn).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public BookDTO createBook(Book bookToCreate) {
        if(bookRepository.existsById(bookToCreate.getIsbn())){
            throw new IllegalArgumentException("This Book ISBN already exists.");
        }
        Book bookCreated = bookRepository.save(bookToCreate);
        return convertBookToDTO(bookCreated);
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
