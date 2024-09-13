package com.example.application.service;

import com.example.application.controller.dto.BookDTO;
import com.example.application.model.Book;

import java.util.List;

public interface BookService {

    List<BookDTO> findAllBooks();
    Book findBookByIsbn(Long isbn);
    BookDTO createBook(Book bookToCreate);
}
