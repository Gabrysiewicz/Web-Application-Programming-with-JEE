package com.example.memstagram.controller;

import com.example.memstagram.dto.MemeDto;
import com.example.memstagram.model.Meme;
import com.example.memstagram.service.MemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.memstagram.util.JwtUtil;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MemeController {

    @Autowired
    private MemeService memeService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Get all memes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of all memes",
                    content = @Content(schema = @Schema(implementation = Meme.class)))
    })
    @GetMapping("/memes")
    public List<Meme> getAllMemes() {
        return memeService.getAllMemes();
    }

    @Operation(summary = "Get meme by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meme found",
                    content = @Content(schema = @Schema(implementation = Meme.class))),
            @ApiResponse(responseCode = "404", description = "Meme not found")
    })

    @GetMapping("/meme/{id}")
    public ResponseEntity<Meme> getMemeById(@PathVariable Long id) {
        Optional<Meme> meme = memeService.getMemeById(id);
        return meme.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get memes by user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of memes by user ID",
                    content = @Content(schema = @Schema(implementation = Meme.class)))
    })
    @GetMapping("/memes/id/{userId}")
    public List<Meme> getMemesByUserId(@PathVariable Long userId) {
        return memeService.getMemesByUserId(userId);
    }

    @Operation(summary = "Get memes by user username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of memes by username",
                    content = @Content(schema = @Schema(implementation = Meme.class)))
    })
    @GetMapping("/memes/username/{username}")
    public List<Meme> getMemesByUserUsername(@PathVariable String username) {
        return memeService.getMemesByUserUsername(username);
    }

    @GetMapping("/memes/username/{username}/{from}/{to}")
    public List<Meme> getMemesByUserUsernameInRange(
            @PathVariable String username,
            @PathVariable Long from,
            @PathVariable Long to) {
        return memeService.getMemesByUserUsernameInRange(username, from, to);
    }

    @Operation(summary = "Get count of memes by user username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Count of memes by username",
                    content = @Content(schema = @Schema(implementation = Long.class)))
    })
    @GetMapping("/memes/count/username/{username}")
    public ResponseEntity<Long> countMemesByUserUsername(@PathVariable String username) {
        long count = memeService.countMemesByUserUsername(username);
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Create a new meme")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Meme created successfully",
                    content = @Content(schema = @Schema(implementation = Meme.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/meme")
    public ResponseEntity<Meme> createMeme(HttpServletRequest request, @RequestBody MemeDto memeDto) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtUtil.extractUserId(token);
        if (userId == memeDto.getUserId() ){
            Meme createdMeme = memeService.createMeme(memeDto);
            return new ResponseEntity<>(createdMeme, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Update an existing meme")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Meme updated successfully",
                    content = @Content(schema = @Schema(implementation = Meme.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Meme not found")
    })
    @PutMapping("/meme/{id}")
    public ResponseEntity<Meme> updateMeme(@PathVariable Long id, @Valid @RequestBody Meme meme) {
        Optional<Meme> updatedMeme = memeService.updateMeme(id, meme);
        return updatedMeme.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Delete a meme by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Meme deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Meme not found")
    })
    @DeleteMapping("/meme/{id}")
    public ResponseEntity<Void> deleteMeme(@PathVariable Long id) {
        boolean deleted = memeService.deleteMeme(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
