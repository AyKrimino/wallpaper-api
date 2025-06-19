package com.wallpaper.wallpaper_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDto {
    private Integer id;

    @NotNull
    @NotBlank(message = "Name must not be blank")
    private String name;

    private String description;

    @NotNull
    @Size(min = 1, message = "At least one wallpaper is required")
    private List<@NotNull @Positive Integer> wallpaperIds = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer currentIndex = 0;

    private Instant createdAt;

    private Instant updatedAt;
}
