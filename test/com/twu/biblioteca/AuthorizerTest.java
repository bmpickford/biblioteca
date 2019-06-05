package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import javax.naming.AuthenticationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class AuthorizerTest {

    private Authorizer authorizer;
    private final Customer authorizedCustomer = new Customer("000-0000", "password");

    @Before
    public void CustomerSetup() {
        authorizer = new Authorizer(authorizedCustomer);
    }

    @Test
    public void ShouldBeAbleToAuthorizeExistingCustomerUsingCredentials() throws AuthenticationException {
        Customer customer = authorizer.Authorize("000-0000", "password");
        assertThat(customer, is(authorizedCustomer));
    }

    @Test(expected = AuthenticationException.class)
    public void ShouldRejectAuthorizationWhenCustomerEntersIncorrectUsername() throws AuthenticationException {
        Customer customer = authorizer.Authorize("000-0000", "notthepassword");
        assertThat(customer, notNullValue());
    }

    @Test(expected = AuthenticationException.class)
    public void ShouldRejectAuthorizationWhenCustomerEntersIncorrectPassword() throws AuthenticationException {
        Customer customer = authorizer.Authorize("username", "password");
        assertThat(customer, notNullValue());
    }
}
