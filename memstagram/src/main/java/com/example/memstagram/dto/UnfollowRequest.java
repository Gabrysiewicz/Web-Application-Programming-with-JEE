package com.example.memstagram.dto;

public class UnfollowRequest {
    private Long followerId; // ID of the user who is unfollowing
    private Long followedId; // ID of the user who is being unfollowed

    // Getters and setters
    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }
}