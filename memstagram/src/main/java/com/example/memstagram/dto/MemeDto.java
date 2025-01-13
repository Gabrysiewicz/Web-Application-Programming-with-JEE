package com.example.memstagram.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemeDto {
    private String imageUrl;
    private String description;
    private Long userId; // Add this field for userId
}