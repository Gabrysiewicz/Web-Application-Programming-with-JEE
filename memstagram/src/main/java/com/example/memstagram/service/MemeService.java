package com.example.memstagram.service;

import com.example.memstagram.dto.MemeDto;
import com.example.memstagram.model.Meme;
import com.example.memstagram.repository.MemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.memstagram.repository.UserRepository;
import com.example.memstagram.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class MemeService {

    @Autowired
    private MemeRepository memeRepository;
    @Autowired
    private UserRepository userRepository;
    // Get all memes
    public List<Meme> getAllMemes() {
        return memeRepository.findAll(Pageable.unpaged()).getContent();
    }

    // Get meme by ID
    public Optional<Meme> getMemeById(Long id) {
        return memeRepository.findById(id);
    }

    // Get memes by User ID
    public List<Meme> getMemesByUserId(Long userId) {
        return memeRepository.findByUserId(userId);
    }

    // Get memes by User Username
    public List<Meme> getMemesByUserUsername(String username) {
        return memeRepository.findByUserUsername(username);
    }

    // Create a new meme
    public Meme createMeme(MemeDto memeDto) {
        // Find the user by ID
        User user = userRepository.findById(memeDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create new meme object
        Meme meme = Meme.builder()
                .imageUrl(memeDto.getImageUrl())
                .description(memeDto.getDescription())
                .user(user)
                .build();

        // Save and return the meme
        return memeRepository.save(meme);
    }

    // Update meme
    public Optional<Meme> updateMeme(Long id, Meme meme) {
        Optional<Meme> existingMemeOpt = memeRepository.findById(id);
        if (existingMemeOpt.isPresent()) {
            Meme existingMeme = existingMemeOpt.get();

            // Copy properties from the new meme to the existing one, except for the ID
            existingMeme.setImageUrl(meme.getImageUrl());
            existingMeme.setDescription(meme.getDescription());

            // Save and return the updated meme
            return Optional.of(memeRepository.save(existingMeme));
        }
        return Optional.empty();
    }

    // Delete meme
    public boolean deleteMeme(Long id) {
        Optional<Meme> meme = memeRepository.findById(id);
        if (meme.isPresent()) {
            memeRepository.delete(meme.get()); // Delete the meme from the database
            return true;
        }
        return false; // Return false if the meme was not found
    }
    public List<Meme> getMemesByUserUsernameInRange(String username, Long from, Long to) {
        return memeRepository.findByUserUsernameAndIdBetween(username, from, to);
    }

    public long countMemesByUserUsername(String username) {
        return memeRepository.countByUserUsername(username);
    }
}
