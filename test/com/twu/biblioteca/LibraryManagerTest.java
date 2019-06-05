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

    @Before
    public void setupLibraryManager() {
        libraryItemManager = new LibraryItemManager(book1, book2);
    }

    @Test
    public void checkoutShouldRemoveItemFromLibraryItemList() {
        boolean result = libraryItemManager.Checkout(book1);
        ArrayList<Book> expectedList = new ArrayList<>();
        expectedList.add(book2);

        assertThat(result, is(true));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void checkoutNonExistentBookShouldNotRemoveItemFromList() {
        boolean result = libraryItemManager.Checkout(new Book("", "", 1900));
        ArrayList<Book> expectedList = new ArrayList<>();
        expectedList.add(book1);
        expectedList.add(book2);

        assertThat(result, is(false));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void checkoutAlreadyCheckedOutBookShouldNotRemoveItemFromList() {
        boolean result = libraryItemManager.Checkout(book1);
        assertThat(result, is(true));

        result = libraryItemManager.Checkout(book1);
        ArrayList<Book> expectedList = new ArrayList<>();
        expectedList.add(book2);

        assertThat(result, is(false));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void checkinItemShouldAddToLibraryItemList() {
        boolean result = libraryItemManager.Checkout(book1);
        assertThat(result, is(true));

        ArrayList<Book> expectedList = new ArrayList<>();
        expectedList.add(book2);
        expectedList.add(book1);

        result = libraryItemManager.Checkin(book1);
        assertThat(result, is(true));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void checkinItemThatHasNotBeenCheckedOutShouldNotBeAddedAgain() {
        ArrayList<Book> expectedList = new ArrayList<>();
        expectedList.add(book1);
        expectedList.add(book2);

        boolean result = libraryItemManager.Checkin(book1);
        assertThat(result, is(false));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }

    @Test
    public void checkinItemThatDoesntExistShouldNotBeAdded() {
        ArrayList<Book> expectedList = new ArrayList<>();
        expectedList.add(book1);
        expectedList.add(book2);

        boolean result = libraryItemManager.Checkin(new Book("", "", 1900));
        assertThat(result, is(false));
        assertThat(libraryItemManager.GetItems(), is(expectedList));
    }
}
