package com.example.memstagram.repository;

import com.example.memstagram.model.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {

    List<Meme> findByUserId(Long id);
    List<Meme> findByUserUsername(String username);
    List<Meme> findByUserUsernameAndIdBetween(String username, Long from, Long to); // Method for ID range

    long countByUserUsername(String username); // Method to count memes by username

}
