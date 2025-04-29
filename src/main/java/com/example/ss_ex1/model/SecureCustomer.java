package com.example.ss_ex1.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecureCustomer implements UserDetails{
    private final Customer customer;

    public SecureCustomer(Customer customer) {
        this.customer = customer;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(customer::getAuthority);
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getUsername();
    }

}
