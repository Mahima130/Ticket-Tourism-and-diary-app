package com.Mahima.app.repository;

import com.Mahima.app.model.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntry, Long> { // ⭐ CORRECTED INTERFACE NAME ⭐
    List<DiaryEntry> findByUserId(Long userId);
}