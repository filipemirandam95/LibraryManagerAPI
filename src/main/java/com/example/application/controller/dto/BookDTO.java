package com.example.application.controller.dto;

import com.example.application.model.Author;
import com.example.application.model.Book;
import com.example.application.view.View;
import com.fasterxml.jackson.annotation.JsonView;

public class BookDTO {
    @JsonView(View.HideIdView.class)
    private Long isbn;
    @JsonView(View.DefaultView.class)
    private String title;
    @JsonView({View.HideIdView.class, View.BookView.class})
    private long authorId;
    @JsonView(View.DefaultView.class)
    private int availableCopies;

    public BookDTO() {
        this.isbn = 0L;
    }

    public BookDTO(Book model) {
        this.isbn = model.getIsbn();
        this.title = model.getTitle();
        this.authorId = model.getAuthor().getId();
        this.availableCopies = model.getAvailableCopies();
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Book toModel(Author author){
        Book book = new Book();
        book.setIsbn(this.isbn);
        book.setTitle(this.title);
        book.setAuthor(author);
        book.setAvailableCopies(this.availableCopies);
        return book;
    }
}
