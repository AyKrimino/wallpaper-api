package com.wallpaper.wallpaper_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class WallpaperEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String url;

    private String path;

    private String resolution;

    private String author;

    private String licence;

    @ElementCollection
    @CollectionTable(
            name = "wallpaper_tags",
            joinColumns = @JoinColumn(name = "wallpaper_id")
    )
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}