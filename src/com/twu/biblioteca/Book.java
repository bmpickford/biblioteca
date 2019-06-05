package com.twu.biblioteca;

public class Book extends LibraryItem {

    private String author;

    private final String type = "Book";

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String Author() {
        return author;
    }

    public String PrintDetails() {
        return String.join(" | ", Name(), Author(), String.valueOf(Year()), type);
    }

    public String Type() {
        return type;
    }
}
