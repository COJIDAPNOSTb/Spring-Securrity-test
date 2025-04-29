package com.example.ss_ex1.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    @Secured("ROLE_ADMIN")
    public String hello() {
        return "hello";
    }

    @GetMapping("/good")
    public String good() {
        return "good";
    }

    @PostMapping("/hell")
    @PermitAll
    public String hi() {
        return "hi";
    }
}
