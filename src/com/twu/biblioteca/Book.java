package com.twu.biblioteca;

public class Book extends LibraryItem {

    private String author;

    public Book(String name, String author, int yearPublished) {
        this.name = name;
        this.author = author;
        this.year = yearPublished;
    }

    public String Author() {
        return author;
    }

    public String PrintDetails() {
        return String.join(" | ", Name(), Author(), String.valueOf(Year()));
    }
}
