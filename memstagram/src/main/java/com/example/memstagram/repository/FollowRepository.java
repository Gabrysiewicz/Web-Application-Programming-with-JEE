package com.example.memstagram.repository;

import com.example.memstagram.model.Follow;
import com.example.memstagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    // Find all follow relationships where the given user is the follower
    List<Follow> findByFollower(User follower);

    // Find all follow relationships where the given user is the followed
    List<Follow> findByFollowed(User followed);
    // Modified to use userId
    List<Follow> findByFollowedId(Long followedId);

    List<Follow> findByFollowerId(Long followerId);

    // Delete Follow by followerId and followedId
    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);

    // Optional: Check if a Follow relationship exists
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);
}
