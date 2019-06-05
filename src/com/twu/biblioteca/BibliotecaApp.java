package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        LibraryItemManager libraryItemManager = new LibraryItemManager(
            new Book("Where's Wally?", "Martin Handford", 1987),
            new Book("Where's Wally Now?", "Martin Handford", 1988),
            new Movie("Easy", "Jane Weinstock", 2003, Movie.RatingOption.FIVE),
            new Movie("Red", "Robert Schwentke", 2010, Movie.RatingOption.SEVEN)
        );
        Customer customer1 = new Customer("000-0000", "password");
        Customer customer2 = new Customer("000-0001", "password");
        Biblioteca biblioteca = new Biblioteca(System.out, System.in, libraryItemManager, new Authorizer(customer1, customer2));
        biblioteca.Start();
    }
}
