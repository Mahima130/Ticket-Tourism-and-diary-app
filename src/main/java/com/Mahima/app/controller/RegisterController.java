//package com.Mahima.app.controller;
//
//import com.Mahima.app.dto.RegisterRequest;
//import com.Mahima.app.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/register")
//public class RegisterController {
//
//    @Autowired
//    private AuthService authService;
//
//    @GetMapping
//    public String showRegisterPage() {
//        return "register";
//    }
//
//    @PostMapping
//    public String processRegistration(
//            @RequestParam String name,
//            @RequestParam String email,
//            @RequestParam String password,
//            Model model) {
//
//        RegisterRequest request = new RegisterRequest();
//        request.setName(name);
//        request.setEmail(email);
//        request.setPassword(password);
//
//        try {
//            authService.registerUser(request);
//            model.addAttribute("success", "Registration successful! Please login.");
//            return "redirect:/login";
//        } catch (Exception e) {
//            model.addAttribute("error", "Registration failed: " + e.getMessage());
//            return "register";
//        }
//    }
//}
package com.Mahima.app.controller;

import com.Mahima.app.dto.RegisterRequest;
import com.Mahima.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public String showRegisterPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping
    public String processRegistration(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        RegisterRequest request = new RegisterRequest();
        request.setName(name);
        request.setEmail(email);
        request.setPassword(password);

        try {
            authService.registerUser(request);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            model.addAttribute("registerRequest", request); // Return the entered data
            return "register";
        }
    }
}
