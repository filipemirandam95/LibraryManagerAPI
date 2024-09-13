package com.example.application.service.impl;

import com.example.application.controller.dto.LoanDTO;
import com.example.application.model.Loan;
import com.example.application.repository.BookRepository;
import com.example.application.repository.LoanRepository;
import com.example.application.repository.UserRepository;
import com.example.application.service.LoanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<LoanDTO> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        List<LoanDTO> loanDTOs = loans.stream().map(this::convertToDTO).collect(Collectors.toList());
        return loanDTOs;
    }

    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public LoanDTO createLoan(Loan loanToCreate) {

        System.out.println("Loan Date: " + loanToCreate.getLoanDate());
        System.out.println("Return Date: " + loanToCreate.getReturnDate());

        if(loanRepository.existsById(loanToCreate.getLoanId())){
            throw new IllegalArgumentException("This Loan ID already exists.");
        }

        if(!bookRepository.existsById(loanToCreate.getBook().getIsbn())){
            throw new IllegalArgumentException("This Book Id doesn't exists.");
        }

        if(!userRepository.existsById(loanToCreate.getUser().getId())){
            throw new IllegalArgumentException("This User Id doesn't exists.");
        }

        if(loanToCreate.getBook().getAvailableCopies() == 0){
        throw new IllegalArgumentException("There are no available copies to loan.");
        }

        if(loanToCreate.getLoanDate().isBefore(LocalDate.now()) ){
            throw new IllegalArgumentException("This loan date is invalid.");
        }

        Loan loanCreated = loanRepository.save(loanToCreate);
        return convertToDTO(loanCreated);
    }

    private LoanDTO convertToDTO(Loan loan){
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setLoanId(loan.getLoanId());
        loanDTO.setIsbn(loan.getBook().getIsbn());
        loanDTO.setUserId(loan.getUser().getId());
        loan.setLoanDate(loan.getLoanDate());
        loan.setReturnDate(loan.getReturnDate());
        return loanDTO;
    }
}
