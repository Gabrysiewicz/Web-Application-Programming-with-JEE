package com.example.memstagram.controller;

import com.example.memstagram.model.Follow;
import com.example.memstagram.dto.UnfollowRequest;
import com.example.memstagram.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.memstagram.dto.FollowedDto;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Operation(summary = "Get all followers of a user by userId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Followers fetched successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/followers/{userId}")
    public List<Follow> getFollowers(@PathVariable Long userId) {
        return followService.getFollowers(userId);
    }

    @Operation(summary = "Get all users that a specific user is following by userId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Following fetched successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/following/{userId}")
    public List<Follow> getFollowing(@PathVariable Long userId) {
        return followService.getFollowing(userId);
    }

    @Operation(summary = "Get all followers of a user by username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Followers fetched successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/followers/username/{username}")
    public List<FollowedDto> getFollowersByUsername(@PathVariable String username) {
        return followService.getFollowersByUsername(username);
    }

    @Operation(summary = "Get all users that a specific user is following by username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Following fetched successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/following/username/{username}")
    public List<FollowedDto> getFollowingByUsername(@PathVariable String username) {
        return followService.getFollowingByUsername(username);
    }

    @Operation(summary = "Create a new follow relationship")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Follow created successfully",
                    content = @Content(schema = @Schema(implementation = Follow.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PostMapping("/follow")
    public ResponseEntity<Follow> createFollow(@RequestBody Follow follow) {
        if (follow.getFollower() == null || follow.getFollower().getId() == null ||
                follow.getFollowed() == null || follow.getFollowed().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            Follow createdFollow = followService.createFollow(follow);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFollow);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Unfollow a user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Unfollowed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Follow relationship not found", content = @Content)
    })
    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestBody UnfollowRequest unfollowRequest) {
        try {
            Long followerId = unfollowRequest.getFollowerId();
            Long followedId = unfollowRequest.getFollowedId();

            if (followerId == null || followedId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Both followerId and followedId must be provided.");
            }

            followService.unfollow(followerId, followedId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
