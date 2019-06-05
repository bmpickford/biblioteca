package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Biblioteca {

    private Scanner scanner;
    private PrintStream printStream;
    private LibraryItemManager libraryItemManager;

    public Biblioteca(PrintStream printStream, InputStream inputStream, LibraryItemManager libraryItemManager) {
        scanner = new Scanner(inputStream);

        this.printStream = printStream;
        this.libraryItemManager = libraryItemManager;
    }

    // FIXME: Make this more readable
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
                        String checkoutItemName = scanner.nextLine();
                        LibraryItem checkedOutItem = libraryItemManager.GetItemByName(checkoutItemName);
                        if (checkedOutItem != null) {
                            boolean result = libraryItemManager.Checkout(checkedOutItem);
                            if (result) {
                                printStream.println("Thank you! Enjoy the book");
                                break;
                            }                        }
                        printStream.println("Sorry, that book is not available");
                        break;
                    case "3":
                        printStream.println("Enter the name of the book you want to check in: ");
                        String checkinItemName = scanner.nextLine();
                        LibraryItem checkedInItem = libraryItemManager.GetItemByName(checkinItemName);
                        if (checkedInItem != null) {
                            boolean result = libraryItemManager.Checkin(checkedInItem);
                            if (result) {
                                printStream.println("Thank you for returning the book");
                                break;
                            }
                        }
                        printStream.println("That's not a valid book to return");

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
        printStream.println(libraryItemManager.PrintItems());
    }
}
