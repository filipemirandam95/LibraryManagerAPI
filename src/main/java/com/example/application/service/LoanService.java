package com.example.application.service;

import com.example.application.controller.dto.LoanDTO;
import com.example.application.model.Loan;

import java.util.List;

public interface LoanService {

    List<LoanDTO> getAllLoans();
    Loan getLoanById(Long id);
    LoanDTO createLoan(Loan loanToCreate);

}
