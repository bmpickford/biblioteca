package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import javax.naming.AuthenticationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsNull.notNullValue;

public class AuthenticatorTest {

    private Authenticator authenticator;

    @Before
    public void Setup() {
        authenticator = Authenticator.getInstance();
    }
    
    @Test
    public void AuthenticatorShouldBeInitialisedOnGetInstanceOnce() {
        Authenticator authenticator = Authenticator.getInstance();
        assertThat(authenticator, notNullValue());
    }
    
    @Test
    public void ShouldBeAbleToAuthorizeExistingCustomerUsingCredentials() throws AuthenticationException {
        Customer customer = authenticator.Authorize("000-0000", "password");
        assertThat(customer, isA(Customer.class));
    }

    @Test(expected = AuthenticationException.class)
    public void ShouldRejectAuthorizationWhenCustomerEntersIncorrectUsername() throws AuthenticationException {
        Customer customer = authenticator.Authorize("000-0000", "notthepassword");
        assertThat(customer, notNullValue());
    }

    @Test(expected = AuthenticationException.class)
    public void ShouldRejectAuthorizationWhenCustomerEntersIncorrectPassword() throws AuthenticationException {
        Customer customer = authenticator.Authorize("username", "password");
        assertThat(customer, notNullValue());
    }
}
