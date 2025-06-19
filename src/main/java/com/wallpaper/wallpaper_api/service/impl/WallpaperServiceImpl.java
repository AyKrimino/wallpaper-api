package com.wallpaper.wallpaper_api.service.impl;

import com.wallpaper.wallpaper_api.entity.WallpaperEntity;
import com.wallpaper.wallpaper_api.repository.WallpaperRepository;
import com.wallpaper.wallpaper_api.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WallpaperServiceImpl implements WallpaperService {

    @Autowired
    private WallpaperRepository wallpaperRepository;

    @Override
    public WallpaperEntity saveWallpaper(WallpaperEntity wallpaperEntity) {
        return wallpaperRepository.save(wallpaperEntity);
    }

    @Override
    public Page<WallpaperEntity> getWallpapers(Pageable pageable) {
        return wallpaperRepository.findAll(pageable);
    }

    @Override
    public Optional<WallpaperEntity> getWallpaper(Integer id) {
        return wallpaperRepository.findById(id);
    }

    @Override
    public boolean isWallpaperExist(Integer id) {
        return wallpaperRepository.existsById(id);
    }

    @Override
    public void deleteWallpaper(Integer id) {
        wallpaperRepository.deleteById(id);
    }
}
