package com.example.application.controller;

import com.example.application.controller.dto.LoanDTO;
import com.example.application.model.Book;
import com.example.application.model.Loan;
import com.example.application.model.User;
import com.example.application.service.BookService;
import com.example.application.service.LoanService;
import com.example.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;
    private final UserService userService;
    private final BookService bookService;

    public LoanController(LoanService loanService, UserService userService, BookService bookService) {
        this.loanService = loanService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans(){
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId){
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanToCreate){
        User user = userService.findUserById(loanToCreate.getUserId());
        Book book  = bookService.findBookByIsbn(loanToCreate.getIsbn());
        var loanCreated = loanService.createLoan(loanToCreate.toModel(user,book));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{loanId}").buildAndExpand(loanCreated.getLoanId()).toUri();
        return ResponseEntity.created(location).body(loanCreated);
    }
}
