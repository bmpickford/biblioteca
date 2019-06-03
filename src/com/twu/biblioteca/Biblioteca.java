package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {

    private Scanner scanner;
    private PrintStream printStream;
    private InputStream inputStream;
    private ArrayList<Book> books = new ArrayList<Book>();

    public Biblioteca(PrintStream printStream, InputStream inputStream) {
        scanner = new Scanner(inputStream);

        this.printStream = printStream;
        this.inputStream = inputStream;
    }

    public Biblioteca(PrintStream printStream, InputStream inputStream, ArrayList<Book> books) {
        scanner = new Scanner(inputStream);

        this.printStream = printStream;
        this.inputStream = inputStream;
        this.books = books;
    }

    public void Start() {
        printWelcomeMessage();

        boolean exitApp = false;

        while (!exitApp) {
            printOptionsMessage();

            if (scanner.hasNext()) {
                String option = scanner.nextLine();
                switch (option) {
                    case "1":
                        printBookList();
                        break;
                    case "2":
                        printStream.println("Enter the name of the book you want to checkout: ");
                        String bookName = scanner.nextLine();
                        checkoutByName(bookName);
                        break;
                    case "q":
                    case "Q":
                    case "0":
                        exitApp = true;
                        break;
                    default:
                        printStream.println("That is not a valid option");
                }
            } else {
                exitApp = true;
            }
        }
    }

    private boolean checkoutByName(String bookName) {
        Book book = getBookByName(bookName);
        if (book != null) {
            checkoutBook(book);
        }
        printStream.println("Sorry, that book is not available");
        return false;
    }

    private Book getBookByName(String bookName) {
        for(int i = 0; i < books.size(); i++) {
            if (books.get(i).Name().equals(bookName)) {
                return books.get(i);
            }
        }
        return null;
    }

    private void checkoutBook(Book book) {
        books.remove(book);
        printStream.println("Thank you! Enjoy the book");
    }

    private void printWelcomeMessage() {
        printStream.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
    }

    private void printOptionsMessage() {
        printStream.println("Input the number of your option as shown below: \n" +
                "0. Quit \n" +
                "1. Show book list \n" +
                "2. Checkout a book");
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
