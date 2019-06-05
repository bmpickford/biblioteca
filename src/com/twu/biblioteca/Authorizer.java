package com.twu.biblioteca;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.Arrays;

public class Authorizer {

    private ArrayList<Customer> customers = new ArrayList<>();

    public Authorizer(Customer... customers) {
        this.customers.addAll(Arrays.asList(customers));
    }

    public Customer Authorize(String username, String password) throws AuthenticationException {
        for (Customer customer : customers) {
            if (customer.Username().equals(username) && customer.Password().equals(password)) {
                return customer;
            }
        }
        throw new AuthenticationException("Credentials don't match");
    }
}
