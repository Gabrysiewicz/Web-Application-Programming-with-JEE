package com.example.memstagram.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowedDto {
    private Long id;
    private String username;
    private String profileImageUrl;
}