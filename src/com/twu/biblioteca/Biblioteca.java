package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {

    private PrintStream printStream;
    private InputStream inputStream;
    private ArrayList<Book> books = new ArrayList<Book>();

    public Biblioteca(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.inputStream = inputStream;
    }

    public Biblioteca(PrintStream printStream, InputStream inputStream, ArrayList<Book> books) {
        this.printStream = printStream;
        this.inputStream = inputStream;
        this.books = books;
    }

    public void Start() {
        printWelcomeMessage();
        printOptionsMessage();

        Scanner scanner = new Scanner(inputStream);
        if (scanner.hasNext()) {
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    printBookList();
                    break;
            }
        }
    }

    private void printWelcomeMessage() {
        printStream.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
    }

    private void printOptionsMessage() {
        printStream.println("Input the number of your option as shown below: \n" +
                "1. Show book list");
    }

    private void printBookList() {
        if (!books.isEmpty()) {
            printStream.println("\nThe current list of books is: ");
            for (Book book : books) {
                printStream.println(book.Name() + " | " + book.Author() + " | " + book.YearPublished());
            }
        } else {
            printStream.println("There are no books");
        }
    }
}
