package com.wallpaper.wallpaper_api.dto;

import com.wallpaper.wallpaper_api.validation.PathOrUrl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PathOrUrl
public class WallpaperDto {
    private Integer id;

    @Pattern(
            regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
            message = "Must be a valid URL"
    )
    private String url;

    private String path;

    private String resolution;

    private String author;

    private String licence;

    @NotNull @Size(min = 1, message = "At least one tag is required")
    private Set<@NotBlank(message = "Tag must not be blank")String> tags =
            new HashSet<>();

    private Instant createdAt;

    private Instant updatedAt;
}
