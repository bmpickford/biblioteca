package com.twu.biblioteca;

public class Customer {

    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;

    public Customer(String username, String password, String name, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String Username() {
        return username;
    }

    public String Password() {
        return password;
    }

    public String Name() { return name; }

    public String Email() { return email; }

    public String PhoneNumber() { return phoneNumber; }

    public String PrintDetails() {
        return "Name: " + Name() + "\nEmail: " + Email() + "\nPhone: " + PhoneNumber();
    }
}
