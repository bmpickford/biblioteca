package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class BibiotecaTest {

    private Biblioteca biblioteca;
    private final ByteArrayOutputStream testOutStream = new ByteArrayOutputStream();
    private LibraryItemManager libraryItemManager;

    private Customer customer = new Customer("000-0000", "password", "Bob", "bob@builder.com", "0400000000");

    @Before
    public void setup() {
        LibraryItem[] items = getTestItems();
        this.libraryItemManager = new LibraryItemManager(items);
        this.biblioteca = new Biblioteca(
                new PrintStream(testOutStream),
                new ByteArrayInputStream((loginCustomerCommands(customer) + "0").getBytes()),
                libraryItemManager
        );
    }

    private String loginCustomerCommands(Customer customer) {
        return "1" + System.getProperty("line.separator") +
                customer.Username() + System.getProperty("line.separator") +
                customer.Password() + System.getProperty("line.separator");
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void ShouldPrintWelcomeMessage() {
        this.biblioteca.Start();
        assertThat(testOutStream.toString(), containsString(getWelcomeMessage()));
        assertThat(testOutStream.toString(), containsString(getAuthorizedOptionsMessage()));
    }

    @Test
    public void ShouldPrintInvalidOptionWithByteInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream(new byte[] { 0 }));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage()));
        assertThat(testOutStream.toString(), containsString("That is not a valid option\n"));
    }

    @Test
    public void ShouldPrintInvalidOptionWithABCInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream("abc".getBytes()));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage()));
        assertThat(testOutStream.toString(), containsString("That is not a valid option\n"));
    }

    @Test
    public void ShouldPrintWelcomeMessageWithNoInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream(new byte[] {}));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), is(getWelcomeMessage() + getUnAuthorizedOptionsMessage()));
    }

    @Test
    public void ShouldPrintBookList() {
        InputStream inputStream = new ByteArrayInputStream((loginCustomerCommands(customer) + "1").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();
        String bookList = String.join("\n", libraryItemManager.PrintItems());

        assertThat(testOutStream.toString(), containsString(bookList));
    }

    @Test
    public void ShouldNotErrorWhenNoBooksAreAvailable() {
        Biblioteca biblioteca = new Biblioteca(new PrintStream(testOutStream), new ByteArrayInputStream((loginCustomerCommands(customer) + "1").getBytes()), new LibraryItemManager());
        biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage()));
    }

    @Test
    public void ShouldCheckoutBookUsingBookName() {
        InputStream inputStream = new ByteArrayInputStream((loginCustomerCommands(customer) + "2" + System.getProperty("line.separator") + "The Chamber of Secrets" + System.getProperty("line.separator") + "1\nq").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        LibraryItem[] items = getTestItems();
        items[1] = null;
        String bookList = String.join("\n", printItemArray(items));

        assertThat(testOutStream.toString(), containsString(bookList));
        assertThat(testOutStream.toString(), containsString("Thank you! Enjoy the item"));
    }

    @Test
    public void ShouldNotSuccessfullyCheckoutBookWithIncorrectName() {
        InputStream inputStream = new ByteArrayInputStream((loginCustomerCommands(customer) + "2" + System.getProperty("line.separator") + "The Phils Stove" + System.getProperty("line.separator") + "1").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString("Sorry, that item is not available"));
        assertThat(testOutStream.toString(), containsString(String.join("\n", libraryItemManager.PrintItems())));
    }

    @Test
    public void ShouldCheckinBookWhenRequested() {
        String bookName = "The Chamber of Secrets";
        InputStream inputStream = new ByteArrayInputStream(
            (
                loginCustomerCommands(customer) +
                "2" + System.getProperty("line.separator") +
                bookName + System.getProperty("line.separator") +
                "3" + System.getProperty("line.separator") +
                bookName + System.getProperty("line.separator") +
                "1"
            ).getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();
        LibraryItem[] items = getTestItems();
        items[1] = null;
        String bookList = String.join("\n", printItemArray(items));

        assertThat(testOutStream.toString(), containsString("Thank you for returning the item"));
        assertThat(testOutStream.toString(), containsString(bookList));
    }

    @Test
    public void ShouldNotCheckInBookAlreadyCheckedIn() {
        InputStream inputStream = new ByteArrayInputStream(
            (
                loginCustomerCommands(customer) +
                "3" + System.getProperty("line.separator") +
                "The Chamber of Secrets" + System.getProperty("line.separator") +
                "1"
            ).getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(String.join("\n", libraryItemManager.PrintItems())));
        assertThat(testOutStream.toString(), containsString("That's not a valid item to return"));
    }

    @Test
    public void ShouldNotCheckInBookThatDoesntExistInLibrary() {
        InputStream inputStream = new ByteArrayInputStream(
            (
                loginCustomerCommands(customer) +
                "3" + System.getProperty("line.separator") +
                "NotABook" + System.getProperty("line.separator") +
                "1"
            ).getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(String.join("\n", libraryItemManager.PrintItems())));
        assertThat(testOutStream.toString(), containsString("That's not a valid item to return"));
    }

    @Test
    public void LogoutShouldReturnOptionsToUnauthorized() {
        InputStream inputStream = new ByteArrayInputStream((loginCustomerCommands(customer) + "5").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString("Logout success"));
    }

    @Test
    public void ShouldPrintCustomerDetails() {
        InputStream inputStream = new ByteArrayInputStream((loginCustomerCommands(customer) + "4").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(customer.PrintDetails()));
    }

    private LibraryItem[] getTestItems() {
        LibraryItem[] items = {
            new Book("The Philosopher's Stone", "J.K Rowling", 1997),
            new Book("The Chamber of Secrets", "J.K Rowling", 1998),
            new Book("The Prisoner of Azkaban", "J.K Rowling", 1999),
            new Movie("The Lion King", "Rob Minkoff", 1994, Movie.RatingOption.EIGHT)
        };

        return items;
    }

    private void setupBibliotecaWithInputStream(InputStream inputStream) {
        LibraryItem[] items = getTestItems();
        this.biblioteca = new Biblioteca(new PrintStream(testOutStream), inputStream, new LibraryItemManager(items));
    }

    private String getWelcomeMessage() {
        return "Welcome to Biblioteca. Your one-stop-shop for great book and movie titles in Bangalore!\n";
    }

    private String getAuthorizedOptionsMessage() {
        return "Input the number of your option as shown below: \n0. Quit\n1. Show items list\n2. Checkout an item\n3. Checkin an item\n4. View my details\n5. Logout";
    }

    private String getUnAuthorizedOptionsMessage() {
        return "Input the number of your option as shown below: \n0. Quit\n1. Login\n";
    }

    private String printItemArray(LibraryItem[] items) {
        StringBuilder printedItems = new StringBuilder();
        for(LibraryItem item : items) {
            if (item != null) {
                printedItems.append(item.PrintDetails()).append("\n");
            }
        }

        return printedItems.toString();
    }
}
