package com.example.application.controller.dto;

import com.example.application.model.Book;
import com.example.application.model.Loan;
import com.example.application.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class LoanDTO {
    private long loanId;
    private long isbn;
    private long userId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Loan toModel(User user , Book book){
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(this.loanDate);
        loan.setReturnDate(this.returnDate);

        return loan;
    }
}
