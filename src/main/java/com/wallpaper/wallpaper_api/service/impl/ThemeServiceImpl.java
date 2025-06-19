package com.wallpaper.wallpaper_api.service.impl;

import com.wallpaper.wallpaper_api.entity.ThemeEntity;
import com.wallpaper.wallpaper_api.repository.ThemeRepository;
import com.wallpaper.wallpaper_api.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public ThemeEntity saveTheme(ThemeEntity themeEntity) {
        return themeRepository.save(themeEntity);
    }
}
