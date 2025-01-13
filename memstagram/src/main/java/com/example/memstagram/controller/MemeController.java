package com.example.memstagram.controller;

import com.example.memstagram.dto.MemeDto;
import com.example.memstagram.model.Meme;
import com.example.memstagram.service.MemeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MemeController {

    @Autowired
    private MemeService memeService;

    // Get all memes
    @GetMapping("/memes")
    public List<Meme> getAllMemes() {
        return memeService.getAllMemes();
    }

    // Get meme by ID
    @GetMapping("/meme/{id}")
    public ResponseEntity<Meme> getMemeById(@PathVariable Long id) {
        Optional<Meme> meme = memeService.getMemeById(id);
        return meme.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get memes by user id
    @GetMapping("/memes/id/{userId}")
    public List<Meme> getMemesByUserId(@PathVariable Long userId) {
        return memeService.getMemesByUserId(userId);
    }

    // Get memes by user username
    @GetMapping("/memes/username/{username}")
    public List<Meme> getMemesByUserUsername(@PathVariable String username) {
        return memeService.getMemesByUserUsername(username);
    }

    // Create a new meme
    @PostMapping("/meme")
    public ResponseEntity<Meme> createMeme(@RequestBody MemeDto memeDto) {
        Meme createdMeme = memeService.createMeme(memeDto);
        return new ResponseEntity<>(createdMeme, HttpStatus.CREATED);
    }

    // Update meme
    @PutMapping("/meme/{id}")
    public ResponseEntity<Meme> updateMeme(@PathVariable Long id, @Valid @RequestBody Meme meme) {
        Optional<Meme> updatedMeme = memeService.updateMeme(id, meme);
        return updatedMeme.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Soft delete meme by id
    @DeleteMapping("/meme/{id}")
    public ResponseEntity<Void> deleteMeme(@PathVariable Long id) {
        boolean deleted = memeService.deleteMeme(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
