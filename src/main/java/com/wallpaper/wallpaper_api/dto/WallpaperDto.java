package com.wallpaper.wallpaper_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WallpaperDto {
    private Integer id;

    private String url;

    private String path;

    private String resolution;

    private String author;

    private String licence;

    private Set<String> tags = new HashSet<>();

    private Instant createdAt;

    private Instant updatedAt;
}
