package com.example.demo.model.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Image {
    private Integer albumId;
    private Integer id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
