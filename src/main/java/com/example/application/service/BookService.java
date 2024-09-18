package com.example.application.service;

import com.example.application.controller.dto.BookDTO;
import java.util.List;

public interface BookService {

    List<BookDTO> findAllBooks();
    List<BookDTO> findAllBooksByName(String name);
    BookDTO findBookByIsbn(Long isbn);
    BookDTO createBook(BookDTO bookToCreate);
    BookDTO modifyBook(BookDTO bookToModify);

}
