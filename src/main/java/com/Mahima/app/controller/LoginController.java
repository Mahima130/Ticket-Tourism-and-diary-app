package com.Mahima.app.controller;

import com.Mahima.app.model.User;
import com.Mahima.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // If your login uses POST
import org.springframework.web.bind.annotation.RequestParam; // If your login uses RequestParam

import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Keep your existing login and registration methods here:
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Assuming you have a login.html
    }

    // Your existing POST method for login (keep as is, just ensure it redirects to /welcome)
    // Example (uncomment and adjust if this is your actual login POST method):
    // @PostMapping("/login")
    // public String processLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
    //     // ... authentication logic ...
    //     if (authenticationSuccess) {
    //         return "redirect:/welcome"; // This redirect is crucial
    //     } else {
    //         redirectAttributes.addFlashAttribute("errorMessage", "Invalid credentials.");
    //         return "redirect:/login";
    //     }
    // }

    // ⭐⭐⭐ REMOVED THE CONFLICTING @GetMapping("/welcome") METHOD FROM HERE ⭐⭐⭐
    // This method is now handled by HomeController to avoid ambiguous mapping.
}