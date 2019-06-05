package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Biblioteca {

    private Scanner scanner;
    private PrintStream printStream;
    private LibraryItemManager libraryItemManager;

    // Input options used
    public static final String EXIT = "0";
    public static final String LIST_ITEMS = "1";
    public static final String CHECKOUT = "2";
    public static final String CHECKIN = "3";

    public Biblioteca(PrintStream printStream, InputStream inputStream, LibraryItemManager libraryItemManager) {
        scanner = new Scanner(inputStream);

        this.printStream = printStream;
        this.libraryItemManager = libraryItemManager;
    }

    public void Start() {
        printWelcomeMessage();

        // Main loop for input options
        while (true) {
            printOptionsMessage();

            if (!scanner.hasNext()) {
                return;
            }
            switch (scanner.nextLine()) {
                case EXIT:
                    return;
                case LIST_ITEMS:
                    printBookList();
                    break;
                case CHECKOUT:
                    checkoutItem();
                    break;
                case CHECKIN:
                    checkinItem();
                    break;
                default:
                    printStream.println("That is not a valid option");
            }
        }
    }

    private void checkoutItem() {
        printStream.println("Enter the name of the item you want to checkout: ");
        String checkoutItemName = scanner.nextLine();

        LibraryItem checkedOutItem = libraryItemManager.GetItemByName(checkoutItemName);
        if (checkedOutItem != null) {
            boolean result = libraryItemManager.Checkout(checkedOutItem);
            if (result) {
                printCheckoutSuccessMessage();
                return;
            }
        }
        printCheckoutFailMessage();
    }

    private void checkinItem() {
        printStream.println("Enter the name of the item you want to check in: ");
        String checkinItemName = scanner.nextLine();

        LibraryItem checkedInItem = libraryItemManager.GetItemByName(checkinItemName);
        if (checkedInItem != null) {
            boolean result = libraryItemManager.Checkin(checkedInItem);
            if (result) {
                printCheckinSuccessMessage();
                return;
            }
        }
        printCheckinFailMessage();
    }

    private void printWelcomeMessage() {
        printStream.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
    }

    private void printCheckoutSuccessMessage() {
        printStream.println("Thank you! Enjoy the book");
    }

    private void printCheckoutFailMessage() {
        printStream.println("Sorry, that book is not available");
    }

    private void printCheckinSuccessMessage() {
        printStream.println("Thank you for returning the book");
    }

    private void printCheckinFailMessage() {
        printStream.println("That's not a valid book to return");
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
