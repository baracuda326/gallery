package com.example.demo.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
public class ImageEntity {
    private int albumId;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String title;
    private String url;
    private String thumbnailUrl;
    private String downloadDateTime;
    private String localPath;
    private Long fileSize;
}
