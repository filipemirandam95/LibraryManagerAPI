package com.example.application.service.impl;

import com.example.application.controller.dto.LoanDTO;
import com.example.application.model.Book;
import com.example.application.model.Loan;
import com.example.application.model.User;
import com.example.application.repository.BookRepository;
import com.example.application.repository.LoanRepository;
import com.example.application.repository.UserRepository;
import com.example.application.service.LoanService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
        return loans.stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public LoanDTO getLoanById(Long id) {
        return new LoanDTO(loanRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        User user = userRepository.findById(loanDTO.getUserId()).orElseThrow(NoSuchElementException::new);
        Book book = bookRepository.findById(loanDTO.getIsbn()).orElseThrow(NoSuchElementException::new);
        Loan loanToCreate = loanDTO.toModel(user,book);
        if(loanRepository.existsById(loanToCreate.getLoanId())){
            throw new IllegalArgumentException("This Loan ID already exists.");
        }

        if(!bookRepository.existsById(loanToCreate.getBook().getIsbn())){
            throw new IllegalArgumentException("This Book Id doesn't exist.");
        }

        if(!userRepository.existsById(loanToCreate.getUser().getId())){
            throw new IllegalArgumentException("This User Id doesn't exist.");
        }

        if(loanToCreate.getBook().getAvailableCopies() == 0){
        throw new IllegalArgumentException("There are no available copies to loan.");
        }

        if(loanToCreate.getLoanDate().isBefore(LocalDate.now()) ){
            throw new IllegalArgumentException("The loan date can not be earlier than the current date.");
        }
        loanToCreate.getBook().decrementAvailableCopies();
        bookRepository.save(loanToCreate.getBook());
        Loan loanCreated = loanRepository.save(loanToCreate);
        return new LoanDTO(loanCreated);
    }

    @Override
    public LoanDTO modifyLoan(LoanDTO loanToModify) {
        Optional<Loan> loan = loanRepository.findById(loanToModify.getLoanId());
        if(loan.isPresent()) {
            if (loanToModify.getLoanDate().isBefore(loan.get().getLoanDate())) {
                throw new IllegalArgumentException("The loan date can not be earlier than the original loan date.");
            }
            if (loanToModify.getReturnDate().isBefore(loan.get().getReturnDate())) {
                throw new IllegalArgumentException("The loan return date can not be earlier than the original loan return date.");
            }
            return new LoanDTO(loanRepository.save(loanToModify.toModel(loan.get().getUser(), loan.get().getBook())));
        }else{
            throw new NoSuchElementException("This loan id was not found. ");
        }
    }
}
