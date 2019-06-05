package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LibraryManagerTest {
    private LibraryItemManager libraryItemManager;

    private Book book1 = new Book("Anna Karenina", "Leo Tolstoy", 1878);
    private Book book2 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1922);

    private Movie movie1 = new Movie("The Great Gatsby", "Baz Luhrmann", 2013, Movie.RatingOption.EIGHT);
    private Movie movie2 = new Movie("A Few Good Men", "Rob Reiner", 1992, Movie.RatingOption.SEVEN);

    @Before
    public void setupLibraryManager() {
        libraryItemManager = new LibraryItemManager(book1, book2, movie1, movie2);
    }

    @Test
    public void ShouldRemoveItemFromLibraryItemListOnCheckout() {
        boolean result = libraryItemManager.Checkout(book1);
        ArrayList<LibraryItem> expectedList = new ArrayList<>();
        expectedList.addAll(new ArrayList<>(){{ add(book2); add(movie1); add(movie2); }});

        assertThat(result, is(true));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void ShouldNotRemoveItemFromListUsingNonExistentBook() {
        boolean result = libraryItemManager.Checkout(new Book("", "", 1900));
        ArrayList<LibraryItem> expectedList = new ArrayList<>();
        expectedList.addAll(new ArrayList<>(){{ add(book1); add(book2); add(movie1); add(movie2); }});

        assertThat(result, is(false));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void ShouldNotRemoveItemFromListWhenAlreadyCheckedOutBook() {
        boolean result = libraryItemManager.Checkout(book1);
        assertThat(result, is(true));

        result = libraryItemManager.Checkout(book1);
        ArrayList<LibraryItem> expectedList = new ArrayList<>();
        expectedList.addAll(new ArrayList<>(){{ add(book2); add(movie1); add(movie2); }});

        assertThat(result, is(false));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void ShouldAddToLibraryItemListOnCheckin() {
        boolean result = libraryItemManager.Checkout(book1);
        assertThat(result, is(true));

        ArrayList<LibraryItem> expectedList = new ArrayList<>();
        expectedList.addAll(new ArrayList<>(){{ add(book2); add(movie1); add(movie2); add(book1); }});

        result = libraryItemManager.Checkin(book1);
        assertThat(result, is(true));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void ItemThatHasNotBeenCheckedOutShouldNotBeAddedAgainOnCheckin() {
        ArrayList<LibraryItem> expectedList = new ArrayList<>();
        expectedList.addAll(new ArrayList<>(){{ add(book1); add(book2); add(movie1); add(movie2); }});

        boolean result = libraryItemManager.Checkin(book1);
        assertThat(result, is(false));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void ItemThatDoesntExistShouldNotBeAddedOnCheckin() {
        ArrayList<LibraryItem> expectedList = new ArrayList<>();
        expectedList.addAll(new ArrayList<>(){{ add(book1); add(book2); add(movie1); add(movie2); }});

        boolean result = libraryItemManager.Checkin(new Book("", "", 1900));
        assertThat(result, is(false));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void CheckoutBookAndMovieShouldBothBeRemovedFromList() {
        boolean result = libraryItemManager.Checkout(book1);
        assertThat(result, is(true));

        result = libraryItemManager.Checkout(movie1);
        assertThat(result, is(true));

        ArrayList<LibraryItem> expectedList = new ArrayList<>();
        expectedList.addAll(new ArrayList<>(){{ add(book2); add(movie2); }});

        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }
}
