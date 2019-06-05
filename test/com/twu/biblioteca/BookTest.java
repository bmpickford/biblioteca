package com.twu.biblioteca;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BookTest {

    @Test
    public void TestBookName() {
        Book book = new Book("name", "", 0);
        assertThat(book.Name(), is("name"));
    }

    @Test
    public void TestEmptyBookName() {
        Book book = new Book("", "", 0);
        assertThat(book.Name(), is(""));
    }

    @Test
    public void TestBookNameWithSpecialCharacters() {
        Book book = new Book("\nthis-is_a\\book", "", 0);
        assertThat(book.Name(), is("\nthis-is_a\\book"));
    }

    @Test
    public void TestBookAuthor() {
        Book book = new Book("Jonothan Livingstone Seagull", "Richard Baci", 0);
        assertThat(book.Author(), is("Richard Baci"));
    }

    @Test
    public void TestBookEmptyAuthor() {
        Book book = new Book("Jonothan Livingstone Seagull", "", 0);
        assertThat(book.Author(), is(""));
    }

    @Test
    public void TestBookYear() {
        Book book = new Book("Jonothan Livingstone Seagull", "Richard Baci", 1970);
        assertThat(book.Year(), is(1970));
    }

    @Test
    public void TestBookNegativeYear() {
        Book book = new Book("Jonothan Livingstone Seagull", "Richard Baci", -1970);
        assertThat(book.Year(), is(-1970));
    }

    @Test
    public void TestBookZeroYear() {
        Book book = new Book("Jonothan Livingstone Seagull", "Richard Baci", 0);
        assertThat(book.Year(), is(0));
    }
}
