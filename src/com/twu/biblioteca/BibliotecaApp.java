package com.twu.biblioteca;

import java.util.ArrayList;

public class BibliotecaApp {

    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("Where's Wally?", "Martin Handford", 1987));
        books.add(new Book("Where's Wally Now?", "Martin Handford", 1988));
        books.add(new Book("Where's Wally? The Fantastic Journey", "Martin Handford", 1989));
        books.add(new Book("Where's Wally in Hollywood?", "Martin Handford", 1993));
        books.add(new Book("Where's Wally? The Wonder Book", "Martin Handford", 1997));
        books.add(new Book("WWhere's Wally? The Great Picture Hunt!", "Martin Handford", 2006));
        books.add(new Book("Where's Wally? The Incredible Paper Chase", "Martin Handford", 2009));

        Biblioteca biblioteca = new Biblioteca(System.out, books);
        biblioteca.Start();
    }
}
