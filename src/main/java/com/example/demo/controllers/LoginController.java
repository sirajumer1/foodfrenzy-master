package com.example.demo.controllers;


import com.example.demo.loginCredentials.AdminLogin;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @PostMapping
    public String login(@RequestBody AdminLogin adminLogin) {
        System.out.println("Login Request: " + adminLogin);

        // Example: Check email and password hardcoded
        if ("admin@example.com".equals(adminLogin.getEmail()) &&
                "password123".equals(adminLogin.getPassword())) {
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }
}