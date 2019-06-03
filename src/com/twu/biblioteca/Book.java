package com.twu.biblioteca;

public class Book {

    private String name;
    private String author;
    private int yearPublished;

    public Book(String name, String author, int yearPublished) {
        this.name = name;
        this.author = author;
        this.yearPublished = yearPublished;
    }

    public String Name() {
        return name;
    }

    public String Author() {
        return author;
    }

    public int YearPublished() {
        return yearPublished;
    }
}
