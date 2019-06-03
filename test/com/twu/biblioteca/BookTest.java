package com.twu.biblioteca;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BookTest {

    @Test
    public void TestBookName() {
        Book book = new Book("name");
        assertThat(book.Name(), is("name"));
    }

    @Test
    public void TestEmptyBookName() {
        Book book = new Book("");
        assertThat(book.Name(), is(""));
    }

    @Test
    public void TestBookNameWithSpecialCharacters() {
        Book book = new Book("\nthis-is_a\\book");
        assertThat(book.Name(), is("\nthis-is_a\\book"));
    }
}
