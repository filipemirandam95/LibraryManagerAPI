package com.example.application.controller;

import com.example.application.controller.dto.LoanDTO;
import com.example.application.model.Book;
import com.example.application.model.Loan;
import com.example.application.model.User;
import com.example.application.service.BookService;
import com.example.application.service.LoanService;
import com.example.application.service.UserService;
import com.example.application.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;

    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans(){
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("{loanId}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long loanId){
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @PostMapping
    @JsonView(View.DefaultView.class)
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanToCreate){
        var loanCreated = loanService.createLoan(loanToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{loanId}").buildAndExpand(loanCreated.getLoanId()).toUri();
        return ResponseEntity.created(location).body(loanCreated);
    }

    @PutMapping
    @JsonView(View.PutLoanView.class)
    public ResponseEntity<LoanDTO> modifyLoan(@RequestBody LoanDTO loanToModify){
        var loanModified = loanService.modifyLoan(loanToModify);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{loanId}").buildAndExpand(loanModified.getLoanId()).toUri();
        return ResponseEntity.created(location).body(loanModified);
    }
}
