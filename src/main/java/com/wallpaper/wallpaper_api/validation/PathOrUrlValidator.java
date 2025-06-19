package com.wallpaper.wallpaper_api.validation;

import com.wallpaper.wallpaper_api.dto.WallpaperDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PathOrUrlValidator implements ConstraintValidator<PathOrUrl, WallpaperDto> {
    @Override
    public boolean isValid(WallpaperDto wallpaperDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean hasUrl =
                wallpaperDto.getUrl() != null && !wallpaperDto.getUrl().isBlank();
        boolean hasPath =
                wallpaperDto.getPath() != null && !wallpaperDto.getPath().isBlank();
        return hasUrl || hasPath;
    }
}
