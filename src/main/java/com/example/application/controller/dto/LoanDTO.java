package com.example.application.controller.dto;

import com.example.application.model.Book;
import com.example.application.model.Loan;
import com.example.application.model.User;
import com.example.application.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDate;

public class LoanDTO {

    @JsonView({View.HideIdView.class,View.PutLoanView.class})
    private long loanId;
    @JsonView(View.DefaultView.class)
    private long isbn;
    @JsonView(View.DefaultView.class)
    private long userId;
    @JsonView({View.DefaultView.class,View.PutLoanView.class})
    private LocalDate loanDate;
    @JsonView({View.DefaultView.class,View.PutLoanView.class})
    private LocalDate returnDate;

    public LoanDTO() {
        this.loanId=0L;
    }

    public LoanDTO(Loan loan) {
        this.loanId = loan.getLoanId();
        this.isbn = loan.getBook().getIsbn();
        this.userId = loan.getUser().getId();
        this.loanDate = loan.getLoanDate();
        this.returnDate = loan.getReturnDate();
    }

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
