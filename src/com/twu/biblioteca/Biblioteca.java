package com.twu.biblioteca;

import java.io.PrintStream;
import java.util.ArrayList;

public class Biblioteca {

    private PrintStream printStream;
    private ArrayList<Book> books = new ArrayList<Book>();

    public Biblioteca(PrintStream printStream) {
        this.printStream = printStream;
    }

    public Biblioteca(PrintStream printStream, ArrayList<Book> books) {
        this.printStream = printStream;
        this.books = books;
    }

    public void Start() {
        printWelcomeMessage();
        if (!books.isEmpty())
            printBookList();
    }

    private void printWelcomeMessage() {
        printStream.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
    }

    private void printBookList() {
        printStream.println("The current list of books is: ");
        for(Book book : books) {
            printStream.println(book.Name() + " | " + book.Author() + " | " + book.YearPublished());
        }
    }
}
