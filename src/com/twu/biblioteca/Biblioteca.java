package com.twu.biblioteca;

import java.io.PrintStream;

public class Biblioteca {

    private PrintStream printStream;

    public Biblioteca(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void Start() {
        printWelcomeMessage();
    }

    private void printWelcomeMessage() {
        printStream.println("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!");
    }
}
