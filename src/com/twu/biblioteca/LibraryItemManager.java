package com.twu.biblioteca;

import java.util.ArrayList;

public class LibraryItemManager {

    private ArrayList<LibraryItem> items = new ArrayList<>();
    private ArrayList<LibraryItem> checkedInItems = new ArrayList<>();

    public LibraryItemManager(LibraryItem... libraryItem) {
        for(LibraryItem item : libraryItem) {
            items.add(item);
            checkedInItems.add(item);
        }
    }

    public boolean Checkout(LibraryItem libraryItem) {
        return checkedInItems.remove(libraryItem);
    }

    public boolean Checkin(LibraryItem libraryItem) {
        if (items.contains(libraryItem) && !checkedInItems.contains(libraryItem)) {
            return checkedInItems.add(libraryItem);
        }
        return false;
    }

    public String PrintItems() {
        StringBuilder printedItems = new StringBuilder();
        for(LibraryItem item : checkedInItems) {
            printedItems.append(item.PrintDetails()).append("\n");
        }

        return printedItems.toString();
    }

    public ArrayList<LibraryItem> GetItems() {
        return checkedInItems;
    }

    public LibraryItem GetItemByName(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).name.equals(name)){
                return items.get(i);
            }
        }
        return null;
    }
}
