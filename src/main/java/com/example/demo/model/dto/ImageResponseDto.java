package com.example.demo.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ImageResponseDto {
    private Integer albumId;
    private Integer id;
    private String title;
    private String url;
    private String thumbnailUrl;
    private String downloadDateTime;
    private String localPath;
    private Long fileSize;
}
