package com.example.memstagram.controller;

import com.example.memstagram.model.Follow;
import com.example.memstagram.dto.UnfollowRequest;
import com.example.memstagram.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.memstagram.dto.FollowedDto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FollowController {

    @Autowired
    private FollowService followService;

    // Get all followers of a user
    @GetMapping("/followers/{userId}")
    public List<Follow> getFollowers(@PathVariable Long userId) {
        return followService.getFollowers(userId);
    }
    // Get all users that a specific user is following
    @GetMapping("/following/{userId}")
    public List<Follow> getFollowing(@PathVariable Long userId) {
        return followService.getFollowing(userId);
    }
    // Get all followers of a user by username
    @GetMapping("/followers/username/{username}")
    public List<FollowedDto> getFollowersByUsername(@PathVariable String username) {
        return followService.getFollowersByUsername(username);
    }

    // Get all users that a specific user is following by username
    @GetMapping("/following/username/{username}")
    public List<FollowedDto> getFollowingByUsername(@PathVariable String username) {
        return followService.getFollowingByUsername(username);
    }
    // Create a new follow relationship
    @PostMapping("/follow")
    public ResponseEntity<Follow> createFollow(@RequestBody Follow follow) {
        // Validate input
        if (follow.getFollower() == null || follow.getFollower().getId() == null ||
                follow.getFollowed() == null || follow.getFollowed().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            // Delegate to service to save the follow relationship
            Follow createdFollow = followService.createFollow(follow);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFollow);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Unfollow a user
    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestBody UnfollowRequest unfollowRequest) {
        try {
            Long followerId = unfollowRequest.getFollowerId();
            Long followedId = unfollowRequest.getFollowedId();

            // Validate that the input is present
            if (followerId == null || followedId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Both followerId and followedId must be provided.");
            }

            followService.unfollow(followerId, followedId);
            return ResponseEntity.noContent().build(); // Return 204 No Content

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
