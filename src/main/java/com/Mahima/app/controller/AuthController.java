
package com.Mahima.app.controller;

import com.Mahima.app.dto.RegisterRequest;
import com.Mahima.app.model.User;
import com.Mahima.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            org.springframework.ui.Model model) {

        // Create RegisterRequest manually
        RegisterRequest request = new RegisterRequest();
        request.setName(name);
        request.setEmail(email);
        request.setPassword(password);

        User registeredUser = authService.registerUser(request);

        // Optionally, you can set success message
        model.addAttribute("success", "User registered successfully. Please log in.");
        return "login";  // Redirect to login page with message
    }
}

