package com.example.application.service;

import com.example.application.controller.dto.LoanDTO;
import java.util.List;

public interface LoanService {

    List<LoanDTO> getAllLoans();
    LoanDTO getLoanById(Long id);
    LoanDTO createLoan(LoanDTO loanToCreate);
    LoanDTO modifyLoan(LoanDTO loanToModify);

}
