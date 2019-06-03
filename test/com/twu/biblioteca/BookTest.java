package com.twu.biblioteca;

import org.junit.Test;

public class BookTest {

    @Test
    public void TestBookName() {
        Book book = new Book("name");
        assertThat(book.Name, "name");
    }

    @Test
    public void TestEmptyBookName() {
        Book book = new Book();
        assertThat(book.Name, "");
    }

    @Test
    public void TestBookNameWithSpecialCharacters() {
        Book book = new Book("\nthis-is_a\\book");
        assertThat(book.Name, "\nthis-is_a\\book");
    }
}
