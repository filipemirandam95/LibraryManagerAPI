package com.example.application.controller;

import com.example.application.controller.dto.BookDTO;
import com.example.application.model.Author;
import com.example.application.model.Book;
import com.example.application.service.AuthorService;
import com.example.application.service.BookService;
import com.example.application.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAllBooks());
    }
    @GetMapping("{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable Long isbn){
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createUser(@RequestBody BookDTO bookToCreate){
        Author author = authorService.findAuthorById(bookToCreate.getAuthorId());
        var bookCreated = bookService.createBook(bookToCreate.toModel(author));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{isbn}").buildAndExpand(bookCreated.getIsbn()).toUri();
        return ResponseEntity.created(location).body(bookCreated);
    }
}
