package com.twu.biblioteca;

import java.util.ArrayList;

public class BibliotecaApp {

    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("Where's Wally?"));
        books.add(new Book("Where's Wally Now?"));
        books.add(new Book("Where's Wally? The Fantastic Journey"));
        books.add(new Book("Where's Wally in Hollywood?"));
        books.add(new Book("Where's Wally? The Wonder Book"));
        books.add(new Book("WWhere's Wally? The Great Picture Hunt!"));
        books.add(new Book("Where's Wally? The Incredible Paper Chase"));

        Biblioteca biblioteca = new Biblioteca(System.out, books);
        biblioteca.Start();
    }
}
