package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        LibraryItemManager libraryItemManager = new LibraryItemManager(
            new Book("Where's Wally?", "Martin Handford", 1987),
            new Book("Where's Wally Now?", "Martin Handford", 1988),
            new Book("Where's Wally? The Fantastic Journey", "Martin Handford", 1989),
            new Book("Where's Wally in Hollywood?", "Martin Handford", 1993),
            new Book("Where's Wally? The Wonder Book", "Martin Handford", 1997),
            new Book("WWhere's Wally? The Great Picture Hunt!", "Martin Handford", 2006),
            new Book("Where's Wally? The Incredible Paper Chase", "Martin Handford", 2009)
        );
        Biblioteca biblioteca = new Biblioteca(System.out, System.in, libraryItemManager);
        biblioteca.Start();
    }
}
