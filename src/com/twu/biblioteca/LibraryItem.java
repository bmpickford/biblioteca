package com.twu.biblioteca;

public abstract class LibraryItem {

    protected String name;
    protected int year;

    public String Name() {
        return name;
    }

    public int Year() {
        return year;
    }

    public abstract String PrintDetails();
}
