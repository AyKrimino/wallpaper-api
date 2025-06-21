package com.wallpaper.wallpaper_api.service.impl;

import com.wallpaper.wallpaper_api.entity.ThemeEntity;
import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.repository.ThemeRepository;
import com.wallpaper.wallpaper_api.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public ThemeEntity saveTheme(ThemeEntity themeEntity) {
        return themeRepository.save(themeEntity);
    }

    @Override
    public Page<ThemeEntity> getThemes(Pageable pageable) {
        return themeRepository.findAll(pageable);
    }

    @Override
    public Optional<ThemeEntity> getTheme(Integer id) {
        return themeRepository.findById(id);
    }

    @Override
    public boolean isThemeExist(Integer id) {
        return themeRepository.existsById(id);
    }

    @Override
    public void deleteTheme(Integer id) {
        themeRepository.deleteById(id);
    }

    @Override
    public Optional<WallpaperEntity> applyTheme(Integer id) {
        Optional<ThemeEntity> theme = themeRepository.findById(id);
        return theme
                .map(themeEntity -> {
                    Integer idx = themeEntity.getCurrentIndex();
                    int sz = themeEntity.getWallpapers().size();
                    if (idx < 0 || idx > sz - 1) {
                        idx = 0;
                    }

                    WallpaperEntity wallpaper = themeEntity.getWallpapers().get(idx);

                    themeEntity.setCurrentIndex((idx + 1) % sz);
                    themeRepository.save(themeEntity);

                    return Optional.of(wallpaper);
                })
                .orElse(Optional.empty());
    }
}
