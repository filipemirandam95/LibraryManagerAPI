package com.example.application.controller;

import com.example.application.controller.dto.BookDTO;
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

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    @JsonView(View.BookView.class)
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        return ResponseEntity.ok(bookService.findAllBooks());
    }
    @GetMapping("{isbn}")
    @JsonView(View.BookView.class)
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable Long isbn){
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @GetMapping("/findAllBooksByName")
    @JsonView(View.BookView.class)
    public ResponseEntity<List<BookDTO>> findAllBooksByName(@RequestParam String title){
        return ResponseEntity.ok(bookService.findAllBooksByName(title));
    }

    @PostMapping
    @JsonView(View.BookView.class)
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookToCreate){
        var bookCreated = bookService.createBook(bookToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{isbn}").buildAndExpand(bookCreated.getIsbn()).toUri();
        return ResponseEntity.created(location).body(bookCreated);
    }

    @PutMapping
    @JsonView(View.HideIdView.class)
    public ResponseEntity<BookDTO> modifyBook(@RequestBody BookDTO bookToModify){
        var bookModified = bookService.modifyBook(bookToModify);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{isbn}").buildAndExpand(bookToModify.getIsbn()).toUri();
        return ResponseEntity.created(location).body(bookToModify);
    }
}
