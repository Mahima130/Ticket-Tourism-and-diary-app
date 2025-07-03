package com.Mahima.app.service;

import com.Mahima.app.model.DiaryEntry;
import com.Mahima.app.model.User; // Keep for other potential uses if any, or if other methods need it
import com.Mahima.app.repository.DiaryRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private UserService userService; // Keeping it for now; its necessity depends on other potential methods.

    @Transactional
    public DiaryEntry saveDiaryEntry(DiaryEntry diaryEntry) {
        return diaryRepository.save(diaryEntry);
    }

    @Transactional
    public DiaryEntry addEntry(DiaryEntry entry, MultipartFile image, Long userId) {
        entry.setUserId(userId);

        if (image != null && !image.isEmpty()) {
            try {
                entry.setImage(image.getBytes());
            } catch (IOException e) {
                System.err.println("Error reading image bytes for diary entry: " + e.getMessage());
                // Consider throwing a custom exception or handling this more robustly
            }
        }
        return diaryRepository.save(entry);
    }

    // ⭐ CORRECTED METHOD: getEntriesByUser now accepts Long userId ⭐
    @Transactional(readOnly = true)
    public List<DiaryEntry> getEntriesByUser(Long userId) { // Changed parameter type
        // Directly use the provided userId to query the repository
        return diaryRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Optional<DiaryEntry> getDiaryEntryById(Long id) {
        return diaryRepository.findById(id);
    }

    @Transactional
    public void deleteDiaryEntry(Long id) {
        diaryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<DiaryEntry> getAllDiaryEntries() {
        return diaryRepository.findAll();
    }

    // ⭐ Optional: If you plan to uncomment the delete logic in Controller ⭐
    /*
    @Transactional
    public boolean deleteEntryIfOwnedByUser(Long entryId, Long userId) {
        Optional<DiaryEntry> entryOpt = diaryRepository.findById(entryId);
        if (entryOpt.isPresent()) {
            DiaryEntry entry = entryOpt.get();
            if (entry.getUserId().equals(userId)) { // Ensure the entry belongs to the user
                diaryRepository.delete(entry);
                return true;
            }
        }
        return false; // Entry not found or not owned by the user
    }
    */
}