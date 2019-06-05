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

    @Before
    public void setup() {
        Book[] books = getTestBooks();
        this.libraryItemManager = new LibraryItemManager(books);
        this.biblioteca = new Biblioteca(new PrintStream(testOutStream), new ByteArrayInputStream("q".getBytes()), libraryItemManager);
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void ShouldPrintWelcomeMessage() {
        this.biblioteca.Start();
        assertThat(testOutStream.toString(), is(getWelcomeMessage()));
    }

    @Test
    public void ShouldPrintInvalidOptionWithByteInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream(new byte[] { 0 }));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage() + "That is not a valid option\n"));
    }

    @Test
    public void ShouldPrintInvalidOptionWithABCInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream("abc".getBytes()));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage() + "That is not a valid option\n"));
    }

    @Test
    public void ShouldPrintWelcomeMessageWithNoInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream(new byte[] {}));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), is(getWelcomeMessage() + ""));
    }

    @Test
    public void ShouldPrintBookList() {
        InputStream inputStream = new ByteArrayInputStream("1".getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();
        String bookList = String.join("\n", libraryItemManager.PrintItems());

        assertThat(testOutStream.toString(), containsString(bookList));
    }

    @Test
    public void ShouldNotErrorWhenNoBooksAreAvailable() {
        Biblioteca biblioteca = new Biblioteca(new PrintStream(testOutStream), new ByteArrayInputStream("1".getBytes()), new LibraryItemManager());
        biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage()));
    }

    @Test
    public void ShouldCheckoutBookUsingBookName() {
        InputStream inputStream = new ByteArrayInputStream(("2" + System.getProperty("line.separator") + "The Chamber of Secrets" + System.getProperty("line.separator") + "1\nq").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        Book[] books = getTestBooks();
        books[1] = null;
        String bookList = String.join("\n", printItemArray(books));

        assertThat(testOutStream.toString(), containsString(bookList));
        assertThat(testOutStream.toString(), containsString("Thank you! Enjoy the book"));
    }

    @Test
    public void ShouldNotSuccessfullyCheckoutBookWithIncorrectName() {
        InputStream inputStream = new ByteArrayInputStream(("2" + System.getProperty("line.separator") + "The Phils Stove" + System.getProperty("line.separator") + "1").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString("Sorry, that book is not available"));
        assertThat(testOutStream.toString(), containsString(String.join("\n", libraryItemManager.PrintItems())));
    }

    @Test
    public void ShouldCheckinBookWhenRequested() {
        String bookName = "The Chamber of Secrets";
        InputStream inputStream = new ByteArrayInputStream(
            (
                "2" + System.getProperty("line.separator") +
                bookName + System.getProperty("line.separator") +
                "3" + System.getProperty("line.separator") +
                bookName + System.getProperty("line.separator") +
                "1"
            ).getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();
        Book[] books = getTestBooks();
        books[1] = null;
        String bookList = String.join("\n", printItemArray(books));

        assertThat(testOutStream.toString(), containsString("Thank you for returning the book"));
        assertThat(testOutStream.toString(), containsString(bookList));
    }

    @Test
    public void ShouldNotCheckInBookAlreadyCheckedIn() {
        InputStream inputStream = new ByteArrayInputStream(
            (
                "3" + System.getProperty("line.separator") +
                "The Chamber of Secrets" + System.getProperty("line.separator") +
                "1"
            ).getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(String.join("\n", libraryItemManager.PrintItems())));
        assertThat(testOutStream.toString(), containsString("That's not a valid book to return"));
    }

    @Test
    public void ShouldNotCheckInBookThatDoesntExistInLibrary() {
        InputStream inputStream = new ByteArrayInputStream(
            (
                "3" + System.getProperty("line.separator") +
                "NotABook" + System.getProperty("line.separator") +
                "1"
            ).getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(String.join("\n", libraryItemManager.PrintItems())));
        assertThat(testOutStream.toString(), containsString("That's not a valid book to return"));
    }

    private Book[] getTestBooks() {
        Book[] books = {
            new Book("The Philosopher's Stone", "J.K Rowling", 1997),
            new Book("The Chamber of Secrets", "J.K Rowling", 1998),
            new Book("The Prisoner of Azkaban", "J.K Rowling", 1999),
            new Book("The Goblet of Fire", "J.K Rowling", 2000),
            new Book("The Order of the Phoenix", "J.K Rowling", 2003),
            new Book("The Half-Blood Prince", "J.K Rowling", 2005),
            new Book("The Deathly Hallows", "J.K Rowling", 2007),
        };

        return books;
    }

    private void setupBibliotecaWithInputStream(InputStream inputStream) {
        Book[] books = getTestBooks();
        this.biblioteca = new Biblioteca(new PrintStream(testOutStream), inputStream, new LibraryItemManager(books));
    }

    private String getWelcomeMessage() {
        return "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\nInput the number of your option as shown below: \n0. Quit \n1. Show book list \n2. Checkout a book\n3. Check in a book\n";
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
