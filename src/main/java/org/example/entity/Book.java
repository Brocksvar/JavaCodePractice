package org.example.entity;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Book {

    @Id
    private UUID id;

    private String title;

    private String author;

    private int publicationYear;

    public Book() {}

    public Book(String title, int publicationYear, String author) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
