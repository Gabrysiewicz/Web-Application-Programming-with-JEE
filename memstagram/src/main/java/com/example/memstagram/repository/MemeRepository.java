package com.example.memstagram.repository;

import com.example.memstagram.model.Meme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {

    // Find memes by the user ID (corrected to use 'user' instead of 'userId')
    List<Meme> findByUserId(Long id);

    // Find memes by the username of the user that posted it (corrected to use 'user' instead of 'userUsername')
    List<Meme> findByUserUsername(String username);
}
