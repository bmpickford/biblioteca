package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {

    private Scanner scanner;
    private PrintStream printStream;
    private ArrayList<Book> books = new ArrayList<Book>();
    private ArrayList<Book> checkedInBooks = new ArrayList<Book>();

    public Biblioteca(PrintStream printStream, InputStream inputStream) {
        scanner = new Scanner(inputStream);

        this.printStream = printStream;
    }

    public Biblioteca(PrintStream printStream, InputStream inputStream, ArrayList<Book> books) {
        scanner = new Scanner(inputStream);

        this.printStream = printStream;
        this.books.addAll(books);
        this.checkedInBooks.addAll(books);
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
                        checkoutBookByName();
                        break;
                    case "3":
                        printStream.println("Enter the name of the book you want to check in: ");
                        checkInBook();
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

    private void checkInBook() {
        String name = scanner.nextLine();

        Book book = getBookByName(name);
        if (book != null && !isBookCheckedIn(book)) {
            checkedInBooks.add(book);
            printStream.println("Thank you for returning the book");
            return;
        }
        printStream.println("That's not a valid book to return");
    }

    private void checkoutBookByName() {
        String bookName = scanner.nextLine();
        Book book = getBookByName(bookName);
        if (book != null && isBookCheckedIn(book)) {
            checkoutBook(book);
            return;
        }
        printStream.println("Sorry, that book is not available");
    }

    private boolean isBookCheckedIn(Book book) {
        return checkedInBooks.contains(book);
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
        checkedInBooks.remove(book);
        printStream.println("Thank you! Enjoy the book");
    }

    private void printWelcomeMessage() {
        printStream.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
    }

    private void printOptionsMessage() {
        printStream.println("Input the number of your option as shown below: \n" +
                "0. Quit \n" +
                "1. Show book list \n" +
                "2. Checkout a book\n" +
                "3. Check in a book");
    }

    private void printBookList() {
        if (!checkedInBooks.isEmpty()) {
            printStream.println("\nThe current list of books is: ");
            for (Book book : checkedInBooks) {
                printStream.println(book.Name() + " | " + book.Author() + " | " + book.Year());
            }
        } else {
            printStream.println("There are no books");
        }
    }
}
