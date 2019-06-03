package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BibiotecaTest {

    private Biblioteca biblioteca;
    private final ByteArrayOutputStream testOutStream = new ByteArrayOutputStream();

    @Before
    public void setup() {
        ArrayList<Book> books = getTestBooks();
        this.biblioteca = new Biblioteca(new PrintStream(testOutStream), books);
    }


    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void testPrintWelcomeMessage() {
        String expectedMessage = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\n Input the number of your option as shown below: \n";
        this.biblioteca.Start();
        assertThat(testOutStream.toString(), is(expectedMessage));
    }

    @Test
    public void testPrintWithZeroInput() {
        this.biblioteca.Start();
        testOutStream.reset();

        // Create input
        ByteArrayInputStream testIn = new ByteArrayInputStream("0".getBytes());
        System.setIn(testIn);

        assertThat(testOutStream.toString(), is("That is not a valid option"));
    }

    @Test
    public void testPrintWithInvalidInput() {
        this.biblioteca.Start();
        testOutStream.reset();

        // Create input
        ByteArrayInputStream testIn = new ByteArrayInputStream("abc".getBytes());
        System.setIn(testIn);

        assertThat(testOutStream.toString(), is("That is not a valid option"));
    }

    @Test
    public void testPrintWithNoInput() {
        this.biblioteca.Start();
        testOutStream.reset();

        // Create input
        ByteArrayInputStream testIn = new ByteArrayInputStream("".getBytes());
        System.setIn(testIn);

        assertThat(testOutStream.toString(), is("That is not a valid option"));
    }

    @Test
    public void testPrintBookList() {
        this.biblioteca.Start();
        testOutStream.reset();

        // Create input
        ByteArrayInputStream testIn = new ByteArrayInputStream("1".getBytes());
        System.setIn(testIn);

        assertThat(testOutStream.toString(), is(booksToStringArray(getTestBooks())));
    }

    @Test
    public void testPrintBookListWithNoBooks() {
        Biblioteca biblioteca = new Biblioteca(new PrintStream(testOutStream));
        biblioteca.Start();
        testOutStream.reset();

        // Create input
        ByteArrayInputStream testIn = new ByteArrayInputStream("1".getBytes());
        System.setIn(testIn);

        assertThat(testOutStream.toString(), is("\n"));
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
}
