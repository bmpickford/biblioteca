package com.twu.biblioteca;

import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BibiotecaTest {

    private Biblioteca biblioteca;
    private final ByteArrayOutputStream testOutStream = new ByteArrayOutputStream();

    @After
    public void restoreStreams() {
        System.setOut(System.out);
    }

    @Test
    public void testWelcomeMessage() {
        this.biblioteca = new Biblioteca(new PrintStream(testOutStream));

        String expectedMessage = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\n";
        this.biblioteca.Start();
        assertThat(testOutStream.toString(), is(expectedMessage));
    }

    @Test
    public void testPrintStartingMessage() {
        ArrayList<Book> books = getTestBooks();
        this.biblioteca = new Biblioteca(new PrintStream(testOutStream), books);

        String bookList = String.join("\n", booksToStringArray(books));

        String expectedMessage = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\nThe current list of books is: \n" + bookList + "\n";
        this.biblioteca.Start();
        assertThat(testOutStream.toString(), is(expectedMessage));
    }

    private String[] booksToStringArray(ArrayList<Book> books) {
        String[] bookNames = new String[books.size()];
        for(int i = 0; i < books.size(); i++) {
            bookNames[i] = books.get(i).Name();
        }

        return bookNames;
    }

    private ArrayList<Book> getTestBooks() {
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("The Philosopher's Stone"));
        books.add(new Book("The Chamber of Secrets"));
        books.add(new Book("The Prisoner of Azkaban"));
        books.add(new Book("The Goblet of Fire"));
        books.add(new Book("The Order of the Phoenix"));
        books.add(new Book("The Half-Blood Prince"));
        books.add(new Book("The Deathly Hallows"));

        return books;
    }
}
