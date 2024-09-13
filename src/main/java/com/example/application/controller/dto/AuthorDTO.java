package com.example.application.controller.dto;

import com.example.application.model.Author;
import com.example.application.view.View;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorDTO {
    @JsonView(View.DefaultView.class)
    private Long id;
    @JsonView(View.DefaultView.class)
    private String name;
    @JsonView(View.DefaultView.class)
    private String nationality;
    @JsonView(View.DefaultView.class)
    private List<BookDTO> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public Author toModel(){
        Author author = new Author();
        author.setId(this.id);
        author.setName(this.name);
        author.setNationality(this.nationality);

        if(this.books!=null){
            author.setBooks(this.books.stream().map(bookDTO -> bookDTO.toModel(author)).collect(Collectors.toList()));
        }
        return author;
    }
}
