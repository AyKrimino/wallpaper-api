package com.wallpaper.wallpaper_api.service.impl;

import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.repository.WallpaperRepository;
import com.wallpaper.wallpaper_api.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WallpaperServiceImpl implements WallpaperService {
    @Autowired
    private WallpaperRepository wallpaperRepository;

    @Override
    public WallpaperEntity addWallpaper(WallpaperEntity wallpaperEntity) {
        return wallpaperRepository.save(wallpaperEntity);
    }
}
