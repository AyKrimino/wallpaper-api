package com.wallpaper.wallpaper_api.service.impl;

import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.repository.WallpaperRepository;
import com.wallpaper.wallpaper_api.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WallpaperServiceImpl implements WallpaperService {

    @Autowired
    private WallpaperRepository wallpaperRepository;

    @Override
    public WallpaperEntity saveWallpaper(WallpaperEntity wallpaperEntity) {
        return wallpaperRepository.save(wallpaperEntity);
    }

    @Override
    public List<WallpaperEntity> getWallpapers() {
        return wallpaperRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<WallpaperEntity> getWallpaper(Integer id) {
        return wallpaperRepository.findById(id);
    }
}
