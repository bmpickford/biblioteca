package com.twu.biblioteca;

import java.util.ArrayList;

public class LibraryItemManager {

    private ArrayList<Book> items = new ArrayList<>();
    private ArrayList<Book> checkedInItems = new ArrayList<>();

    public LibraryItemManager(Book ...book) {
        for(Book b : book) {
            items.add(b);
            checkedInItems.add(b);
        }
    }

    public boolean Checkout(Book book) {
        return checkedInItems.remove(book);
    }

    public boolean Checkin(Book book) {
        if (items.contains(book) && !checkedInItems.contains(book)) {
            return checkedInItems.add(book);
        }
        return false;
    }

    public String PrintItems() {
        StringBuilder printedItems = new StringBuilder();
        for(Book item : checkedInItems) {
            printedItems.append(item.PrintDetails()).append("\n");
        }

        return printedItems.toString();
    }

    public ArrayList<Book> GetItems() {
        return checkedInItems;
    }
}
