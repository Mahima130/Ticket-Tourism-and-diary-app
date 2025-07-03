// In src/main/java/com/Mahima/app/repository/UserRepository.java

package com.Mahima.app.repository;

import com.Mahima.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // ⭐ ADD THIS IMPORT ⭐

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}