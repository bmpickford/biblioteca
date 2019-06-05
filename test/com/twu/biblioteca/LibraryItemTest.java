package com.twu.biblioteca;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LibraryItemTest {

    @Test
    public void BookNameIsEqual() {
        Book book = new Book("name", "", 0);
        assertThat(book.Name(), is("name"));
    }

    @Test
    public void EmptyBookNameIsEqual() {
        Book book = new Book("", "", 0);
        assertThat(book.Name(), is(""));
    }

    @Test
    public void BookNameWithSpecialCharactersIsEqual() {
        Book book = new Book("\nthis-is_a\\book", "", 0);
        assertThat(book.Name(), is("\nthis-is_a\\book"));
    }

    @Test
    public void BookAuthor() {
        Book book = new Book("Jonothan Livingstone Seagull", "Richard Baci", 0);
        assertThat(book.Author(), is("Richard Baci"));
    }

    @Test
    public void BookEmptyAuthor() {
        Book book = new Book("Jonothan Livingstone Seagull", "", 0);
        assertThat(book.Author(), is(""));
    }

    @Test
    public void BookYear() {
        Book book = new Book("Jonothan Livingstone Seagull", "Richard Baci", 1970);
        assertThat(book.Year(), is(1970));
    }

    @Test
    public void BookNegativeYear() {
        Book book = new Book("Jonothan Livingstone Seagull", "Richard Baci", -1970);
        assertThat(book.Year(), is(-1970));
    }

    @Test
    public void BookZeroYear() {
        Book book = new Book("Jonothan Livingstone Seagull", "Richard Baci", 0);
        assertThat(book.Year(), is(0));
    }

    @Test
    public void MoviePrintsCorrectDetails() {
        Movie movie = new Movie("Inception", "Christopher Nolan", 2010, Movie.RatingOption.NINE);
        String expected = "Inception | Christopher Nolan | 2010 | 9 | Movie";

        assertThat(movie.PrintDetails(), is(expected));
    }
}
