package com.example.memstagram.repository;

import com.example.memstagram.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by username (ignoring deletion status)
    Optional<User> findByUsername(String username);
    // Find a user by email (ignoring deletion status)
    Optional<User> findByEmail(String email);

}


