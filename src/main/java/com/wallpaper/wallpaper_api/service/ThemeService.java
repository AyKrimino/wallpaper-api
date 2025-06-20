package com.wallpaper.wallpaper_api.service;

import com.wallpaper.wallpaper_api.entity.ThemeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ThemeService {
    ThemeEntity saveTheme(ThemeEntity themeEntity);

    Page<ThemeEntity> getThemes(Pageable pageable);

    Optional<ThemeEntity> getTheme(Integer id);

    boolean isThemeExist(Integer id);

    void deleteTheme(Integer id);
}
