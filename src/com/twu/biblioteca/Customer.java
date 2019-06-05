package com.twu.biblioteca;

public class Customer {

    private String username;
    private String password;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String Username() {
        return username;
    }

    public String Password() {
        return password;
    }
}
