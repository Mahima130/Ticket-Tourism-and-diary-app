package com.Mahima.app.controller;

import com.Mahima.app.model.DiaryEntry;
import com.Mahima.app.model.User; // Import the User model
import com.Mahima.app.repository.UserRepository; // Import UserRepository
import com.Mahima.app.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal; // Import Principal for authenticated user
import java.time.LocalDate; // Import LocalDate for setting the date
import java.util.*;
import java.util.Base64;
import java.util.List;

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserRepository userRepository; // To fetch User object from Principal

    @GetMapping("/diary")
    public String getDiaryEntries(Model model, Principal principal) {
        if (principal == null) {
            // User is not authenticated. Redirect to login page.
            // This assumes your Spring Security configuration redirects unauthenticated users to /login
            return "redirect:/login";
        }

        String username = principal.getName(); // In Spring Security, principal.getName() is often the username/email
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + username));

        Long userId = user.getId(); // Get the ID of the authenticated user

        List<DiaryEntry> entries = diaryService.getEntriesByUser(userId);

        List<Map<String, Object>> processedEntries = new ArrayList<>();
        for (DiaryEntry entry : entries) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", entry.getId()); // Include ID for potential future delete/edit features
            map.put("title", entry.getTitle());
            map.put("content", entry.getContent());
            map.put("date", entry.getDate()); // Add date to the map

            if (entry.getImage() != null && entry.getImage().length > 0) {
                String base64Image = Base64.getEncoder().encodeToString(entry.getImage());
                map.put("image", base64Image);
            }

            processedEntries.add(map);
        }

        model.addAttribute("entries", processedEntries);
        model.addAttribute("userId", userId); // Pass userId to the template for the form
        return "diary"; // Renders src/main/resources/templates/diary.html
    }

    @PostMapping("/diary/add")
    public String addDiaryEntry(@RequestParam Long userId,
                                @RequestParam String title,
                                @RequestParam String content,
                                @RequestParam String travelDate, // Capture travelDate from form
                                @RequestParam(required = false) MultipartFile image) {

        DiaryEntry entry = new DiaryEntry();
        entry.setTitle(title);
        entry.setContent(content);
        entry.setDate(LocalDate.parse(travelDate)); // Set the date from the form input

        diaryService.addEntry(entry, image, userId);
        return "redirect:/diary"; // Redirect back to the diary page to show updated entries
    }

    // Optional: Add a delete mapping if you want to allow users to delete entries
    /*
    @PostMapping("/diary/delete/{id}")
    public String deleteDiaryEntry(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // Redirect if not logged in
        }
        String username = principal.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + username));

        diaryService.deleteEntryIfOwnedByUser(id, user.getId()); // You'd need to implement this in service
        return "redirect:/diary";
    }
    */
}