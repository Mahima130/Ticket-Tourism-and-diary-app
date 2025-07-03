// In src/main/java/com/Mahima/app/repository/HolidayPackageRepository.java

package com.Mahima.app.repository;

import com.Mahima.app.model.HolidayPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // ⭐ ADD THIS IMPORT ⭐

@Repository
public interface HolidayPackageRepository extends JpaRepository<HolidayPackage, Long> {

    // ⭐ IMPORTANT CHANGE HERE: Ensure findByName returns Optional<HolidayPackage> ⭐
    Optional<HolidayPackage> findByName(String name);
}