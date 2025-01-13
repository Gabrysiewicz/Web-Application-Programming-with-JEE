package com.example.memstagram.service;

import com.example.memstagram.model.Follow;
import com.example.memstagram.model.User;
import com.example.memstagram.repository.FollowRepository;
import com.example.memstagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;

    // Get all followers of a user
    public List<Follow> getFollowers(Long userId) {
        return followRepository.findByFollowedId(userId); // Use ID directly
    }

    public List<Follow> getFollowing(Long userId) {
        return followRepository.findByFollowerId(userId); // Use ID directly
    }

    // Create a new follow relationship
    public Follow createFollow(Follow follow) {
        // Validate that follower and followed users exist in the database
        User follower = userRepository.findById(follow.getFollower().getId())
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User followed = userRepository.findById(follow.getFollowed().getId())
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        // Set the valid User objects
        follow.setFollower(follower);
        follow.setFollowed(followed);

        return followRepository.save(follow);
    }

    // Unfollow a user
    @Transactional
    public boolean unfollow(Long followerId, Long followedId) {
        // Check if the follow relationship exists
        if (!followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            throw new RuntimeException("Follow relationship does not exist.");
        }

        // Delete the follow relationship
        followRepository.deleteByFollowerIdAndFollowedId(followerId, followedId);
        return true;
    }
}
