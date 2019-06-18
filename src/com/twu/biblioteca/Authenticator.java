package com.twu.biblioteca;

import javax.naming.AuthenticationException;
import java.util.ArrayList;

public class Authenticator {

    private ArrayList<Customer> customers = new ArrayList<>();
    private static Authenticator auth_instance;

    private Authenticator() {
        customers.add(new Customer("000-0000", "password", "Bob", "bob@builder.com", "0400000000"));
        customers.add(new Customer("000-0001", "password", "Scoop", "scoop@digger.com", "02000000"));
    }

    public Customer Authorize(String username, String password) throws AuthenticationException {
        for (Customer customer : customers) {
            if (customer.Username().equals(username) && customer.Password().equals(password)) {
                return customer;
            }
        }
        throw new AuthenticationException("Credentials don't match");
    }

    public static Authenticator getInstance() {
        if (auth_instance == null) {
            auth_instance = new Authenticator();
        }

        return auth_instance;
    }
}
