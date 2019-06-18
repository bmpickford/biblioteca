package com.twu.biblioteca;

import javax.naming.AuthenticationException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Biblioteca {

    private Scanner scanner;
    private PrintStream printStream;
    private LibraryItemManager libraryItemManager;
    private Authenticator authenticator;
    private Customer loggedInCustomer = null;

    // Input options used for authorized users
    public static final String EXIT = "0";
    public static final String LIST_ITEMS = "1";
    public static final String CHECKOUT = "2";
    public static final String CHECKIN = "3";
    public static final String VIEW_CUSTOMER_DETAILS = "4";
    public static final String LOGOUT = "5";

    // Input options used for unauthorized users
    public static final String LOGIN = "1";

    public Biblioteca(PrintStream printStream, InputStream inputStream, LibraryItemManager libraryItemManager, Authenticator authenticator) {
        scanner = new Scanner(inputStream);

        this.printStream = printStream;
        this.libraryItemManager = libraryItemManager;
        this.authenticator = authenticator;
    }

    public void Start() {
        printWelcomeMessage();

        // Main loop for input options
        while (true) {
            if (loggedInCustomer != null) {
                if (showAuthorizedOptions()) {
                    return;
                }
            } else {
                if (showUnauthorizedOptions()) {
                    return;
                }
            }
        }

    }

    // Operates the input based on the authorized options. Returns true if the program should exit
    private boolean showAuthorizedOptions() {
        printOptionsMessage();

        if (!scanner.hasNext()) {
            return true;
        }
        switch (scanner.nextLine()) {
            case EXIT:
                return true;
            case LIST_ITEMS:
                printItemList();
                break;
            case CHECKOUT:
                checkoutItem();
                break;
            case CHECKIN:
                checkinItem();
                break;
            case VIEW_CUSTOMER_DETAILS:
                printCustomerDetails();
                break;
            case LOGOUT:
                logoutCustomer();
                break;
            default:
                printStream.println("That is not a valid option");
        }
        return false;
    }

    private void printCustomerDetails() {
        printStream.println(loggedInCustomer.PrintDetails());
    }

    private void logoutCustomer() {
        printStream.println("Logout success");
        loggedInCustomer = null;
    }

    // Operates the input based on the unauthorized options. Returns true if the program should exit
    private boolean showUnauthorizedOptions() {
        printLoginOptionsMessage();

        if (!scanner.hasNext()) {
            return true;
        }
        switch (scanner.nextLine()) {
            case EXIT:
                return true;
            case LOGIN:
                loginCustomer();
                break;
            default:
                printStream.println("That is not a valid option");
        }
        return false;
    }

    private void loginCustomer() {
        printStream.println("Enter your username: ");
        String username = scanner.nextLine();

        printStream.println("Enter your password: ");
        String password = scanner.nextLine();

        try {
            loggedInCustomer = authenticator.Authorize(username, password);
        } catch (AuthenticationException e) {
            printStream.println("Invalid credentials");
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
        printStream.println("Enter the name of the item you want to checkin: ");
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
        printStream.println("Welcome to Biblioteca. Your one-stop-shop for great book and movie titles in Bangalore!");
    }

    private void printCheckoutSuccessMessage() {
        printStream.println("Thank you! Enjoy the item");
    }

    private void printCheckoutFailMessage() {
        printStream.println("Sorry, that item is not available");
    }

    private void printCheckinSuccessMessage() {
        printStream.println("Thank you for returning the item");
    }

    private void printCheckinFailMessage() {
        printStream.println("That's not a valid item to return");
    }

    private void printOptionsMessage() {
        printStream.println("Input the number of your option as shown below: \n" +
                "0. Quit\n" +
                "1. Show items list\n" +
                "2. Checkout an item\n" +
                "3. Checkin an item\n" +
                "4. View my details\n" +
                "5. Logout");
    }

    private void printLoginOptionsMessage() {
        printStream.println("Input the number of your option as shown below: \n" +
                "0. Quit\n" +
                "1. Login");
    }

    private void printItemList() {
        printStream.println(libraryItemManager.PrintItems());
    }
}
