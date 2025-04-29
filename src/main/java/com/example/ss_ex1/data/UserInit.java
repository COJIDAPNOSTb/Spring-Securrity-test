package com.example.ss_ex1.data;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class UserInit {
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserInit(JdbcUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if(!userDetailsManager.userExists("root")) {
            UserDetails user = User.builder().username("root").password(passwordEncoder.encode("root")).roles("ADMIN").build();
        }
        if(!userDetailsManager.userExists("user1")) {
        UserDetails user = User.builder().username("user1").password(passwordEncoder.encode("qwe")).roles("USER").build();
        userDetailsManager.createUser(user);
        }
    }
}
