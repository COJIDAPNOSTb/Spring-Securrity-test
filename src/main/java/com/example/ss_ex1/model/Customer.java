package com.example.ss_ex1.model;

import jakarta.persistence.*;


@Entity


public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String authority;

    public String getAuthority() {
        return authority;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
