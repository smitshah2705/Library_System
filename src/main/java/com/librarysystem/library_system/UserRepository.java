package com.librarysystem.library_system;

import org.springframework.data.jpa.repository.JpaRepository;  // Provides basic CRUD operations
import java.util.Optional;  // Optional is useful for cases where a result might not be found
import com.librarysystem.library_system.User;  // Import the User entity

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);  // This will be used for login and authentication
}
