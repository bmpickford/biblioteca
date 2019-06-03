package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class BibiotecaTest {

    private Biblioteca biblioteca;
    private final ByteArrayOutputStream testOutStream = new ByteArrayOutputStream();

    @Before
    public void setup() {
        ArrayList<Book> books = getTestBooks();
        this.biblioteca = new Biblioteca(new PrintStream(testOutStream), new ByteArrayInputStream("q".getBytes()), books);
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void testPrintWelcomeMessage() {
        this.biblioteca.Start();
        assertThat(testOutStream.toString(), is(getWelcomeMessage()));
    }

    @Test
    public void testPrintWithZeroInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream(new byte[] { 0 }));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage() + "That is not a valid option\n"));
    }

    @Test
    public void testPrintWithInvalidInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream("abc".getBytes()));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage() + "That is not a valid option\n"));
    }

    @Test
    public void testPrintWithNoInput() {
        setupBibliotecaWithInputStream(new ByteArrayInputStream(new byte[] {}));
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), is(getWelcomeMessage() + ""));
    }

    @Test
    public void testPrintBookList() {
        InputStream inputStream = new ByteArrayInputStream("1".getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();
        String bookList = String.join("\n", booksToStringArray(getTestBooks()));

        assertThat(testOutStream.toString(), containsString(bookList));
    }

    @Test
    public void testPrintBookListWithNoBooks() {
        Biblioteca biblioteca = new Biblioteca(new PrintStream(testOutStream), new ByteArrayInputStream("1".getBytes()));
        biblioteca.Start();

        assertThat(testOutStream.toString(), containsString(getWelcomeMessage() + "There are no books\n"));
    }

    @Test
    public void testCheckoutBookByName() {
        InputStream inputStream = new ByteArrayInputStream(("2" + System.getProperty("line.separator") + "The Chamber of Secrets" + System.getProperty("line.separator") + "1\nq").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        ArrayList<Book> books = getTestBooks();
        books.remove(1);
        String bookList = String.join("\n", booksToStringArray(books));

        assertThat(testOutStream.toString(), containsString(bookList));
        assertThat(testOutStream.toString(), containsString("Thank you! Enjoy the book"));
    }

    @Test
    public void testCheckoutBookIncorrectName() {
        InputStream inputStream = new ByteArrayInputStream(("2" + System.getProperty("line.separator") + "The Phils Stove" + System.getProperty("line.separator") + "1").getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        assertThat(testOutStream.toString(), containsString("Sorry, that book is not available"));
        assertThat(testOutStream.toString(), containsString(String.join("\n", booksToStringArray(getTestBooks()))));
    }

    @Test
    public void testCheckinBook() {
        Book book = new Book("Gone Girl", "Gillian Flynn", 2012);
        InputStream inputStream = new ByteArrayInputStream(
            (
                "3" + System.getProperty("line.separator") +
                book.Name() + System.getProperty("line.separator") +
                book.Author() + System.getProperty("line.separator") +
                book.YearPublished() + System.getProperty("line.separator") +
                "1"
            ).getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();

        ArrayList<Book> books = getTestBooks();
        books.add(book);
        String bookList = String.join("\n", booksToStringArray(books));

        assertThat(testOutStream.toString(), containsString(bookList));
    }

    @Test
    public void testCheckinBookIncorrectYearFormat() {
        InputStream inputStream = new ByteArrayInputStream(
            (
                "3" + System.getProperty("line.separator") +
                "Gone Girl" + System.getProperty("line.separator") +
                "Gillian Flynn" + System.getProperty("line.separator") +
                "notayear" + System.getProperty("line.separator") +
                "1"
            ).getBytes());
        setupBibliotecaWithInputStream(inputStream);
        this.biblioteca.Start();
        
        assertThat(testOutStream.toString(), containsString(String.join("\n", booksToStringArray(getTestBooks())));
    }

    private String[] booksToStringArray(ArrayList<Book> books) {
        String[] bookNames = new String[books.size()];
        for(int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            bookNames[i] = book.Name() + " | " + book.Author() + " | " + book.YearPublished();
        }

        return bookNames;
    }

    private ArrayList<Book> getTestBooks() {
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("The Philosopher's Stone", "J.K Rowling", 1997));
        books.add(new Book("The Chamber of Secrets", "J.K Rowling", 1998));
        books.add(new Book("The Prisoner of Azkaban", "J.K Rowling", 1999));
        books.add(new Book("The Goblet of Fire", "J.K Rowling", 2000));
        books.add(new Book("The Order of the Phoenix", "J.K Rowling", 2003));
        books.add(new Book("The Half-Blood Prince", "J.K Rowling", 2005));
        books.add(new Book("The Deathly Hallows", "J.K Rowling", 2007));

        return books;
    }

    private void setupBibliotecaWithInputStream(InputStream inputStream) {
        ArrayList<Book> books = getTestBooks();
        this.biblioteca = new Biblioteca(new PrintStream(testOutStream), inputStream, books);
    }

    private String getWelcomeMessage() {
        return "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\nInput the number of your option as shown below: \n0. Quit \n1. Show book list \n2. Checkout a book\n";
    }
}
